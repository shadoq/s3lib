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
package mobi.shad.s3lib.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ArrayMap.Keys;
import com.badlogic.gdx.utils.XmlReader;
import mobi.shad.s3lib.gfx.g3d.shaders.BaseShader;
import mobi.shad.s3lib.gfx.g3d.shaders.EffectShader;
import mobi.shad.s3lib.gfx.g3d.shaders.SimpleShader;
import mobi.shad.s3lib.utils.S3FileHandleResolver;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 */
public class S3ResourceManager{

	public final static ArrayMap<String, String> asset = new ArrayMap<String, String>();
	private static final String TAG = S3ResourceManager.class.getSimpleName();
	private static final S3FileHandleResolver fileHandleResolver = new S3FileHandleResolver();
	private static final AssetManager assetManager = new AssetManager(fileHandleResolver);
	public static boolean LOG = true;
	public static boolean USE_CACHE_RESOURCE = true;
	public static ArrayMap<String, BitmapFont> bitmapFontResuorce = new ArrayMap<String, BitmapFont>();
	public static ArrayMap<String, Pixmap> pixmapResuorce = new ArrayMap<String, Pixmap>();
	public static ArrayMap<String, TextureHandle> textureResuorce = new ArrayMap<String, TextureHandle>();
	public static ArrayMap<String, TextureRegion> regionResuorce = new ArrayMap<String, TextureRegion>();
	public static ArrayMap<String, TextureRegion> regionAnimResuorce = new ArrayMap<String, TextureRegion>();
	public static ArrayMap<String, Skin> skinResuorce = new ArrayMap<String, Skin>();
	public static ArrayMap<String, XmlReader.Element> xmlResuorce = new ArrayMap<String, XmlReader.Element>();
	public static ArrayMap<String, ShaderProgram> shaderProgramResuorce = new ArrayMap<String, ShaderProgram>();
	public static ArrayMap<String, BaseShader> baseShaderProgramResuorce = new ArrayMap<String, BaseShader>();
	public static ArrayMap<String, SimpleShader> simpleShaderProgramResuorce = new ArrayMap<String, SimpleShader>();
	public static ArrayMap<String, String> soundResuorce = new ArrayMap<String, String>();
	private static boolean reBindProcess = false;

	private static class TextureHandle{
		String fileName;
		Texture texture;
		int width;
		int height;
		int defResolution;
	}

	private S3ResourceManager(){

	}

	public static void initialize(){
		pixmapResuorce = new ArrayMap<String, Pixmap>();
		regionResuorce = new ArrayMap<String, TextureRegion>();
		regionAnimResuorce = new ArrayMap<String, TextureRegion>();
		textureResuorce = new ArrayMap<String, TextureHandle>();
		skinResuorce = new ArrayMap<String, Skin>();
		xmlResuorce = new ArrayMap<String, XmlReader.Element>();
		shaderProgramResuorce = new ArrayMap<String, ShaderProgram>();
		bitmapFontResuorce = new ArrayMap<String, BitmapFont>();
		simpleShaderProgramResuorce = new ArrayMap<String, SimpleShader>();
		loadAssert();
	}

	/**
	 * Save asserts data to Json file
	 */
	public static void saveAssert(){
		String fileDef = S3.currentDir + "assets/data/config/" + S3.cfg.title.toLowerCase() + "_asset.dat";
		S3Log.log(TAG, "Save Assert def: " + fileDef);
		Gdx.files.absolute(fileDef)
				 .writeString(S3.json.toJson(asset, ArrayMap.class, String.class), false);
	}


