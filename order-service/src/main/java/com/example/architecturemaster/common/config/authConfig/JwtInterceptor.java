
package com.example.architecturemaster.common.config.authConfig;

import cn.hutool.core.util.StrUtil;
import com.example.architecturemaster.VO.Exception.AuthException;
import com.example.architecturemaster.common.utils.TokenUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if ("OPTIONS".equals(request.getMethod())){
            String requestHeader = request.getHeader("Access-Control-Request-Headers");
            if (requestHeader != null){
                response.addHeader("Access-Control-Request-Headers",requestHeader);
            }
            else response.addHeader("Access-Control-Allow-Origin","*");
            return true;
        }
        String token = request.getHeader("token");
        System.out.println(request.getRequestURL());
        System.out.println(token);
        System.out.println(StrUtil.isBlank(token));
        if (StrUtil.isBlank(token)){
            token = request.getParameter("token");
        }
        if (handler instanceof HandlerMethod){
            if (((HandlerMethod)handler).getMethodAnnotation(AuthAccess.class) != null){
                return true;
            }
        }
//        执行认证
        if(StrUtil.isBlank(token)){
            throw new AuthException();
        }
        TokenUtils.verify(token,response);
        return true;
    }
}



