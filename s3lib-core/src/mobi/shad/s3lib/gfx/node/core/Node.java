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
package mobi.shad.s3lib.gfx.node.core;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import mobi.shad.s3lib.gui.GuiForm;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Log;

import java.util.Iterator;

/**
 * @author Jarek
 */
public class Node{

	protected static int countId = 0;
	public String name = "";
	public int id = 0;
	public Type type = Type.NONE;
	public int level = 0;
	public int parentId = -1;
	public ChangeListener changeListener = null;
	public GuiForm formGui = null;
	public Node parent;
	public Array<Node> children = new Array<Node>(3);
	public Data data = null;
	public boolean disableChange = true;
	public boolean localProcess = false;
	public boolean isChangeParameters = false;
	protected final ChangeListener localChangeListener = new ChangeListener(){
		@Override
		public void changed(ChangeListener.ChangeEvent event, Actor actor){

			if (disableChange){
				return;
			}
			if (S3.input.isTouched() && actor instanceof Slider){
				return;
			}
			if (S3Constans.NOTICE){
				S3Log.log("FxFilter::localChangeListener", "event: " + event.toString() + " actor: " + actor.toString() + " ev: " + event.isHandled());
			}
			disableChange = true;
			isChangeParameters = true;
			if (changeListener != null){
				changeListener.changed(event, actor);
			} else {
				if (localProcess){
					processLocal();
				} else {
					process();
				}
			}
			disableChange = false;
		}
	};

	public static enum Type{

		NONE, TEXTURE, TEXTURE_FILTER,
		SHADER, EFFECT_SIZE, MESH_2D_POINT, MESH_3D_POINT, MESH_3D_OBJECT,
		CAMERA
	}

	/**
	 *
	 */
	public Node(){
		this("Node_" + countId, Type.NONE, null, null);
	}

	/**
	 * @param name
	 */
	public Node(String name){
		this(name, Type.NONE, null, null);
	}

	/**
	 * @param name
	 */
	public Node(String name, Type type, GuiForm effectData){
		this(name, type, effectData, null);
	}

	/**
	 * @param name
	 * @param type
	 * @param formGui
	 * @param changeListener
	 */
	public Node(String name, Type type, GuiForm effectData, ChangeListener changeListener){
		this.name = name;
		this.type = type;
		this.formGui = effectData;
		this.changeListener = changeListener;
		this.id = countId;
		countId++;
		children = new Array<Node>(3);
		level = 0;
		parent = null;
		parentId = 0;
		data = new Data();
	}

	/**
	 *
	 */
	protected void initData(){
	}

	/**
	 *
	 */
	public void initForm(){
	}

	public Node setFormGui(GuiForm formGui, ChangeListener listener){
		this.formGui = formGui;
		this.changeListener = listener;
		initForm();
		return this;
	}

	/**
	 * @param node
	 */
	public Node addChild(Node node){
		node.parent = this;
		node.parentId = id;
		node.level = level + 1;
		node.data = data;
		children.add(node);
		return node;
	}

	/**
	 * @param index
	 * @return
	 */
	public Node getChild(int index){
		return (Node) children.get(index);
	}

	/**
	 * @param name
	 * @return
	 */
	public Node serach(String serachName){

		if (name.equalsIgnoreCase(serachName)){
			return this;
		}
		Iterator<Node> it = children.iterator();
		while (it.hasNext()){
			Node next = it.next();
			Node serach = next.serach(serachName);
			if (serach != null){
				return serach;
			}
		}
		return null;
	}

	/**
	 * @param level
	 * @return
	 */
	public String printTree(){

		String out = "\n" + charMultiply(level, "\t") + toString();
		if (children.size == 0){
			return out;
		}
		Iterator<Node> it = children.iterator();
		while (it.hasNext()){
			Node next = it.next();
			out = out + next.printTree();
		}
		return out;
	}

	/**
	 * @param level
	 * @return
	 */
	public String printPostorder(){

		String out = "";
		level++;

		if (children.size == 0){
			out = out + "\n" + charMultiply(level, "\t") + toString();
			return out;
		}
		Iterator<Node> it = children.iterator();
		while (it.hasNext()){
			Node next = it.next();
			out = out + next.printPostorder();
		}
		out = out + "\n" + charMultiply(level, "\t") + toString();
		return out;
	}

	/**
	 * @return
	 */
	public int size(){
		int size = 1;
		Iterator<Node> it = children.iterator();
		while (it.hasNext()){
			Node next = it.next();
			size = size + next.size();
		}
		return size;
	}

	/**
	 * @return
	 */
	@Override
	public String toString(){
		return "name: " + name + " id: " + id + " level: " + level + " parentId: " + parentId + " ";
	}

	/**
	 * @return
	 */
	public int maxDepth(){

		int depth = level + 1;

		for (Node fxFilter : children){
			int maxDepth = fxFilter.maxDepth();
			if (maxDepth > depth){
				depth = maxDepth;
			}
		}
		return depth;
	}

	/**
	 * @param n
	 * @param c
	 * @return
	 */
	private String charMultiply(int n, String c){
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < n; i++){
			buffer.append(c);
		}
		return buffer.toString();
	}

	/**
	 * @return
	 */
	public void process(){

		for (Node fxFilter : children){
			fxFilter.process();
		}
		processLocal();
		if (S3Constans.NOTICE){
			S3Log.log("FxProcess::process", "Process:" + name + "\n" + data, 0);
		}
	}

	/**
	 *
	 */
	protected void processLocal(){
	}

	/**
	 * @param effectTime
	 * @param sceneTime
	 * @param endTime
	 * @param procent
	 * @param isPause
	 */
	public void updateProcess(float effectTime, float sceneTime, float endTime, float procent, boolean isPause){
		for (Node fxFilter : children){
			fxFilter.updateProcess(effectTime, sceneTime, endTime, procent, isPause);
		}
		updateLocal(effectTime, sceneTime, endTime, procent, isPause);
	}

	/**
	 * @param effectTime
	 * @param sceneTime
	 * @param endTime
	 * @param procent
	 * @param isPause
	 */
	protected void updateLocal(float effectTime, float sceneTime, float endTime, float procent, boolean isPause){
	}
}
