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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import mobi.shad.s3lib.gui.GuiResource;
import mobi.shad.s3lib.main.S3Constans;
import mobi.shad.s3lib.main.S3File;
import mobi.shad.s3lib.main.S3Lang;
import mobi.shad.s3lib.main.S3Log;

/**
 * @author Jarek
 */
public class FileBrowser implements WidgetInterface{

	private String currentDir = "";
	private String currentFile = "";
	private String currentPath = "";
	private boolean getOk = false;
	private WidgetStringListener listenerFileChange = null;
	private Label fileLabel = null;
	private ScrollPane fileList = null;
	private List<String> list = null;
	private boolean externalMode = false;
	private TextButton chengeDirButton;

	@Override
	public void show(){
	}

	public Table getTable(String fileToPatch, String defaultDir){
		return getTable(fileToPatch, defaultDir, "");
	}

	public Table getTable(String filePatch, String defaultDir, final String listId){

		S3Log.log("FileBrowser::getTable", "fileToPatch: " + filePatch + " defaultDir: " + defaultDir + " listId: "
		+ listId, 3);

		currentDir = defaultDir;
		currentFile = "";
		getOk = false;

		//
		// Read Dir
		//
		getOk = false;
		filePatch = filePatch.trim();
		if (filePatch.equalsIgnoreCase("")){
			filePatch = defaultDir + "/";
			currentFile = "";
			currentDir = defaultDir;
			currentPath = defaultDir + "/";
		} else {
			FileHandle fh;
			if (externalMode){
				fh = S3File.getFileHandle(filePatch);
			} else {
				fh = Gdx.files.absolute(filePatch);
			}
			if (fh.parent().path().equalsIgnoreCase("/")){
				currentFile = "";
				currentDir = fh.path();
				currentPath = fh.path();
			} else {
				currentFile = fh.name();
				currentDir = fh.parent().path();
				currentPath = fh.path();
			}
		}

		if (S3Constans.RESOURCE_DEBUG){
			S3Log.log("FileBrowser", "-- Read Directory -----------------", 3);
			S3Log.log("FileBrowser", "filePatch: " + filePatch, 3);
			S3Log.log("FileBrowser", "defaultDir: " + defaultDir, 3);
			S3Log.log("FileBrowser", "currentFile: " + currentFile, 3);
			S3Log.log("FileBrowser", "currentDir: " + currentDir, 3);
			S3Log.log("FileBrowser", "currentPath: " + currentPath, 3);
			S3Log.log("FileBrowser", "------------------------------------", 3);
		}

		//
		// Elementy GUI
		//
		list = GuiResource.list(new String[]{}, "listFile");
		fileList = GuiResource.scrollPane(list, "fileListScroll");
		fileList.setScrollingDisabled(true, false);
		fileList.setFlickScroll(false);
		fileList.setFadeScrollBars(false);

		fileLabel = GuiResource.label("File: ", "labelFile" + listId);
		if (!currentFile.equalsIgnoreCase("")){
			fileLabel.setText("File: " + currentFile);
			list.getSelection().set(currentFile);
		}

		chengeDirButton = GuiResource.textButton(S3Lang.get("chengeDir"), "ChengeDir");

		actionDirectory(currentFile, currentDir, externalMode);

		//
		// Akcje GUI
		//
		list.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor){

				String value = (String) list.getSelection().first();
				if (value == null){
					return;
				}
				value = value.trim();
				if (value.equalsIgnoreCase("")){
					return;
				}
				currentFile = value;
				currentPath = currentDir + "/" + value;
				if (S3Constans.DEBUG){
					S3Log.log("WidgetFileBrowser::changed", "currentFile=" + currentFile + " currentPath="
					+ currentPath + " test=" + S3File.getFileSaveName(currentPath));
				}
				fileLabel.setText("File: " + currentFile);
				if (listenerFileChange != null){
					listenerFileChange.Action(currentPath);
				}
			}
		});

		list.addListener(new InputListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				super.touchDown(event, x, y, pointer, button);
				event.stop();
				return true;
			}
		});

		fileLabel.setAlignment(Align.left, Align.center);
		fileLabel.setWrap(false);

		chengeDirButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeListener.ChangeEvent event, Actor actor){
				S3Log.log("WidgetFileBrowser::changed", "event: " + event + " actor: " + actor);
				final mobi.shad.s3lib.gui.dialog.FileBrowser fB = new mobi.shad.s3lib.gui.dialog.FileBrowser();
				fB.setOkListener(new WidgetStringListener(){
					@Override
					public void Action(String text){
						currentFile = fB.getFile();
						currentDir = fB.getDirectory();
						currentPath = fB.getPatch();
						externalMode = fB.isExternalMode();

						actionDirectory(currentFile, currentDir, externalMode);

						S3Log.log("WidgetFileBrowser::changed", "currentFile=" + currentFile + " currentPath="
						+ currentPath + " test=" + S3File.getFileSaveName(currentPath));
						fileLabel.setText("File: " + currentFile);
						if (listenerFileChange != null){
							listenerFileChange.Action(currentPath);
						}

					}
				});

				fB.show(currentPath, currentDir);
			}
		});

		//
		// Utworzenie okna
		//
		Table window = GuiResource.table("fileBrowserTable");
		window.row();
		window.add(fileList).expandX().fillX().minHeight(150).maxHeight(250).left();
		window.row();
		window.add(chengeDirButton).fillX().expandX().left();
		window.row();
		window.add(fileLabel).fillX().expandX().left();
		window.pack();

		return window;
	}

	@Override
	public void hide(){
	}

	public void setListener(WidgetStringListener listener){
		listenerFileChange = listener;
	}

	public String getFile(){
		return currentPath;
	}

	public String getPatch(){
		return currentDir;
	}

	/**
	 * @param filePatch
	 * @param directory
	 * @param exMode
	 */
	private void actionDirectory(String fileName, String directory, boolean exMode){

		if (S3Constans.RESOURCE_DEBUG){
			S3Log.log("WidgetDialogFileBrowser", "-- Read Directory -----------------", 1);
			S3Log.log("WidgetDialogFileBrowser", "exMode: " + exMode, 1);
			S3Log.log("WidgetDialogFileBrowser", "fileName: " + fileName, 1);
			S3Log.log("WidgetDialogFileBrowser", "directory: " + directory, 1);
			S3Log.log("WidgetDialogFileBrowser", "------------------------------------", 1);
		}
		String[] files = S3File.getDirectoryList(directory, directory, exMode);

		list.setItems(files);
		list.invalidate();
		currentFile = fileName;
		fileLabel.setText("File: " + currentFile);
		list.getSelection().set(currentFile);
	}
}
