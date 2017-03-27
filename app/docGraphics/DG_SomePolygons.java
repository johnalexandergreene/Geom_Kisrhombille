package org.fleen.geom_Kisrhombille.app.docGraphics;

import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.KPoint;

/*
 * A few polygons on a grid
 * A little sample, to demonstrate the existence of kpolygons
 */
public class DG_SomePolygons extends DocGraphics{
  
  void doGraphics(){
    //do the grid
    initImage(IMAGEWIDTH1,IMAGEHEIGHT1,IMAGESCALE1,WHITE);
    strokeGrid(8,STROKETHICKNESS1,GREY6);
    //do polygons
    KPolygon p0=new KPolygon(
      new KPoint(-3,0,3,0),
      new KPoint(-1,0,1,0),
      new KPoint(-3,-2,1,0));
    renderPolygon(p0,STROKETHICKNESS2,DOTSPAN1,GREEN);
    p0=new KPolygon(
      new KPoint(-1,2,3,5),
      new KPoint(0,3,3,5),
      new KPoint(1,2,1,5),
      new KPoint(0,1,1,5));
    renderPolygon(p0,STROKETHICKNESS2,DOTSPAN1,GREEN);
    p0=new KPolygon(
      new KPoint(-1,-2,-1,2),
      new KPoint(0,-1,-1,0),
      new KPoint(1,-2,-3,2),
      new KPoint(0,-3,-3,0),
      new KPoint(-1,-4,-3,2),
      new KPoint(-2,-3,-1,0));
    renderPolygon(p0,STROKETHICKNESS2,DOTSPAN1,GREEN);
    p0=new KPolygon(
      new KPoint(0,0,0,5),
      new KPoint(2,2,0,5),
      new KPoint(3,2,-1,0),
      new KPoint(2,1,-1,0),
      new KPoint(2,0,-2,5),
      new KPoint(3,1,-2,5),
      new KPoint(4,1,-3,0),
      new KPoint(2,-1,-3,0));
    renderPolygon(p0,STROKETHICKNESS2,DOTSPAN1,GREEN);}
  
  public static final void main(String[] a){
    new DG_SomePolygons();}

}
