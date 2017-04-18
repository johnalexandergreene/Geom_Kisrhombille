package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KPolygon;

/*
 * a grid with a polygon on it
 * label params on polygon for grid within polygon
 */
public class DG_KGrid_KPolygonWithLabelledKGridInsideIt extends DocGraphics{
  
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
    points=strokeGrid(2,STROKETHICKNESS2,GREEN);
    Shape bigclip=clip.createTransformedShape(AffineTransform.getScaleInstance(1.2,1.2));
    graphics.setClip(bigclip);
    for(KPoint p:points)
      renderPoint(p,DOTSPAN1,GREEN);
    graphics.setClip(null);
    renderGrid0Label();
    renderGrid1Label();


    
    
   }
  
  private void renderGrid0Label(){
    AffineTransform graphicstransform=graphics.getTransform();
    graphics.setTransform(new AffineTransform());
    //
    graphics.setPaint(GREY3);
    graphics.setFont(new Font("Sans",Font.PLAIN,40));
    graphics.drawString("grid0",(float)(10),(float)(44));
    renderNorthArrow(new double[]{140,170},new double[]{140,160});
    //
    graphics.setTransform(graphicstransform);}
  
  private void renderGrid1Label(){
    AffineTransform graphicstransform=graphics.getTransform();
    graphics.setTransform(new AffineTransform());
    //
    graphics.setPaint(GREY0);
    graphics.setFont(new Font("Sans",Font.PLAIN,40));
    graphics.drawString("grid1",(float)(270),(float)(200));
    renderNorthArrow(new double[]{200,250},new double[]{200+GD.SQRT3,250-3});
    //
    graphics.setTransform(graphicstransform);}
  
  //NORTH
  
  static final double 
    ARROWOFFSET0=80,
    ARROWSHAFTLENGTH=40,
    ARROWHEADLENGTH=30,
    ARROWHEADWIDTH=7;
  
  private void renderNorthArrow(double[] pt0,double[] pt1){
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
    graphics.setStroke(createStroke(STROKETHICKNESS2*imagescale));
    graphics.draw(path);
    path=new Path2D.Double();
    path.moveTo(p2[0],p2[1]);
    path.lineTo(pleft[0],pleft[1]);
    path.lineTo(pright[0],pright[1]);
    path.closePath();
    graphics.fill(path);}
  
  public static final void main(String[] a){
    new DG_KGrid_KPolygonWithLabelledKGridInsideIt();}

}
