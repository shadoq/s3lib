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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import mobi.shad.s3lib.main.S3File;
import mobi.shad.s3lib.main.S3Log;

import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * @author Jarek
 */
public class ScreenShot{

	/**
	 *
	 */
	public static void saveScreenShot(){
		Pixmap pixmap = getScreenShot(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
		FileHandle image = S3File.getFileHandle("screen/" + System.currentTimeMillis() + ".png", false, false, true);
		OutputStream stream = image.write(false);

		try {
			byte[] bytes = Png.toPNG(pixmap);
			stream.write(bytes);
			stream.close();
		} catch (Exception ex){
			S3Log.error("SceneToImage", "Error save PNG ...", ex);
		}
	}

	/**
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param flipY
	 * @return
	 */
	public static Pixmap getScreenShot(int x, int y, int w, int h, boolean flipY){
		Gdx.gl.glPixelStorei(GL20.GL_PACK_ALIGNMENT, 1);

		final Pixmap pixmap = new Pixmap(w, h, Format.RGBA8888);
		ByteBuffer pixels = pixmap.getPixels();
		Gdx.gl.glReadPixels(x, y, w, h, GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE, pixels);

		final int numBytes = w * h * 4;
		byte[] lines = new byte[numBytes];
		if (flipY){
			final int numBytesPerLine = w * 4;
			for (int i = 0; i < h; i++){
				pixels.position((h - i - 1) * numBytesPerLine);
				pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
			}
			pixels.clear();
			pixels.put(lines);
		} else {
			pixels.clear();
			pixels.get(lines);
		}

		return pixmap;
	}
}