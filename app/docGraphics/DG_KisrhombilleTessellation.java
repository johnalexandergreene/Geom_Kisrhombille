package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.util.List;

/*
 * A few polygons on a grid
 * A little sample, to demonstrate the existence of kpolygons
 */
public class DG_KisrhombilleTessellation extends DocGraphics{
  
  void doGraphics(){
    initImage(IMAGEWIDTH4,IMAGEHEIGHT4,IMAGESCALE2,BLACK);
    List<GridTriangle> triangles=getGridTriangles(12);
    System.out.println("triangles:"+triangles.size());
    Color c;
    for(GridTriangle t:triangles){
      if(t.index%2==0)
        c=BLACK;
      else
        c=WHITE;
      fillPolygon(t,c);}}
  
  public static final void main(String[] a){
    new DG_KisrhombilleTessellation();}

}
