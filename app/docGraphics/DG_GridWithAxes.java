package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.geom.Path2D;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KPoint;

/*
 * a kgrid with coordinates on the points
 */
public class DG_GridWithAxes extends DocGraphics{
  
  void doGraphics(){
    //do the grid
    initImage(IMAGEWIDTH4,IMAGEHEIGHT4,IMAGESCALE2,WHITE);
    Set<KPoint> points=strokeGrid(8,STROKETHICKNESS2,GREY6);
    for(KPoint p:points)
      renderPoint(p,DOTSPAN1,GREY6);
    //
    renderAxesStar();}
  
  private static final double 
    SHAFTLENGTH=3,
    HEADLENGTH=1,
    HEADSPAN=0.8;
  
  private void renderAxesStar(){
    DPoint pc=new KPoint(0,0,0,0).getBasicPoint2D();
    double[] dir=new double[12];
    DPoint[] 
      pa=new DPoint[12];
    for(int i=0;i<12;i++){
      dir[i]=i*GD.PI/6;
      pa[i]=new DPoint(GD.getPoint_PointDirectionInterval(pc.x,pc.y,dir[i],SHAFTLENGTH));
      strokeSeg(pc,pa[i],STROKETHICKNESS3,BLUE);
      renderArrowhead(pa[i],dir[i]);}}
  
  private void renderArrowhead(DPoint p,double d){
    double 
      dleft=GD.normalizeDirection(d-GD.PI/2),
      dright=GD.normalizeDirection(d+GD.PI/2);
    DPoint 
      pleft=p.getPoint(dleft,HEADSPAN/2),
      pright=p.getPoint(dright,HEADSPAN/2),
      pend=p.getPoint(d,HEADLENGTH);
    Path2D path=new Path2D.Double();
    path.moveTo(pleft.x,pleft.y);
    path.lineTo(pend.x,pend.y);
    path.lineTo(pright.x,pright.y);
    path.closePath();
    graphics.setPaint(BLUE);
    graphics.fill(path);}
  
  
  public static final void main(String[] a){
    new DG_GridWithAxes();}

}
