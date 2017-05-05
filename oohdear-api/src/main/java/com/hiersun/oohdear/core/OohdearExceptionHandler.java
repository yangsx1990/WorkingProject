package com.hiersun.oohdear.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * ServiceException处理控制器
 * @author liuyang
 * @email y_liu@hiersun.com | 745089707@qq.com
 */
@ControllerAdvice
public class OohdearExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(OohdearExceptionHandler.class);

	/**
	 * 处理Exception
	 * @param e 异常
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseMessage> processException(Exception e) {
		ResponseMessage body = new ResponseMessage();
		//如果是ServiceException则将异常解析为可读的错误代码，否则转换为服务器异常，并打印日志
		if (e instanceof OohdearException) {
			OohdearException serviceException = (OohdearException) e;
			body.getHead().setCode(serviceException.getCode());
			body.getHead().setDescription(serviceException.getDescription());
			body.getHead().setMessage(serviceException.getMessage());
			logger.info("Ooh Dear Exception {}", e);
		} else {
			body.getHead().setCode(99999);
			body.getHead().setDescription("服务异常");
			body.getHead().setMessage("服务异常");
			logger.error("服务异常", e);
		}
		return new ResponseEntity<ResponseMessage>(body, HttpStatus.OK);
	}
}
