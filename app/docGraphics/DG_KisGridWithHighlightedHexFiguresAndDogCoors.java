package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.KPoint;

/*
 * a kgrid with coordinates on the points
 */
public class DG_KisGridWithHighlightedHexFiguresAndDogCoors extends DocGraphics{
  
  void doGraphics(){
    initImage(IMAGEWIDTH5,IMAGEHEIGHT5,IMAGESCALE3,WHITE);
    Set<KPoint> v0s=getV0s(12);
    KPoint[] cp;
    //grid, strokes and vertices
    for(KPoint v0:v0s){
      cp=getClockKPoints(v0);
      for(KPoint p:cp)
        renderPoint(p,DOTSPAN2,GREY6);
      for(int i=1;i<=12;i++)
        strokeSeg(cp[0],cp[i],STROKETHICKNESS2,GREY6);
      strokeSeg(cp[1],cp[3],STROKETHICKNESS2,GREY6);
      strokeSeg(cp[3],cp[5],STROKETHICKNESS2,GREY6);
      strokeSeg(cp[5],cp[7],STROKETHICKNESS2,GREY6);
      strokeSeg(cp[7],cp[9],STROKETHICKNESS2,GREY6);
      strokeSeg(cp[9],cp[11],STROKETHICKNESS2,GREY6);
      strokeSeg(cp[11],cp[1],STROKETHICKNESS2,GREY6);}
    //figure fill overlay
    Color cz=new Color(255,255,0,64);
    for(KPoint v0:v0s){
      cp=getClockKPoints(v0);
      fillPolygon(new KPolygon(cp[12],cp[1],cp[3],cp[4],cp[0]),cz);}
    //figure stroke
    for(KPoint v0:v0s){
      cp=getClockKPoints(v0);
      strokeSeg(cp[12],cp[1],STROKETHICKNESS2,GREEN);
      strokeSeg(cp[1],cp[3],STROKETHICKNESS2,GREEN);
      strokeSeg(cp[3],cp[4],STROKETHICKNESS2,GREEN);
      strokeSeg(cp[4],cp[0],STROKETHICKNESS2,GREEN);
      strokeSeg(cp[0],cp[12],STROKETHICKNESS2,GREEN);}
    //render dog
    for(KPoint v0:v0s){
      cp=getClockKPoints(v0);
      renderDog(cp[12]);
      renderDog(cp[1]);
      renderDog(cp[2]);
      renderDog(cp[3]);
      renderDog(cp[4]);
      renderDog(cp[0]);}}
  
  void renderDog(KPoint v){
    DPoint p=v.getBasicPoint2D();
    AffineTransform graphicstransform=graphics.getTransform();
    double[] pt={p.x,p.y};
    graphicstransform.transform(pt,0,pt,0,1);
    graphics.setTransform(new AffineTransform());
    graphics.setPaint(BLACK);
    graphics.setFont(new Font("Sans",Font.PLAIN,28));
    graphics.drawString(String.valueOf(v.getDog()),(float)(pt[0]-7),(float)(pt[1]+9));
    graphics.setTransform(graphicstransform);}
  
  public static final void main(String[] a){
    new DG_KisGridWithHighlightedHexFiguresAndDogCoors();}

}
