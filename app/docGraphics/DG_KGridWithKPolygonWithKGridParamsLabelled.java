package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_Kisrhombille.KGrid;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;

/*
 * a grid with a polygon on it
 * label params on polygon for grid within polygon
 */
public class DG_KGridWithKPolygonWithKGridParamsLabelled extends DocGraphics{
  
  static final Color FILL=new Color(255,255,0,64);
  
  void doGraphics(){
    //init the image and render the grid
    initImage(IMAGEWIDTH5,IMAGEHEIGHT5,IMAGESCALE3,WHITE);
    Set<KPoint> points=strokeGrid(8,STROKETHICKNESS2,GREY6);
    for(KPoint p:points)
      renderPoint(p,DOTSPAN1,GREY6);
    //render polygon
    KPolygon p0=new KPolygon(
      new KPoint(-1,-1,0,0),
      new KPoint(-1,0,1,0),
      new KPoint(0,1,1,0),
      new KPoint(1,1,0,0),
      new KPoint(1,0,-1,0),
      new KPoint(0,-1,-1,0));
    fillPolygon(p0,FILL);
    renderPolygon(p0,STROKETHICKNESS2,DOTSPAN1,GREEN);
    KPoint p;
    for(int i=0;i<6;i++){
      p=p0.get(i);
      renderPolygonPointIndex(p,i);}

    //render vertex indices
    
   }
  
  private void renderPolygonPointIndex(KPoint p,int index){
    DPoint dp=p.getBasicPoint2D();
    AffineTransform graphicstransform=graphics.getTransform();
    double[] pt={dp.x,dp.y};
    graphicstransform.transform(pt,0,pt,0,1);
    graphics.setTransform(new AffineTransform());
    graphics.setPaint(GREY0);
    graphics.setFont(new Font("Sans",Font.PLAIN,22));
    graphics.drawString(String.valueOf(index),(float)(pt[0]+8),(float)(pt[1]+3));
    graphics.setTransform(graphicstransform);}
  
  public static final void main(String[] a){
    new DG_KGridWithKPolygonWithKGridParamsLabelled();}

}
