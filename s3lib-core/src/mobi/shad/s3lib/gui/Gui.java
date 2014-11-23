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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import mobi.shad.s3lib.gui.dialog.Editor2d;
import mobi.shad.s3lib.gui.widget.*;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Element;
import mobi.shad.s3lib.main.S3Log;

import java.util.HashMap;

/**
 * @author Jarek
 */
public class Gui{

	public static boolean disableListener = false;
	private boolean isChange = false;
	private Table table = new Table();
	private HashMap<String, ButtonGroup> hashButton = new HashMap<String, ButtonGroup>();
	private String[] colorsName = {"Hue", "Fire", "Blue>Violet", "Fire 2", "Blue>Violet>Yellow", "Orange>Blue",
								   "Orange>Cyan>Red", "Blue>Green>Red", "Yellow>Red>White", "Red", "Green", "Blue", "Yellow", "Green>Cyan",
								   "Blue>Yellow", "Red>Cyan", "Yellow-Blue", "Grey 1", "Grey 2", "Grey 3", "Black>Red>White",
								   "Black>Green>White", "Black>Blue>White", "Black>Yelow>White", "Black>Pink>White", "Black>Cyan>White",
								   "Black>Orange>White", "Black>Green 2>White", "Black>Cyan 2>White", "Black>Orange>White",
								   "Black>Grass>White", "Black>Green 3>White", "Chrome", "Blue>Red>Yellow", "Copper", "Orange>Black>Blue",
								   "Yellow>Black>Violet", "Green>Black>Red", "Red>Blue>Yellow", "Green>Black>Orange", "Red>Black>Violet",
								   "Blue>Black>Pink", "Yellow>Black>Violet"};

	class GuiElement{

		private Object generic;
		private Object value;
		private String name;
		private String fieldName;
		private int type = 0;
		private S3Element element = null;

		public GuiElement(String name, int type, Object object, String fieldName, Object value){
			this.generic = generic;
			this.value = value;
			this.name = name;
			this.fieldName = fieldName;
			this.type = type;
			element = new S3Element(object, fieldName);
		}

		/**
		 *
		 */
		public void assignValue(){
			element.setValue(value);
		}

		/**
		 * @param valueFloat
		 */
		public void setValue(Float valueFloat){
			element.setValue(valueFloat);
		}

		/**
		 * @param valueFloat
		 */
		public void setValue(String valueString){
			element.setValue(valueString);
		}

		/**
		 * @param valueInt
		 */
		public void setValue(int valueInt){
			element.setValue(valueInt);
		}

		/**
		 * @param valueInt
		 */
		public void setValue(Color valueInt){
			element.setValue(valueInt);
		}

		/**
		 * @param valueInt
		 */
		public void setValue(Object valueObject){
			element.setValue(valueObject);
		}

		/**
		 * @return
		 */
		public String getValueAsString(){

			if (element.getValue() == null){
				return "";
			}
			return element.getValue().toString();
		}

		public Object getValue(){
			return element.getValue();
		}

		public void setValue(String[] value){
			element.setValue(value);
		}
	}

	public Gui(){
		table = GuiResource.table("GuiTable");
	}

	public Table getTable(){
		table.pack();
		return table;
	}

	/**
	 * @return
	 */
	public ScrollPane getScrollPane(){
		table.pack();
		ScrollPane guiScrollCell = GuiResource.scrollPaneTransparent(table, "guiScrollCell");
		guiScrollCell.setScrollingDisabled(true, false);
		guiScrollCell.setFlickScroll(true);
		guiScrollCell.setFadeScrollBars(false);
		return guiScrollCell;
	}

	public void row(){
		table.row();
	}

	public Cell add(Actor actor){
		return table.add(actor);
	}

	public Cell addRow(final Actor firstElement, final Actor secondElement, final String label, final int height, final int colSpan, final boolean newRow,
					   final boolean spaceBox){

		if (label != null){
			final Label guiLabel = GuiResource.label(label, label);
			if (newRow){
				table.row();
				table.add(guiLabel).left().expandX().fillX().colspan(colSpan + 1).padBottom(-S3Constans.cellPaddding * 2)
					 .padTop(0f).padLeft(0f);
				table.row();
			} else {
				table.add(guiLabel).left().expandX().fillX().colspan(colSpan + 1).padBottom(0f).padTop(0f);
			}
		} else {
			if (newRow){
				table.row();
			}
		}
		if (height > 0){
			table.add(firstElement).left().expandX().fillX().height(height).colspan(colSpan).padBottom(0f);
		} else {
			table.add(firstElement).left().expandX().fillX().colspan(colSpan).padTop(0f);
		}
		if (secondElement != null){
			table.add(secondElement).left().minWidth(S3Constans.sliderMinTextWidth);
		} else {
			if (spaceBox){
				table.add("").left().minWidth(S3Constans.sliderMinTextWidth);
			}
		}
		return table.getCell(firstElement);
	}

	public void addSeparator(final Color color, final boolean newRow, final int colSpan){
		Pixmap px = new Pixmap(1, 1, Pixmap.Format.RGB888);
		px.setColor(color);
		px.fill();
		Texture tex = new Texture(px);
		Image img = new Image(tex);
		if (newRow){
			table.row();
		}
		table.add(img).left().expandX().fillX().height(1).colspan(colSpan);
	}

	public Button addButtonToggle(String label, Object object, String fieldName, Object value){
		return addButton(label, object, fieldName, value, true, 1, true, null, null);
	}

