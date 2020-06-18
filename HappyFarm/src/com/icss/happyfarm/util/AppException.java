package com.icss.happyfarm.util;

public class AppException extends RuntimeException {

	public AppException() {
		
	}

	public AppException(String arg0) {
		super(arg0);
		
	}

	public AppException(Throwable arg0) {
		super(arg0);
		
	}

	public AppException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

}
