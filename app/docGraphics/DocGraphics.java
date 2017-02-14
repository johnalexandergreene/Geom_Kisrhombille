package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.GK;
import org.fleen.geom_Kisrhombille.KSeg;
import org.fleen.geom_Kisrhombille.KVertex;

/*
 * create graphics for documentation
 */

@SuppressWarnings("unused")
public class DocGraphics{
  
  /*
   * ################################
   * RENDER 
   * ################################
   */

  private static final Color 
    BLACK=new Color(0,0,0),
    GREY0=new Color(32,32,32),
    GREY1=new Color(64,64,64),
    GREY2=new Color(96,96,96),
    GREY3=new Color(128,128,128),
    GREY4=new Color(160,160,160),
    GREY5=new Color(192,192,192),
    GREY6=new Color(224,224,224),
    WHITE=new Color(255,255,255),
    GREEN=new Color(39,184,111),
    BLUE=new Color(36,140,192),
    YELLOW=new Color(223,159,2),
    ORANGE=new Color(225,75,0),
    RED=new Color(192,19,21),
    PURPLE=new Color(178,67,133);
        
  private static final double 
    DOTSPAN0=8,
    DOTSPAN1=12,
    DOTSPAN2=44;
  
  private static final float
    STROKETHICKNESS0=2f,
    STROKETHICKNESS1=3f,
    STROKETHICKNESS2=4f;
  
  private static final double 
    IMAGESCALE0=10,
    IMAGESCALE1=20,
    IMAGESCALE2=40;
  
  private static final int 
    //small
    IMAGEWIDTH0=500,
    IMAGEHEIGHT0=500,
    //medium
    IMAGEWIDTH1=800,
    IMAGEHEIGHT1=400,
    //full sheet
    IMAGEWIDTH2=1000,
    IMAGEHEIGHT2=2000;
   
  /*
   * ################################
   * ILLUSTRATIONS
   * ################################
   */
  
  void generate(){
    renderSomeKSegs();
  }
  
  /*
   * KSegs on a grid
   * get some random segs, render them and their end points
   */
  void renderSomeKSegs(){
    //do the grid
    initImage(IMAGEWIDTH0,IMAGEHEIGHT0,IMAGESCALE1,WHITE);
    strokeGrid(8,STROKETHICKNESS1,GREY6);
    //get some nonintersecting segs
    List<KSeg> segs=null;
    while(segs==null||segsIntersect(segs))
      segs=getSegs(18,3,5);
    //render segs
    for(KSeg s:segs)
      renderSeg(s,STROKETHICKNESS2,DOTSPAN1,GREEN);
    //
    ui.repaint();}
  
  List<KSeg> getSegs(int count,int v0range,int lengthrange){
    List<KSeg> segs=new ArrayList<KSeg>();
    KSeg s;
    for(int i=0;i<count;i++){
      s=getRandomSeg(v0range,lengthrange);
      segs.add(s);}
    return segs;}
  
  boolean segsIntersect(List<KSeg> segs){
    for(KSeg s0:segs){
      for(KSeg s1:segs){
        if(s0.equals(s1))
          continue;
        if(s0.intersects(s1))
          return true;}}
    return false;}
  
  
  /*
   * for printing out a hunk of kis graph paper or whatever
   */
  void renderAGrid(){
    initImage(IMAGEWIDTH2,IMAGEHEIGHT2,IMAGESCALE1,WHITE);
    strokeGrid(12,STROKETHICKNESS1,GREY5);
    ui.repaint();}
  

  

  
  /*
   * ################################
   * RENDERING UTIL
   * ################################
   */
  
  private KVertex getRandomPoint(int range){
    Random r=new Random();
    boolean valid=false;
    int a=0,b=0,c=0,count=0;
    while(!valid){
      count++;
      if(count==1000)throw new IllegalArgumentException("infinite loop");
      a=r.nextInt(range*2)-range;
      b=r.nextInt(range*2)-range;
      c=r.nextInt(range*2)-range;
      valid=(c==b-a);}
    int d=r.nextInt(6);
    return new KVertex(a,b,c,d);}
  
