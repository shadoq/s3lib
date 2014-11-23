/*******************************************************************************
 * Copyright 2014
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

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import mobi.shad.s3lib.gfx.g3d.shaders.SimpleShader;
import mobi.shad.s3lib.gui.ui.*;
import mobi.shad.s3lib.main.constans.InterpolationType;
import mobi.shad.s3lib.main.constans.MusicPlayType;
import mobi.shad.s3lib.main.constans.ScreenEffectType;

/**
 * Class to read/write game object from/to json text file
 */
public class Serializer{

	private static final String TAG = "Serializer";

	/**
	 * Read/Write config file
	 */
	private static class S3CfgSerializer implements Json.Serializer<S3Cfg>{

		@Override
		public S3Cfg read(Json json, JsonValue jsonData, Class type){
			S3Log.log("S3CfgSerializer::read", "json: " + json);

			S3.cfg.reset();
			S3.cfg.title = jsonData.getString("title", "Unknown");
			S3.cfg.projectFile = jsonData.getString("projectFile", "Unknown");
			S3.cfg.targetSize = jsonData.getString("targetSize", "800x480");
			S3.cfg.screenSize = jsonData.getString("screenSize", "800x480");
			S3.cfg.audioBufferCount = Integer.parseInt(jsonData.getString("audioBufferCount", "10"));
			S3.cfg.resize = Boolean.parseBoolean(jsonData.getString("resize", "false"));
			S3.cfg.forceExit = Boolean.parseBoolean(jsonData.getString("forceExit", "true"));
			S3.cfg.fullScreen = Boolean.parseBoolean(jsonData.getString("fullScreen", "false"));
			S3.cfg.vSync = Boolean.parseBoolean(jsonData.getString("vSync", "true"));
			S3.cfg.disableAudio = Boolean.parseBoolean(jsonData.getString("disableAudio", "false"));
			S3.cfg.debug = Boolean.parseBoolean(jsonData.getString("debug", "true"));
			S3.cfg.logging = Boolean.parseBoolean(jsonData.getString("logging", "true"));
			S3.cfg.version = jsonData.getString("version", "00.00.00");
			S3.cfg.target = jsonData.getString("target", "Desktop");
			return S3.cfg;
		}

		@Override
		public void write(Json json, S3Cfg s3Cfg, Class knownType){

			S3Log.log("S3CfgSerializer::write", "json: " + json);

			json.writeObjectStart();
			json.writeValue("title", s3Cfg.title);
			json.writeValue("projectFile", s3Cfg.projectFile);
			json.writeValue("targetSize", s3Cfg.targetSize);
			json.writeValue("screenSize", s3Cfg.screenSize);
			json.writeValue("audioBufferCount", s3Cfg.audioBufferCount);
			json.writeValue("resize", s3Cfg.resize);
			json.writeValue("forceExit", s3Cfg.forceExit);
			json.writeValue("fullScreen", s3Cfg.fullScreen);
			json.writeValue("vSync", s3Cfg.vSync);
			json.writeValue("disableAudio", s3Cfg.disableAudio);
			json.writeValue("debug", s3Cfg.debug);
			json.writeValue("logging", s3Cfg.logging);
			json.writeValue("version", s3Cfg.version);
			json.writeValue("target", s3Cfg.target);
			json.writeObjectEnd();
		}
	}

	/**
	 * Read/Write scene setting
	 */
	private static class S3AppSerializer implements Json.Serializer<S3AppImpl>{

		@Override
		public S3AppImpl read(Json json, JsonValue jsonData, Class type){
			S3Log.log("S3AppImplSerializer::read", "json: " + json);
			S3AppImpl app = new S3AppImpl();
			app.screenName = jsonData.getString("screenName", "Unknown");
			app.screenColor = Color.valueOf(jsonData.getString("screenColor", "00000000"));

			app.screenTransition = ScreenEffectType.valueOf(jsonData.getString("screenTransition", "None"));
			app.screenTransitionDuration = Float.valueOf(jsonData.getString("screenTransitionDuration", "1.0"));
			app.screenTransitionInterpolation =
			InterpolationType.valueOf(jsonData.getString("screenTransitionInterpolation", "Linear"));

			app.sceneBackground = jsonData.getString("background", "None");

			app.screenMusic = jsonData.getString("music", "None");
			app.musicPlayType = MusicPlayType.valueOf(jsonData.getString("musicType", "None"));

			S3.app.screenName = app.screenName;
			S3.app.screenColor = app.screenColor;
			S3.app.screenTransition = app.screenTransition;
			S3.app.screenTransitionDuration = app.screenTransitionDuration;
			S3.app.screenTransitionInterpolation = app.screenTransitionInterpolation;

			S3.app.sceneBackground = app.sceneBackground;

			S3.app.screenMusic = app.screenMusic;
			S3.app.musicPlayType = app.musicPlayType;

			return app;
		}

