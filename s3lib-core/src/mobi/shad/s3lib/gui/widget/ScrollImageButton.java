/*******************************************************************************
 * Copyright 2013
 *
 * Jaroslaw Czub
 * http://shad.mobi
 *
 /*******************************************************************************
 * Copyright 2013
 *
 * Jaroslaw Czub
 * http://shad.mobi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ******************************************************************************/
package mobi.shad.s3lib.gui.widget;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.Vector;
import mobi.shad.s3lib.gui.Gui;
import mobi.shad.s3lib.gui.GuiResource;

/**
 *
 * @author Jarek
 */
public class ScrollImageButton implements WidgetInterface
{

private Vector<String> items=new Vector<String>();
private Vector<Button> buttonAct=new Vector<Button>();
private int value=0;

private float buttonIdx=0;
private ChangeListener changeListener=null;
private boolean vertical=false;
private Table window=null;
private int buttonSize=0;
/**
 *
 */
  @Override
  public void show(){
  show(new String[]{}, false, 0, 0, null);
}

/**
 * 
 * @param buttons
 * @param vertical
 * @param changeListener 
 */
public void show(String[] buttons, boolean vertical, int value, int buttonSize, ChangeListener changeListener){

  this.changeListener=changeListener;
  this.vertical=vertical;
  this.buttonSize=buttonSize;
  this.value=value;

  items=new Vector<String>();

  int count=buttons.length;
  for (int i=0; i < count; i++){
	items.add(buttons[i]);
  }

}

/**
 * 
 * @return 
 */
public Table getTable(){

  Gui gui=new Gui();
  buttonAct=new Vector<Button>();

  gui.row();
  for (int i=0; i < items.size(); i++){
	Button addImgButton=gui.addImgButton(items.get(i), this, "buttonIdx", i, vertical, 1, true, buttonSize, "btn" + this.toString(), changeListener);
	if (i == value){
	  addImgButton.setChecked(true);
	}
	buttonAct.add(i, addImgButton);	
  }
  if (!vertical){
	gui.row();
	gui.addLabel("", true, items.size());
  }

  Table table=gui.getTable();
  table.row();
  table.add(" ").colspan(items.size());

  ScrollPane scrollPane= GuiResource.scrollPane(table, "scroll");
  scrollPane.setFadeScrollBars(true);
  scrollPane.setClamp(true);
  scrollPane.setFlickScroll(true);
  
  if (!vertical){
	scrollPane.setScrollingDisabled(false, true);
  } else {
	scrollPane.setScrollingDisabled(true, false);
  }

  window= GuiResource.table("window");
  window.row();
  window.add(scrollPane);
  window.row();

  return window;
}

/**
 *
 * @return
 */
public int getValue(){
  return (int) buttonIdx;
}

/**
 * 
 * @param value 
 */
public void setValue(int value){
  this.value=value;
  if (buttonAct!=null){
	if (value<buttonAct.size()){
	  buttonAct.get(value).setChecked(true);
	}
  }
}

  @Override
  public void hide(){
  }
}
