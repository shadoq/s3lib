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
package mobi.shad.s3lib.gfx.node.pixmap.procedural;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gfx.node.core.Data;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Lang;
import mobi.shad.s3lib.main.S3ResourceManager;

/**
 * @author Jarek
 */
public class FxTextureLoader extends Node{

	private String fileTextureName;

	public FxTextureLoader(){
		this(null, null);
	}

	/**
	 *
	 * @param effectData
	 * @param changeListener
	 */
	public FxTextureLoader(GuiForm effectData, ChangeListener changeListener){
		super("FxTextureLoader_" + countId, Type.TEXTURE, effectData, changeListener);
		initData();
		initForm();
	}

	@Override
	public final void initForm(){
		if (formGui == null){
			return;
		}
		disableChange = true;
		formGui.addLabel(S3Lang.get("fxTextureLoader"), S3Lang.get("fxTextureLoader"), Color.YELLOW);
		formGui.addFileBrowser("FxTextureLoaderFile", "Texture File", "texture/texture_0.jpg", "texture", localChangeListener);
		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){
		if (formGui != null){
			fileTextureName = formGui.getString("FxTextureLoaderFile");
		}
		data.type = Data.Type.EFFECT_2D;
		if (data.pixmap == null){
			Pixmap px = S3ResourceManager.getPixmap(fileTextureName, S3Constans.textureImageSizeHight);
			data.pixmap = new Pixmap(px.getWidth(), px.getHeight(), px.getFormat());
			data.pixmap.drawPixmap(px, 0, 0);
		} else {
			data.pixmap.drawPixmap(S3ResourceManager.getPixmap(fileTextureName, data.pixmap.getWidth()), 0, 0);
		}
		data.texture = null;
		data.textureChange = true;
	}
}
