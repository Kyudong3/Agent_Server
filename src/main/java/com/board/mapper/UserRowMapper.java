package com.board.mapper;

import com.board.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Kyudong on 2017. 11. 14..
 */
public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        Integer id = resultSet.getInt("user_seqNo");
        String userName = resultSet.getString("user_id");
        String password = resultSet.getString("user_password");
        String phoneNumber = resultSet.getString("user_phoneNumber");
        String address = resultSet.getString("user_address");

        return new User(id, userName, password, phoneNumber, address);
    }
}
