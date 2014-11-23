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
package mobi.shad.s3lib.main;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

import java.io.*;
import java.util.LinkedList;

/**
 * @author Jarek
 */
public class S3File{

	private S3File(){

	}

	/**
	 * @param directory
	 * @param defaultDir
	 * @return
	 */
	public static String[] getDirectoryList(String directory, String defaultDir){
		return getDirectoryList(directory, defaultDir, false, null);
	}

	public static String[] getDirectoryList(String directory, String defaultDir, boolean externalMode){
		return getDirectoryList(directory, defaultDir, externalMode, null);
	}

	/**
	 * Pobiera katalog dysku zwraca w postaci tablicy stringów
	 *
	 * @param directory - katalog do odczytania
	 * @return - tablica plikow danego katalogu
	 */
	public static String[] getDirectoryList(String directory, String defaultDir, boolean externalMode, final String[] extensionMask){

		FilenameFilter filter = new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name){

				if (name.startsWith(".")){
					return false;
				}
				if (name.equalsIgnoreCase("res.dat")){
					return false;
				}
				if (name.equalsIgnoreCase("_meta")){
					return false;
				}
				if (dir.isFile()){
					return false;
				}

				if (extensionMask != null){
					boolean flag = false;
					for (String ext : extensionMask){
						if (name.contains(ext)){
							flag = true;
						}
					}
					return flag;
				}
				return true;
			}
		};

		FilenameFilter filterDir = new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name){

				if (name.equalsIgnoreCase("..")){
					return true;
				}
				if (name.startsWith(".")){
					return false;
				}
				if (name.equalsIgnoreCase("res.dat")){
					return false;
				}
				if (name.equalsIgnoreCase("_meta")){
					return false;
				}
				if (dir.isFile()){
					return false;
				}
				if (extensionMask != null){
					if (!dir.isFile()){
						return true;
					}
					boolean flag = false;
					for (String ext : extensionMask){
						if (name.contains(ext)){
							flag = true;
						}
					}
					return flag;
				}
				return true;
			}
		};

		if (S3Constans.RESOURCE_DEBUG){
			S3Log.log("getDirectoryList::getDir", "currentDir: " + directory + " defaultDir: " + defaultDir
			+ " externalMode: " + externalMode, 5);
		}

		String[] metaDirInfo = null;
		String[] out = null;
		String[] children = null;
		String[] childrenInternal = null;
		FileHandle directoryHandle;
		File dir;

		if (externalMode){

			directoryHandle = Gdx.files.absolute(directory);
			if (S3Constans.RESOURCE_DEBUG){
				S3Log.log("getDirectoryList::getDir", "Read external DIR part 1: " + directoryHandle.toString(), 3);
			}

			String extension = directoryHandle.extension();
			if ( //
				//
				//
			extension.equalsIgnoreCase("png")
			|| extension.equalsIgnoreCase("jpg")
			|| extension.equalsIgnoreCase("gif")
			|| extension.equalsIgnoreCase("tif")
			|| extension.equalsIgnoreCase("bmp")
			|| extension.equalsIgnoreCase("jpeg")
			|| extension.equalsIgnoreCase("tga")
			|| //
			//
			//
			extension.equalsIgnoreCase("txt")
			|| extension.equalsIgnoreCase("xml")
			|| extension.equalsIgnoreCase("html")
			|| extension.equalsIgnoreCase("htm")
			|| extension.equalsIgnoreCase("rtf")
			|| extension.equalsIgnoreCase("pdf")
			|| extension.equalsIgnoreCase("epub")
			|| //
			//
			//
			extension.equalsIgnoreCase("avi")
			|| extension.equalsIgnoreCase("mpg")
			|| extension.equalsIgnoreCase("wmf")
			|| //
			//
			//
			extension.equalsIgnoreCase("mp3")
			|| extension.equalsIgnoreCase("ogg")
			|| //
			//
			//
			extension.equalsIgnoreCase("exe") || extension.equalsIgnoreCase("com")
			|| extension.equalsIgnoreCase("zip") || extension.equalsIgnoreCase("rar")
			|| extension.equalsIgnoreCase("bz2")){
				directory = directoryHandle.parent().path();
				directoryHandle = getFileHandle(directory, false, false);
			}

			if (S3Constans.RESOURCE_DEBUG){
				S3Log.log("getDirectoryList::getDir", "Read external DIR part 2: " + directoryHandle.toString(), 3);
			}

			dir = directoryHandle.file();
			children = dir.list(filterDir);

			FileHandle fh;
			if (children != null){
				for (int i = 0; i < children.length; i++){
					String string = children[i];
					fh = Gdx.files.absolute(string);
					if (fh.isDirectory()){
						children[i] = "<Dir> " + string;
					}
				}
			}

		} else {
			//
			// Odczyt plików internal
			//
			if (!defaultDir.equalsIgnoreCase("")){

				if (S3Constans.RESOURCE_DEBUG){
					S3Log.log("getDirectoryList::getDir", "= Read META File ========================", 2);
				}

				String metaDir = defaultDir;
				FileHandle metaFileHandle;
				metaFileHandle = getFileHandle(metaDir, true, false);

				if (!metaFileHandle.extension().equalsIgnoreCase("")){
					metaDir = metaFileHandle.parent().path() + "/_meta";
				} else if (metaFileHandle.isDirectory()){
					metaDir = metaDir + "/_meta";
				} else {
					if (metaDir.startsWith("data/")){
						metaDir = metaDir + "/_meta";
					} else {
						metaDir = "data/" + metaDir + "/_meta";
					}
				}
				if (S3Constans.RESOURCE_DEBUG){
					S3Log.log("getDirectoryList::getDir", "Prepare to read _meta dir info >>>>>>>>>>>>>> "
					+ metaDir.toString(), 3);
				}

				//
				// Odczyt pliku _meta dla wewnętrznego katalogu
				//
				metaFileHandle = getFileHandle(metaDir, true, false);
				if (metaFileHandle.exists()){
					if (S3Constans.RESOURCE_DEBUG){
						S3Log.log("getDirectoryList::getDir", "Read _meta dir info >>>>>>>>>>>  "
						+ metaFileHandle.path() + " ext: " + metaFileHandle.extension(), 3);
					}
					try {
						String readString = metaFileHandle.readString();
						if (readString != null){
							if (readString.length() > 10){

								String[] lines = readString.split("\n");
								LinkedList<String> list = new LinkedList<String>();
								for (int i = 0; i < lines.length; i++){
									String line = lines[i].trim();
									if (!line.equalsIgnoreCase("_meta") && !line.equalsIgnoreCase(".")
									&& !line.equalsIgnoreCase("..") && line.length() > 1){
										list.add(line);
									}
								}
								metaDirInfo = list.toArray(new String[list.size()]);
							}
						}

					} catch (Exception ex){
						S3Log.error(S3.class.getName(), "Error Read _meta data ...", ex);
					}
				}
			}

			//
			// Odczyt fizycznego katalogu (zewnętrznego)
			//

			if (S3Constans.RESOURCE_DEBUG){
				S3Log.log("getDirectoryList::getDir", "= Read external DIR ========================", 2);
			}

			directoryHandle = getFileHandle(directory, false, false, false);
			if (!directoryHandle.extension().equalsIgnoreCase("")){
				directory = directoryHandle.parent().path();
				directoryHandle = getFileHandle(directory, false, false);
			}
			dir = directoryHandle.file();

			if (S3Constans.RESOURCE_DEBUG){
				S3Log.log("getDirectoryList::getDir", "Prepare to read external dir info: "
				+ directoryHandle.toString() + " file: " + dir.getAbsolutePath(), 3);
			}

			children = dir.list(filter);

			//
			// Odczyt fizycznego katalogu (wewnętrznego)
			//
			if (S3Constans.RESOURCE_DEBUG){
				S3Log.log("getDirectoryList::getDir", "= Read internal DIR ========================", 2);
			}
			directoryHandle = getFileHandle(directory, true, true, false);
			if (!directoryHandle.extension().equalsIgnoreCase("")){
				directory = directoryHandle.parent().path();
				directoryHandle = getFileHandle(directory, false, false);
			}
			dir = directoryHandle.file();

			if (S3Constans.RESOURCE_DEBUG){
				S3Log.log("getDirectoryList::getDir", "Prepare to read internal dir info >>>>>>>>>>>>>> "
				+ directoryHandle.toString() + " dir: " + dir.getAbsolutePath(), 3);
			}
			childrenInternal = dir.list(filter);
		}

		if (S3Constans.RESOURCE_DEBUG){
			S3Log.log("getDirectoryList::getDir", "Result meta: " + metaDirInfo, 3);
			S3Log.log("getDirectoryList::getDir", "Result children: " + children, 3);
			S3Log.log("getDirectoryList::getDir", "Result childrenInternal: " + childrenInternal, 3);
		}
		//
		// Łączenie wyników
		//
		Array<String> merageArray = new Array();
		if (metaDirInfo != null){
			merageArray.addAll(metaDirInfo);
		}
		if (children != null){
			for (int i = 0; i < children.length; i++){
				if (!merageArray.contains(children[i], false)){
					merageArray.add(children[i]);
				}
			}
		}
		if (childrenInternal != null){
			for (int i = 0; i < childrenInternal.length; i++){
				if (!merageArray.contains(childrenInternal[i], false)){
					merageArray.add(childrenInternal[i]);
				}
			}
		}

		merageArray.sort();
		out = new String[merageArray.size];
		for (int i = 0; i < merageArray.size; i++){
			if (!merageArray.get(i).equalsIgnoreCase("_meta")){
				out[i] = merageArray.get(i);
				// if (S3Constans.NOTICE){
				S3Log.log("getDirectoryList::getDirectoryList", "Add file: " + out[i]);
				// }
			}
		}

		if (out == null){
			return new String[]{};
		}
		return out;
	}

	/**
	 * @param name
	 * @return
	 */
	public static FileHandle getFileHandle(String name){
		return getFileHandle(name, true, false, false);
	}

	/**
	 * @param name
	 * @param useInternal
	 * @param writeMode
	 * @return
	 */
	public static FileHandle getFileHandle(String name, boolean useInternal, boolean writeMode){
		return getFileHandle(name, useInternal, false, writeMode);
	}

	/**
	 * Przeszukuje katalogi danych w poszukiwaniu pliku o podanej nazwie
	 *
	 * @param name
	 * @param useInternal
	 * @return
	 */
	public static FileHandle getFileHandle(String name, boolean useInternal, boolean olnyInternal, boolean writeMode){

		if (name.equals("")){
			return null;
		}

		if (S3Constans.RESOURCE_DEBUG){
			S3Log.log("OS::getFileHandle", "Load file: `" + name + "` useInternal: " + useInternal + " writeMode: "
			+ writeMode, 1);
		}

		FileHandle fileHeader = Gdx.files.absolute(name);
		if (fileHeader != null && fileHeader.exists()){
			if (S3Constans.RESOURCE_DEBUG){
				S3Log.log("OS::getFileHandle", "File extis in absolute path: " + fileHeader.path(), 1);
			}
			return fileHeader;
		}

		if (olnyInternal == false){
			fileHeader = Gdx.files.absolute(Gdx.files.getExternalStoragePath() + S3Constans.appDirectory + "/" + name);
			if (fileHeader.exists()){
				if (S3Constans.RESOURCE_DEBUG){
					S3Log.log("OS::getFileHandle", "File extis in external storage path: " + fileHeader.path(), 1);
				}
				return fileHeader;
			}
		}

		//
		// Internal files
		//
		if (useInternal && writeMode == false){

			//
			// Asert/Data -> for PC
			//
			if (Gdx.app.getType() != ApplicationType.Android){
				if (name.startsWith("/assets/data/")){
					if (S3Constans.RESOURCE_DEBUG){
						S3Log.log("OS::getFileHandle", "Set to assets data path: " + name);
					}
					fileHeader = Gdx.files.internal(S3.currentDir + name);
				} else {
					if (S3Constans.RESOURCE_DEBUG){
						S3Log.log("OS::getFileHandle", "Set to assets data path: " + "data/" + name);
					}
					fileHeader = Gdx.files.internal(S3.currentDir + "/assets/data/" + name);
				}

				if (S3Constans.RESOURCE_DEBUG){
					S3Log.log("OS::getFileHandle", "Asert file name: " + fileHeader.path(), 1);
				}
				if (fileHeader.exists()){
					if (S3Constans.RESOURCE_DEBUG){
						S3Log.log("OS::getFileHandle", "File extis in " + fileHeader.path());
					}
					return fileHeader;
				}
			}

			//
			// Data -> for Android
			//
			if (Gdx.app.getType() == ApplicationType.Android){
				if (name.startsWith("data/")){
					if (S3Constans.RESOURCE_DEBUG){
						S3Log.log("OS::getFileHandle", "Set to data path: " + name);
					}
					fileHeader = Gdx.files.internal(name);
				} else {
					if (S3Constans.RESOURCE_DEBUG){
						S3Log.log("OS::getFileHandle", "Set to data path: " + "data/" + name);
					}
					fileHeader = Gdx.files.internal("data/" + name);
				}

				if (fileHeader.exists()){
					if (S3Constans.RESOURCE_DEBUG){
						S3Log.log("OS::getFileHandle", "File extis in " + fileHeader.path());
					}
					return fileHeader;
				}
			}

			if (name.startsWith("/assets/data/")){
				if (S3Constans.RESOURCE_DEBUG){
					S3Log.log("OS::getFileHandle", "Set to assets data path: " + name);
				}
				fileHeader = Gdx.files.internal(S3.currentDir + name);
			} else {
				if (S3Constans.RESOURCE_DEBUG){
					S3Log.log("OS::getFileHandle", "Set to assets data path: " + "data/" + name);
				}
				fileHeader = Gdx.files.internal(S3.currentDir + "/assets/data/" + name);
			}

			if (S3Constans.RESOURCE_DEBUG){
				S3Log.log("OS::getFileHandle", "Asert file name: " + fileHeader.path(), 1);
			}
			if (fileHeader.exists()){
				if (S3Constans.RESOURCE_DEBUG){
					S3Log.log("OS::getFileHandle", "File extis in " + fileHeader.path());
				}
				return fileHeader;
			}

		}

		//
		// //
		// // External files
		// //
		// if (S3Constans.RESOURCE_DEBUG){
		// S3Log.log("OS::getFileHandle", "File name: " + name, 3);
		// S3Log.log("OS::getFileHandle", "File name: " +
		// name.replace(Gdx.files.internal(name).parent().parent().path(), ""),
		// 3);
		// S3Log.log("OS::getFileHandle", "getExternalStoragePath: " +
		// Gdx.files.getExternalStoragePath(), 3);
		// S3Log.log("OS::getFileHandle", "getLocalStoragePath: " +
		// Gdx.files.getLocalStoragePath(), 3);
		// S3Log.log("OS::getFileHandle", "appDirectory: " +
		// S3Constans.appDirectory, 3);
		// S3Log.log("OS::getFileHandle", "internal 3: " +
		// Gdx.files.internal(name).parent().parent().path(), 3);
		// S3Log.log("OS::getFileHandle", "external 3: " +
		// Gdx.files.external(name).parent().parent().path(), 3);
		// S3Log.log("OS::getFileHandle", "absolute 3: " +
		// Gdx.files.absolute(name).parent().parent().path(), 3);
		// }
		//
		// if (writeMode == true){
		// fileHeader=Gdx.files.absolute(Gdx.files.getExternalStoragePath() +
		// S3Constans.appDirectory);
		// if (!fileHeader.exists()){
		// if (S3Constans.RESOURCE_DEBUG){
		// S3Log.log("OS::getFileHandle", "Create absolute storage data: " +
		// fileHeader.path());
		// }
		// fileHeader.mkdirs();
		// if (fileHeader.isDirectory()){
		// if (S3Constans.RESOURCE_DEBUG){
		// S3Log.log("OS::getFileHandle", "Create absolute dir OK !!!");
		// }
		// } else {
		// if (S3Constans.RESOURCE_DEBUG){
		// S3Log.log("OS::getFileHandle", "Create external dir Error !!!");
		// }
		// }
		// }
		// }
		//
		// fileHeader=Gdx.files.absolute(name);
		// if (fileHeader.exists()){
		// if (S3Constans.RESOURCE_DEBUG){
		// S3Log.log("OS::getFileHandle", "Create absolute storage data: " +
		// fileHeader.path());
		// }
		// return fileHeader;
		// }
		//
		// if (name.startsWith(Gdx.files.getExternalStoragePath())){
		// if (S3Constans.RESOURCE_DEBUG){
		// S3Log.log("OS::getFileHandle", "Set 1 ..." + name);
		// }
		// fileHeader=Gdx.files.absolute(name);
		// } else if
		// (name.startsWith(Gdx.files.internal(name).parent().parent().path())){
		// name=name.replace(Gdx.files.internal(name).parent().parent().path(),
		// "");
		// if (S3Constans.RESOURCE_DEBUG){
		// S3Log.log("OS::getFileHandle", "Set 2 ..." + name);
		// }
		// fileHeader=Gdx.files.absolute(Gdx.files.getExternalStoragePath() +
		// S3Constans.appDirectory + "/" + name);
		// } else {
		// if (S3Constans.RESOURCE_DEBUG){
		// S3Log.log("OS::getFileHandle", "Set 3 ..." + name);
		// }
		// fileHeader=Gdx.files.absolute(Gdx.files.getExternalStoragePath() +
		// S3Constans.appDirectory + "/" + name);
		// }
		//
		// if (S3Constans.RESOURCE_DEBUG){
		// S3Log.log("OS::getFileHandle", "Set to external storage data", 3);
		// S3Log.log("OS::getFileHandle", "External File: " + fileHeader.path(),
		// 3);
		// S3Log.log("OS::getFileHandle", "External File: " +
		// fileHeader.toString(), 3);
		// }
		return fileHeader;
	}

	/**
	 * @param fileHandle
	 * @return
	 */
	public static String getFileSaveName(FileHandle fileHandle){
		return getFileSaveName(fileHandle.toString());
	}

	/**
	 * @param filePath
	 * @return
	 */
	public static String getFileSaveName(String filePath){

		if (filePath == null){
			return null;
		}

		filePath = filePath.replaceAll("\\\\", "/");

		if (S3Constans.RESOURCE_DEBUG){
			S3Log.log("getFileSaveName", ">>>>>>>>>>>>>> In filePath=" + filePath);
		}
		String[] arr = filePath.split("/");
		String out = filePath;
		if (arr.length > 1){
			out = arr[arr.length - 2] + "/" + arr[arr.length - 1];
		}
		if (S3Constans.RESOURCE_DEBUG){
			S3Log.log("getFileSaveName", ">>>>>>>>>>>>>> Out filePath=" + out);
		}
		return out;
	}

	/**
	 * @param name
	 * @param TextureSize
	 * @param scaleMode   0 - FullScreen
	 * @return
	 */
	public static Texture getFileTexture(String name, int TextureSize, int scaleMode){
		Texture texture = new Texture(TextureSize, TextureSize, Pixmap.Format.RGBA8888);
		Pixmap px = new Pixmap(TextureSize, TextureSize, Pixmap.Format.RGBA8888);
		px.setColor(Color.RED);
		px.fill();
		try {
			px = new Pixmap(S3File.getFileHandle(name));
		} catch (Exception ex){
			S3Log.error("S3File::getFileTexture", "Error open file texture: " + name, ex);
		}
		Pixmap px2 = new Pixmap(TextureSize, TextureSize, Pixmap.Format.RGBA8888);
		px2.drawPixmap(px, 0, 0, px.getWidth(), px.getHeight(), 0, 0, TextureSize, TextureSize);
		texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);
		texture.draw(px2, 0, 0);
		return texture;
	}

	/**
	 * @param name
	 * @param TextureSize
	 * @return
	 */
	public static Texture getFileTextureCenter(String name, int TextureSize){
		Texture texture = new Texture(TextureSize, TextureSize, Pixmap.Format.RGBA8888);
		Pixmap px = new Pixmap(S3File.getFileHandle(name));
		texture.draw(px, (TextureSize / 2) - (px.getWidth() / 2), (TextureSize / 2) - (px.getHeight() / 2));
		return texture;
	}

	/**
	 * @param o
	 * @return
	 */
	public static String serializeObjectToString(Object o){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ObjectOutput out = new ObjectOutputStream(bos);
			out.writeObject(o);
			out.close();

			return bos.toString();
		} catch (IOException ioe){
			S3Log.error("S3File::serializeObjectToString:", "IOException", ioe);
			return null;
		}
	}

	/**
	 * @param o
	 * @return
	 */
	public static byte[] serializeObject(Object o){
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ObjectOutput out = new ObjectOutputStream(bos);
			out.writeObject(o);
			out.close();

			byte[] buf = bos.toByteArray();
			return buf;
		} catch (IOException ioe){
			S3Log.log("serializeObject", "error" + ioe.toString());
			return null;
		}
	}

	public static Object deserializeObject(byte[] b){
		try {
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(b));
			Object object = in.readObject();
			in.close();
			return object;
		} catch (ClassNotFoundException ex){
			S3Log.error("S3File::deserializeObject:", "ClassNotFoundException", ex);
			return null;
		} catch (IOException ex){
			S3Log.error("S3File::deserializeObject:", "IOException", ex);
			return null;
		}
	}
}
