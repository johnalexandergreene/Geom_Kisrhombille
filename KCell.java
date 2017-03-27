package org.fleen.geom_Kisrhombille;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import org.fleen.geom_2D.GD;

/*
 * A cell in the diamond system
 * we use this in a few rare situations
 * it has 3 vertices : v12, v6 ,v4
 * we define it by it's v12 coordinates (ant,bat,cat) and an index (dog)
 * it has 3 faces/segs. we id them by index in order of short to long
 * 
 *             v12
 *             o  
 *            /|
 *           / |
 *          /  |
 *     f2  /   | f1
 *        /    | 
 *       /     |
 *      /      |
 *     o-------o v4    
 *   v6    f0
 *      
 *         o v12
 *         |\
 *         | \
 *         |  \
 *      f1 |   \ f2
 *         |    \ 
 *         |     \
 *         |      \
 *         o-------o v6
 *        v4   f0
 *        
 */
public class KCell{
  
  /*
   * ################################
   * FIELDS
   * ################################
   */
  
  public static final int 
    SEG_FISH=0,//short
    SEG_GOAT=1,//medium
    SEG_HAWK=2;//long
  
  public int ant,bat,cat,dog;
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public KCell(int ant,int bat,int cat,int dog){
    this.ant=ant;
    this.bat=bat;
    this.cat=cat;
    this.dog=dog;}
  
  /*
   * ################################
   * DVERTEX
   * ################################
   */
  
  //coordinates for the other 2 vertices (v6 and v4, respectively) in a cell in terms of it's v12
  //consider our array params : [p0][p1]p2]
  //p0 is the dog param for the resulting cell
  //p1 is the coors of the v6 relative to the v12
  //p2 is the coors of the v4
  private static final int[][][] DVERTEX_OFFSETS={
    {{0,0,0,2},{0,0,0,3}},    //dog==0
    {{0,0,0,4},{0,0,0,3}},    //dog==1
    {{0,0,0,4},{0,0,0,5}},    //dog==2
    {{1,0,-1,2},{0,0,0,5}},   //dog==3
    {{1,0,-1,2},{1,0,-1,1}},  //dog==4
    {{0,-1,-1,4},{1,0,-1,1}}, //dog==5
    {{0,-1,-1,4},{0,-1,-1,3}},//dog==6
    {{0,-1,-1,2},{0,-1,-1,3}},//dog==7
    {{0,-1,-1,2},{-1,-1,0,5}},//dog==8
    {{-1,-1,0,4},{-1,-1,0,5}},//dog==9
    {{-1,-1,0,4},{0,0,0,1}},  //dog==10
    {{0,0,0,2},{0,0,0,1}}};   //dog==11
  
  /*
   * returns this cells 3 vertices in order : v12,v6,v4
   */
  public KPoint[] getVertices(){
    KPoint p0=new KPoint(ant,bat,cat,0);
    KPoint p1=new KPoint(
      ant+DVERTEX_OFFSETS[dog][0][0],
      bat+DVERTEX_OFFSETS[dog][0][1],
      cat+DVERTEX_OFFSETS[dog][0][2],
      DVERTEX_OFFSETS[dog][0][3]);
    KPoint p2=new KPoint(
      ant+DVERTEX_OFFSETS[dog][1][0],
      bat+DVERTEX_OFFSETS[dog][1][1],
      cat+DVERTEX_OFFSETS[dog][1][2],
      DVERTEX_OFFSETS[dog][1][3]);
    return new KPoint[]{p0,p1,p2};}
  
  /*
   * ################################
   * ADJACENT CELLS
   * ################################
   */
  
