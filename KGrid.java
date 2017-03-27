package org.fleen.geom_Kisrhombille;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_2D.GD;

/*
 * This is a kisrhombille grid coordinate system
 * it's parameters are :
 * twist : rotary direction of direction indexing, ie the chirality (?) of the vertex traversal
 * fish : basic interval
 * origin : 0,0,0,0
 * foreward : the real 2d direction at direction index 0
 */

public class KGrid implements Serializable{
  
  private static final long serialVersionUID=-7272582675212521562L;

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public KGrid(double originx,double originy,double north,boolean twist,double fish){
    this(
      new double[]{originx,originy},
      north,
      twist,
      fish);}
  
  public KGrid(double[] origin,double north,boolean twist,double fish){
    this.origin=origin;
    this.north=north;
    this.twist=twist;
    this.fish=fish;}
  
  /*
   * Default
   */
  public KGrid(){
    this(
      DEFAULT_ROOT_ORIGIN_2D,
      DEFAULT_ROOT_FOREWARD,
      DEFAULT_ROOT_TWIST,
      DEFAULT_ROOT_FISH);}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  //default params
  private static final double[] DEFAULT_ROOT_ORIGIN_2D={0,0};
  private static final double DEFAULT_ROOT_FOREWARD=0;
  private static final boolean DEFAULT_ROOT_TWIST=true;
  private static final double DEFAULT_ROOT_FISH=1.0;
  
  //intervals for use in point2d and direction accquirement
  private static final double
    UINT_1=1.0,
    UINT_2=2.0,
    UINT_SQRT3=Math.sqrt(3.0),
    DIRECTION12UNIT=GD.PI*2.0/12.0;
  
  //the origin for our k grid in 2d geom terms
  private double[] origin=null;
  //foreward (direction==0) for our k grid in 2d geom terms
  private double north;
  //we have 2 mirroring possibilities here
  //true means positive twist : direction indices go clockwise
  //false means negative twist : direction indices go counterclockwise
  private boolean twist;
  //the basic interval in the k coordinate system
  //goat is fish*SQRT3, hawk is fish*2.0
  private double fish;
  
  public double[] getOrigin(){
    return origin;}
  
  public double getNorth(){
    return north;}
  
  public boolean getTwist(){
    return twist;}
  
  public double getFish(){
    return fish;}

  /*
   * ################################
   * GIVEN A KVERTEX GET THE CORROSPONDING 2D POINT
   * ################################
   */
  
  public double[] getPoint2D(KPoint v){
    return getPoint2D(v.getAnt(),v.getBat(),v.getCat(),v.getDog());}
    
  public double[] getPoint2D(int ant,int bat,int cat,int dog){
    //get the 2d coordinates of the v12 local to v assuming a standard diamond grid
    double[] pv12={(ant+bat)*UINT_SQRT3,cat*(UINT_1+UINT_2)};
    //convert to polar coordinates
    double 
      pv12dir=GD.getDirection_PointPoint(0,0,pv12[0],pv12[1]),
      pv12dis=GD.getDistance_PointPoint(0,0,pv12[0],pv12[1]);
    //scale it
    pv12dis*=fish;
    //adjust direction for foreward according to twist
    if(twist==GK.TWIST_POSITIVE){
      pv12dir=GD.normalizeDirection(north+pv12dir);
    }else{
      pv12dir=GD.normalizeDirection(north-pv12dir);}
    //now we have the point in a form offset (p12dir,p12dis) from the hypothetical origin
    //get the actual v12 point
    pv12=GD.getPoint_PointDirectionInterval(origin[0],origin[1],pv12dir,pv12dis);
    //get the point for v by working from pv12, accounting for foreward, twist and fishscale
    double dir0,dis0;
    if(dog==0){
      dir0=0;//if our vertex is a v12 then do nothing
      dis0=0;
    }else if(dog==1){
      dir0=-DIRECTION12UNIT;
      dis0=GK.EDGESLV_GOAT;
    }else if(dog==2){
      dir0=0;
      dis0=GK.EDGESLV_HAWK;
    }else if(dog==3){
      dir0=DIRECTION12UNIT;
      dis0=GK.EDGESLV_GOAT;
    }else if(dog==4){
      dir0=DIRECTION12UNIT*2.0;
      dis0=GK.EDGESLV_HAWK;
    }else if(dog==5){
      dir0=DIRECTION12UNIT*3.0;
      dis0=GK.EDGESLV_GOAT;
    }else{
      throw new IllegalArgumentException("invalid dog : "+dog);}
    dis0*=fish;
    if(twist==GK.TWIST_POSITIVE){
      dir0=GD.normalizeDirection(north+dir0);
    }else{
      dir0=GD.normalizeDirection(north-dir0);}
    double[] pv=GD.getPoint_PointDirectionInterval(pv12[0],pv12[1],dir0,dis0);
    return pv;}
  
  /*
   * given a kgrid direction on this grid, return it's 2d direction 
   */
  public double getDirection2D(int kdir){
    double d=north;
    if(twist)
      d=GD.normalizeDirection(d+kdir*DIRECTION12UNIT);
    else
      d=GD.normalizeDirection(2.0*GD.PI+d-kdir*DIRECTION12UNIT);
    return d;}
  
  /*
   * given a kpolygon, get it's 2dpolygon form in terms of this grid
   */
  public DPolygon getPolygon2D(KPolygon kpolygon){
    int s=kpolygon.size();
    DPolygon p=new DPolygon(s);
    for(int i=0;i<s;i++)
      p.add(new DPoint(getPoint2D(kpolygon.get(i))));
    return p;}
  