		@Override
		public void write(Json json, S3AppImpl s3App, Class knownType){
			S3Log.log("S3AppSerializer::write", "json: " + json);
			json.writeObjectStart();
			json.writeValue("class", s3App.getClass().getName());
			json.writeValue("screenName", s3App.screenName);
			json.writeValue("screenColor", s3App.screenColor.toString());

			json.writeValue("screenTransition", s3App.screenTransition.toString());
			json.writeValue("screenTransitionDuration", s3App.screenTransitionDuration);
			json.writeValue("screenTransitionInterpolation", s3App.screenTransitionInterpolation.toString());

			json.writeValue("background", s3App.sceneBackground);

			json.writeValue("music", s3App.screenMusic);
			json.writeValue("musicType", s3App.musicPlayType.toString());

			json.writeObjectEnd();
		}
	}

	/**
	 * Read/Write libGdx 2d Actor data
	 */
	private static class ActorSerializer implements Json.Serializer<Actor>{

		public static void readActor(JsonValue jsonData, Actor actor){
			actor.setName(jsonData.getString("name"));
			actor.setX(jsonData.getFloat("x"));
			actor.setY(jsonData.getFloat("y"));
			actor.setWidth(jsonData.getFloat("width"));
			actor.setHeight(jsonData.getFloat("height"));
			actor.setOriginX(jsonData.getFloat("ox"));
			actor.setOriginY(jsonData.getFloat("oy"));
			actor.setRotation(jsonData.getFloat("rotation"));
			if (jsonData.getString("color") != null && (jsonData.getString("color").length() == 6 || jsonData.getString("color").length() == 8)){
				actor.setColor(Color.valueOf(jsonData.getString("color")));
			}
			actor.setTouchable(Touchable.valueOf(jsonData.getString("touchable")));
			actor.setVisible(jsonData.getBoolean("visible"));
			actor.setZIndex(jsonData.getInt("zindex"));

			if (S3.app != null){
				S3.app.addActor(actor);
			}
		}

		public static void writeActor(Json json, Actor actor){
			json.writeValue("class", actor.getClass().getName());
			json.writeValue("name", actor.getName());
			json.writeValue("x", actor.getX());
			json.writeValue("y", actor.getY());
			json.writeValue("width", actor.getWidth());
			json.writeValue("height", actor.getHeight());
			json.writeValue("ox", actor.getOriginX());
			json.writeValue("oy", actor.getOriginY());
			json.writeValue("rotation", actor.getRotation());
			json.writeValue("zindex", actor.getZIndex());
			json.writeValue("color", actor.getColor().toString());
			json.writeValue("touchable", actor.getTouchable().toString());
			json.writeValue("visible", actor.isVisible());
		}

		@Override
		public Actor read(Json json, JsonValue jsonData, Class arg2){
			Actor actor = new Actor();
			readActor(jsonData, actor);
			return actor;
		}

		@Override
		public void write(Json json, Actor actor, Class arg2){
			json.writeObjectStart();
			writeActor(json, actor);
			json.writeObjectEnd();
		}

	}

	/**
	 * Read/Write label data
	 */
	private static class LabelSerializer implements Json.Serializer<Label>{

		@Override
		public void write(Json json, Label label, Class knownType){

			S3Log.log("LabelSerializer", "Write: " + label.getName());
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActor(json, label);
			json.writeValue("text", ((Label) label).getText().toString());

			json.writeValue("styleName", ((Label) label).getStyleName());
			json.writeValue("align", ((Label) label).getAlign());
			json.writeValue("wrap", ((Label) label).isWrap());

			json.writeValue("font", ((Label) label).getFontName());
			json.writeValue("fontScaleX", ((Label) label).getScaleX());
			json.writeValue("fontScaleY", ((Label) label).getScaleY());

			json.writeObjectEnd();
		}

