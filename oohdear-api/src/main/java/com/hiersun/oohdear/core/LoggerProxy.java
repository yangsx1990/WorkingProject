/*package com.hiersun.oohdear.core;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

*//**
 * 日志记录代理<br>
 * @author liuyang
 * @email y_liu@hiersun.com | 745089707@qq.com
 *//*
@Aspect
@Component
public class LoggerProxy {
	private static final Logger logger = LoggerFactory.getLogger(LoggerProxy.class);

	*//**
	 * 切面是所有的Controller中的请求，也就是带有@RequestMapping注解的方法
	 * @param jp 切点
	 * @return
	 * @throws Throwable 做完日志继续向上抛出异常
	 *//*
	@Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public Object requestAndResponseLogger(ProceedingJoinPoint jp) throws Throwable {
		Object[] parameters = jp.getArgs();
		//time代表每一次请求与相应的唯一标识
		long time = System.currentTimeMillis();
		if (parameters.length > 0) {
			for (Object parameter : parameters) {
				if (parameter instanceof ServletRequest || parameter instanceof ServletResponse
						|| parameter instanceof HttpSession || parameter instanceof Model) {
					continue;
				}
				logger.debug("[{}={}] 请求={}", getMethod(jp.getSignature()), time, JSON.toJSON(parameter));
			}
		} else {
			logger.debug("[{}={}] 请求={}", getMethod(jp.getSignature()), time, "");
		}
		Object object;
		try {
			object = jp.proceed(parameters);
		} catch (Throwable e) {
			//如果抛出的异常是ServiceException，那么进行打印日志，并继续向上抛出
			if (e instanceof OohdearException) {
				OohdearException serviceException = (OohdearException) e;
				ResponseMessage body = new ResponseMessage();
				if (serviceException.getCode() != null) {
					body.getHead().setCode(serviceException.getCode());
					body.getHead().setMessage(serviceException.getMessage());
					logger.debug("[{}={}] 响应={}", getMethod(jp.getSignature()), time, JSON.toJSONString(body));
				}
			}
			throw e;
		}
		if (object instanceof ResponseEntity) {
			ResponseEntity<?> entity = (ResponseEntity<?>) object;
			if (entity.getBody() instanceof ResponseBody) {
				ResponseBody body = (ResponseBody) entity.getBody();
				logger.debug("[{}={}] 响应={}", getMethod(jp.getSignature()), time, JSON.toJSONString(body));
			} else {
				logger.debug("[{}={}] 响应={}", getMethod(jp.getSignature()), time, JSON.toJSONString(entity));
			}
		} else {
			logger.debug("[{}={}] 响应={}", getMethod(jp.getSignature()), time, JSON.toJSONString(object));
		}
		return object;
	}

	*//**
	 * 获取自定义格式的方法名
	 * @param signature 
	 * @return
	 *//*
	private String getMethod(Signature signature) {
		return signature.getDeclaringTypeName() + "." + signature.getName();
	}
}
*/