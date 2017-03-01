package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.util.Set;

import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.KVertex;

/*
 * a kgrid with coordinates on the points
 */
public class DG_GridWithShapesAlignedWithAxes extends DocGraphics{
  
  private static final Color TRANSFILL=new Color(255,255,0,64);
  
  void doGraphics(){
    //do the grid
    initImage(IMAGEWIDTH4,IMAGEHEIGHT4,IMAGESCALE2,WHITE);
    Set<KVertex> points=strokeGrid(8,STROKETHICKNESS2,GREY6);
    for(KVertex p:points)
      renderPoint(p,DOTSPAN1,GREY6);
    //
    KPolygon p0=new KPolygon(
      new KVertex(-4,-3,1,5),
      new KVertex(-3,-2,1,5),
      new KVertex(-2,-2,0,0),
      new KVertex(-2,-2,0,5),
      new KVertex(-1,-2,-1,0),
      new KVertex(-3,-4,-1,5));
    renderPolygon(p0);
    KPolygon p1=new KPolygon(
      new KVertex(-2,-1,1,0),
      new KVertex(0,1,1,0),
      new KVertex(0,-1,-1,0));
    renderPolygon(p1);
    KPolygon p2=new KPolygon(
      new KVertex(1,1,0,2),
      new KVertex(1,1,0,4),
      new KVertex(2,1,-1,2),
      new KVertex(1,0,-1,4),
      new KVertex(1,0,-1,2),
      new KVertex(0,0,0,4));
    renderPolygon(p2);
    KPolygon p3=new KPolygon(
      new KVertex(6,2,-4,0),
      new KVertex(2,2,0,0),
      new KVertex(2,6,4,0));
    renderPolygon(p3);
    KPolygon p4=new KPolygon(
        new KVertex(2,1,-1,4),
        new KVertex(2,1,-1,5),
        new KVertex(2,1,-1,0));
      renderPolygon(p4);
        
    }
  
  private void renderPolygon(KPolygon p){
    fillPolygon(p,TRANSFILL);
    strokePolygon(p,STROKETHICKNESS2,GREEN);}
  
  public static final void main(String[] a){
    new DG_GridWithShapesAlignedWithAxes();}

}