		@Override
		public Label read(Json json, JsonValue jsonData, Class type){
			S3Log.log("LabelSerializer", "Read: " + jsonData.getString("name"));
			com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle ls;
			Label label;
			if (jsonData.has("styleName")){
				ls =
				S3.skin.get(jsonData.getString("styleName", "default"), com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle.class);
				if (ls.font != null){
					label = new Label(jsonData.getString("text"), ls, jsonData.getString("styleName", "default"));
				} else {
					ls = S3.skin.get("default", com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle.class);
					label = new Label(jsonData.getString("text"), ls);
				}
			} else {
				ls = S3.skin.get("default", com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle.class);
				label = new Label(jsonData.getString("text"), ls);
			}
			if (jsonData.has("align")){
				label.setAlign(jsonData.getString("align"));
			}
			if (jsonData.has("wrap")){
				label.setWrap(Boolean.parseBoolean(jsonData.getString("wrap")));
			}
			if (jsonData.has("font")){
				label.setFontName(jsonData.getString("font"));
			}
			if (jsonData.has("fontScaleX")){
				label.setScaleX(Float.parseFloat(jsonData.getString("fontScaleX")));
			}
			if (jsonData.has("fontScaleY")){
				label.setScaleY(Float.parseFloat(jsonData.getString("fontScaleY")));
			}

			Serializer.ActorSerializer.readActor(jsonData, label);
			return label;
		}

	}

	/**
	 * Read/Write Button data
	 */
	private static class ButtonSerializer implements Json.Serializer<Button>{

		@Override
		public void write(Json json, Button button, Class knownType){
			S3Log.log("ButtonSerializer", "Write: " + button.getName());
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActor(json, button);
			json.writeObjectEnd();
		}

		@Override
		public Button read(Json json, JsonValue jsonData, Class type){
			S3Log.log("ButtonSerializer", "Read: " + jsonData.getString("name"));
			com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle
			bs = S3.skin.get("default", com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle.class);
			Button button = new Button(bs);
			Serializer.ActorSerializer.readActor(jsonData, button);
			return button;
		}
	}

	/**
	 *
	 */
	private static class ImageSerializer implements Json.Serializer<Image>{

		@Override
		public void write(Json json, Image image, Class knownType){
			S3Log.log("ImageSerializer", "Write: " + image.getName());
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActor(json, image);
			json.writeValue("texture", image.getTextureName());
			json.writeObjectEnd();
		}

		@Override
		public Image read(Json json, JsonValue jsonData, Class type){
			S3Log.log("ImageSerializer", "Read: " + jsonData.getString("name"));

			if (!jsonData.has("texture")){
				Image image = new Image(S3.skin, "btn_default_pressed");
				Serializer.ActorSerializer.readActor(jsonData, image);
				return image;
			}

			if (!jsonData.getString("texture").equalsIgnoreCase("")){
				Image image = new Image(S3ResourceManager.getTextureRegion(jsonData.getString("texture")),
										jsonData.getString("texture"));
				Serializer.ActorSerializer.readActor(jsonData, image);
				return image;
			} else {
				Image image = new Image(S3.skin, "btn_default_pressed");
				Serializer.ActorSerializer.readActor(jsonData, image);
				return image;
			}
		}
	}

	/**
	 *
	 */
	private static class ImageButtonSerializer implements Json.Serializer<ImageButton>{

		@Override
		public void write(Json json, ImageButton image, Class knownType){
			S3Log.log("ImageButtonSerializer", "Write: " + image.getName());
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActor(json, image);
			json.writeValue("texture", image.getTextureName());
			json.writeObjectEnd();
		}

		@Override
		public ImageButton read(Json json, JsonValue jsonData, Class type){
			S3Log.log("ImageButtonSerializer", "Read: " + jsonData.getString("name"));

			if (!jsonData.has("texture")){
				ImageButton image = new ImageButton(S3.skin, "default");
				Serializer.ActorSerializer.readActor(jsonData, image);
				return image;
			}

			if (!jsonData.getString("texture").equalsIgnoreCase("")){
				ImageButton image =
				new ImageButton(S3.skin, S3ResourceManager.getTextureRegion(jsonData.getString("texture")),
								jsonData.getString("texture"), "default");
				Serializer.ActorSerializer.readActor(jsonData, image);
				return image;
			} else {
				ImageButton image = new ImageButton(S3.skin, "default");
				Serializer.ActorSerializer.readActor(jsonData, image);
				return image;
			}
		}
	}

	/**
	 *
	 */
	private static class TextButtonSerializer implements Json.Serializer<TextButton>{

