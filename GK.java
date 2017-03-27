package org.fleen.geom_Kisrhombille;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.fleen.geom_2D.GD;

/*
 * KISRHOMBILLE GEOMETRY
 * 
 * General kisrhombille coordinate system constants and methods.
 * 
 * Directions are integers. We have 12, like a clock.
 * Vertex has 4 coordinates. TODO reduce to 3
 * 
 * 
 * 
 *            o V12
 *            |\
 *            | \
 *            |  \ 
 *            |   \ hawk
 *       goat |    \
 *            |     \
 *            |      \
 *            |       \
 *         V4 o--------o V6
 *               fish
 * 
 */
public class GK{

  public static final double SQRT3=Math.sqrt(3.0);
  
  public static final int AXISCOUNT=6;
  
  /*
   * ################################
   * BASIC DIAMOND CONSTANTS AND METRICS
   * ################################
   */
  
  public static final boolean 
    TWIST_POSITIVE=true,
    TWIST_NEGATIVE=false;
  
  public static enum Orbit{
    CLOCKWISE,
    COUNTERCLOCKWISE}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * VERTEX TYPES
   * type values corrospond to dog values
   * name index corrosponds to the number of segs that connect at a vertex of that type
   * eg : VERTEX12 has 12 connections, VERTEX4A has 4 connections, etc
   */
  public static final int 
    VERTEX_NULL=-1,
    VERTEX_12=0,
    VERTEX_4A=1,
    VERTEX_6A=2,
    VERTEX_4B=3,
    VERTEX_6B=4,
    VERTEX_4C=5;
  
  /*
   * ++++++++++++++++++++++++++++++++
   * VERTEX GENERAL TYPES
   * general type is 4,6 or 12
   * it is also the number of edges at the vertex
   */
  public static final int 
    VERTEX_GTYPE_4=0,
    VERTEX_GTYPE_6=1,
    VERTEX_GTYPE_12=2;
  
  /*
   * ++++++++++++++++++++++++++++++++
   * EDGE TYPES
   * "edge" as in a graph edge
   * that is, the connecting lines between vertices
   */
  public static final int 
    EDGE_NULL=-1,EDGE_FISH=0,EDGE_GOAT=1,EDGE_HAWK=2;
  
  /*
   * ++++++++++++++++++++++++++++++++
   * EDGE STANDARD LENGTH VALUES
   * these can be scaled however, of course
   */
  public static final double
    EDGESLV_FISH=1.0,
    EDGESLV_GOAT=Math.sqrt(3.0),
    EDGESLV_HAWK=2.0;
  
  /*
   * ++++++++++++++++++++++++++++++++
   * DIRECTIONS
   * For standard grid spin (spin==true) DIRECTION_0 is north and 
   * they are addressed clockwise.
   */
  
  public static final int 
    DIRECTION_NULL=-1,
    DIRECTION_0=0,
    DIRECTION_1=1,
    DIRECTION_2=2,
    DIRECTION_3=3,
    DIRECTION_4=4,
    DIRECTION_5=5,
    DIRECTION_6=6,
    DIRECTION_7=7,
    DIRECTION_8=8,
    DIRECTION_9=9,
    DIRECTION_10=10,
    DIRECTION_11=11;
  
  /*
   * ++++++++++++++++++++++++++++++++
   * 2D VALUES FOR OUR 12 DIRECTIONS
   */
  
  public static final double[] DIRECTION_2D={
    (0.0/12.0)*(GD.PI*2.0),
    (1.0/12.0)*(GD.PI*2.0),
    (2.0/12.0)*(GD.PI*2.0),
    (3.0/12.0)*(GD.PI*2.0),
    (4.0/12.0)*(GD.PI*2.0),
    (5.0/12.0)*(GD.PI*2.0),
    (6.0/12.0)*(GD.PI*2.0),
    (7.0/12.0)*(GD.PI*2.0),
    (8.0/12.0)*(GD.PI*2.0),
    (9.0/12.0)*(GD.PI*2.0),
    (10.0/12.0)*(GD.PI*2.0),
    (11.0/12.0)*(GD.PI*2.0)};
  
  /*
   * convert diamond direction to real 2d direciton
   */
  public static final double getDirection2D(int d){
    if(d<0||d>11)return -1;
    return DIRECTION_2D[d];}
  
  /*
   * Direction axis types
   */
  public static final boolean 
    DIRECTION_AXIS_HAWK=true,
    DIRECTION_AXIS_GOAT=false;
  
  /*
   * 
   */
  public static final boolean getAxisType(int d){
    return d%2==0;}
  
  public static final boolean directionAxisIsHawky(int d){
    return d%2==0;}
  
  public static final boolean directionAxisIsGoaty(int d){
    return d%2==1;}
  
  /*
   * normalize arbitrary integer value to range [0,11]
   */
  public static final int normalizeDirection(int d){
    d=d%12;
    if(d<0)d+=12;
    return d;}
  
  /*
   * ################################
   * VERTEX LIBERTIES
   * each vertex is connected to the diamond graph via 4, 6 or 12 directions.
   * We refer to each of directions as a "liberty".
   * herein we describe the set of liberties for each vertex type (indicated by the "dog" coordinate)  
   * ################################
   */
  
  public static final int[][] VERTEX_LIBERTIES={
    {0,1,2,3,4,5,6,7,8,9,10,11},//V12
    {2,5,8,11},//V4A
    {0,2,4,6,8,10},//V6A
    {1,4,7,10},//V4B
    {0,2,4,6,8,10},//V6B
    {0,3,6,9}};//V4C
  
  public static final int[] getLiberties(int vdog){
    return VERTEX_LIBERTIES[vdog];}
  
  /*
   * return true if a vertex of the specified type (vdog)
   * has liberty in the specified direction.
   * false otherwise.
   */
  public static final boolean hasLiberty(int vdog,int dir){
    for(int i=0;i<VERTEX_LIBERTIES[vdog].length;i++){
      if(VERTEX_LIBERTIES[vdog][i]==dir)
        return true;}
    return false;}
  
  /*
   * ################################
   * ADJACENT VERTEX
   * ################################
   */
  
  /*
   * returns null if the specified vertex has no such adjacent in the specified direction
   */
  public static final KPoint getVertex_Adjacent(KPoint v,int dir){
    int[] v1=new int[4];
    getVertex_Adjacent(
      v.coors[0],
      v.coors[1],
      v.coors[2],
      v.coors[3],
      dir,
      v1);
    if(v1[3]==VERTEX_NULL)return null;
    return new KPoint(v1);}
  
