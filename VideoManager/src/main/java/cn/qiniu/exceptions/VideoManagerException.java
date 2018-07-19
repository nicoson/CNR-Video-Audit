package cn.qiniu.exceptions;

import org.apache.commons.httpclient.HttpStatus;

import cn.qiniu.framework.exceptions.CjjException;

public class VideoManagerException extends CjjException {

	private static final long serialVersionUID = 5157600963685038937L;

	private int httpStatus;

	private int code;

	private String developerMessage;

	public VideoManagerException(int code, String message, Object... args) {
		super(HttpStatus.SC_BAD_REQUEST,code,String.format(message, args));
		this.code = code;
		this.httpStatus = httpStatus;
	}

	public VideoManagerException(int httpStatus,int code, String message, Object... args) {
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
