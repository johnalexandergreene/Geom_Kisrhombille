package org.fleen.geom_Kisrhombille.app.docGraphics;

import org.fleen.geom_2D.DPoint;
import org.fleen.geom_2D.DPolygon;
import org.fleen.geom_Kisrhombille.KPoint;

@SuppressWarnings("serial")
public class GridTriangle extends DPolygon{
  
  public GridTriangle(DPoint p0,DPoint p1,DPoint p2,int index){
    super(p0,p1,p2);
    this.index=index;}
  
  int index;

}
