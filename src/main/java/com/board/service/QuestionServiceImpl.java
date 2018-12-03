package com.board.service;

import com.board.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * Created by Kyudong on 2018. 11. 17..
 */
@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final RowMapper<Question> q_RowMapper = (resultSet, i) -> {

        Integer seqNo = resultSet.getInt("seqNo");
        String tag = resultSet.getString("tag");
        Integer difficulty = resultSet.getInt("difficulty");
        String option1 = resultSet.getString("option1");
        String option2 = resultSet.getString("option2");
        String option3 = resultSet.getString("option3");
        Integer answer = resultSet.getInt("answer");

        return new Question(seqNo, tag, difficulty, option1, option2, option3, answer);
    };

    @Override
    public List<Question> findAll() {
        List<Question> question = jdbcTemplate.query(
                "SELECT * from question", q_RowMapper);
        return question;
    }

    public List<Question> findName(String name1) {
        List<Question> question = jdbcTemplate.query(
                "Select * from question where tag = " + name1 , q_RowMapper
        );
        return question;
    }

    // seqNo 를 통해 계시판 찾기 //
    @Override
    public List<Map<String,Object>> findName2(String name1) {
        String query = "select option1, option2, option3 from question where tag = ?";
        return jdbcTemplate.queryForList(query, new Object[]{name1});

    }

    @Override
    public List<Map<String,Object>> findQuestions(int diff) {
        String query = "select tag, option1, option2, option3, answer from question where difficulty = ? order by RAND() limit 10";
        return jdbcTemplate.queryForList(query, new Object[]{diff});
    }


}
