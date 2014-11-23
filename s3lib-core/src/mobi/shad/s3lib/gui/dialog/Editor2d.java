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
package mobi.shad.s3lib.gui.dialog;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ArrayMap;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.gui.GuiUtil;
import mobi.shad.s3lib.gui.widget.WidgetInterface;
import mobi.shad.s3lib.main.*;

/**
 * @author Jarek
 */
public class Editor2d extends DialogBase implements WidgetInterface{

	protected Window backendWindow;
	ArrayMap<String, Image> touchPoints = new ArrayMap<String, Image>(10);
	//
	// Area size
	//
	private float currentX;
	private float currentY;
	private float currentWidth;
	private float currentHeight;
	//
	// Editor Image
	//
	private Widget areaImage;
	private Widget gridLayer;
	private Texture gridtextureLayer;
	private Image dotLeftImage;
	private Image dotRightImage;
	private Image dotTopImage;
	private Image dotBottomImage;
	private Image dotCenterImage;
	private Image dotTopRightImage;
	private Image dotBottomLeftImage;
	private Image dotBottomRightImage;
	private Image dotTopLeftImage;
	private Label sizeElementLabel;
	//
	//
	//
	private float deltaX = 0;
	private float deltaY = 0;
	private float pointX = 0;
	private float pointY = 0;
	private float lastPointX = 0;
	private float lastPointY = 0;
	private float aspectRatioX = 1;
	private float aspectRatioY = 1;
	//
	// Status
	//
	private boolean isAreaImageClick = false;
	private Image pointClick = null;
	private String pointClickName = "";

	public float getX(){
		return currentX;
	}

	public float getY(){
		return currentY;
	}

	public float getWidth(){
		return currentWidth;
	}

	public float getHeight(){
		return currentHeight;
	}

	public void create(final float startX, final float startY, final float width, final float height){
		create(S3ResourceManager.getTexture("texture/def256.jpg", S3Constans.textureImageSize), startX, startY, width, height, null);
	}

	public void create(final float startX, final float startY, final float width, final float height, final ChangeListener changeListener){
		create(S3ResourceManager.getTexture("texture/def256.jpg", S3Constans.textureImageSize), startX, startY, width, height, changeListener);
	}

	public void create(String areaTextureName, final float startX, final float startY, final float width, final float height){
		create(S3ResourceManager.getTexture(areaTextureName, S3Constans.textureImageSize), startX, startY, width, height, null);
	}