	/**
	 * Load asserts data from Json file, and preloaded gfx and other data
	 */
	public static void loadAssert(){

		//
		// Load Atlas texture
		//
		if (asset.get("atlas") != null){
			final String[] uis = asset.get("atlas").split("#");

			S3Log.info(TAG, "Load assert: " + Arrays.toString(uis));

			for (String atlas : uis){
				if (!atlas.isEmpty()){

					final FileHandle fileHandle = S3File.getFileHandle("atlas/" + atlas);
					S3Log.info(TAG, "Load: " + fileHandle.path());
					if (fileHandle.exists()){
						TextureAtlas textureAtlas = new TextureAtlas(fileHandle);
						getAtlasRegion(textureAtlas);
					} else {
						S3Log.info(TAG, "Error load: " + fileHandle.path() + " file not extis ...");
					}
				}
			}
		}

		//
		// Load Bitmap Font
		//
		if (asset.get("fonts") != null){
			final String[] uis = asset.get("fonts").split("#");

			S3Log.info(TAG, "Load assert: " + Arrays.toString(uis));

			for (String font : uis){
				if (!font.isEmpty()){
					getBitmapFont(font);
				}
			}
		}

	}

	/**
	 * @param textureAtlas
	 */
	private static void getAtlasRegion(TextureAtlas textureAtlas){
		final Array<TextureAtlas.AtlasRegion> regions = textureAtlas.getRegions();
		for (TextureAtlas.AtlasRegion region : regions){

			if (region == null){
				continue;
			}

			S3Log.info(TAG, "Load region: " + region.name + " index: " + region.index);

			if (USE_CACHE_RESOURCE){
				if (region.index == -1){
					if (regionResuorce.containsKey(region.name)){
						regionResuorce.removeKey(region.name);
					}
					regionResuorce.put(region.name, region);
				} else {
					if (regionAnimResuorce.containsKey(region.name + region.index)){
						regionAnimResuorce.removeKey(region.name + region.index);
					}
					regionAnimResuorce.put(region.name + region.index, region);
					if (regionResuorce.containsKey(region.name)){
						regionResuorce.removeKey(region.name);
					}
					regionResuorce.put(region.name, region);
				}
			}
		}
	}

	/**
	 *
	 */
	public static void logAssert(){

		if (textureResuorce.keys != null){
			S3Log.info(TAG, "- Texture files ------------");
			for (String file : (String[]) textureResuorce.keys){
				S3Log.info(TAG, "Texture file:" + file);
			}
		}
		if (regionResuorce != null){
			S3Log.info(TAG, "- Region files ------------");
			for (String file : regionResuorce.keys){
				S3Log.info(TAG, "Region file:" + file);
			}
		}
		if (pixmapResuorce != null){
			S3Log.info(TAG, "- Pixmap files ------------");
			for (String file : pixmapResuorce.keys){
				S3Log.info(TAG, "Pixmap file:" + file);
			}
		}
		if (skinResuorce != null){
			S3Log.info(TAG, "- Skin files ------------");
			for (String file : skinResuorce.keys){
				S3Log.info(TAG, "Skin file:" + file);
			}
		}
		if (xmlResuorce != null){
			S3Log.info(TAG, "- XML files ------------");
			for (String file : xmlResuorce.keys){
				S3Log.info(TAG, "XML file:" + file);
			}
		}
		if (shaderProgramResuorce != null){
			S3Log.info(TAG, "- Shader files ------------");
			for (String file : shaderProgramResuorce.keys){
				S3Log.info(TAG, "Shader file:" + file);
			}
		}
	}

	/**
	 *
	 */
	public static void rebind(){

		reBindProcess = true;

		if (LOG){
			S3Log.info(TAG, "Reload ShaderProgram", 1);
		}
		//
		// Reload Shaderprogram
		//
		if (shaderProgramResuorce != null){
			Keys<String> keys = shaderProgramResuorce.keys();
			if (keys != null){
				for (String key : keys){
					getShaderProgram(key);
				}
			}
		}

		//
		// Reload Region
		//
		if (LOG){
			S3Log.info(TAG, "Reload Texture", 1);
		}
		if (regionResuorce != null){
			Keys<String> keys = regionResuorce.keys();
			if (keys != null){
				for (String key : keys){
					//					getTexture(key);
				}
			}
		}

		//
		// Reload Texture
		//
		if (LOG){
			S3Log.info(TAG, "Reload Texture", 1);
		}
		if (textureResuorce != null){
			Keys<String> keys = textureResuorce.keys();
			if (keys != null){
				for (String key : keys){
					getTexture(key, textureResuorce.get(key).defResolution);
				}
			}
		}

		//
		// Reload Pixmap
		//
		if (LOG){
			S3Log.info(TAG, "Reload Pixmap", 1);
		}
		if (pixmapResuorce != null){
			Keys<String> keys = pixmapResuorce.keys();
			if (keys != null){
				for (String key : keys){
					getPixmap(key, S3Constans.proceduralTextureSize);
				}
			}
		}

		reBindProcess = false;
	}