  /*
   * return the cell adjacent to this one, specified by face index
   * fish=0
   * goat=1
   * hawk=2
   */
  public KCell getAdjacent(int face){
    //normalize
    face%=3;
    if(face<0)face+=3;
    //12 cases for dog, 3 cases for face
    switch(dog){
    case 0:
      switch(face){
      case 0:
        return new KCell(ant,bat+1,cat+1,7);
      case 1:
        return new KCell(ant,bat,cat,1);
      case 2:
        return new KCell(ant,bat,cat,11);}
    case 1:
      switch(face){
      case 0:
        return new KCell(ant,bat+1,cat+1,6);
      case 1:
        return new KCell(ant,bat,cat,0);
      case 2:
        return new KCell(ant,bat,cat,2);}
    case 2:
      switch(face){
      case 0:
        return new KCell(ant+1,bat+1,cat,9);
      case 1:
        return new KCell(ant,bat,cat,3);
      case 2:
        return new KCell(ant,bat,cat,1);}
    case 3:
      switch(face){
      case 0:
        return new KCell(ant+1,bat+1,cat,8);
      case 1:
        return new KCell(ant,bat,cat,2); 
      case 2:
        return new KCell(ant,bat,cat,4);}
    case 4:
      switch(face){
      case 0:
        return new KCell(ant+1,bat,cat-1,11);
      case 1:
        return new KCell(ant,bat,cat,5);
      case 2:
        return new KCell(ant,bat,cat,3);}
    case 5:
      switch(face){
      case 0:
        return new KCell(ant+1,bat,cat-1,10); 
      case 1:
        return new KCell(ant,bat,cat,4);
      case 2:
        return new KCell(ant,bat,cat,6);}
    case 6:
      switch(face){
      case 0:
        return new KCell(ant,bat-1,cat-1,1);
      case 1:
        return new KCell(ant,bat,cat,7); 
      case 2:
        return new KCell(ant,bat,cat,5);}
    case 7:
      switch(face){
      case 0:
        return new KCell(ant,bat-1,cat-1,0);
      case 1:
        return new KCell(ant,bat,cat,6); 
      case 2:
        return new KCell(ant,bat,cat,8);}
    case 8:
      switch(face){
      case 0:
        return new KCell(ant-1,bat-1,cat,3);
      case 1:
        return new KCell(ant,bat,cat,9); 
      case 2:
        return new KCell(ant,bat,cat,7);}
    case 9:
      switch(face){
      case 0:
        return new KCell(ant-1,bat-1,cat,2);
      case 1:
        return new KCell(ant,bat,cat,8); 
      case 2:
        return new KCell(ant,bat,cat,10);}
    case 10:
      switch(face){
      case 0:
        return new KCell(ant-1,bat,cat+1,5);
      case 1:
        return new KCell(ant,bat,cat,11); 
      case 2:
        return new KCell(ant,bat,cat,9);}
    case 11:
      switch(face){
      case 0:
        return new KCell(ant-1,bat,cat+1,4);
      case 1:
        return new KCell(ant,bat,cat,10); 
      case 2:
        return new KCell(ant,bat,cat,0);}
    default:
      throw new IllegalArgumentException("foo");}}
  
  /*
   * returns the 3 cells adjacent to this one in standard face order 
   */
  public KCell[] getAdjacents(){
    KCell[] a={getAdjacent(0),getAdjacent(1),getAdjacent(2)};
    return a;}
  
  /*
   * ################################
   * GEOMETRY 2D
   * ################################
   */
  
  public Path2D.Double getPath2D(){
    KPoint[] v=getVertices();
    double[] p0=v[0].getBasicPointCoor(),p1=v[1].getBasicPointCoor(),p2=v[2].getBasicPointCoor();
    Path2D.Double path=new Path2D.Double();
    path.moveTo(p0[0],p0[1]);
    path.lineTo(p1[0],p1[1]);
    path.lineTo(p2[0],p2[1]);
    path.closePath();
    return path;}
  
  /*
   * ################################
   * CELL CONTAINS 2D POINT
   * ################################
   */
  
  public boolean contains(double x,double y){
    KCell c=getCell(x,y);
    return c.equals(this);}
  
