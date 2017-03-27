package org.fleen.geom_Kisrhombille;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/*
 * A location, orientation, twist and scale independent way of describing a KPolygon. 
 * 
 * A sequence of kgrid vectors with relative distances and a base distance value.
 * 
 * It is a baseinterval and list of MetagonVectors.
 * 
 * A MetagonVector is 
 *    a direction delta : int in range [-5,5] 
 *    a proportional (to the base interval) distance : double in range (0,inf) 
 * 
 * baseinterval is the prospective kpolygon's distance(v0,v1) relative to the other involved intervals.
 * 
 * MetagonVector0 is 
 *   direction(v1,v2) relative to the kpolygon's direction(v0,v1)
 *   distance(v1,v2) relative to the baseinterval
 *    
 * MetagonVector1 is 
 *   direction(v2,v3) relative to the kpolygon's direction(v1,v2)
 *   distance(v2,v3) relative to the baseinterval
 * etc
 * 
 */
public class KMetagon implements Serializable{

  private static final long serialVersionUID=-4829434091754784682L;
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  //copy
  public KMetagon(KMetagon km){
    baseinterval=km.baseinterval;
    vectors=new KMetagonVector[km.vectors.length];
    for(int i=0;i<vectors.length;i++){
      vectors[i]=(KMetagonVector)km.vectors[i].clone();}}
  
  public KMetagon(double baseinterval,KMetagonVector[] vectors){
    this.baseinterval=baseinterval;
    this.vectors=vectors;
    normalizeBaseInterval();}
  
  public KMetagon(KPolygon polygon){
    this(polygon.toArray(new KPoint[polygon.size()]));}
  
  public KMetagon(List<KPoint> vertices){
    this(vertices.toArray(new KPoint[vertices.size()]));}
  
  public KMetagon(KPoint... vertices){
    baseinterval=vertices[0].getDistance(vertices[1]);
    int dir0=vertices[0].getDirection(vertices[1]),dir1;
    int vectorcount=vertices.length-2;
    double dis;
    vectors=new KMetagonVector[vectorcount];
    int delta;
    for(int i=0;i<vectorcount;i++){
      dir1=vertices[i+1].getDirection(vertices[i+2]);
      dis=vertices[i+1].getDistance(vertices[i+2]);
      delta=GK.getDirectionDelta(dir0,dir1);
      vectors[i]=new KMetagonVector(delta,dis/baseinterval);
      dir0=dir1;}
    normalizeBaseInterval();}
  
  /*
   * ################################
   * BASE INTERVAL NORMALIZATION
   * ################################
   */
  
  private static final int MAXTRANSITIONSFORBASEINTERVALNORMALIZATION=200;
  private static final KPoint ORIGIN=new KPoint(0,0,0,0);
  
  /*
   * This standardizes our base interval to its minimal form 
   * 
   * we do it by creating a standard minimal polygon form of this metagon.
   * That is : v0 is the origin, direction(v0,v1) is 0 and baseinterval is minimal
   * glean that minimal base interval.
   * 
   * Starting at 2, increment the base interval. Increment pattern is 2,1,1,2,2,1,1,2,2,1,1,2,2,1...etc
   * Keep doing this until we can successfully create a sequence of vertices for our minimal polygon.
   */
  private void normalizeBaseInterval(){
    int prospectivebaseinterval=0,transitioncount=0;
    boolean found=false;
    //
    while((!found)&&transitioncount<MAXTRANSITIONSFORBASEINTERVALNORMALIZATION){
      switch(transitioncount%4){
      case 0:
        prospectivebaseinterval+=2;
        break;
      case 1:
        prospectivebaseinterval+=1;
        break;
      case 2:
        prospectivebaseinterval+=1;
        break;
      case 3:
        prospectivebaseinterval+=2;
        break;}
      found=tryBaseInterval(prospectivebaseinterval);
      transitioncount++;}
    //
    if(found){
      baseinterval=prospectivebaseinterval;
    }else{
      throw new IllegalArgumentException("KMetagon.normalizeBaseInterval() failed : KMETAGON : "+this);}}
  
