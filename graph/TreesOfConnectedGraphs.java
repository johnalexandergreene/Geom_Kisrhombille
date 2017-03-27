package org.fleen.geom_Kisrhombille.graph;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.KPoint;

/*
 * 1..n trees of connected graphs
 * 
 * our jig graph is a disconnected graph
 *   consisting of 1..n connected graphs
 *     consisting of some vertices, edges and polygons
 *     
 * and in-between nested connected graphs are Yards
 * 
 * 
 */
class TreesOfConnectedGraphs{
  
  TreesOfConnectedGraphs(DisconnectedGraph dg){
    disconnectedgraph=dg;
    build();
  }
  
  DisconnectedGraph disconnectedgraph;
  List<ConnectedGraph> roots=new ArrayList<ConnectedGraph>();
  
  /*
   * init enclosure models
   * an enclosuremodelslist is the list of enclosure models for a cg (cg0)
   *   each model contains
   *     the cg (cg1) getting enclosed (by cg0)
   *     the undivided polygon in cg0 that's geometrically containing the enclosed cg1 
   * 
   * connect nodes
   * make parent-child connections
   * for each cg in the system : A
   *   get the cgs that enclose A : S
   *   for each cg in S : T
   *     if T encloses A AND does not enclose any other cg in S then T is the enclosing parent of A
   * 
   * get the roots via cg.getRoot() for all nodes in the system, put in set to cull dupes.
   * 
   */
  
  private void build(){
    initEnclosureModels();
    connectNodes();
    
  System.out.println("ROOTCOUNT="+roots.size());
  }
  
  /*
   * ################################
   * ENCLOSURE MODELS
   * 
   * Given a system of connected graphs (cg)
   * 
   * given 2 cgs : cg0 and cg1
   * There are 3 different possible relationships between them
   * cg0 encloses cg1, cg1 encloses cg0, neither encloses either 
   * 
   * Definition of Enclose.
   * If cg X is geometrically contained within an undivided polygon belonging to 
   * cg Y then X is enclosed by Y
   * 
   * For each cg in the system : cg0
   *   get 2 lists
   *     The list of cgs enclosed by cg0 : enclosuremodels_enclosed
   *     The list of cgs enclosing cg0 : enclosuremodels_enclosing
   * 
   * an enclosure model describes a an enclosure scenario
   * the model holds
   *   cg : the relevant connected graph
   *   polygon : the relevant undivided polygon
   *   
   * ---
   *   
   * a root cg has no enclosing cg
   * a leaf cg encloses no cg
   * ################################
   */
  
  Map<ConnectedGraph,List<EnclosureModel>> 
    //for each cg, that which the cg encloses
    enclosuremodels_enclosed=new Hashtable<ConnectedGraph,List<EnclosureModel>>(),
    //for each cg, that which the cg is enclosed by
    enclosuremodels_enclosing=new Hashtable<ConnectedGraph,List<EnclosureModel>>();
  
  private class EnclosureModel{
    
    EnclosureModel(ConnectedGraph cg,KPolygon polygon){
      this.cg=cg;
      this.polygon=polygon;}
    
    ConnectedGraph cg;
    KPolygon polygon;}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * ENCLOSURE MODELS INIT
   * ++++++++++++++++++++++++++++++++
   */
  
  private void initEnclosureModels(){
    List<EnclosureModel> enclosed,enclosing;
    KPolygon enclosingpolygon;
    for(ConnectedGraph cg0:disconnectedgraph.connectedgraphs){
      //create enclosure model lists
      enclosed=new ArrayList<EnclosureModel>();
      enclosuremodels_enclosed.put(cg0,enclosed);
      enclosing=new ArrayList<EnclosureModel>();
      enclosuremodels_enclosing.put(cg0,enclosing);
      //test enclosure both ways
      for(ConnectedGraph cg1:disconnectedgraph.connectedgraphs){
        if(cg0!=cg1){
          //test if cg0 encloses cg1
          enclosingpolygon=getEnclosingPolygon(cg0,cg1);
          if(enclosingpolygon!=null)
            enclosed.add(new EnclosureModel(cg1,enclosingpolygon));
          //test if cg0 is enclosed by cg1
          enclosingpolygon=getEnclosingPolygon(cg1,cg0);
          if(enclosingpolygon!=null)
            enclosing.add(new EnclosureModel(cg1,enclosingpolygon));}}}}
  
