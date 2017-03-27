package org.fleen.geom_Kisrhombille.graph;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KPoint;


/*
 * a connection between 2 vertices.
 * v0 v1 order doesn't matter
 * unique within the graph
 */
public class GEdge{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public GEdge(){}
  
  public GEdge(GVertex v0,GVertex v1){
    this.v0=v0;
    this.v1=v1;}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public GVertex v0,v1;
  
  public boolean uses(GVertex v){
    return v0.equals(v)||v1.equals(v);}
  
  //assuming that v is one of this edge's vertices, return the other
  //if v is not one of this edge's vertices then throw exception
  public GVertex getOther(GVertex v){
    if(v.equals(v0)){
      return v1;
    }else if(v.equals(v1)){
      return v0;
    }else{
      throw new IllegalArgumentException("the specified vertex is not used by this edge");}}
  
  public void disconnect(){
    v0.edges.remove(this);
    v1.edges.remove(this);}
  
  public boolean crosses(KPoint v){
    DPoint
      pv0=v0.kvertex.getBasicPoint2D(),
      pv1=v1.kvertex.getBasicPoint2D(),
      pv=v.getBasicPoint2D();
    boolean b=GD.isBetween(pv.x,pv.y,pv0.x,pv0.y,pv1.x,pv1.y);
    System.out.println("between="+b);
    return b;}
  
  /*
   * ################################
   * MAPPING TRAVERSAL RECORD
   * when we map the disconnected graph we traverse edges
   * for our mapping alg we want to traverse each edge in each direction at most once
   * so when we traverse this edge, we record the fact here
   * ################################
   */
  
  public boolean
    //record of this strand's traversal in the mapping process
    //the idea is to ensure that a strand gets traversed just once in either direction
    //for example, if this strand hasn't been traversed v0->v1 then traversal_open_v0_v1=true
    traversal_open_v0_v1=true,
    traversal_open_v1_v0=true;
  
  /*
   * mark traversal at specified v0
   */
  public void registerTraversal(GVertex v){
    if(v.equals(this.v0)){
      traversal_open_v0_v1=false;
    }else if(v.equals(this.v1)){
      traversal_open_v1_v0=false;
    }else{
      throw new IllegalArgumentException("specified vertex not on this edge");}}
  
  //init to untraversed, open both ways
  public void initTraversalRecord(){
    traversal_open_v0_v1=true;
    traversal_open_v1_v0=true;}
  
  public boolean isOpen(GVertex v){
    if(v.equals(v0)){
      return traversal_open_v0_v1;
    }else if(v.equals(v1)){
      return traversal_open_v1_v0;
    }else{
      throw new IllegalArgumentException("specified vertex not on this edge");}}
  
  public boolean isClosed(GVertex v){
    return !isOpen(v);}

  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public int hashCode(){
    return v0.hashCode()+v1.hashCode();}
  
  public boolean equals(Object a){
    GEdge b=(GEdge)a;
    return 
      (b.v0.equals(v0)&&b.v1.equals(v1))||
      (b.v1.equals(v0)&&b.v0.equals(v1));}
  
  public String toString(){
    return "["+v0.kvertex.toString()+v1.kvertex.toString()+"]";}

}