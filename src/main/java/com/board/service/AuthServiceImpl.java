package com.board.service;

import com.board.entity.Auth;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by Kyudong on 2017. 11. 17..
 */

@Service    // 현재 클래스를 스프링에서 관리하는 service bean으로 등록
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    SqlSession sqlSession;

    @Override
    public List<Map<String, Object>> validateUser(Auth auth) {
        List<Map<String, Object>> user = jdbcTemplate.queryForList("select user_seqNo, user_id, user_password," +
                "user_phoneNumber, user_address from user where user_id=?", auth.getAuth_id());
        return user;
    }

//    @Override
//    public String validateUser2(Auth auth) {
//        String userId = jdbcTemplate.query("select user_id from user where user_id=?",
//                auth.getAuth_id());
//        return null;
//    }

    @Override
    public String loginCheck(Auth auth) {
        String name = sqlSession.selectOne("Select user_id from user " +
                "where user_id=" + auth.getAuth_id() + " and user_password=" +
                auth.getAuth_password());
        //return (name == null) ? false : true;
        return name;
    }


//    // 회원 로그인체크
//    @Override
//    public boolean loginCheck(User user, HttpSession session) {
//        boolean result = userDao.loginCheck(user);
//        if(result) {    // true 일 경우 세션에 등록
//            User user2 = viewUser(user);
//            // 세션 변수 등록
//            session.setAttribute("user_id", user2.getUser_id());
//            session.setAttribute("user_password", user2.getUser_password());
//        }
//        return result;
//    }
//
//    // 회원 로그인 정보
//    @Override
//    public User viewUser(User user) {
//        return userDao.viewUser(user);
//    }
//
//    // 회원 로그아웃
//    @Override
//    public void logout(HttpSession session) {
//        // 세션 변수 개별 삭제
//        // session.removeAttribute("user_id");
//        // 세션 정보를 초기화 시킴
//        session.invalidate();
//    }
}