  /*
   * if any undivided polygon in cg0 geometrically contains any point in cg1 
   *   then cg0 is considered to be enclosing cg1
   *   return that polygon
   * if no undivided polygons in cg0 enclose cg1 then
   *   cg0 oes not enclose cg1 
   *   return null
   */
  private KPolygon getEnclosingPolygon(ConnectedGraph enclosing,ConnectedGraph enclosed){
    for(KPolygon p:enclosing.getUndividedPolygons())
      if(encloses(p,enclosed))
        return p;
    return null;}
  
  /*
   * test a single point in cg
   * if that point is geometrically contained in polygon then that polygon
   * encloses cg 
   */
  private boolean encloses(KPolygon polygon,ConnectedGraph cg){
    KPoint v=cg.getVertices().iterator().next().kvertex;
    DPoint p=v.getBasicPoint2D();
    boolean c=polygon.getDefaultPolygon2D().containsPoint(p.x,p.y);
    return c;}
    
  /*
   * ################################
   * CONNECT NODES
   * 
   * In our tree parents enclose children, nestingwise.
   * A node's parent is the immediately enclosing connected graph.
   * 
   * For each cg in the system
   * get its immediate enclosing parent
   * if it doesn't have an enclosing parent then its a root
   *   stick it in the root list
   * if it has an enclosing parent
   *   make parent-child connections
   * ################################
   */
  
  private void connectNodes(){
    //clear all node connections
    for(ConnectedGraph cg:disconnectedgraph.connectedgraphs){
      cg.clearChildren();
      cg.setParent(null);}
    //connect  
    EnclosureModel em;
    for(ConnectedGraph cg:disconnectedgraph.connectedgraphs){
      em=getImmediateEnclosingParent(cg);
      if(em==null){
        roots.add(cg);
      }else{
        em.cg.addChild(cg);
        cg.setParent(em.cg);
        cg.parentalencompassingpolygon=em.polygon;}}}
  
  /*
   * the immediate enclosing parent is the enclosing cg that does not enclose any other cg in the cg's list of enclosing cgs. 
   * 
   * get cg's list of enclosure models : cgenclosuremodels
   * for each enclosuremodel in cgenclosuremodels : m0
   *   for each enclosuremodel in cgenclosuremodels : m1
   *     if(m0!=m1)
   *       if m0.cg encloses m1.cg then try another em0
   *   if m0 encloses none of the m1s then m0 is the immediate enclosing parent. return it.
   * at this point we don't have a parent. return null. cg is root. 
   */
  private EnclosureModel getImmediateEnclosingParent(ConnectedGraph test){
    //if a cg has no enclosing cgs then cg is root
    List<EnclosureModel> enclosing=enclosuremodels_enclosing.get(test);
    if(enclosing.isEmpty())return null;
    //find an enclosing cg that encloses test but does not enclose any other cg that encloses test 
    for(EnclosureModel m:enclosing)
      if(enclosesNoOtherEnclosing(m,enclosing))
        return m;
    return null;}
  
  /*
   * return true if m0's list of enclosed cg models refers to none of the model.cg in enclosing
   */
  private boolean enclosesNoOtherEnclosing(EnclosureModel m,List<EnclosureModel> enclosing){
    List<EnclosureModel> enclosed=enclosuremodels_enclosed.get(m.cg);
    for(EnclosureModel m0:enclosed)
      for(EnclosureModel m1:enclosing)
        if(m0.cg==m1.cg)
          return false;
    return true;}
  
}
