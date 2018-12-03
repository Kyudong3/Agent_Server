package com.board.service;

import com.board.entity.Board;
import com.board.entity.Question;

import java.util.List;
import java.util.Map;

/**
 * Created by Kyudong on 2018. 11. 17..
 */
public interface QuestionService {

    public List<Question> findAll();

    public List<Question> findName(String name);

    //public List<Question> findName2(String name);

    public List<Map<String,Object>> findName2(String name);

    public List<Map<String,Object>> findQuestions(int diff);

}
