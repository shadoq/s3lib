/*******************************************************************************
 * Copyright 2012
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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import mobi.shad.s3lib.gfx.util.GradientUtil;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Gfx;

/**
 * @author Jarek
 */
public class S3GfxGradientTest extends S3App{

	private Pixmap pixmap;
	private Texture texture;
	private Color col;

	@Override
	public void initalize(){

		pixmap = new Pixmap(S3Constans.proceduralTextureSize, S3Constans.proceduralTextureSize, Pixmap.Format.RGBA8888);
		texture = new Texture(pixmap);
		texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

		for (int y = 0; y < S3Constans.proceduralTextureSize; y++){
			for (int x = 0; x < (S3Constans.proceduralTextureSize / 2); x++){

				col = GradientUtil.getColorPalleteUncache((float) x / S3Constans.proceduralTextureSize * 2, y / 5);
				pixmap.drawPixel(x, y, Color.rgba8888(col.r, col.g, col.b, col.a));
				col = GradientUtil.getColorPallete((float) x / S3Constans.proceduralTextureSize * 2, y / 5);
				pixmap.drawPixel(x + S3Constans.proceduralTextureSize / 2, y, Color.rgba8888(col.r, col.g, col.b, col.a));
			}
		}

		texture.draw(pixmap, 0, 0);
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx g){
		g.clear();
		g.drawBackground(texture);
	}
}
