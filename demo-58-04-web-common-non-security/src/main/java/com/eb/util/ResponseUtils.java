package com.eb.util;

import com.eb.mvc.error.BaseErrorAttributes;
import com.eb.mvc.vo.ResponseResult;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * @author suyh
 * @since 2024-08-31
 */
public final class ResponseUtils {
    public static void doResponse(
            HttpServletResponse response, int status, int code,
            MessageSource messageSource, Locale locale, Object... args) throws IOException {

        if (locale == null) {
            locale = Locale.ENGLISH;
        }

        String message = messageSource.getMessage(BaseErrorAttributes.MESSAGE_PREFIX + "." + code, args, "UNKNOWN ERROR", locale);

        ResponseResult<Object> result = ResponseResult.ofFail(code, message);
        String resultJson = JsonUtils.serializable(result);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(status);
        response.getWriter().println(resultJson);
    }
}