		@Override
		public void write(Json json, TextButton button, Class knownType){
			S3Log.log("TextButtonSerializer", "Write: " + button.getName());
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActor(json, button);
			json.writeValue("text", button.getText().toString());
			json.writeValue("style", button.getStyleName());

			json.writeValue("align", button.getLabelAlign());
			json.writeValue("wrap", button.isLabelWrap());

			json.writeValue("font", button.getFontName());
			json.writeValue("fontScaleX", button.getFontScaleX());
			json.writeValue("fontScaleY", button.getFontScaleY());

			json.writeObjectEnd();
		}

		@Override
		public TextButton read(Json json, JsonValue jsonData, Class type){
			S3Log.log("TextButtonSerializer", "Read: " + jsonData.getString("name"));
			TextButton button =
			new TextButton(jsonData.getString("text", ""), S3Skin.skin, jsonData.getString("style", "default"));
			Serializer.ActorSerializer.readActor(jsonData, button);

			if (jsonData.has("align")){
				button.setLabelAlign(jsonData.getString("align"));
			}
			if (jsonData.has("wrap")){
				button.setLabelWrap(Boolean.parseBoolean(jsonData.getString("wrap")));
			}

			if (jsonData.has("font")){
				button.setFontName(jsonData.getString("font"));
			}
			if (jsonData.has("fontScaleX")){
				button.setFontScaleX(Float.parseFloat(jsonData.getString("fontScaleX")));
			}
			if (jsonData.has("fontScaleY")){
				button.setFontScaleY(Float.parseFloat(jsonData.getString("fontScaleY")));
			}

			return button;
		}
	}

	private static class SliderSerializer implements Json.Serializer<Slider>{

		@Override
		public void write(Json json, Slider slider, Class knownType){

			S3Log.log("SliderSerializer", "Write: " + slider.getName());
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActor(json, slider);
			json.writeValue("min", slider.getMinValue());
			json.writeValue("max", slider.getMaxValue());
			json.writeValue("stepSize", slider.getStepSize());
			json.writeObjectEnd();
		}

		@Override
		public Slider read(Json json, JsonValue jsonData, Class type){
			S3Log.log("SliderSerializer", "Read: " + jsonData.getString("name"));

			Slider slider = new Slider(
			Float.valueOf(jsonData.getString("min", "0.0")), Float.valueOf(jsonData.getString("max", "100.0")),
			Float.valueOf(jsonData.getString("stepSize", "1.0")), false, S3Skin.skin);
			Serializer.ActorSerializer.readActor(jsonData, slider);
			return slider;
		}

	}

	private static class TextFieldSerializer implements Json.Serializer<TextField>{

		@Override
		public void write(Json json, TextField textField, Class knownType){

			S3Log.log("TextFieldSerializer", "Write: " + textField.getName());
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActor(json, textField);
			json.writeValue("text", textField.getText());
			json.writeValue("messageText", textField.getMessageText());
			json.writeObjectEnd();
		}

		@Override
		public TextField read(Json json, JsonValue jsonData, Class type){
			S3Log.log("TextFieldSerializer", "Read: " + jsonData.getString("name"));

			TextField textField =
			new TextField(jsonData.getString("text", ""), jsonData.getString("messageText", ""), S3Skin.skin,
						  "default");
			Serializer.ActorSerializer.readActor(jsonData, textField);
			return textField;
		}

	}

	/**
	 *
	 */
	private static class CheckBoxSerializer implements Json.Serializer<CheckBox>{

		@Override
		public void write(Json json, CheckBox checkBox, Class knownType){
			S3Log.log("CheckBoxSerializer", "Write: " + checkBox.getName());
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActor(json, checkBox);
			json.writeValue("text", checkBox.getText().toString());
			json.writeValue("style", checkBox.getStyleName());
			json.writeValue("state", checkBox.isChecked());
			json.writeObjectEnd();
		}

		@Override
		public CheckBox read(Json json, JsonValue jsonData, Class type){
			S3Log.log("CheckBoxSerializer", "Read: " + jsonData.getString("name"));
			CheckBox checkBox =
			new CheckBox(jsonData.getString("text", ""), S3Skin.skin, jsonData.getString("style", "default"));
			Serializer.ActorSerializer.readActor(jsonData, checkBox);
			checkBox.setChecked(Boolean.valueOf(jsonData.getString("state")));
			return checkBox;
		}
	}

	/**
	 *
	 */
	private static class ImageTextButtonSerializer implements Json.Serializer<ImageTextButton>{

		@Override
		public void write(Json json, ImageTextButton image, Class knownType){
			S3Log.log("ImageTextButtonSerializer", "Write: " + image.getName());
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActor(json, image);
			json.writeValue("texture", image.getTextureName());
			json.writeValue("text", image.getText().toString());
			json.writeValue("style", image.getStyleName());
			json.writeObjectEnd();
		}

