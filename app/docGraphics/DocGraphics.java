package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.GK;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.KSeg;
import org.fleen.geom_Kisrhombille.KPoint;

/*
 * create graphics for documentation
 * this is the general stuff
 * specifics are in the concrete classes
 */
abstract class DocGraphics{
  
  /*
   * ################################
   * CONSTRUCTOR 
   * ################################
   */
  
  DocGraphics(){
    init();}
  
  /*
   * ################################
   * RENDER 
   * ################################
   */

  static final Color 
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
        
  static final double 
    DOTSPAN0=8,
    DOTSPAN1=12,
    DOTSPAN2=16;
  
  static final float
    STROKETHICKNESS0=2f,
    STROKETHICKNESS1=3f,
    STROKETHICKNESS2=4f,
    STROKETHICKNESS3=6f;
  
  static final double 
    IMAGESCALE0=4,
    IMAGESCALE1=20,
    IMAGESCALE2=30,
    IMAGESCALE3=60,
    IMAGESCALE4=180;
  
  static final int 
    //small box
    IMAGEWIDTH0=96,
    IMAGEHEIGHT0=96,
    //medium box
    IMAGEWIDTH1=512,
    IMAGEHEIGHT1=512,
    //full sheet
    IMAGEWIDTH2=1000,
    IMAGEHEIGHT2=2000,
    //lo rez diagram 
    IMAGEWIDTH3=1600,
    IMAGEHEIGHT3=800,
    //basic column-spanning diagram
    IMAGEWIDTH4=768,
    IMAGEHEIGHT4=256,
    //double tall column-spanning diagram
    IMAGEWIDTH5=768,
    IMAGEHEIGHT5=512;
  
  static final int 
    INNEROUTERPOLYGONOFFSET0=48;
   
  void init(){
    initUI();
    doGraphics();
    ui.repaint();}
  
  void regenerate(){
    doGraphics();
    ui.repaint();}
  
  abstract void doGraphics();
  
  /*
   * ################################
   * RENDERING UTIL
   * ################################
   */
  
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
  
  void renderPolygon(KPolygon polygon,double strokethickness,double dotspan,Color color){
    int s=polygon.size(),i1;
    KPoint p0,p1;
    for(int i0=0;i0<s;i0++){
      i1=i0+1;
      if(i1==s)i1=0;
      p0=polygon.get(i0);
      renderPoint(p0,dotspan,color);
      p1=polygon.get(i1);
      strokeSeg(p0,p1,strokethickness,color);}}
  
  void strokePolygon(KPolygon polygon,double strokethickness,Color color){
    DPolygon p=polygon.getDefaultPolygon2D();
    strokePolygon(p,strokethickness,color);}
  
  void strokePolygon(DPolygon polygon,double strokethickness,Color color){
    Path2D path=new Path2D.Double();
    int s=polygon.size();
    DPoint p=polygon.get(0);
    path.moveTo(p.x,p.y);
    for(int i=1;i<s;i++){
      p=polygon.get(i);
      path.lineTo(p.x,p.y);}
    path.closePath();
    graphics.setStroke(createStroke(strokethickness));
    graphics.setPaint(color);
    graphics.draw(path); }
  
  void fillPolygon(DPolygon polygon,Color c){
    Path2D path=new Path2D.Double();
    int s=polygon.size();
    DPoint p=polygon.get(0);
    path.moveTo(p.x,p.y);
    for(int i=1;i<s;i++){
      p=polygon.get(i);
      path.lineTo(p.x,p.y);}
    path.closePath();
    graphics.setPaint(c);
    graphics.fill(path); }
  
  void fillPolygon(KPolygon polygon,Color c){
    Path2D path=new Path2D.Double();
    int s=polygon.size();
    DPoint p=polygon.get(0).getBasicPoint2D();
    path.moveTo(p.x,p.y);
    for(int i=1;i<s;i++){
      p=polygon.get(i).getBasicPoint2D();
      path.lineTo(p.x,p.y);}
    path.closePath();
    graphics.setPaint(c);
    graphics.fill(path); }
  
