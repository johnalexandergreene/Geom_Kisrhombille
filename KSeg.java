package org.fleen.geom_Kisrhombille;


/*
 * ordered : (v4,v6),(v6,v12) or (v12,v4)
 */
public class KSeg{
  
  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  public KSeg(KVertex a,KVertex b){
    //put the vertices in the proper order
    int gta=a.getGeneralType(),gtb=b.getGeneralType();
    if(gta==GK.VERTEX_GTYPE_4){
      if(gtb==GK.VERTEX_GTYPE_6){
        vertex0=a;
        vertex1=b;
      }else{//gtb==MathDiamond.VERTEX_GTYPE_12
        vertex0=b;
        vertex1=a;}
    }else if(gta==GK.VERTEX_GTYPE_6){
      if(gtb==GK.VERTEX_GTYPE_12){
        vertex0=a;
        vertex1=b;
      }else{//gtb==MathDiamond.VERTEX_GTYPE_4
        vertex0=b;
        vertex1=a;}
    }else{//gta==MathDiamond.VERTEX_GTYPE_12
      if(gtb==GK.VERTEX_GTYPE_4){
        vertex0=a;
        vertex1=b;
      }else{//gtb==MathDiamond.VERTEX_GTYPE_6
        vertex0=b;
        vertex1=a;}}}
  
  /*
   * ################################
   * VERTICES
   * ################################
   */
  
  private KVertex vertex0,vertex1;
  
  public KVertex getVertex0(){
    return vertex0;}
  
  public KVertex getVertex1(){
    return vertex1;}
  
  /*
   * ################################
   * OBJECT
   * ################################
   */
  
  private Integer hashcode=null;
  
  public int hashCode(){
    if(hashcode==null){
      hashcode=
        vertex0.coors[0]*65536+
        vertex0.coors[1]*32668+
        vertex0.coors[2]*16384+
        vertex0.coors[3]*16+
        vertex1.coors[0]*8192+
        vertex1.coors[1]*4096+
        vertex1.coors[2]*2048+
        vertex1.coors[3];}
    return hashcode;}
  
  private KSeg equals_a;
  
  public boolean equals(Object a){
    equals_a=(KSeg)a;
    if(equals_a.hashCode()==hashCode()){
      return 
        equals_a.vertex0.coors[0]==vertex0.coors[0]&&
        equals_a.vertex0.coors[1]==vertex0.coors[1]&&
        equals_a.vertex0.coors[2]==vertex0.coors[2]&&
        equals_a.vertex0.coors[3]==vertex0.coors[3]&&
        equals_a.vertex1.coors[0]==vertex1.coors[0]&&
        equals_a.vertex1.coors[1]==vertex1.coors[1]&&
        equals_a.vertex1.coors[2]==vertex1.coors[2]&&
        equals_a.vertex1.coors[3]==vertex1.coors[3];
    }else{
      return false;}}
  
  public String toString(){
    return "["+vertex0+","+vertex1+"]";}
  
}