	/**
	 *
	 */
	public static void clear(){
		if (pixmapResuorce != null){
			pixmapResuorce.clear();
		}
		if (regionResuorce != null){
			regionResuorce.clear();
		}
		if (regionAnimResuorce != null){
			regionAnimResuorce.clear();
		}
		if (textureResuorce != null){
			textureResuorce.clear();
		}
		if (skinResuorce != null){
			skinResuorce.clear();
		}
		if (xmlResuorce != null){
			xmlResuorce.clear();
		}
		if (shaderProgramResuorce != null){
			shaderProgramResuorce.clear();
		}
		if (bitmapFontResuorce != null){
			bitmapFontResuorce.clear();
		}
		if (simpleShaderProgramResuorce != null){
			simpleShaderProgramResuorce.clear();
		}
	}

	/**
	 * Clear and free memory data
	 */
	public static void dispose(){

		if (shaderProgramResuorce != null){

			if (LOG){
				S3Log.info(TAG, "Dispose ShaderProgram", 1);
			}
			//
			// Dispose ShaderProgram
			//
			Keys<String> keys = shaderProgramResuorce.keys();
			for (String key : keys){
				shaderProgramResuorce.get(key).dispose();
				shaderProgramResuorce.removeKey(key);
			}
			shaderProgramResuorce.clear();
			shaderProgramResuorce = null;
		}
		if (textureResuorce != null){

			if (LOG){
				S3Log.info(TAG, "Dispose Texture", 1);
			}
			//
			// Dispose Texture
			//
			Keys<String> keys = textureResuorce.keys();
			for (String key : keys){
				textureResuorce.get(key).texture.dispose();
				textureResuorce.removeKey(key);
			}
			textureResuorce.clear();
			textureResuorce = null;
		}
		if (skinResuorce != null){

			if (LOG){
				S3Log.info(TAG, "Dispose skin", 1);
			}
			//
			// Dispose Skin
			//
			Keys<String> keys = skinResuorce.keys();
			for (String key : keys){
				skinResuorce.get(key).dispose();
				skinResuorce.removeKey(key);
			}
			skinResuorce.clear();
			skinResuorce = null;
		}
		if (xmlResuorce != null){
			if (LOG){
				S3Log.info(TAG, "Dispose XML", 1);
			}
			//
			// Dispose Skin
			//
			Keys<String> keys = xmlResuorce.keys();
			for (String key : keys){
				xmlResuorce.removeKey(key);
			}
			xmlResuorce.clear();
			xmlResuorce = null;
		}


		if (bitmapFontResuorce != null){
			if (LOG){
				S3Log.info(TAG, "Dispose Bitmap Font", 1);
			}
			//
			// Dispose BitMap Fonts
			//
			Keys<String> keys = bitmapFontResuorce.keys();
			for (String key : keys){
				bitmapFontResuorce.get(key).dispose();
				bitmapFontResuorce.removeKey(key);
			}
			bitmapFontResuorce.clear();
			bitmapFontResuorce = null;
		}

	}


