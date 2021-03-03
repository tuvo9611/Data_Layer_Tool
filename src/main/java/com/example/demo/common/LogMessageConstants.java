package com.example.demo.common;

public class LogMessageConstants {

	private LogMessageConstants() {
		// empty constructor
	}

	public static final String LOG_001 = "The program has started. Controller: {}.{} [{} ms]";
	public static final String LOG_002 = "The program has ended. Controller: {}.{} [{} ms]";
	public static final String LOG_003 = "The program has error. Controller: {} - error {}";
	public static final String LOG_004 = "The program has started. Service: {}.{} [{} ms]";
	public static final String LOG_005 = "The program has ended. Service: {}.{} [{} ms]";
	public static final String LOG_006 = "The program has error. Service: {} - error {}";
	public static final String LOG_007 = "The program has started. Business logic: {}.{} [{} ms]";
	public static final String LOG_008 = "The program has ended. Business logic: {}.{} [{} ms]";
	public static final String LOG_009 = "The program has error. Business logic: {} - error {}";
}