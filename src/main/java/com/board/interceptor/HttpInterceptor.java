package com.board.interceptor;

import com.board.entity.Auth;
import com.board.entity.User;
import com.board.service.AuthService;
import com.board.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Kyudong on 2017. 11. 17..
 */

@Component
public class HttpInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    AuthService service;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Before process request");

        // 세션 객체 생성
        HttpSession session = request.getSession();

        Object obj = session.getAttribute("auth");

        try {
            if(request.getSession().getAttribute("wow") == null) {
                logger.info("no session");
                return true;
            } else {
                logger.info("auth auth hahaha" + "  " + request.getSession().getAttribute("auth"));
                return true;
            }
        }  catch (Exception e) {
            e.printStackTrace();
        }

//        if(session.getAttribute("auth") == null) {
//            // 컨트롤러를 실행(요청페이지로 이동한다)
//            logger.info("auth failed!!" + " " + session.getAttribute("auth") + " " + obj);
//            //response.sendRedirect(request.getContextPath() + "/login");
//            //컨트롤러를 실행하지 않는다 (요청페이지로 이동하지 않는다)
//            return true;
//
//        // null 이 아니면
//        } else {
//            logger.info("auth Success!");
//            //로그인 페이지로 이동 //
//            return true;
//        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //super.postHandle(request, response, handler, modelAndView);
        logger.info("Method executed");

        HttpSession session = request.getSession();
        if(session.getAttribute("auth")==null) {
            logger.info("no session");
        } else {
            logger.info("wowsession " + session.getAttribute("auth"));
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.info("Request Completed!");
    }
}
