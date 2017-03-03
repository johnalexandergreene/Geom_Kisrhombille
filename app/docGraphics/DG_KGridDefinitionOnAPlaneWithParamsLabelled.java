package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KGrid;
import org.fleen.geom_Kisrhombille.KVertex;

/*
 * 3 examples of KGrids defined in the plane
 * use a square image 256x256
 * render 1 at a time
 */
public class DG_KGridDefinitionOnAPlaneWithParamsLabelled extends DocGraphics{
  
  private static final double originx=1.2,originy=3.4;
  private static final double 
    NORTH=GD.PI/8,
    FISH=1.234;
  private static final boolean
    TWIST=true;
  
  
  
  void doGraphics(){
    initImage(IMAGEWIDTH4,IMAGEHEIGHT4,IMAGESCALE2,WHITE);
    //do the grid
    KGrid g0=new KGrid(originx,originy,NORTH,TWIST,FISH);
    
    
    Set<KVertex> points=strokeGrid(g0,8,STROKETHICKNESS2,GREY6);
//    for(KVertex p:points)
//      renderPoint(p,DOTSPAN1,GREY6);
    //
//    renderPoint(new KVertex(-3,-4,-1,5));
//    renderPoint(new KVertex(-1,-2,-1,4));
//    renderPoint(new KVertex(1,1,0,0));
    }
  
  /*
   * stroke it by stroking clocks
   * start at 0,0,0,0 and move out to range.
   * this is sloppy but brief
   * returns all involved points
   */
   Set<KVertex> strokeGrid(KGrid grid,int range,double thickness,Color color){
     Set<KVertex> points=new HashSet<KVertex>();
     Set<KVertex> v0s=getV0s(range);
     for(KVertex p:v0s){
       points.addAll(Arrays.asList(getClockKPoints(p)));
       strokeClock(grid,p,thickness,color);}
    return points;}
   
   void strokeClock(KGrid grid,KVertex v,double thickness,Color color){
     KVertex[] cp=getClockKPoints(v);
     int j;
     for(int i=1;i<cp.length;i++){
       j=i+1;
       if(j==cp.length)j=1;
       strokeSeg(grid,cp[i],cp[j],thickness,color);
       strokeSeg(grid,cp[0],cp[i],thickness,color);}}
   
   void strokeSeg(KGrid grid,KVertex v0,KVertex v1,double thickness,Color color){
     DPoint 
       p0=new DPoint(grid.getPoint2D(v0)),
       p1=new DPoint(grid.getPoint2D(v1));
     strokeSeg(p0,p1,thickness,color);}
   

  
  private void renderPoint(KVertex p){
    renderPoint(p,DOTSPAN2,GREEN);
    DPoint dp=p.getBasicPoint2D();
    AffineTransform graphicstransform=graphics.getTransform();
    double[] pt={dp.x,dp.y};
    graphicstransform.transform(pt,0,pt,0,1);
    graphics.setTransform(new AffineTransform());
    graphics.setPaint(BLACK);
    int xoff=18;
    graphics.setFont(new Font("Sans",Font.PLAIN,25));
    graphics.drawString("ant = "+p.getAnt(),(float)(pt[0]+xoff),(float)(pt[1]-75));
    graphics.drawString("bat = "+p.getBat(),(float)(pt[0]+xoff),(float)(pt[1]-50));
    graphics.drawString("cat = "+p.getCat(),(float)(pt[0]+xoff),(float)(pt[1]-25));
    graphics.drawString("dog = "+p.getDog(),(float)(pt[0]+xoff),(float)(pt[1]));
    
    graphics.setTransform(graphicstransform);}
  
  public static final void main(String[] a){
    new DG_KGridDefinitionOnAPlaneWithParamsLabelled();}

}
