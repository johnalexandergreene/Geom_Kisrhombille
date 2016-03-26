package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.KGrid;

/*
 * for documentation and such
 */
public class DocGraphics{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  DocGraphics(){
    initUI();
    initGrid();
    initImage();}
  
  /*
   * ################################
   * UI
   * ################################
   */
  
  DGUI ui;
  
  private void initUI(){
    ui=new DGUI(this);}
  
  /*
   * ################################
   * GRID
   * ################################
   */
  
  KGrid grid;
//  private static final double FISH=60;
  private static final double FISH=30;
  
  private void initGrid(){
    Container c=ui.getContentPane();
    double w=c.getWidth(),h=c.getHeight();
    grid=new KGrid(w/2,h/2,0,true,FISH);}
  
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
  
  BufferedImage image;
  
  private static final Color 
    COLOR0=new Color(64,192,64),//green
    COLOR1=new Color(192,64,64),//red
    COLOR2=new Color(64,64,64),//black
    COLOR3=new Color(255,255,128);//yellow
  
  private static final double 
    DOTSPAN0=8,
    DOTSPAN1=12,
    DOTSPAN2=44;
  
  private void initImage(){
    Container c=ui.getContentPane();
    int w=c.getWidth(),h=c.getHeight();
    image=new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
    Graphics2D graphics=image.createGraphics();
    graphics.setRenderingHints(RENDERING_HINTS);
    graphics.setPaint(new Color(255,255,255));
    graphics.fillRect(0,0,w,h);
    //
    
    List<HexClock> clocks=getHexClocks(8);
    
    
    
    //stroke hexagon
    graphics.setPaint(COLOR0);
    BasicStroke s=new BasicStroke(4f,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND,0,null,0);
    graphics.setStroke(s);
    Path2D path;
    for(HexClock k:clocks){
      path=k.getHexagon().getPath2D();
      graphics.draw(path);}
    
//    //fill hexagon
//    int a;
//    for(HexClock k:clocks){
//      a=k.cant%3;
//      if(a==0){
//        graphics.setPaint(new Color(255,0,0,128));
//      }else if(a==1){
//        graphics.setPaint(new Color(255,255,0,128));
//      }else{
//        graphics.setPaint(new Color(0,0,255,128));
//      }
//      
//      path=k.getHexagon().getPath2D();
//      graphics.fill(path);}
    
    
    //fill 6 triangles
//    for(HexClock k:clocks){
//      fillSkinnyTriangle(graphics,k,3);
//      fillSkinnyTriangle(graphics,k,4);
//      fillSkinnyTriangle(graphics,k,5);
//      fillSkinnyTriangle(graphics,k,6);
//      
//      }
    
    //stroke triangles
//    graphics.setPaint(COLOR0);
//    Stroke s=new BasicStroke(4f,BasicStroke.CAP_SQUARE,BasicStroke.JOIN_ROUND,0,null,0);
//    graphics.setStroke(s);
//    for(HexClock k:clocks)
//      for(DPolygon triangle:k.getTriangles()){
//        path=triangle.getPath2D();
//        graphics.draw(path);}
    
    
//    //stroke 6 triangles
//    for(HexClock k:clocks){
//      drawSkinnyTriangle(graphics,k,3);
//      drawSkinnyTriangle(graphics,k,4);
//      drawSkinnyTriangle(graphics,k,5);
//      drawSkinnyTriangle(graphics,k,6);
//      
//      }
    
////    paint dots on clock vertices
//    graphics.setPaint(COLOR2);
//    Ellipse2D dot=new Ellipse2D.Double();
//    for(HexClock k:clocks)
//      for(DPoint p:k.getClockPoints()){
//        dot.setFrame(p.x-DOTSPAN0/2,p.y-DOTSPAN0/2,DOTSPAN0,DOTSPAN0);
//        graphics.fill(dot);}
    
    
    
    
    //paint dots on clock 6 vertices
//    for(HexClock k:clocks){
//      renderVertexDot(graphics,k,0,"0");
//      renderVertexDot(graphics,k,8,"1");
//      renderVertexDot(graphics,k,7,"2");
//      renderVertexDot(graphics,k,6,"3");
//      renderVertexDot(graphics,k,5,"4");
//      renderVertexDot(graphics,k,4,"5");
//      renderVertexDot(graphics,k,0,null);
//      renderVertexDot(graphics,k,8,null);
//      renderVertexDot(graphics,k,7,null);
//      renderVertexDot(graphics,k,6,null);
//      renderVertexDot(graphics,k,5,null);
//      renderVertexDot(graphics,k,4,null);
//      }
    
    
    
    
    
    //do hexagon coors
//    graphics.setPaint(new Color(64,64,64));
//    graphics.setFont(new Font("Sans",Font.PLAIN,16));
//    String coors;
//    DPoint center;
//    for(HexClock k:clocks){
//      center=k.getPoint(0);
//      coors=k.cant+","+k.cbat+","+k.ccat;
//      graphics.drawString(coors,(float)center.x-21,(float)center.y+7);}
    //
    ui.repaint();}
  
  private void renderVertexDot(Graphics2D g,HexClock clock,int i,String text){
    Ellipse2D dot=new Ellipse2D.Double();
    DPoint[] clockpoints=clock.getClockPoints();
    DPoint p=clockpoints[i];
    dot.setFrame(p.x-DOTSPAN1/2,p.y-DOTSPAN1/2,DOTSPAN1,DOTSPAN1);
    g.setPaint(COLOR2);
    g.fill(dot);
//    g.setPaint(COLOR2);
//    g.draw(dot);
    //
    if(text!=null){
      g.setPaint(COLOR1);
      g.setFont(new Font("Sans",Font.PLAIN,17));
      String z=clock.cant+","+clock.cbat+",";
      g.drawString(z,(float)p.x-13,(float)p.y+1);
      z=clock.ccat+","+text;
      g.drawString(z,(float)p.x-13,(float)p.y+15);
    }}
  
  private void fillSkinnyTriangle(Graphics2D g,HexClock clock,int i){
    List<DPolygon> triangles=clock.getTriangles();
    DPolygon t=triangles.get(i);
    g.setPaint(COLOR3);
    g.fill(t.getPath2D());
    g.setPaint(COLOR1);
    g.draw(t.getPath2D());}
  
  private void drawSkinnyTriangle(Graphics2D g,HexClock clock,int i){
    List<DPolygon> triangles=clock.getTriangles();
    DPolygon t=triangles.get(i);
    g.setPaint(COLOR1);
    g.draw(t.getPath2D());}
  
  /*
   * ################################
   * HEXCLOCKS
   * ################################
   */
  
  private List<HexClock> getHexClocks(int range){
    List<HexClock> clocks=new ArrayList<HexClock>();
    HexClock clock;
    //
    if(range==0){
      clock=new HexClock(grid,0,0,0);
      clocks.add(clock);
      return clocks;}
    //
    for(int ant=-range;ant<range;ant++){
      for(int bat=-range;bat<range;bat++){
        for(int cat=-range;cat<range;cat++){
          clock=new HexClock(grid,ant,bat,cat);
          if(clock.valid)
            clocks.add(clock);}}}
    return clocks;}
  
  /*
   * ################################
   * MAIN
   * ################################
   */
  
  public static final void main(String[] a){
    DocGraphics ckgi=new DocGraphics();
    
    
  }
  
  
  

}