  private KSeg getRandomSeg(int v0range,int lengthrange){
    KVertex v0=getRandomPoint(v0range);
    int[] b=GK.getLiberties(v0.getDog());
    Random r=new Random();
    int 
      dir=b[r.nextInt(b.length)],
      length=r.nextInt(lengthrange)+1;
    KVertex v1=GK.getVertex_Transitionswise(v0,dir,length);
    return new KSeg(v0,v1);}
  
  private Stroke createStroke(double thickness){
    Stroke s=new BasicStroke((float)(thickness/imagescale),BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND,0,null,0);
    return s;}
  
  private void strokeSeg(KVertex v0,KVertex v1,double thickness,Color color){
    DPoint 
      p0=v0.getBasicPoint2D(),
      p1=v1.getBasicPoint2D();
    Path2D path=new Path2D.Double();
    path.moveTo(p0.x,p0.y);
    path.lineTo(p1.x,p1.y);
    graphics.setStroke(createStroke(thickness));
    graphics.setPaint(color);
    graphics.draw(path);}
  
  private void renderSeg(KVertex v0,KVertex v1,double strokethickness,double dotspan,Color color){
    strokeSeg(v0,v1,strokethickness,color);
    renderPoint(v0,dotspan,color);
    renderPoint(v1,dotspan,color);}
  
  private void renderSeg(KSeg s,double strokethickness,double dotspan,Color color){
    renderSeg(s.getVertex0(),s.getVertex1(),strokethickness,dotspan,color);}
  
  private void strokeClock(KVertex v,double thickness,Color color){
    KVertex[] cp=getClockPoints(v);
    int j;
    for(int i=1;i<cp.length;i++){
      j=i+1;
      if(j==cp.length)j=1;
      strokeSeg(cp[i],cp[j],thickness,color);
      strokeSeg(cp[0],cp[i],thickness,color);}}
  
  /*
   * stroke it by stroking clocks
   * start at 0,0,0,0 and move out to range.
   * this is sloppy but brief
   */
  private void strokeGrid(int range,double thickness,Color color){
    boolean valid;
    for(int ant=-range;ant<range;ant++){
      for(int bat=-range;bat<range;bat++){
        for(int cat=-range;cat<range;cat++){
          valid=(cat==bat-ant);
          if(valid)
            strokeClock(new KVertex(ant,bat,cat,0),thickness,color);}}}}
  
  /*
   * 13 points
   * we treat v lke the center of a clock, disregard dog, branch from there.
   */
  private KVertex[] getClockPoints(KVertex v){
    int a=v.getAnt(),b=v.getBat(),c=v.getCat();
    KVertex[] w={ 
      new KVertex(a,b,c,0),//center
      new KVertex(a,b,c,2),//north
      new KVertex(a,b,c,3),//just cw of north
      new KVertex(a,b,c,4),
      new KVertex(a,b,c,5),
      new KVertex(a+1,b,c-1,2),
      new KVertex(a+1,b,c-1,1),
      new KVertex(a,b-1,c-1,4),
      new KVertex(a,b-1,c-1,3),
      new KVertex(a,b-1,c-1,2),
      new KVertex(a-1,b-1,c,5),
      new KVertex(a-1,b-1,c,4),
      new KVertex(a,b,c,1)};
    return w;}
  
  private void renderPoint(KVertex v,double dotspan,Color color){
    DPoint p=v.getBasicPoint2D();
    dotspan=dotspan/imagescale;
    Ellipse2D dot=new Ellipse2D.Double(p.x-dotspan/2,p.y-dotspan/2,dotspan,dotspan);
    graphics.setPaint(color);
    graphics.fill(dot);}
  
  
//  void renderHexagonCoordinateSystemAxisArrows(Graphics2D graphics){
//    DPoint p=new HexClock(grid,0,0,0).getPoint(0);
//    double radius=200;
//    //
//    double[] 
//      p0=GD.getPoint_PointDirectionInterval(p.x,p.y,GD.normalizeDirection((GD.PI*1.0/6.0)*5),radius),
//      p1=GD.getPoint_PointDirectionInterval(p.x,p.y,GD.normalizeDirection((GD.PI*1.0/6.0)*11),radius);
//    renderAHexagonCoordinateSystemAxis(p0,p1,"ant",graphics);
//    //
//    p0=GD.getPoint_PointDirectionInterval(p.x,p.y,GD.normalizeDirection((GD.PI*1.0/6.0)*7),radius);
//    p1=GD.getPoint_PointDirectionInterval(p.x,p.y,GD.normalizeDirection((GD.PI*1.0/6.0)*1),radius);
//    renderAHexagonCoordinateSystemAxis(p0,p1,"bat",graphics);
//    //
//    p0=GD.getPoint_PointDirectionInterval(p.x,p.y,GD.normalizeDirection((GD.PI*1.0/6.0)*9),radius);
//    p1=GD.getPoint_PointDirectionInterval(p.x,p.y,GD.normalizeDirection((GD.PI*1.0/6.0)*3),radius);
//    renderAHexagonCoordinateSystemAxis(p0,p1,"cat",graphics);
//    
//  }
  
