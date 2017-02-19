package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.util.Set;

import org.fleen.geom_Kisrhombille.KVertex;

/*
 * a kgrid with coordinates on the points
 */
public class DG_GridWithCoordinates extends DocGraphics{
  
  void doGraphics(){
    //do the grid
    initImage(IMAGEWIDTH0,IMAGEHEIGHT0,IMAGESCALE1,WHITE);
    Set<KVertex> points=strokeGrid(8,STROKETHICKNESS1,GREY6);
    for(KVertex p:points)
      renderPointCoors(p,10,RED);}
  
  public static final void main(String[] a){
    new DG_GridWithCoordinates();}

}
