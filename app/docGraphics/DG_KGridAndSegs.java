package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.List;
import java.util.Set;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.GD;
import org.fleen.geom_Kisrhombille.KPoint;
import org.fleen.geom_Kisrhombille.KSeg;

/*
 * some segs on a kgrid. no coordinates.
 */
public class DG_KGridAndSegs extends DocGraphics{
  
  void doGraphics(){
    //do the grid
    initImage(IMAGEWIDTH4,IMAGEHEIGHT4,IMAGESCALE2,WHITE);
    Set<KPoint> points=strokeGrid(8,STROKETHICKNESS2,GREY6);
    for(KPoint p:points)
      renderPoint(p,DOTSPAN1,GREY6);
    //do 3 segs
    List<KSeg> segs=null;
    while(segs==null||segsIntersect(segs))
      segs=getSegs(6,2,5);
    //render segs
    for(KSeg s:segs)
      renderSeg(s,STROKETHICKNESS2,DOTSPAN1,GREEN);
//    renderPoint(new KVertex(-3,-4,-1,5));
//    renderPoint(new KVertex(-1,-2,-1,4));
//    renderPoint(new KVertex(1,1,0,0));
    }
  
  private void renderPoint(KPoint p){
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
    new DG_KGridAndSegs();}

}