	public Button addButtonToggle(String label, Object object, String fieldName, Object value, boolean newRow, String groupName){
		return addButton(label, object, fieldName, value, newRow, 1, true, groupName, null);
	}

	public Button addButtonToggle(String label, Object object, String fieldName, Object value, boolean newRow, String groupName, ChangeListener changeListener){
		return addButton(label, object, fieldName, value, newRow, 1, true, groupName, changeListener);
	}

	public Button addButton(String label, ChangeListener changeListener){
		return addButton(label, null, null, null, true, 1, false, null, changeListener);
	}

	public Button addButton(String label, Object object, String fieldName, Object value){
		return addButton(label, object, fieldName, value, true, 1, false, null, null);
	}

	public Button addButton(String label, Object object, String fieldName, Object value, boolean newRow, ChangeListener changeListener){
		return addButton(label, object, fieldName, value, newRow, 1, false, null, changeListener);
	}

	public Button addButton(String label, Object object, String fieldName, Object value, boolean newRow, String groupName){
		return addButton(label, object, fieldName, value, newRow, 1, false, groupName, null);
	}

	public Button addButton(String label, Object object, String fieldName, Object value, boolean newRow, int colSpan, boolean toggle, String groupName,
							ChangeListener changeListener){

		final ChangeListener outListener = changeListener;

		//
		// Gui element
		//
		TextButton buttonTmp = null;
		if (toggle){
			buttonTmp = GuiResource.textButtonToggle(label, label);
		} else {
			buttonTmp = GuiResource.textButton(label, label);
		}

		final TextButton button = buttonTmp;

		//
		// Action
		//
		if (object != null && fieldName != null){
			final GuiElement element = new GuiElement(label, 0, object, fieldName, value);

			button.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor){
					if (S3Constans.DEBUG){
						S3Log.log("S3Gui::button", "Change on button: " + button.getText() + " event: "
						+ event.toString());
					}
					element.assignValue();
					if (S3Constans.DEBUG){
						S3Log.log("S3Gui::button", "New value element: " + element.getValueAsString());
					}
					if (outListener != null && disableListener == false){
						outListener.changed(event, actor);
					}
				}
			});

			if (element.getValue().equals(value)){
				button.setChecked(true);
			}
		} else if (outListener != null){
			button.addListener(outListener);
		}

		//
		// Fix slider FixScroll
		//
		button.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
			}
		});

		if (groupName != null){
			if (!hashButton.containsKey(groupName)){
				hashButton.put(groupName, new ButtonGroup(button));
			} else {
				hashButton.get(groupName).add(button);
			}

			hashButton.get(groupName).setChecked(label);
		}

		if (newRow){
			table.row();
		}
		table.add(button).left().expandX().fillX().colspan(colSpan);
		return button;
	}

	/**
	 * @param label
	 * @param newRow
	 * @param colSpan
	 * @return
	 */
	public Label addLabel(String label, boolean newRow, int colSpan){
		return addLabel(label, newRow, 0, colSpan, Color.WHITE);
	}

	public Label addLabel(String label, boolean newRow, int colSpan, Color color){
		return addLabel(label, newRow, 0, colSpan, color);
	}

	public Label addLabel(String label, boolean newRow, int width, int colSpan){
		return addLabel(label, newRow, width, colSpan, Color.WHITE);
	}

	/**
	 * @param label
	 * @param newRow
	 * @param width
	 * @param colSpan
	 * @return
	 */
	public Label addLabel(String label, boolean newRow, int width, int colSpan, Color color){

		Label guiLabel = GuiResource.label(label, "label");
		guiLabel.setColor(color);
		if (newRow){
			table.row();
		}
		if (width == 0){
			table.add(guiLabel).left().expandX().fillX().colspan(colSpan);
		} else {
			table.add(guiLabel).left().colspan(colSpan).width(width);
		}
		return guiLabel;
	}

	/**
	 * @param fileName
	 * @param newRow
	 * @param toggle
	 * @param buttonSize
	 */
	public Button addImgButton(String fileName, boolean newRow, boolean toggle, int buttonSize){
		return addImgButton(fileName, null, null, null, newRow, 1, toggle, buttonSize, null, null);
	}

	public Button addImgButton(String fileName, boolean newRow, boolean toggle, int buttonSize, ChangeListener changeListener){
		return addImgButton(fileName, null, null, null, newRow, 1, toggle, buttonSize, null, changeListener);
	}

	/**
	 * @param fileName
	 * @param object
	 * @param fieldName
	 * @param value
	 * @param newRow
	 * @param colSpan
	 * @param toggle
	 * @param buttonSize
	 * @param groupName
	 * @param changeListener
	 * @return
	 */
	public Button addImgButton(final String fileName, Object object, String fieldName, Object value, boolean newRow, int colSpan, boolean toggle,
							   int buttonSize, String groupName, ChangeListener changeListener){

		final ChangeListener outListener = changeListener;

		Button buttonTmp = null;
		if (toggle){
			buttonTmp = GuiResource.buttonToogle(fileName);
		} else {
			buttonTmp = GuiResource.button(fileName);
		}

		final Button button = buttonTmp;

		if (object != null && fieldName != null){
			final GuiElement element = new GuiElement(fileName, 0, object, fieldName, value);

			button.addListener(new ChangeListener(){
				public void changed(ChangeEvent event, Actor actor){
					if (S3Constans.DEBUG){
						S3Log.log("S3Gui::button", "Change on button: " + fileName + " event: " + event.toString());
					}
					element.assignValue();
					if (S3Constans.DEBUG){
						S3Log.log("S3Gui::button", "New value element: " + element.getValueAsString());
					}
					if (outListener != null && disableListener == false){
						outListener.changed(event, actor);
					}
				}
			});

			if (element.getValue().equals(value)){
				button.setChecked(true);
			}
		} else if (outListener != null){
			button.addListener(outListener);
		}

		if (groupName != null){
			if (!hashButton.containsKey(groupName)){
				hashButton.put(groupName, new ButtonGroup(button));
			} else {
				hashButton.get(groupName).add(button);
			}
			hashButton.get(groupName).setChecked(fileName);
		}

		if (newRow){
			table.row();
		}
		if (buttonSize == 0){
			table.add(button).left().expandX().fillX().colspan(colSpan);
		} else {
			table.add(button).left().width(buttonSize).height(buttonSize).colspan(colSpan);

		}
		return button;
	}

	/**
	 * @param buttonSize
	 */
	public void addEmpty(int buttonSize){
		if (buttonSize == 0){
			table.add().left().expandX().fillX();
		} else {
			table.add().left().width(buttonSize).height(buttonSize);

		}
	}

	public void addEmptyY(int buttonSize){
		if (buttonSize == 0){
			table.add().left().expandY().fillY();
		} else {
			table.add().left().width(buttonSize).height(buttonSize);
		}
	}

	public void addEmptyY(int buttonSize, int span){
		if (buttonSize == 0){
			table.add().left().expandY().fillY().colspan(span);
		} else {
			table.add().left().width(buttonSize).height(buttonSize).colspan(span);
		}
	}

	/**
	 * @param label
	 * @param object
	 * @param fieldName
	 * @param valueOn
	 * @param valueOff
	 * @param newRow
	 * @param colSpan
	 * @param groupName
	 * @param changeListener
	 * @return
	 */
	public CheckBox addCheckBox(String label, Object object, String fieldName, final Object valueOn, final Object valueOff, boolean newRow, int colSpan,
								String groupName, ChangeListener changeListener){

		final ChangeListener outListener = changeListener;

		final CheckBox checkBox = GuiResource.checkBox(label, label);

		if (object != null && fieldName != null){
			final GuiElement element = new GuiElement(label, 0, object, fieldName, valueOff);

			checkBox.addListener(new ChangeListener(){
				@Override
				public void changed(ChangeEvent event, Actor actor){
					if (S3Constans.DEBUG){
						S3Log.log("S3Gui::checkBox", "Click on checkBox: " + checkBox.getText() + " event: "
						+ event.toString());
					}
					if (checkBox.isChecked()){
						element.setValue(valueOn);
					} else {
						element.setValue(valueOff);
					}
					if (S3Constans.DEBUG){
						S3Log.log("S3Gui::checkBox", "New value element: " + element.getValueAsString());
					}
					if (outListener != null && disableListener == false){
						outListener.changed(event, actor);
					}
				}
			});

			if (element.getValue().equals(valueOn)){
				checkBox.setChecked(true);
			}
		} else if (outListener != null){
			checkBox.addListener(outListener);
		}

		if (groupName != null){
			if (!hashButton.containsKey(groupName)){
				hashButton.put(groupName, new ButtonGroup(checkBox));
			} else {
				hashButton.get(groupName).add(checkBox);
			}

			hashButton.get(groupName).setChecked(label);
		}

		if (newRow){
			table.row();
			table.add(checkBox).left().expandX().fillX().colspan(colSpan).padBottom(-S3Constans.cellPaddding * 3);
		} else {
			table.add(checkBox).left().expandX().fillX().colspan(colSpan);
		}

		return checkBox;
	}

	public Slider addSlider(final String label, final Object object, final String fieldName, final float value, final float min, final float max,
							final float step){
		return addSlider(label, object, fieldName, value, min, max, step, true, true, 1, null);
	}

	public Slider addSlider(final String label, final Object object, final String fieldName, final float value, final float min, final float max,
							final float step, final int colSpan){
		return addSlider(label, object, fieldName, value, min, max, step, true, true, colSpan, null);
	}

	public Slider addSlider(final String label, final Object object, final String fieldName, final float value, final float min, final float max,
							final float step, final ChangeListener changeListener){
		return addSlider(label, object, fieldName, value, min, max, step, true, true, 1, changeListener);
	}

	public Slider addSlider(final String label, final Object object, final String fieldName, final float value, final float min, final float max,
							final float step, final int colSpan, final ChangeListener changeListener){
		return addSlider(label, object, fieldName, value, min, max, step, true, true, colSpan, changeListener);
	}

	/**
	 * @param label
	 * @param object
	 * @param fieldName
	 * @param value
	 * @param min
	 * @param max
	 * @param step
	 * @param newRow
	 * @param showValue
	 * @param colSpan
	 * @param changeListener
	 */
	public Slider addSlider(final String label, final Object object, final String fieldName, final float value, final float min, final float max,
							final float step, final boolean newRow, final boolean showValue, final int colSpan, final ChangeListener changeListener){
		if (S3Constans.DEBUG){
			S3Log.log("3SGui::addSlider", "label: " + label + " fieldName: " + fieldName + " value:" + value + " min: "
			+ min + " max: " + max + " step: " + step);
		}
		//
		// Gui Element
		//
		final ChangeListener outListener = changeListener;
		final GuiElement element = new GuiElement(label, 0, object, fieldName, value);
		final Slider slider = GuiResource.slider(min, max, step, "slider");

		final String text = "" + String.format("%.1f", value);
		final Label valueLabel = GuiResource.label(text, label);

		//
		// Action
		//
		slider.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				if (S3Constans.DEBUG){
					S3Log.log("S3Gui::button", "Click on button: " + slider.getValue() + " event: " + event.toString());
				}
				element.setValue(slider.getValue());
				valueLabel.setText(new StringBuilder(String.format("%.1f", slider.getValue())));
				if (S3Constans.DEBUG){
					S3Log.log("S3Gui::button", "New value element: " + element.getValueAsString());
				}
				if (outListener != null && disableListener == false){
					outListener.changed(event, actor);
				}
			}
		});

		//
		// Fix slider FixScroll
		//
		slider.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
			}
		});

		//
		// Add value
		//
		try {
			slider.setValue(value);
		} catch (Exception e){
			S3Log.error("S3Gui:addSlider", "Error set value", e);
		}

		addRow(slider, valueLabel, label, 0, colSpan, newRow, true);
		return slider;
	}

	/**
	 * @param label
	 * @param object
	 * @param fieldName
	 * @param value
	 * @param messageText
	 * @param newRow
	 * @param colSpan
	 * @param addStep
	 * @param changeListener
	 * @return
	 */
	public TextField addTextNumericField(String label, Object object, String fieldName, String value, String messageText, boolean newRow, int colSpan,
										 final int addStep, ChangeListener changeListener){
		if (S3Constans.DEBUG){
			S3Log.log("3SGui::addTextField", "label: " + label + " fieldName: " + fieldName + " value:" + value);
		}
		//
		// Gui Element
		//
		final Table bboX = GuiResource.table("");
		final ChangeListener outListener = changeListener;

		final GuiElement element = new GuiElement(label, 0, object, fieldName, value);
		final TextField textField = GuiResource.textField(value, messageText, label);

		final TextButton btnMinus = GuiResource.textButton("-", "btn+");
		btnMinus.pad(S3Constans.buttonPadding);
		final TextButton btnPlus = GuiResource.textButton("+", "btn+");
		btnPlus.pad(S3Constans.buttonPadding);
		final TextButton btnOk = GuiResource.textButton("Ok", "btn+");
		btnOk.pad(S3Constans.buttonPadding);

		final Label guiLabel = GuiResource.label(label, label);

		bboX.row();
		bboX.add(guiLabel).left().width(S3Constans.textInputNumericLabel);
		bboX.add(btnMinus).left().width(S3Constans.textInputNumericPlus);
		bboX.add(textField).left().width(S3Constans.textInputNumeric);
		bboX.add(btnPlus).left().width(S3Constans.textInputNumericPlus);
		bboX.add(btnOk).left().width(S3Constans.textInputNumericPlus);

		//
		// Action
		//
		textField.setTextFieldListener(new TextField.TextFieldListener(){
			@Override
			public void keyTyped(TextField textField, char key){
				String text = textField.getText();
				// && key!='.'
				if (!Character.isDigit(key) && !Character.isISOControl(key) && key != '.'){
					if (text.length() > 1){
						textField.setText(text.substring(0, text.length() - 1));
						textField.setCursorPosition(text.length());
					} else {
						textField.setText("");
					}
				}
			}
		});

		btnMinus.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				float val = Float.parseFloat(textField.getText());
				val = val - addStep;
				textField.setText("" + val);
			}
		});
		btnPlus.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				float val = Float.parseFloat(textField.getText());
				val = val + addStep;
				textField.setText("" + val);
			}
		});
		btnOk.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				if (S3Constans.DEBUG){
					S3Log.log("S3Gui::addTextNumericField", "Click on button Ok: " + textField.getText() + " event: "
					+ event.toString());
				}
				element.setValue(textField.getText());
				if (S3Constans.DEBUG){
					S3Log.log("S3Gui::button", "New value element: " + element.getValueAsString());
				}
				if (outListener != null && disableListener == false){
					outListener.changed(event, actor);
				}
			}
		});

		addRow(bboX, null, null, 0, colSpan, newRow, true);
		return textField;
	}

	public TextField addTextField(String label, Object object, String fieldName, String value, String messageText, boolean newRow, int colSpan,
								  ChangeListener changeListener){

		if (S3Constans.DEBUG){
			S3Log.log("3SGui::addTextField", "label: " + label + " fieldName: " + fieldName + " value:" + value);
		}
		final Table bboX = GuiResource.table("");
		final ChangeListener outListener = changeListener;

		final GuiElement element = new GuiElement(label, 0, object, fieldName, value);
		final TextField textField = GuiResource.textField(value, messageText, label);

		//
		// Fix slider FixScroll
		//
		textField.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
			}
		});

		final TextButton btnOk = GuiResource.textButton("Ok", "btn+");
		btnOk.pad(S3Constans.buttonPaddingSmall);

		bboX.row();
		bboX.add(textField).left().fillX().expandX().minWidth(100);
		bboX.add(btnOk).left().width(30);

		btnOk.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				if (S3Constans.DEBUG){
					S3Log.log("S3Gui::addTextNumericField", "Click on button Ok: " + textField.getText() + " event: "
					+ event.toString());
				}
				element.setValue(textField.getText());
				if (S3Constans.DEBUG){
					S3Log.log("S3Gui::button", "New value element: " + element.getValueAsString());
				}
				if (outListener != null && disableListener == false){
					outListener.changed(event, actor);
				}
			}
		});

		addRow(bboX, null, label, 0, colSpan, newRow, false);
		return textField;
	}

	public List addList(String label, Object object, String fieldName, String[] listItem){
		return addList(label, object, fieldName, listItem, "", true, 1, null);
	}

	public List addList(String label, Object object, String fieldName, String[] listItem, String value){
		return addList(label, object, fieldName, listItem, value, true, 1, null);
	}

	public List addList(String label, Object object, String fieldName, String[] listItem, String value, boolean newRow){
		return addList(label, object, fieldName, listItem, value, newRow, 1, null);
	}

	public List addList(String label, Object object, String fieldName, String[] listItem, String value, ChangeListener changeListener){
		return addList(label, object, fieldName, listItem, value, true, 1, changeListener);
	}

	public List addList(String label, Object object, String fieldName, String[] listItem, String value, boolean newRow, int colSpan,
						ChangeListener changeListener){
		return addList(label, object, fieldName, listItem, value, newRow, colSpan, changeListener, S3Constans.listSizeY);
	}

	/**
	 * @param label
	 * @param object
	 * @param fieldName
	 * @param listItem
	 * @param value
	 * @param newRow
	 * @param colSpan
	 * @param changeListener
	 * @return
	 */
	public List addList(String label, Object object, String fieldName, String[] listItem, String value, boolean newRow, int colSpan,
						ChangeListener changeListener, int height){

		//
		// Gui Element
		//
		final ChangeListener outListener = changeListener;
		final GuiElement element = new GuiElement(label, 0, object, fieldName, value);
		final List list = GuiResource.list(listItem, "list");
		final ScrollPane scrollList = GuiResource.scrollPane(list, "scrollPaneList");
		scrollList.setScrollingDisabled(true, false);
		scrollList.setFlickScroll(false);
		scrollList.setFadeScrollBars(false);

		list.getSelection().set(value);

		//
		// Action
		//
		list.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				if (S3Constans.DEBUG){
					S3Log.log("S3Gui::addList", "Click on List: " + list.getSelection().first() + " event: "
					+ event.toString());
				}
				element.setValue(list.getSelection().first());
				if (S3Constans.DEBUG){
					S3Log.log("S3Gui::addList", "New value element: " + element.getValueAsString());
				}
				if (outListener != null && disableListener == false){
					outListener.changed(event, actor);
				}
			}
		});

		//
		// Fix slider FixScroll
		//
		list.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
			}
		});

		addRow(scrollList, null, label, S3Constans.listSizeY, colSpan, newRow, true);
		return list;
	}

	public List addListIndex(String label, Object object, String fieldName, String[] listItem){
		return addListIndex(label, object, fieldName, listItem, 0, true, 1, null);
	}

	public List addListIndex(String label, Object object, String fieldName, String[] listItem, int value){
		return addListIndex(label, object, fieldName, listItem, value, true, 1, null);
	}

	public List addListIndex(String label, Object object, String fieldName, String[] listItem, int value, boolean newRow){
		return addListIndex(label, object, fieldName, listItem, value, newRow, 1, null);
	}

	public List addListIndex(String label, Object object, String fieldName, String[] listItem, int value, ChangeListener changeListener){
		return addListIndex(label, object, fieldName, listItem, value, true, 1, changeListener);
	}

	public List addListIndex(String label, Object object, String fieldName, String[] listItem, int value, boolean newRow, int colSpan,
							 ChangeListener changeListener){
		return addListIndex(label, object, fieldName, listItem, value, newRow, colSpan, changeListener, S3Constans.listSizeY);
	}

	/**
	 * @param label
	 * @param object
	 * @param fieldName
	 * @param listItem
	 * @param value
	 * @param newRow
	 * @param colSpan
	 * @param changeListener
	 * @return
	 */
	public List addListIndex(String label, Object object, String fieldName, String[] listItem, int value, boolean newRow, int colSpan,
							 ChangeListener changeListener, int height){

		final ChangeListener outListener = changeListener;

		final GuiElement element = new GuiElement(label, 0, object, fieldName, value);

		final List list = GuiResource.list(listItem, "list");
		final ScrollPane scrollList = GuiResource.scrollPane(list, "scrollPaneList");
		scrollList.setScrollingDisabled(true, false);
		scrollList.setFlickScroll(false);
		scrollList.setFadeScrollBars(false);
		list.setSelectedIndex(value);

		list.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				if (S3Constans.DEBUG){

					S3Log.log("S3Gui::button", "Click on button: " + list.getSelectedIndex() + " event: "
					+ event.toString());
				}
				element.setValue(list.getSelectedIndex());
				if (S3Constans.DEBUG){
					S3Log.log("S3Gui::button", "New value element: " + element.getValueAsString());
				}
				if (outListener != null && disableListener == false){
					outListener.changed(event, actor);
				}
			}
		});

		//
		// Fix slider FixScroll
		//
		list.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
			}
		});

		addRow(scrollList, null, label, S3Constans.listSizeY, colSpan, newRow, true);
		return list;
	}

	public SelectBox addSelectBox(String label, Object object, String fieldName, String[] listItem){
		return addSelectBox(label, object, fieldName, listItem, "", true, 1, null);
	}

	public SelectBox addSelectBox(String label, Object object, String fieldName, String[] listItem, String value){
		return addSelectBox(label, object, fieldName, listItem, value, true, 1, null);
	}

	public SelectBox addSelectBox(String label, Object object, String fieldName, String[] listItem, String value, boolean newRow){
		return addSelectBox(label, object, fieldName, listItem, value, newRow, 1, null);
	}

	public SelectBox addSelectBox(String label, Object object, String fieldName, String[] listItem, String value, ChangeListener changeListener){
		return addSelectBox(label, object, fieldName, listItem, value, true, 1, changeListener);
	}

	/**
	 * @param label
	 * @param object
	 * @param fieldName
	 * @param listItem
	 * @param value
	 * @param newRow
	 * @param colSpan
	 * @param changeListener
	 * @return
	 */
	public SelectBox addSelectBox(String label, Object object, String fieldName, String[] listItem, String value, boolean newRow, int colSpan,
								  ChangeListener changeListener){

		final ChangeListener outListener = changeListener;

		final GuiElement element = new GuiElement(label, 0, object, fieldName, value);

		final SelectBox selectBox = GuiResource.selectBox(listItem, "selectBox");

		selectBox.setSelected(value);

		selectBox.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				if (S3Constans.DEBUG){
					S3Log.log("S3Gui::SelectBox", "Click on SelectBox: " + selectBox.getSelection().first()
					+ " event: " + event.toString());
				}
				element.setValue(selectBox.getSelection().first());
				if (S3Constans.DEBUG){
					S3Log.log("S3Gui::SelectBox", "New value SelectBox: " + element.getValueAsString());
				}

				if (outListener != null && disableListener == false){
					outListener.changed(event, actor);
				}
			}
		});

		//
		// Fix slider FixScroll
		//
		selectBox.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
			}
		});

		addRow(selectBox, null, label, 0, colSpan, newRow, true);
		return selectBox;
	}

	public SelectBox addSelectIndexBox(final String label, final Object object, final String fieldName, final String[] listItem){
		return addSelectIndexBox(label, object, fieldName, listItem, 0, true, 1, null);
	}

	public SelectBox addSelectIndexBox(final String label, final Object object, final String fieldName, final String[] listItem, final int value){
		return addSelectIndexBox(label, object, fieldName, listItem, value, true, 1, null);
	}

	public SelectBox addSelectIndexBox(final String label, final Object object, final String fieldName, final String[] listItem, final int value,
									   final boolean newRow){
		return addSelectIndexBox(label, object, fieldName, listItem, value, newRow, 1, null);
	}

	public SelectBox addSelectIndexBox(final String label, final Object object, final String fieldName, final String[] listItem, final int value,
									   final ChangeListener changeListener){
		return addSelectIndexBox(label, object, fieldName, listItem, value, true, 1, changeListener);
	}

	/**
	 * @param label
	 * @param object
	 * @param fieldName
	 * @param listItem
	 * @param value
	 * @param newRow
	 * @param colSpan
	 * @param changeListener
	 * @return
	 */
	public SelectBox addSelectIndexBox(final String label, final Object object, final String fieldName, final String[] listItem, final int value,
									   final boolean newRow, final int colSpan, final ChangeListener changeListener){

		final ChangeListener outListener = changeListener;

		final GuiElement element = new GuiElement(label, 0, object, fieldName, value);

		final SelectBox selectBox = GuiResource.selectBox(listItem, "selectBox");

		selectBox.setSelectedIndex(value);
		selectBox.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				if (S3Constans.DEBUG){
					S3Log.log("S3Gui::button", "Click on button: " + selectBox.getSelectedIndex() + " event: "
					+ event.toString());
				}
				element.setValue(selectBox.getSelectedIndex());
				if (S3Constans.DEBUG){
					S3Log.log("S3Gui::button", "New value element: " + element.getValueAsString());
				}
				if (outListener != null && disableListener == false){
					outListener.changed(event, actor);
				}
			}
		});

		//
		// Fix slider FixScroll
		//
		selectBox.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
			}
		});

		addRow(selectBox, null, label, 0, colSpan, newRow, true);
		return selectBox;
	}

	public List addColorList(String label, Object object, String fieldName){
		return addColorList(label, object, fieldName, 0, true, 1, null);
	}

	public List addColorList(String label, Object object, String fieldName, int value, boolean newRow){
		return addColorList(label, object, fieldName, value, newRow, 1, null);
	}

	public List addColorList(String label, Object object, String fieldName, int value, ChangeListener changeListener){
		return addColorList(label, object, fieldName, value, true, 1, changeListener);
	}

	/**
	 * @param label
	 * @param object
	 * @param fieldName
	 * @param value
	 * @param newRow
	 * @param colSpan
	 * @param changeListener
	 * @return
	 */
	public List addColorList(String label, Object object, String fieldName, int value, boolean newRow, int colSpan, ChangeListener changeListener){
		return addListIndex(label, object, fieldName, colorsName, value, newRow, colSpan, changeListener, S3Constans.listSizeY);
	}

	public ColorBrowser addColorBrowser(String label, Object object, String fieldName, Color value){
		return addColorBrowser(label, object, fieldName, value, true, 1, null);
	}

	public ColorBrowser addColorBrowser(String label, Object object, String fieldName, Color value, boolean newRow){
		return addColorBrowser(label, object, fieldName, value, newRow, 1, null);
	}

	public ColorBrowser addColorBrowser(String label, Object object, String fieldName, Color value, WidgetColorListener changeListener){
		return addColorBrowser(label, object, fieldName, value, true, 1, changeListener);
	}

	/**
	 * @param label
	 * @param object
	 * @param fieldName
	 * @param value
	 * @param newRow
	 * @param colSpan
	 * @param changeListener
	 * @return
	 */
	public ColorBrowser addColorBrowser(String label, Object object, String fieldName, Color value, boolean newRow, int colSpan,
										WidgetColorListener changeListener){

		final WidgetColorListener outListener = changeListener;

		final GuiElement element = new GuiElement(label, 0, object, fieldName, value);

		final ColorBrowser colorBrowser = new ColorBrowser();
		colorBrowser.set(value);
		final Table colorBrowserTable = colorBrowser.getTable();
		colorBrowser.setColorListener(new WidgetColorListener(){
			@Override
			public void Action(Color color){
				if (S3Constans.DEBUG){
					S3Log.log("S3Gui::addColorBrowser::Action", "Set color: " + color.toString());
				}

				element.setValue(color);
				if (outListener != null && disableListener == false){
					outListener.Action(color);
				}
			}
		});

		addRow(colorBrowserTable, null, label, 0, colSpan, newRow, true);
		return colorBrowser;
	}

	/**
	 * @param label
	 * @param object
	 * @param fieldName
	 * @param value
	 * @param newRow
	 * @param colSpan
	 * @param changeListener
	 * @return
	 */
	public ColorBrowser addColorBrowserChange(String label, Object object, String fieldName, Color value, boolean newRow, int colSpan,
											  ChangeListener changeListener){

		final ChangeListener outListener = changeListener;

		final GuiElement element = new GuiElement(label, 0, object, fieldName, value);

		final ColorBrowser colorBrowser = new ColorBrowser();
		colorBrowser.set(value);
		final Table colorBrowserTable = colorBrowser.getTable();
		colorBrowser.setColorListener(new WidgetColorListener(){
			@Override
			public void Action(Color color){
				if (S3Constans.DEBUG){
					S3Log.log("S3Gui::addColorBrowser::Action", "Set color: " + color.toString());
				}
				element.setValue(color);
				if (outListener != null && disableListener == false){
					outListener.changed(new ChangeEvent(), colorBrowserTable);
				}
			}
		});

		addRow(colorBrowserTable, null, label, 0, colSpan, newRow, true);
		return colorBrowser;
	}

	public FileBrowser addFileBrowser(String label, Object object, String fieldName, String value, WidgetStringListener changeListener){
		return addFileBrowser(label, object, fieldName, value, true, 2, false, changeListener, S3Constans.listSizeY
		+ S3Constans.buttonImage);
	}

	public FileBrowser addFileBrowser(String label, Object object, String fieldName, String value, boolean newRow, int colSpan, boolean showBlank,
									  WidgetStringListener changeListener){
		return addFileBrowser(label, object, fieldName, "", true, colSpan, false, changeListener, S3Constans.listSizeY
		+ S3Constans.buttonImage);
	}

	/**
	 * @param label
	 * @param object
	 * @param fieldName
	 * @param value
	 * @param newRow
	 * @param colSpan
	 * @param changeListener
	 * @return
	 */
	public FileBrowser addFileBrowser(String label, Object object, String fieldName, String value, boolean newRow, int colSpan, boolean showBlank,
									  WidgetStringListener changeListener, int cellWidth){

		final WidgetStringListener outListener = changeListener;
		final GuiElement element = new GuiElement(label, 0, object, fieldName, "");

		if (!element.getValueAsString().equalsIgnoreCase("")){
			value = element.getValueAsString();
		}

		FileBrowser fileBrowser = new FileBrowser();
		Table fileTable = fileBrowser.getTable(value, value, "fileBrowser");

		fileBrowser.setListener(new WidgetStringListener(){
			@Override
			public void Action(String text){
				if (S3Constans.DEBUG){
					S3Log.log("effectTextureFadeWindow::fileTableFrom::click", "selectFile=" + text);
				}
				element.setValue(text);
				if (outListener != null && disableListener == false){
					outListener.Action(text);
				}
			}
		});

		addRow(fileTable, null, label, cellWidth, colSpan, newRow, showBlank).fillY().expandY();
		return fileBrowser;
	}

	/**
	 * @param label
	 * @param object
	 * @param fieldName
	 * @param value
	 * @param newRow
	 * @param colSpan
	 * @param changeListener
	 * @return
	 */
	public FileBrowser addFileBrowserChange(String label, Object object, String fieldName, String value, boolean newRow, int colSpan,
											ChangeListener changeListener){

		final ChangeListener outListener = changeListener;

		final GuiElement element = new GuiElement(label, 0, object, fieldName, "");

		FileBrowser fileBrowser = new FileBrowser();
		final Table fileTable = fileBrowser.getTable(value, S3Constans.defaultDir, "fileBrowser");

		fileBrowser.setListener(new WidgetStringListener(){
			@Override
			public void Action(String text){
				if (S3Constans.DEBUG){
					S3Log.log("effectTextureFadeWindow::fileTableFrom::click", "selectFile=" + text);
				}
				element.setValue(text);
				if (outListener != null && disableListener == false){
					outListener.changed(new ChangeEvent(), fileTable);
				}
			}
		});

		addRow(fileTable, null, label, 0, colSpan, newRow, true);
		return fileBrowser;
	}

	/**
	 * @param label
	 * @param object
	 * @param fieldName
	 * @param value
	 * @param newRow
	 * @param colSpan
	 * @param changeListener
	 * @return
	 */
	public ScrollImageButton addScrollImageButton(String label, Object object, String fieldName, String[] listItem, int value, boolean newRow, int colSpan,
												  int buttonSize, int width, ChangeListener changeListener){

		final ChangeListener outListener = changeListener;

		final GuiElement element = new GuiElement(label, 0, object, fieldName, value);

		final ScrollImageButton scrollImgButton = new ScrollImageButton();

		ChangeListener localListener = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				element.setValue(scrollImgButton.getValue());
				if (outListener != null && disableListener == false){
					outListener.changed(event, actor);
				}
			}
		};

		scrollImgButton.show(listItem, newRow, value, buttonSize, localListener);

		addRow(scrollImgButton.getTable(), null, label, 0, colSpan, newRow, true);
		return scrollImgButton;
	}

	/**
	 * @param label
	 * @param object
	 * @param fieldName
	 * @param listItem
	 * @param value
	 * @param newRow
	 * @param colSpan
	 * @param buttonSize
	 * @param width
	 * @param height
	 * @param changeListener
	 * @return
	 */
	public ScrollBoxImageButton addScrollBoxImageButton(String label, Object object, String fieldName, String[] listItem, int value, boolean newRow,
														int colSpan, int buttonSize, int width, int height, int buttonX, boolean spaceBox,
														ChangeListener changeListener){

		final ChangeListener outListener = changeListener;

		final GuiElement element = new GuiElement(label, 0, object, fieldName, value);

		final ScrollBoxImageButton scrollImgButton = new ScrollBoxImageButton();

		ChangeListener localListener = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				element.setValue(scrollImgButton.getValue());
				if (outListener != null && disableListener == false){
					outListener.changed(event, actor);
				}
			}
		};

		scrollImgButton.show(listItem, value, height, width, buttonSize, buttonX, localListener);

		addRow(scrollImgButton.getTable(), null, label, 0, colSpan, newRow, spaceBox);
		return scrollImgButton;
	}

	/**
	 * @param label
	 * @param object
	 * @param fieldNameX
	 * @param fieldNameY
	 * @param fieldNameWidth
	 * @param fieldNameHeight
	 * @param newRow
	 * @param colSpan
	 * @param changeListener
	 * @return
	 */
	public Table addEditor2d(String label, Object object, String fieldNameX, String fieldNameY, String fieldNameWidth, String fieldNameHeight,
							 final float valueX, final float valueY, final float valueWidth, final float valueHeight, boolean newRow, int colSpan,
							 ChangeListener changeListener){

		final ChangeListener outListener = changeListener;

		final GuiElement elementX = new GuiElement(label, 0, object, fieldNameX, valueX);
		final GuiElement elementY = new GuiElement(label, 0, object, fieldNameY, valueY);
		final GuiElement elementW = new GuiElement(label, 0, object, fieldNameWidth, valueWidth);
		final GuiElement elementH = new GuiElement(label, 0, object, fieldNameHeight, valueHeight);

		final Button guiBtn = GuiResource.textButton("Edit", label + "Edit");
		final Label guiTableLabel = GuiResource.label("", "");
		guiTableLabel.setText("X: " + (int) valueX + " Y: " + (int) valueY + " W: " + (int) valueWidth + " H:"
							  + (int) valueHeight);
		guiBtn.addListener(new ChangeListener(){
			public void changed(ChangeEvent event, Actor actor){
				S3Log.log("S3Gui::button", "Click on Edit2d button ... event: " + event.toString());

				final Editor2d editor = new Editor2d();

				ChangeListener saveEditor = new ChangeListener(){
					@Override
					public void changed(ChangeEvent event, Actor actor){

						S3Log.log("S3Gui::button", "Save on Edit2d button ... event: " + event.toString() + " actor: "
						+ actor.toString());

						elementX.setValue(editor.getX());
						elementY.setValue(editor.getY());
						elementW.setValue(editor.getWidth());
						elementH.setValue(editor.getHeight());

						guiTableLabel.setText("X: " + (int) editor.getX() + " Y: " + (int) editor.getY() + " W: "
											  + (int) editor.getWidth() + " H:" + (int) editor.getHeight());
						if (outListener != null && disableListener == false){
							outListener.changed(event, actor);
						}
					}
				};
				editor.create(Float.parseFloat(elementX.getValueAsString()), Float.parseFloat(elementY
																							  .getValueAsString()),
							  Float.parseFloat(elementW.getValueAsString()), Float.parseFloat(elementH
																							  .getValueAsString()), saveEditor);
				editor.show();
			}
		});

		Table localTable = GuiResource.table("");
		localTable.row();
		localTable.add(guiBtn).left();
		localTable.add(guiTableLabel).left().expandX().fillX();

		addRow(localTable, null, label, 0, colSpan, newRow, true);
		return localTable;
	}

	/**
	 * @param label
	 * @param object
	 * @param fieldName
	 * @param listItem
	 * @param newRow
	 * @param colSpan
	 * @param changeListener
	 * @return
	 */
	public ImageList addImageList(String label, Object object, String fieldName, String[] listItem, boolean newRow, int colSpan, ChangeListener changeListener){

		final ChangeListener outListener = changeListener;

		final GuiElement element = new GuiElement(label, 0, object, fieldName, listItem);
		final ImageList imageList = new ImageList();

		ChangeListener localListener = new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){

				S3Log.log("S3Gui::addImageList::changed()", "Change value: " + event + " event: " + actor);
				element.setValue(imageList.getFilesPatch());

				if (outListener != null && disableListener == false){
					outListener.changed(event, actor);
				}
			}
		};
		imageList.show(listItem, localListener);

		addRow(imageList.getTable(), null, label, 0, colSpan, newRow, true);
		return imageList;
	}
}