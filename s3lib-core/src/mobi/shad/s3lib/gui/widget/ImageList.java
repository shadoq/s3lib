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

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import mobi.shad.s3lib.gui.Gui;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3Log;
import mobi.shad.s3lib.main.S3ResourceManager;

import java.util.Vector;

/**
 * @author Jarek
 */
public class ImageList implements WidgetInterface{

	private float buttonIdx = 0;
	private ChangeListener localChangeListener = null;
	private boolean userChange = true;
	private String currentFile = "";
	private String currentDir = S3Constans.defaultDir;
	private Vector<ListItems> items = new Vector<ListItems>();
	private Vector<Cell> imageCels = new Vector<Cell>();
	private Table window = null;
	private Gui gui = new Gui();
	private Gui gui2 = new Gui();

	public class ListItems{

		public String fileName;
		public String filePatch;
		public boolean active;
	}

	@Override
	public void show(){
		show(null, S3Constans.defaultDir, null);
	}

	public void show(String[] items, ChangeListener changeListener){
		show(items, S3Constans.defaultDir, changeListener);
	}

	public void show(String[] items, String directory, ChangeListener changeListener){
		gui = new Gui();
		gui2 = new Gui();
		if (items != null){
			for (String item : items){
				if (!item.equalsIgnoreCase("") && item.length() > 0){
					getRow(item, gui);
				}
			}
		}

		currentDir = directory;
		localChangeListener = changeListener;
	}

	public Table getTable(){

		final FileBrowser fileBrowser = new FileBrowser();

		gui2.row();
		gui2.add(fileBrowser.getTable(currentFile, currentDir)).expandX().fillX().pad(0);
		Button addButton = gui2.addButton("Add File", null);

		addButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor){
				String file = fileBrowser.getFile();
				int size = imageCels.size();
				S3Log.log("ImageList::changed", "event: " + event + " actor:" + actor.getName() + " file: " + file
				+ " pos: " + size);

				if (!file.equalsIgnoreCase("") && file.length() > 0){
					getRow(file, gui);
				}
				if (localChangeListener != null){
					localChangeListener.changed(event, actor);
				}
			}
		});

		gui2.row();
		gui2.add(gui.getTable()).expandX().fillX().pad(0);

		window = gui2.getTable();
		return window;
	}

	@Override
	public void hide(){
	}

	public String[] getFilesPatch(){
		Array<String> outList = new Array<String>();
		for (ListItems item : items){
			if (item.active){
				outList.add(item.filePatch);
			}
		}
		String[] out = new String[outList.size];
		int i = 0;
		for (String item : outList){
			out[i] = item;
			i++;
		}

		return out;
	}

	public String[] getFilesName(){
		Array<String> outList = new Array<String>();
		for (ListItems item : items){
			if (item.active){
				outList.add(item.fileName);
			}
		}
		String[] out = new String[outList.size];
		int i = 0;
		for (String item : outList){
			out[i] = item;
			i++;
		}
		return out;
	}

	public ListItems[] getList(){
		Vector<ListItems> outList = new Vector<ListItems>();
		for (ListItems item : items){
			if (item.active){
				outList.add(item);
			}
		}
		return (ListItems[]) outList.toArray();
	}

	private Table getRow(String fileName, Gui gui){

		final int cellRowIdx = imageCels.size();

		ListItems item = new ListItems();
		item.filePatch = fileName;

		int index = fileName.lastIndexOf('/');
		item.fileName = fileName.substring(index + 1);

		item.active = true;
		items.add(cellRowIdx, item);

		final Table row = GuiResource.table("row");

		Image imageRow = new Image(S3ResourceManager.getTexture(fileName, S3Constans.textureButtonSize));
		imageRow.setWidth(S3Constans.buttonImageMedium);
		imageRow.setHeight(S3Constans.buttonImageMedium);
		Label guiLabel = GuiResource.label(item.fileName, item.fileName);
		TextButton guiTextButton = GuiResource.textButton("x", "x");
		guiTextButton.setName("" + cellRowIdx);
		guiTextButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor){

				S3Log.log("ImageList::changed", "event: " + event + " actor:" + actor.getName());
				Cell get = imageCels.get(cellRowIdx);
				get.height(0);
				get.pad(0);
				items.get(cellRowIdx).active = false;
				items.get(cellRowIdx).fileName = "";
				row.remove();
				window.invalidate();

				if (localChangeListener != null){
					localChangeListener.changed(event, actor);
				}
			}
		});

		row.add(imageRow).height(S3Constans.buttonImageMedium).width(S3Constans.buttonImageMedium).left();
		row.add(guiLabel).expandX().fillX().minWidth(S3Constans.gridX0_75).left();
		row.add(guiTextButton).right().height(S3Constans.buttonImageSmall).width(S3Constans.buttonImageSmall);

		gui.row();
		Cell add = gui.add(row).expandX().fillX().left();
		imageCels.add(cellRowIdx, add);
		return row;
	}
}
