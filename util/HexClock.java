package org.fleen.geom_Kisrhombille.util;

import java.util.ArrayList;
import java.util.List;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.KGrid;

/*
 * a hexagon clock in a kisrhombille tesselation
 * point up
 */
public class HexClock{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public HexClock(KGrid grid,int ant,int bat,int cat){
    this.grid=grid;
    this.cant=ant;
    this.cbat=bat;
    this.ccat=cat;
    valid=(ccat==cbat-cant); 
  
  }
  
  /*
   * ################################
   * GEOM
   * ################################
   */
  
  //it's valid if the coordinates specified for the center vertex are valid coors for a KVertex
  boolean valid;
  
  KGrid grid;
  
  //center
  int cant,cbat,ccat;
  
  /*
   * 6 vertices
   * 0..5
   */
  public DPoint getPoint(int dog){
    return new DPoint(grid.getPoint2D(cant,cbat,ccat,dog));}
  
  DPolygon getHexagon(){
    DPolygon p=new DPolygon();
    p.add(new DPoint(grid.getPoint2D(cant,cbat,ccat,2)));
    p.add(new DPoint(grid.getPoint2D(cant,cbat,ccat,4)));
    p.add(new DPoint(grid.getPoint2D(cant+1,cbat,ccat-1,2)));
    p.add(new DPoint(grid.getPoint2D(cant,cbat-1,ccat-1,4)));
    p.add(new DPoint(grid.getPoint2D(cant,cbat-1,ccat-1,2)));
    p.add(new DPoint(grid.getPoint2D(cant-1,cbat-1,ccat,4)));
    return p;}
  
  List<DPolygon> getTriangles(){
    List<DPolygon> triangles=new ArrayList<DPolygon>(12);
    DPoint[] p=getClockPoints();
    DPolygon t=new DPolygon(p[0],p[1],p[2]);
    triangles.add(t);
    t=new DPolygon(p[0],p[2],p[3]);
    triangles.add(t);
    t=new DPolygon(p[0],p[3],p[4]);
    triangles.add(t);
    t=new DPolygon(p[0],p[4],p[5]);
    triangles.add(t);
    t=new DPolygon(p[0],p[5],p[6]);
    triangles.add(t);
    t=new DPolygon(p[0],p[6],p[7]);
    triangles.add(t);
    t=new DPolygon(p[0],p[7],p[8]);
    triangles.add(t);
    t=new DPolygon(p[0],p[8],p[9]);
    triangles.add(t);
    t=new DPolygon(p[0],p[9],p[10]);
    triangles.add(t);
    t=new DPolygon(p[0],p[10],p[11]);
    triangles.add(t);
    t=new DPolygon(p[0],p[11],p[12]);
    triangles.add(t);
    t=new DPolygon(p[0],p[12],p[1]);
    triangles.add(t);
    //
    return triangles;}
  
  /*
   * 13 points.
   * center, north then traverse clockwise
   */
  DPoint[] getClockPoints(){
    List<DPoint> points=new ArrayList<DPoint>(13);
    points.add(new DPoint(grid.getPoint2D(cant,cbat,ccat,0)));//center
    points.add(new DPoint(grid.getPoint2D(cant,cbat,ccat,2)));//north
    points.add(new DPoint(grid.getPoint2D(cant,cbat,ccat,3)));//just cw 0f p1
    points.add(new DPoint(grid.getPoint2D(cant,cbat,ccat,4)));
    points.add(new DPoint(grid.getPoint2D(cant,cbat,ccat,5)));
    points.add(new DPoint(grid.getPoint2D(cant+1,cbat,ccat-1,2)));
    points.add(new DPoint(grid.getPoint2D(cant+1,cbat,ccat-1,1)));
    points.add(new DPoint(grid.getPoint2D(cant,cbat-1,ccat-1,4)));//north
    points.add(new DPoint(grid.getPoint2D(cant,cbat-1,ccat-1,3)));
    points.add(new DPoint(grid.getPoint2D(cant,cbat-1,ccat-1,2)));
    points.add(new DPoint(grid.getPoint2D(cant-1,cbat-1,ccat,5)));
    points.add(new DPoint(grid.getPoint2D(cant-1,cbat-1,ccat,4)));
    points.add(new DPoint(grid.getPoint2D(cant,cbat,ccat,1)));//just ccw of p1
    return points.toArray(new DPoint[13]);}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public int hashCode(){
    return cant+17*cbat+31*ccat;}
  
  public boolean equals(Object a){
    HexClock b=(HexClock)a;
    return b.cant==cant&&b.cbat==cbat&&b.ccat==ccat;}
  
  

}
