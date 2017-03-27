package org.fleen.geom_Kisrhombille.graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.util.tree.TreeNode;
import org.fleen.util.tree.TreeNodeIterator;
import org.fleen.util.tree.TreeNodeServices;

/*
 * a component in a KPseudograph analysis
 * Contains 1..n vertices, 0..n edges and 0.n polygons
 * we present the analysis in terms of kgeom
 */
public class ConnectedGraph implements TreeNode{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public ConnectedGraph(GVertex v){
    build(v);}
  
  /*
   * ################################
   * GEOMETRY
   * We do it in KGeom form because that's what we're using for everything else
   * All this graph geometry is just used internally to the graphing
   * 
   * we map to strands
   * A strand is a list of points
   * some are open, some describe a loop (polygon). Some are composed of a single vertex.
   * ################################
   */
  
  List<Strand> strands=new ArrayList<Strand>();
  
  /*
   * returns arbitrary open vertex from list of strands
   * returns null if no such exist (which means we're done mapping)
   */
  GVertex getStrandOpenVertex(){
    for(Strand s:strands){
      for(GVertex v:s.gvertices)
        if(v.isOpen())
          return v;}
    return null;}
  
  public Set<GVertex> getVertices(){
    Set<GVertex> vertices=new HashSet<GVertex>();
    for(Strand s:strands)
      vertices.addAll(s.gvertices);
    return vertices;}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * EDGE CENTERS
   * note that we use kvertex default point2ds here
   * it's for comparing geometry withn the graph, it doesn't have to be translated into reality.
   * ++++++++++++++++++++++++++++++++
   */
  
  List<double[]> edgecenters=null;
  
  private List<double[]> getEdgeCenters(){
    if(edgecenters==null)
      initEdgeCenters();
    return edgecenters;}
  
  private void initEdgeCenters(){
    edgecenters=new ArrayList<double[]>();
    for(Strand strand:strands)
      edgecenters.addAll(strand.getEdgeCenters());}
  
  /*
   * ################################
   * ANALYSIS ARTIFACTS
   * ################################
   */

  private void initArtifacts(){
    initKVertices();
    initOpenKVertexSequences();
    initKPolygons();
    initOuterPolygon();
    initUndividedPolygons();}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * VERTICES
   * ++++++++++++++++++++++++++++++++
   */
  
  Set<KPoint> kvertices;
  
  public Set<KPoint> getKVertices(){
    return kvertices;}
  
  private void initKVertices(){
    kvertices=new HashSet<KPoint>();
    for(Strand s:strands){
      for(GVertex gv:s.gvertices)
        kvertices.add(gv.kvertex);}}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * OPEN VERTEX SEQUENCES
   * ++++++++++++++++++++++++++++++++
   */
  
  List<List<KPoint>> openkvertexsequences;
  
  public List<List<KPoint>> getOpenKVertexSequences(){
    return openkvertexsequences;}
  
  private void initOpenKVertexSequences(){
    openkvertexsequences=new ArrayList<List<KPoint>>();
    for(Strand s:strands){
      if(!s.isPolygonal()){
        openkvertexsequences.add(s.getOpenKVertexSequence());}}}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * POLYGONS
   * ++++++++++++++++++++++++++++++++
   */
  
  List<KPolygon> kpolygons;
  
  public List<KPolygon> getKPolygons(){
    return kpolygons;}

  private void initKPolygons(){
    kpolygons=new ArrayList<KPolygon>();
    for(Strand s:strands){
      if(s.isPolygonal()){
        kpolygons.add(s.getKPolygon());}}
    cullDupes();}
  
  /*
   * if 2 polygons have the same vertices, irrespective of order, then they are dupes, right?
   */
  private void cullDupes(){
    Iterator<KPolygon> i0=kpolygons.iterator();
    KPolygon p0;
    while(i0.hasNext()){
      p0=i0.next();
      SEEK:for(KPolygon p1:kpolygons){
        if((p0!=p1)&&(dupes(p0,p1))){
          i0.remove();
          break SEEK;}}}}
  
  private boolean dupes(KPolygon p0,KPolygon p1){
    if(p0.size()!=p1.size())return false;
    if(!p0.containsAll(p1))return false;
    return true;}

