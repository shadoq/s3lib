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

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3File;

/**
 * @author Jarek
 */
public class HtmlView extends Table{

	private CssStyle defaultStyle = new CssStyle();
	private Skin skin;
	private FileHandle recourceDir;
	private String parseCode;
	private XmlReader xmlReader;
	private XmlReader.Element rootXML;

	public class CssStyle{

		float paddingLeft = 0;
		float paddingTop = 0;
		float paddingRight = 0;
		float paddingBottom = 0;
		Color color = null;
		int collSpan = 1;
		int align = 0;
		float scaleX = 0;
		float scaleY = 0;
		String font = "";

		@Override
		public String toString(){
			return "CssStyle{" + "paddingLeft=" + paddingLeft + ", paddingTop=" + paddingTop + ", paddingRight=" + paddingRight + ", paddingBottom=" + paddingBottom + ", color=" + color + ", collSpan=" + collSpan + ", align=" + align + ", scaleX=" + scaleX + ", scaleY=" + scaleY + ", font=" + font + '}';
		}
	}

	public HtmlView(){
	}

	public HtmlView(final String parseCode, final FileHandle recourceDir, final Skin skin){
		super(skin);
		this.skin = skin;
		this.recourceDir = recourceDir;
		this.parseCode = parseCode;
		parseXML();
	}

	public HtmlView(final FileHandle fileCode, final FileHandle recourceDir, final Skin skin){
		super(skin);
		this.skin = skin;
		this.recourceDir = recourceDir;
		this.parseCode = fileCode.readString();
		parseXML();
	}

	private void parseXML(){
		xmlReader = new XmlReader();
		rootXML = xmlReader.parse(parseCode);
		parseNode(rootXML, this);
		if (S3Constans.GUI_DEBUG){
			debug(Table.Debug.all);
		}
	}

	/**
	 * @param node
	 * @param mainTable
	 * @return
	 */
	private Element parseNode(Element node, Table mainTable){
		int childCount = node.getChildCount();
		for (int i = 0; i < childCount; i++){
			Element element = node.getChild(i);
			if (element.getChildCount() > 0){
				Table nodeTable = new Table(skin);
				parseNode(element, nodeTable);
			}

			//
			// Dodawanie elemntów do tabeli
			// GuiResuorce.button(S3ResourceManager.getTexture("icon/create.png", S3Constans.textureButtonSize));

			//
			// tag: p
			//
			if (element.getName().equalsIgnoreCase("p")){
				CssStyle style = parseCssStyle(element);
				mainTable.row();
				addTableNode(element, style, mainTable);
				//
				// tag: div
				//
			} else if (element.getName().equalsIgnoreCase("div")){
				CssStyle style = parseCssStyle(element);
				mainTable.row();
				addTableNode(element, style, mainTable);
				//
				// tag: h1
				//
			} else if (element.getName().equalsIgnoreCase("h1")){
				CssStyle style = parseCssStyle(element);
				style.scaleX = 1.8f;
				style.scaleY = 1.8f;
				mainTable.row();
				addTableNode(element, style, mainTable);
				//
				// tag: h2
				//
			} else if (element.getName().equalsIgnoreCase("h2")){
				CssStyle style = parseCssStyle(element);
				style.scaleX = 1.6f;
				style.scaleY = 1.6f;
				mainTable.row();
				addTableNode(element, style, mainTable);
				//
				// tag: h3
				//
			} else if (element.getName().equalsIgnoreCase("h3")){
				CssStyle style = parseCssStyle(element);
				style.scaleX = 1.4f;
				style.scaleY = 1.4f;
				mainTable.row();
				addTableNode(element, style, mainTable);
				//
				// tag: h4
				//
			} else if (element.getName().equalsIgnoreCase("h4")){
				CssStyle style = parseCssStyle(element);
				style.scaleX = 1.2f;
				style.scaleY = 1.2f;
				mainTable.row();
				addTableNode(element, style, mainTable);
				//
				// tag: h5
				//
			} else if (element.getName().equalsIgnoreCase("h5")){
				CssStyle style = parseCssStyle(element);
				style.scaleX = 0.8f;
				style.scaleY = 0.8f;
				mainTable.row();
				addTableNode(element, style, mainTable);
				//
				// tag: h6
				//
			} else if (element.getName().equalsIgnoreCase("h6")){
				CssStyle style = parseCssStyle(element);
				style.scaleX = 0.6f;
				style.scaleY = 0.6f;
				mainTable.row();
				addTableNode(element, style, mainTable);
				//
				// tag: img
				//
			} else if (element.getName().equalsIgnoreCase("img")){
				String src = element.getAttribute("src", "");
				int size = element.getIntAttribute("size", S3Constans.textureImageSize);
				Image image = new Image(S3File.getFileTextureCenter(recourceDir.path() + "/" + src, size));
				addActor(element, image, mainTable);
			} else if (element.getName().equalsIgnoreCase("button")){
				String src = element.getAttribute("src", "");
				int size = element.getIntAttribute("size", S3Constans.textureImageSizeLow);
				Button button = GuiResource.button(S3File.getFileTextureCenter(recourceDir.path() + "/" + src, size));
				button.setWidth(S3Constans.buttonImageSmall);
				button.setHeight(S3Constans.buttonImageSmall);
				addActor(element, button, mainTable);
			}
		}
		return node;
	}