  /*
   * Given the specified vertex (v0a,v0b,v0c,v0d) and a direction (dir), return
   * the coordinates of the implied adjacent vertex in the v1 array 
   * if the specified direction indicates an invalid liberty for v0
   * then we return VERTEX_NULL in v1[3]
   */
  public static final void getVertex_Adjacent(int v0a,int v0b,int v0c,int v0d,int dir,int[] v1){
    switch(v0d){
    case VERTEX_12:
      switch(dir){
      case DIRECTION_0:
        v1[0]=v0a;
        v1[1]=v0b;
        v1[2]=v0c;
        v1[3]=2;
        return;
      case DIRECTION_1:
        v1[0]=v0a;
        v1[1]=v0b;
        v1[2]=v0c;
        v1[3]=3;
        return;
      case DIRECTION_2:
        v1[0]=v0a;
        v1[1]=v0b;
        v1[2]=v0c;
        v1[3]=4;
        return;
      case DIRECTION_3:
        v1[0]=v0a;
        v1[1]=v0b;
        v1[2]=v0c;
        v1[3]=5;
        return;
      case DIRECTION_4:
        v1[0]=v0a+1;
        v1[1]=v0b;
        v1[2]=v0c-1;
        v1[3]=2;
        return;
      case DIRECTION_5:
        v1[0]=v0a+1;
        v1[1]=v0b;
        v1[2]=v0c-1;
        v1[3]=1;
        return;
      case DIRECTION_6:
        v1[0]=v0a;
        v1[1]=v0b-1;
        v1[2]=v0c-1;
        v1[3]=4;
        return;
      case DIRECTION_7:
        v1[0]=v0a;
        v1[1]=v0b-1;
        v1[2]=v0c-1;
        v1[3]=3;
        return;
      case DIRECTION_8:
        v1[0]=v0a;
        v1[1]=v0b-1;
        v1[2]=v0c-1;
        v1[3]=2;
        return;
      case DIRECTION_9:
        v1[0]=v0a-1;
        v1[1]=v0b-1;
        v1[2]=v0c;
        v1[3]=5;
        return;
      case DIRECTION_10:
        v1[0]=v0a-1;
        v1[1]=v0b-1;
        v1[2]=v0c;
        v1[3]=4;
        return;
      case DIRECTION_11:
        v1[0]=v0a;
        v1[1]=v0b;
        v1[2]=v0c;
        v1[3]=1;
        return;
      default:
        v1[3]=VERTEX_NULL;
        return;}
    case VERTEX_4A:
      switch(dir){
      case DIRECTION_2:
        v1[0]=v0a;
        v1[1]=v0b;
        v1[2]=v0c;
        v1[3]=2;
        return;
      case DIRECTION_5:
        v1[0]=v0a;
        v1[1]=v0b;
        v1[2]=v0c;
        v1[3]=0;
        return;
      case DIRECTION_8:
        v1[0]=v0a-1;
        v1[1]=v0b-1;
        v1[2]=v0c;
        v1[3]=4;
        return;
      case DIRECTION_11:
        v1[0]=v0a-1;
        v1[1]=v0b;
        v1[2]=v0c+1;
        v1[3]=0;
        return;
      default:
        v1[3]=VERTEX_NULL;
        return;}
    case VERTEX_6A:
      switch(dir){
      case DIRECTION_0:
        v1[0]=v0a-1;
        v1[1]=v0b;
        v1[2]=v0c+1;
        v1[3]=5;
        return;
      case DIRECTION_2:
        v1[0]=v0a;
        v1[1]=v0b+1;
        v1[2]=v0c+1;
        v1[3]=0;
        return;
      case DIRECTION_4:
        v1[0]=v0a;
        v1[1]=v0b;
        v1[2]=v0c;
        v1[3]=3;
        return;
      case DIRECTION_6:
        v1[0]=v0a;
        v1[1]=v0b;
        v1[2]=v0c;
        v1[3]=0;
        return;
      case DIRECTION_8:
        v1[0]=v0a;
        v1[1]=v0b;
        v1[2]=v0c;
        v1[3]=1;
        return;
      case DIRECTION_10:
        v1[0]=v0a-1;
        v1[1]=v0b;
        v1[2]=v0c+1;
        v1[3]=0;
        return;
      default:
        v1[3]=VERTEX_NULL;
        return;}
    case VERTEX_4B:
      switch(dir){
      case DIRECTION_1:
        v1[0]=v0a;
        v1[1]=v0b+1;
        v1[2]=v0c+1;
        v1[3]=0;
        return;
      case DIRECTION_4:
        v1[0]=v0a;
        v1[1]=v0b;
        v1[2]=v0c;
        v1[3]=4;
        return;
      case DIRECTION_7:
        v1[0]=v0a;
        v1[1]=v0b;
        v1[2]=v0c;
        v1[3]=0;
        return;
      case DIRECTION_10:
        v1[0]=v0a;
        v1[1]=v0b;
        v1[2]=v0c;
        v1[3]=2;
        return;
      default:
        v1[3]=VERTEX_NULL;
        return;}
    case VERTEX_6B:
      switch(dir){
      case DIRECTION_0:
        v1[0]=v0a;
        v1[1]=v0b+1;
        v1[2]=v0c+1;
        v1[3]=0;
        return;
      case DIRECTION_2:
        v1[0]=v0a+1;
        v1[1]=v0b+1;
        v1[2]=v0c;
        v1[3]=1;
        return;
      case DIRECTION_4:
        v1[0]=v0a+1;
        v1[1]=v0b+1;
        v1[2]=v0c;
        v1[3]=0;
        return;
      case DIRECTION_6:
        v1[0]=v0a;
        v1[1]=v0b;
        v1[2]=v0c;
        v1[3]=5;
        return;
      case DIRECTION_8:
        v1[0]=v0a;
        v1[1]=v0b;
        v1[2]=v0c;
        v1[3]=0;
        return;
      case DIRECTION_10:
        v1[0]=v0a;
        v1[1]=v0b;
        v1[2]=v0c;
        v1[3]=3;
        return;
      default:
        v1[3]=VERTEX_NULL;
        return;}
    case VERTEX_4C:
      switch(dir){
      case DIRECTION_0:
        v1[0]=v0a;
        v1[1]=v0b;
        v1[2]=v0c;
        v1[3]=4;
        return;
      case DIRECTION_3:
        v1[0]=v0a+1;
        v1[1]=v0b+1;
        v1[2]=v0c;
        v1[3]=0;
        return;
      case DIRECTION_6:
        v1[0]=v0a+1;
        v1[1]=v0b;
        v1[2]=v0c-1;
        v1[3]=2;
        return;
      case DIRECTION_9:
        v1[0]=v0a;
        v1[1]=v0b;
        v1[2]=v0c;
        v1[3]=0;
        return;
      default:
        v1[3]=VERTEX_NULL;
        return;}
    default:
      v1[3]=VERTEX_NULL;
      return;}}
  
  /*
   * ################################
   * VERTEX EDGE TYPES BY DIRECTION
   * ################################
   */
  
  /*
   * Given a vertex type (vdog) and a direction (dir), get the edge type therein 
   */
  