  /*
   * ++++++++++++++++++++++++++++++++
   * OUTER POLYGON
   * All points in the graph are either on the outer polygon or contained within it
   * ++++++++++++++++++++++++++++++++
   */
  
  KPolygon outerpolygon;
  
  public KPolygon getOuterPolygon(){
    return outerpolygon;}
  
  private void initOuterPolygon(){
    SEEK:for(KPolygon polygon:getKPolygons()){
      if(isOuterPolygon(polygon)){
        outerpolygon=polygon;
        break SEEK;}}}
  
  /*
   * test polygon to see if its the outer polygon
   * for each point in this conected graph
   * test to see if it's either 
   *   contained within this polygon, geometrically
   *   a member of the set of points that comprises this polygon
   * if yes then that's the outer polygon
   */
  private boolean isOuterPolygon(KPolygon polygon){
    DPolygon polygon2d=polygon.getDefaultPolygon2D();
    DPoint p2d;
    for(KPoint v:getKVertices()){
      if(polygon.contains(v))continue;
      p2d=v.getBasicPoint2D();
      if(!polygon2d.containsPoint(p2d.x,p2d.y))return false;}
    return true;}
  
  
  /*
   * ++++++++++++++++++++++++++++++++
   * UNDIVIDED POLYGONS
   * contains no points
   * contains no edge center points
   * ++++++++++++++++++++++++++++++++
   */
  
  private static final double CLOSEPOINTDISTANCE=0.0001;
  
  List<KPolygon> undividedpolygons;
  
  public List<KPolygon> getUndividedPolygons(){
    return undividedpolygons;}
  
  /*
   * test all the polygons
   * if a polygon contains no vertices and no edge centerpoints then that polygon is undivided
   * 
   * For a connected graph composed of a single polygon, that polygon is both the outer polygon AND an undivided polygon
   */
  private void initUndividedPolygons(){
    undividedpolygons=new ArrayList<KPolygon>();
    for(KPolygon polygon:getKPolygons())
      if(isUndivided(polygon))
        undividedpolygons.add(polygon);}
  
  /*
   * 
   * 
   * TODO we should be caching these 2d points
   * this is a little heavy
   * it would be nice if we could test these points for on-polygon by vertex id, it would speed things up.
   * optimize
   * 
   */
  private boolean isUndivided(KPolygon polygon){
    DPolygon polygon2d=polygon.getDefaultPolygon2D();
    //test points
    //if any of them is neither on (close enough) nor outside polygon2d then this polygon is not undivided
    //note that tyhis is default kgeometry here, so the scale is known. distance testing is ez.
    List<double[]> point2ds=getKVertexPoint2Ds();
    for(double[] p:point2ds){
      if(polygon2d.getDistance(p[0],p[1])<CLOSEPOINTDISTANCE)
        continue;
      if(polygon2d.containsPoint(p[0],p[1]))
        return false;}
    //test edge centers
    List<double[]> edgecenters=getEdgeCenters();
    for(double[] p:edgecenters){
      if(polygon2d.getDistance(p[0],p[1])<CLOSEPOINTDISTANCE)
        continue;
      if(polygon2d.containsPoint(p[0],p[1]))
        return false;}
    //
    return true;}
  
  private List<double[]> getKVertexPoint2Ds(){
    List<double[]> p=new ArrayList<double[]>();
    for(KPoint v:getKVertices())
      p.add(v.getBasicPointCoor());
    return p;}
  
  /*
   * ################################
   * TREENODE
   * ################################
   */
  
  TreeNodeServices treenodeservices=new TreeNodeServices();
  
  public TreeNode getParent(){
    return treenodeservices.getParent();}
  
  public void setParent(TreeNode node){
    treenodeservices.setParent(node);}
  
  public List<? extends TreeNode> getChildren(){
    return treenodeservices.getChildren();}
  
  public TreeNode getChild(){
    return treenodeservices.getChild();}
  
  public void setChildren(List<? extends TreeNode> nodes){
    treenodeservices.setChildren(nodes);}
  
  public void addChild(TreeNode node){
    treenodeservices.addChild(node);}
  
  public void setChild(TreeNode node){
    treenodeservices.setChild(node);}
  
  public int getChildCount(){
    return treenodeservices.getChildCount();}
  
  public boolean hasChildren(){
    return treenodeservices.hasChildren();}
  
