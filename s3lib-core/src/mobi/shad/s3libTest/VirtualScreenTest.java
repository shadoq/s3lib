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

import com.badlogic.gdx.graphics.Color;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3lib.main.S3Gfx;
import mobi.shad.s3lib.main.S3Screen;

/**
 * @author Jarek
 */
public class VirtualScreenTest extends S3App{

	@Override
	public void initalize(){
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx gfx){
		gfx.clear(0.2f, 0.2f, 0.2f);
		S3Gfx.setColor(Color.GRAY);
		S3Gfx.drawGrid(0, 0, S3Screen.width, S3Screen.height, 10, 10);
		S3Gfx.setColor(Color.RED);
		S3Gfx.drawCircle(S3Screen.centerX, S3Screen.centerY, 100);
		S3Gfx.setColor(Color.YELLOW);
		S3Gfx.drawCircle(S3Screen.centerX, S3Screen.centerY, 200);

	}
}
