package org.fleen.geom_Kisrhombille;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * A 2d area
 * outside edge is defined by a polygon
 * inside edge/s (holes) are defined by 1..n polygons
 * 
 * It's a list of polygons
 * the first polygon is the outer edge
 * the rest of the polygons (if any) are the holes
 */
public class KYard extends ArrayList<KPolygon>{

  private static final long serialVersionUID=-6074974764136594169L;
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public KYard(KPolygon... polygons){
    super(Arrays.asList(polygons));}
  
  public KYard(List<KPolygon> polygons){
    super(polygons);}
  
  public KYard(int s){
    super(s);}
  
  /*
   * ################################
   * EDGE POLYGONS
   * ################################
   */
  
  /*
   * returns the outer edge of this yard
   * returns null if there is no outer edge
   */
  public KPolygon getOuterEdge(){
    if(isEmpty())return null;
    return get(0);}
  
  /*
   * returns the inner edges of this yard
   * returns empty list if there are no inner edges
   */
  public List<KPolygon> getInnerEdges(){
    if(size()<2)return new ArrayList<KPolygon>(0);
    return subList(1,size());}

}
