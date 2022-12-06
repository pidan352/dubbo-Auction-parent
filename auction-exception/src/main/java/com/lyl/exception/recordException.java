package com.lyl.exception;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2022/10/27
 */

public class recordException extends Exception {
	public recordException() {
	}

	public recordException(String message) {
		super(message);
	}

	public recordException(String message, Throwable cause) {
		super(message, cause);
	}

	public recordException(Throwable cause) {
		super(cause);
	}

	public recordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
