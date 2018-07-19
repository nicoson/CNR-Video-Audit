package cn.qiniu.controller.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import cn.qiniu.exceptions.VideoManagerException;
import cn.qiniu.framework.springext.exceptionhandler.ErrorResponse;

@ControllerAdvice(annotations = { RestController.class})
public class VideoManagerControllerExceptionHandler {
	// 日志对象
	private Logger logger = LoggerFactory.getLogger(VideoManagerControllerExceptionHandler.class);

	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(VideoManagerException e) {
		ErrorResponse errorResponse = new ErrorResponse(e.getHttpStatus(), e.getCode(), e.getMessage(),
				e.getDeveloperMessage());
		
		logger.error("Server exception. " + JSON.toJSONString(errorResponse), e);
		
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.valueOf(e.getHttpStatus()));
	}

}