		@Override
		public ImageTextButton read(Json json, JsonValue jsonData, Class type){
			S3Log.log("ImageTextButtonSerializer", "Read: " + jsonData.getString("name"));

			if (!jsonData.has("texture")){
				ImageTextButton image =
				new ImageTextButton(jsonData.getString("text", ""), S3.skin, jsonData.getString("style", "default"));
				Serializer.ActorSerializer.readActor(jsonData, image);
				return image;
			}

			if (!jsonData.getString("texture").equalsIgnoreCase("")){
				ImageTextButton image =
				new ImageTextButton(jsonData.getString("text", ""), S3.skin,
									S3ResourceManager.getTextureRegion(jsonData.getString("texture")),
									jsonData.getString("texture"), jsonData.getString("style", "default"));
				Serializer.ActorSerializer.readActor(jsonData, image);
				return image;
			} else {
				ImageTextButton image =
				new ImageTextButton(jsonData.getString("text", ""), S3.skin, jsonData.getString("style", "default"));
				Serializer.ActorSerializer.readActor(jsonData, image);
				return image;
			}
		}
	}

	private static class ProgressBarSerializer implements Json.Serializer<ProgressBar>{

		@Override
		public void write(Json json, ProgressBar progressBar, Class knownType){

			S3Log.log("ProgressBarSerializer", "Write: " + progressBar.getName());
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActor(json, progressBar);
			json.writeValue("min", progressBar.getMinValue());
			json.writeValue("max", progressBar.getMaxValue());
			json.writeValue("stepSize", progressBar.getStepSize());
			json.writeObjectEnd();
		}

		@Override
		public ProgressBar read(Json json, JsonValue jsonData, Class type){
			S3Log.log("ProgressBarSerializer", "Read: " + jsonData.getString("name"));

			ProgressBar progressBar = new ProgressBar(
			Float.valueOf(jsonData.getString("min", "0.0")), Float.valueOf(jsonData.getString("max", "100.0")),
			Float.valueOf(jsonData.getString("stepSize", "1.0")), false, S3Skin.skin);
			Serializer.ActorSerializer.readActor(jsonData, progressBar);
			return progressBar;
		}

	}

	/**
	 *
	 */
	private static class FxEffectSerializer implements Json.Serializer<FxEffect>{

		@Override
		public void write(Json json, FxEffect fxEffect, Class knownType){

			S3Log.log("FxEffectSerializer", "Write: " + fxEffect.getName());
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActor(json, fxEffect);
			json.writeValue("effect", ((FxEffect) fxEffect).getEffect().getClass().getName());
			fxEffect.getEffect().write(json, fxEffect);
			json.writeObjectEnd();
		}

		@Override
		public FxEffect read(Json json, JsonValue jsonData, Class type){
			S3Log.log("LabelSerializer", "Read: " + jsonData.getString("name"));
			FxEffect fxEffect = new FxEffect(jsonData.getString("effect"));
			try {
				fxEffect.getEffect().read(json, jsonData);
			} catch (Exception ex){
			}
			Serializer.ActorSerializer.readActor(jsonData, fxEffect);
			return fxEffect;
		}
	}

	private static class SpriteSerializer implements Json.Serializer<Sprite>{

		@Override
		public Sprite read(Json arg0, JsonValue jv, Class arg2){

			float tmpDur = jv.getFloat("duration");
			if (tmpDur < 0){
				tmpDur = 0.0f;
			}

			Sprite sprite;
			if (jv.getInt("frameCount") != 1){
				sprite = new Sprite(jv.getString("textures").split(",")[0], jv.getInt("frameCount"),
									tmpDur, jv.getBoolean("single"));
			} else {
				sprite = new Sprite(tmpDur, jv.getBoolean("single"), jv.getString("textures").split(","));
			}

			sprite.isAnimationActive = jv.getBoolean("active");
			sprite.isAnimationLooping = jv.getBoolean("looping");
			sprite.isSingleImage = jv.getBoolean("single");
			sprite.setFrameCount(jv.getInt("frameCount"));
			sprite.setDuration(tmpDur);

			ActorSerializer.readActor(jv, sprite);
			return sprite;
		}


