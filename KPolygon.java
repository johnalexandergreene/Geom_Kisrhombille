package org.fleen.geom_Kisrhombille;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.fleen.geom_2D.DPolygon;


public class KPolygon extends ArrayList<KPoint> implements Serializable{

  private static final long serialVersionUID=-7629682211684455666L;
  
  /*
   * ################################
   * INIT
   * ################################
   */
  
  public KPolygon(){}
  
  public KPolygon(int size){
    super.size();}
  
  public KPolygon(List<KPoint> v){
    super(v);}
  
  public KPolygon(KPoint... v){
    this(Arrays.asList(v));}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  /*
   * derive a kmetagon
   */
  public KMetagon getKMetagon(){
    return new KMetagon(this);}
  
  /*
   * reverse vertex order in this list
   * preserve v0
   * fail if this polygon has fewer than 3 vertices
   */
  public void reverse(){
    int s=size();
    if(s<3)return;
    List<KPoint> a=new ArrayList<KPoint>(size());
    int vindex=0;
    for(int i=0;i<s;i++){
      a.add(get(vindex));
      vindex--;
      if(vindex==-1)vindex=s-1;}
    clear();
    addAll(a);}
  
  public void rotate(int i){
    Collections.rotate(this,i);}
  
  //TODO this could probably be optimized
  public void removeRedundantColinearVertices(){
    List<KPoint> rcv=new ArrayList<KPoint>();
    int s=size(),iprev,inext;
    KPoint v,vprev,vnext;
    for(int i=0;i<s;i++){
      iprev=i-1;
      if(iprev==-1)iprev=s-1;
      inext=i+1;
      if(inext==s)inext=0;
      v=get(i);
      vprev=get(iprev);
      vnext=get(inext);
      if(vprev.getDirection(v)==vprev.getDirection(vnext))
        rcv.add(v);}
//    System.out.println("removed RCVs:"+rcv.size());
    removeAll(rcv);}
  
  //get polygon2D assuming the default grid: fish=1, foreward=0, twist=true
  public DPolygon getDefaultPolygon2D(){
    int s=size();
    DPolygon p=new DPolygon(s);
    for(int i=0;i<s;i++)
      p.add(get(i).getBasicPoint2D());
    return p;}
  
  public boolean getTwist(){
    return getDefaultPolygon2D().getTwist();}
  
  /*
   * Returns true if the specified KPolygon is Location Orientation Twist Isomorphic to this one.
   * That is, compare the metagons
   * if the metagons are equal or mirror (opposite twist) then we have an LOT Isomorph.
   * We use this for assigning chorus indices in a jig
   * if 2 polygons are LOT Isomorphic then they MAY get the same chorus index. We have the option. 
   */
  public boolean isLOTIsomorphic(KPolygon kpolygon){
    KMetagon m0=getKMetagon(),m1=kpolygon.getKMetagon();
    if(m0.equals(m1))return true;
    m0.reverseDeltas();
    if(m0.equals(m1))return true;
    return false;}
  
  /*
   * returns the fully reticulated form of this polygon
   * That is, all traversed vertices are represented. 
   *   Not just the corners, the vertices in-between the corners too. If any. 
   */
  public KPolygon getReticulation(){
    List<KPoint> vertices=new ArrayList<KPoint>();
    int s=size(),i0,i1;
    KPoint c0,c1,v;
    int d;
    for(i0=0;i0<s;i0++){
      i1=i0+1;
      if(i1==s)i1=0;
      c0=get(i0);
      c1=get(i1);
      d=c0.getDirection(c1);
      v=c0;
      while(!v.equals(c1)){
        vertices.add(v);
        v=v.getVertex_Adjacent(d);}}
    return new KPolygon(vertices);}
  
  /*
   * returns true if the specified shares at least one vertex with this
   * returns false otherwise
   * consider using this method with a reticulated polygon
   */
  public boolean touch(KPolygon p){
    for(KPoint v:p)
      if(contains(v))return true;
    return false;}
  
  /*
   * Returns all vertices shared by the specified and this in a list
   * Returns empty list if there are no shared vertices.
   */
  public List<KPoint> getSharedVertices(KPolygon p){
    List<KPoint> s=new ArrayList<KPoint>();
    for(KPoint v:p)
      if(contains(v))s.add(v);
    return s;}
  
  /*
   * returns true if the specified polygon is congruent to this polygon
   * By congruent we mean that they have the same number of vertices, all vertices are duplicated and all vertex neighborhoods are duplicated
   * Just the vertex order and/or traversal direction (cw vs ccw) are altered.
   * TODO test this
   */
  public boolean isCongruent(KPolygon p0){
    int s=p0.size();
    if(s!=size())return false;
    if(!p0.containsAll(this))return false;
    //get a starting vertex for both polygons (p0 is the param, p1 is this)
    int ip0=0,ip1=indexOf(p0.get(0));
    //get the direction of the indices in p1 relative to p0
    int ip1maybenext=ip1+1;
    if(ip1maybenext==s)ip1maybenext=0;
    KPoint v=p0.get(1);
    int incrementp1=-1;//index-
    if(indexOf(v)==ip1maybenext)incrementp1=1;
    for(ip0=0;ip0<s;ip0++){
      if(!p0.get(ip0).equals(get(ip1)))
        return false;
      ip1+=incrementp1;
      if(ip1==s)ip1=0;
      if(ip1==-1)ip1=s-1;}
    return true;}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public boolean equals(Object a){
    KPolygon p=(KPolygon)a;
    int s=size();
    if(p.size()!=s)return false;
    for(int i=0;i<s;i++)
      if(!get(i).equals(p.get(i)))return false;
    return true;}
  
  public Object clone(){
    KPolygon p=new KPolygon(size());
    for(KPoint q:this)
      p.add((KPoint)q.clone());
    return p;}
  
  public String toString(){
    if(isEmpty())return"P[]";
    KPoint v;
    StringBuffer a=new StringBuffer();
    v=get(0);
    a.append("P["+v);
    if(size()>1){
      for(int i=1;i<size();i++){
        v=get(i);
        a.append(","+v);}
      a.append("]");}
    return a.toString();}
  
}