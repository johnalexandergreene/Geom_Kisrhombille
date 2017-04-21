package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_Kisrhombille.KGrid;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;

/*
 * a grid with a polygon on it
 * label params on polygon for grid within polygon
 */
public class DG_KGrid_KPolygon_VariableGridDensity extends DocGraphics{
  
  static final Color FILL0=new Color(220,255,220);
  
  void doGraphics(){
    //init the image and render the grid
    initImage(IMAGEWIDTH5,IMAGEHEIGHT5,IMAGESCALE3,WHITE);
    Set<KPoint> points=strokeGrid(8,STROKETHICKNESS2,GREY6);
    for(KPoint p:points)
      renderPoint(p,DOTSPAN1,GREY6);
    //
    KPolygon a=new KPolygon(
      new KPoint(-1,-1,0,0),
      new KPoint(-1,0,1,0),
      new KPoint(0,1,1,0),
      new KPoint(1,1,0,0),
      new KPoint(1,0,-1,0),
      new KPoint(0,-1,-1,0));
//    //render vertex indices
    fillPolygon(a,FILL0);
    strokePolygon(a,STROKETHICKNESS2,GREEN);
    Path2D clip=a.getDefaultPolygon2D().getPath2D();
    graphics.setClip(clip);
    //
//    KGrid g=new KGrid(new double[]{0,0},0,true,1.0);
//    renderKGrid(g);
//    graphics.setClip(null);
//    renderLabel("density=1");
    //
//    KGrid g=new KGrid(new double[]{0,0},0,true,0.5);
//    renderKGrid(g);
//    graphics.setClip(null);
//    renderLabel("density=2");
    //
    KGrid g=new KGrid(new double[]{0,0},0,true,0.25);
    renderKGrid(g);
    graphics.setClip(null);
    renderLabel("density=3");


    
    
   }
  
  /*
   * ################################
   * LABEL
   * ################################
   */
  
  private void renderLabel(String label){
    AffineTransform graphicstransform=graphics.getTransform();
    graphics.setTransform(new AffineTransform());
    //
    graphics.setPaint(GREY2);
    graphics.setFont(new Font("Sans",Font.PLAIN,30));
    graphics.drawString(label,(float)(300),(float)(66));
    //
    graphics.setTransform(graphicstransform);}
  
  /*
   * ################################
   * KGRID
   * ################################
   */
  
  private void renderKGrid(KGrid g){
    renderKGrid(g,8,STROKETHICKNESS0,GREY3);
//    Set<KPoint> gp=renderKGrid(g,8,STROKETHICKNESS0,GREY6);
//    for(KPoint p:gp)
//      renderKGridPoint(g,p);
    }
  
  Set<KPoint> renderKGrid(KGrid grid,int range,double thickness,Color color){
    Set<KPoint> points=new HashSet<KPoint>();
    Set<KPoint> v0s=getV0s(range);
    for(KPoint p:v0s){
      points.addAll(Arrays.asList(getClockKPoints(p)));
      strokeClock(grid,p,thickness,color);}
   return points;}
  
  void strokeClock(KGrid grid,KPoint v,double thickness,Color color){
    KPoint[] cp=getClockKPoints(v);
    int j;
    for(int i=1;i<cp.length;i++){
      j=i+1;
      if(j==cp.length)j=1;
      strokeSeg(grid,cp[i],cp[j],thickness,color);
      strokeSeg(grid,cp[0],cp[i],thickness,color);}}
  
  void strokeSeg(KGrid grid,KPoint v0,KPoint v1,double thickness,Color color){
    DPoint 
      p0=new DPoint(grid.getPoint2D(v0)),
      p1=new DPoint(grid.getPoint2D(v1));
    strokeSeg(p0,p1,thickness,color);}
  
//  private void renderKGridPoint(KGrid grid,KPoint p){
//    DPoint dp=new DPoint(grid.getPoint2D(p));
//    renderPoint(dp,DOTSPAN0,GREY4);
//    AffineTransform graphicstransform=graphics.getTransform();
//    double[] pt={dp.x,dp.y};
//    graphicstransform.transform(pt,0,pt,0,1);
//    graphics.setTransform(new AffineTransform());
//    graphics.setPaint(GREY3);
//    graphics.setFont(new Font("Sans",Font.PLAIN,10));
//    String s=p.getAnt()+" "+p.getBat();
//    graphics.drawString(s,(float)(pt[0]+4),(float)(pt[1]-15));
//    s=p.getCat()+" "+p.getDog();
//    graphics.drawString(s,(float)(pt[0]+4),(float)(pt[1]-3));
//    graphics.setTransform(graphicstransform);}
  
  //------------------------------------
  
  
  public static final void main(String[] a){
    new DG_KGrid_KPolygon_VariableGridDensity();}

}