  public static final int getEdgeType(int vdog,int dir){
    switch(vdog){
    case VERTEX_12:
      switch(dir){
      case DIRECTION_0:
        return EDGE_HAWK;
      case DIRECTION_1:
        return EDGE_GOAT;
      case DIRECTION_2:
        return EDGE_HAWK;
      case DIRECTION_3:
        return EDGE_GOAT;
      case DIRECTION_4:
        return EDGE_HAWK;
      case DIRECTION_5:
        return EDGE_GOAT;
      case DIRECTION_6:
        return EDGE_HAWK;
      case DIRECTION_7:
        return EDGE_GOAT;
      case DIRECTION_8:
        return EDGE_HAWK;
      case DIRECTION_9:
        return EDGE_GOAT;
      case DIRECTION_10:
        return EDGE_HAWK;
      case DIRECTION_11:
        return EDGE_GOAT;
      default:
        return EDGE_NULL;}
    case VERTEX_4A:
      switch(dir){
      case DIRECTION_2:
        return EDGE_FISH;
      case DIRECTION_5:
        return EDGE_GOAT;
      case DIRECTION_8:
        return EDGE_FISH;
      case DIRECTION_11:
        return EDGE_GOAT;
      default:
        return EDGE_NULL;}
    case VERTEX_6A:
      switch(dir){
      case DIRECTION_0:
        return EDGE_FISH;
      case DIRECTION_2:
        return EDGE_HAWK;
      case DIRECTION_4:
        return EDGE_FISH;
      case DIRECTION_6:
        return EDGE_HAWK;
      case DIRECTION_8:
        return EDGE_FISH;
      case DIRECTION_10:
        return EDGE_HAWK;
      default:
        return EDGE_NULL;}
    case VERTEX_4B:
      switch(dir){
      case DIRECTION_1:
        return EDGE_GOAT;
      case DIRECTION_4:
        return EDGE_FISH;
      case DIRECTION_7:
        return EDGE_GOAT;
      case DIRECTION_10:
        return EDGE_FISH;
      default:
        return EDGE_NULL;}
    case VERTEX_6B:
      switch(dir){
      case DIRECTION_0:
        return EDGE_HAWK;
      case DIRECTION_2:
        return EDGE_FISH;
      case DIRECTION_4:
        return EDGE_HAWK;
      case DIRECTION_6:
        return EDGE_FISH;
      case DIRECTION_8:
        return EDGE_HAWK;
      case DIRECTION_10:
        return EDGE_FISH;
      default:
        return EDGE_NULL;}
    case VERTEX_4C:
      switch(dir){
      case DIRECTION_0:
        return EDGE_FISH;
      case DIRECTION_3:
        return EDGE_GOAT;
      case DIRECTION_6:
        return EDGE_FISH;
      case DIRECTION_9:
        return EDGE_GOAT;
      default:
        return EDGE_NULL;}
    default:
      return EDGE_NULL;}}
  
  
  /*
   * ################################
   * VERTEX, VECTOR, DISTANCE, DIRECTION OPS
   * NOTE We don't have to be really optimal or efficient, so we arent. We brute it.  
   * We will be dealing with distances in the range of 1..20 at most. 
   * More likely maxing at 5. We do it the easy way. Prolly the faster way too.
   * Look at the old code for our old bloated complex universally applicable methods. 
   * ################################
   */
  
  /**
   * Given 2 directions, get the direction of d0 relative to d1
   * returns value in range [-5,5]
   * throws exception on direction reversal
   * TODO just return the -6?
   */
  public static final int getDirectionDelta(int d0,int d1){
    int delta;
    if(d0==d1){//same direction
      delta=0;
    }else{
      //pretend that d0 is 0
      int w=(d1+12-d0)%12;
      if(w<6){
        delta=w;
      }else if(w>6){
        delta=w-12;
      }else{
        throw new IllegalArgumentException("BAD GEOMETRY : direction reversal : d0="+d0+" d1="+d1);}}
    return delta;}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * GET DIRECTION VERTEX VERTEX
   * ++++++++++++++++++++++++++++++++
   */
  
  private static final double 
    GETDIRVV_ERROR=1.0/(65596.0*2.0*GD.PI),
    DIRECTION_2D_0_ALTERNATE=GD.PI*2.0;
  private static final double[][] GETDIRVV_RANGES={
    {DIRECTION_2D_0_ALTERNATE-GETDIRVV_ERROR,GETDIRVV_ERROR},
    {DIRECTION_2D[1]-GETDIRVV_ERROR,DIRECTION_2D[1]+GETDIRVV_ERROR},
    {DIRECTION_2D[2]-GETDIRVV_ERROR,DIRECTION_2D[2]+GETDIRVV_ERROR},
    {DIRECTION_2D[3]-GETDIRVV_ERROR,DIRECTION_2D[3]+GETDIRVV_ERROR},
    {DIRECTION_2D[4]-GETDIRVV_ERROR,DIRECTION_2D[4]+GETDIRVV_ERROR},
    {DIRECTION_2D[5]-GETDIRVV_ERROR,DIRECTION_2D[5]+GETDIRVV_ERROR},
    {DIRECTION_2D[6]-GETDIRVV_ERROR,DIRECTION_2D[6]+GETDIRVV_ERROR},
    {DIRECTION_2D[7]-GETDIRVV_ERROR,DIRECTION_2D[7]+GETDIRVV_ERROR},
    {DIRECTION_2D[8]-GETDIRVV_ERROR,DIRECTION_2D[8]+GETDIRVV_ERROR},
    {DIRECTION_2D[9]-GETDIRVV_ERROR,DIRECTION_2D[9]+GETDIRVV_ERROR},
    {DIRECTION_2D[10]-GETDIRVV_ERROR,DIRECTION_2D[10]+GETDIRVV_ERROR},
    {DIRECTION_2D[11]-GETDIRVV_ERROR,DIRECTION_2D[11]+GETDIRVV_ERROR}};
  
  /*
   * Given 2 vertices : v0,v1
   * get the direction from v0 to v1
   * If the direction is invalid because the 2 vertices are not colinar (coaxial)
   * (not one of our 12, within error) then we return DIRECTION_NULL
   */
  public static final int getDirection_VertexVertex(
    int v0a,int v0b,int v0c,int v0d,int v1a,int v1b,int v1c,int v1d){
    //get the direction 2dwise
    double[] p0=new double[2],p1=new double[2];
    getBasicPoint2D_Vertex(v0a,v0b,v0c,v0d,p0);
    getBasicPoint2D_Vertex(v1a,v1b,v1c,v1d,p1);
    double d2d=GD.getDirection_PointPoint(p0[0],p0[1],p1[0],p1[1]);
    double[] range;
    //filter the 2d direction value for gkis direction 0
    if(d2d>GETDIRVV_RANGES[0][0]||d2d<GETDIRVV_RANGES[0][1])
      return 0;
    //filter the 2d direction value for our other 11 gkis directions
    for(int i=1;i<12;i++){
      range=GETDIRVV_RANGES[i];
      if(d2d>range[0]&&d2d<range[1])
        return i;}
    return DIRECTION_NULL;}
  
