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

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.gui.widget.WidgetInterface;

/**
 * @author Jarek
 */
public class DialogBase implements WidgetInterface{

	protected float fadeDuration = 0.2f;
	protected Window mainWindow;

	public void create(){
		mainWindow = GuiResource.windowDialog("WidgetBase", "WidgetBase");
		mainWindow.setColor(1f, 1f, 1f, 0f);
		mainWindow.setVisible(false);
		mainWindow.setModal(true);
		mainWindow.setMovable(false);
	}

	@Override
	public void show(){
		mainWindow.setVisible(true);
		if (fadeDuration > 0){
			mainWindow.addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
		}
	}

	@Override
	public void hide(){
		mainWindow.addAction(Actions.sequence(Actions.fadeOut(fadeDuration, Interpolation.fade), Actions.removeActor()));
	}
}
