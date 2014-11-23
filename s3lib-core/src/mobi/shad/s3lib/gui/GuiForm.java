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
package mobi.shad.s3lib.gui;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlWriter;
import mobi.shad.s3lib.gfx.util.ColorUtil;
import mobi.shad.s3lib.main.*;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Class to create dynamic form. Access to variables is implemented with Java reflection API
 *
 * @author Jarek
 */
public class GuiForm{

	private static final String TAG = GuiForm.class.getSimpleName();
	private static LinkedHashMap<String, Element> globalElemets = new LinkedHashMap<String, Element>(50);
	private float fadeDuration = 0.3f;
	private Table guiTable;
	;
	private LinkedHashMap<String, Element> elemets = new LinkedHashMap<String, Element>(10);
	private LinkedHashMap<String, Actor> guiEelemets = new LinkedHashMap<String, Actor>(10);
	private String presentDir = "present/";
	private Object className = "";

	public static enum ValueType{

		FLOAT_SLIDER, BUTTON_RANDOM, BUTTON_RESET, BUTTON_GROUP, CHECKBOX, FILE_BROWSER, LIST_INDEX, SELECT_INDEX, SELECT_COLOR_INDEX, SELECT_STRING,
		COLOR_SELECT, TEXT_FIELD, SIZE2D_EDITOR, IMAGE_LIST, PRESENT_READ, PRESENT_SAVE, LABEL
	}

	private class Element{

		public String name = "";
		public String label = "";
		public float valueFloat = 0;
		public float valueFloatMin = 0f;
		public float valueFloatMax = 1f;
		public float valueFloatStep = 0.1f;
		public float valueFloatStart = 0;
		public String valueString = "";
		public String valueStringStart = "";
		public boolean valueBoolean = false;
		public String[] valueArrayString;
		public Color valueColor = Color.WHITE;
		public ValueType type = ValueType.FLOAT_SLIDER;
		public ChangeListener changeListener = null;
		public float valueX = 0;
		public float valueY = 0;
		public float valueWidth = 0;
		public float valueHeight = 0;
		public Actor actor = null;
	}

	public GuiForm(String className){
		this.className = className.toLowerCase().trim();
		elemets = new LinkedHashMap<String, Element>(10);
		guiEelemets = new LinkedHashMap<String, Actor>(10);
	}

	public static void setGlobalPresent(final String[][] values){
		for (String[] strings : values){
			if (strings != null){
				if (!strings[0].equalsIgnoreCase("") && !strings[1].equalsIgnoreCase("")){
					if (globalElemets.containsKey(strings[0])){
						S3Log.info(TAG, "Set global present: key: " + strings[0] + " value: " + strings[1]);
						Element element = globalElemets.get(strings[0]);

						switch (element.type){
							case FLOAT_SLIDER:
								element.valueFloat = Float.parseFloat(strings[1]);
								if (element.actor instanceof Slider){
									((Slider) element.actor).setValue(element.valueFloat);
								}
								break;
							case SELECT_INDEX:
								element.valueFloat = Integer.parseInt(strings[1]);
								if (element.actor instanceof SelectBox){
									((SelectBox) element.actor).getSelection().set((int) element.valueFloat);
								}
								break;
						}
					}
				}
			}

		}
	}

	/**
	 *
	 */
	public void clean(){
		elemets = new LinkedHashMap<String, Element>(10);
		guiEelemets = new LinkedHashMap<String, Actor>(10);
		globalElemets = new LinkedHashMap<String, Element>(10);
	}

