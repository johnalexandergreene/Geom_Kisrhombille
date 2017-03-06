package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KGrid;
import org.fleen.geom_Kisrhombille.KVertex;

/*
 * 3 examples of KGrids defined in the plane
 * use a square image 512x512
 * render 1 at a time by commenting out the param constants
 */
public class DG_KGridDefinitionOnAPlaneWithParamsLabelled extends DocGraphics{
  
  /*
   * ################################
   * GRID AND GRAPHICS PARAMS
   * ################################
   */
  
  //========
  //GRID #1
  
  //========
  //GRID #2
  private static final double 
    originx=2,originy=3;
  private static final double 
    NORTH=GD.PI/8,
    FISH=1.23;
  private static final boolean
    TWIST=true;
  private static final double 
    IMAGEXOFFSET=-3,
    IMAGEYOFFSET=-3;
  private static final int 
    CGRIDLABELSOFFSETXX=-32,
    CGRIDLABELSOFFSETXY=12,
    CGRIDLABELSOFFSETYX=-12,
    CGRIDLABELSOFFSETYY=40;
  private static final int 
    ORIGINLABELOFFSETX=-24,
    ORIGINLABELOFFSETY=26;
  private static final 
    String NORTHLABELTEXT="north=\u03c0/8";
  private static final KVertex
    FISHPOINT0=new KVertex(0,0,0,4),
    FISHPOINT1=new KVertex(1,1,0,1);
  private static final int 
    FISHLABELOFFSETX=18,
    FISHLABELOFFSETY=20;
  private static final double 
    TWISTARCRADIUS=1.5,
    TWISTARCSTART=GD.PI*0.7,
    TWISTARCEND=GD.PI*1.8;
  private static final String 
    TWISTLABELTEXT="twist=clockwise";
  private static final int
    TWISTLABELOFFSETX=-120,
    TWISTLABELOFFSETY=-19;
  
  //========
  //GRID #3
  
  /*
   * ################################
   * DO GRAPHICS
   * ################################
   */
  
  //arrowhead
  private static final double
    SHAFTOFFSET=1,
    SHAFTLENGTH=0.6,
    HEADLENGTH=0.5,
    HEADSPAN=0.25;
  
  void doGraphics(){
    //init kgrid
    KGrid g=new KGrid(originx,originy,NORTH,TWIST,FISH);
    //init image, more or less center on the diagram
    initImage(IMAGEWIDTH1,IMAGEHEIGHT1,IMAGESCALE3,WHITE);
    AffineTransform t=graphics.getTransform();
    t.translate(IMAGEXOFFSET,IMAGEYOFFSET);
    graphics.setTransform(t);  
    //render everything
    renderCGrid();
    renderKGrid(g);
    renderOrigin(g);
    renderNorth(g);
    renderFish(g);
    renderTwist(g);}
  
  /*
   * ################################
   * CGRID
   * just x and y axes, and little labels
   * ################################
   */
  
  private void renderCGrid(){
    graphics.setStroke(createStroke(STROKETHICKNESS0));
    graphics.setPaint(YELLOW);
    graphics.drawLine(-100,0,100,0);
    graphics.drawLine(0,-100,0,100);
    renderCGridLabels();}
  
  private void renderCGridLabels(){
    AffineTransform graphicstransform=graphics.getTransform();
    double[] p={0,0};
    graphicstransform.transform(p,0,p,0,1);
    graphics.setTransform(new AffineTransform());
    graphics.setPaint(YELLOW);
    graphics.setFont(new Font("Sans",Font.PLAIN,12));
    graphics.drawString("x",(float)(p[0]+CGRIDLABELSOFFSETXX),(float)(p[1]+CGRIDLABELSOFFSETXY));
    graphics.drawString("y",(float)(p[0]+CGRIDLABELSOFFSETYX),(float)(p[1]+CGRIDLABELSOFFSETYY));
    graphics.setTransform(graphicstransform);}
  
