package com.board.controller;

import com.board.configuration.ResponseCode;
import com.board.entity.Auth;
import com.board.entity.Logout;
import com.board.entity.User;
import com.board.service.AuthService;
import com.board.service.AuthServiceImpl;
import com.board.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kyudong on 2017. 11. 17..
 */

@RestController
@RequestMapping(path = "/auth")
public class LoginController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AuthService authService;

    ResponseCode responseCode;

    @GetMapping(path = "/loginForm")
    public String loginzForm() {
        return "/user/login";
    }

    // ** 로그인 ** //
    @PostMapping(path = "/login")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody Auth auth, BindingResult bindingResult,
                        HttpServletRequest request ,HttpSession session, HttpServletResponse response) {
        Map<String, Object> res = new HashMap<>();


        if(bindingResult.hasErrors()) {
            res.put("code", "error");
        }

        if ( session.getAttribute("auth") != null ){
            log.info("auth session exist");
            // 기존에 login이란 세션 값이 존재한다면
            session.removeAttribute("auth"); // 기존값을 제거해 준다.

        }

        //String user_id = auth.getAuth_id();
        List<Map<String, Object>> user = authService.validateUser(auth);

        if(user.isEmpty()) {
            log.info("no user");
            res.put("code", "NO_AUTHORIZATION");
            return new ResponseEntity<Map<String, Object>>(res, HttpStatus.BAD_REQUEST);
            //"No User or Password!";
        } else {
            if(user.get(0).get("user_id").equals(auth.getAuth_id()) && user.get(0).get("user_password").equals(auth.getAuth_password())) {
                session.setAttribute("auth", user);
                log.info("yes user  " + session.getId());
                res.put("data", user);
                res.put("code", "LOGIN_SUCCESS");
                response.addHeader("authorization", user.get(0).get("user_seqNo").toString());
                response.addHeader("auth", "not auth");
                request.getSession().setAttribute("wow", res);
                return new ResponseEntity<Map<String, Object>>(res, HttpStatus.CREATED);
            } else {
                log.info("no use22r");
                res.put("code", "NO_AUTHORIZATION");
                return new ResponseEntity<Map<String, Object>>(res, HttpStatus.BAD_REQUEST);
            }
        }
    }

    // ** 로그아웃 ** //
    @RequestMapping(path = "/logout/{seqNo}")
    public ResponseEntity<Map<String, Object>> logout(@PathVariable("seqNo") String seqNo, HttpSession session) {
        Map<String, Object> res = new HashMap<>();
        session.invalidate();

        res.put("code", "LOGOUT_SUCCESS");
        log.info("no user " + res);
        return new ResponseEntity<Map<String, Object>>(res, HttpStatus.OK);
    }

    // ** 아이디 중복 체크 ** //

}
