package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.KPoint;

/*
 * A few polygons on a grid
 * A little sample, to demonstrate the existence of kpolygons
 */
public class DG_UnitTriangle extends DocGraphics{
  
  void doGraphics(){
    initImage(IMAGEWIDTH4,IMAGEHEIGHT4,IMAGESCALE4,WHITE);
    AffineTransform t=graphics.getTransform();
    t.translate(-1.3,0.45);
    t.rotate(-GD.PI/6);
    graphics.setTransform(t);
    KPoint 
      v0=new KPoint(0,0,0,0),
      v1=new KPoint(0,0,0,4),
      v2=new KPoint(0,0,0,5);
    KPolygon kp=new KPolygon(v0,v1,v2);
    renderPolygon(kp,STROKETHICKNESS3,DOTSPAN2,ORANGE);
    //
    DPoint 
      p0=v0.getBasicPoint2D(),
      p1=v1.getBasicPoint2D(),
      p2=v2.getBasicPoint2D(),
      phawk=new DPoint(GD.getPoint_Between2Points(p0.x,p0.y,p1.x,p1.y,0.5)),
      pfish=new DPoint(GD.getPoint_Between2Points(p1.x,p1.y,p2.x,p2.y,0.5)),
      pgoat=new DPoint(GD.getPoint_Between2Points(p2.x,p2.y,p0.x,p0.y,0.5));
    renderLabel(p0,"P12",-56,0);
    renderLabel(p1,"P6",17,-7);
    renderLabel(p2,"P4",-3,33);
    renderLabel(pfish,"F",15,18);
    renderLabel(pgoat,"G",-21,28);
    renderLabel(phawk,"H",0,-12);
    
  }

  void renderLabel(DPoint p,String s,int xoff,int yoff){
    AffineTransform oldgt=graphics.getTransform();
    double[] pt={p.x,p.y};
    oldgt.transform(pt,0,pt,0,1);
    graphics.setTransform(new AffineTransform());
    graphics.setPaint(BLACK);
    graphics.setFont(new Font("Sans",Font.PLAIN,22));
    graphics.drawString(s,(float)(pt[0]+xoff),(float)(pt[1]+yoff));
    graphics.setTransform(oldgt);}
  

  
  public static final void main(String[] a){
    new DG_UnitTriangle();}

}
