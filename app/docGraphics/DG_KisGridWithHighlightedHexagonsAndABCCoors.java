package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_Kisrhombille.KPoint;

/*
 * A few polygons on a grid
 * A little sample, to demonstrate the existence of kpolygons
 */
public class DG_KisGridWithHighlightedHexagonsAndABCCoors extends DocGraphics{
  
  void doGraphics(){
    initImage(IMAGEWIDTH5,IMAGEHEIGHT5,IMAGESCALE3,WHITE);
    Set<KPoint> v0s=getV0s(12);
    KPoint[] cp;
    
    for(KPoint v0:v0s){
      cp=getClockKPoints(v0);
      for(KPoint p:cp)
        renderPoint(p,DOTSPAN2,GREY6);}
    
    for(KPoint v0:v0s){
      cp=getClockKPoints(v0);
      
      for(int i=1;i<=12;i++)
        strokeSeg(cp[0],cp[i],STROKETHICKNESS2,GREY6);
      
      strokeSeg(cp[1],cp[3],STROKETHICKNESS2,GREEN);
      strokeSeg(cp[3],cp[5],STROKETHICKNESS2,GREEN);
      strokeSeg(cp[5],cp[7],STROKETHICKNESS2,GREEN);
      strokeSeg(cp[7],cp[9],STROKETHICKNESS2,GREEN);
      strokeSeg(cp[9],cp[11],STROKETHICKNESS2,GREEN);
      strokeSeg(cp[11],cp[1],STROKETHICKNESS2,GREEN);
      renderHexagonCoors(v0);}}
  
  void renderHexagonCoors(KPoint v){
    DPoint p=v.getBasicPoint2D();
    AffineTransform graphicstransform=graphics.getTransform();
    double[] pt={p.x,p.y};
    graphicstransform.transform(pt,0,pt,0,1);
    graphics.setTransform(new AffineTransform());
    graphics.setPaint(BLACK);
    graphics.setFont(new Font("Sans",Font.PLAIN,18));
    String z=v.getAnt()+","+v.getBat()+","+v.getDog();
    graphics.drawString(z,(float)(pt[0]-23),(float)(pt[1]+6));
    graphics.setTransform(graphicstransform);}
  
  public static final void main(String[] a){
    new DG_KisGridWithHighlightedHexagonsAndABCCoors();}

}
