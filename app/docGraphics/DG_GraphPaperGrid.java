package org.fleen.geom_Kisrhombille.app.docGraphics;

/*
 * Graph paper grid
 * for printing out a hunk of kis graph paper or whatever
 */
public class DG_GraphPaperGrid extends DocGraphics{
  
  void doGraphics(){
    initImage(IMAGEWIDTH2,IMAGEHEIGHT2,IMAGESCALE1,WHITE);
    strokeGrid(12,STROKETHICKNESS1,GREY5);}
  
  public static final void main(String[] a){
    new DG_GraphPaperGrid();}

}
