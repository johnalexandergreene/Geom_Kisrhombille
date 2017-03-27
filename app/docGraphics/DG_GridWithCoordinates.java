package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_Kisrhombille.KPoint;

/*
 * a kgrid with coordinates on the points
 */
public class DG_GridWithCoordinates extends DocGraphics{
  
  void doGraphics(){
    //do the grid
    initImage(IMAGEWIDTH5,IMAGEHEIGHT5,IMAGESCALE3,WHITE);
    Set<KPoint> points=strokeGrid(8,STROKETHICKNESS2,GREY5);
    for(KPoint p:points)
      renderPoint(p,DOTSPAN2,GREY4);
    for(KPoint p:points)
      renderPointCoors(p);}
  
  void renderPointCoors(KPoint v){
    DPoint p=v.getBasicPoint2D();
    AffineTransform graphicstransform=graphics.getTransform();
    double[] pt={p.x,p.y};
    graphicstransform.transform(pt,0,pt,0,1);
    graphics.setTransform(new AffineTransform());
    graphics.setPaint(BLACK);
    graphics.setFont(new Font("Sans",Font.PLAIN,18));
    String z=v.getAnt()+","+v.getBat()+",";
    graphics.drawString(z,(float)(pt[0]-13),(float)(pt[1]-3));
    z=v.getCat()+","+v.getDog();
    graphics.drawString(z,(float)(pt[0]-13),(float)(pt[1]+15));
    graphics.setTransform(graphicstransform);}
  
  public static final void main(String[] a){
    new DG_GridWithCoordinates();}

}
