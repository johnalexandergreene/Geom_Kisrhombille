package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.fleen.geom_2D.GD;

public class DGUI extends JFrame{
  
  private static final long serialVersionUID=8413646909208773971L;

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  DGUI(DocGraphics ckgi){
    this.ckgi=ckgi;;
    setBounds(400,50,1000,800);
    setVisible(true);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);}
  
  /*
   * ################################
   * DRIFTER
   * ################################
   */
  
  DocGraphics ckgi;
  
  /*
   * ################################
   * IMAGE
   * ################################
   */
  
  public void paint(Graphics g){
    if(ckgi.image!=null){
      Graphics2D h=(Graphics2D)getContentPane().getGraphics();
      h.drawImage(ckgi.image,null,null);}}

}