  /*
   * returns the cell that contains the specified 2d point
   * 
   * consider our diamond plane as a grid of minimal rectangles.
   * each rectangle is composed of 6 cells
   * 
   * these rectangles come in 2 flavors, 2 different arrangements of cell-triangles
   * get the rectangle, get the flavor, get the vertex dpoints for the 6 triangles (7 dpoints total) 
   * test the 2 larger triangles for containment
   * test triangles within the one of those 2 larger triangles
   * etc 
   * 
   * TODO move this into DMath or something
   * 
   */
  public static final KCell getCell(double x,double y){
    //get min rect cartesian grid coordinates for lower-left corner dpoint of selected min rect
    int mrx=(int)Math.floor(x/GD.SQRT3);
    int mry=(int)Math.floor(y/(3));
    //get cell by rectangle type
    //if px and py are identically even/odd then we use the lower-left corner dpoint of the
    //rectangle as reference. If they aren't then we use the lower right.
    KCell cell;
    int mrxc=mrx%2,mryc=mry%2;
    //MIN RECT TYPE A if they're both odd or both even
    if((mrxc==0&&mryc==0)||(mrxc!=0&&mryc!=0)){
      int ant=(mrx-mry)/2;
      int cat=mry;
      int bat=KPoint.getBat(ant,cat);
      KPoint dpr=new KPoint(ant,bat,cat,0);
      cell=getCell_RectA(x,y,dpr);
    //MIN RECT TYPE B if one is odd and the other is even
    }else{//px%2!=py%2
      int ant=(mrx-mry+1)/2;
      int cat=mry;
      int bat=KPoint.getBat(ant,cat);
      KPoint dpr=new KPoint(ant,bat,cat,0);
      cell=getCell_RectB(x,y,dpr);}
    return cell;}
  
  /*
   * get cell in rectangle type A specified by lower-left corner point dpr that contains the point (px,py)    
   * TODO use DGeom interval constants instead of literal values       
   */
  private static final KCell getCell_RectA(double px,double py,KPoint dpr){
    KCell cell;
    double[] pr=dpr.getBasicPointCoor();
    //get our 7 test points
    double 
      p0x=pr[0],p0y=pr[1]+2.0,
      p1x=pr[0],p1y=pr[1]+3.0,
      p2x=pr[0]+GD.SQRT3,p2y=pr[1]+3.0,
      p3x=pr[0]+GD.SQRT3/2.0,p3y=pr[1]+3.0/2.0,
      p4x=pr[0]+GD.SQRT3,p4y=pr[1]+1.0;
//      p5x=pr[0]+FM.SQRT3,p5y=pr[1];
    //
    //test (pr,p1,p2)
    if(triangleContainsPoint(pr[0],pr[1],p1x,p1y,p2x,p2y,px,py)){
      //test (pr,p0,p2)
      if(triangleContainsPoint(pr[0],pr[1],p0x,p0y,p2x,p2y,px,py)){
        //test (pr,p0,p3)
        if(triangleContainsPoint(pr[0],pr[1],p0x,p0y,p3x,p3y,px,py)){
          cell=new KCell(dpr.coors[0],dpr.coors[1],dpr.coors[2],0);
        //not contained in (pr,p0,p3), therefor contained in (p0,p2,p3)
        }else{
          cell=new KCell(dpr.coors[0],dpr.coors[1]+1,dpr.coors[2]+1,7);}
      //not contained in (pr,p0,p2) therefor it's contained in (p0,p1,p2)
      }else{
        cell=new KCell(dpr.coors[0],dpr.coors[1]+1,dpr.coors[2]+1,8);}
    //not contained in (pr,p1,p2) therefor it's contained in (pr,p2,p5)
    }else{
      //test (pr,p2,p4)
      if(triangleContainsPoint(pr[0],pr[1],p2x,p2y,p4x,p4y,px,py)){
        //test (pr,p3,p4)
        if(triangleContainsPoint(pr[0],pr[1],p3x,p3y,p4x,p4y,px,py)){
          cell=new KCell(dpr.coors[0],dpr.coors[1],dpr.coors[2],1);
        //not contained in (pr,p3,p4) therefor contained in (p3,p2,p4)
        }else{
          cell=new KCell(dpr.coors[0],dpr.coors[1]+1,dpr.coors[2]+1,6);}
      //not contained in (pr,p2,p4) therefor contained in (pr,p4,p5) 
      }else{
        cell=new KCell(dpr.coors[0],dpr.coors[1],dpr.coors[2],2);}}
    //
    return cell;}
  
