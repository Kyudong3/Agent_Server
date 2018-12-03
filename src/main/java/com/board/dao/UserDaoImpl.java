package com.board.dao;

import com.board.entity.User;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.http.HttpSession;

/**
 * Created by Kyudong on 2017. 11. 18..
 */

public class UserDaoImpl implements UserDao {

    SqlSession sqlSession;

    @Override
    public boolean loginCheck(User user) {
        String name = sqlSession.selectOne("Select user_id AS userName from user " +
                "where user_seq=" + user.getUser_seqNo() + " and user_password=" +
                user.getUser_password());
        return (name == null) ? false : true;
    }

    @Override
    public User viewUser(User user) {
        return sqlSession.selectOne("select user_id AS userName, user_password AS userPassWord" +
                "from user where user_id=" + user.getUser_id() + " and user_password=" +
                user.getUser_password());
    }

    @Override
    public void logout(HttpSession session) {

    }
}
