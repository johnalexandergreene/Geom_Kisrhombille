package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.util.List;

import org.fleen.geom_2D.DPolygon;

/*
 * A few polygons on a grid
 * A little sample, to demonstrate the existence of kpolygons
 */
public class DG_HexagonTessellation extends DocGraphics{
  
  void doGraphics(){
    initImage(IMAGEWIDTH4,IMAGEHEIGHT4,IMAGESCALE2,RED);
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
    new DG_HexagonTessellation();}

}
