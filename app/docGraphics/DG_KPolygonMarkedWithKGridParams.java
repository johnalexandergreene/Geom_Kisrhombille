package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_2D.DVector;
import org.fleen.geom_Kisrhombille.KPolygon;
import org.fleen.geom_Kisrhombille.KPoint;

public class DG_KPolygonMarkedWithKGridParams extends DocGraphics{

  void doGraphics(){
    initImage(IMAGEWIDTH3,IMAGEHEIGHT3,IMAGESCALE3,WHITE);
//    strokeGrid(8,STROKETHICKNESS1,GREY6);
    
    Set<KPoint> points=strokeGrid(8,STROKETHICKNESS1,GREY6);
//    for(KVertex p:points)
//      renderPointCoors(p,10,RED);
    
    //render a triangle, T and hexagon
    KPolygon ptriangle=new KPolygon(new KPoint(-3,-4,-1,0),new KPoint(-3,-2,1,0),new KPoint(-1,-2,-1,0));
    renderPolygonWithKGridParamMarks(ptriangle,STROKETHICKNESS3,DOTSPAN2,GREEN);
    
    
  }
  
  void renderPolygonWithKGridParamMarks(KPolygon polygon,double strokethickness,double dotspan,Color color){
    renderPolygon(polygon,strokethickness,dotspan,color);
    DPolygon dp0=polygon.getDefaultPolygon2D();
    List<DVector> vouter=dp0.getInnerOuterPolygonVectors(-INNEROUTERPOLYGONOFFSET0/imagescale);
    DPoint 
      v0glyphcenter=dp0.get(0).getPoint(vouter.get(0)),
      v1glyphcenter=dp0.get(1).getPoint(vouter.get(1));
    
    renderVertexNameGlyph(dp0.get(0),BLACK,33,-44,"P0");
    
    
  }
  
  void renderVertexNameGlyph(DPoint p,Color color,int fontsize,int offset,String nameglyph){
    AffineTransform graphicstransform=graphics.getTransform();
    double[] pt={p.x,p.y};
    graphicstransform.transform(pt,0,pt,0,1);
    graphics.setTransform(new AffineTransform());
    graphics.setPaint(color);
    graphics.setFont(new Font("Sans",Font.PLAIN,fontsize));
    graphics.drawString(nameglyph,(float)(pt[0]+offset),(float)(pt[1]));
    graphics.setTransform(graphicstransform);
  }
  
  public static final void main(String[] a){
    new DG_KPolygonMarkedWithKGridParams();}
  
  

}