  public static final int getDirection_VertexVertex(KPoint v0,KPoint v1){
    return getDirection_VertexVertex(
      v0.getAnt(),v0.getBat(),v0.getCat(),v0.getDog(),
      v1.getAnt(),v1.getBat(),v1.getCat(),v1.getDog());}
  
//  public static final void main(String[] a){
//    KVertex v0=new KVertex(-1,2,3,0),v1=new KVertex(-1,1,2,2);
//    int dir=getDirection_VertexVertex(v0,v1);
//    System.out.println("dir="+dir);
//  }
  
  /*
   * COLINEARITY TEST
   * If the direction from v0 to v1 is a valid one for that particular
   * pair of vertex types then v0 and v1 are colinear. 
   */
  public static final boolean getColinear_VertexVertex(
    int v0a,int v0b,int v0c,int v0d,int v1a,int v1b,int v1c,int v1d){
    int d=getDirection_VertexVertex(v0a,v0b,v0c,v0d,v1a,v1b,v1c,v1d);
    //if null direction then noncolinear
    if(d==DIRECTION_NULL)return false;
    //we have a valid direction, is it valid for this pair of vertex types?
    //that is, is it a shared liberty?
    return hasLiberty(v0d,d)&&hasLiberty(v1d,d);}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * GET DISTANCE VERTEX VERTEX
   * simply convert to basic 2d points and use 2d distance
   * ++++++++++++++++++++++++++++++++
   */
  
  public static final double getDistance_VertexVertex(
    KPoint v0,KPoint v1){
    return getDistance_VertexVertex(
      v0.getAnt(),v0.getBat(),v0.getCat(),v0.getDog(),
      v1.getAnt(),v1.getBat(),v1.getCat(),v1.getDog());}
  
  public static final double getDistance_VertexVertex(
    int v0a,int v0b,int v0c,int v0d,int v1a,int v1b,int v1c,int v1d){
    double[] p0=new double[2],p1=new double[2];
    getBasicPoint2D_Vertex(v0a,v0b,v0c,v0d,p0);
    getBasicPoint2D_Vertex(v1a,v1b,v1c,v1d,p1);
    return GD.getDistance_PointPoint(p0[0],p0[1],p1[0],p1[1]);}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * GET VECTOR FROM 2 VERTICES
   * ++++++++++++++++++++++++++++++++
   */
  
  public static final KVector getVector_VertexVertex(
    int v0a,int v0b,int v0c,int v0d,int v1a,int v1b,int v1c,int v1d){
    int dir=getDirection_VertexVertex(v0a,v0b,v0c,v0d,v1a,v1b,v1c,v1d);
    //if it's a null direction then bad vector
    if(dir==DIRECTION_NULL)return null;
    //if the direction is one where either of the vertices has no such liberty then bad vector
    if(!(hasLiberty(v0d,dir))&&(hasLiberty(v1d,dir)))return null;
    //get distance and that's that
    double dis=getDistance_VertexVertex(v0a,v0b,v0c,v0d,v1a,v1b,v1c,v1d);
    return new KVector(dir,dis);}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * GET VERTEX FROM VERTEX AND VECTOR
   * Crawl from the specified vertex one adjacent neighbor at a time in
   * the specified direction over the specified distance until distance is within error of zero
   * if it falls beneath negative error then fail.
   * ++++++++++++++++++++++++++++++++
   */
  
  private static final double 
    GETVERTEXVV_TRAVERSALERRORCEILING=1.0/65536.0,
    GETVERTEXVV_TRAVERSALERRORFLOOR=-GETVERTEXVV_TRAVERSALERRORCEILING; 
  
  public static final KPoint getVertex_VertexVector(KPoint v,KVector t){
    int[] v1=new int[4];
    getVertex_VertexVector(v.getAnt(),v.getBat(),v.getCat(),v.getDog(),t.direction,t.distance,v1);
    if(v1[3]==VERTEX_NULL)return null;
    KPoint vertex=new KPoint(v1);
    return vertex;}
  
  public static final int[] getVertex_VertexVector(int[] v0,int tdir,double tdis){
    int[] v1=new int[4];
    getVertex_VertexVector(v0[0],v0[1],v0[2],v0[3],tdir,tdis,v1);
    return v1;}
  
  /*
   * return the vertex in v1 as an int[4] of coordinates
   * return VERTEX_NULL at dog on fail
   */
  public static final void getVertex_VertexVector(
    int v0a,int v0b,int v0c,int v0d,int tdir,double tdis,int[] v1){
    double remaining=tdis;
    v1[0]=v0a;
    v1[1]=v0b;
    v1[2]=v0c;
    v1[3]=v0d;
    while(remaining>GETVERTEXVV_TRAVERSALERRORCEILING){
      remaining-=getAdjacentDistance(v1[3],tdir);
      if(remaining<GETVERTEXVV_TRAVERSALERRORFLOOR){
        v1[3]=VERTEX_NULL;
        return;}
      getVertex_Adjacent(v1[0],v1[1],v1[2],v1[3],tdir,v1);
      if(v1[3]==VERTEX_NULL){
        return;}}}
  
  /*
   * given the specified vertex type and direction, get the distance to the vertex adjacent to 
   * that vertex in that direction.
   * we DO NOT check that a vertex with the specified vdog does indeed have a liberty at the specified direction
   * so if the specified vertex type does not have a liberty at the specified direction then an ambiguous value
   * will be returned
   */
  public static final double getAdjacentDistance(int vdog,int dir){
    if(dir%2==1)return EDGESLV_GOAT;
    switch(vdog){
    case VERTEX_12:
      return EDGESLV_HAWK;
    case VERTEX_4A:
      return EDGESLV_FISH;
    case VERTEX_6A:
      if(dir%4==0){
        return EDGESLV_FISH;
      }else{
        return EDGESLV_HAWK;}
    case VERTEX_4B:
      return EDGESLV_FISH;
    case VERTEX_6B:
      if(dir%4==0){
        return EDGESLV_HAWK;
      }else{
        return EDGESLV_FISH;}
    case VERTEX_4C:
      return EDGESLV_FISH;
    default:
      throw new IllegalArgumentException("invalid value for vdog : "+vdog);}}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
   * TEST
   * &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
   * ++++++++++++++++++++++++++++++++
   */
  
  private static final int TEST_CYCLES_COUNT=10000;
  
