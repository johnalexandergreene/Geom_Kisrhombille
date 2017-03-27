package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.util.ArrayList;

import org.fleen.geom_Kisrhombille.KPoint;

/*
 * a kgrid with coordinates on the points
 */
public class DG_HexagonAndSplitHexagon extends DocGraphics{
  
  void doGraphics(){
    //do the grid
    initImage(192,192,IMAGESCALE2,WHITE);
   KPoint v0=new ArrayList<KPoint>(getV0s(1)).get(0);
   KPoint[] c=getClockKPoints(v0);
   strokeSeg(c[1],c[3],STROKETHICKNESS2,GREEN);
   strokeSeg(c[3],c[5],STROKETHICKNESS2,GREEN);
   strokeSeg(c[5],c[7],STROKETHICKNESS2,GREEN);
   strokeSeg(c[7],c[9],STROKETHICKNESS2,GREEN);
   strokeSeg(c[9],c[11],STROKETHICKNESS2,GREEN);
   strokeSeg(c[11],c[1],STROKETHICKNESS2,GREEN);
//   renderPoint(c[1],DOTSPAN1,GREEN);
//   renderPoint(c[3],DOTSPAN1,GREEN);
//   renderPoint(c[5],DOTSPAN1,GREEN);
//   renderPoint(c[7],DOTSPAN1,GREEN);
//   renderPoint(c[9],DOTSPAN1,GREEN);
//   renderPoint(c[11],DOTSPAN1,GREEN);
   //splitty
   for(int i=1;i<=12;i++){
     strokeSeg(c[0],c[i],STROKETHICKNESS2,GREEN);}
//   renderPoint(c[2],DOTSPAN1,GREEN);
//   renderPoint(c[4],DOTSPAN1,GREEN);
//   renderPoint(c[6],DOTSPAN1,GREEN);
//   renderPoint(c[8],DOTSPAN1,GREEN);
//   renderPoint(c[10],DOTSPAN1,GREEN);
//   renderPoint(c[12],DOTSPAN1,GREEN);
  
  }
  
  
  public static final void main(String[] a){
    new DG_HexagonAndSplitHexagon();}

}
