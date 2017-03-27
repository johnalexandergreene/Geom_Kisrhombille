package org.fleen.geom_Kisrhombille;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;

/*
 * ordered : (v4,v6),(v6,v12) or (v12,v4)
 */
public class KSeg{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public KSeg(KPoint a,KPoint b){
    //put the vertices in the proper order
    int gta=a.getGeneralType(),gtb=b.getGeneralType();
    if(gta==GK.VERTEX_GTYPE_4){
      if(gtb==GK.VERTEX_GTYPE_6){
        vertex0=a;
        vertex1=b;
      }else{//gtb==MathDiamond.VERTEX_GTYPE_12
        vertex0=b;
        vertex1=a;}
    }else if(gta==GK.VERTEX_GTYPE_6){
      if(gtb==GK.VERTEX_GTYPE_12){
        vertex0=a;
        vertex1=b;
      }else{//gtb==MathDiamond.VERTEX_GTYPE_4
        vertex0=b;
        vertex1=a;}
    }else{//gta==MathDiamond.VERTEX_GTYPE_12
      if(gtb==GK.VERTEX_GTYPE_4){
        vertex0=a;
        vertex1=b;
      }else{//gtb==MathDiamond.VERTEX_GTYPE_6
        vertex0=b;
        vertex1=a;}}}
  
  /*
   * ################################
   * VERTICES
   * ################################
   */
  
  private KPoint vertex0,vertex1;
  
  public KPoint getVertex0(){
    return vertex0;}
  
  public KPoint getVertex1(){
    return vertex1;}
  
  /*
   * ################################
   * GEOM
   * ################################
   */
  
  public boolean intersects(KSeg s){
    DPoint 
      s0p0=vertex0.getBasicPoint2D(),
      s0p1=vertex1.getBasicPoint2D(),
      s1p0=s.vertex0.getBasicPoint2D(),
      s1p1=s.vertex1.getBasicPoint2D();
    boolean i=GD.getIntersection_SegSeg(s0p0.x,s0p0.y,s0p1.x,s0p1.y,s1p0.x,s1p0.y,s1p1.x,s1p1.y)!=null;
    return i;}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  private Integer hashcode=null;
  
  public int hashCode(){
    if(hashcode==null){
      hashcode=
        vertex0.coors[0]*65536+
        vertex0.coors[1]*32668+
        vertex0.coors[2]*16384+
        vertex0.coors[3]*16+
        vertex1.coors[0]*8192+
        vertex1.coors[1]*4096+
        vertex1.coors[2]*2048+
        vertex1.coors[3];}
    return hashcode;}
  
  public boolean equals(Object a){
    KSeg equals_a=(KSeg)a;
    if(equals_a.hashCode()==hashCode()){
      return 
        equals_a.vertex0.coors[0]==vertex0.coors[0]&&
        equals_a.vertex0.coors[1]==vertex0.coors[1]&&
        equals_a.vertex0.coors[2]==vertex0.coors[2]&&
        equals_a.vertex0.coors[3]==vertex0.coors[3]&&
        equals_a.vertex1.coors[0]==vertex1.coors[0]&&
        equals_a.vertex1.coors[1]==vertex1.coors[1]&&
        equals_a.vertex1.coors[2]==vertex1.coors[2]&&
        equals_a.vertex1.coors[3]==vertex1.coors[3];
    }else{
      return false;}}
  
  public String toString(){
    return "["+vertex0+","+vertex1+"]";}
  
}