  /*
   * get cell in rectangle type B specified by lower-right corner point dpr that contains the point (px,py)    
   * TODO use DGeom interval constants instead of literal values       
   */
  private static final KCell getCell_RectB(double px,double py,KPoint dpr){
    KCell cell;
    double[] pr=dpr.getBasicPointCoor();
    //get our 7 test points
    double 
      p0x=pr[0],p0y=pr[1]+2.0,
      p1x=pr[0],p1y=pr[1]+3.0,
      p2x=pr[0]-GD.SQRT3,p2y=pr[1]+3.0,
      p3x=pr[0]-GD.SQRT3/2.0,p3y=pr[1]+3.0/2.0,
      p4x=pr[0]-GD.SQRT3,p4y=pr[1]+1.0;
//      p5x=pr.x-FM.SQRT3,p5y=pr.y;
    //
    //test (pr,p1,p2)
    if(triangleContainsPoint(pr[0],pr[1],p1x,p1y,p2x,p2y,px,py)){
      //test (pr,p0,p2)
      if(triangleContainsPoint(pr[0],pr[1],p0x,p0y,p2x,p2y,px,py)){
        //test (pr,p0,p3)
        if(triangleContainsPoint(pr[0],pr[1],p0x,p0y,p3x,p3y,px,py)){
          cell=new KCell(dpr.coors[0],dpr.coors[1],dpr.coors[2],11);
        //not contained in (pr,p0,p3), therefor contained in (p0,p2,p3)
        }else{
          cell=new KCell(dpr.coors[0]-1,dpr.coors[1],dpr.coors[2]+1,4);}
      //not contained in (pr,p0,p2) therefor it's contained in (p0,p1,p2)
      }else{
        cell=new KCell(dpr.coors[0]-1,dpr.coors[1],dpr.coors[2]+1,3);}
    //not contained in (pr,p1,p2) therefor it's contained in (pr,p2,p5)
    }else{
      //test (pr,p2,p4)
      if(triangleContainsPoint(pr[0],pr[1],p2x,p2y,p4x,p4y,px,py)){
        //test (pr,p3,p4)
        if(triangleContainsPoint(pr[0],pr[1],p3x,p3y,p4x,p4y,px,py)){
          cell=new KCell(dpr.coors[0],dpr.coors[1],dpr.coors[2],10);
        //not contained in (pr,p3,p4) therefor contained in (p3,p2,p4)
        }else{
          cell=new KCell(dpr.coors[0]-1,dpr.coors[1],dpr.coors[2]+1,5);}
      //not contained in (pr,p2,p4) therefor contained in (pr,p4,p5) 
      }else{
        cell=new KCell(dpr.coors[0],dpr.coors[1],dpr.coors[2],9);}}
    //
    return cell;}
  
  /*
   * returns true if the specified triangle contained the specified point
   */
  public static final boolean triangleContainsPoint(
    double tp0x,double tp0y,double tp1x,double tp1y,double tp2x,double tp2y,double px,double py){
    // Compute vectors 
    //TODO do this with just scalars. no need to create points
    Point2D.Double v0=new Point2D.Double(tp2x-tp0x,tp2y-tp0y);
    Point2D.Double v1=new Point2D.Double(tp1x-tp0x,tp1y-tp0y);
    Point2D.Double v2=new Point2D.Double(px-tp0x,py-tp0y);
    // Compute dot products
    double dot00=v0.x*v0.y+v0.y*v0.x;
    double dot01=v0.x*v1.y+v0.y*v1.x;
    double dot02=v0.x*v2.y+v0.y*v2.x;
    double dot11=v1.x*v1.y+v1.y*v1.x;
    double dot12=v1.x*v2.y+v1.y*v2.x;
    // Compute barycentric coordinates
    double invDenom = 1 / (dot00 * dot11 - dot01 * dot01);
    double u = (dot11 * dot02 - dot01 * dot12) * invDenom;
    double v = (dot00 * dot12 - dot01 * dot02) * invDenom;
    // Check if point is in triangle
    return (u > 0) && (v > 0) && (u + v < 1);}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public boolean equals(Object a){
    if(!(a instanceof KCell))return false;
    KCell b=(KCell)a;
    boolean e=b.ant==ant&&b.bat==bat&&b.cat==cat&&b.dog==dog;
    return e;}
  
  public int hashCode(){
    return (ant*65536)+(bat*4096)+(cat*256)+(dog*16);}
  
  public String toString(){
    String s="["+ant+","+bat+","+cat+","+dog+"]";
    return s;}
  
}
