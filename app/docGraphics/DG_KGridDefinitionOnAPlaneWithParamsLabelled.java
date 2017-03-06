package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
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
  
  private static final Color 
    KGRIDCOLOR=GREY6,
    CGRIDCOLOR=RED;
  
  //
  private static final double originx=2,originy=3;
  private static final double 
    NORTH=GD.PI/8,
    FISH=1.234;
  private static final boolean
    TWIST=true;
  private static final double 
    IMAGEXOFFSET=-3,
    IMAGEYOFFSET=-3;
  //
  
  void doGraphics(){
    initImage(IMAGEWIDTH1,IMAGEHEIGHT1,IMAGESCALE3,WHITE);
    AffineTransform t=graphics.getTransform();
    t.translate(IMAGEXOFFSET,IMAGEYOFFSET);
    graphics.setTransform(t);  
    //render the cgrid
    renderCGrid();
    //do the kgrid
    KGrid g0=new KGrid(originx,originy,NORTH,TWIST,FISH);
    renderKGrid(g0,8,STROKETHICKNESS0,KGRIDCOLOR);
    //
    renderOrigin(g0);
    renderNorth(g0);
    
    
//    for(KVertex p:points)
//      renderPoint(p,DOTSPAN1,GREY6);
    //
//    renderPoint(new KVertex(-3,-4,-1,5));
//    renderPoint(new KVertex(-1,-2,-1,4));
//    renderPoint(new KVertex(1,1,0,0));
    }
  
  private void renderCGrid(){
    graphics.setStroke(createStroke(STROKETHICKNESS0));
    graphics.setPaint(CGRIDCOLOR);
    graphics.drawLine(-100,0,100,0);
    graphics.drawLine(0,-100,0,100);
    
    
  }
  
  /*
   * ################################
   * NORTH
   * ################################
   */
  
  private static final double
    SHAFTOFFSET=1.5,
    SHAFTLENGTH=1,
    HEADLENGTH=0.7,
    HEADSPAN=0.4;
  
  private void renderNorth(KGrid grid){
    KVertex 
      p0=new KVertex(0,0,0,0),
      p1=new KVertex(0,0,0,2);
    DPoint 
      p0d=new DPoint(grid.getPoint2D(p0)),
      p1d=new DPoint(grid.getPoint2D(p1));
    double dir=p0d.getDirection(p1d);
    DPoint 
      a0=p0d.getPoint(dir,SHAFTOFFSET),
      a1=a0.getPoint(dir,SHAFTLENGTH);
    strokeSeg(a0,a1,STROKETHICKNESS3,BLUE);
    renderArrowhead(p1d,dir);
    renderArrowheadLable(a1);}
  
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
  
  private void renderArrowheadLable(DPoint p){
    AffineTransform graphicstransform=graphics.getTransform();
    double[] pt={p.x,p.y};
    graphicstransform.transform(pt,0,pt,0,1);
    graphics.setTransform(new AffineTransform());
    graphics.setPaint(BLUE);
    graphics.setFont(new Font("Sans",Font.PLAIN,18));
    String s="north=\u03c0/8";
    graphics.drawString(s,(float)(pt[0]),(float)(pt[1]+26));
    graphics.setTransform(graphicstransform);
  }
  
  /*
   * ################################
   * ORIGIN
   * ################################
   */
  
  private void renderOrigin(KGrid grid){
    KVertex origin=new KVertex(0,0,0,0);
    DPoint p=new DPoint(grid.getPoint2D(origin));
    renderPoint(p,DOTSPAN1,BLACK);
    AffineTransform graphicstransform=graphics.getTransform();
    double[] pt={p.x,p.y};
    graphicstransform.transform(pt,0,pt,0,1);
    graphics.setTransform(new AffineTransform());
    graphics.setPaint(BLACK);
    graphics.setFont(new Font("Sans",Font.PLAIN,18));
    String s="origin=("+p.x+","+p.y+")";
    graphics.drawString(s,(float)(pt[0]-24),(float)(pt[1]+26));
    graphics.setTransform(graphicstransform);}
  
 
  /*
   * ################################
   * KGRID
   * ################################
   */
  
  /*
   * stroke it by stroking clocks
   * start at 0,0,0,0 and move out to range.
   * this is sloppy but brief
   * returns all involved points
   */
   Set<KVertex> renderKGrid(KGrid grid,int range,double thickness,Color color){
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
   
   void strokeSeg(DPoint p0,DPoint p1,double thickness,Color color){
     Path2D path=new Path2D.Double();
     path.moveTo(p0.x,p0.y);
     path.lineTo(p1.x,p1.y);
     graphics.setStroke(createStroke(thickness));
     graphics.setPaint(color);
     graphics.draw(path);}
   

  
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
