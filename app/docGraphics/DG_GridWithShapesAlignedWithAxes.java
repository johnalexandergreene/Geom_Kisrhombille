package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.util.Set;

import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.KPoint;

/*
 * a kgrid with coordinates on the points
 */
public class DG_GridWithShapesAlignedWithAxes extends DocGraphics{
  
  private static final Color TRANSFILL=new Color(255,255,0,64);
  
  void doGraphics(){
    //do the grid
    initImage(IMAGEWIDTH4,IMAGEHEIGHT4,IMAGESCALE2,WHITE);
    Set<KPoint> points=strokeGrid(8,STROKETHICKNESS2,GREY6);
    for(KPoint p:points)
      renderPoint(p,DOTSPAN1,GREY6);
    //
    KPolygon p0=new KPolygon(
      new KPoint(-4,-3,1,5),
      new KPoint(-3,-2,1,5),
      new KPoint(-2,-2,0,0),
      new KPoint(-2,-2,0,5),
      new KPoint(-1,-2,-1,0),
      new KPoint(-3,-4,-1,5));
    renderPolygon(p0);
    KPolygon p1=new KPolygon(
      new KPoint(-2,-1,1,0),
      new KPoint(0,1,1,0),
      new KPoint(0,-1,-1,0));
    renderPolygon(p1);
    KPolygon p2=new KPolygon(
      new KPoint(1,1,0,2),
      new KPoint(1,1,0,4),
      new KPoint(2,1,-1,2),
      new KPoint(1,0,-1,4),
      new KPoint(1,0,-1,2),
      new KPoint(0,0,0,4));
    renderPolygon(p2);
    KPolygon p3=new KPolygon(
      new KPoint(6,2,-4,0),
      new KPoint(2,2,0,0),
      new KPoint(2,6,4,0));
    renderPolygon(p3);
    KPolygon p4=new KPolygon(
        new KPoint(2,1,-1,4),
        new KPoint(2,1,-1,5),
        new KPoint(2,1,-1,0));
      renderPolygon(p4);
        
    }
  
  private void renderPolygon(KPolygon p){
    fillPolygon(p,TRANSFILL);
    strokePolygon(p,STROKETHICKNESS2,GREEN);}
  
  public static final void main(String[] a){
    new DG_GridWithShapesAlignedWithAxes();}

}