	/**
	 * @param name
	 * @param label
	 * @param start
	 * @param min
	 * @param max
	 * @param step
	 * @param changeListener
	 */
	public void add(String name, String label, float start, float min, float max, float step, ChangeListener changeListener){

		S3Log.debug(TAG, "Add slider ... name: " + name + " start: " + start + " min: " + min + " max: " + max
		+ " step: " + step);
		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueFloat = start;
		el.valueFloatStart = start;
		el.valueFloatMin = min;
		el.valueFloatMax = max;
		el.valueFloatStep = step;
		el.changeListener = changeListener;
		el.type = ValueType.FLOAT_SLIDER;
		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	public void addFileBrowser(String name, String label, String value, String directory, final ChangeListener changeListener){

		S3Log.debug(TAG, "Add FileBrowser ... name: " + name + " value: " + value + " directory: " + directory);

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueString = value;
		el.valueStringStart = directory;
		el.changeListener = changeListener;
		el.type = ValueType.FILE_BROWSER;
		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	public void addTextField(String name, String label, String value, String messageText, final ChangeListener changeListener){

		S3Log.debug(TAG, "Add TextField ... name: " + name + " value: " + value);

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueString = value;
		el.valueStringStart = messageText;
		el.changeListener = changeListener;
		el.type = ValueType.TEXT_FIELD;
		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	public void addSelectIndex(String name, String label, int value, String[] items, final ChangeListener changeListener){

		S3Log.debug(TAG, "Add SelectIndex ... name: " + name + " value: " + value);

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueFloat = value;
		el.valueArrayString = items;
		el.changeListener = changeListener;
		el.type = ValueType.SELECT_INDEX;
		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	public void addListIndex(String name, String label, int value, String[] items, final ChangeListener changeListener){

		S3Log.debug(TAG, "Add ListIndex ... name: " + name + " value: " + value);

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueFloat = value;
		el.valueArrayString = items;
		el.changeListener = changeListener;
		el.type = ValueType.LIST_INDEX;
		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	public void addColorList(String name, String label, final ChangeListener changeListener){

		S3Log.debug(TAG, "Add ColorList ... name: " + name);

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueFloat = 0;
		el.changeListener = changeListener;
		el.type = ValueType.SELECT_COLOR_INDEX;
		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	public void addColorSelect(String name, String label, Color value, final ChangeListener changeListener){

		S3Log.debug(TAG, "Add ColorSelect ... name: " + name + " value: " + value);

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueColor = value;
		el.changeListener = changeListener;
		el.type = ValueType.COLOR_SELECT;
		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	public void addButtonGroup(String name, String label, final String[] values, final ChangeListener changeListener){

		S3Log.debug(TAG, "Add ButtonGroup ... name: " + name);

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueArrayString = values;
		el.changeListener = changeListener;
		el.type = ValueType.BUTTON_GROUP;
		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	/**
	 * @param name
	 * @param label
	 * @param values
	 * @param changeListener
	 */
	public void addRandomButton(String name, String label, final String[] values, final ChangeListener changeListener){

		S3Log.debug(TAG, "Add RandomButton ... name: " + name);

		ChangeListener localChangeListener = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				if (S3Constans.DEBUG){
					S3Log.log("S3GuiForm::RandomButtonChange", "", 2);
				}

				Gui.disableListener = true;
				for (int i = 0; i < values.length; i++){
					String key = values[i];
					Element get = elemets.get(key);

					switch (get.type){
						default:
							break;
						case FLOAT_SLIDER:
							get.valueFloat =
							(float) (get.valueFloatMin + (get.valueFloatMax / 2f) + (Math.random() * ((get.valueFloatMax - get.valueFloatMin) / 2f)));
							if (guiEelemets.containsKey(key)){
								((Slider) guiEelemets.get(key)).setValue(get.valueFloat);
							}
							break;
					}

				}
				Gui.disableListener = false;
				if (changeListener != null){
					changeListener.changed(event, actor);
				}
			}
		};

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueArrayString = values;
		el.changeListener = localChangeListener;
		el.type = ValueType.BUTTON_RANDOM;
		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	/**
	 * @param name
	 * @param label
	 * @param values
	 * @param changeListener
	 */
	public void addResetButton(String name, String label, final String[] values, final ChangeListener changeListener){

		S3Log.debug(TAG, "Add ResetButton ... name: " + name);

		ChangeListener localChangeListener = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				if (S3Constans.DEBUG){
					S3Log.log("S3GuiForm::RandomButtonChange", "", 2);
				}

				Gui.disableListener = true;
				for (int i = 0; i < values.length; i++){
					String key = values[i];
					Element get = elemets.get(key);

					switch (get.type){
						default:
							break;
						case FLOAT_SLIDER:
							get.valueFloat = (float) (get.valueFloatStart);
							if (guiEelemets.containsKey(key)){
								((Slider) guiEelemets.get(key)).setValue(get.valueFloat);
							}
							break;
					}

				}
				Gui.disableListener = false;
				if (changeListener != null){
					changeListener.changed(event, actor);
				}
			}
		};

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueArrayString = values;
		el.changeListener = localChangeListener;
		el.type = ValueType.BUTTON_RESET;
		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	/**
	 * @param name
	 * @param label
	 * @param changeListener
	 */
	public void addSavePresentButton(String name, String label, final ChangeListener changeListener){

		S3Log.debug(TAG, "Add SavePresentButton ... name: " + name);

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.changeListener = changeListener;
		el.type = ValueType.PRESENT_SAVE;
		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	/**
	 * @param name
	 * @param label
	 * @param changeListener
	 */
	public void addReadPresentButton(String name, String label, final ChangeListener changeListener){

		S3Log.debug(TAG, "Add ReadPresentButton ... name: " + name);

		final Element el = new Element();

		ChangeListener outListener = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				el.valueFloat = (int) el.valueFloat - 1;
				if (el.valueFloat < 0){
					return;
				}
				guiTable = null;
				readPresent((int) el.valueFloat);
				if (changeListener != null){
					changeListener.changed(event, actor);
				}
			}
		};

		el.name = name;
		el.label = label;
		el.changeListener = outListener;
		el.valueFloat = 0;
		el.valueArrayString = readPresentList();
		el.type = ValueType.PRESENT_READ;
		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	/**
	 * @param name
	 * @param label
	 * @param value
	 * @param changeListener
	 */
	public void addCheckBox(String name, String label, boolean value, final ChangeListener changeListener){

		S3Log.debug(TAG, "Add CheckBox ... name: " + name + " value: " + value);

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueFloat = 0;
		el.valueBoolean = value;
		el.changeListener = changeListener;
		el.type = ValueType.CHECKBOX;
		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	/**
	 * @param name
	 * @param label
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param changeListener
	 */
	public void addEditorBox(String name, String label, float x, float y, float width, float height, final ChangeListener changeListener){

		S3Log.debug(TAG, "Add EditorBox ... name: " + name + " value: " + x + ":" + y + " " + width + ":" + height);

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueX = x;
		el.valueY = y;
		el.valueWidth = width;
		el.valueHeight = height;
		el.changeListener = changeListener;
		el.type = ValueType.SIZE2D_EDITOR;
		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	/**
	 * @param name
	 * @param label
	 * @param values
	 * @param changeListener
	 */
	public void addImageList(String name, String label, final String[] values, final ChangeListener changeListener){

		S3Log.debug(TAG, "Add ImageList ... name: " + name + " label: " + label);

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueArrayString = values;
		el.changeListener = changeListener;
		el.type = ValueType.IMAGE_LIST;
		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	/**
	 * @param name
	 * @param label
	 * @param color
	 */
	public void addLabel(String name, String label, Color color){

		S3Log.debug(TAG, "Add Label ... name: " + name);

		Element el = new Element();
		el.name = name;
		el.label = label;
		el.valueColor = color;
		el.type = ValueType.LABEL;
		elemets.put(name, el);
		globalElemets.put(name, el);
	}

	/**
	 * @param name
	 * @param value
	 */
	public void set(String name, float value){
		elemets.get(name).valueFloat = value;
	}

	/**
	 * @param name
	 * @param value
	 */
	public void set(String name, boolean value){
		elemets.get(name).valueBoolean = value;
	}

	/**
	 * @param name
	 * @param value
	 */
	public void set(String name, int value){
		elemets.get(name).valueFloat = (int) value;
	}

	/**
	 * @param name
	 * @param value
	 */
	public void set(String name, Color value){
		elemets.get(name).valueColor = value;
	}

	/**
	 * @param name
	 * @param value
	 */
	public void set(String name, String value){
		elemets.get(name).valueString = value;
	}

	/**
	 * @param name
	 * @return
	 */
	public float getFloat(String name){
		if (elemets.containsKey(name)){
			return elemets.get(name).valueFloat;
		}
		return 0;
	}

	/**
	 * @param name
	 * @return
	 */
	public int getInt(String name){
		if (elemets.containsKey(name)){
			return (int) elemets.get(name).valueFloat;
		}
		return 0;
	}

	public String getString(String name){
		if (elemets.containsKey(name)){
			return elemets.get(name).valueString;
		}
		return "";
	}

	public Color getColor(String name){
		if (elemets.containsKey(name)){
			return elemets.get(name).valueColor;
		}
		return Color.WHITE;
	}

	public boolean getBoolean(String name){
		if (elemets.containsKey(name)){
			return elemets.get(name).valueBoolean;
		}
		return false;
	}

	public float getX(String name){
		if (elemets.containsKey(name)){
			return elemets.get(name).valueX;
		}
		return 0;
	}

	public float getY(String name){
		if (elemets.containsKey(name)){
			return elemets.get(name).valueY;
		}
		return 0;
	}

	public float getWidth(String name){
		if (elemets.containsKey(name)){
			return elemets.get(name).valueWidth;
		}
		return 0;
	}

	public float getHeight(String name){
		if (elemets.containsKey(name)){
			return elemets.get(name).valueHeight;
		}
		return 0;
	}

	public void setX(String name, float value){
		elemets.get(name).valueX = value;
	}

	public void setY(String name, float value){
		elemets.get(name).valueY = value;
	}

	public void setWidth(String name, float value){
		elemets.get(name).valueWidth = value;
	}

	public void setHeight(String name, float value){
		elemets.get(name).valueHeight = value;
	}

	public String[] getArrayString(String name){
		return elemets.get(name).valueArrayString;
	}

	/**
	 * @return
	 */
	public Table getTable(){

		S3Log.debug(TAG, "Get Table GUI ...");

		if (guiTable == null){
			Gui gui = new Gui();
			Gui.disableListener = true;
			Set<String> keySet = elemets.keySet();
			Iterator<String> iterator = keySet.iterator();

			while (iterator.hasNext()){
				String key = iterator.next();
				Element el = elemets.get(key);

				switch (el.type){
					default:
						if (S3Constans.DEBUG){
							S3Log.log("S3GuiForm::getTable()", "Not support GUI from " + el.type.name(), 1);
						}
						break;
					case FLOAT_SLIDER:
						if (S3Constans.DEBUG){
							S3Log.log("S3GuiForm::getTable()", "Get float slider: " + key, 1);
						}
						Slider addSlider = gui
						.addSlider(el.label, el, "valueFloat", el.valueFloat, el.valueFloatMin, el.valueFloatMax, el.valueFloatStep, true, true, 1,
								   el.changeListener);
						guiEelemets.put(key, addSlider);
						el.actor = addSlider;
						break;
					case BUTTON_RANDOM:
						if (S3Constans.DEBUG){
							S3Log.log("S3GuiForm::getTable()", "Get BUTTON RANDOM: " + key, 1);
						}
						Button addButton = gui
						.addButton(el.label, el, null, null, true, 1, false, null, el.changeListener);
						guiEelemets.put(key, addButton);
						el.actor = addButton;
						break;
					case BUTTON_RESET:
						if (S3Constans.DEBUG){
							S3Log.log("S3GuiForm::getTable()", "Get BUTTON RESET: " + key, 1);
						}
						Button addButton1 = gui
						.addButton(el.label, el, null, null, true, 1, false, null, el.changeListener);
						guiEelemets.put(key, addButton1);
						el.actor = addButton1;
						break;
					case FILE_BROWSER:
						if (S3Constans.DEBUG){
							S3Log.log("S3GuiForm::getTable()", "Get file browser: " + key, 1);
						}
						gui.addFileBrowserChange(el.label, el, "valueString", el.valueString, true, 1, el.changeListener);
						break;
					case SELECT_INDEX:
						if (S3Constans.DEBUG){
							S3Log.log("S3GuiForm::getTable()", "Get select_index: " + key, 1);
						}
						SelectBox addSelectIndexBox = gui
						.addSelectIndexBox(el.label, el, "valueFloat", el.valueArrayString, (int) el.valueFloat, true, 1, el.changeListener);
						guiEelemets.put(key, addSelectIndexBox);
						el.actor = addSelectIndexBox;
						break;
					case LIST_INDEX:
						if (S3Constans.DEBUG){
							S3Log.log("S3GuiForm::getTable()", "Get list_index: " + key, 1);
						}
						List addListIndex = gui
						.addListIndex(el.label, el, "valueFloat", el.valueArrayString, (int) el.valueFloat, true, 1, el.changeListener);
						guiEelemets.put(key, addListIndex);
						el.actor = addListIndex;
						break;
					case COLOR_SELECT:
						if (S3Constans.DEBUG){
							S3Log.log("S3GuiForm::getTable()", "Get select color: " + key, 1);
						}
						gui.addColorBrowserChange(el.label, el, "valueColor", el.valueColor, true, 1, el.changeListener);
						break;
					case SELECT_COLOR_INDEX:
						if (S3Constans.DEBUG){
							S3Log.log("S3GuiForm::getTable()", "Get select color index browser: " + key, 1);
						}
						List addColorList = gui
						.addColorList(el.label, el, "valueFloat", (int) el.valueFloat, true, 1, el.changeListener);
						guiEelemets.put(key, addColorList);
						el.actor = addColorList;
						break;
					case BUTTON_GROUP:
						for (int i = 0; i < el.valueArrayString.length; i++){
							Button addButtonToggle = gui
							.addButtonToggle(el.valueArrayString[i], el, "valueFloat", (float) i, true, el.name
							+ "_group", el.changeListener);
							guiEelemets.put(el.valueArrayString[i], addButtonToggle);
						}
						break;
					case TEXT_FIELD:
						if (S3Constans.DEBUG){
							S3Log.log("S3GuiForm::getTable()", "Get text field: " + key, 1);
						}
						TextField addTextField = gui
						.addTextField(el.label, el, "valueString", el.valueString, el.valueStringStart, true, 1, el.changeListener);
						guiEelemets.put(key, addTextField);
						el.actor = addTextField;
						break;
					case CHECKBOX:
						if (S3Constans.DEBUG){
							S3Log.log("S3GuiForm::getTable()", "Get checkbox: " + key, 1);
						}
						CheckBox addCheckBox = gui
						.addCheckBox(el.label, el, "valueBoolean", true, false, true, 1, null, el.changeListener);
						guiEelemets.put(key, addCheckBox);
						el.actor = addCheckBox;
						break;
					case PRESENT_SAVE:
						if (S3Constans.SAVE_PRESENT){
							ChangeListener presentChange = new ChangeListener(){
								@Override
								public void changed(ChangeEvent event, Actor actor){
									guiTable = null;
									savePresent();
									readPresentList();
								}
							};
							gui.addButton(el.label, null, null, null, true, 1, false, null, presentChange);
						}
						break;
					case PRESENT_READ:
						if (S3Constans.USE_PRESENT){
							if (S3Constans.DEBUG){
								S3Log.log("S3GuiForm::getTable()", "Get present_index: " + key, 1);
							}
							gui.addListIndex(el.label, el, "valueFloat", el.valueArrayString, (int) el.valueFloat, true, 1, el.changeListener);
						}
						break;
					case SIZE2D_EDITOR:
						if (S3Constans.DEBUG){
							S3Log.log("S3GuiForm::getTable()", "Get size2d editor: " + key, 1);
						}
						Table addEditor2d = gui
						.addEditor2d(el.label, el, "valueX", "valueY", "valueWidth", "valueHeight", el.valueX, el.valueY, el.valueWidth, el.valueHeight, true,
									 1, el.changeListener);
						guiEelemets.put(key, addEditor2d);
						el.actor = addEditor2d;
						break;
					case IMAGE_LIST:
						if (S3Constans.DEBUG){
							S3Log.log("S3GuiForm::getTable()", "Get image list: " + key, 1);
						}
						gui.addImageList(el.label, el, "valueArrayString", el.valueArrayString, true, 1, el.changeListener);
						break;
					case LABEL:
						if (S3Constans.DEBUG){
							S3Log.log("S3GuiForm::getTable()", "Get LABEL: " + key, 1);
						}
						gui.addLabel(el.label, true, 0, 1, el.valueColor);
						break;
				}
			}
			guiTable = gui.getTable();
			Gui.disableListener = false;
		}

		return guiTable;
	}

	/**
	 *
	 */
	public void clearGuiTable(){
		guiTable = null;
	}

	/**
	 * @return
	 */
	public ScrollPane getScrollPane(){

		Table table = getTable();
		ScrollPane guiScrollCell = GuiResource.scrollPaneTransparent(table, "guiScrollCell");
		guiScrollCell.setScrollingDisabled(true, false);
		guiScrollCell.setFlickScroll(true);
		guiScrollCell.setFadeScrollBars(false);

		return guiScrollCell;
	}

	/**
	 *
	 */
	public void showAdvance(){

		Set<String> keySet = elemets.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()){
			String key = iterator.next();
			Element el = elemets.get(key);
			if (guiEelemets.containsKey(key)){
				Actor guiElement = guiEelemets.get(key);
				switch (el.type){
					default:
						break;
					case PRESENT_READ:
						break;
				}
			}
		}
	}

	/**
	 *
	 */
	public void hideAdvance(){
		Set<String> keySet = elemets.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()){
			String key = iterator.next();
			Element el = elemets.get(key);

			switch (el.type){
				default:
					break;
			}
			;
		}
	}

	/**
	 * @param writer
	 * @return
	 */
	public XmlWriter save(XmlWriter writer){

		try {
			XmlWriter element = writer.element("setting");

			Set<String> keySet = elemets.keySet();
			Iterator<String> iterator = keySet.iterator();

			while (iterator.hasNext()){
				String key = iterator.next();
				Element el = elemets.get(key);

				if (S3Constans.DEBUG){
					S3Log.log("S3GuiForm::save", "Save key: " + key + " type: " + el.type, 2);
				}

				switch (el.type){
					default:
						S3Log.log("S3GuiForm::save", "Unsupported type !!!  key: " + key + " type: " + el.type, 1);
						break;
					case BUTTON_RANDOM:
					case BUTTON_RESET:
					case PRESENT_SAVE:
					case PRESENT_READ:
					case TEXT_FIELD:
						break;
					case FLOAT_SLIDER:
						element.attribute(el.name, el.valueFloat);
						break;
					case FILE_BROWSER:
						element.attribute(el.name, S3File.getFileSaveName(el.valueString));
						break;
					case SELECT_INDEX:
						element.attribute(el.name, el.valueFloat);
						break;
					case COLOR_SELECT:
						element.attribute(el.name, el.valueColor);
						break;
					case SELECT_COLOR_INDEX:
						element.attribute(el.name, el.valueFloat);
						break;
					case BUTTON_GROUP:
						element.attribute(el.name, el.valueFloat);
						break;
					case LIST_INDEX:
						element.attribute(el.name, el.valueFloat);
						break;
					case CHECKBOX:
						element.attribute(el.name, el.valueBoolean);
						break;
					case SIZE2D_EDITOR:
						element.attribute(el.name + "_x", el.valueX / S3Screen.width);
						element.attribute(el.name + "_y", el.valueY / S3Screen.height);
						element.attribute(el.name + "_width", el.valueWidth / S3Screen.width);
						element.attribute(el.name + "_height", el.valueHeight / S3Screen.height);
						break;
					case IMAGE_LIST:
						if (el.valueArrayString != null){
							element.attribute(el.name + "_size", el.valueArrayString.length);
							for (int i = 0; i < el.valueArrayString.length; i++){
								element.attribute(el.name + "_item" + i, el.valueArrayString[i]);
							}
						} else {
							element.attribute(el.name + "_size", 0);
						}
						break;
				}
			}

			keySet = elemets.keySet();
			iterator = keySet.iterator();
			while (iterator.hasNext()){
				String key = iterator.next();
				Element el = elemets.get(key);

				if (S3Constans.DEBUG){
					S3Log.log("S3GuiForm::save", "Save key: " + key + " type: " + el.type, 2);
				}

				switch (el.type){
					default:
						break;
					case TEXT_FIELD:
						element.text("<![CDATA[" + el.valueString + "]]>");
						break;
				}
			}
			writer.pop();
			return writer;
		} catch (IOException ex){
			S3Log.log("S3GuiForm::save", ex.toString() + " Exception in " + this.getClass().getName());
			return writer;
		}
	}

	/**
	 * @param xmlElement
	 */
	public void read(XmlReader.Element xmlElement){
		XmlReader.Element setting = xmlElement.getChildByName("setting");

		Set<String> keySet = elemets.keySet();
		Iterator<String> iterator = keySet.iterator();

		while (iterator.hasNext()){
			String key = iterator.next();
			Element el = elemets.get(key);

			if (S3Constans.DEBUG){
				S3Log.log("S3GuiForm::read", "Read key: " + key + " type: " + el.type, 3);
			}

			switch (el.type){
				default:
					break;
				case FLOAT_SLIDER:
					if (S3Constans.DEBUG){
						S3Log.log("S3GuiForm::read", "value: " + setting.getFloatAttribute(key, 1.0f), 3);
					}
					el.valueFloat = setting.getFloatAttribute(key, 1.0f);
					break;
				case FILE_BROWSER:
					if (S3Constans.DEBUG){
						S3Log.log("S3GuiForm::read", "value: " + setting.getAttribute(key, ""), 3);
					}
					el.valueString = setting.getAttribute(key, "sprite");
					break;
				case SELECT_INDEX:
					if (S3Constans.DEBUG){
						S3Log.log("S3GuiForm::read", "value: " + setting.getFloatAttribute(key, 1.0f), 3);
					}
					el.valueFloat = setting.getFloatAttribute(key, 1.0f);
					break;
				case COLOR_SELECT:
					if (S3Constans.DEBUG){
						S3Log.log("S3GuiForm::read", "value: " + setting.getAttribute(key, ""), 3);
					}
					el.valueColor = ColorUtil.HexToColor(setting.getAttribute(key, "FFFFFFFF"));
					break;
				case SELECT_COLOR_INDEX:
					if (S3Constans.DEBUG){
						S3Log.log("S3GuiForm::read", "value: " + setting.getFloatAttribute(key, 0.0f), 3);
					}
					el.valueFloat = (int) setting.getFloatAttribute(key, 0.0f);
					break;
				case BUTTON_GROUP:
					if (S3Constans.DEBUG){
						S3Log.log("S3GuiForm::read", "value: " + setting.getFloatAttribute(key, 0.0f), 3);
					}
					el.valueFloat = (int) setting.getFloatAttribute(key, 0.0f);
					break;
				case LIST_INDEX:
					if (S3Constans.DEBUG){
						S3Log.log("S3GuiForm::read", "value: " + setting.getFloatAttribute(key, 0.0f), 3);
					}
					el.valueFloat = (int) setting.getFloatAttribute(key, 0.0f);
					break;
				case TEXT_FIELD:
					if (S3Constans.DEBUG){
						S3Log.log("S3GuiForm::read", "value: " + setting.getText(), 3);
					}
					el.valueString = setting.getText();
					break;
				case CHECKBOX:
					if (S3Constans.DEBUG){
						S3Log.log("S3GuiForm::read CHECKBOX", "name: " + key + " value: "
						+ setting.getBooleanAttribute(key, false), 3);
					}
					el.valueBoolean = setting.getBooleanAttribute(key, false);
					break;
				case SIZE2D_EDITOR:
					el.valueX = (int) (setting.getFloatAttribute(key + "_x", 0.0f) * S3Screen.width);
					el.valueY = (int) (setting.getFloatAttribute(key + "_y", 0.0f) * S3Screen.height);
					el.valueWidth = (int) (setting.getFloatAttribute(key + "_width", 1.0f) * S3Screen.width);
					el.valueHeight = (int) (setting.getFloatAttribute(key + "_height", 1.0f) * S3Screen.height);
					if (S3Constans.DEBUG){
						S3Log.log("S3GuiForm::read SIZE2D_EDITOR", "name: " + key + " x:" + el.valueX + " y:"
						+ el.valueY + " width: " + el.valueWidth + " height: " + el.valueHeight, 3);
					}
					break;
				case IMAGE_LIST:
					int items = setting.getInt(key + "_size", 0);
					el.valueArrayString = new String[items];
					for (int i = 0; i < items; i++){
						el.valueArrayString[i] = setting.getAttribute(key + "_item" + i, "");
					}
					break;
			}
		}
	}

	/**
	 *
	 */
	public void savePresent(){

		try {
			FileHandle fileSaveHandle = S3File.getFileHandle(presentDir + className + ".xml", true, false, true);

			StringWriter writer = new StringWriter();
			XmlWriter xml = new XmlWriter(writer);
			XmlWriter element = xml.element("present");
			element.attribute("class", className);
			int countElement = 0;

			//
			// Odczytanie dodanych pozycji
			//
			if (fileSaveHandle.exists()){
				FileHandle fileReadHandle = S3File.getFileHandle(presentDir + className + ".xml");

				XmlReader xmlReader = new XmlReader();
				XmlReader.Element mainElement = xmlReader.parse(fileReadHandle);

				countElement = mainElement.getChildCount();

				XmlWriter xmlItem = element.element("slot");
				xmlItem.attribute("name", "item_" + (countElement + 1));
				save(xmlItem);
				xmlItem.pop();

				for (int i = 0; i < countElement; i++){
					XmlReader.Element child = mainElement.getChild(i);
					read(child);
					xmlItem = element.element("slot");
					xmlItem.attribute("name", child.getAttribute("name", ""));
					save(xmlItem);
					xmlItem.pop();
				}
			} else {

				XmlWriter xmlItem = element.element("slot");
				countElement++;
				xmlItem.attribute("name", "item_" + countElement);
				save(xmlItem);
				xmlItem.pop();

			}

			element.pop();
			fileSaveHandle.writeString(writer.toString(), false);
			writer.close();
		} catch (Exception ex){
			S3Log.error("savePresent", " Exception in save present: ", ex);
		}
	}

	/**
	 * @param index
	 */
	public void readPresent(int index){

		try {

			FileHandle fileReadHandle = S3File.getFileHandle(presentDir + className + ".xml", true, false, false);

			if (fileReadHandle.exists()){

				XmlReader xmlReader = new XmlReader();
				XmlReader.Element mainElement = xmlReader.parse(fileReadHandle);

				int countElement = mainElement.getChildCount();
				XmlReader.Element child = mainElement.getChild(index);
				read(child);
			}
		} catch (IOException ex){
			S3Log.error("readPresent", " Exception in read present: ", ex);
		}
	}

	/**
	 *
	 */
	private String[] readPresentList(){
		try {

			FileHandle fileReadHandle = S3File.getFileHandle(presentDir + className + ".xml", true, false, false);

			if (fileReadHandle.exists()){

				XmlReader xmlReader = new XmlReader();
				XmlReader.Element mainElement = xmlReader.parse(fileReadHandle);

				int countElement = mainElement.getChildCount();
				String names[] = new String[countElement + 1];
				names[0] = S3Lang.get("select_custom");
				for (int i = 0; i < countElement; i++){
					XmlReader.Element child = mainElement.getChild(i);
					names[i + 1] = child.getAttribute("name", "item " + i);
				}
				return names;
			}
		} catch (IOException ex){
			S3Log.error("readPresentList", " Exception: ", ex);
		}
		return null;
	}
}
