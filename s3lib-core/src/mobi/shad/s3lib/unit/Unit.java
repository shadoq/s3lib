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
package mobi.shad.s3lib.unit;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.ArrayMap;

/**
 * Created by Jarek on 2014-05-16.
 */
public class Unit extends Actor{

	private static int index = 0;
	public String id = "";
	public int current = 0;
	public Unit next, previous;
	public ArrayMap<Class<? extends Component>, Component> components;

	public Unit(){
		this.id = "Untitled_" + index;
		current = index;
		index++;
		components = new ArrayMap<Class<? extends Component>, Component>(5);
	}

	public Unit(String id){
		this.id = id;
		current = index;
		index++;
		components = new ArrayMap<Class<? extends Component>, Component>(5);
	}

	public Unit add(Component component){
		return add(component.getClass(), component);
	}

	public Unit add(Class<? extends Component> klass, Component component){
		if (has(klass)){
			remove(klass);
		}
		component.unit = this;
		components.put(klass, component);
		return this;
	}

	public Component remove(Class<? extends Component> klass){
		if (has(klass)){
			Component component = components.removeKey(klass);
			component.unit = null;
			return component;
		}
		return null;
	}

	public boolean has(Class<? extends Component> klass){
		return components.containsKey(klass);
	}

	/**
	 * Gets and array of all the components
	 *
	 * @return
	 */
	public Component[] allComponents(){
		return components.values;
	}

	public Unit getNext(){
		return next;
	}

	public void setNext(Unit next){
		this.next = next;
	}

	public boolean hasNext(){
		return next != null;
	}

	public Unit getPrevious(){
		return previous;
	}

	public void setPrevious(Unit previous){
		this.previous = previous;
	}

	public boolean hasPrevious(){
		return previous != null;
	}

}