	/**
	 * Zwraca teksturę załadowaną z dysku
	 *
	 * @param fileName
	 * @return
	 */
	public static Texture getTexture(String fileName, int destAndroidResolution){

		Texture texture;

		if (textureResuorce == null){
			textureResuorce = new ArrayMap<String, TextureHandle>(20);
		}

		if (!textureResuorce.containsKey(fileName) || reBindProcess == true){

			if (LOG){
				S3Log.trace(TAG, "Load texture (def size: " + destAndroidResolution + ") from file: " + fileName, 3);
			}

			try {
				if (destAndroidResolution == 0){
					FileHandle fileHandle = S3File.getFileHandle(fileName, true, false);
					texture = new Texture(fileHandle);
					texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
				} else {
					Pixmap px = new Pixmap(S3File.getFileHandle(fileName, true, false));
					Pixmap px2 = new Pixmap(destAndroidResolution, destAndroidResolution, Pixmap.Format.RGBA8888);
					px2.drawPixmap(px, 0, 0, px.getWidth(), px.getHeight(), 0, 0, destAndroidResolution,
								   destAndroidResolution);
					texture = new Texture(px2);
					texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
					texture.draw(px2, 0, 0);
				}

				if (USE_CACHE_RESOURCE){
					if (textureResuorce.containsKey(fileName)){
						textureResuorce.removeKey(fileName);
					}
					TextureHandle textureHeandle = new TextureHandle();
					textureHeandle.fileName = fileName;
					textureHeandle.texture = texture;
					textureHeandle.width = texture.getWidth();
					textureHeandle.height = texture.getHeight();
					textureHeandle.defResolution = destAndroidResolution;
					textureResuorce.put(fileName, textureHeandle);
				}

			} catch (Exception e){
				S3Log.error("S3ResourceManager::getTexture", "Error create texture data ....", e);

				Pixmap pixmap = new Pixmap(S3Constans.proceduralTextureSizeLow, S3Constans.proceduralTextureSizeLow,
										   Pixmap.Format.RGBA4444);
				pixmap.setColor(Color.RED);
				pixmap.fill();
				texture = new Texture(pixmap);
				texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
				texture.draw(pixmap, 0, 0);
			}
		} else {

			if (LOG){
				S3Log.trace(TAG, "Load texture from cache (def size: " + destAndroidResolution + ") from file: " + fileName, 2);
			}
			TextureHandle textureHandle = textureResuorce.get(fileName);
			texture = textureHandle.texture;
		}
		if (LOG){
			S3Log.trace(TAG, "Texture size width: " + texture.getWidth() + "px height: " + texture.getHeight());
		}
		return texture;
	}


	/**
	 * Zwraca teksturę załadowaną z dysku
	 *
	 * @param fileName
	 * @return
	 */
	public static TextureRegion getTextureRegion(String fileName){

		TextureRegion textureRegion;

		if (regionResuorce == null){
			regionResuorce = new ArrayMap<String, TextureRegion>(20);
		}

		if (!regionResuorce.containsKey(fileName) || reBindProcess == true){

			if (LOG){
				S3Log.trace(TAG, "Load textureRegion region: " + fileName, 3);
			}

			try {
				FileHandle fileHandle = S3File.getFileHandle(fileName, true, false);
				textureRegion = new TextureRegion(assetManager.get(fileHandle.path(), Texture.class));
				textureRegion.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

				if (USE_CACHE_RESOURCE){
					if (regionResuorce.containsKey(fileName)){
						regionResuorce.removeKey(fileName);
					}
					regionResuorce.put(fileName, textureRegion);
				}

			} catch (Exception e){
				S3Log.error("S3ResourceManager::getTexture", "Error create textureRegion data ....", e);

				try {
					Pixmap pixmap = new Pixmap(S3Constans.proceduralTextureSizeLow, S3Constans.proceduralTextureSizeLow,
											   Pixmap.Format.RGBA4444);
					pixmap.setColor(Color.RED);
					pixmap.fill();
					Texture text2 = new Texture(pixmap);
					text2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
					text2.draw(pixmap, 0, 0);
					textureRegion = new TextureRegion(text2);
				} catch (Exception ex){
					textureRegion = regionResuorce.firstValue();
				}
			}
		} else {

			if (LOG){
				S3Log.trace(TAG, "Load textureRegion region from cache: " + fileName, 2);
			}
			textureRegion = regionResuorce.get(fileName);
		}
		return textureRegion;
	}

