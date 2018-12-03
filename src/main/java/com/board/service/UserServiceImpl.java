package com.board.service;

import com.board.entity.User;
import com.board.mapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by Kyudong on 2017. 11. 14..
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<User> userRowMapper2 = (resultSet, i) -> {
        //Integer id1 = resultSet.getInt("user_seqNo");
        String userName1 = resultSet.getString("user_id");
        String password1 = resultSet.getString("user_password");

        return new User(userName1, password1);
    };

    @Override
    public List<User> findAll() {
        RowMapper<User> rowMapper = new UserRowMapper();
        List<User> user = jdbcTemplate.query(
                "select * from user", rowMapper
        );
        return user;
    }

    @Override
    public int addUser(User user) {
        String query = "INSERT INTO user (user_id, user_password, user_phoneNumber, user_address) values (?, ?, ?, ?)";

        return jdbcTemplate.update(query, user.getUser_id(), user.getUser_password(), user.getUser_phoneNumber(), user.getUser_address());
    }

    public List<User> findByUserId(User user) {
        String query =  "select user_id, user_password from user where user_id=?, user_password=?";
        List<User> user2 = jdbcTemplate.query(query, userRowMapper2);

        return user2;


    }

    @Override
    public List<User> findDuplicatedId(String id) {
        RowMapper<User> rowMapper = new UserRowMapper();
        try {
            List<User> user = jdbcTemplate.query(
                    "select * from user where user_id = ?", new Object[] {id},
                    rowMapper
            );

            return user;
        } catch (UncategorizedSQLException e) {
            return null;
        }
    }

    @Override
    public String findDupl(String id) {
        try {
            String query = "select user_id from user where user_id = ?";
            return jdbcTemplate.queryForObject(query, new Object[]{id}, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
//    public void addUser(User user) {
//        String sql = "INSERT INTO articles (user_id, user_password, user_phoneNumber, user_address) values (?, ?, ?, ?)";
//        jdbcTemplate.update(sql, user.getUser_id(), user.getUser_password(), user.getUser_phoneNumber(), user.getUser_address());
//    }



}
