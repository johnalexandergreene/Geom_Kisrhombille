package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KGrid;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;

/*
 * a grid with a polygon on it
 * label params on polygon for grid within polygon
 */
public class DG_KGrid_KPolygon_NewKGridParamsLabelled extends DocGraphics{
  
  static final Color FILL=new Color(255,255,0,64);
  
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
    //render vertex indices
    fillPolygon(a,FILL);
    renderPolygon(a,STROKETHICKNESS2,DOTSPAN1,GREEN);
    renderPolygonPointIndex(a.get(0),0,12,7);
    renderPolygonPointIndex(a.get(1),1,6,25);
    renderPolygonPointIndex(a.get(2),2,-15,25);
    renderPolygonPointIndex(a.get(3),3,-25,7);
    renderPolygonPointIndex(a.get(4),4,-15,-7);
    renderPolygonPointIndex(a.get(5),5,6,-7);
    //
    renderOrigin(a.get(0));
    //
    renderNorth(a.get(0),a.get(1));

    
    
   }
  
  //NORTH
  
  static final double 
    ARROWOFFSET0=60,
    ARROWSHAFTLENGTH=60,
    ARROWHEADLENGTH=30,
    ARROWHEADWIDTH=7;
  
  private void renderNorth(KPoint p0,KPoint p1){
    DPoint 
      dp0=p0.getBasicPoint2D(),
      dp1=p1.getBasicPoint2D();
    AffineTransform graphicstransform=graphics.getTransform();
    double[] 
      pt0={dp0.x,dp0.y},
      pt1={dp1.x,dp1.y};
    graphicstransform.transform(pt0,0,pt0,0,1);
    graphicstransform.transform(pt1,0,pt1,0,1);
    graphics.setTransform(new AffineTransform());
    //
    graphics.setPaint(BLUE);
    graphics.setFont(new Font("Sans",Font.PLAIN,25));
    graphics.drawString("north",(float)(pt0[0]+85),(float)(pt0[1]-75));
    //
    renderNorthArrow(pt0,pt1);
    //
    graphics.setTransform(graphicstransform);}
  
  private void renderNorthArrow(double[] pt0,double[] pt1){
    pt0[0]+=15;
    pt0[1]+=15;
    pt1[0]+=15;
    pt1[1]+=15;
    //
    double 
      da=GD.getDirection_PointPoint(pt0[0],pt0[1],pt1[0],pt1[1]),
      dright=GD.normalizeDirection(da+GD.HALFPI),
      dleft=GD.normalizeDirection(da-GD.HALFPI);
    double[] 
      p0=GD.getPoint_PointDirectionInterval(pt0[0],pt0[1],da,ARROWOFFSET0),
      p1=GD.getPoint_PointDirectionInterval(p0[0],p0[1],da,ARROWSHAFTLENGTH),
      p2=GD.getPoint_PointDirectionInterval(p1[0],p1[1],da,ARROWHEADLENGTH),
      pleft=GD.getPoint_PointDirectionInterval(p1[0],p1[1],dleft,ARROWHEADWIDTH),
      pright=GD.getPoint_PointDirectionInterval(p1[0],p1[1],dright,ARROWHEADWIDTH);
    Path2D path=new Path2D.Double();
    path.moveTo(p0[0],p0[1]);
    path.lineTo(p1[0],p1[1]);
    graphics.setPaint(BLUE);
    graphics.setStroke(createStroke(STROKETHICKNESS2*imagescale));
    graphics.draw(path);
    path=new Path2D.Double();
    path.moveTo(p2[0],p2[1]);
    path.lineTo(pleft[0],pleft[1]);
    path.lineTo(pright[0],pright[1]);
    path.closePath();
    graphics.fill(path);}
  
  //ORIGIN
  
  private void renderOrigin(KPoint p){
    DPoint dp=p.getBasicPoint2D();
    AffineTransform graphicstransform=graphics.getTransform();
    double[] pt={dp.x,dp.y};
    graphicstransform.transform(pt,0,pt,0,1);
    graphics.setTransform(new AffineTransform());
    //
    graphics.setPaint(RED);
    graphics.setFont(new Font("Sans",Font.PLAIN,25));
    graphics.drawString("origin",(float)(pt[0]-80),(float)(pt[1]+28));
    //
    graphics.setTransform(graphicstransform);}
  
  //POLYGON POINT INDICES
  
  private void renderPolygonPointIndex(KPoint p,int index,float offsetx,float offsety){
    DPoint dp=p.getBasicPoint2D();
    AffineTransform graphicstransform=graphics.getTransform();
    double[] pt={dp.x,dp.y};
    graphicstransform.transform(pt,0,pt,0,1);
    graphics.setTransform(new AffineTransform());
    //
    graphics.setPaint(GREY0);
    graphics.setFont(new Font("Sans",Font.PLAIN,22));
    graphics.drawString(String.valueOf(index),(float)(pt[0]+offsetx),(float)(pt[1]+offsety));
    //
    graphics.setTransform(graphicstransform);}
  
  public static final void main(String[] a){
    new DG_KGrid_KPolygon_NewKGridParamsLabelled();}

}