	/**
	 * @param node
	 * @return
	 */
	private CssStyle parseCssStyle(final Element node){
		final CssStyle style = new CssStyle();

		//
		// Color
		//
		String color = node.getAttribute("color", "");
		if (color.equalsIgnoreCase("yellow")){
			style.color = Color.YELLOW;
		} else if (color.equalsIgnoreCase("red")){
			style.color = Color.RED;
		} else if (color.equalsIgnoreCase("green")){
			style.color = Color.GREEN;
		} else if (color.equalsIgnoreCase("cyan")){
			style.color = Color.CYAN;
		} else if (color.equalsIgnoreCase("blue")){
			style.color = Color.BLUE;
		} else if (color.equalsIgnoreCase("gray")){
			style.color = Color.GRAY;
		} else if (color.equalsIgnoreCase("light_gray")){
			style.color = Color.LIGHT_GRAY;
		} else if (color.equalsIgnoreCase("dark_gray")){
			style.color = Color.DARK_GRAY;
		} else if (color.equalsIgnoreCase("orange")){
			style.color = Color.ORANGE;
		} else if (color.equalsIgnoreCase("magenta")){
			style.color = Color.MAGENTA;
		} else if (color.equalsIgnoreCase("pink")){
			style.color = Color.PINK;
		}

		//
		// Align
		//
		String align = node.getAttribute("align", "");
		if (align.equalsIgnoreCase("right")){
			style.align = Align.right;
		} else if (align.equalsIgnoreCase("left")){
			style.align = Align.left;
		} else if (align.equalsIgnoreCase("center")){
			style.align = Align.center;
		} else {
			style.align = Align.left;
		}

		//
		// Font
		//
		String font = node.getAttribute("font", "");
		//		if (font.equalsIgnoreCase("sans12")){
		//			style.font="sans12";
		//		} else if (font.equalsIgnoreCase("sans13")){
		//			style.font="sans13";
		//		} else if (font.equalsIgnoreCase("sans14")){
		//			style.font="sans14";
		//		} else if (font.equalsIgnoreCase("sans15")){
		//			style.font="sans15";
		//		} else if (font.equalsIgnoreCase("droid14")){
		//			style.font="droid14";
		//		} else if (font.equalsIgnoreCase("droid15")){
		//			style.font="droid15";
		//		} else if (font.equalsIgnoreCase("droid16")){
		//			style.font="droid16";
		//		} else if (font.equalsIgnoreCase("droid17")){
		//			style.font="droid17";
		//		} else if (font.equalsIgnoreCase("droid18")){
		//			style.font="droid18";
		//		} else if (font.equalsIgnoreCase("droid22")){
		//			style.font="droid22";
		//		} else if (font.equalsIgnoreCase("droid24")){
		//			style.font="droid24";
		//		}

		//
		// CollSpan
		//
		int collSpan = node.getIntAttribute("collspan", 1);
		if (collSpan > 1){
			style.collSpan = collSpan;
		}

		return style;
	}