  /*
   * ################################
   * GIVEN A 2D POINT GET THE CLOSEST CORROSPONDING KVERTEX
   * ################################
   */
 
  /*
   * given point p and grid g
   * interpret 6 -> 0 line (back to fore) as y axis : ay
   * 3 -> 9 as x axis : ax
   * distance of p from ay is its x coordinate : kx
   * distance of p from ax is its y coordinate : ky
   * the cartesian sector of p gives us the polarity of kx and ky
   * from kx and ky get closest v12 to p
   * get closest vertex in v12's local group to p. That's our vertex.
   * TODO optimize
   */
  public KPoint getKVertex(double[] point){
    //get the point's sector
    double pointdir=GD.getDirection_PointPoint(origin[0],origin[1],point[0],point[1]);
    int pointsector=getKVertex_GetSector(pointdir);
    //get our axis lines
    double[]
      xlineb=getPoint2D(1,1,0,0),
      ylineb=getPoint2D(-1,1,2,0);
    //get distances from axis lines. our pseudocartesian coordinates
    double 
      ky=GD.getDistance_PointLine(point[0],point[1],0,0,xlineb[0],xlineb[1]),
      kx=GD.getDistance_PointLine(point[0],point[1],0,0,ylineb[0],ylineb[1]);
    //get close vertex
    //first we move on the y axis
    int a=(int)(ky/(fish*3.0));
    if(pointsector==1||pointsector==2)a*=-1;
    int 
      ant=-a/2,
      bat=a/2,
      cat=a,
      dog=0;
    if(a%2==1)dog=5;
    //then the x axis
    a=(int)(kx/(fish*GD.SQRT3));
    if(pointsector==2||pointsector==3)a*=-1;
    if(dog==0){
      ant+=a/2;
      bat+=a/2; 
    }else{
      ant+=(a+1)/2;
      bat+=(a+1)/2;}
    //now we have a close vertex
    KPoint close=new KPoint(ant,bat,cat,dog);
    //get the group of vertices near it
    List<KPoint> closegroup=getKVertex_GetGroup(close);
    //get the closest vertex
    KPoint closest=getKVertex_GetClosestVertex(point,closegroup);
    return closest;}
  
  /*
   * Given a point and a list of vertices, return the closest vertex in the list to that point 
   * TODO optimize, generalize
   */
  private KPoint getKVertex_GetClosestVertex(double[] point,List<KPoint> vertices){
    double testdist,closestdist=Double.MAX_VALUE;
    KPoint closest=null;
    double[] testpoint;
    for(KPoint v:vertices){
      testpoint=getPoint2D(v);
      testdist=GD.getDistance_PointPoint(point[0],point[1],testpoint[0],testpoint[1]);
      if(testdist<closestdist){
        closestdist=testdist;
        closest=v;}}
    return closest;}
  
  private List<KPoint> getKVertex_GetGroup(KPoint c){
    List<KPoint> a=new ArrayList<KPoint>();
    a.add(c);
    int[] b=c.coors;
    //if the corner is a v12
    if(c.getDog()==0){
      a.addAll(KPoint.getV12LocalGroup(c));
    //otherwise it's a v4 
    }else{
      a.add(new KPoint(b[0],b[1],b[2],0));
      a.add(new KPoint(b[0],b[1],b[2],3));
      a.add(new KPoint(b[0],b[1],b[2],4));
      a.add(new KPoint(b[0]+1,b[1]+1,b[2],1));
      a.add(new KPoint(b[0]+1,b[1]+1,b[2],0));
      a.add(new KPoint(b[0]+1,b[1],b[2]-1,3));
      a.add(new KPoint(b[0]+1,b[1],b[2]-1,2));
      a.add(new KPoint(b[0]+1,b[1],b[2]-1,1));}
    return a;}
  
  /*
   * yr basic cartesian 4 sector dealy more or less
   * given a direction, get the sector
   * consider foreward and twist
   *                        
   *             ^
   *             | 
   *        3    |   0
   *             |  
   *     --------o--------
   *             | 
   *         2   |   1
   *             |
   *             
   *  This is how the sectors work out if foreward is directly north and twist is true
   *  If twist is false then flip everything horizontally. Switch 0 with 3, 1 with 2
   * 
   */
  private int getKVertex_GetSector(double dir){
    double r;//relative direction
    if(twist){
      if(dir>north){
        r=dir-north;
      }else{
        r=(GD.PI*2.0)-(north-dir);}  
    }else{
      if(dir<north){
        r=dir-north;
      }else{
        r=(GD.PI*2.0)-(north-dir);}}
    if(r>=0&&r<GD.PI*0.5){
      return 0;
    }else if(r>=GD.PI*0.5&&r<GD.PI){
      return 1;
    }else if(r>=GD.PI&&r<GD.PI*1.5){
      return 2;
    }else{
      return 3;}}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public String toString(){
    return "["+hashCode()+"]";}
  
  /*
   * ++++++++++++++++++++++++++++++++
   * &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
   * TEST
   * &&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
   * ++++++++++++++++++++++++++++++++
   */
  
  public static final void main(String[] a){
    test0();
  }
  
  private static final void test0(){
    System.out.println("KGRID TEST0");
    KGrid g=new KGrid(0,0,GD.PI/3.0,false,1.0);
    double[] point={4*GD.SQRT3,6};
    KPoint v=g.getKVertex(point);
    System.out.println("KGRID : "+g);
    System.out.println("POINT : ("+point[0]+","+point[1]+")");
    System.out.println("KVERTEX : "+v);
  }
  
}
