package com.ccvb.utils.log;

/**
 * contains data about a log call
 */
public class LogLine implements Cloneable
{
	/**
	 * log level of the log line
	 */
	public Log.LOG logLevel;
	
	/**
	 * Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 */
	public String tag;
	
	/**
	 * The logged message.
	 */
	public String message;
	
	/**
	 * @param type - <b>int</b>, one among Log.LOG.VERBOSE, Log.LOG.DEBUG, Log.LOG.INFO, Log.LOG.WARNING, Log.LOG.ERROR
	 * @param tag - <b>String</b>, used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
	 * @param message - <b>String</b>, the logged message.
	 */
	protected LogLine(Log.LOG type, String tag, String message)
	{
		this.logLevel = type;
		this.tag = tag;
		this.message = message;
	}
	
	/**
	 * Return a string formated in JSON representing this instance
	 * 
	 * @return String
	 */
	@Override
	public String toString()
	{
		return "{\"type\":\"" + this.logLevel.name().toLowerCase() + "\",\"tag\":\"" + this.tag + "\",\"msg\":\"" + this.message.replaceAll("\"", "'") + "\"}";
	}
}