  private void renderAHexagonCoordinateSystemAxis(double[] p0,double[] p1,String axisname,Graphics2D graphics){
    Path2D path=new Path2D.Double();
    double d=GD.getDirection_PointPoint(p1[0],p1[1],p0[0],p0[1]);
    
    path.moveTo(p0[0],p0[1]);
    path.lineTo(p1[0],p1[1]);
    graphics.setPaint(new Color(0,0,255,64));
    BasicStroke s=new BasicStroke(24f,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND,0,null,0);
    graphics.setStroke(s);
    graphics.draw(path);}
  
  private void renderPointCoors(KVertex v,Color color){
    DPoint p=v.getBasicPoint2D();
    graphics.setPaint(color);
    graphics.setFont(new Font("Sans",Font.PLAIN,17));
    String z=v.getAnt()+","+v.getBat()+",";
    graphics.drawString(z,(float)p.x-13,(float)p.y+1);
    z=v.getCat()+","+v.getDog();
    graphics.drawString(z,(float)p.x-13,(float)p.y+15);}
  
  /*
   * ################################
   * UI
   * ################################
   */
  
  DGUI ui;
  
  private void initUI(){
    ui=new DGUI(this);
    ui.setBackground(Color.black);}
  
  /*
   * ################################
   * EXPORT
   * ################################
   */
  
  private static final String EXPORTDIR="";
  
  void export(){
    
  }
  
  /*
   * ################################
   * IMAGE
   * ################################
   */
  
  //RENDERING HINTS
  public static final HashMap<RenderingHints.Key,Object> RENDERING_HINTS=
    new HashMap<RenderingHints.Key,Object>();
  static{
    RENDERING_HINTS.put(
      RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
    RENDERING_HINTS.put(
      RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
    RENDERING_HINTS.put(
      RenderingHints.KEY_DITHERING,RenderingHints.VALUE_DITHER_DEFAULT);
    RENDERING_HINTS.put(
      RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    RENDERING_HINTS.put(
      RenderingHints.KEY_ALPHA_INTERPOLATION,RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    RENDERING_HINTS.put(
      RenderingHints.KEY_COLOR_RENDERING,RenderingHints.VALUE_COLOR_RENDER_QUALITY); 
    RENDERING_HINTS.put(
      RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_NORMALIZE);}
  
  double imagescale;
  int imagewidth,imageheight;
  BufferedImage image;
  Graphics2D graphics;
  
  private void initImage(int width,int height,double scale,Color fill){
    imagewidth=width;
    imageheight=height;
    imagescale=scale;
    Container c=ui.getContentPane();
    image=new BufferedImage(imagewidth,imageheight,BufferedImage.TYPE_INT_ARGB);
    graphics=image.createGraphics();
    graphics.setRenderingHints(RENDERING_HINTS);
    graphics.setPaint(fill);
    graphics.fillRect(0,0,imagewidth,imageheight);
    graphics.setTransform(getTransform());}
  
  private AffineTransform getTransform(){
    AffineTransform t=new AffineTransform();
    t.translate(imagewidth/2,imageheight/2);
    t.scale(imagescale,-imagescale);
    return t;}
  
  /*
   * ################################
   * CONSTRUCTOR AND MAIN
   * ################################
   */
  
  DocGraphics(){
    initUI();}
  
  public static final void main(String[] a){
    DocGraphics dg=new DocGraphics();}
  
  
  

}
