package cn.qiniu.entity.exceptions;

import org.apache.http.HttpStatus;

public class VideoReviewException extends RuntimeException {

	private static final long serialVersionUID = -9011522126511754228L;

	private int httpStatus;

	private int code;

	private String developerMessage;

	public VideoReviewException(int code, String message, Object... args) {
		super(String.format(message, args));
		this.code = code;
		this.httpStatus = HttpStatus.SC_BAD_REQUEST;
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