	/**
	 * Zwraca teksturę załadowaną z dysku
	 *
	 * @param fileName
	 * @return
	 */
	public static TextureRegion getTextureRegion(String fileName, int index){

		TextureRegion texture = null;

		if (regionResuorce == null){
			regionResuorce = new ArrayMap<String, TextureRegion>(20);
		}

		if (regionAnimResuorce == null){
			regionAnimResuorce = new ArrayMap<String, TextureRegion>();
		}

		if (regionAnimResuorce.containsKey(fileName + index)){
			if (LOG){
				S3Log.trace(TAG,
							"Load texture region: " + fileName + " index: " + index,
							2);
			}
			texture = regionAnimResuorce.get(fileName + index);
			if (LOG){
				S3Log.trace(TAG,
							"Texture size width: " + texture.getTexture()
															.getWidth() + "px height: " + texture.getTexture()
																								 .getHeight()
				);
			}
		}
		return texture;
	}


	/**
	 * @param fileName
	 * @param destAndroidResolution
	 * @return
	 */
	public static Pixmap getPixmap(String fileName, int destAndroidResolution){

		Pixmap pixmap;

		if (pixmapResuorce == null){
			pixmapResuorce = new ArrayMap<String, Pixmap>(20);
		}

		if (!pixmapResuorce.containsKey(fileName) || reBindProcess == true){

			if (LOG){
				S3Log.trace(TAG, "Load pixmap (def size: " + destAndroidResolution + ") from file: " + fileName, 3);
			}
			try {

				FileHandle fileHandle = S3File.getFileHandle(fileName, true, false);
				pixmap = assetManager.get(fileHandle.path(), Pixmap.class);
				if (USE_CACHE_RESOURCE){
					pixmapResuorce.put(fileName, pixmap);
				}
			} catch (Exception e){
				S3Log.error("S3ResourceManager::getPixmap", "Error create pixmap data ....", e);
				pixmap = new Pixmap(S3Constans.proceduralTextureSizeLow, S3Constans.proceduralTextureSizeLow,
									Pixmap.Format.RGBA4444);
				pixmap.setColor(Color.RED);
				pixmap.fill();
			}
		} else {

			if (LOG){
				S3Log.trace(TAG,
							"Load pixmap from cache (def size: " + destAndroidResolution + ") from file: " + fileName,
							1);
			}
			pixmap = pixmapResuorce.get(fileName);
		}

		if (LOG){
			S3Log.trace(TAG, "Pixmap size width: " + pixmap.getWidth() + "px height: " + pixmap.getHeight() + "px ", 0);
		}
		return pixmap;
	}

	/**
	 * Zwraca objekt skina załadowany z dysku
	 *
	 * @param fileName
	 * @return
	 */
	public static Skin getSkin(String fileName){

		if (skinResuorce == null){
			skinResuorce = new ArrayMap<String, Skin>(10);
		}

		Skin skin;
		if (!skinResuorce.containsKey(fileName) || reBindProcess == true){

			if (LOG){
				S3Log.trace(TAG, "Load skin from file: " + fileName, 3);
			}
			FileHandle fileHandle = S3File.getFileHandle(fileName, true, false);
			skin = new Skin(fileHandle);
			if (USE_CACHE_RESOURCE){
				skinResuorce.put(fileName, skin);
			}
		} else {
			if (LOG){
				S3Log.trace(TAG, "Load skin from cache: " + fileName, 1);
			}
			skin = skinResuorce.get(fileName);
		}
		if (S3Constans.DEBUG){
			S3Log.log("S3ResourceManager::getStyleName", "Skin: " + skin.toString(), 0);
		}
		return skin;
	}

