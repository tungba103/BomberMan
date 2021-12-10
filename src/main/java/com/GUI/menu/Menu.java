package com.GUI.menu;
import com.GUI.Frame;
import javax.swing.JMenuBar;


public class Menu extends JMenuBar {

  public Menu(Frame frame) {
    add( new Game(frame) );
    add( new Option(frame) );
    add( new Help(frame) );
  }

}
