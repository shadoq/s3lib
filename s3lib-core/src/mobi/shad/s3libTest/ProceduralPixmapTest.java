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

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mobi.shad.s3lib.gfx.pixmap.disort.Disort;
import mobi.shad.s3lib.gfx.pixmap.disort.RotoZoom;
import mobi.shad.s3lib.gfx.pixmap.disort.Vortex;
import mobi.shad.s3lib.gfx.pixmap.filter.*;
import mobi.shad.s3lib.gfx.pixmap.procedural.*;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.main.*;

/**
 * @author Jarek
 */
public class ProceduralPixmapTest extends S3App{

	private Pixmap pixmap;
	private Texture texture;
	private ProceduralInterface procText = null;
	private String[] effectList = {
	//
	// New Function
	//
	FaddingPixel.class.getName(),
	Falling.class.getName(),
	Fire.class.getName(),
	MetaBalls.class.getName(),
	Object2D.class.getName(),
	Plasma.class.getName(),
	Wave.class.getName(),
	//
	// Procedural
	//
	Mandelbrot.class.getName(),
	Mandelbrot2.class.getName(),
	Mandelbrot3.class.getName(),
	Mandelbrot4.class.getName(),
	Mandelbrot5.class.getName(),
	Julia.class.getName(),
	PerlinNoise1D.class.getName(),
	PerlinNoise2D.class.getName(),
	PerlinNoise2Dv2.class.getName(),
	Cell.class.getName(),
	Flat.class.getName(),
	//
	// Filter
	//
	GradientMap.class.getName(),
	MultiplyMask.class.getName(),
	Noise.class.getName(),
	NoiseGrey.class.getName(),
	Glow.class.getName(),
	Gradient.class.getName(),
	ColorFilter.class.getName(),
	Threshold.class.getName(),
	Invert.class.getName(),
	Normals.class.getName(),
	Alpha.class.getName(),
	RotoZoom.class.getName(),
	Vortex.class.getName(),
	Disort.class.getName(),};
	private String[] effectListName = {
	//
	// New Function
	//
	FaddingPixel.class.getSimpleName(),
	Falling.class.getSimpleName(),
	Fire.class.getSimpleName(),
	MetaBalls.class.getSimpleName(),
	Object2D.class.getSimpleName(),
	Plasma.class.getSimpleName(),
	Wave.class.getSimpleName(),
	//
	// Procedural
	//
	Mandelbrot.class.getSimpleName(),
	Mandelbrot2.class.getSimpleName(),
	Mandelbrot3.class.getSimpleName(),
	Mandelbrot4.class.getSimpleName(),
	Mandelbrot5.class.getSimpleName(),
	Julia.class.getSimpleName(),
	PerlinNoise1D.class.getSimpleName(),
	PerlinNoise2D.class.getSimpleName(),
	PerlinNoise2Dv2.class.getSimpleName(),
	Cell.class.getSimpleName(),
	Flat.class.getSimpleName(),
	//
	// Filter
	//
	GradientMap.class.getSimpleName(),
	MultiplyMask.class.getSimpleName(),
	Noise.class.getSimpleName(),
	NoiseGrey.class.getSimpleName(),
	Glow.class.getSimpleName(),
	Gradient.class.getSimpleName(),
	ColorFilter.class.getSimpleName(),
	Threshold.class.getSimpleName(),
	Invert.class.getSimpleName(),
	Normals.class.getSimpleName(),
	Alpha.class.getSimpleName(),
	RotoZoom.class.getSimpleName(),
	Vortex.class.getSimpleName(),
	Disort.class.getSimpleName(),};

	@Override
	public void initalize(){

		gui();
		pixmap = new Pixmap(S3Constans.proceduralTextureSizeHight, S3Constans.proceduralTextureSizeHight, Pixmap.Format.RGBA8888);
		//		pixmap=new Pixmap(64, 64, Pixmap.Format.RGBA8888);
		Mandelbrot.generate(pixmap, 0.0, 0.0, 1.0, 1.0, 32);
		texture = new Texture(pixmap);

		mobi.shad.s3lib.gfx.math.PerlinNoise.premInit();
	}

	@Override
	public void update(){
	}

	@Override
	public void render(S3Gfx g){
		g.clear(0.2f, 0.2f, 0.2f);
		texture.draw(pixmap, 0, 0);
		g.drawBackground(texture);
	}

	public void gui(){

		Window window = GuiResource.window("Test Effect", "window");
		window.defaults().align(Align.top | Align.left);


		int i = 0;
		final ButtonGroup buttonGroup = new ButtonGroup();

		for (String effectName : effectList){
			Button buttonLoop = GuiResource.textButton(effectListName[i], effectName);

			buttonLoop.setName(effectName);
			buttonGroup.add(buttonLoop);

			buttonLoop.addListener(new ClickListener(){
				@Override
				public void clicked(InputEvent event, float x, float y){
					Button checked = buttonGroup.getChecked();

					try {
						procText = (ProceduralInterface) Class.forName(checked.getName()).newInstance();
						if (procText instanceof ProceduralInterface){
							//							pixmap=new Pixmap(S3Constans.proceduralTextureSizeHight, S3Constans.proceduralTextureSizeHight, Pixmap.Format.RGBA8888);
							//							pixmap.setColor(Color.BLACK);
							//							pixmap.fill();
							procText.random(pixmap);
							//							procText.generate(pixmap);
						}
					} catch (InstantiationException ex){
						S3Log.error(this.getClass().getName(), "Error creare effect ...", ex);
					} catch (IllegalAccessException ex){
						S3Log.error(this.getClass().getName(), "Error creare effect ...", ex);
					} catch (ClassNotFoundException ex){
						S3Log.error(this.getClass().getName(), "Error creare effect ...", ex);
					}
				}
			});

			window.add(buttonLoop).fillX();
			if (i % 3 == 2){
				window.row();
			}
			i++;
		}
		window.pack();
		window.setX(0);
		window.setY(S3Screen.height - window.getHeight());
		S3.stage.addActor(window);

	}
}