  /*
   * get a random vertex v0
   * get a random vector t
   * derive v1 via sequential adjacent process
   * derive v1 via getVertex_VertexVector method
   * if they match then good
   * if they don't then fail
   * run it 10000 times or so
   * time it too
   */
  public static final void TEST_getVertex_VertexVector(){
    int[] v0,v1a,v1b,vector;
    int failurecount=0;
    for(int i=0;i<TEST_CYCLES_COUNT;i++){
      v0=getRandomVertex();
      vector=getRandomVector(v0);
      v1a=new int[4];
      v1b=new int[4];
      getVertex_VertexVector_AdjProcessForTest(v0[0],v0[1],v0[2],v0[3],vector[0],vector[1],v1a);
      getVertex_VertexVector(v0[0],v0[1],v0[2],v0[3],vector[0],vector[1],v1b);
      //our derived vertices can match in 2 ways :
      // 1) they both express failure (dog==VERTEX_NULL therefor invalid vertex, due to bad distance)
      // 2) all of the coordinates match 
      if(
        (v1a[3]==VERTEX_NULL&&v1b[3]==VERTEX_NULL)||  
        (v1a[0]==v1b[0]&&
        v1a[1]==v1b[1]&&
        v1a[2]==v1b[2]&&
        v1a[3]==v1b[3])){
        System.out.println("TEST INDEX : "+i+" : --- SUCCEESS ---");
      }else{
        failurecount++;
        System.out.println("TEST INDEX : "+i+" : ##>> FAIL <<##");
        System.out.println("v0 : "+v0[0]+","+v0[1]+","+v0[2]+","+v0[3]);
        System.out.println("vector dir : "+vector[0]);
        System.out.println("vector dis : "+vector[1]);
        System.out.println("v1a : "+v1a[0]+","+v1a[1]+","+v1a[2]+","+v1a[3]);
        System.out.println("v1b : "+v1b[0]+","+v1b[1]+","+v1b[2]+","+v1b[3]);
        System.out.println("#><##><##><##><##><##><##><##><#");}}
    //
    if(failurecount==0){
      System.out.println("^^^^^^^^^^^^^^");
      System.out.println("TEST SUCCEEDED");
      System.out.println("TEST CYCLES : "+TEST_CYCLES_COUNT);
      System.out.println("^^^^^^^^^^^^^^");
    }else{
      System.out.println("#><##><##><##><##><##><##><##><#");
      System.out.println("TEST FAILED");
      System.out.println("TEST CYCLES : "+TEST_CYCLES_COUNT);
      System.out.println("FAILURE COUNT : "+failurecount);
      System.out.println("#><##><##><##><##><##><##><##><#");}}
  
  /*
   * consider our vertex and vector
   * traverse adjacent vertices from v0 in direction vector.direction until total distance >= vector.distance
   * if 0 was skipped and total distance reached is > vector.distance then we have arrived in the middle of a hawk
   * edge. Therefor fail because distances must work out perfectly.  
   */
  private static final void getVertex_VertexVector_AdjProcessForTest(
    int v0a,int v0b,int v0c,int v0d,int tdir,int tdis,int[] v1){
    int distancetraversed=0,edgelength;
    v1[0]=v0a;
    v1[1]=v0b;
    v1[2]=v0c;
    v1[3]=v0d;
    while(distancetraversed<tdis){
      edgelength=(getEdgeType(v1[3],tdir)==EDGE_HAWK)?2:1;
      getVertex_Adjacent(v1[0],v1[1],v1[2],v1[3],tdir,v1);
      distancetraversed+=edgelength;}
    //if we have overrun our vector distance then the vertex is invalid
    if(distancetraversed>tdis){
      v1[3]=VERTEX_NULL;}}
  
  private static final int[] getRandomVertex(){
    Random r=new Random();
    int 
      a=r.nextInt(21)-10,
      b=r.nextInt(21)-10,
      c=b-a,
      d=r.nextInt(6);
    return new int[]{a,b,c,d};}
  
  private static final int[] getRandomVector(int[] v){
    Random r=new Random();
    int 
      dir=VERTEX_LIBERTIES[v[3]][r.nextInt(VERTEX_LIBERTIES[v[3]].length)],
      dis=r.nextInt(33)+1;
    return new int[]{dir,dis};}
  
  /*
   * ################################
   * POINT 2D FROM VERTEX
   * Given a basic diamond coordinate system (origin=(0,0), twist=true, foreward=0, fish=1),
   * translate the specified diamond vertex coordinates into 2d point coordinates
   * ################################
   */
  
  private static final double
    UINT_1=1.0,
    UINT_2=2.0,
    UINT_SQRT3=Math.sqrt(3.0),
    P2D_G=UINT_SQRT3/2.0,
    P2D_H=3.0/2.0;
  
  public static final double[] getBasicPoint2D_Vertex(int[] p){
    double[] p2=new double[2];
    getBasicPoint2D_Vertex(p[0],p[1],p[2],p[3],p2);
    return p2;}
  
  /*
   * return the 2d point assuming a standard diamond grid where 
   * foreward == 0/2PI, fish=1.0 and direction indexing is clockwise. 
   */
  public static final void getBasicPoint2D_Vertex(int ant,int bat,int cat,int dog,double[] p2d){
    //start with coordinates of the v12 at the center of the flower
    p2d[0]=(ant+bat)*UINT_SQRT3;
    p2d[1]=cat*(UINT_1+UINT_2);
    //the other 5 vertices are relative to the V12
    switch(dog){
    case 0: //V12
      break;
    case 1: //V4A
      p2d[0]-=P2D_G;
      p2d[1]+=P2D_H;
      break;
    case 2: //V6A
      p2d[1]+=UINT_2;
      break;
    case 3: //V4B
      p2d[0]+=P2D_G;
      p2d[1]+=P2D_H;
      break;
    case 4: //V6B
      p2d[0]+=UINT_SQRT3;
      p2d[1]+=UINT_1;
      break;
    case 5: //V4C
      p2d[0]+=UINT_SQRT3;
      break;
    default:throw new IllegalArgumentException("dog out of range [0,5]. dog=="+dog);}}
  
  /*
   * ################################
   * GET VERTEX TRANSITIONWISE
   * Use an integer transitions count instead of real distance
   * ################################
   */
  
  /*
   * ++++++++++++++++++++++++++++++++
   * get vertex via vertex, direction and transitions
   * ++++++++++++++++++++++++++++++++
   */
  
  //dog patterns by direction
  private static final int[] 
      DOGPATTERN0={0,2,5,4},
      DOGPATTERN1={0,3,0,3},
      DOGPATTERN2={0,4,1,2},
      DOGPATTERN3={0,5,0,5},
      DOGPATTERN4={0,2,3,4},
      DOGPATTERN5={0,1,0,1},
      DOGPATTERN6={0,4,5,2},
      DOGPATTERN7={0,3,0,3},
      DOGPATTERN8={0,2,1,4},
      DOGPATTERN9={0,5,0,5},
      DOGPATTERN10={0,4,3,2},
      DOGPATTERN11={0,1,0,1};
    
