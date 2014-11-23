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
package mobi.shad.s3lib.main;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;

/**
 * Class to log operation
 */
public class S3Log{

	static String ANSI_WHITE = "\u001b[37m";
	static String ANSI_RED = "\u001b[31m";
	static String ANSI_BLACK = "\u001b[30m";
	static String ANSI_BLUE = "\u001b[34m";
	static String ANSI_RESET = "\u001b[0m";
	static String ANSI_GREEN = "\u001b[32m";
	static String ANSI_YELLOW = "\u001b[33m";
	static String ANSI_CYAN = "\u001b[36m";
	static String ANSI_PURPLE = "\u001b[35m";
	private static StringBuilder log_output = new StringBuilder("");

	private S3Log(){
	}

	/**
	 * @param tag
	 * @param text
	 */
	public static void error(String tag, String text){
		try {
			if (log_output.length() > 1024){
				log_output = null;
				log_output = new StringBuilder();
			}
		} catch (NullPointerException ex){
			log_output = new StringBuilder();
		}
		log_output.append("[").append(tag).append("] ").append(text).append("\n");
		Gdx.app.error(tag, text);
	}

	/**
	 * @param className
	 * @param message
	 * @param thrown
	 */
	public static void error(String className, String message, Throwable thrown){
		error("[ Error in: " + className + "] ", message + " - " + thrown.getLocalizedMessage());
		error("[ Error in: " + className + "] ", getStackTrace(thrown));
	}

	/**
	 * @param tag
	 * @param text
	 */
	public static void logNumerLines(String tag, String text){
		try {
			if (log_output.length() > 1024){
				log_output = null;
				log_output = new StringBuilder();
			}
		} catch (NullPointerException ex){
			log_output = new StringBuilder();
		}
		StringBuilder tmpLog = new StringBuilder();
		tmpLog.append("[").append(tag).append("] ").append(" ------------------------------------ \n");
		String[] lines = text.split("\n");
		for (int i = 0; i < lines.length; i++){
			String line = lines[i];
			tmpLog.append("[").append(tag).append("][").append(i + 1).append("] ").append(line).append("\n");
		}
		tmpLog.append("[").append(tag).append("] ").append(" ------------------------------------ \n");
		log_output.append(tmpLog);
		Gdx.app.log(tag, tmpLog.toString());
	}

	/**
	 * @param tag
	 * @param text
	 */
	public static void log(String tag, String text){
		try {
			if (log_output.length() > 1024){
				log_output = null;
				log_output = new StringBuilder();
			}
		} catch (NullPointerException ex){
			log_output = new StringBuilder();
		}
		log_output.append("[").append(tag).append("] ").append(text).append("\n");
		if (Gdx.app != null){
			Gdx.app.log(tag, text);
		} else {
			System.out.println("[" + tag + "] " + text);
		}
	}

	/**
	 * @param tag
	 * @param text
	 * @param color
	 */
	public static void log(String tag, String text, int color){

		if (S3.osPlatform == ApplicationType.Android){
			log(tag, text);
		} else {
			if (S3.cfg.ansiLog){

				switch (color){
					case 1:
						log(tag, ANSI_RED + text + ANSI_RESET);
						break;
					case 2:
						log(tag, ANSI_GREEN + text + ANSI_RESET);
						break;
					case 3:
						log(tag, ANSI_BLUE + text + ANSI_RESET);
						break;
					case 4:
						log(tag, ANSI_YELLOW + text + ANSI_RESET);
						break;
					case 5:
						log(tag, ANSI_CYAN + text + ANSI_RESET);
						break;
					default:
						log(tag, ANSI_RESET + text);
						break;
				}
			} else {
				log(tag, text);
			}
		}
	}

	/**
	 * Najbardziej szczegółowy poziom logowania
	 *
	 * @param tag
	 * @param text
	 */
	public static void trace(String tag, String text){
		log(tag, text);
	}

	/**
	 * @param tag
	 * @param text
	 * @param color
	 */
	public static void trace(String tag, String text, int color){
		log(tag, text, color);
	}

	/**
	 * Poziom logowaniu uzywany do debugowania aplikacji
	 *
	 * @param tag
	 * @param text
	 */
	public static void debug(String tag, String text){
		log(tag, text);
	}

	/**
	 * @param tag
	 * @param text
	 * @param color
	 */
	public static void debug(String tag, String text, int color){
		log(tag, text, color);
	}

	/**
	 * Poziom logowaniu uzywany do normalnej pracy aplikacji
	 *
	 * @param tag
	 * @param text
	 */
	public static void info(String tag, String text){
		log(tag, text);
	}

	/**
	 * @param tag
	 * @param text
	 * @param color
	 */
	public static void info(String tag, String text, int color){
		log(tag, text, color);
	}

	/**
	 * Logowanie podejzanych zachowan systemu
	 *
	 * @param tag
	 * @param text
	 */
	public static void warn(String tag, String text){
		log(tag, text);
	}

	/**
	 * @param tag
	 * @param text
	 * @param color
	 */
	public static void warn(String tag, String text, int color){
		log(tag, text, color);
	}

	/**
	 * Logowanie podejzanych zachowan systemu
	 *
	 * @param tag
	 * @param text
	 */
	public static void event(String tag, String text){
		log(tag, text);
	}

	/**
	 * @param tag
	 * @param text
	 * @param color
	 */
	public static void event(String tag, String text, int color){
		log(tag, text, color);
	}

	/**
	 *
	 */
	public static void dispose(){
		log_output = new StringBuilder("");
		log_output = null;
	}

	/**
	 * @param thrown
	 * @return
	 */
	public static synchronized String getStackTrace(Throwable thrown){
		String out = "";
		out = thrown.toString();
		StackTraceElement[] trace = thrown.getStackTrace();
		for (int i = 0; i < trace.length; i++){
			out = out + ("\n at " + trace[i]);
		}
		return out;
	}
}
