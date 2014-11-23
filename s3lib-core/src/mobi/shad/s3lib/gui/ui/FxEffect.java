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
package mobi.shad.s3lib.gui.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.ArrayMap;
import mobi.shad.s3lib.gfx.effect.*;
import mobi.shad.s3lib.main.S3Log;
import mobi.shad.s3lib.main.interfaces.GuiDefinition;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;

public class FxEffect extends Widget implements GuiDefinition{

	private static final String[] effectList = {
	Wait.class.getSimpleName(),
	BobsMulti.class.getSimpleName(),
	Point.class.getSimpleName(),
	Copper.class.getSimpleName(),
	PointMulti.class.getSimpleName(),
	StarField.class.getSimpleName(),
	TopBorder.class.getSimpleName(),
	Plasma.class.getSimpleName(),
	Grid.class.getSimpleName(),
	SimpleShader.class.getSimpleName(),
	};
	private static final String[] effectClassList = {
	Wait.class.getName(),
	BobsMulti.class.getName(),
	Point.class.getName(),
	Copper.class.getName(),
	PointMulti.class.getName(),
	StarField.class.getName(),
	TopBorder.class.getName(),
	Plasma.class.getName(),
	Grid.class.getName(),
	SimpleShader.class.getName(),
	};
	private static final String TAG = "FxEffect";

	protected boolean needSwapEffect = false;
	protected String newEffectName = "";

	protected float effectTime;
	protected float effectPercent;
	protected AbstractEffect abstractEffect;
	private boolean inSetValues = false;

	public FxEffect(){
		abstractEffect = new Wait();
		setWidth(64);
		setHeight(64);
		abstractEffect.setParentActor(this);
	}

	public FxEffect(String effectName){
		S3Log.log(TAG, "Create effect: " + effectName);
		createEffect(effectName);
	}

	public FxEffect(AbstractEffect abstractEffect){
		this.abstractEffect = abstractEffect;
		abstractEffect.setParentActor(this);
	}

	private void createEffect(String effectName){
		abstractEffect = null;
		try {
			abstractEffect = (AbstractEffect) Class.forName(effectName).newInstance();
			abstractEffect.setParentActor(this);
		} catch (InstantiationException ex){
			S3Log.error(TAG, "Error create effect", ex);
		} catch (IllegalAccessException ex){
			S3Log.error(TAG, "Error create effect", ex);
		} catch (ClassNotFoundException ex){
			S3Log.error(TAG, "Error create effect", ex);
		}
	}

	private void createEffectForName(String effectName){
		abstractEffect = null;
		try {
			int index = getEffectIndexList(effectName);
			abstractEffect = (AbstractEffect) Class.forName(effectClassList[index]).newInstance();
			abstractEffect.setParentActor(this);
		} catch (InstantiationException ex){
			S3Log.error(TAG, "Error create effect", ex);
		} catch (IllegalAccessException ex){
			S3Log.error(TAG, "Error create effect", ex);
		} catch (ClassNotFoundException ex){
			S3Log.error(TAG, "Error create effect", ex);
		}
	}

	public int getEffectIndexList(String className){

		for (int i = 0; i < effectList.length; i++){
			if (effectList[i].equalsIgnoreCase(className)){
				return i;
			}
		}
		return 0;
	}

	public AbstractEffect getEffect(){
		return abstractEffect;
	}

	@Override
	public void draw(Batch batch, float parentAlpha){

		if (inSetValues){
			return;
		}

		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		if (abstractEffect != null){
			abstractEffect.render(batch, parentAlpha);
		}
	}

	@Override
	public void act(float delta){
		super.act(delta);

		if (inSetValues){
			return;
		}

		effectTime += delta;
		if (needSwapEffect){
			createEffectForName(newEffectName);
			needSwapEffect = false;
		}
		if (abstractEffect != null){
			abstractEffect.update(effectTime, effectTime, 10, 1, false);
		}
	}

	@Override
	public ArrayList<String[]> getGuiDefinition(){

		ArrayList<String[]> guiDef = new ArrayList<>();
		guiDef.add(new String[]{"Fx Effect", "", "LABEL", ""});
		guiDef.add(
		new String[]{"effect", "effect", "LIST", "Wait", StringUtils.join(Arrays.asList(effectList), ",")});
		guiDef.add(new String[]{"name", "name", "TEXT", ""});

		if (abstractEffect != null){
			guiDef.add(new String[]{abstractEffect.getClass().getSimpleName(), "", "LABEL", ""});
			try {
				abstractEffect.getGuiDefinition(guiDef);
			} catch (Exception ex){
				S3Log.error(this.getClass().getName(), "Exception in getGuiDefinition", ex);
			}
		}
		Label.getGuiDefinition(guiDef);
		return guiDef;
	}

	@Override
	public ArrayMap<String, String> getValues(){
		ArrayMap<String, String> values = new ArrayMap<>();
		values.put("effect", abstractEffect.getClass().getSimpleName());

		if (abstractEffect != null){
			abstractEffect.getValues(values);
		}

		values.put("name", getName());
		values.put("x", getX() + "");
		values.put("y", getY() + "");
		values.put("width", getWidth() + "");
		values.put("height", getHeight() + "");
		values.put("originX", getOriginX() + "");
		values.put("originY", getOriginY() + "");
		values.put("rotation", getRotation() + "");
		values.put("color", getColor().toString());
		values.put("touchable", getTouchable() + "");
		values.put("visible", isVisible() + "");
		values.put("zindex", getZIndex() + "");
		return values;
	}

	@Override
	public void setValues(String changeKey, ArrayMap<String, String> values){

		inSetValues = true;

		if (changeKey.equals("effect")){
			if (needSwapEffect == false){
				needSwapEffect = true;
				newEffectName = values.get("effect");
			}
		}

		if (Integer.valueOf(values.get("zindex")) < 0){
			values.put("zindex", "0");
		}

		if (abstractEffect != null){
			try {
				abstractEffect.setValues(changeKey, values);
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}

		setName(values.get("name"));
		setX(Float.valueOf(values.get("x")));
		setY(Float.valueOf(values.get("y")));
		setWidth(Float.valueOf(values.get("width")));
		setHeight(Float.valueOf(values.get("height")));
		setOriginX(Float.valueOf(values.get("originX")));
		setOriginY(Float.valueOf(values.get("originY")));
		setRotation(Float.valueOf(values.get("rotation")));
		setColor(Color.valueOf(values.get("color")));
		setTouchable(Touchable.valueOf(values.get("touchable")));
		setVisible(Boolean.valueOf(values.get("visible")));
		setZIndex((int) (float) (Integer.valueOf(values.get("zindex"))));

		inSetValues = false;
	}

}
