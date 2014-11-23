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
package mobi.shad.s3lib.gui;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import mobi.shad.s3lib.main.S3Log;
import mobi.shad.s3lib.main.S3Screen;

/**
 * Short method tool to operation of Gui Object
 */
public class GuiUtil{

	private static final String TAG = GuiUtil.class.getSimpleName();

	public enum Position{
		CENTER,
		LEFT, TOP, RIGHT, BOTTOM,
		TOP_LEFT, TOP_RIGHT,
		BOTTOM_LEFT, BOTTOM_RIGHT
	}

	private GuiUtil(){

	}

	/**
	 * @param actor
	 * @param sizeX
	 * @param sizeY
	 * @return
	 */
	public static Actor normalizeSize(Actor actor, float sizeX, float sizeY){
		if (sizeX < 0.0){
			sizeX = 0;
		} else if (sizeX > 1){
			sizeX = 1;
		}
		if (sizeY < 0.0){
			sizeY = 0;
		} else if (sizeY > 1){
			sizeY = 1;
		}
		actor.setWidth(sizeX * S3Screen.width);
		actor.setHeight(sizeY * S3Screen.height);
		return actor;
	}

	/**
	 * @param sizeY
	 * @return
	 */
	public static int normalizeY(float sizeY){
		if (sizeY < 0.0){
			sizeY = 0;
		} else if (sizeY > 1){
			sizeY = 1;
		}
		return (int) (sizeY * S3Screen.height);
	}

	/**
	 * @param sizeX
	 * @return
	 */
	public static int normalizeX(float sizeX){
		if (sizeX < 0.0){
			sizeX = 0;
		} else if (sizeX > 1){
			sizeX = 1;
		}
		return (int) (sizeX * S3Screen.width);
	}

	/**
	 * Wyrównuje pozycję okna do grida 0 - wyrówanie do lewej 5 - centrowanie 10
	 * - wyrownanie do prawej
	 *
	 * @param window
	 * @param gridX
	 * @param gridY
	 * @return
	 */
	public static Table windowPosition(Table window, int gridX, int gridY){
		if (gridX > 10){
			gridX = 10;
		} else if (gridX < 0){
			gridX = 0;
		}
		if (gridY > 10){
			gridY = 10;
		} else if (gridX < 0){
			gridY = 0;
		}
		int posX = (int) ((S3Screen.width / 10) * gridX);
		int posY = (int) ((S3Screen.height / 10) * gridY);
		window.pack();
		int posXCalc = posX;
		int posYCalc = posY;
		if (gridX == 5){
			posXCalc = (int) (S3Screen.centerX - (window.getWidth() / 2));
		} else if (gridX == 10){
			posXCalc = (int) (S3Screen.width - window.getWidth());
		}
		if (gridY == 5){
			posYCalc = (int) (S3Screen.centerY - (window.getHeight() / 2));
		} else if (gridY == 10){
			posYCalc = (int) (S3Screen.height - window.getHeight());
		}
		S3Log.debug(TAG, "Set grid: " + gridX + ":" + gridY + " - " + posX + ":" + posY + " - " + posXCalc + ":" + posYCalc);
		window.setX(posXCalc);
		window.setY(posYCalc);
		return window;
	}

	/**
	 * @param window
	 * @param position
	 * @return
	 */
	public static Table windowPosition(Table window, Position position){
		switch (position){
			case CENTER:
				return windowPosition(window, 5, 5);
			case LEFT:
				return windowPosition(window, 0, 5);
			case TOP:
				return windowPosition(window, 5, 10);
			case RIGHT:
				return windowPosition(window, 10, 5);
			case BOTTOM:
				return windowPosition(window, 5, 0);
			case TOP_LEFT:
				return windowPosition(window, 0, 10);
			case TOP_RIGHT:
				return windowPosition(window, 10, 10);
			case BOTTOM_LEFT:
				return windowPosition(window, 0, 0);
			case BOTTOM_RIGHT:
				return windowPosition(window, 10, 0);
		}
		return null;
	}

	/**
	 * @param source
	 * @param destination
	 */
	public static void copyActor(Actor source, Actor destination){
		destination.setBounds(source.getX(), source.getY(), source.getWidth(), source.getHeight());
		destination.setColor(source.getColor());
		destination.setName(source.getName());
		destination.setOrigin(source.getOriginX(), source.getOriginY());
		destination.setRotation(source.getRotation());
		destination.setScale(source.getScaleX(), source.getScaleY());
		destination.setTouchable(source.getTouchable());
		destination.setUserObject(source.getUserObject());
		destination.setVisible(source.isVisible());
		destination.setZIndex(source.getZIndex());
		destination.getStage();
	}

	/**
	 * @param actor
	 * @return
	 */
	public static Rectangle rectangleFromActor(Actor actor){
		return new Rectangle(actor.getX(), actor.getY(), actor.getWidth(), actor.getHeight());
	}

}