  /*
   * given a vertex (v0a,v0b,v0c,v0d) a direction (dir) and a transitions count (trans), return the 
   * implied vertex coordinates in v1.
   */
  public static final void getVertex_Transitionswise(
    int v0a,int v0b,int v0c,int v0d,int dir,int trans,int[] v1){
    //if transitions is 0 then we return the original vertex
    if(trans==0){
      v1[0]=v0a;
      v1[1]=v0b;
      v1[2]=v0c;
      v1[3]=v0d;}
    //
    switch(v0d){
    //++++++++++++++++
    case VERTEX_12:
      switch(dir){
      case 0:
        v1[0]=v0a-(trans+2)/4;
        v1[1]=v0b+trans/4;
        v1[2]=v0c+trans/2;
        v1[3]=DOGPATTERN0[trans%4];
        return;
      case 1:
        v1[0]=v0a;
        v1[1]=v0b+trans/2;
        v1[2]=v0c+trans/2;
        v1[3]=DOGPATTERN1[trans%4];
        return;
      case 2:
        v1[0]=v0a+(trans+2)/4;
        v1[1]=v0b+trans/2;
        v1[2]=v0c+trans/4;
        v1[3]=DOGPATTERN2[trans%4];
        return;
      case 3:
        v1[0]=v0a+trans/2;
        v1[1]=v0b+trans/2;
        v1[2]=v0c;
        v1[3]=DOGPATTERN3[trans%4];
        return;
      case 4:
        v1[0]=v0a+trans/2;
        if(trans%4==1)v1[0]++;
        v1[1]=v0b+trans/4;
        v1[2]=v0c-(trans+3)/4;
        v1[3]=DOGPATTERN4[trans%4];
        return;
      case 5:
        v1[0]=v0a+(trans+1)/2;
        v1[1]=v0b;
        v1[2]=v0c-(trans+1)/2;
        v1[3]=DOGPATTERN5[trans%4];
        return;
      case 6:
        v1[0]=v0a+(trans+1)/4;
        v1[1]=v0b-(trans+3)/4;
        v1[2]=v0c-(trans+1)/2;
        v1[3]=DOGPATTERN6[trans%4];
        return;
      case 7:
        v1[0]=v0a;
        v1[1]=v0b-(trans+1)/2;
        v1[2]=v0c-(trans+1)/2;
        v1[3]=DOGPATTERN7[trans%4];
        return;
      case 8:
        v1[0]=v0a-(trans+1)/4;
        v1[1]=v0b-(trans+1)/2;
        v1[2]=v0c-(trans+3)/4;
        v1[3]=DOGPATTERN8[trans%4];
        return;
      case 9:
        v1[0]=v0a-(trans+1)/2;
        v1[1]=v0b-(trans+1)/2;
        v1[2]=v0c;
        v1[3]=DOGPATTERN9[trans%4];
        return;
      case 10:
        v1[0]=v0a-trans/2;
        if(trans%4==1)v1[0]--;
        v1[1]=v0b-(trans+3)/4;
        v1[2]=v0c+trans/4;
        v1[3]=DOGPATTERN10[trans%4];
        return;
      case 11:
        v1[0]=v0a-trans/2;
        v1[1]=v0b;
        v1[2]=v0c+trans/2;
        v1[3]=DOGPATTERN11[trans%4];
        return;
      //INVALID DIRECTION
      default:
        return;}
      
    //++++++++++++++++
    case VERTEX_4A:
      switch(dir){
      case 2:
        v1[0]=v0a+trans/4;
        v1[1]=v0b+trans/2;
        v1[2]=v0c+(trans+2)/4;
        v1[3]=DOGPATTERN2[(trans+2)%4];
        return;
      case 5:
        v1[0]=v0a+trans/2;
        v1[1]=v0b;
        v1[2]=v0c-trans/2;
        v1[3]=DOGPATTERN5[(trans+1)%4];
        return;
      case 8:
        v1[0]=v0a-(trans+3)/4;
        v1[1]=v0b-(trans+1)/2;
        v1[2]=v0c-(trans+1)/4;
        v1[3]=DOGPATTERN8[(trans+2)%4];
        return;
      case 11:
        v1[0]=v0a-(trans+1)/2;
        v1[1]=v0b;
        v1[2]=v0c+(trans+1)/2;
        v1[3]=DOGPATTERN5[(trans+1)%4];
        return;
      //INVALID DIRECTION
      default:
        return;}
        
    //++++++++++++++++
    case VERTEX_6A:
      switch(dir){
      case 0:
        v1[0]=v0a-(trans+3)/4;
        v1[1]=v0b+(trans+1)/4;
        v1[2]=v0c+(trans+1)/2;
        v1[3]=DOGPATTERN0[(trans+1)%4];
        return;
      case 2:
        v1[0]=v0a+(trans+1)/4;
        v1[1]=v0b+(trans+1)/2;
        v1[2]=v0c+(trans+3)/4;
        v1[3]=DOGPATTERN2[(trans+3)%4];
        return;
      case 4:
        v1[0]=v0a+trans/2;
        if(trans%4==2)v1[0]--;
        v1[1]=v0b+(trans+1)/4;
        v1[2]=v0c-trans/4;
        v1[3]=DOGPATTERN4[(trans+1)%4];
        return;
      case 6:
        v1[0]=v0a+trans/4;
        v1[1]=v0b-(trans+2)/4;
        v1[2]=v0c-trans/2;
        v1[3]=DOGPATTERN6[(trans+3)%4];
        return;
      case 8:
        v1[0]=v0a-(trans+2)/4;
        v1[1]=v0b-trans/2;
        v1[2]=v0c-trans/4;
        v1[3]=DOGPATTERN8[(trans+1)%4];
        return;
      case 10:
        v1[0]=v0a-(trans+1)/2;
        if(trans%4==2)v1[0]--;
        v1[1]=v0b-(trans+2)/4;
        v1[2]=v0c+(trans+3)/4;
        v1[3]=DOGPATTERN10[(trans+3)%4];
        return;
      //INVALID DIRECTION
      default:
        return;}
      
    //++++++++++++++++
    case VERTEX_4B:
      switch(dir){
      case 1:
        v1[0]=v0a;
        v1[1]=v0b+(trans+1)/2;
        v1[2]=v0c+(trans+1)/2;
        v1[3]=DOGPATTERN1[(trans+1)%4];
        return;
      case 4:
        v1[0]=v0a+(trans+1)/2;
        if(trans%4==1)v1[0]--;
        v1[1]=v0b+(trans+2)/4;
        v1[2]=v0c-(trans+1)/4;
        v1[3]=DOGPATTERN4[(trans+2)%4];
        return;
      case 7:
        v1[0]=v0a;
        v1[1]=v0b-trans/2;
        v1[2]=v0c-trans/2;
        v1[3]=DOGPATTERN7[(trans+1)%4];
        return;
      case 10:
        v1[0]=v0a-trans/2;
        if(trans%4==3)v1[0]--;
        v1[1]=v0b-(trans+1)/4;
        v1[2]=v0c+(trans+2)/4;
        v1[3]=DOGPATTERN10[(trans+2)%4];
        return;
      //INVALID DIRECTION
      default:
        return;}
      
    //++++++++++++++++
    case VERTEX_6B:
      switch(dir){
      case 0:
        v1[0]=v0a-(trans+1)/4;
        v1[1]=v0b+(trans+3)/4;
        v1[2]=v0c+(trans+1)/2;
        v1[3]=DOGPATTERN0[(trans+3)%4];
        return;
      case 2:
        v1[0]=v0a+(trans+3)/4;
        v1[1]=v0b+(trans+1)/2;
        v1[2]=v0c+(trans+1)/4;
        v1[3]=DOGPATTERN2[(trans+1)%4];
        return;
      case 4:
        v1[0]=v0a+(trans+1)/2;
        if(trans%4==2)v1[0]++;
        v1[1]=v0b+(trans+3)/4;
        v1[2]=v0c-(trans+2)/4;
        v1[3]=DOGPATTERN4[(trans+3)%4];
        return;
      case 6:
        v1[0]=v0a+(trans+2)/4;
        v1[1]=v0b-trans/4;
        v1[2]=v0c-trans/2;
        v1[3]=DOGPATTERN6[(trans+1)%4];
        return;
      case 8:
        v1[0]=v0a-trans/4;
        v1[1]=v0b-trans/2;
        v1[2]=v0c-(trans+2)/4;
        v1[3]=DOGPATTERN8[(trans+3)%4];
        return;
      case 10:
        v1[0]=v0a-trans/2;
        if(trans%4==2)v1[0]++;
        v1[1]=v0b-trans/4;
        v1[2]=v0c+(trans+1)/4;
        v1[3]=DOGPATTERN10[(trans+1)%4];
        return;
      //INVALID DIRECTION
      default:
        return;}

    //++++++++++++++++
    case VERTEX_4C:
      switch(dir){
      case 0:
        v1[0]=v0a-trans/4;
        v1[1]=v0b+(trans+2)/4;
        v1[2]=v0c+trans/2;
        v1[3]=DOGPATTERN0[(trans+2)%4];
        return;
      case 3:
        v1[0]=v0a+(trans+1)/2;
        v1[1]=v0b+(trans+1)/2;
        v1[2]=v0c;
        v1[3]=DOGPATTERN3[(trans+1)%4];
        return;
      case 6:
        v1[0]=v0a+(trans+3)/4;
        v1[1]=v0b-(trans+1)/4;
        v1[2]=v0c-(trans+1)/2;
        v1[3]=DOGPATTERN6[(trans+2)%4];
        return;
      case 9:
        v1[0]=v0a-trans/2;
        v1[1]=v0b-trans/2;
        v1[2]=v0c;
        v1[3]=DOGPATTERN9[(trans+1)%4];
        return;
      //INVALID DIRECTION
      default:
        return;}
      
    //++++++++++++++++
    //INVALID VERTEX INDEX(?)
    default:
      return;}}
  
