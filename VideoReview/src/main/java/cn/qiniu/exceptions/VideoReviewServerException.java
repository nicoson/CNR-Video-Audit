package cn.qiniu.exceptions;

import org.apache.commons.httpclient.HttpStatus;

public class VideoReviewServerException extends RuntimeException{

	private static final long serialVersionUID = 2094953799290970785L;
	private int httpStatus;

	private int code;

	private String developerMessage;

	public VideoReviewServerException(int code, String message, Object... args) {
		super(String.format(message, args));
		this.code = code;
		this.httpStatus = HttpStatus.SC_INTERNAL_SERVER_ERROR;
	}

	public int getHttpStatus() {
		return httpStatus;
	}

	public int getCode() {
		return code;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

}
