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
package mobi.shad.s3libTest;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3File;
import mobi.shad.s3lib.main.S3Gfx;
import mobi.shad.s3lib.main.S3Screen;

/**
 * @author Jarek
 */
public class G2DTTFFont extends S3App{

	private BitmapFont orangeJuice40;
	private BitmapFont orangeJuice80;
	private String pl = " - Polskie znaczki: ąęśćłóńżź ĄĘŚĆŁÓŃŻŹ";

	@Override
	public void initalize(){

		FileHandle orangeJuice = S3File.getFileHandle("font/orange_juice_2.ttf");

		/**
		 * Generates the fonts images from the TTF file
		 */
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(orangeJuice);
		orangeJuice40 = generator.generateFont(40);
		orangeJuice80 = generator.generateFont(80);
		orangeJuice80.setColor(Color.PINK);
		generator.dispose();
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx gfx){
		gfx.clear(0.2f, 0.0f, 0.0f);
		float deltaY = S3Screen.height / 6.0f;
		gfx.drawCenterString(deltaY / 2 + deltaY * 1, "OrangeJuice 40" + pl, orangeJuice40);
		gfx.drawCenterString(deltaY / 2 + deltaY * 2, "OrangeJuice 80" + pl, orangeJuice80);
		gfx.drawFPS();
	}
}
