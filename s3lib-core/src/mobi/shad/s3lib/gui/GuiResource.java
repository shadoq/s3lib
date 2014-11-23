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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox.CheckBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox.SelectBoxStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import mobi.shad.s3lib.gui.dialog.Alert;
import mobi.shad.s3lib.main.*;

/**
 * Factory class to create GUI elements with default parameters
 *
 * @author Jarek
 */
public class GuiResource{

	private static final String TAG = GuiResource.class.getSimpleName();

	private GuiResource(){

	}

	/**
	 * Zwraca image button
	 *
	 * @param fileName
	 * @return
	 */
	public static Button button(String fileName){
		if (S3Constans.INFO){
			S3Log.info(TAG, "Create button: " + fileName);
		}
		Texture texture = S3ResourceManager.getTexture(fileName, S3Constans.textureButtonSize);
		Button button = new Button(new Image(texture), S3Skin.skin);
		button.pad(S3Constans.buttonPadding);
		button.setWidth(S3Constans.minButtonSizeX);
		return button;
	}

	/**
	 * Zwraca image button
	 *
	 * @param region
	 * @return
	 */
	public static Button button(Texture region){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create button texture: " + region);
		}
		Button button = new Button(new Image(region), S3Skin.skin);
		button.pad(S3Constans.buttonPadding);
		button.setWidth(S3Constans.minButtonSizeX);
		return button;
	}

	/**
	 * Zwraca elementu GUI - Image Button
	 *
	 * @param region
	 * @param name
	 * @return
	 */
	public static Button button(TextureRegion region, String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create button texture: " + name);
		}
		Button button = new Button(new Image(region), S3Skin.skin);
		button.pad(S3Constans.buttonPadding);
		button.setWidth(S3Constans.minButtonSizeX);
		button.setName(name);
		return button;
	}

	/**
	 * @param fileName
	 * @return
	 */
	public static Button buttonToogle(String fileName){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create button toogle FileName: " + fileName);
		}
		Texture texture = S3ResourceManager.getTexture(fileName, S3Constans.textureButtonSize);
		Button button = new Button(new Image(texture), S3Skin.skin, "toggle");
		button.pad(S3Constans.buttonPadding);
		button.setWidth(S3Constans.minButtonSizeX);
		return button;
	}

	/**
	 * Zwraca elementu GUI - Image Button Toggle
	 *
	 * @param region
	 * @param name
	 * @return
	 */
	public static Button buttonToggle(TextureRegion region, String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create button toggle: " + name);
		}
		Button button = new Button(new Image(region), S3Skin.skin, "toggle");
		button.pad(S3Constans.buttonPadding);
		button.setName(name);
		return button;
	}

	/**
	 * @param text
	 * @param name
	 * @return
	 */
	public static TextField textField(String text, String messageText, String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create TextField: " + name);
		}
		TextField textfield = new TextField(text, S3Skin.skin.get(TextFieldStyle.class));
		textfield.setName(name);
		textfield.setMessageText(messageText);
		textfield.setTextFieldListener(new TextField.TextFieldListener(){
			@Override
			public void keyTyped(TextField textField, char key){
				if (key == '\n'){
					textField.getOnscreenKeyboard().show(false);
				}
			}
		});
		return textfield;
	}

	/**
	 * @param widget
	 * @param name
	 * @return
	 */
	public static ScrollPane scrollPaneTransparent(Actor widget, String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create guiScrollPaneTransparent: " + name);
		}
		ScrollPane scrollPane = new ScrollPane(widget, S3Skin.skin, "default-no-background");
		scrollPane.setName(name);
		return scrollPane;
	}

	/**
	 * @param widget
	 * @param widget2
	 * @param name
	 * @return
	 */
	public static SplitPane splitPaneVertical(Actor widget, Actor widget2, String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create guiSplitPaneHorizontal: " + name);
		}
		final SplitPane splitPane = new SplitPane(widget, widget2, true, S3Skin.skin, "default-vertical");
		splitPane.setName(name);
		return splitPane;
	}

	/**
	 * @param widget
	 * @param name
	 * @return
	 */
	public static ScrollPane scrollPane(Actor widget, String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create guiScrollPane: " + name);
		}
		ScrollPane scrollPane = new ScrollPane(widget, S3Skin.skin.get(ScrollPaneStyle.class));
		scrollPane.setName(name);
		return scrollPane;
	}

	/**
	 * @param name
	 * @return
	 */
	public static Table table(String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create Window: " + name);
		}
		final Table table = new Table(S3Skin.skin);
		table.setName(name);
		table.center();
		table.defaults().pad(S3Constans.cellPaddding);
		table.defaults().minWidth(S3Constans.minButtonSizeX);
		return table;
	}

	/**
	 * Zwraca element GUI - Window
	 *
	 * @param text
	 * @param name
	 * @return
	 */
	public static Window window(String text, String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create Window: " + name);
		}
		Window window = new Window(text, S3Skin.skin);
		window.setName(name);
		window.setX(0);
		window.setY(0);
		window.center();
		window.defaults().pad(S3Constans.cellPaddding);
		window.defaults().minWidth(S3Constans.minButtonSizeX);
		return window;
	}

	public static Window windowDialog(String text, String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create Window: " + name);
		}
		Window window = new Window(text, S3Skin.skin, "dialog");
		window.setName(name);
		window.setX(0);
		window.setY(0);
		window.center();
		window.defaults().pad(S3Constans.cellPaddding);
		window.defaults().minWidth(S3Constans.minButtonSizeX);
		return window;
	}

	/**
	 * @return
	 */
	public static Window windowNoBackground(){
		Window window = new Window("", S3Skin.skin, "default-transparent");
		window.setMovable(false);
		window.setX(0);
		window.setY(0);
		window.center();
		window.defaults().pad(S3Constans.cellPaddding);
		return window;
	}

	/**
	 * Zwraca element GUI - Window
	 *
	 * @return
	 */
	public static Window windowBackend(){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create guiWindowBackend ");
		}
		Window windowBackend = new Window("", S3Skin.skin, "default-backend");
		windowBackend.setX(0);
		windowBackend.setY(0);
		windowBackend.setWidth(S3Screen.width);
		windowBackend.setHeight(S3Screen.height);
		windowBackend.setMovable(false);
		windowBackend.setModal(false);
		return windowBackend;
	}

	/**
	 * @param text
	 * @param name
	 * @return
	 */
	public static CheckBox checkBox(String text, String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create CheckBox: " + name);
		}
		CheckBox checkBox = new CheckBox(text, S3Skin.skin.get(CheckBoxStyle.class));
		checkBox.align(Align.left);
		checkBox.setName(name);
		return checkBox;
	}

	/**
	 * @param listItem
	 * @param name
	 * @return
	 */
	public static List<String> list(String[] listItem, String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create List: " + name);
		}
		if (listItem == null){
			listItem = new String[]{"-= Select =-"};
		}
		List<String> list = new List<String>(S3Skin.skin.get(ListStyle.class));
		list.setItems(listItem);
		list.setName(name);
		return list;
	}

	/**
	 * Zwraca element GUI - slider
	 *
	 * @param min
	 * @param max
	 * @param step
	 * @param name
	 * @return
	 */
	public static Slider slider(float min, float max, float step, String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create Slider: " + name);
		}

		Slider slider = new Slider(min, max, step, false, S3Skin.skin);
		slider.setName(name);
		return slider;
	}

	/**
	 * Zwraca element GUI - label
	 *
	 * @param text
	 * @param name
	 * @return
	 */
	public static Label label(String text, String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create Label: " + name);
		}
		Label label = new Label(text, S3Skin.skin.get(LabelStyle.class));
		label.setName(name);
		return label;
	}

	/**
	 * @param widget
	 * @param name
	 * @return
	 */
	public static ScrollPane flickScrollPane(Actor widget, String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create FlickScrollPane: " + name);
		}
		ScrollPane scrollPane = new ScrollPane(widget);
		scrollPane.setFlickScroll(true);
		scrollPane.setName(name);
		return scrollPane;
	}

	/**
	 * @param region
	 * @param name
	 * @return
	 */
	public static ScrollPane flickScrollPane(TextureRegion region, String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create FlickScrollPane: " + name);
		}
		ScrollPane scrollPane = new ScrollPane(new Image(region));
		scrollPane.setFlickScroll(true);
		scrollPane.setName(name);
		return scrollPane;
	}

	/**
	 * @param widget
	 * @param widget2
	 * @param name
	 * @return
	 */
	public static SplitPane splitPaneHorizontal(Actor widget, Actor widget2, String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create guiSplitPaneHorizontal: " + name);
		}
		final SplitPane splitPane = new SplitPane(widget, widget2, false, S3Skin.skin, "default-horizontal");
		splitPane.setName(name);
		return splitPane;
	}

	/**
	 * @param item
	 * @param name
	 * @return
	 */
	public static SelectBox selectBox(String[] item, String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create SelectBox: " + name);
		}
		SelectBox selectbox = new SelectBox(S3Skin.skin.get(SelectBoxStyle.class));
		selectbox.setItems(item);
		selectbox.setName(name);
		return selectbox;
	}

	/**
	 * Zwraca elementu GUI - TextButton
	 *
	 * @param text
	 * @param name
	 * @return
	 */
	public static TextButton textButton(String text, String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create TextButton: " + name);
		}
		TextButton button = new TextButton(text, S3Skin.skin);
		button.pad(S3Constans.buttonPadding);
		button.setName(name);
		return button;
	}

	/**
	 * @param text
	 * @param name
	 * @return
	 */
	public static TextButton textButtonToggle(String text, String name){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create TextButton: " + name);
		}
		TextButton button = new TextButton(text, S3Skin.skin, "toggle");
		button.setName(name);
		button.pad(S3Constans.buttonPadding);
		button.setWidth(S3Constans.minButtonSizeX);
		return button;
	}

	/**
	 * @param fileName
	 * @return
	 */
	public static Image image(String fileName){
		if (S3Constans.INFO){
			S3Log.log(TAG, "Create Button Toggle: " + fileName);
		}
		Image image = new Image(S3ResourceManager.getTexture(fileName, S3Constans.textureImageSize));
		return image;
	}

	/**
	 * @param message
	 */
	public static void alertMemory(String message){

		Runtime rt = Runtime.getRuntime();
		long freeMem = (rt.freeMemory() / 1024 / 1024);
		long maxMem = (rt.maxMemory() / 1024 / 1024);
		long totalMem = (rt.totalMemory() / 1024 / 1024);
		long javaHeap = (Gdx.app.getJavaHeap() / 1024 / 1024);
		long nativeHeap = (Gdx.app.getNativeHeap() / 1024 / 1024);

		S3Log.warn(TAG + "::alertMemory", "Memory: -----------------------------");
		S3Log.warn(TAG + "::alertMemory", "Free memory: " + (rt.freeMemory() / 1024 / 1024) + " MB");
		S3Log.warn(TAG + "::alertMemory", "Max memory: " + (rt.maxMemory() / 1024 / 1024) + " MB");
		S3Log.warn(TAG + "::alertMemory", "Total memory: " + (rt.totalMemory() / 1024 / 1024) + " MB");
		S3Log.warn(TAG + "::alertMemory", "Java Heap: " + (Gdx.app.getJavaHeap() / 1024 / 1024) + " MB");
		S3Log.warn(TAG + "::alertMemory", "Native Heap: " + (Gdx.app.getNativeHeap() / 1024 / 1024) + " MB");
		S3Log.warn(TAG + "::alertMemory", "Memory: -----------------------------");

		Alert alert = new Alert();
		alert.show(
		message + "\n\nFree Memory: " + freeMem + " MB \nMax memory: " + maxMem + " MB \n Total memory: " + totalMem + " MB\n Java Heap: " + javaHeap + " MB\n Native Heap: " + nativeHeap + " MB");

		System.gc();
	}
}
