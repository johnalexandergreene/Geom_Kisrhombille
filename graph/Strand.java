package org.fleen.geom_Kisrhombille.graph;

import java.util.ArrayList;
import java.util.List;

import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.GK;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.KPoint;

/*
 * a sequence of vertices
 * the last may equal the first. if this is the case then a polygon is described (we ignore the dupe).
 */
class Strand{

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  Strand(GVertex v){
    gvertices.add(v);}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  GEdge elast;//last edge in the strand
  List<GVertex> gvertices=new ArrayList<GVertex>();
  
  GVertex getFirst(){
    return gvertices.get(0);}
  
  List<KPoint> getOpenKVertexSequence(){
    List<KPoint> a=new ArrayList<KPoint>();
    for(GVertex gv:gvertices)
      a.add(gv.kvertex);
    return a;}
  
  boolean isPolygonal(){
    boolean a=gvertices.size()>2;
    a=a&&gvertices.get(0).equals(gvertices.get(gvertices.size()-1));
    return a;}
  
  KPolygon getKPolygon(){
    int s=gvertices.size()-1;
    KPolygon p=new KPolygon(s);
    for(int i=0;i<s;i++)
      p.add(gvertices.get(i).kvertex);
    return p;}
  
  /*
   * returns the center point of every edge in this strand
   * used by the isUndivided(KPolygon) test in the ConnectedGraphAnalysis
   * 
   * TODO we need to cache the point2d 
   */
  List<double[]> getEdgeCenters(){
    List<double[]> centers=new ArrayList<double[]>();
    int
      i1,
      s=gvertices.size();
    double[] p0,p1,pcenter;
    for(int i0=0;i0<s;i0++){
      i1=i0+1;
      if(i1==s)i1=0;
      p0=gvertices.get(i0).kvertex.getBasicPointCoor();
      p1=gvertices.get(i1).kvertex.getBasicPointCoor();
      pcenter=GD.getPoint_Mid2Points(p0[0],p0[1],p1[0],p1[1]);
      centers.add(pcenter);}
    return centers;}
  
  /*
   * ################################
   * MAPPING
   * ################################
   */
  
  /*
   * returns true if further extension is possible
   * if no further extension is possible then we're done extending this strand and we return false
   * 
   * we lead with the vertex
   * we probably have a trailing edge
   */
  boolean extend(){
    //if elast is null then we're just starting our traversal
    if(elast==null){
      //if vlast is closed then we're done (one vertex graph)
      GVertex vlast=gvertices.get(gvertices.size()-1);
      if(vlast.isClosed())return false;
      //vlast is open. get an arbitrary open edge from it
      //then get a new vlast from the other end of that edge
      //so now we have a leading vlast and just behind it, elast
      elast=vlast.getOpenEdge();
      elast.registerTraversal(vlast);
      vlast=elast.getOther(vlast);
      gvertices.add(vlast);
      return true;
    //elast is not null. we are in mid-traversal
    }else{
      //if vlast has just 1 edge then we're done (stubbed)
      GVertex vlast=gvertices.get(gvertices.size()-1);
      if(vlast.edges.size()==1)return false;
      //if vcount>1 and vfirst==vlast then we're done (looped)
      int vcount=gvertices.size();
      GVertex vfirst=gvertices.get(0);
      if(vcount>1&&vfirst.equals(vlast))return false;
      //we have a vlast and, connected to its butt, an elast
      //extend to a new elast and vlast
      elast=getNextEdge(elast,vlast);
      if(elast==null)return false;//if there's no elast then we're done TODO???
      elast.registerTraversal(vlast);
      vlast=elast.getOther(vlast);
      gvertices.add(vlast);
      return true;}}
  
  /*
   * RIGHT TURN RULE
   *   given the vertices vprior and vthis
   *   direction vthis -> vprior = d0
   *   given the traversal direction described by any of other available edges : vthis -> vnext : d1
   *   return the edge where d1 is closest to d0, traversing the direction rosette ccw
   *   exclude the prior edge and any edges that have already been traversed in this direction
   *   
   * return null if there are no valid edges available
   */
  public GEdge getNextEdge(GEdge eprior,GVertex vpresent){
    int 
      dtest,
      dreverseprior=vpresent.kvertex.getDirection(eprior.getOther(vpresent).kvertex),
      testoffset,
      minoffset=Integer.MAX_VALUE;
    GEdge rightturnedge=null;
    //get the right turn edge
    for(GEdge e:vpresent.edges){
      if(e.equals(eprior))continue;//exclude eprior
      if(e.isClosed(vpresent))continue;//exclude previously directionally traversed
      dtest=vpresent.kvertex.getDirection(e.getOther(vpresent).kvertex);
      testoffset=GK.getCCWOffset(dreverseprior,dtest);
      if(testoffset<minoffset){
        minoffset=testoffset;
        rightturnedge=e;}}
    return rightturnedge;}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    StringBuffer a=new StringBuffer();
    for(GVertex v:gvertices)
      a.append(v);
    return a.toString();}

}