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
package mobi.shad.s3lib.gfx.node.pixmap.anim;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gfx.node.core.Data;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gfx.pixmap.procedural.Plasma;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Lang;

/**
 * @author Jarek
 */
public class FxPlasma extends Node{

	private int plasmaSpeed = 10;
	private int plasmaScrollSpeed = 10;
	private int cellSizeX = 6;
	private int cellSizeY = 6;

	public FxPlasma(){
		this(null, null);
	}

	/**
	 * @param formGui
	 * @param changeListener
	 */
	public FxPlasma(GuiForm effectData, ChangeListener changeListener){
		super("FxPlasma_" + countId, Type.TEXTURE, effectData, changeListener);
		initData();
		initForm();
	}

	/**
	 *
	 */
	@Override
	public final void initForm(){
		if (formGui == null){
			return;
		}
		disableChange = true;
		formGui.addLabel(S3Lang.get("FxPlasma"), S3Lang.get("FxPlasma"), Color.YELLOW);
		formGui.add("FxPlasma_plasmaSpeed", S3Lang.get("plasmaSpeed"), plasmaSpeed, -100f, 100f, 2f, localChangeListener);
		formGui.add("FxPlasma_plasmaScrollSpeed", S3Lang.get("plasmaScrollSpeed"), plasmaScrollSpeed, -100f, 100f, 2f, localChangeListener);
		formGui.add("FxPlasma_cellSizeX", S3Lang.get("cellSizeX"), cellSizeX, -100f, 100f, 2f, localChangeListener);
		formGui.add("FxPlasma_cellSizeY", S3Lang.get("cellSizeY"), cellSizeY, -100f, 100f, 2f, localChangeListener);
		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){

		if (formGui != null){
			plasmaSpeed = formGui.getInt("FxPlasma_plasmaSpeed");
			plasmaScrollSpeed = formGui.getInt("FxPlasma_plasmaScrollSpeed");
			cellSizeX = formGui.getInt("FxPlasma_cellSizeX");
			cellSizeY = formGui.getInt("FxPlasma_cellSizeY");
		}
		if (data.pixmap == null){
			data.pixmap = new Pixmap(S3Constans.proceduralTextureSize, S3Constans.proceduralTextureSize, Pixmap.Format.RGBA8888);
			data.pixmap.setColor(Color.BLACK);
			data.pixmap.fill();
			data.texture = null;
		}
		data.type = Data.Type.EFFECT_2D;
		Plasma.generate(data.pixmap, plasmaSpeed, plasmaScrollSpeed, cellSizeX, cellSizeY);
		data.textureChange = true;
	}

	@Override
	protected void updateLocal(float effectTime, float sceneTime, float endTime, float procent, boolean isPause){
		if (isPause){
			return;
		}
		processLocal();
	}
}
