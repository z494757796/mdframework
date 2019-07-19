package cn.mdsoftware.mdframework.common.exception;

import cn.mdsoftware.mdframework.bean.api.HttpResponse;
import cn.mdsoftware.mdframework.common.enums.HttpResponseEnum;
import cn.mdsoftware.mdframework.common.utils.R;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * 异常处理器
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月27日 下午10:16:19
 */
@RestControllerAdvice
public class BTExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 自定义异常
	 */
//	@ExceptionHandler(BTException.class)
//	public R handleRRException(RRException e) {
//		R r = new R();
//		r.put("code", e.getCode());
//		r.put("msg", e.getMessage());
//
//		return r;
//	}

	@ExceptionHandler(DuplicateKeyException.class)
	public R handleDuplicateKeyException(DuplicateKeyException e) {
		logger.error(e.getMessage(), e);
		return R.error("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public ModelAndView handleAuthorizationException(AuthorizationException e) {
		ModelAndView mView = new ModelAndView("403");
		return mView;
//		logger.error(e.getMessage(), e);
//		return R.error("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(Exception.class)
	public Object handleException(Exception e) {
		HttpResponse resp = new HttpResponse<>();
		resp.setHttpResponseEnum(HttpResponseEnum.SYSTEM_ERROR);
		return resp;
	}


	@ExceptionHandler(value = { ConstraintViolationException.class })
	public HttpResponse handleResourceNotFoundException(ConstraintViolationException e) {
		HttpResponse resp = new HttpResponse<>();
		resp.setHttpResponseEnum(HttpResponseEnum.PARAM_REQUIRED);
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		for (ConstraintViolation<?> violation : violations ) {
			resp.setMessage(violation.getMessage());
		}
		return resp;
	}

	@ExceptionHandler(value = { BindException.class })
	public HttpResponse handleBindException(BindException e) {
		HttpResponse resp = new HttpResponse<>();
		List<ObjectError> violations = e.getAllErrors();
		for (ObjectError violation : violations ) {
			resp.setMessage(violation.getDefaultMessage());
		}
		return resp;
	}

	@ExceptionHandler(value = { cn.mdsoftware.mdframework.common.exception.TokenNullException.class })
	public HttpResponse handleTokenException(cn.mdsoftware.mdframework.common.exception.TokenNullException e) {
		return HttpResponse.createByErrorResponseEnum(HttpResponseEnum.CODE_ACCESS_DENIED);
	}

	@ExceptionHandler(value = { MissingServletRequestParameterException.class })
	public HttpResponse missingServletRequestParameterException(MissingServletRequestParameterException e) {
		return HttpResponse.createByErrorResponseEnum(HttpResponseEnum.PARAM_REQUIRED);
	}

	/*@ExceptionHandler(value = { WxRuntimeException.class })
	public HttpResponse handlWxException(cn.mdsoftware.mdframework.common.exception.TokenNullException e) {
		HttpResponse resp = new HttpResponse<>();
		resp.setHttpResponseEnum(HttpResponseEnum.WECHAT_USER_NOT_EXIST);
		return resp;
	}*/
}
