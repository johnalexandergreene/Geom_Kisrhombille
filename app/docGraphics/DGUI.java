package org.fleen.geom_Kisrhombille.app.docGraphics;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class DGUI extends JFrame{
  
  private static final long serialVersionUID=8413646909208773971L;

  /*
   * ################################
   * CONSTRUCTOR
   * ################################
   */
  
  DGUI(DocGraphics dg){
    this.dg=dg;
    setExtendedState(MAXIMIZED_BOTH);
    setVisible(true);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    addKeyListener(new KL0());}
  
  class KL0 extends KeyAdapter{
    public void keyTyped(KeyEvent e){
      if(e.getKeyChar()=='r')dg.regenerate();
      if(e.getKeyChar()=='e')dg.export();}}
  
  /*
   * ################################
   * DRIFTER
   * ################################
   */
  
  DocGraphics dg;
  
  /*
   * ################################
   * IMAGE
   * ################################
   */
  
  AffineTransform t=new AffineTransform();
  
  public void paint(Graphics g){
    if(dg.image!=null){
      Container c=getContentPane();
      int 
        xoff=(c.getWidth()-dg.imagewidth)/2,
        yoff=(c.getHeight()-dg.imageheight)/2;
      t.setToIdentity();
      t.translate(xoff,yoff);
      Graphics2D h=(Graphics2D)c.getGraphics();
      h.drawImage(dg.image,t,null);}}

}
