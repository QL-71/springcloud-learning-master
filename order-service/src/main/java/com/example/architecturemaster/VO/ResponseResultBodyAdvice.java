package com.example.architecturemaster.VO;


import cn.hutool.json.JSONUtil;
import com.example.architecturemaster.VO.Exception.AppException;
import com.example.architecturemaster.VO.Exception.AuthException;
import com.example.architecturemaster.VO.Exception.MessageException;
import com.example.architecturemaster.VO.Exception.PayException;
import org.springframework.context.annotation.Profile;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Profile("!swagger")
@RestControllerAdvice
public class ResponseResultBodyAdvice implements ResponseBodyAdvice<Object> {

    @ExceptionHandler({AppException.class})
    public ResponseVO<?> handleBusinessException(AppException ex) {
        return ResponseVO.failure();
    }

    @ExceptionHandler({MessageException.class})
    public ResponseVO<?> MessageExceptionHandler(MessageException e){return ResponseVO.failure(ResponseStatusEnum.Message_ERROR,null);}

    @ExceptionHandler({PayException.class})
    public ResponseVO<?> PayExceptionHandler(PayException e){return ResponseVO.failure(ResponseStatusEnum.Pay_ERROR,"支付错误信息");}

    @ExceptionHandler({AuthException.class})
    public ResponseVO<?> AuthExceptionHandler(AuthException e){return ResponseVO.failure(ResponseStatusEnum.Auth_ERROR,"");}


    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.hasMethodAnnotation(IgnoreResponseHandler.class) &&
                !returnType.getContainingClass().getPackage().getName().startsWith("org.springdoc");
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {

        ResponseVO<?> respVo;
        if (body instanceof ResponseVO) {
            respVo = (ResponseVO<?>) body;
        }
        else {
            respVo = ResponseVO.success(body);
        }
        if (body instanceof String) {
            return JSONUtil.toJsonStr(respVo);
        }
        return respVo;
    }

}