  KPoint getRandomPoint(int range){
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
    return new KPoint(a,b,c,d);}
  
  KSeg getRandomSeg(int v0range,int lengthrange){
    KPoint v0=getRandomPoint(v0range);
    int[] b=GK.getLiberties(v0.getDog());
    Random r=new Random();
    int 
      dir=b[r.nextInt(b.length)],
      length=r.nextInt(lengthrange)+1;
    KPoint v1=GK.getVertex_Transitionswise(v0,dir,length);
    return new KSeg(v0,v1);}
  
  Stroke createStroke(double thickness){
    Stroke s=new BasicStroke((float)(thickness/imagescale),BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND,0,null,0);
    return s;}
  
  void strokeSeg(KPoint v0,KPoint v1,double thickness,Color color){
    DPoint 
      p0=v0.getBasicPoint2D(),
      p1=v1.getBasicPoint2D();
    strokeSeg(p0,p1,thickness,color);}
  
  void strokeSeg(DPoint p0,DPoint p1,double thickness,Color color){
    Path2D path=new Path2D.Double();
    path.moveTo(p0.x,p0.y);
    path.lineTo(p1.x,p1.y);
    graphics.setStroke(createStroke(thickness));
    graphics.setPaint(color);
    graphics.draw(path);}
  
   void renderSeg(KPoint v0,KPoint v1,double strokethickness,double dotspan,Color color){
    strokeSeg(v0,v1,strokethickness,color);
    renderPoint(v0,dotspan,color);
    renderPoint(v1,dotspan,color);}
  
   void renderSeg(KSeg s,double strokethickness,double dotspan,Color color){
    renderSeg(s.getVertex0(),s.getVertex1(),strokethickness,dotspan,color);}
  