  private boolean tryBaseInterval(int prospectivebaseinterval){
    KPoint v=new KPoint(0,0,0,0);
    KVector e=new KVector(0,prospectivebaseinterval);
    v=v.getVertex_Vector(e);
    if(v==null)return false;
    for(int i=0;i<vectors.length;i++){
      e.direction=(e.direction+vectors[i].directiondelta+12)%12;
      e.distance=vectors[i].relativeinterval*prospectivebaseinterval;
      v=v.getVertex_Vector(e);
      if(v==null)return false;}
    return true;}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public double baseinterval;
  public KMetagonVector[] vectors;
  
  public boolean isClockwise(){
    int a=0;
    for(KMetagonVector v:vectors)
      a+=v.directiondelta;
    return a>0;}
  
  /*
   * reverse all direction deltas
   * left becomes right and right, left
   * The effect upon a derived polygon is that it gets mirrored over the line : v0,v1.
   */
  public void reverseDeltas(){
    for(KMetagonVector v:vectors)
      v.directiondelta=(v.directiondelta*-1);}
  
  /*
   * ################################
   * DERIVE KPOLYGON
   * ################################
   */
  
  private static final int DEFAULTFOREWARD=0;
  private static final double DEFAULTSCALE=1.0;
  
  /*
   * returns the default polygon for this metagon
   * TODO rename to "getDefaultPolygon"
   */
  public KPolygon getPolygon(){
    return getPolygon(ORIGIN,DEFAULTFOREWARD,DEFAULTSCALE);}
  
  //optionally flipped
  public KPolygon getPolygon(boolean twist){
    return getPolygon(ORIGIN,DEFAULTFOREWARD,DEFAULTSCALE,twist);}
  
  /*
   * returns polygon with specified v0 and init direction. 
   * Scale is default (1.0).
   */
  public KPolygon getPolygon(KPoint v0,int d0){
    return getPolygon(v0,d0,1.0);}
  
  /*
   * v0 is specified. 
   * v0 to v1 direction is dir(v0,v1)
   * scale is dis(v0,v1)/baseinterval
   * twist is default (true. that is, clockwise)
   */
  public KPolygon getPolygon(KPoint v0,KPoint v1){
    return getPolygon(v0,v1,true);}
  
  /*
   * we use this for deriving polygon from jigs
   * v0,v1 and twist are gotten from an anchor I suppose
   */
  public KPolygon getPolygon(KPoint v0,KPoint v1,boolean twist){
    int dir=v0.getDirection(v1);
    double scale=v0.getDistance(v1)/baseinterval;
    return getPolygon(v0,dir,scale,twist);}
  
  public KPolygon getPolygon(KAnchor anchor){
    return getPolygon(anchor.v0,anchor.v1,anchor.twist);}
  
  /*
   * Returns polygon with v0=origin, dir(v0,v1)=0 and the specified dis(v0,v1)
   * Returns null on fail.
   */
  public KPolygon getPolygon(double v0v1dis){
    double scale=v0v1dis/baseinterval;
    KPolygon p=getPolygon(ORIGIN,0,scale);
    return p;}
  
  /*
   * TODO does this work?
   * yes, integer scale
   */
  public KPolygon getScaledPolygon(int scale){
    KPolygon p=getPolygon(ORIGIN,0,scale);
    return p;}
  
  public KPolygon getPolygon(int scale,boolean twist){
    KPolygon p=getPolygon(ORIGIN,0,scale,twist);
    return p;}
  
  /*
   * returns a scaled kpolygon derived from this ghost and the specified v0, initial direction and scaling factor
   * returns null on fail
   */
  public KPolygon getPolygon(KPoint v0,int direction,double scale){
    KPolygon p=getPolygon(v0,direction,scale,true);
    return p;}
  
  public KPolygon getPolygon(KPoint v0,int direction,double scale,boolean twist){
    KPolygon p=new KPolygon();
    p.add(v0);
    KPoint vertex=v0;
    KVector vector=new KVector(direction,baseinterval*scale);
    vertex=vertex.getVertex_Vector(vector);
    p.add(vertex);
    for(int i=0;i<vectors.length;i++){
      if(twist){
        vector.direction=(vector.direction+vectors[i].directiondelta+12)%12;
      }else{
        vector.direction=(vector.direction-vectors[i].directiondelta+12)%12;}
      vector.distance=scale*vectors[i].relativeinterval*baseinterval;
      vertex=vertex.getVertex_Vector(vector);
      if(vertex==null)return null;//fail
      p.add(vertex);}
    return p;}
  
