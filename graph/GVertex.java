package org.fleen.geom_Kisrhombille.graph;

import java.util.ArrayList;
import java.util.List;

import org.fleen.geom_Kisrhombille.KPoint;

/*
 * a vertex in the graph
 * unique within the graph
 */
public class GVertex{
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public GVertex(KPoint kvertex){
    this.kvertex=kvertex;}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public KPoint kvertex;
  public List<GEdge> edges=new ArrayList<GEdge>(12);//12 is max edges in gkis geom
  
  //if all of this vertex's edges are closed 
  //(relative to this vertex) then this vertex is closed
  public boolean isClosed(){
    for(GEdge e:edges)
      if(e.isOpen(this))
        return false;
    return true;}
   
  //if any of this vertex's edges are open
  //(relative to this vertex) then this vertex is open
  public boolean isOpen(){
    for(GEdge e:edges)
      if(e.isOpen(this))
        return true;
    return false;}
  
  //return arbitrary open edge from this vertex's list of edges
  //throw exception if this vertex has no open edges
  public GEdge getOpenEdge(){
    for(GEdge e:edges)
      if(e.isOpen(this))
        return e;
    throw new IllegalArgumentException("this vertex is not open");}
  
  /*
   * ################################
   * OBJECT
   * This GVertex does equals and hashcode by value 
   * uses wrapped kvertex
   * ################################
   */
  
  public int hashCode(){
    return kvertex.hashCode();}
  
  public boolean equals(Object a){
    GVertex b=(GVertex)a;
    return b.kvertex.equals(kvertex);}
  
  public String toString(){
    StringBuffer a=new StringBuffer();
    a.append("----------------\n");
    a.append("kv:"+kvertex.toString()+"\n");
    a.append("----------------\n");
    for(GEdge e:edges){
      a.append(e.toString());
      a.append("----------------\n");}
    return a.toString();}
  
}
