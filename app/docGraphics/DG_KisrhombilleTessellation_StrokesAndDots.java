package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.util.ArrayList;
import java.util.List;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DSeg;

/*
 * A few polygons on a grid
 * A little sample, to demonstrate the existence of kpolygons
 */
public class DG_KisrhombilleTessellation_StrokesAndDots extends DocGraphics{
  
  void doGraphics(){
    initImage(IMAGEWIDTH4,IMAGEHEIGHT4,IMAGESCALE2,WHITE);
    List<GridTriangle> triangles=getGridTriangles(12);
    List<DSeg> segs=new ArrayList<DSeg>();
    for(GridTriangle t:triangles)
      segs.addAll(t.getSegs());
    List<DPoint> points=new ArrayList<DPoint>();
    for(DSeg s:segs){
      points.add(s.p0);
      strokeSeg(s.p0,s.p1,STROKETHICKNESS2,GREEN);}
    for(DPoint p:points){
      this.renderPoint(p,DOTSPAN0,BLACK);}}
  
  public static final void main(String[] a){
    new DG_KisrhombilleTessellation_StrokesAndDots();}

}