	public void create(Texture areaTexture, final float startX, final float startY, final float width, final float height, final ChangeListener changeListener){

		super.create();

		backendWindow = GuiResource.windowBackend();
		backendWindow.setColor(1f, 1f, 1f, 0f);
		backendWindow.setVisible(false);
		S3.stage.addActor(backendWindow);

		mainWindow = GuiResource.window("WidgetBase", "WidgetBase");
		mainWindow.setColor(1f, 1f, 1f, 0f);
		mainWindow.setVisible(false);
		mainWindow.setModal(true);
		mainWindow.setMovable(false);

		this.currentX = startX;
		this.currentY = startY;
		this.currentWidth = width;
		this.currentHeight = height;
		//
		// Grid Layer
		//
		Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		gridtextureLayer = new Texture(pixmap);

		gridLayer = new Widget(){

			@Override
			public void draw(Batch batch, float parentAlpha){
				batch.setColor(0.8f, 0.8f, 0.8f, 0.3f);
				batch.draw(gridtextureLayer, 0, S3Constans.gridY1, S3Screen.width, 1);
				batch.draw(gridtextureLayer, 0, S3Constans.gridY2, S3Screen.width, 1);
				batch.draw(gridtextureLayer, 0, S3Constans.gridY3, S3Screen.width, 1);
				batch.draw(gridtextureLayer, 0, S3Constans.gridY4, S3Screen.width, 1);
				batch.draw(gridtextureLayer, 0, S3Constans.gridY5, S3Screen.width, 3);
				batch.draw(gridtextureLayer, 0, S3Constans.gridY6, S3Screen.width, 1);
				batch.draw(gridtextureLayer, 0, S3Constans.gridY7, S3Screen.width, 1);
				batch.draw(gridtextureLayer, 0, S3Constans.gridY8, S3Screen.width, 1);
				batch.draw(gridtextureLayer, 0, S3Constans.gridY9, S3Screen.width, 1);

				batch.draw(gridtextureLayer, S3Constans.gridX1, 0, 1, S3Screen.height);
				batch.draw(gridtextureLayer, S3Constans.gridX2, 0, 1, S3Screen.height);
				batch.draw(gridtextureLayer, S3Constans.gridX3, 0, 1, S3Screen.height);
				batch.draw(gridtextureLayer, S3Constans.gridX4, 0, 1, S3Screen.height);
				batch.draw(gridtextureLayer, S3Constans.gridX5, 0, 3, S3Screen.height);
				batch.draw(gridtextureLayer, S3Constans.gridX6, 0, 1, S3Screen.height);
				batch.draw(gridtextureLayer, S3Constans.gridX7, 0, 1, S3Screen.height);
				batch.draw(gridtextureLayer, S3Constans.gridX8, 0, 1, S3Screen.height);
				batch.draw(gridtextureLayer, S3Constans.gridX9, 0, 1, S3Screen.height);
			}
		};
		gridLayer.setX(0);
		gridLayer.setY(0);
		gridLayer.setWidth(S3Screen.width);
		gridLayer.setHeight(S3Screen.height);

		//
		//
		//
		final TextureRegion areaRegion = new TextureRegion(areaTexture);

		areaImage = new Widget(){

			@Override
			public void draw(Batch batch, float parentAlpha){
				Color color = getColor();
				batch.setColor(color.r, color.g, color.b, parentAlpha);
				batch.draw(areaRegion, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
			}
		};
		areaImage.setX(currentX);
		areaImage.setY(currentY);
		areaImage.setWidth(currentWidth);
		areaImage.setHeight(currentHeight);

		//
		// Create texture
		//
		Pixmap redPixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		redPixmap.setColor(Color.RED);
		redPixmap.fillCircle(16, 16, 14);
		Texture redTexture = new Texture(redPixmap);
		Pixmap yellowPixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		yellowPixmap.setColor(Color.YELLOW);
		yellowPixmap.fillCircle(16, 16, 14);
		Texture yellowTexture = new Texture(yellowPixmap);
		Pixmap greenPixmap = new Pixmap(32, 32, Pixmap.Format.RGBA8888);
		greenPixmap.setColor(Color.GREEN);
		greenPixmap.fillCircle(16, 16, 14);
		Texture greenTexture = new Texture(greenPixmap);

		//
		//
		//
		TextButton textButtonOk = GuiResource.textButton("Save", "Save");
		TextButton textButtonFullScreen = GuiResource.textButton("Full", "Full");
		TextButton textButtonCenter = GuiResource.textButton("Center", "Center");
		TextButton textButtonCancel = GuiResource.textButton("Cancel", "Cancel");

		//
		//
		//
		textButtonOk.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				S3Log.log("Editor2d::textButtonOk::clicked", " event: " + event + " actor: " + actor.toString(), 2);
				hide();
				if (changeListener != null){
					changeListener.changed(event, areaImage);
				}
			}
		});

		textButtonFullScreen.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				S3Log.log("Editor2d::textButtonFullScreen::clicked", " event: " + event + " x: " + x + " y: " + y, 2);
				currentX = 0;
				currentY = 0;
				currentWidth = S3Screen.width;
				currentHeight = S3Screen.height;
				setAreaPosistion();
				setTouchPosistion();
			}
		});

		textButtonCenter.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				S3Log.log("Editor2d::textButtonCenter::clicked", " event: " + event + " x: " + x + " y: " + y, 2);
				currentX = S3Screen.centerX - (float) S3Screen.centerX / 2;
				currentY = S3Screen.centerY - (float) S3Screen.centerY / 2;
				currentWidth = (float) S3Screen.centerX;
				currentHeight = (float) S3Screen.centerY;
				setAreaPosistion();
				setTouchPosistion();
			}
		});

		textButtonCancel.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				S3Log.log("Editor2d::textButtonCancel::clicked", " event: " + event + " x: " + x + " y: " + y, 2);
				currentX = startX;
				currentY = startY;
				currentWidth = width;
				currentHeight = height;
				hide();
			}
		});

		sizeElementLabel = GuiResource.label("X: Y: Width: Height:", "sizeElementLabel");
		mainWindow.row();
		mainWindow.add(textButtonOk);
		mainWindow.add(textButtonFullScreen);
		mainWindow.add(textButtonCenter);
		mainWindow.add(textButtonCancel);
		mainWindow.row();
		mainWindow.add(sizeElementLabel).colspan(4).left();
		GuiUtil.windowPosition(mainWindow, 0, 0);

		S3.stage.addActor(gridLayer);
		S3.stage.addActor(areaImage);

		//
		// Create AreaPlot
		//
		dotLeftImage = createAreaPoint("dotLeftImage", redTexture, (int) (currentX - 16), (int) (currentY
		+ (currentHeight / 2) - 16));
		dotRightImage = createAreaPoint("dotRightImage", redTexture, (int) (currentX + currentWidth - 16), (int) (currentY
		+ (currentHeight / 2) - 16));

		dotTopImage = createAreaPoint("dotTopImage", redTexture, (int) (currentX + (currentWidth / 2) - 16), (int) (currentY
		+ currentHeight - 16));
		dotBottomImage = createAreaPoint("dotBottomImage", redTexture, (int) (currentX + (currentWidth / 2) - 16), (int) (currentY - 16));

		dotTopLeftImage = createAreaPoint("dotTopLeftImage", yellowTexture, (int) (currentX - 16), (int) (currentY
		+ currentHeight - 16));
		dotTopRightImage = createAreaPoint("dotTopRightImage", yellowTexture, (int) (currentX + currentWidth - 16), (int) (currentY
		+ currentHeight - 16));

		dotBottomLeftImage = createAreaPoint("dotBottomLeftImage", yellowTexture, (int) (currentX + currentWidth - 16), (int) (currentY - 16));
		dotBottomRightImage = createAreaPoint("dotBottomRightImage", yellowTexture, (int) (currentX + currentWidth - 16), (int) (currentY - 16));

		dotCenterImage = createAreaPoint("dotCenter", greenTexture, (int) (currentX + (currentWidth / 2) - 16), (int) (currentY
		+ (currentHeight / 2) - 16));

		S3.stage.addActor(mainWindow);

		//
		// Assign Listener
		//
		mainWindow.addListener(new InputListener(){
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer){

				lastPointX = pointX;
				lastPointY = pointY;
				pointX = x;
				pointY = y;

				deltaX = pointX - lastPointX;
				deltaY = pointY - lastPointY;

				S3Log.log("dotCenterImage::touchDragged", " event: " + event + " x: " + x + " y: " + y + " pointer: "
				+ pointer + " deltaX: " + deltaX + " deltaY: " + deltaY, 3);

				if (pointClick != null){
					S3Log.log("dotCenterImage::touchDragged", " Point click: " + pointClickName + " aX: "
					+ aspectRatioX + " aY: " + aspectRatioY, 2);
					if (pointClickName.equalsIgnoreCase("dotLeftImage")){
						if (currentX > 0 && deltaX < 0 && currentWidth >= 32){
							currentX += deltaX;
							currentWidth -= deltaX;
						} else if (currentX < S3Screen.width && deltaX > 0 && currentWidth > 32){
							currentX += deltaX;
							currentWidth -= deltaX;
						}
						setAreaPosistion();
						setTouchPosistion();
					} else if (pointClickName.equalsIgnoreCase("dotRightImage")){
						if (currentX + currentWidth > 0 && currentX + currentWidth < S3Screen.width
						&& currentWidth >= 32 && currentX > -1 && deltaX > 0){
							currentWidth += deltaX;
						} else if (currentX + currentWidth > 0 && currentX + currentWidth <= S3Screen.width
						&& currentWidth >= 32 && currentX > -1 && deltaX < 0){
							currentWidth += deltaX;
						}
						setAreaPosistion();
						setTouchPosistion();
					} else if (pointClickName.equalsIgnoreCase("dotTopImage")){
						if (currentY + currentHeight > 0 && currentY + currentHeight < S3Screen.height
						&& currentHeight >= 32 && currentY > -1 && deltaY > 0){
							currentHeight += deltaY;
						} else if (currentY + currentHeight > 0 && currentY + currentHeight <= S3Screen.height
						&& currentHeight >= 32 && currentY > -1 && deltaY < 0){
							currentHeight += deltaY;
						}
						setAreaPosistion();
						setTouchPosistion();
					} else if (pointClickName.equalsIgnoreCase("dotBottomImage")){
						if (currentY > 0 && deltaY < 0 && currentHeight >= 32){
							currentY += deltaY;
							currentHeight -= deltaY;
						} else if (currentY < S3Screen.height && deltaY > 0 && currentHeight > 32){
							currentY += deltaY;
							currentHeight -= deltaY;
						}
						setAreaPosistion();
						setTouchPosistion();
					} else if (pointClickName.equalsIgnoreCase("dotTopRightImage")){
						float delta = (deltaX + deltaY) / 2;
						currentHeight += delta * aspectRatioY;
						currentWidth += delta * aspectRatioX;
						setAreaPosistion();
						setTouchPosistion();
					} else if (pointClickName.equalsIgnoreCase("dotBottomLeftImage")){
						float delta = (deltaX + deltaY) / 2;
						currentX += delta * aspectRatioX;
						currentY += delta * aspectRatioY;
						currentHeight -= delta * aspectRatioY;
						currentWidth -= delta * aspectRatioX;
						setAreaPosistion();
						setTouchPosistion();
					} else if (pointClickName.equalsIgnoreCase("dotCenter")){
						currentX += deltaX;
						currentY += deltaY;
						setAreaPosistion();
						setTouchPosistion();
					} else if (pointClickName.equalsIgnoreCase("dotTopLeftImage")){
						float delta = (deltaX - deltaY) / 2;
						currentX += delta * aspectRatioY;
						currentHeight -= delta * aspectRatioY;
						currentWidth -= delta * aspectRatioY;
						setAreaPosistion();
						setTouchPosistion();
					} else if (pointClickName.equalsIgnoreCase("dotBottomRightImage")){
						float delta = (deltaX - deltaY) / 2;
						currentY -= delta * aspectRatioY;
						currentHeight += delta * aspectRatioY;
						currentWidth += delta * aspectRatioY;
						setAreaPosistion();
						setTouchPosistion();
					}

					//
					// dotBottomRightImage

				} else if (isAreaImageClick){
					currentX += deltaX;
					currentY += deltaY;
					setAreaPosistion();
					setTouchPosistion();
				}
			}

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				S3Log.log("dotCenterImage::touchDown", " event: " + event + " x: " + x + " y: " + y + " pointer: "
				+ pointer + " button: " + button, 3);

				pointX = x;
				pointY = y;
				lastPointX = x;
				lastPointY = y;

				isAreaImageClick = checkAreaClick(x, y);
				pointClick = checkPointClick(x, y);
				return true;
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button){
				S3Log.log("dotCenterImage::touchUp", " event: " + event + " x: " + x + " y: " + y + " pointer: "
				+ pointer + " button: " + button, 2);
				isAreaImageClick = false;
				pointClick = null;
				pointClickName = "";
			}
		});

		setAreaPosistion();
		setTouchPosistion();
	}

	@Override
	public void show(){

		super.show();
		backendWindow.setVisible(true);
		if (fadeDuration > 0){
			backendWindow.addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
		}

		gridLayer.setVisible(true);
		areaImage.setVisible(true);
		dotLeftImage.setVisible(true);
		dotRightImage.setVisible(true);
		dotTopImage.setVisible(true);
		dotBottomImage.setVisible(true);
		dotTopRightImage.setVisible(true);
		dotBottomLeftImage.setVisible(true);
		dotCenterImage.setVisible(true);

		dotBottomRightImage.setVisible(true);
		dotTopLeftImage.setVisible(true);

		if (fadeDuration > 0){
			gridLayer.addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
			areaImage.addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
			dotLeftImage.addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
			dotRightImage.addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
			dotTopImage.addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
			dotBottomImage.addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
			dotTopRightImage.addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
			dotBottomLeftImage.addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
			dotCenterImage.addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
			dotBottomRightImage.addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
			dotTopLeftImage.addAction(Actions.fadeIn(fadeDuration, Interpolation.fade));
		}
	}

	@Override
	public void hide(){
		super.hide();
		backendWindow.addAction(Actions.sequence(Actions.fadeOut(fadeDuration, Interpolation.fade), Actions
		.removeActor()));
		gridLayer.addAction(Actions.sequence(Actions.fadeOut(fadeDuration, Interpolation.fade), Actions.removeActor()));
		areaImage.addAction(Actions.sequence(Actions.fadeOut(fadeDuration, Interpolation.fade), Actions.removeActor()));
		dotLeftImage.addAction(Actions.sequence(Actions.fadeOut(fadeDuration, Interpolation.fade), Actions
		.removeActor()));
		dotRightImage.addAction(Actions.sequence(Actions.fadeOut(fadeDuration, Interpolation.fade), Actions
		.removeActor()));
		dotTopImage
		.addAction(Actions.sequence(Actions.fadeOut(fadeDuration, Interpolation.fade), Actions.removeActor()));
		dotBottomImage.addAction(Actions.sequence(Actions.fadeOut(fadeDuration, Interpolation.fade), Actions
		.removeActor()));
		dotTopRightImage.addAction(Actions.sequence(Actions.fadeOut(fadeDuration, Interpolation.fade), Actions
		.removeActor()));
		dotBottomLeftImage.addAction(Actions.sequence(Actions.fadeOut(fadeDuration, Interpolation.fade), Actions
		.removeActor()));
		dotCenterImage.addAction(Actions.sequence(Actions.fadeOut(fadeDuration, Interpolation.fade), Actions
		.removeActor()));
		dotBottomRightImage.addAction(Actions.sequence(Actions.fadeOut(fadeDuration, Interpolation.fade), Actions
		.removeActor()));
		dotTopLeftImage.addAction(Actions.sequence(Actions.fadeOut(fadeDuration, Interpolation.fade), Actions
		.removeActor()));
	}

	public void dispose(){
	}

	/**
	 * @param texture
	 * @param x
	 * @param y
	 * @return
	 */
	private Image createAreaPoint(String ID, Texture texture, int x, int y){

		Image imageActor = new Image(texture);
		imageActor.setX(x);
		imageActor.setY(y);
		imageActor.setWidth(32);
		imageActor.setHeight(32);
		imageActor.setVisible(false);
		touchPoints.put(ID, imageActor);
		S3.stage.addActor(imageActor);
		return imageActor;
	}

	/*
	 *
	 */
	private void setAreaPosistion(){

		if (currentX < 0){
			currentX = 0;
		}
		if (currentY < 0){
			currentY = 0;
		}
		if (currentX + currentWidth > S3Screen.width){
			currentX = S3Screen.width - currentWidth;
		}
		if (currentY + currentHeight > S3Screen.height){
			currentY = S3Screen.height - currentHeight;
		}

		if (currentWidth < 32){
			currentWidth = 32;
		}
		if (currentHeight < 32){
			currentHeight = 32;
		}

		areaImage.setX(currentX);
		areaImage.setY(currentY);
		areaImage.setWidth(currentWidth);
		areaImage.setHeight(currentHeight);

		aspectRatioX = currentWidth / currentHeight;
		aspectRatioY = currentHeight / currentWidth;

		sizeElementLabel.setText("X: " + ((int) currentX) + " Y: " + ((int) currentY) + " W: " + ((int) currentWidth)
								 + " H: " + ((int) currentHeight));
	}

	private void setTouchPosistion(){
		for (int i = 0; i < touchPoints.size; i++){
			Image point = touchPoints.getValueAt(i);
			String pointName = touchPoints.getKeyAt(i);
			if (pointName.equalsIgnoreCase("dotLeftImage")){
				point.setX(currentX - 16);
				point.setY((currentY + (currentHeight / 2) - 16));
			} else if (pointName.equalsIgnoreCase("dotRightImage")){
				point.setX((currentX + currentWidth - 16));
				point.setY((currentY + (currentHeight / 2) - 16));
			} else if (pointName.equalsIgnoreCase("dotTopImage")){
				point.setX((currentX + (currentWidth / 2) - 16));
				point.setY((currentY + currentHeight - 16));
			} else if (pointName.equalsIgnoreCase("dotBottomImage")){
				point.setX((currentX + (currentWidth / 2) - 16));
				point.setY(currentY - 16);
			} else if (pointName.equalsIgnoreCase("dotTopRightImage")){
				point.setX((currentX + currentWidth - 16));
				point.setY((currentY + currentHeight - 16));
			} else if (pointName.equalsIgnoreCase("dotBottomLeftImage")){
				point.setX((currentX - 16));
				point.setY((currentY - 16));
			} else if (pointName.equalsIgnoreCase("dotCenter")){
				point.setX((currentX + (currentWidth / 2) - 16));
				point.setY((currentY + (currentHeight / 2) - 16));
			} else if (pointName.equalsIgnoreCase("dotTopLeftImage")){
				point.setX((currentX - 16));
				point.setY((currentY + currentHeight - 16));
			} else if (pointName.equalsIgnoreCase("dotBottomRightImage")){
				point.setX((currentX + currentWidth - 16));
				point.setY((currentY - 16));
			}
		}
	}

	private Image checkPointClick(float x, float y){

		for (int i = 0; i < touchPoints.size; i++){
			Image point = touchPoints.getValueAt(i);
			if ((x > point.getX() && x < (point.getX() + point.getWidth()))
			&& (y > point.getY() && y < (point.getY() + point.getHeight()))){
				pointClickName = touchPoints.getKeyAt(i);
				return point;
			}
		}
		return null;
	}

	private boolean checkAreaClick(float x, float y){
		if ((x > currentX && x < (currentX + currentWidth)) && (y > currentY && y < (currentY + currentHeight))){
			return true;
		}
		return false;
	}
}