  public static final KPoint getVertex_Transitionswise(KPoint v0,int dir,int transitions){
    int[] a=new int[4];
    getVertex_Transitionswise(v0.getAnt(),v0.getBat(),v0.getCat(),v0.getDog(),dir,transitions,a);
    return new KPoint(a);}
  
  /*
   * ################################
   * CONVERT 2D COORDINATES TO GRID COORDINATES FOR STANDARD GRID
   * Given a point, get the closest vertex within the specified margin
   * It chops the space up into boxes and delivers us the closest point, excluding a small degenerate space.
   * It's fast.
   * we're working with a standard grid here: foreward=0, twist=clockwise, fish=1.0
   * If we're too far from any vertices then null is returned
   * ################################
   */
  
  private static final double 
    GRIDCELLBOXWIDTH=Math.sqrt(3.0),
    GRIDCELLBOXHEIGHT=3.0;
  
  public static final KPoint getStandardVertex(double[] p,double margin){
    return getStandardVertex(p[0],p[1],margin);}
  
  public static final KPoint getStandardVertex(double px,double py,double margin){
    //get the box coordinates
    int 
      bx=(int)Math.floor(px/GRIDCELLBOXWIDTH),
      by=(int)Math.floor(py/GRIDCELLBOXHEIGHT); 
    //get the intra-box coordinates
    int 
      ibx=(int)((px-(bx*GRIDCELLBOXWIDTH))/(GRIDCELLBOXWIDTH/4)),
      iby=(int)((py-(by*GRIDCELLBOXHEIGHT))/(GRIDCELLBOXHEIGHT/12));
    //we have 2 kinds of boxes
    //get the actual vertex coordinates
    int ka,kb,kc,kd;
    //GET COORDINATES FOR BOX TYPE A
    SEEK:if(Math.abs(bx%2)==Math.abs(by%2)){
      //get the key v12 vertex coors
      ka=(bx-by)/2;//beware those rounding integers. bx/2 - by/2 is not what we want
      kc=by;
      kb=ka+kc;
      //analyze position within box grid : (ibx,iby)
      if(ibx==0){//0 (see notes)
        if(iby==0||iby==1){
          kd=0;
          break SEEK;
        }else if(iby==6||iby==7||iby==8||iby==9){//4
          kd=2;
          break SEEK;
        }else if(iby==10||iby==11){//5
          ka=ka-1;
          kc=kc+1;
          kd=5;
          break SEEK;
        }else{//ambiguous
          return null;}
      }else if(ibx==1||ibx==2){
        if(iby==4||iby==5||iby==6||iby==7){//3
          kd=3;
          break SEEK;
        }else{//ambiguous
          return null;}
      }else{//ibx==3
        if(iby==0||iby==1){//1
          kd=5;
          break SEEK;
        }else if(iby==2||iby==3||iby==4||iby==5){//2
          kd=4;
          break SEEK;
        }else if(iby==10||iby==11){//6
          kb=kb+1;
          kc=kc+1;
          kd=0;
          break SEEK;
        }else{//ambiguous
          return null;}}
    //GET COORDINATES FOR BOX TYPE B
    }else{
      //get the key v12 vertex coors
      ka=(bx-by+1)/2;
      kc=by;
      kb=ka+kc;
      //analyze position within box grid : (ibx,iby)
      if(ibx==0){
        if(iby==0||iby==1){//1
          ka=ka-1;
          kb=kb-1;
          kd=5;
          break SEEK;
        }else if(iby==2||iby==3||iby==4||iby==5){//2
          ka=ka-1;
          kb=kb-1;
          kd=4;
          break SEEK;
        }else if(iby==10||iby==11){//6
          ka=ka-1;
          kc=kc+1;
          kd=0;
          break SEEK;
        }else{//ambiguous
          return null;}
      }else if(ibx==1||ibx==2){
        if(iby==4||iby==5||iby==6||iby==7){//3
          kd=1;
          break SEEK;
        }else{//ambiguous
          return null;}
      }else{//ibx==3
        if(iby==0||iby==1){//0
          kd=0;
          break SEEK;
        }else if(iby==6||iby==7||iby==8||iby==9){//4
          kd=2;
          break SEEK;
        }else if(iby==10||iby==11){//5
          ka=ka-1;
          kc=kc+1;
          kd=5;
          break SEEK;
        }else{//ambiguous
          return null;}}}
      //now we have our most likely suspect
      //are we close enough?
      KPoint v=new KPoint(ka,kb,kc,kd);
      double[] precisepoint=v.getBasicPointCoor();
      if(Math.abs(px-precisepoint[0])<margin&&Math.abs(py-precisepoint[1])<margin){
        return v;
      }else{
        return null;}}
  
