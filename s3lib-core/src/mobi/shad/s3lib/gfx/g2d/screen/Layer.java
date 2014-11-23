/*******************************************************************************
 * Copyright 2012
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
package mobi.shad.s3lib.gfx.g2d.screen;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;

/**
 * @author Jarek
 */
public class Layer extends Group implements InputProcessor, NodeInterface{

	private SnapshotArray<Actor> children;
	private int childrenSize = 0;

	/**
	 *
	 */
	@Override
	public void enter(){
		children = getChildren();
		childrenSize = children.size;
		for (int i = 0; i < childrenSize; i++){
			Actor actor = children.get(i);

			if (actor instanceof Layer){
				Layer layer = (Layer) actor;
				layer.enter();
			}
		}
	}

	/**
	 *
	 */
	@Override
	public void exit(){
		children = getChildren();
		childrenSize = children.size;
		for (int i = 0; i < childrenSize; i++){
			Actor actor = children.get(i);

			if (actor instanceof Layer){
				Layer layer = (Layer) actor;
				layer.exit();
			}
		}
	}

	/**
	 * @param x
	 * @param y
	 * @param pointer
	 * @param button
	 * @return
	 */
	@Override
	public boolean touchDown(int x, int y, int pointer, int button){
		boolean handled = false;
		children = getChildren();
		childrenSize = children.size;

		int index = 0;
		while (index < childrenSize && !handled){
			Actor actor = children.get(index);

			if (actor instanceof Layer){
				Layer layer = (Layer) actor;
				handled = layer.touchDown(x, y, pointer, button);
			}
			index++;
		}
		return handled;
	}

	/**
	 * @param x
	 * @param y
	 * @param pointer
	 * @param button
	 * @return
	 */
	@Override
	public boolean touchUp(int x, int y, int pointer, int button){
		boolean handled = false;
		children = getChildren();
		childrenSize = children.size;

		int index = 0;
		while (index < childrenSize && !handled){
			Actor actor = children.get(index);

			if (actor instanceof Layer){
				Layer layer = (Layer) actor;
				handled = layer.touchUp(x, y, pointer, button);
			}
			index++;
		}
		return handled;
	}

	/**
	 * @param x
	 * @param y
	 * @param pointer
	 * @return
	 */
	@Override
	public boolean touchDragged(int x, int y, int pointer){
		boolean handled = false;

		children = getChildren();
		childrenSize = children.size;

		int index = 0;
		while (index < childrenSize && !handled){
			Actor actor = children.get(index);

			if (actor instanceof Layer){
				Layer layer = (Layer) actor;
				handled = layer.touchDragged(x, y, pointer);
			}
			index++;
		}
		return handled;
	}

	/**
	 * @param x
	 * @param y
	 * @return
	 */
	@Override
	public boolean mouseMoved(int x, int y){
		boolean handled = false;

		children = getChildren();
		childrenSize = children.size;
		int index = 0;
		while (index < childrenSize && !handled){
			Actor actor = children.get(index);

			if (actor instanceof Layer){
				Layer layer = (Layer) actor;
				handled = layer.mouseMoved(x, y);
			}
			index++;
		}
		return handled;
	}

	/**
	 * @param keycode
	 * @return
	 */
	@Override
	public boolean keyDown(int keycode){
		return false;
	}

	/**
	 * @param keycode
	 * @return
	 */
	@Override
	public boolean keyUp(int keycode){
		return false;
	}

	/**
	 * @param character
	 * @return
	 */
	@Override
	public boolean keyTyped(char character){
		return false;
	}

	/**
	 * @param amount
	 * @return
	 */
	@Override
	public boolean scrolled(int amount){
		return false;
	}

	/**
	 * @param x
	 * @param y
	 * @param touchable
	 * @return
	 */
	@Override
	public Actor hit(float x, float y, boolean touchable){
		Actor hit = super.hit(x, y, touchable);
		if (hit == this){
			hit = null;
		}
		return hit;
	}
}