	/**
	 * @param node
	 * @param style
	 * @param table
	 * @return
	 */
	private Cell addTableNode(final Element node, final CssStyle style, final Table table){

		Label nodeLabel;
		if (style.font.equalsIgnoreCase("")){
			nodeLabel = new Label(node.getText(), skin);
			if (style.color != null){
				nodeLabel.setColor(style.color);
			}
		} else {
			if (style.color != null){
				nodeLabel = new Label(node.getText(), skin, style.font, style.color);
			} else {
				nodeLabel = new Label(node.getText(), skin, style.font, Color.WHITE);
			}
		}
		nodeLabel.setWrap(true);

		if (style.scaleX > 0.0f){
			nodeLabel.setFontScaleX(style.scaleX);
		}
		if (style.scaleY > 0.0f){
			nodeLabel.setFontScaleY(style.scaleY);
		}
		Cell cell =
		table.add(nodeLabel).pad(style.paddingTop, style.paddingLeft, style.paddingBottom, style.paddingRight).colspan(style.collSpan).expandX().fillX();
		if (style.align > 0){
			cell.align(style.align);
		}
		return cell;
	}

	private void addActor(final Element element, final Actor actor, final Table mainTable){
		CssStyle style = parseCssStyle(element);
		mainTable.row();
		String src = element.getAttribute("src", "");
		String align = element.getAttribute("align", "");
		int width = element.getIntAttribute("width", 0);
		int height = element.getIntAttribute("height", 0);
		int collSpan = element.getIntAttribute("collspan", 1);
		int padding = element.getIntAttribute("padding", 0);
		int cellwidth = element.getIntAttribute("cellwidth", 0);
		int cellheight = element.getIntAttribute("cellheight", 0);
		int fill = element.getIntAttribute("fill", 0);

		int size = element.getIntAttribute("size", S3Constans.textureImageSize);
		int length = 0;
		if (element.getText() != null){
			length = element.getText().length();
		}

		if (width > 0){
			actor.setWidth(width);
		}
		if (height > 0){
			actor.setHeight(height);
		}

		if (align.equalsIgnoreCase("center")){
			mainTable.row();
			Cell cell = mainTable.add(actor).center().colspan(collSpan).pad(padding);
			if (width > 0){
				cell.width(width);
			}
			if (height > 0){
				cell.height(height);
			}
			if (cellwidth > 0){
				cell.width(cellwidth);
			}
			if (cellheight > 0){
				cell.height(cellheight);
			}
			if (fill > 0){
				cell.fill();
			}
			if (length > 0){
				mainTable.row();
				addTableNode(element, style, mainTable);
			}
		} else if (align.equalsIgnoreCase("left")){
			mainTable.row();
			Cell cell = mainTable.add(actor).left().colspan(collSpan).pad(padding).uniform();
			if (width > 0){
				cell.width(width);
			}
			if (height > 0){
				cell.height(height);
			}
			if (cellwidth > 0){
				cell.width(cellwidth);
			}
			if (cellheight > 0){
				cell.height(cellheight);
			}
			if (fill > 0){
				cell.fill();
			}
			if (length > 0){
				addTableNode(element, style, mainTable);
			}
		} else if (align.equalsIgnoreCase("right")){
			mainTable.row();
			if (length > 0){
				addTableNode(element, style, mainTable);
			}
			Cell cell = mainTable.add(actor).right().colspan(collSpan).pad(padding).uniform();
			if (width > 0){
				cell.width(width);
			}
			if (height > 0){
				cell.height(height);
			}
			if (cellwidth > 0){
				cell.width(cellwidth);
			}
			if (cellheight > 0){
				cell.height(cellheight);
			}
			if (fill > 0){
				cell.fill();
			}
		} else {
			mainTable.row();
			Cell cell = mainTable.add(actor).colspan(collSpan).pad(padding);
			if (width > 0){
				cell.width(width);
			}
			if (height > 0){
				cell.height(height);
			}
			if (cellwidth > 0){
				cell.width(cellwidth);
			}
			if (cellheight > 0){
				cell.height(cellheight);
			}
			if (fill > 0){
				cell.fill();
			}
			if (length > 0){
				addTableNode(element, style, mainTable);
			}
		}
	}
}