  /*
   * ################################
   * ANGLES
   * ################################
   */
  
  /**
   * given 3 different vertices, v0,v1,v2
   * return the angle formed on the right
   * 
   *  v2  o
   *      |
   *      |
   *      |
   *  v1  o  <--this angle
   *       \
   *        \
   *  v0     o
   * 
   */
  public static final int getRightAngle(KPoint v0,KPoint v1,KPoint v2){
    int 
      d0=getDirection_VertexVertex(v0,v1),
      d1=getDirection_VertexVertex(v1,v2);
    int d=getDirectionDelta(d0,d1);
    return 6-d;}
  
  /**
   * given 3 different vertices, v0,v1,v2
   * return the angle formed on the left
   * 
   *                  o  v2
   *                  |
   *                  |
   *                  |
   *  this angle-->   o  v1
   *                 / 
   *                /    
   *               o  v0  
   * 
   */
  public static final int getLeftAngle(KPoint v0,KPoint v1,KPoint v2){
    int 
    d0=getDirection_VertexVertex(v0,v1),
    d1=getDirection_VertexVertex(v1,v2);
  int d=getDirectionDelta(d0,d1);
  return 6+d;}
  
  /**
   * Get the counterclockwise offset of d1 relative to d0
   * We assume that d0 and d1 have been normalized to range {0,11}
   * 
   *    o---------> d1
   *    |      .
   *    |    .
   *    |  .   offset angle
   *    |. 
   *    |
   *    V
   *    d0
   *  
   * @param d0 a direction
   * @param d1 another direction
   * @return the counterclockwise offset from d0 to d1
   */
//  public static final int getCCWOffset(int d0,int d1){
//    d0%=12;
//    d1%=12;
//    d0+=12;
//    d1+=12;
//    if(d1>d0)return 12-(d1-d0);
//    return d0-d1;}
  
  /*
   * returns true is direction d is "between right" of the directions d0 and d1
   * TODO TEST
   * 
   *     ^ d1
   *     | 
   *     | 
   *     |
   *     o ------> right
   *     |
   *     | 
   *     |
   *     V d0
   */
  public static final boolean isBetweenRight(int d0,int d1,int d){
    int 
      d0cw=getCWOffset(d,d0),
      d1ccw=getCCWOffset(d,d1);
    return (d0cw+d1ccw)<11;}
  
  /*
   * returns true is direction d is "between right" of the directions d0 and d1
   * TODO TEST
   * 
   *             ^ d1
   *             | 
   *             | 
   *  left       |
   *     <------ o
   *             |
   *             | 
   *             |
   *             V d0
   */
  public static final boolean isBetweenLeft(int d0,int d1,int d){
    int 
      d0ccw=getCCWOffset(d,d0),
      d1cw=getCWOffset(d,d1);
    return (d0ccw+d1cw)<11;}
  
  
  /*
   * 
   * returns the clockwise offset of direction d1 relative to direction d0
   * ex0 : the clockwise offset of 6 relative to 2 is 4
   * ex1 : the clockwise offset of 0 relative to 4 is 8
   * 
   * 
   *     d0
   *    ^
   *    | . .
   *    |     . + clockwise offset
   *    |     .
   *    |     V
   *    o-----------> d1
   * 
   */
  public static final int getCWOffset(int d0,int d1){
    if(d1>=d0)
      return d1-d0;
    else
      return 12-d0+d1;}
  
  public static final int getCCWOffset(int d0,int d1){
    int f=12-getCWOffset(d0,d1);
    if(f==12)f=0;
    return f;}
  
  //TEST
  public static final void main(String[] a){
    Random r=new Random();
    int a0,a1,cwoff;
    for(int i=0;i<10;i++){
      a0=r.nextInt(12);
      a1=r.nextInt(12);
      cwoff=getCCWOffset(a0,a1);
      System.out.println("a0="+a0+" : a1="+a1+" : cwoff="+cwoff);
      
    }}
  
  
  /*
   * ################################
   * METAGON ANCHOR CONGRUENT POLYGON PERMUTATIONS
   * Given a metagon and an anchor, we can derive a polygon
   * however, implicit are 0..n other anchors that would also deliver the same polygon geometry
   * The specific point location and index+ traversal direction may vary, but the same basic polygon will result.
   * for example :
   * 
   * These 3 polygons are congruent, yet they do the points differently 
   *  
   *  0        1    1        0    3        2
   *  o--------o    o--------o    o--------o
   *  |        |    |        |    |        |
   *  |        |    |        |    |        |
   *  |        |    |        |    |        |
   *  |        |    |        |    |        |
   *  o--------o    o--------o    o--------o
   *  3        2    2        3    0        1
   *  
   *  Some metagon anchor systems will have several permutations, some will have just 1
   * ################################
   */
  
  /*
   * Given a metagon and an anchor, producing polygon P.
   * return a list of all anchors that, with this metagon, also describe polygons congruent to P. 
   * This list includes the original parameter anchor.
   * These anchors are alternatives to the specified anchor. 
   * Other ways to generate the same basic polygon but with different orientation and twist.
   * 
   * Create a polygon from params metagon and anchor : porg
   * Get every pair of adjacent vertices in the polygon : va,vb
   * Create 4 anchors from each of those pairs. 
   *   That is, the vertices in foreward and reverse order, with positive and negative twist.
   *   ie : va,vb,pos; va,vb,neg; vb,va,pos; vb,va,neg
   * 
   * now we have a list of prospective anchors : pa
   * for every anchor in pa, create a polygon : pp
   * if pp is valid, and congruent to porg, then it passes the test. Add that anchor to the list
   */
  public static final List<KAnchor> getMetagonAnchorOptions(KMetagon metagon,KAnchor anchor){
    KPolygon sample=metagon.getPolygon(anchor),test;
    List<KAnchor> prospects=getProspectiveAnchorsForMAA(sample);
    List<KAnchor> validprospects=new ArrayList<KAnchor>();
    for(KAnchor a:prospects){
      test=metagon.getPolygon(a);
      if(test!=null&&test.isCongruent(sample))
        validprospects.add(a);}
    System.out.println("VALID PROSPECTS COUNT : "+validprospects.size());
    return validprospects;}
  
  private static final List<KAnchor> getProspectiveAnchorsForMAA(KPolygon p){
    List<KAnchor> anchors=new ArrayList<KAnchor>();
    int i0,i1,s=p.size();
    KPoint v0,v1;
    for(i0=0;i0<s;i0++){
      i1=i0+1;
      if(i1==s)i1=0;
      v0=p.get(i0);
      v1=p.get(i1);
      anchors.add(new KAnchor(v0,v1,true));
      anchors.add(new KAnchor(v0,v1,false));
      anchors.add(new KAnchor(v1,v0,true));
      anchors.add(new KAnchor(v1,v0,false));}
    return anchors;}
  
}
