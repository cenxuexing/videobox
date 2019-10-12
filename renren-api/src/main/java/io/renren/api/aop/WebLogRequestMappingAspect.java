package io.renren.api.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * web 接口日志

* <p>Title: WebLogRequestMappingAspect</p>  

* <p>Description: </p>  

* @author 8902

* @date 2018年12月14日
 */
@Aspect
@Component
public class WebLogRequestMappingAspect {
	private static Logger log = LoggerFactory.getLogger(WebLogRequestMappingAspect.class);

	private final ObjectMapper mapper;

	@Autowired
	public WebLogRequestMappingAspect(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	@Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void webLog() {
	}

	@Before("webLog()")
	public void doBefore(JoinPoint joinPoint) {
		for (Object object : joinPoint.getArgs()) {
			if (object instanceof MultipartFile || object instanceof HttpServletRequest || object instanceof HttpServletResponse) {
				continue;
			}
			try {
				ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
				HttpServletRequest request = attributes.getRequest();
				log.info("\n\t"//
						+ "----------------------------------------------------------"//
						+ "\n\t" //
						+ "request path : {}"//
						+ "\n\t" //
						+ "request url : {}"//
						+ "\n\t" //
						+ "request parameter : {}"//
						+ "\n\t" //
						+ "----------------------------------------------------------", //
						joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName(), //
						request.getRequestURI().toString(), //
						mapper.writeValueAsString(object));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@AfterReturning(returning = "response", pointcut = "webLog()")
	public void doAfterReturning(Object response) throws Throwable {
		log.info("\n\t"//
				+ "----------------------------------------------------------"//
				+ "\n\t" //
				+ "response parameter : {}"//
				+ "\n\t" //
				+ "----------------------------------------------------------", mapper.writeValueAsString(response));
	}
}