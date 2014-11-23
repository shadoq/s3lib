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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import mobi.shad.s3lib.main.*;

/**
 * @author Jarek
 */
public class VirtualSpritePicking extends S3App{

	Vector3 tempVector = new Vector3();
	Vector3 intersection = new Vector3();
	Vector3 unprojectedVertex = new Vector3();
	Rectangle boundingRectangle;
	private int numSprite = 200;
	private Sprite[] sprites = new Sprite[numSprite];
	private Texture tex;

	@Override
	public void initalize(){
		tex = S3ResourceManager.getTexture("sprite/bobs1.png", 0);
		for (int i = 0; i < numSprite; i++){
			sprites[i] = new Sprite(tex);
			sprites[i].setColor(Color.WHITE);
			sprites[i].setPosition((int) (Math.random() * S3Screen.width), (int) (Math.random() * S3Screen.height));
			sprites[i].setOrigin(16, 16);
		}
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx gfx){
		S3Gfx.clear(0.2f, 0.0f, 0.0f);
		S3.spriteBatch.setProjectionMatrix(S3.fixedCamera.combined);
		S3.spriteBatch.begin();
		for (int i = 0; i < numSprite; i++){
			sprites[i].setColor(Color.WHITE);
			boundingRectangle = sprites[i].getBoundingRectangle();
			if (boundingRectangle.contains(unprojectedVertex.x, unprojectedVertex.y)){
				sprites[i].setColor(Color.YELLOW);
			}
			sprites[i].draw(S3.spriteBatch);
		}
		S3.spriteBatch.end();
	}

	@Override
	public void onTouchDown(int x, int y, int button){
		unprojectedVertex.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		S3.fixedCamera.unproject(unprojectedVertex);
		S3Log.log("SpritePicking", x + " " + y + " " + button + " " + unprojectedVertex.toString());
	}
}
