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
package mobi.shad.s3lib.gfx.node.filter;

import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gfx.node.core.Data;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Lang;
import mobi.shad.s3lib.main.S3Screen;

/**
 * @author Jarek
 */
public class FxEffectSize extends Node{

	public FxEffectSize(){
		this(null, null);
	}

	public FxEffectSize(GuiForm effectData, ChangeListener changeListener){
		super("FxEffectSize_" + countId, Type.EFFECT_SIZE, effectData, changeListener);
		initData();
		initForm();
	}

	@Override
	public final void initForm(){
		if (formGui == null){
			return;
		}

		disableChange = true;
		formGui.addEditorBox("editor_Box", S3Lang.get("editor_Box"), 0f, 0f, S3Screen.width, S3Screen.height, localChangeListener);
		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){
		data.type = Data.Type.EFFECT_2D;
		if (formGui != null){
			data.startX = formGui.getX("editor_Box");
			data.startY = formGui.getY("editor_Box");

			data.width = formGui.getWidth("editor_Box");
			data.height = formGui.getHeight("editor_Box");
		}
		data.endX = data.startX + data.width;
		data.endY = data.startY + data.height;

		data.centerX = data.startX + data.width / 2;
		data.centerY = data.startY + data.height / 2;
	}
}
