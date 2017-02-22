package org.fleen.geom_Kisrhombille.app.docGraphics;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.KVertex;

@SuppressWarnings("serial")
public class GridHexagon extends DPolygon{
  
  public GridHexagon(DPoint p0,DPoint p1,DPoint p2,DPoint p3,DPoint p4,DPoint p5, KVertex center){
    super(p0,p1,p2,p3,p4,p5);
    this.center=center;}
  
  KVertex center;

}
