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
package mobi.shad.s3lib.gui.dialog;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.gui.GuiUtil;
import mobi.shad.s3lib.gui.widget.WidgetBoolListener;
import mobi.shad.s3lib.gui.widget.WidgetInterface;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3Lang;
import mobi.shad.s3lib.main.S3Log;

/**
 * @author Jarek
 */
public class Confirm extends DialogBase implements WidgetInterface, InputProcessor{

	private boolean getOk = false;
	private WidgetBoolListener listener = null;

	/**
	 * @param dir
	 */
	public void show(String alertString){

		S3.addInputProcessor(this);
		super.create();

		getOk = false;

		final Label alertLabel = GuiResource.label(alertString, "labelFile");

		alertLabel.setWrap(true);
		alertLabel.setAlignment(Align.center | Align.center);

		final Button buttonOk = GuiResource.textButton("Ok", "confirmButtonOk");
		final Button buttonCancel = GuiResource.textButton("Cancel", "confirmButtonCancel");

		//
		// Utworzenie okna
		//
		mainWindow.setTitle(S3Lang.get("Confirm"));
		mainWindow.row();
		mainWindow.add(alertLabel).colspan(2).minWidth(250).minHeight(110).fill();
		mainWindow.row();
		mainWindow.add(buttonOk).fillX();
		mainWindow.add(buttonCancel).fillX();
		mainWindow.pack();
		mainWindow.setModal(true);

		GuiUtil.windowPosition(mainWindow, 5, 5);
		S3.stage.addActor(mainWindow);

		//
		// Akcje GUI
		//
		buttonOk.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				getOk = true;
				if (listener != null){
					listener.Action(getOk);
				}
				hide();
			}
		});

		buttonCancel.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				getOk = false;
				if (listener != null){
					listener.Action(getOk);
				}
				hide();
			}
		});
		super.show();
	}

	/**
	 * @param listener
	 */
	public void setListener(WidgetBoolListener listener){
		this.listener = listener;
	}

	/**
	 * @return
	 */
	public boolean get(){
		return getOk;
	}

	@Override
	public void hide(){
		super.hide();
		S3.removeLastInputProcessor();
	}

	@Override
	public boolean keyDown(int keycode){
		S3Log.log("keyDown", "code=" + keycode);
		return true;
	}

	@Override
	public boolean keyUp(int keycode){
		S3Log.log("keyUp", "code=" + keycode);
		if (keycode == Keys.ESCAPE){
			getOk = false;
			if (listener != null){
				listener.Action(getOk);
			}
			hide();
		} else if (keycode == Keys.ENTER){
			getOk = true;
			if (listener != null){
				listener.Action(getOk);
			}
			hide();
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character){
		S3Log.log("keyTyped", "code=" + character);
		return true;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button){
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button){
		return true;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer){
		return true;
	}

	@Override
	public boolean scrolled(int amount){
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY){
		return true;
	}
}
