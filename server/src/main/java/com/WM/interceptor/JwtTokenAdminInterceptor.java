package com.WM.interceptor;

import com.WM.constant.JwtClaimsConstant;
import com.WM.properties.JwtProperties;
import com.WM.utils.JwtUtil;
import com.WM.utils.ThreadLocalUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    //检查JWT信息
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //检查是否为HandlerMethod里面的东西（过滤掉静态资源）
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        //从请求头中获取token（JWT信息）
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        //检查JWT信息
        try {
            log.info("JWT信息："+token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            //获取JWT里面的id字段
            Long employeeId = Long.valueOf(claims.get(JwtClaimsConstant.EMPLOYEE_ID).toString());
            ThreadLocalUtil.setCurrentId(employeeId);
            log.info("当前员工Id：{}", employeeId);
            //通过
            return true;
        } catch (Exception ex) {
            //不通过，设置状态码401
            response.setStatus(401);
            return false;
        }
    }
}