  public void removeChild(TreeNode child){
    treenodeservices.removeChild(child);}
  
  public void clearChildren(){
    treenodeservices.clearChildren();}
  
  public boolean isRoot(){
    return treenodeservices.isRoot();}
  
  public boolean isLeaf(){
    return treenodeservices.isLeaf();}
  
  public int getDepth(){
    return treenodeservices.getDepth(this);}
  
  public TreeNode getRoot(){
    return treenodeservices.getRoot(this);}
  
  public TreeNode getAncestor(int levels){
    return treenodeservices.getAncestor(this,levels);}
  
  /*
   * ################################
   * TREENODE PARENT-CHILD GEOMETRIC RELATIONSHIPS
   * If this CG has a parent then this CG is contained within an undivided polygon of that parent
   * If this CG has child/ren then each child is contained within an undivided polygon of this CG
   * ################################
   */
  
  public KPolygon parentalencompassingpolygon=null;
  
  

  
  /*
   * given an undivided polygon in this connected graph, return all children of this ConnectedGraph (treenode) 
   * geometrically contained within that undividedpolygon
   * if the specified undividedpolygon does no geometrically contain any children then an empty list is returned
   */
  public List<ConnectedGraph> getChildrenContainedByUndividedPolygon(KPolygon undividedpolygon){
    List<ConnectedGraph> contained=new ArrayList<ConnectedGraph>();
    List<TreeNode> children=new ArrayList<TreeNode>(getChildren());
    ConnectedGraph cg;
    for(TreeNode n:children){
      cg=(ConnectedGraph)n;
      if(polygonContainsConnectedGraph(undividedpolygon,cg))
        contained.add(cg);}
    return contained;}
  
  public boolean polygonContainsThisConnectedGraph(KPolygon polygon){
    return polygonContainsConnectedGraph(polygon,this);}
  
  /*
   * test one vertex on the graph
   * return true if that vetex is contained within the polygon
   */
  private boolean polygonContainsConnectedGraph(KPolygon polygon,ConnectedGraph cg){
    DPolygon p2d=polygon.getDefaultPolygon2D();
    KPoint v=cg.kvertices.iterator().next();
    DPoint p=v.getBasicPoint2D();
    boolean contained=p2d.containsPoint(p.x,p.y);
    return contained;}
  
  /*
   * ################################
   * BUILD CONNECTED GRAPH ANALYSIS
   * create new Strand : s
   * get arbitrary vertex : v0
   * add v0 to s
   * if v0 has no edges then we're done
   * get arbitrary edge from v0
   * traverse graph from v0, using right turn rule to select edge at subsequent vertices
   * until we hit dead end or our path loops
   * Then the strand is done. add it to strandlist
   * search strandlist for another arbitrary open vertex
   * (a vertex is open when it has a connected edge which is open in the direction starting at that vertex.) 
   * use that to create another strand
   * keep doing this until we have no more open vertices 
   * ################################
   */
  
  private void build(GVertex v0){
    //create our first strand
    Strand strand=new Strand(v0);
    strands.add(strand);
    //main strand mapping loop
    boolean extended,finished=false;
    while(!finished){
      extended=strand.extend();
      if(!extended){
        v0=getStrandOpenVertex();
        if(v0==null){
          finished=true;
        }else{
          strand=new Strand(v0);
          strands.add(strand);}}}
    //finished build
    initArtifacts();}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  /*
   * A textual rendering of the polygons list
   */
  public String toString(){
    StringBuffer a=new StringBuffer();
    a.append("[");
    for(KPolygon p:getKPolygons())
      a.append(p);
    a.append("]");
    return a.toString();}
  
  /*
   * render the Tree of ConnectedGraph nodes rooted at this node
   */
  public String getTreeString(){
    StringBuffer a=new StringBuffer();
    TreeNodeIterator i=new TreeNodeIterator(this);
    ConnectedGraph cg;
    while(i.hasNext()){
      cg=(ConnectedGraph)i.next();
      a.append(cg+"\n");}
    a.append("\n");
    return a.toString();}

  @Override
  public void removeChildren(Collection<? extends TreeNode> children){
    // TODO Auto-generated method stub
    
  }

  @Override
  public List<TreeNode> getSiblings(){
    // TODO Auto-generated method stub
    return null;
  }
  
  
}
