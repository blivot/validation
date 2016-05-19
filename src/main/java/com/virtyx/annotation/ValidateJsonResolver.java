package com.virtyx.annotation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtyx.exception.BulkValidationException;
import com.virtyx.exception.ValidationError;
import com.virtyx.validation.Validation;

public class ValidateJsonResolver implements HandlerMethodArgumentResolver {

	final protected Logger log = LogManager.getLogger();

	protected List<HttpMessageConverter<?>> messageConverters;

	private final BeanFactory beanFactory;

	public ValidateJsonResolver(BeanFactory beanFactory){
		this.beanFactory = beanFactory;
	}

	@SuppressWarnings("unchecked")
	protected <T> Object readWithMessageConverters(HttpInputMessage inputMessage, MethodParameter parameter, Class<T> paramType) throws IOException, HttpMediaTypeNotSupportedException {

		MediaType contentType = MediaType.parseMediaType(MediaType.APPLICATION_JSON_VALUE); //annot.contentType()

		for (HttpMessageConverter<?> messageConverter : getMessageConverters()) {
			if (messageConverter.canRead(paramType, contentType)) {
				log.debug("Reading [{}] as \"{}\" using [{}]", paramType.getName(), contentType, messageConverter);
				return ((HttpMessageConverter<T>) messageConverter).read(paramType, inputMessage);
			}
		}

		throw new HttpMediaTypeNotSupportedException("500");
	}	

	/**
	 * Supports the following:
	 * <ul>
	 * 	<li>@RequestFormEntity-annotated method arguments. 
	 * 	<li>Arguments of type {@link BaseInput} 
	 * 		unless annotated with @{@link RequestPart} or @{@link RequestBody}.
	 * </ul>
	 */
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(ValidateJson.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, 
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		log.debug("This is the big apple");
		String paramName = getParamName(parameter); 
		log.debug("Param Name REsolve: {}", paramName);
		Object arg;

		try {
			arg = readWithMessageConverters(createInputMessage(webRequest, paramName), parameter, parameter.getParameterType());
			log.debug("CONVERTED and Created: {}", arg);
			log.debug("Created: {}", arg.getClass().toString());
			validate(
					arg,
					getValidationClass(parameter)
			);

			if (arg != null) {
				Annotation[] annotations = parameter.getParameterAnnotations();
				for (Annotation annot : annotations) {
					if (annot.annotationType().getSimpleName().startsWith("Valid")) {
						WebDataBinder binder = binderFactory.createBinder(webRequest, arg, paramName);
						Object hints = AnnotationUtils.getValue(annot);
						binder.validate(hints instanceof Object[] ? (Object[]) hints : new Object[] {hints});
						BindingResult bindingResult = binder.getBindingResult();
						if (bindingResult.hasErrors()) {
							throw new MethodArgumentNotValidException(parameter, bindingResult);
						}
					}
				}
			}
		}
		catch (MissingServletRequestParameterException ex) {
			// handled below
			log.debug("EXCEPTION");
			ex.printStackTrace();
			arg = null;
		}				

		RequestPart annot = parameter.getParameterAnnotation(RequestPart.class);
		boolean isRequired = (annot == null || annot.required());

		if (arg == null && isRequired) {
			throw new MissingServletRequestParameterException(paramName, parameter.getParameterType().getSimpleName());
		}

		return arg;
	}

	@SuppressWarnings({ "unchecked", "serial" })
	private void validate(Object arg, Class<? extends ValidateJsonDefault> clazz) throws Exception {

		ValidateJsonDefault validatorBuilder; 
		Constructor<?> ctor = clazz.getConstructor();
		validatorBuilder = (ValidateJsonDefault) ctor.newInstance();
		
		Validation validator = validatorBuilder.getValidation();
		List<ValidationError> errors = validator.validate(arg);
		
		if (errors.size() > 0) {
			throw new BulkValidationException(errors);
		}
	}

	private String getParamName(MethodParameter parameter) {

		ValidateJson annot = parameter.getParameterAnnotation(ValidateJson.class);
		String paramName = ""; //(annot != null) ? annot.value() : "";
		if (paramName.length() == 0) {
			paramName = parameter.getParameterName();
			log.debug("Param Name: {}", paramName);
			//			Assert.notNull(paramName, "Request parameter name for argument type [" + parameter.getParameterType().getName()
			//					+ "] not available, and parameter name information not found in class file either.");
		}
		return paramName;
	}

	private Class<? extends ValidateJsonDefault> getValidationClass(MethodParameter parameter) {
		ValidateJson annot = parameter.getParameterAnnotation(ValidateJson.class);
		return annot.value();
	}

	/**
	 * Creates a new {@link HttpInputMessage} from the given {@link NativeWebRequest}.
	 *
	 * @param webRequest the web request to create an input message from MockHttpInputMessage
	 * @param paramName the name of parameter
	 * @return fake input message
	 */
	protected HttpInputMessage createInputMessage(final NativeWebRequest webRequest, final String paramName) {
		//		HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		//		try {
		//			String body = IOUtils.toString(servletRequest.getInputStream());
		//			return body;
		//		} catch (IOException e) {
		//			throw new RuntimeException(e);
		//		}
		////		return null;
		//	}

		return new HttpInputMessage(){

			@Override
			public HttpHeaders getHeaders() {
				return null;
			}

			@Override
			public InputStream getBody() throws IOException {
				HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
				return servletRequest.getInputStream();
				//				return new ByteArrayInputStream(webRequest.get);
				//				return new ByteArrayInputStream(webRequest.getParameter(paramName).getBytes());
			}};
	}	



	private List<HttpMessageConverter<?>> getMessageConverters(){
		if(messageConverters == null){
			messageConverters = beanFactory.getBean(RequestMappingHandlerAdapter.class).getMessageConverters();
		}
		return messageConverters;
	}

}