  /*
   * ################################
   * ANCHOR OPTIONS
   * 
   * These are permutations of polygon within the geometric domain of this metagon
   * Different ways to fit the polygon in this metagon. Each way described by an anchor.
   * 
   * 
   * Given a metagon, 1..n congruent polygons can be generated given variations of the same parameters
   * these congruent poygons "permutations" of the metagon at that polygon.
   * if, given polygon p0, we could also describe polygons p1 and p2 that are congruent to p0, using the same 
   * metagon, then we call p0, p1 and p2 a "permutation set" for that metagon. 
   * ################################
   */
  
  /*
   * Given a polygon (p) and a metagon (m)
   * Given our basic metagon-to-polygon function: p=m.getPolygon(v0,v1,twist)
   * Get the set of all permutations, gleaning the fitpolygon ( so fitpolygon=m.getPolygon(fitparams) )where 
   * fitpolygon is congruent to p, irrespective of rotation and direction 
   * 
   * Returns the list of all possible Permutations for this metagon and the specified polygon
   * Returns null if there is no way to fit this metagon to the specified polygon
   * 
   * Given every index in the polygon (0..s-1) : iv0
   * Get the rotated polygon starting at iv0: prot
   * get the first 2 vertices in polyforeward, Using that, get a 
   *     polygon from metagon.getPolygon(v0,v1,true): pmeta
   * compare prot and pmeta. If they're equal then we have a fit
   * do the same thing with the othe twist param : (v0,v1,false)
   * no reverse the polygon and do it all again
   * 
   * 
   */
  public List<KAnchor> getAnchorOptions(KPolygon polygon){
    int polygonsize=polygon.size();
    //test the vector and vertex counts
    if(polygonsize!=vectors.length+2)return null;
    List<KAnchor> anchors=new ArrayList<KAnchor>();
    KPoint v0,v1;
    KPolygon protated,pmeta;
    //get all possible anchors, keep the ones that work
    //do it for the polygon unreversed
    for(int i=0;i<polygonsize;i++){
      protated=(KPolygon)polygon.clone();
      Collections.rotate(protated,i);
      v0=protated.get(0);
      v1=protated.get(1);
      pmeta=getPolygon(v0,v1,true);
      if(pmeta!=null&&protated.equals(pmeta))anchors.add(new KAnchor(v0,v1,true));
      pmeta=getPolygon(v0,v1,false);
      if(pmeta!=null&&protated.equals(pmeta))anchors.add(new KAnchor(v0,v1,false));}
    //do it for the polygon reversed
    KPolygon preversed=(KPolygon)polygon.clone();
    Collections.reverse(preversed);
    for(int i=0;i<polygonsize;i++){
      protated=(KPolygon)preversed.clone();
      Collections.rotate(protated,i);
      v0=protated.get(0);
      v1=protated.get(1);
      pmeta=getPolygon(v0,v1,true);
      if(pmeta!=null&&protated.equals(pmeta))anchors.add(new KAnchor(v0,v1,true));
      pmeta=getPolygon(v0,v1,false);
      if(pmeta!=null&&protated.equals(pmeta))anchors.add(new KAnchor(v0,v1,false));}
    //
    if(anchors.isEmpty())return null;
    return anchors;}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public int hashCode(){
    return vectors.length*7919;}
  
  private static final double BASEINTERVALEQUALITYERROR=0.000001;
  
  public boolean equals(Object a){
    if(a.hashCode()!=hashCode())return false;
    KMetagon m=(KMetagon)a;
    if(!equals(m.baseinterval,baseinterval,BASEINTERVALEQUALITYERROR))return false;
    int s=vectors.length;
    if(m.vectors.length!=s)return false;
    for(int i=0;i<s;i++)
      if(!m.vectors[i].equals(vectors[i]))return false;
    return true;}
  
  private boolean equals(double a,double b,double error){
    if(a<b){
      return b-a<error;
    }else{
      return a-b<error;}}
  
  public String toString(){
    StringBuffer a=new StringBuffer();
    a.append("["+getClass().getSimpleName()+" ");
    a.append("baseinterval="+baseinterval+" ");
    a.append("vectors=[");
    for(int i=0;i<vectors.length-1;i++)
      a.append(vectors[i].toString()+" ");
    a.append(vectors[vectors.length-1].toString()+"]]");
    return a.toString();}
  
}
