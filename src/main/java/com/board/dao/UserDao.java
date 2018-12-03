package com.board.dao;

import com.board.entity.Auth;
import com.board.entity.User;

import javax.servlet.http.HttpSession;

/**
 * Created by Kyudong on 2017. 11. 17..
 */

public interface UserDao {
    //회원 로그인 체크
    public boolean loginCheck(User user);
    //회원 로그인 정보
    public User viewUser(User user);
    //회원 로그아웃
    public void logout(HttpSession session);
}
