package com.eb.mvc.response;

import com.eb.mvc.response.annotation.WrapperResponseAdvice;
import com.eb.mvc.vo.ResponseResult;
import com.eb.util.JsonUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 这个是处理统一的返回值的，将所有的返回值都封装到一个公共的模板({@link ResponseResult})中，
 * 这样在Controller 的接口中可以直接返回实际的返回值对象，在不需要有返回值的情况也可以直接添加void 作为返回值。
 */
@ControllerAdvice
public class BaseResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(@NonNull MethodParameter returnType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        Class<?> containingClass = returnType.getContainingClass();
        if (!containingClass.getPackage().getName().startsWith("com.eb.business.controller")) {
            return false;
        }

        if (ResponseEntity.class.isAssignableFrom(returnType.getParameterType())) {
            return false;
        }

        if (ResponseResult.class.isAssignableFrom(returnType.getParameterType())) {
            return false;
        }

        WrapperResponseAdvice wrapperResponseAdvice = returnType.getMethodAnnotation(WrapperResponseAdvice.class);
        if (wrapperResponseAdvice != null && !wrapperResponseAdvice.enabled()) {
            return false;
        }

        return true;
    }

    @Override
    public Object beforeBodyWrite(
            Object body,
            @NonNull MethodParameter returnType,
            @NonNull MediaType selectedContentType,
            @NonNull Class<? extends HttpMessageConverter<?>> selectedConverterType,
            @NonNull ServerHttpRequest request,
            @NonNull ServerHttpResponse response) {

        if (String.class.isAssignableFrom(returnType.getParameterType())) {
            ResponseResult<Object> result = ResponseResult.ofSuccess(body);
            return JsonUtils.serializable(result);
        }

        return ResponseResult.ofSuccess(body);
    }
}