  /*
   * ################################
   * KGRID
   * ################################
   */
  
  private void renderKGrid(KGrid g){
    Set<KVertex> gp=renderKGrid(g,8,STROKETHICKNESS0,GREY6);
    for(KVertex p:gp)
      renderKGridPoint(g,p);}
  
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
  
  private void renderKGridPoint(KGrid grid,KVertex p){
    DPoint dp=new DPoint(grid.getPoint2D(p));
    renderPoint(dp,DOTSPAN0,GREY4);
    AffineTransform graphicstransform=graphics.getTransform();
    double[] pt={dp.x,dp.y};
    graphicstransform.transform(pt,0,pt,0,1);
    graphics.setTransform(new AffineTransform());
    graphics.setPaint(GREY3);
    graphics.setFont(new Font("Sans",Font.PLAIN,10));
    String s=p.getAnt()+" "+p.getBat();
    graphics.drawString(s,(float)(pt[0]+4),(float)(pt[1]-15));
    s=p.getCat()+" "+p.getDog();
    graphics.drawString(s,(float)(pt[0]+4),(float)(pt[1]-3));;
    graphics.setTransform(graphicstransform);}
  
  /*
   * ################################
   * ORIGIN
   * ################################
   */
  
  private void renderOrigin(KGrid grid){
    KVertex origin=new KVertex(0,0,0,0);
    DPoint p=new DPoint(grid.getPoint2D(origin));
    renderPoint(p,DOTSPAN1,RED);
    AffineTransform graphicstransform=graphics.getTransform();
    double[] pt={p.x,p.y};
    graphicstransform.transform(pt,0,pt,0,1);
    graphics.setTransform(new AffineTransform());
    graphics.setPaint(RED);
    graphics.setFont(new Font("Sans",Font.PLAIN,18));
    String s="origin=("+p.x+","+p.y+")";
    graphics.drawString(s,(float)(pt[0]+ORIGINLABELOFFSETX),(float)(pt[1]+ORIGINLABELOFFSETY));
    graphics.setTransform(graphicstransform);}
  
  /*
   * ################################
   * TWIST
   * ################################
   */
  
  private static final double SEGARCINCREMENT=GD.PI/24;
  
  DPoint pend=null;
  double dend;
  
  private void renderTwist(KGrid g){
    DPoint p=new DPoint(g.getPoint2D(new KVertex(0,0,0,0)));
    renderTwistArc(p,TWISTARCRADIUS,TWISTARCSTART,TWISTARCEND);
    renderTwistArcArrowhead();
    renderTwistLabel(TWISTLABELTEXT);}
  
  private void renderTwistLabel(String text){
    AffineTransform graphicstransform=graphics.getTransform();
    double[] pt={pend.x,pend.y};
    graphicstransform.transform(pt,0,pt,0,1);
    graphics.setTransform(new AffineTransform());
    graphics.setPaint(PURPLE);
    graphics.setFont(new Font("Sans",Font.PLAIN,18));
    graphics.drawString(text,(float)(pt[0]+TWISTLABELOFFSETX),(float)(pt[1]+TWISTLABELOFFSETY));
    graphics.setTransform(graphicstransform);}
  
  private void renderTwistArc(DPoint center,double radius,double start,double end){
    Path2D path=new Path2D.Double();
    double arclength=Math.abs(GD.getAngle_2Directions(start,end));
    int segcount=(int)(arclength/SEGARCINCREMENT)+1;
    double d=start;
    double[] p=GD.getPoint_PointDirectionInterval(center.x,center.y,d,radius),pprior=null;
    path.moveTo(p[0],p[1]);
    for(int i=0;i<segcount;i++){
      d=GD.normalizeDirection(d+SEGARCINCREMENT);
      pprior=p;
      p=GD.getPoint_PointDirectionInterval(center.x,center.y,d,radius);
      path.lineTo(p[0],p[1]);}
    //
    graphics.setPaint(PURPLE);
    graphics.setStroke(createStroke(STROKETHICKNESS2));
    graphics.draw(path);
    //
    dend=GD.getDirection_PointPoint(pprior[0],pprior[1],p[0],p[1]);
    pend=new DPoint(p);}
  
