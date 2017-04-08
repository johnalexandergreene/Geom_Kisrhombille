package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.List;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;

public class DG_KisrhombilleTessellationWithNumberedTriangles extends DocGraphics{
  
  void doGraphics(){
    initImage(IMAGEWIDTH4,IMAGEHEIGHT4,IMAGESCALE2,RED);
    doColoredHexagons();
    renderTriangles();}
  
  void renderTriangles(){
    List<GridTriangle> triangles=getGridTriangles(12);
    for(GridTriangle t:triangles){
      strokePolygon(t,STROKETHICKNESS0,BLACK);
      drawIndex(t);
      }}
  
  void drawIndex(GridTriangle t){
    DPoint p=GD.getPoint_Mean(Arrays.asList(new DPoint[]{t.get(0),t.get(1),t.get(2)}));
    AffineTransform graphicstransform=graphics.getTransform();
    double[] pt={p.x,p.y};
    graphicstransform.transform(pt,0,pt,0,1);
    graphics.setTransform(new AffineTransform());
    graphics.setPaint(BLACK);
    graphics.setFont(new Font("Sans",Font.PLAIN,11));
    int xoff=-4,yoff=4;
    if(t.index==10||t.index==11)
      xoff-=2;
    graphics.drawString(String.valueOf(t.index),(float)(pt[0]+xoff),(float)(pt[1]+yoff));
    graphics.setTransform(graphicstransform);}
  
  void doColoredHexagons(){
    List<GridHexagon> hexagons=getGridHexagons(12);
    GridHexagon h;
    Color c;
    int ant,bat,z;
    for(int i=0;i<hexagons.size();i++){
      h=hexagons.get(i);
      ant=h.center.getAnt()+1000;
      bat=h.center.getBat()+1000;
      z=(ant+bat)%3;
      if(z==0)
        c=RED;
      else if(z==1)
        c=YELLOW;
      else
        c=BLUE;
      fillPolygon(h,c);}}
  
  public static final void main(String[] a){
    new DG_KisrhombilleTessellationWithNumberedTriangles();}

}
