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
import mobi.shad.s3lib.gfx.pixmap.procedural.Cell;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Lang;

/**
 * @author Jarek
 */
public class FxCell extends Node{

	private float regularity = 2.0f;
	private int density = 16;
	private int seed = 32;
	private Color color = Color.WHITE;
	private int chessboard = 0;
	private int pattern = 0;

	public FxCell(){
		this(null, null);
	}

	/**
	 * @param effectData
	 * @param changeListener
	 */
	public FxCell(GuiForm effectData, ChangeListener changeListener){
		super("FxCell_" + countId, Type.TEXTURE, effectData, changeListener);
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
		formGui.addLabel(S3Lang.get("FxCell"), S3Lang.get("FxCell"), Color.YELLOW);

		formGui.addSelectIndex("FxCellChessboard", S3Lang.get("chessboard"), chessboard, new String[]{S3Lang.get("off"), S3Lang.get("chessboard")},
							   localChangeListener);
		formGui.addSelectIndex("FxCellPattern", S3Lang.get("pattern"), chessboard, new String[]{S3Lang.get("both"), S3Lang.get("cross"), S3Lang.get("coner")},
							   localChangeListener);

		formGui.add("FxCellRegularity", S3Lang.get("regularity"), regularity, 1f, 4, 0.1f, localChangeListener);
		formGui.add("FxCellDensity", S3Lang.get("density"), density, 1f, 64f, 1.0f, localChangeListener);
		formGui.addRandomButton("FxCellRandom", S3Lang.get("random"), new String[]{"FxCellRegularity", "FxCellDensity"}, localChangeListener);
		formGui.addResetButton("FxCellReset", S3Lang.get("reset"), new String[]{"FxCellRegularity", "FxCellDensity"}, localChangeListener);

		formGui.add("FxCellSeed", S3Lang.get("seed"), seed, 1f, 512, 1f, localChangeListener);

		formGui.addColorSelect("FxCellColor", S3Lang.get("color"), color, localChangeListener);


		disableChange = false;
	}

	/**
	 *
	 */
	@Override
	protected void processLocal(){

		if (formGui != null){
			regularity = formGui.getInt("FxCellRegularity");
			density = formGui.getInt("FxCellDensity");
			seed = formGui.getInt("FxCellSeed");
			color = formGui.getColor("FxCellColor");
			chessboard = formGui.getInt("FxCellChessboard");
			pattern = formGui.getInt("FxCellPattern");
		}
		if (data.pixmap == null){
			data.pixmap = new Pixmap(S3Constans.proceduralTextureSizeHight, S3Constans.proceduralTextureSizeHight, Pixmap.Format.RGBA8888);
			data.pixmap.setColor(Color.BLACK);
			data.pixmap.fill();
			data.texture = null;
		}
		data.type = Data.Type.EFFECT_2D;
		Cell.setSeed(seed);
		Cell.generate(data.pixmap, regularity, density, color, pattern, chessboard);
		data.textureChange = true;
	}
}
