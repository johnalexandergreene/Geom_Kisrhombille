package org.fleen.geom_Kisrhombille.graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.KPoint;

/*
 * A graph where the vertices are kvertices
 * 
 * It's a bunch of vertices and connections between vertices
 * We edit it. It's for editing. We add and remove vertices and edges.
 * 
 * From this we derive a DisconnectedGraph
 *   which contains 1..n ConnectedGraphs
 *     which contain artifacts like vertices, edges and polygons
 *     
 * We talk to the RawGraph in terms of KVertices
 * Internally we use GVertices (Graph Vertices)
 * 
 * FROM THIS WE DERIVE A DISCONNECTED GRAPH
 * 
 * The DisconnectedGraph is this raw graph analyzed and organized. 
 *  
 * The DisconnectedGraph contains 0..n connected graphs. 
 * each connected graph is composed of 
 *   1..n vertices 
 *   0..n edges
 *   0..n kpolygons
 * 
 */
public class Graph{
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public Graph(){}
  
  //construct, and then add a polygon
  //we use it in the forsythia grammar editor jig editor
  public Graph(KPolygon polygon){
    addPolygon(polygon);}
  
  /*
   * ################################
   * EDIT THIS RAW GRAPH
   * This is where we add, remove and connect vertices
   * ################################
   */
  
  public Set<GVertex> vertices=new HashSet<GVertex>();
  public Set<GEdge> edges=new HashSet<GEdge>();
  
  public boolean contains(KPoint v){
    for(GVertex gv:vertices)
      if(gv.kvertex.equals(v))
        return true;
    return false;}
  
  /*
   * create a new gvertex and stick in the set
   */
  public void addVertex(KPoint v){
    invalidateDisconnectedGraph();
    GVertex gv=new GVertex(v);
    vertices.add(gv);}
  
  //remove the vertex and any connected edges
  //if there is no such gvertex associated with the specified, or
  //if the gvertex is immutable, then fail.
  public void removeVertex(KPoint v){
    invalidateDisconnectedGraph();
    GVertex gv=getGVertex(v);
    vertices.remove(gv);
    Iterator<GEdge> i=edges.iterator();
    GEdge edge;
    while(i.hasNext()){
      edge=i.next();
      if(edge.uses(gv)){
        edge.disconnect();
        i.remove();}}}
  
  //connect the specified vertices by creating an edge and setting references
  public void connect(KPoint v0,KPoint v1){
    System.out.println("CONNECT V0 V1");
    invalidateDisconnectedGraph();
    GVertex 
      gv0=getGVertex(v0),
      gv1=getGVertex(v1);
    //create our new edge
    //if it doesn't already exist then set appropriate references and stick it in the edges set
    GEdge e=new GEdge(gv0,gv1);
    if(!edges.contains(e)){
      edges.add(e);
      gv0.edges.add(e);
      gv1.edges.add(e);}}
  
  public void disconnect(KPoint v0,KPoint v1){
    GVertex 
      gv0=getGVertex(v0),
      gv1=getGVertex(v1);
    disconnect(gv0,gv1);}
  
  public void disconnect(GVertex v0,GVertex v1){
    invalidateDisconnectedGraph();
    GEdge e=new GEdge(v0,v1);
    v0.edges.remove(e);
    v1.edges.remove(e);
    edges.remove(e);}
  
  //get gvertex associated with the specified kvertex
  public GVertex getGVertex(KPoint kv){
    GVertex gv0=null;
    for(GVertex gv:vertices)
      if(gv.kvertex.equals(kv)){
        gv0=gv;
        break;}
    if(gv0==null)throw new IllegalArgumentException("specified kvertex is not in this graph : "+kv);
    return gv0;}
  
  /*
   * returns the edge that the specified vertex get crossed by
   * returns null if the vertex doesn't get crossed by any edge 
   */
  public GEdge getCrossingEdge(KPoint v){
    for(GEdge e:edges)
      if(e.crosses(v))
        return e;
    return null;}
  
  public void addPolygon(KPolygon polygon){
    int s,i0,i1;
    KPoint v0,v1;
    s=polygon.size();
    for(KPoint v:polygon)
      addVertex(v);
    for(i0=0;i0<s;i0++){
      i1=i0+1;
      if(i1==s)i1=0;
      v0=polygon.get(i0);
      v1=polygon.get(i1);
      connect(v0,v1);}}
  
  /*
   * ################################
   * DISCONNECTED GRAPH
   * It's an orderly analysis of this graph
   * It contains 
   *   1..n ConnectedGraphs
   * ConnectedGraphs nest, so we arrange them in 1..n trees
   * ConnectedGraphs contain vertices, edges, polygons
   * ################################
   */
  
  private DisconnectedGraph disconnectedgraph=null;
  
  public DisconnectedGraph getDisconnectedGraph(){
    if(disconnectedgraph==null){
      disconnectedgraph=new DisconnectedGraph(this);}
    return disconnectedgraph;}
  
  /*
   * the disconnected graph is an interpretation of the raw graph 
   * so when the raw graph changes we invalidate this disconnected graph
   * because it no longer accurately reflects the raw graph 
   */
  public void invalidateDisconnectedGraph(){
    disconnectedgraph=null;}
  
}