  private void renderTwistArcArrowhead(){ 
    double
      dleft=GD.normalizeDirection(dend-GD.PI/2),
      dright=GD.normalizeDirection(dend+GD.PI/2);
    DPoint 
      pleft=pend.getPoint(dleft,HEADSPAN/2),
      pright=pend.getPoint(dright,HEADSPAN/2),
      ptip=pend.getPoint(dend,HEADLENGTH);
    Path2D path=new Path2D.Double();
    path.moveTo(pleft.x,pleft.y);
    path.lineTo(ptip.x,ptip.y);
    path.lineTo(pright.x,pright.y);
    path.closePath();
    graphics.setPaint(PURPLE);
    graphics.fill(path);}
  
  /*
   * ################################
   * FISH
   * ################################
   */
  
  private void renderFish(KGrid g){
    DPoint 
      p0d=new DPoint(g.getPoint2D(FISHPOINT0)),
      p1d=new DPoint(g.getPoint2D(FISHPOINT1));
    strokeSeg(p0d,p1d,STROKETHICKNESS2,GREEN);
    renderPoint(p0d,DOTSPAN1,GREEN);
    renderPoint(p1d,DOTSPAN1,GREEN);
    renderFishLabel(p0d,"fish="+FISH);}
  
  private void renderFishLabel(DPoint p,String labeltext){
    AffineTransform graphicstransform=graphics.getTransform();
    double[] pt={p.x,p.y};
    graphicstransform.transform(pt,0,pt,0,1);
    graphics.setTransform(new AffineTransform());
    graphics.setPaint(GREEN);
    graphics.setFont(new Font("Sans",Font.PLAIN,18));
    graphics.drawString(labeltext,(float)(pt[0]+FISHLABELOFFSETX),(float)(pt[1]+FISHLABELOFFSETY));
    graphics.setTransform(graphicstransform);}
  
  /*
   * ################################
   * NORTH
   * ################################
   */
  
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
    renderNorthArrow(a0,a1);
    renderNorthLabel(a1);}
  
  private void renderNorthArrow(DPoint p0,DPoint p1){
    strokeSeg(p0,p1,STROKETHICKNESS2,BLUE);  
    double
      d=p0.getDirection(p1),
      dleft=GD.normalizeDirection(d-GD.PI/2),
      dright=GD.normalizeDirection(d+GD.PI/2);
    DPoint 
      pleft=p1.getPoint(dleft,HEADSPAN/2),
      pright=p1.getPoint(dright,HEADSPAN/2),
      pend=p1.getPoint(d,HEADLENGTH);
    Path2D path=new Path2D.Double();
    path.moveTo(pleft.x,pleft.y);
    path.lineTo(pend.x,pend.y);
    path.lineTo(pright.x,pright.y);
    path.closePath();
    graphics.setPaint(BLUE);
    graphics.fill(path);}
  
  private void renderNorthLabel(DPoint p){
    AffineTransform graphicstransform=graphics.getTransform();
    double[] pt={p.x,p.y};
    graphicstransform.transform(pt,0,pt,0,1);
    graphics.setTransform(new AffineTransform());
    graphics.setPaint(BLUE);
    graphics.setFont(new Font("Sans",Font.PLAIN,18));
    graphics.drawString(NORTHLABELTEXT,(float)(pt[0]-2),(float)(pt[1]+35));
    graphics.setTransform(graphicstransform);}
  
  /*
   * ################################
   * ################################
   * ################################
   */
  
  public static final void main(String[] a){
    new DG_KGridDefinitionOnAPlaneWithParamsLabelled();}

}
