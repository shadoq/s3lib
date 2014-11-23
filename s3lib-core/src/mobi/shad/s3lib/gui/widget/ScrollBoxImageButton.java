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

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gui.Gui;
import mobi.shad.s3lib.gui.GuiResource;

import java.util.Vector;

/**
 * @author Jarek
 */
public class ScrollBoxImageButton implements WidgetInterface{

	private Vector<String> items = new Vector<String>();
	private Vector<Button> buttonAct = new Vector<Button>();
	private float buttonIdx = 0;
	private ChangeListener localChangeListener = null;
	private boolean userChange = true;
	private Table window = null;
	private int value = 0;
	private int width = 0;
	private int height = 0;
	private int buttonX = 0;
	private int buttonSize = 0;

	/**
	 *
	 */
	@Override
	public void show(){
	}

	/**
	 * @param buttons
	 * @param vertical
	 * @param localChangeListener
	 */
	public void show(String[] buttons, int value, int height, int width, int buttonSize, int buttonX, final ChangeListener changeListener){

		this.localChangeListener = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				if (userChange){
					if (changeListener != null){
						changeListener.changed(event, actor);
					}
				}
			}
		};

		this.value = value;
		this.width = width;
		this.height = height;
		this.buttonX = buttonX;
		this.buttonSize = buttonSize;

		items = new Vector<String>();

		int count = buttons.length;
		for (int i = 0; i < count; i++){
			items.add(buttons[i]);
		}

	}

	/**
	 * @return
	 */
	public Table getTable(){

		Gui gui = new Gui();
		buttonAct = new Vector<Button>();

		gui.row();
		int j = 0;
		boolean newRow = false;
		for (int i = 0; i < items.size(); i++){
			newRow = false;
			if (j > buttonX){
				newRow = true;
				j = 0;
			}
			Button addImgButton =
			gui.addImgButton(items.get(i), this, "buttonIdx", i, newRow, 1, true, buttonSize, "btn" + this.toString(), localChangeListener);
			if (i == value){
				addImgButton.setChecked(true);
			}
			buttonAct.add(i, addImgButton);
			j++;
		}

		Table table = gui.getTable();
		ScrollPane scrollPane = GuiResource.scrollPane(table, "scroll");
		scrollPane.setFadeScrollBars(true);
		scrollPane.setClamp(true);
		scrollPane.setFlickScroll(true);

		scrollPane.setScrollingDisabled(true, false);

		window = GuiResource.table("window");
		window.row();
		window.add(scrollPane).width(width).height(height);
		window.row();

		return window;
	}

	/**
	 * @return
	 */
	public int getValue(){
		return (int) buttonIdx;
	}

	/**
	 * @param value
	 */
	public void setValue(int value){
		userChange = false;
		this.value = value;
		if (buttonAct != null){
			if (value < buttonAct.size()){
				for (int i = 0; i < buttonAct.size(); i++){
					buttonAct.get(value).setChecked(false);
				}
				buttonAct.get(value).setChecked(true);
			}
		}
		userChange = true;
	}

	@Override
	public void hide(){
	}
}
