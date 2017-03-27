package org.fleen.geom_Kisrhombille.graph;

import java.util.ArrayList;
import java.util.List;

import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KYard;
import org.fleen.util.tree.TreeNodeIterator;

/*
 * Analysis of a graph
 * Contains 1..n ConnectedGraphAnalysis objects
 * TODO stick this in util?
 */
public class DisconnectedGraph{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  DisconnectedGraph(Graph graph){
    this.graph=graph;
    build();}
  
  /*
   * ################################
   * GRAPH
   * the graph from which this disconnected graph is derived
   * ################################
   */
  
  Graph graph;
  
  /*
   * ################################
   * BUILD
   * ################################
   */
  
  private void build(){
    //init traversal record
    for(GEdge e:graph.edges)
      e.initTraversalRecord();
    //traverse the graph
    List<GVertex> vertices=new ArrayList<GVertex>(graph.vertices);
    ConnectedGraph cga;
    GVertex v;
    while(!vertices.isEmpty()){
      v=vertices.remove(0);
      cga=new ConnectedGraph(v);
      connectedgraphs.add(cga);
      vertices.removeAll(cga.getVertices());}}
  
  /*
   * ################################
   * CONNECTED GRAPHS
   * ################################
   */
  
  public List<ConnectedGraph> connectedgraphs=new ArrayList<ConnectedGraph>();

  public boolean hasMutipleConnectedGraphs(){
    return connectedgraphs.size()>1;}
  
  /*
   * ################################
   * OPEN SEQUENCES
   * ################################
   */
  
  public List<List<KPoint>> getOpenKVertexSequences(){
    List<List<KPoint>> seq=new ArrayList<List<KPoint>>();
    for(ConnectedGraph cg:connectedgraphs)
      seq.addAll(cg.getOpenKVertexSequences());
    return seq;}
  
  /*
   * ################################
   * POLYGONS
   * ################################
   */
  
  public List<KPolygon> getPolygons(){
    List<KPolygon> polygons=new ArrayList<KPolygon>();
    for(ConnectedGraph cga:connectedgraphs)
      polygons.addAll(cga.getKPolygons());
    return polygons;}

  public boolean describesSinglePolygon(){
    if(connectedgraphs.size()!=1)return false;
    ConnectedGraph a=connectedgraphs.get(0);
    return a.getKPolygons().size()==1;}
  
  /*
   * ################################
   * UNDIVIDED POLYGONS
   * All the undivided polygons from all the connected graphs
   * used for defining sections in the jig
   * ################################
   */
  
  public List<KPolygon> getUndividedPolygons(){
    List<KPolygon> p=new ArrayList<KPolygon>();
    for(ConnectedGraph g:connectedgraphs)
      p.addAll(g.getUndividedPolygons());
    return p;}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * ANALYSIS ARTIFACTS
   * ++++++++++++++++++++++++++++++++
   */
  
  /*
   * ++++++++++++++++++++++++++++++++
   * YARDS
   * 
   * A yard is the space between unconnected graphs
   * It is defined by an outer edge (an undivided edge of a connected graph)
   * and 1..n inner edges (the outer edges of 1..n connected graphs)
   * 
   * we get area/s when we have multiple connected graphs
   * an area has one outer edge and 1..n inner edges
   * it's a list of polygons
   * the first polygon is the outer edge, the rest are inner edges (holes) 
   * 
   * It's an area defined by an inner edge of a connected graph and the outeredge/s of 1..n connected graphs
   * 
   * I guess we're getting connected graphs here
   * making a tree of nesting connected graphs
   * 
   * For each connected graph get the outer edge polygon and inner undivided polygons (inner edges, basically)
   * 
   * Then test each outer edge against each inner edge. So we get the nesting, the tree
   * then we have an area between each node and its child.
   * 
   * ALG
   * 
   * for each connected graph
   *   test against all other connected graphs
   *     possible outcomes : contains, is contained, neither
   *     (test : test 1 point from graph against outer an undivided polygons) 
   *   hold that in a map
   * with this we should be able to glean the tree. right?
   *   
   * 
   * ++++++++++++++++++++++++++++++++
   */
  
  public List<KYard> getYards(){
    TreesOfConnectedGraphs cgt=new TreesOfConnectedGraphs(this);
    List<KYard> yards=new ArrayList<KYard>();
    for(ConnectedGraph root:cgt.roots)
      yards.addAll(getYardsForCGTreeRoot(root));
    return yards;}
  
  /*
   * get all the ConnectedGraphs in the tree
   * for each ConnectedGraph, get undivided polygons : up
   * for each up, get all of the contained cgs
   * if it has cgs, then create a KYard from the up and the cgs and add it to the list
   */
  private List<KYard> getYardsForCGTreeRoot(ConnectedGraph root){
    List<KYard> yards=new ArrayList<KYard>();
    TreeNodeIterator i=new TreeNodeIterator(root);
    ConnectedGraph cg;
    while(i.hasNext()){
      cg=(ConnectedGraph)i.next();
      yards.addAll(getYardsForCG(cg));}
    return yards;}
  
  /*
   * given a connected graph
   * for each undivided polygons : up
   * if the cg has child cgs contained within this up then create a yard
   * add that yard to the list
   * return the list
   */
  private List<KYard> getYardsForCG(ConnectedGraph cg){
    List<KYard> yards=new ArrayList<KYard>();
    List<ConnectedGraph> children;
    for(KPolygon up:cg.getUndividedPolygons()){
      children=cg.getChildrenContainedByUndividedPolygon(up);
      if(!children.isEmpty())
        yards.add(getYardForUndividedPolygon(up,children));}
    return yards;}
  
  /*
   * glean the outer and inner edges of the yard
   */
  private KYard getYardForUndividedPolygon(KPolygon undividedpolygon,List<ConnectedGraph> children){
    KYard yard=new KYard(children.size()+1);
    yard.add(undividedpolygon);
    for(ConnectedGraph cg:children)
      yard.add(cg.getOuterPolygon());
    return yard;}
  
  /*
   * ################################
   * BUILD
   * ################################
   */


  
 
    
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    StringBuffer s=new StringBuffer();
    s.append("-----------------------\n");
    s.append("vertices : "+graph.vertices.size()+"\n");
    s.append("edges : "+graph.edges.size()+"\n");
    s.append("polygons : "+getPolygons().size()+"\n");
    s.append("connected graphs : "+connectedgraphs.size()+"\n");
    s.append("undivided polygons : "+getUndividedPolygons().size()+"\n");
    s.append("yards : "+getYards().size()+"\n");
    
    s.append("=======\n");
    s.append("CGTREES\n");
    TreesOfConnectedGraphs cgt=new TreesOfConnectedGraphs(this);
    for(ConnectedGraph cg:cgt.roots)
      s.append("\n"+cg.getTreeString());
    
    return s.toString();
    
  }
  
}