		@Override
		public void write(Json json, Sprite sprite, Class arg2){
			json.writeObjectStart();
			ActorSerializer.writeActor(json, sprite);
			json.writeValue("textures", sprite.toString());
			json.writeValue("duration", ((Sprite) sprite).getDuration());
			json.writeValue("active", ((Sprite) sprite).isAnimationActive);
			json.writeValue("looping", ((Sprite) sprite).isAnimationLooping);
			json.writeValue("single", ((Sprite) sprite).isSingleImage);
			json.writeValue("frameCount", ((Sprite) sprite).getFrameCount());
			json.writeObjectEnd();
		}

	}

	/**
	 *
	 */
	private static class GameAreaSerializer implements Json.Serializer<GameArea>{

		@Override
		public void write(Json json, GameArea gameArea, Class knownType){

			S3Log.log("GameAreaSerializer", "Write: " + gameArea.getName());
			json.writeObjectStart();
			Serializer.ActorSerializer.writeActor(json, gameArea);
			json.writeObjectEnd();
		}

		@Override
		public GameArea read(Json json, JsonValue jsonData, Class type){
			S3Log.log("GameAreaSerializer", "Read: " + jsonData.getString("name"));
			GameArea gameArea = new GameArea();
			Serializer.ActorSerializer.readActor(jsonData, gameArea);
			return gameArea;
		}
	}

	private static class ShaderSerializer implements Json.Serializer<SimpleShader.ShaderData>{

		@Override
		public void write(Json json, SimpleShader.ShaderData shader, Class knownType){

			S3Log.log("ShaderSerializer", "Write: " + shader.name);
			json.writeObjectStart();

			json.writeValue("class", shader.getClass().getName());
			json.writeValue("name", shader.name);
			json.writeValue("type", shader.type);
			json.writeValue("id", shader.id);
			json.writeValue("date", shader.date);
			json.writeValue("author", shader.author);
			json.writeValue("description", shader.description);
			json.writeValue("tags", shader.tags);

			json.writeValue("color", shader.color);
			json.writeValue("normal", shader.normal);
			json.writeValue("textures", shader.textures);

			json.writeValue("vertex", shader.vertex);
			json.writeValue("fragment", shader.fragment);

			json.writeObjectEnd();
		}

		@Override
		public SimpleShader.ShaderData read(Json json, JsonValue jsonData, Class type){
			S3Log.log("ShaderSerializer", "Read: " + jsonData.getString("name"));
			SimpleShader.ShaderData shader = new SimpleShader.ShaderData();

			shader.name = jsonData.getString("name");

			shader.type = jsonData.getString("type");
			shader.id = jsonData.getString("id");
			shader.date = jsonData.getString("date");
			shader.author = jsonData.getString("author");
			shader.description = jsonData.getString("description");
			shader.tags = jsonData.getString("tags");

			shader.color = Boolean.valueOf(jsonData.getString("color"));
			shader.normal = Boolean.valueOf(jsonData.getString("normal"));
			shader.textures = Integer.valueOf(jsonData.getString("textures"));

			shader.vertex = jsonData.getString("vertex");
			shader.fragment = jsonData.getString("fragment");

			return shader;
		}
	}

	private Serializer(){
	}

	public static void initialize(){
		registerSerializer(S3Cfg.class, new S3CfgSerializer());
		registerSerializer(S3App.class, new S3AppSerializer());
		registerSerializer(S3AppImpl.class, new S3AppSerializer());
		registerSerializer(Actor.class, new ActorSerializer());
		registerSerializer(Label.class, new LabelSerializer());
		registerSerializer(Button.class, new ButtonSerializer());
		registerSerializer(Image.class, new ImageSerializer());
		registerSerializer(ImageButton.class, new ImageButtonSerializer());
		registerSerializer(TextButton.class, new TextButtonSerializer());
		registerSerializer(Slider.class, new SliderSerializer());
		registerSerializer(TextField.class, new TextFieldSerializer());
		registerSerializer(CheckBox.class, new CheckBoxSerializer());
		registerSerializer(ImageTextButton.class, new ImageTextButtonSerializer());
		registerSerializer(ProgressBar.class, new ProgressBarSerializer());
		registerSerializer(FxEffect.class, new FxEffectSerializer());
		registerSerializer(Sprite.class, new SpriteSerializer());
		registerSerializer(GameArea.class, new GameAreaSerializer());
		registerSerializer(SimpleShader.ShaderData.class, new ShaderSerializer());
	}

	private static void registerSerializer(Class<?> clazz, Json.Serializer serializer){
		S3Log.info(TAG, "Register serializer: Class: " + clazz + " serializer: " + serializer);
		S3.json.setSerializer(clazz, serializer);
	}

}
