package com.board.service;

import com.board.entity.User;

import java.util.List;

/**
 * Created by Kyudong on 2017. 11. 14..
 */
public interface UserService {

    public List<User> findAll();

    public int addUser(User user);

    public List<User> findByUserId(User user);

    public List<User> findDuplicatedId(String id);

    public String findDupl(String id);
}
