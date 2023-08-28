package com.example.GitApi.interceptor;

import com.example.GitApi.exeption.HeaderNotSupportedException;
import com.example.GitApi.exeption.config.GlobalErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class Interceptor implements HandlerInterceptor {



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getHeader("Accept").equals("application/json")){
            response.addHeader("Interceptor", "Valid Accept header");
            return true;
        }
        if(request.getHeader("Accept").equals("application/xml")){
            response.addHeader("Interceptor", "Not Supported Accept header");
            throw  new HeaderNotSupportedException();
        }
        if(request.getHeader("Accept") != null){
            response.addHeader("Interceptor", "Not Accept header");
            throw  new HeaderNotSupportedException(
                    GlobalErrorCode.ERROR_HEADER_NOT_SUPPORTED,
                    "Accept header not provided");
        }
        return false;
    }

}