   void strokeClock(KPoint v,double thickness,Color color){
    KPoint[] cp=getClockKPoints(v);
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
   * returns all involved points
   */
   Set<KPoint> strokeGrid(int range,double thickness,Color color){
     Set<KPoint> points=new HashSet<KPoint>();
     Set<KPoint> v0s=getV0s(range);
     for(KPoint p:v0s){
       points.addAll(Arrays.asList(getClockKPoints(p)));
       strokeClock(p,thickness,color);}
    return points;}
   
   /*
    * get all the v0s (centers of the clocks) out to a specified range
    */
   Set<KPoint> getV0s(int range){
     Set<KPoint> points=new HashSet<KPoint>();
     boolean valid;
     KPoint p;
     for(int ant=-range;ant<range;ant++){
       for(int bat=-range;bat<range;bat++){
         for(int cat=-range;cat<range;cat++){
           valid=(cat==bat-ant);
           if(valid){
             p=new KPoint(ant,bat,cat,0);
             points.add(p);}}}}
     return points;}
   
   List<GridTriangle> getGridTriangles(int range){
     List<GridTriangle> triangles=new ArrayList<GridTriangle>();
     Set<KPoint> v0s=getV0s(range);
     KPoint[] cp;
     for(KPoint p:v0s){
       cp=getClockKPoints(p);
       triangles.add(new GridTriangle(cp[0].getBasicPoint2D(),cp[1].getBasicPoint2D(),cp[2].getBasicPoint2D(),0));
       triangles.add(new GridTriangle(cp[0].getBasicPoint2D(),cp[2].getBasicPoint2D(),cp[3].getBasicPoint2D(),1));
       triangles.add(new GridTriangle(cp[0].getBasicPoint2D(),cp[3].getBasicPoint2D(),cp[4].getBasicPoint2D(),2));
       triangles.add(new GridTriangle(cp[0].getBasicPoint2D(),cp[4].getBasicPoint2D(),cp[5].getBasicPoint2D(),3));
       triangles.add(new GridTriangle(cp[0].getBasicPoint2D(),cp[5].getBasicPoint2D(),cp[6].getBasicPoint2D(),4));
       triangles.add(new GridTriangle(cp[0].getBasicPoint2D(),cp[6].getBasicPoint2D(),cp[7].getBasicPoint2D(),5));
       triangles.add(new GridTriangle(cp[0].getBasicPoint2D(),cp[7].getBasicPoint2D(),cp[8].getBasicPoint2D(),6));
       triangles.add(new GridTriangle(cp[0].getBasicPoint2D(),cp[8].getBasicPoint2D(),cp[9].getBasicPoint2D(),7));
       triangles.add(new GridTriangle(cp[0].getBasicPoint2D(),cp[9].getBasicPoint2D(),cp[10].getBasicPoint2D(),8));
       triangles.add(new GridTriangle(cp[0].getBasicPoint2D(),cp[10].getBasicPoint2D(),cp[11].getBasicPoint2D(),9));
       triangles.add(new GridTriangle(cp[0].getBasicPoint2D(),cp[11].getBasicPoint2D(),cp[12].getBasicPoint2D(),10));
       triangles.add(new GridTriangle(cp[0].getBasicPoint2D(),cp[12].getBasicPoint2D(),cp[1].getBasicPoint2D(),11));}
     return triangles;}
   
   List<GridHexagon> getGridHexagons(int range){
     List<GridHexagon> hexagons=new ArrayList<GridHexagon>();
     Set<KPoint> v0s=getV0s(range);
     KPoint[] cp;
     for(KPoint p:v0s){
       cp=getClockKPoints(p);
       hexagons.add(
         new GridHexagon(
           cp[1].getBasicPoint2D(),
           cp[3].getBasicPoint2D(),
           cp[5].getBasicPoint2D(),
           cp[7].getBasicPoint2D(),
           cp[9].getBasicPoint2D(),
           cp[11].getBasicPoint2D(),
           p));}
     return hexagons;}
   
  /*
   * 13 points
   * we treat v lke the center of a clock, disregard dog, branch from there.
   */
   KPoint[] getClockKPoints(KPoint v){
    int a=v.getAnt(),b=v.getBat(),c=v.getCat();
    KPoint[] w={ 
      new KPoint(a,b,c,0),//center
      new KPoint(a,b,c,2),//north
      new KPoint(a,b,c,3),//just cw of north
      new KPoint(a,b,c,4),
      new KPoint(a,b,c,5),
      new KPoint(a+1,b,c-1,2),
      new KPoint(a+1,b,c-1,1),
      new KPoint(a,b-1,c-1,4),
      new KPoint(a,b-1,c-1,3),
      new KPoint(a,b-1,c-1,2),
      new KPoint(a-1,b-1,c,5),
      new KPoint(a-1,b-1,c,4),
      new KPoint(a,b,c,1)};
    return w;}
  
   void renderPoint(KPoint v,double dotspan,Color color){
    DPoint p=v.getBasicPoint2D();
    renderPoint(p,dotspan,color);}
   
   void renderPoint(DPoint p,double dotspan,Color color){
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
  
  /*
   * render the coordinates as 4 numbers in a square
   * we gotta paint them onto their own image then paint the image, to get around the scale stuff
   */
   void renderPointCoors(KPoint v,int fontsize,Color color){
    DPoint p=v.getBasicPoint2D();
    AffineTransform graphicstransform=graphics.getTransform();
    double[] pt={p.x,p.y};
    graphicstransform.transform(pt,0,pt,0,1);
    graphics.setTransform(new AffineTransform());
    graphics.setPaint(color);
    graphics.setFont(new Font("Sans",Font.PLAIN,fontsize));
    String z=v.getAnt()+","+v.getBat()+",";
    graphics.drawString(z,(float)(pt[0]-11),(float)(pt[1]+1));
    z=v.getCat()+","+v.getDog();
    graphics.drawString(z,(float)(pt[0]-11),(float)(pt[1]+9));
    graphics.setTransform(graphicstransform);}
  
  /*
   * ################################
   * UI
   * ################################
   */
  
  private DGUI ui;
  
  private void initUI(){
    ui=new DGUI(this);
    ui.setBackground(Color.black);}
  
  /*
   * ################################
   * EXPORT
   * ################################
   */
  
//  private static final String EXPORTDIR="";
  
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
  
  void initImage(int width,int height,double scale,Color fill){
    imagewidth=width;
    imageheight=height;
    imagescale=scale;
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
  
}
