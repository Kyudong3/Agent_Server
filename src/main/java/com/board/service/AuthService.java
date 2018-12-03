package com.board.service;

import com.board.entity.Auth;
import com.board.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Kyudong on 2017. 11. 17..
 */
public interface AuthService {

    public List<Map<String, Object>> validateUser(Auth auth);

    //public String validateUser2(Auth auth);

    //회원 로그인 체크
    public String loginCheck(Auth auth);
//    // 회원 로그인 체크
//    public boolean loginCheck(User user, HttpSession session);
//    // 회원 로그인 정보
//    public User viewUser(User user);
//    // 회원 로그아웃
//    public void logout(HttpSession session);
}
