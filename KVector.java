package org.fleen.geom_Kisrhombille;


/*
 * diamond vector
 * direction and distance
 * direction is integer in range [0,11]. We have 12 directions in a diamond plane.
 * distance is double. 
 *    A sum of integers (1s and 2s) or integer multiples of sqrt3.
 *    Or some relative proportion thereof
 */
public class KVector{
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public KVector(){}
  
  public KVector(int direction,double distance){
    this.direction=direction;
    this.distance=distance;}
  
  /*
   * ################################
   * GEOMETRY
   * ################################
   */
  
  public int direction;
  public double distance;
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  public boolean equals(Object a){
    KVector b=(KVector)a;
    return direction==b.direction&&distance==b.distance;}
  
  public int hashCode(){
    int a=direction*65536+(int)distance;
    return a;}
  
  public String toString(){
    String s="["+direction+","+distance+"]";
    return s;}
  
}
