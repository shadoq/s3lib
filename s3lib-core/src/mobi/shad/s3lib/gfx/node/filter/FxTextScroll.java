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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gfx.g2d.SolidFont;
import mobi.shad.s3lib.gfx.node.core.Node;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3Lang;
import mobi.shad.s3lib.main.S3Screen;

/**
 * @author Jarek
 */
public class FxTextScroll extends Node{

	private Color starColor = Color.WHITE;
	private String scrollText = "";
	private String fileFont = "font/0.png";
	private int fontWidth;
	private int fontHeight;
	private float speed;

	public FxTextScroll(){
		this(null, null);
	}

	public FxTextScroll(GuiForm effectData, ChangeListener changeListener){
		super("FxTextScroll_" + countId, Type.MESH_2D_POINT, effectData, changeListener);
		initData();
		initForm();
	}

	@Override
	public final void initForm(){
		if (formGui == null){
			return;
		}

		disableChange = true;
		formGui.addLabel(S3Lang.get("fxTextScroll"), S3Lang.get("fxTextScroll"), Color.YELLOW);
		formGui.addTextField("scrollText", S3Lang.get("scrollText"), scrollText, S3Lang.get("scrollInputText"), localChangeListener);
		formGui.add("speed", S3Lang.get("speed"), 16, 1f, 128, 1f, localChangeListener);
		formGui.add("fontWidth", S3Lang.get("fontWidth"), 16, 8, 128, 8f, localChangeListener);
		formGui.add("fontHeight", S3Lang.get("fontHeight"), 16, 8, 128, 8f, localChangeListener);

		formGui.addFileBrowser("fileFont", S3Lang.get("char_fonts"), fileFont, "font", localChangeListener);

		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){
		if (formGui != null){
			scrollText = formGui.getString("scrollText");
			speed = formGui.getFloat("speed") * S3Screen.aspectRatioX;
			fontWidth = (int) (formGui.getInt("fontWidth") * S3Screen.aspectRatioX);
			fontHeight = (int) (formGui.getInt("fontHeight") * S3Screen.aspectRatioY);
			fileFont = formGui.getString("fileFont");
		}

		data.solidFont = new SolidFont(fileFont, scrollText, (int) data.startX, (int) data.startY, (int) data.width, (int) data.startY, fontWidth, fontHeight);
	}

	/**
	 * @param effectTime
	 * @param sceneTime
	 * @param endTime
	 * @param procent
	 * @param isPause
	 */
	@Override
	protected void updateLocal(float effectTime, float sceneTime, float endTime, float procent, boolean isPause){
		data.solidFont.animeScrollVertical(S3.osDeltaTime, speed);
	}
}
