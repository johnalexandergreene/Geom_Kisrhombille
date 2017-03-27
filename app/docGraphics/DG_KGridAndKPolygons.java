package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.util.Set;

import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;

/*
 * some segs on a kgrid. no coordinates.
 */
public class DG_KGridAndKPolygons extends DocGraphics{
  
  static final Color FILL=new Color(255,255,0,64);
  
  void doGraphics(){
    //do the grid
    initImage(IMAGEWIDTH4,IMAGEHEIGHT4,IMAGESCALE2,WHITE);
    Set<KPoint> points=strokeGrid(8,STROKETHICKNESS2,GREY6);
    for(KPoint p:points)
      renderPoint(p,DOTSPAN1,GREY6);
    //do polygons
    KPolygon p0=new KPolygon(
      new KPoint(-4,-3,1,0),
      new KPoint(-4,-3,1,5),
      new KPoint(-3,-3,0,2),
      new KPoint(-3,-3,0,1));
    fillPolygon(p0,FILL);
    renderPolygon(p0,STROKETHICKNESS2,DOTSPAN1,GREEN);
    p0=new KPolygon(
      new KPoint(-3,-4,-1,2),
      new KPoint(-3,-3,0,0),
      new KPoint(-2,-3,-1,2),
      new KPoint(-2,-3,-1,0),
      new KPoint(-2,-4,-2,2),
      new KPoint(-3,-4,-1,0));
    fillPolygon(p0,FILL);
    renderPolygon(p0,STROKETHICKNESS2,DOTSPAN1,GREEN);
    p0=new KPolygon(
      new KPoint(-3,-2,1,0),
      new KPoint(-1,0,1,0),
      new KPoint(-1,-2,-1,0));
    fillPolygon(p0,FILL);
    renderPolygon(p0,STROKETHICKNESS2,DOTSPAN1,GREEN);
    p0=new KPolygon(
      new KPoint(0,0,0,2),
      new KPoint(-1,-1,0,4),
      new KPoint(0,-1,-1,0),
      new KPoint(2,1,-1,0),
      new KPoint(1,2,1,0),
      new KPoint(0,1,1,0),
      new KPoint(0,0,0,5),
      new KPoint(0,0,0,0));
    fillPolygon(p0,FILL);
    renderPolygon(p0,STROKETHICKNESS2,DOTSPAN1,GREEN);
    p0=new KPolygon(
      new KPoint(2,3,1,0),
      new KPoint(3,4,1,0),
      new KPoint(3,3,0,0),
      new KPoint(4,3,-1,0),
      new KPoint(3,2,-1,0),
      new KPoint(2,2,0,0));
    fillPolygon(p0,FILL);
    renderPolygon(p0,STROKETHICKNESS2,DOTSPAN1,GREEN);}
  
  public static final void main(String[] a){
    new DG_KGridAndKPolygons();}

}
