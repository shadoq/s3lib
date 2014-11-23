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
package mobi.shad.s3lib.gfx.util;

import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import mobi.shad.s3lib.main.S3Screen;

/**
 * @author Jarek
 */
public class CaptureTexture{

	public int width, height;
	private Texture resultTexture;
	private boolean process = false;
	private boolean stateBuffer = false;
	private boolean useBuffer1 = false;
	private boolean useBuffer2 = false;
	private FrameBuffer frameBuffer1;
	private FrameBuffer frameBuffer2;
	private int pingPongSteps = 0;

	public CaptureTexture(){
		// this(S3Config.proceduralTextureSize, S3Config.proceduralTextureSize);
		this(S3Screen.width, S3Screen.height);
	}

	public CaptureTexture(int width, int height){

		this.width = width;
		this.height = height;

		resultTexture = new Texture(width, height, Format.RGBA8888);
		frameBuffer1 = new FrameBuffer(Format.RGBA8888, width, height, false);
		frameBuffer2 = new FrameBuffer(Format.RGBA8888, width, height, false);
		useBuffer1 = false;
		useBuffer2 = false;
		stateBuffer = false;
	}

	public void begin(){
		pingPongSteps = 0;
		beginProcess();
	}

	public void end(){
		endProcess();
	}

	public void pingPong(){
		pingPongSteps++;
		endProcess();
		stateBuffer = !stateBuffer;
		beginProcess();
	}

	private void beginProcess(){
		if (process == false){
			process = true;
			if (stateBuffer == true){
				frameBuffer1.begin();
				useBuffer1 = true;
			} else {
				frameBuffer2.begin();
				useBuffer2 = true;
			}
		}
	}

	private void endProcess(){
		if (process == true){
			process = false;
			if (useBuffer1 == true){
				frameBuffer1.end();
				resultTexture = frameBuffer1.getColorBufferTexture();
				useBuffer1 = false;
			}
			if (useBuffer2 == true){
				frameBuffer2.end();
				resultTexture = frameBuffer2.getColorBufferTexture();
				useBuffer2 = false;
			}
		}
	}

	public Texture getResultTexture(){
		return resultTexture;
	}

	public boolean isFlip(){
		return pingPongSteps % 2 == 0;
	}
}
