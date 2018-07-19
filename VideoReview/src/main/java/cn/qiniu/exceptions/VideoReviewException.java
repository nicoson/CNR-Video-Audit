package cn.qiniu.exceptions;

import org.apache.commons.httpclient.HttpStatus;

import cn.qiniu.framework.exceptions.CjjException;

public class VideoReviewException extends CjjException {

	private static final long serialVersionUID = -9011522126511754228L;

	private int httpStatus;

	private int code;

	private String developerMessage;

	public VideoReviewException(int code, String message, Object... args) {
		super(HttpStatus.SC_BAD_REQUEST,code,String.format(message, args));
		this.code = code;
		this.httpStatus = httpStatus;
	}

	public VideoReviewException(int httpStatus,int code, String message, Object... args) {
		super(httpStatus,code,String.format(message, args));
		this.code = code;
		this.httpStatus = httpStatus;
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
