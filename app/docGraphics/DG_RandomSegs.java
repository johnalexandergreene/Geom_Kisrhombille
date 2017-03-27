package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.util.List;

import org.fleen.geom_Kisrhombille.KSeg;

/*
 * KSegs on a grid
 * get some random segs, render them and their end points
 */
public class DG_RandomSegs extends DocGraphics{
  
  void doGraphics(){
    //do the grid
    initImage(IMAGEWIDTH1,IMAGEHEIGHT1,IMAGESCALE1,WHITE);
    strokeGrid(8,STROKETHICKNESS1,GREY6);
    //get some nonintersecting segs
    List<KSeg> segs=null;
    while(segs==null||segsIntersect(segs))
      segs=getSegs(18,3,5);
    //render segs
    for(KSeg s:segs)
      renderSeg(s,STROKETHICKNESS2,DOTSPAN1,GREEN);}
  
  public static final void main(String[] a){
    new DG_RandomSegs();}

}
