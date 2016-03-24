package org.fleen.geom_Kisrhombille;

import java.io.Serializable;


/*
 * Vector used in general polygon model
 * direction is integer in range [-5,5]. It describes a direction relative to another direction, 0 being equal.
 * relativeinterval is double. It describes an interval in terms of a base interval.
 */
public class KMetagonVector implements Serializable{
  
  private static final long serialVersionUID=1794434316990821717L;
  
  public int directiondelta;
  public double relativeinterval;

  /*
   * ################################
   * CONSTRUCTORS
   * ################################
   */
  
  public KMetagonVector(int directiondelta,double relativeinterval){
    this.directiondelta=directiondelta;
    this.relativeinterval=relativeinterval;}
  
  public KMetagonVector(){}
 
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  private static final double RELATIVEINTERVALEQUALITYERROR=0.000001;
  
  public boolean equals(Object a){
    KMetagonVector b=(KMetagonVector)a;
    boolean e=directiondelta==b.directiondelta;
    if(!e)return false;
    e=equals(relativeinterval,b.relativeinterval,RELATIVEINTERVALEQUALITYERROR);
    return e;}
  
  //simplistic, yes. we eschew employing floats etc
  public int hashCode(){
    return directiondelta*7919;}
  
  public String toString(){
    String s="["+directiondelta+","+relativeinterval+"]";
    return s;}
  
  public Object clone(){
    return new KMetagonVector(directiondelta,relativeinterval);}
  
  private boolean equals(double a,double b,double error){
    if(a<b){
      return b-a<error;
    }else{
      return a-b<error;}}
  
}
