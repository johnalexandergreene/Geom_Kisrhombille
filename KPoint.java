package org.fleen.geom_Kisrhombille;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.fleen.geom_2D.DPoint;



/*
 * 
 * A vertex in a KGrid
 * 
 */
public class KPoint implements Serializable{
  
  private static final long serialVersionUID=5215014207521492571L;

  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
 
  /*
   * (ant,bat,cat) for local t12 
   * ant,bat,cat range [MININT,MAXINT]
   * dog for point relative to local t12
   * range [0,5]
   */
  public KPoint(int ant,int bat,int cat,int dog){
    coors=new int[]{ant,bat,cat,dog};}
  
  public KPoint(int[] params){
    this.coors=params;}
  
  public KPoint(KPoint v){
    coors=new int[]{v.coors[0],v.coors[1],v.coors[2],v.coors[3]};}
  
  public KPoint(){
    coors=new int[4];}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public int[] coors;
  
  public void setCoordinates(int a,int b,int c,int d){
    coors[0]=a;
    coors[1]=b;
    coors[2]=c;
    coors[3]=d;}
  
  public int getAnt(){
    return coors[0];}
  
  public int getBat(){
    return coors[1];}
  
  public int getCat(){
    return coors[2];}
  
  public int getDog(){
    return coors[3];}
  
  public static final int getAnt(int bat,int cat){
    return bat-cat;}
  
  public static final int getBat(int ant,int cat){
    return ant+cat;}
  
  public static final int getCat(int ant,int bat){
    return bat-ant;}
  
  public int getGeneralType(){
    switch(coors[3]){
    case 0:return GK.VERTEX_GTYPE_12;
    case 1:return GK.VERTEX_GTYPE_4;
    case 2:return GK.VERTEX_GTYPE_6;
    case 3:return GK.VERTEX_GTYPE_4;
    case 4:return GK.VERTEX_GTYPE_6;
    case 5:return GK.VERTEX_GTYPE_4;
    default:
      throw new IllegalArgumentException("VERTEX TYPE INVALID");}}
  
  //returns the direction from this vertex to v
  //returns DIRECTION_NULL if this vertex and v are not colinear
  public int getDirection(KPoint v){
    return GK.getDirection_VertexVertex(this,v);}
  
  public double getDistance(KPoint v){
    return GK.getDistance_VertexVertex(
      coors[0],coors[1],coors[2],coors[3],
      v.coors[0],v.coors[1],v.coors[2],v.coors[3]);}
  
  public KPoint getVertex_Adjacent(int dir){
    return GK.getVertex_Adjacent(this,dir);}
  
  public KPoint getVertex_Transitionswise(int dir,int trans){
    int[] v1coor=new int[4];
    GK.getVertex_Transitionswise(
      coors[0],coors[1],coors[2],coors[3],dir,trans,v1coor);
    if(v1coor[3]==GK.DIRECTION_NULL)return null;
    return new KPoint(v1coor);}
  
  public KPoint getVertex_DirDis(int dir,double dis){
    int[] v1coor=new int[4];
    GK.getVertex_VertexVector(coors[0],coors[1],coors[2],coors[3],dir,dis,v1coor);
    if(v1coor[3]==GK.DIRECTION_NULL)return null;
    return new KPoint(v1coor);}
  
  public KPoint getVertex_Vector(KVector vector){
    int[] v1coor=new int[4];
    GK.getVertex_VertexVector(coors[0],coors[1],coors[2],coors[3],vector.direction,vector.distance,v1coor);
    if(v1coor[3]==GK.DIRECTION_NULL)return null;
    return new KPoint(v1coor);}
  
  /*
   * returns true if this vertex is colinear with the specified
   */
  public boolean isColinear(KPoint v){
    return GK.getColinear_VertexVertex(
      coors[0],coors[1],coors[2],coors[3],
      v.coors[0],v.coors[1],v.coors[2],v.coors[3]);}
  
  //returns point coor assuming a basic kgrid : foreward=0, fish=1.0, twist=clockwise
  public double[] getBasicPointCoor(){
    double[] c=new double[2];
    GK.getBasicPoint2D_Vertex(coors[0],coors[1],coors[2],coors[3],c);
    return c;}
  
  //returns point2d assuming a basic kgrid : foreward=0, fish=1.0, twist=clockwise
  public DPoint getBasicPoint2D(){
    return new DPoint(getBasicPointCoor());}
  
  /*
   * ################################
   * UTIL
   * ################################
   */
  
  /*
   * Given a v12, get the 12 vertices that that surround it
   * note that we we don't validate the vertex
   */
  public static final List<KPoint> getV12LocalGroup(KPoint v){
    List<KPoint> a=new ArrayList<KPoint>();
    a.add(new KPoint(v.coors[0],v.coors[1],v.coors[2],1));//0
    a.add(new KPoint(v.coors[0],v.coors[1],v.coors[2],2));//1
    a.add(new KPoint(v.coors[0],v.coors[1],v.coors[2],3));//2
    a.add(new KPoint(v.coors[0],v.coors[1],v.coors[2],4));//3
    a.add(new KPoint(v.coors[0],v.coors[1],v.coors[2],5));//4
    a.add(new KPoint(v.coors[0]+1,v.coors[1],v.coors[2]-1,2));//5
    a.add(new KPoint(v.coors[0]+1,v.coors[1],v.coors[2]-1,1));//6
    a.add(new KPoint(v.coors[0],v.coors[1]-1,v.coors[2]-1,4));//7
    a.add(new KPoint(v.coors[0],v.coors[1]-1,v.coors[2]-1,3));//8
    a.add(new KPoint(v.coors[0],v.coors[1]-1,v.coors[2]-1,2));//9
    a.add(new KPoint(v.coors[0]-1,v.coors[1]-1,v.coors[2],5));//10
    a.add(new KPoint(v.coors[0]-1,v.coors[1]-1,v.coors[2],4));//11 
    return a;}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public boolean equals(Object a){
    KPoint b=(KPoint)a;
    return 
      coors[0]==b.coors[0]&&
      coors[1]==b.coors[1]&&
      coors[2]==b.coors[2]&&
      coors[3]==b.coors[3];}
  
  public int hashCode(){
    int a=coors[0];
      a=(a*31)+coors[1];
      a=(a*37)+coors[2];
      a=(a*41)+coors[3];
    return a;}
  
  public String toString(){
    String s="["+coors[0]+","+coors[1]+","+coors[2]+","+coors[3]+"]";
    return s;}
  
  public Object clone(){
    return new KPoint(coors[0],coors[1],coors[2],coors[3]);}
  
}