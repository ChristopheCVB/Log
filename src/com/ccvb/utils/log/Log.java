package com.ccvb.utils.log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Log
{
	/**
	 * TAG Prefix
	 */
	private final static String LOG_PREFIX = "CCVB_";
	
	/**
	 * Log level
	 */
	private static LOG LOG_LEVEL = Log.LOG.VERBOSE;
	
	private final static LOG DEFAULT_UNSPECIFIED_LEVEL = Log.LOG.ERROR;
	
	private static final String METHOD_SEPARATOR = " -> ";
	
	public static enum LOG
	{				// Ordinal
		NO_LOG,		// 0
		ERROR,		// 1
		WARNING,	// 2
		INFO,		// 3
		DEBUG,		// 4
		VERBOSE		// 5
	};
	
	private static ArrayList<LogLine> LOG_LINES = new ArrayList<LogLine>();
	
	/**
	 * Set the log level
	 * 
	 * @param level - Log.LOG
	 */
	public static void setLogLevel(Log.LOG level)
	{
		Log.LOG_LEVEL = level;
	}
	
	/**
	 * @return String - a JSON formated string representing all Log lines
	 */
	public static String toJSONString()
	{
		synchronized (Log.LOG_LINES)
		{
			return Log.LOG_LINES.toString();
		}
	}
	
	/**
	 * @return a clone of the current ArrayList<LogLine>
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<LogLine> getLines()
	{
		synchronized (Log.LOG_LINES)
		{
			return (ArrayList<LogLine>) Log.LOG_LINES.clone();
		}
	}
	
	/**
	 * Write a message to LogCat, with the specified level
	 * 
	 * @param level - int, Log.LOG.ERROR, Log.LOG.DEBUG, Log.LOG.INFO, Log.LOG.WARNING or Log.LOG.VERBOSE
	 * @param tag - String, tag to write to LogCat
	 * @param message - String, message to write to LogCat
	 */
	public static void log(Log.LOG level, String tag, String message)
	{
		synchronized (Log.LOG_LINES)
		{
			if (Log.LOG_LEVEL.compareTo(level) >= 0) // Same or greater
			{
				tag = Log.LOG_PREFIX + tag;
				switch (level)
				{
					case ERROR:
						android.util.Log.e(tag, message);
						break;
					
					case DEBUG:
						android.util.Log.d(tag, message);
						break;
					
					default:
					case INFO:
						android.util.Log.i(tag, message);
						break;
					
					case WARNING:
						android.util.Log.w(tag, message);
						break;
					
					case VERBOSE:
						android.util.Log.v(tag, message);
						break;
				}
			}
			Log.LOG_LINES.add(new LogLine(level, new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + " " + tag, message));
		}
	}
	
	/**
	 * Write a message to LogCat, with the specified level. The tag will be the className of the caller.
	 * 
	 * @param level - int, Log.LOG.ERROR, Log.LOG.DEBUG, Log.LOG.INFO, Log.LOG.WARNING or Log.LOG.VERBOSE
	 * @param number - int, number to write to LogCat
	 */
	public static void log(Log.LOG level, int number)
	{
		StackTraceElement caller = Log.getCaller();
		String fullClassName = caller.getClassName();
		Log.log(level, fullClassName.substring(fullClassName.lastIndexOf(".") + 1), new StringBuilder().append(caller.getMethodName()).append(Log.METHOD_SEPARATOR).append(number).toString());
	}
	
	/**
	 * Write a message to LogCat, with the spicified level. The tag will be the className of the caller.
	 * 
	 * @param level - int, Log.LOG.ERROR, Log.LOG.DEBUG, Log.LOG.INFO, Log.LOG.WARNING or Log.LOG.VERBOSE
	 * @param object - Object, message to write to LogCat
	 */
	public static void log(Log.LOG level, Object object)
	{
		StackTraceElement caller = Log.getCaller();
		String fullClassName = caller.getClassName();
		Log.log(level, fullClassName.substring(fullClassName.lastIndexOf(".") + 1), new StringBuilder().append(caller.getMethodName()).append(Log.METHOD_SEPARATOR).append(String.valueOf(object)).toString());
	}
	
	/**
	 * Write a message to LogCat. The level will be Log.DEFAULT_TAG and the tag will be the className of the caller.
	 * 
	 * @param message - Object, message to write to LogCat
	 */
	public static void log(Object object)
	{
		StackTraceElement caller = Log.getCaller();
		String fullClassName = caller.getClassName();
		Log.log(Log.DEFAULT_UNSPECIFIED_LEVEL, fullClassName.substring(fullClassName.lastIndexOf(".") + 1), new StringBuilder().append(caller.getMethodName()).append(Log.METHOD_SEPARATOR).append(String.valueOf(object)).toString());
	}
	
	/**
	 * Get the StackTraceElement caller
	 * 
	 * @return StackTraceElement caller
	 */
	private static StackTraceElement getCaller()
	{
		StackTraceElement caller = new StackTraceElement("Class", "method", "file", -1);
		
		try
		{
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			caller = stackTraceElements[4]; // Caller of caller
		}
		catch (Exception e)
		{}
		
		return caller;
	}
	
	/**
	 * Write a message to LogCat. The level will be Log.DEFAULT_TAG and the tag will be the className of the caller.
	 * 
	 * @param number - int, number to write to LogCat
	 */
	public static void log(int number)
	{
		StackTraceElement caller = Log.getCaller();
		String fullClassName = caller.getClassName();
		Log.log(Log.DEFAULT_UNSPECIFIED_LEVEL, fullClassName.substring(fullClassName.lastIndexOf(".") + 1), new StringBuilder().append(caller.getMethodName()).append(Log.METHOD_SEPARATOR).append(number).toString());
	}
}