	/**
	 * Zwraca objekt Xml.Element, który jest parsowany w pozostałych częściach
	 * programu
	 *
	 * @param fileName
	 * @return
	 */
	public static XmlReader.Element getXml(String fileName){

		if (xmlResuorce == null){
			xmlResuorce = new ArrayMap<String, XmlReader.Element>(10);
		}

		XmlReader.Element xml = new XmlReader.Element(null, null);
		if (!xmlResuorce.containsKey(fileName) || reBindProcess == true){
			if (LOG){
				S3Log.trace(TAG, "Load XML from file: " + fileName, 1);
			}
			FileHandle fileHandle = S3File.getFileHandle(fileName, true, false);
			XmlReader reader = new XmlReader();
			try {
				xml = reader.parse(fileHandle);
				if (USE_CACHE_RESOURCE){
					xmlResuorce.put(fileName, xml);
				}
			} catch (IOException ex){
				Logger.getLogger(S3ResourceManager.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			if (LOG){
				S3Log.trace(TAG, "Load XML from cache: " + fileName, 1);
			}
			xml = xmlResuorce.get(fileName);
		}
		return xml;
	}

	/**
	 * Ładuje program Shadera z dysku
	 *
	 * @param fileName
	 * @return
	 */
	public static ShaderProgram getShaderProgram(String fileName){
		ShaderProgram shaderProgram;

		if (shaderProgramResuorce == null){
			shaderProgramResuorce = new ArrayMap<String, ShaderProgram>(10);
		}

		if (!shaderProgramResuorce.containsKey(fileName) || reBindProcess == true){
			if (LOG){
				S3Log.trace(TAG, "Load ShaderProgram from file: " + fileName, 3);
			}
			FileHandle vertexHandle = S3File.getFileHandle(fileName + ".vertex", true, false);
			FileHandle fragmentHandle = S3File.getFileHandle(fileName + ".fragment", true, false);
			shaderProgram =
			getShaderProgramFromString(vertexHandle.readString(), fragmentHandle.readString(), fileName);
			if (USE_CACHE_RESOURCE){
				shaderProgramResuorce.put(fileName, shaderProgram);
			}
		} else {
			if (LOG){
				S3Log.trace(TAG, "Load ShaderProgram from cache: " + fileName, 1);
			}
			shaderProgram = shaderProgramResuorce.get(fileName);
		}
		return shaderProgram;
	}

	/**
	 * Buduje program Shadera na podstawie danych przekazanych w stringu
	 *
	 * @param vertex
	 * @param fragment
	 * @param vertexName
	 * @param fragmentName
	 * @param defines
	 * @return
	 */
	public static ShaderProgram getShaderProgramFromString(String vertex, String fragment, String name){

		if (LOG){
			S3Log.trace(TAG, "Load ShaderProgram from string: " + name, 2);
		}

		ShaderProgram.pedantic = true;
		ShaderProgram shaderProgram = new ShaderProgram(vertex, fragment);

		if (!shaderProgram.isCompiled()){
			S3Log.error(TAG, "-----------------------------------------");
			S3Log.error(TAG, " Error compile " + shaderProgram.getLog());
			S3Log.error(TAG, " fragment:  \n" + vertex);
			S3Log.error(TAG, " vertex:  \n" + fragment);
			S3Log.error(TAG, "-----------------------------------------");
		} else {
			if (LOG){
				S3Log.trace(TAG, "ShaderProgram " + name + " compiled!", 0);
			}
		}
		return shaderProgram;
	}

	/**
	 * @return
	 */
	public static String[] getShaderList(){

		String[] out = S3File.getDirectoryList("shader", "shader");
		String[] out2 = new String[out.length + 1];

		out2[0] = "-= Default =-";
		System.arraycopy(out, 0, out2, 1, out.length);
		return out2;
	}

	/**
	 * @param shaderName
	 * @param hasColor
	 * @param hasNormal
	 * @param numTexCoords
	 * @return
	 */
	public static BaseShader getSimpleShader(String shaderName, boolean hasColor, boolean hasNormal,
											 int numTexCoords){
		if (shaderName.length() > 0){
			return new EffectShader(shaderName, hasColor, hasNormal, numTexCoords);
		} else {
			return new BaseShader(shaderName, hasColor, hasNormal, numTexCoords);
		}
	}

	public static BaseShader getSimpleShader(String shaderName, int numTexCoords){
		if (shaderName.length() > 0){
			return new EffectShader(shaderName, false, false, numTexCoords);
		} else {
			return new BaseShader(shaderName, false, false, numTexCoords);
		}
	}

	public static SimpleShader getShaderData(String shaderName){
		if (shaderName.length() > 0){
			shaderName = shaderName.replace(".json", "");
			final FileHandle fileHandle =
			S3File.getFileHandle("shader/" + shaderName + ".json", true, false, false);
			if (fileHandle.exists()){
				S3Log.log("ShaderPanel", "Load shader: " + fileHandle.path());

				final String s = fileHandle.readString();
				final SimpleShader.ShaderData shaderData = S3.json.fromJson(SimpleShader.ShaderData.class, s);

				SimpleShader shader = new SimpleShader(shaderData);
				simpleShaderProgramResuorce.put(shaderName, shader);

				return shader;

			} else {
				return new SimpleShader();
			}

		} else {
			return new SimpleShader();
		}
	}


	private static Animation getAnimation(String texName, int numberOfFrames, int hOffset){
		// Key frames list
		TextureRegion[] keyFrames = new TextureRegion[numberOfFrames];
		TextureRegion texture = getTextureRegion(texName);
		int width = texture.getRegionWidth() / numberOfFrames;
		int height = texture.getRegionHeight();
		// Set key frames (each comes from the single texture)
		for (int i = 0; i < numberOfFrames; i++){
			keyFrames[i] = new TextureRegion(texture, width * i, hOffset, width, height);
		}
		Animation animation = new Animation(1f / numberOfFrames, keyFrames);
		return animation;
	}

	private static Animation getAnimation(String texName, int numberOfFrames, float duration, int hOffset){
		// Key frames list
		TextureRegion[] keyFrames = new TextureRegion[numberOfFrames];
		TextureRegion texture = getTextureRegion(texName);
		int width = texture.getRegionWidth() / numberOfFrames;
		int height = texture.getRegionHeight();
		// Set key frames (each comes from the single texture)
		for (int i = 0; i < numberOfFrames; i++){
			keyFrames[i] = new TextureRegion(texture, width * i, hOffset, width, height);
		}
		Animation animation = new Animation(duration, keyFrames);
		return animation;
	}

	public static Animation getAnimation(String texName, int numberOfFrames){
		return getAnimation(texName, numberOfFrames, 0);
	}

	public static Animation getAnimation(String texName, int numberOfFrames, float duration){
		return getAnimation(texName, numberOfFrames, duration, 0);
	}

	public static Animation[] getAnimation(String texName, int rows, int cols, float duration){
		TextureRegion texture = getTextureRegion(texName);
		int height = texture.getRegionHeight() / rows;
		Animation[] animations = new Animation[rows];
		for (int i = 0; i < rows; i++){
			animations[i] = getAnimation(texName, cols, i * height);
		}
		return animations;
	}


	/**
	 * Zwraca objekt Xml.Element, który jest parsowany w pozostałych częściach
	 * programu
	 *
	 * @param fileName
	 * @return
	 */
	public static BitmapFont getBitmapFont(String fileName){

		if (bitmapFontResuorce == null){
			bitmapFontResuorce = new ArrayMap<String, BitmapFont>(10);
		}

		BitmapFont bitmapFont;

		if (!bitmapFontResuorce.containsKey(fileName) || reBindProcess == true){
			if (LOG){
				S3Log.trace(TAG, "Load Bitmap Font from file: " + fileName, 1);
			}
			FileHandle fileHandle = S3File.getFileHandle("ui/" + fileName, true, false);
			if (fileHandle.exists()){
				bitmapFont = new BitmapFont(fileHandle);
				bitmapFontResuorce.put(fileName, bitmapFont);
			} else {
				bitmapFont = null;
			}
		} else {
			if (LOG){
				S3Log.trace(TAG, "Load Bitmap Font from cache: " + fileName, 1);
			}
			bitmapFont = bitmapFontResuorce.get(fileName);
		}
		return bitmapFont;
	}

}
