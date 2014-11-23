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
import mobi.shad.s3lib.main.*;

/**
 * @author Jarek
 */
public class MultiAppScreenTest extends S3AppManager{

	private static final String TAG = "MultiAppScreenTest";
	private static final boolean enableDebug = true;

	S3App app1 = new MultiAppScreen1();
	S3App app2 = new MultiAppScreen2();
	S3App app3 = new MultiAppScreen3();

	float time = 0;

	public class MultiAppScreen1 extends S3App{

		@Override
		public void initalize(){
			if (enableDebug){
				S3Log.event(TAG, "Initalize MultiAppScreen1 ...");
			}
		}

		@Override
		public void update(){
		}

		@Override
		public void render(S3Gfx gfx){
			S3Gfx.clear(0.2f, 0.2f, 0.2f);
			S3Gfx.setColor(Color.GRAY);
			S3Gfx.drawGrid(0, 0, S3Screen.width, S3Screen.height, 10, 10);
			S3Gfx.setColor(Color.RED);
			S3Gfx.drawCircle(S3Screen.centerX, S3Screen.centerY, 100);
			S3Gfx.setColor(Color.YELLOW);
			S3Gfx.drawCircle(S3Screen.centerX, S3Screen.centerY, 200);

		}
	}

	public class MultiAppScreen2 extends S3App{

		@Override
		public void initalize(){
			if (enableDebug){
				S3Log.event(TAG, "Initalize MultiAppScreen2 ...");
			}
		}

		@Override
		public void update(){
		}

		@Override
		public void render(S3Gfx gfx){
			S3Gfx.clear(0.5f, 0.0f, 0.0f);
			S3Gfx.setColor(Color.GRAY);
			S3Gfx.drawGrid(0, 0, S3Screen.width, S3Screen.height, 10, 10);
			S3Gfx.setColor(Color.RED);
			S3Gfx.drawCircle(S3Screen.centerX, S3Screen.centerY, 100);
			S3Gfx.setColor(Color.YELLOW);
			S3Gfx.drawCircle(S3Screen.centerX, S3Screen.centerY, 200);

		}
	}

	public class MultiAppScreen3 extends S3App{

		@Override
		public void initalize(){
			if (enableDebug){
				S3Log.event(TAG, "Initalize MultiAppScreen3 ...");
			}
		}

		@Override
		public void update(){
		}

		@Override
		public void render(S3Gfx gfx){
			S3Gfx.clear(0.5f, 0.0f, 0.0f);
			S3Gfx.setColor(Color.GRAY);
			S3Gfx.drawGrid(0, 0, S3Screen.width, S3Screen.height, 10, 10);
			S3Gfx.setColor(Color.RED);
			S3Gfx.drawCircle(S3Screen.centerX, S3Screen.centerY, 100);
			S3Gfx.setColor(Color.YELLOW);
			S3Gfx.drawCircle(S3Screen.centerX, S3Screen.centerY, 200);

		}
	}

	public MultiAppScreenTest(){
	}

	@Override
	public void initalize(){
		add(app3);
		add(app2);
		add(app1);
	}

	@Override
	public void render(S3Gfx gfx){
		time = time + S3.osDeltaTime;
		if (time > 3.0){
			switchTo(app2, true);
		}
		super.render(gfx);
	}
}
