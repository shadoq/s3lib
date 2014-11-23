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
package mobi.shad.s3lib.gui.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Log;

/**
 * @author Jarek
 */
public class ColorBrowser implements WidgetInterface{

	public Color color = null;
	private Stage tempStage;
	private float colorR = 1;
	private float colorG = 1;
	private float colorB = 1;
	private float colorA = 1;
	private Pixmap pixmap;
	private Texture texture;
	private TextureRegion region;
	private Image imageActor;
	private Slider sliderR;
	private Slider sliderG;
	private Slider sliderB;
	private Slider sliderA;
	private WidgetColorListener listener = null;
	private Table window = null;
	private Label labelTextR = null;
	private Label labelTextG = null;
	private Label labelTextB = null;
	private Label labelTextA = null;
	private Label labelR = null;
	private Label labelG = null;
	private Label labelB = null;
	private Label labelA = null;
	private boolean alphaChanel = false;

	/**
	 *
	 */
	@Override
	public void show(){
		alphaChanel = false;
	}

	public void show(boolean alphaChanel){
		this.alphaChanel = false;
	}

	public Table getTable(){

		//
		// Wygenerowanie buttonow
		//
		sliderR = GuiResource.slider(0, 100, 1, "colorDialogSliderR");
		sliderG = GuiResource.slider(0, 100, 1, "colorDialogSliderG");
		sliderB = GuiResource.slider(0, 100, 1, "colorDialogSliderB");
		sliderA = GuiResource.slider(0, 100, 1, "colorDialogSliderA");

		//
		// Fix slider FixScroll
		//
		sliderR.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
			}
		});

		sliderG.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
			}
		});

		sliderB.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
			}
		});

		sliderA.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				event.stop();
				return false;
			}
		});

		labelTextR = GuiResource.label("R", "colorTextDialogLabelR");
		labelTextG = GuiResource.label("G", "colorTextDialogLabelG");
		labelTextB = GuiResource.label("B", "colorTextDialogLabelB");
		labelTextA = GuiResource.label("A", "colorTextDialogLabelA");

		labelR = GuiResource.label("red", "colorDialogLabelR");
		labelG = GuiResource.label("green", "colorDialogLabelG");
		labelB = GuiResource.label("blue", "colorDialogLabelB");
		labelA = GuiResource.label("alpha", "colorDialogLabelA");

		color = new Color();

		//
		// Tworzenie image textury podgladu
		//
		pixmap = new Pixmap(128, 32, Pixmap.Format.RGBA8888); // Pixmap.Format.RGBA8888);
		pixmap.setColor(1, 1, 1, 1);
		pixmap.fillRectangle(0, 0, 128, 32);
		texture = new Texture(pixmap);
		region = new TextureRegion(texture);
		imageActor = new Image(region);

		//
		// Podpiecie akcji do buttonow i sliderow
		//
		sliderR.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				float value = sliderR.getValue();
				if (S3Constans.NOTICE){
					S3Log.log("WidgetColorBrowser:sliderR", " value: " + value);
				}
				colorR = value / 100;
				setBtnColorSampleColor();
			}
		});

		sliderG.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				float value = sliderG.getValue();
				if (S3Constans.NOTICE){
					S3Log.log("WidgetColorBrowser:sliderG", " value: " + value);
				}
				colorG = value / 100;
				setBtnColorSampleColor();
			}
		});

		sliderB.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				float value = sliderB.getValue();
				if (S3Constans.NOTICE){
					S3Log.log("WidgetColorBrowser:sliderB", " value: " + value);
				}
				colorB = value / 100;
				setBtnColorSampleColor();
			}
		});

		sliderA.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				float value = sliderA.getValue();
				if (S3Constans.NOTICE){
					S3Log.log("WidgetColorBrowser:sliderA", " value: " + value);
				}
				colorA = value / 100;
				setBtnColorSampleColor();
			}
		});

		//
		// Utworzenie okna
		//
		window = GuiResource.table("colorBrowserTable");
		window.row();
		window.add(labelTextR).left().width(S3Constans.gridX0_30);
		window.add(sliderR).left().fillX().expandX();
		window.add(labelR).left().width(S3Constans.gridX0_30);
		window.row();
		window.add(labelTextG).left().width(S3Constans.gridX0_30);
		window.add(sliderG).left().fillX().expandX();
		window.add(labelG).left().width(S3Constans.gridX0_30);
		window.row();
		window.add(labelTextB).left().width(S3Constans.gridX0_30);
		window.add(sliderB).left().fillX().expandX();
		window.add(labelB).left().width(S3Constans.gridX0_30);

		if (alphaChanel){
			window.row();
			window.add(labelTextA).left().width(S3Constans.gridX0_30);
			window.add(sliderA).left().fillX().expandX();
			window.add(labelA).left().width(S3Constans.gridX0_30);
		}

		window.row();
		window.add().left().width(S3Constans.gridX0_30);
		window.add(imageActor).left().colspan(2);

		//
		// Inicjacja stanów
		//
		if (colorR < 0){
			colorR = 0;
		}
		if (colorR > 1){
			colorR = 1;
		}
		if (colorG < 0){
			colorG = 0;
		}
		if (colorG > 1){
			colorG = 1;
		}
		if (colorB < 0){
			colorB = 0;
		}
		if (colorB > 1){
			colorB = 1;
		}
		if (colorA < 0){
			colorA = 0;
		}
		if (colorA > 1){
			colorA = 1;
		}

		sliderR.setValue(colorR * 100);
		sliderG.setValue(colorG * 100);
		sliderB.setValue(colorB * 100);
		sliderA.setValue(colorA * 100);
		setBtnColorSampleColor();

		return window;
	}

	/**
	 * Zamkniecie okna i zwrocenie imputu
	 */
	@Override
	public void hide(){
		S3.stage.getRoot().removeActor(window);
		S3Log.log("WidgetColorBrowser:hide", "Color from dialog: " + color.toString());
	}

	/**
	 * @param color
	 */
	public void set(Color color){
		colorR = color.r;
		colorG = color.g;
		colorB = color.b;
		colorA = color.a;
		if (sliderR != null){
			sliderR.setValue(colorR * 100);
			sliderG.setValue(colorG * 100);
			sliderB.setValue(colorB * 100);
			sliderA.setValue(colorA * 100);
			setBtnColorSampleColor();
		}
	}

	/**
	 * @param r
	 * @param g
	 * @param b
	 */
	public void set(float r, float g, float b){
		Color col = new Color(r, g, b, 1);
		set(col);
	}

	/**
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void set(float r, float g, float b, float a){
		Color col = new Color(r, g, b, a);
		set(col);
	}

	public Color get(){
		return color;
	}

	public void setColorListener(WidgetColorListener colorListener){
		listener = colorListener;
	}

	/**
	 *
	 */
	public void setBtnColorSampleColor(){
		imageActor.setColor(colorR, colorG, colorB, colorA);
		color.set(colorR, colorG, colorB, colorA);

		if (labelR != null){

			String text = "" + String.format("%.0f", colorR * 100);
			labelR.setText(text);

			text = "" + String.format("%.0f", colorG * 100);
			labelG.setText(text);

			text = "" + String.format("%.0f", colorB * 100);
			labelB.setText(text);

			text = "" + String.format("%.0f", colorA * 100);
			labelA.setText(text);
		}
		if (listener != null){
			listener.Action(color);
		}
	}
}
