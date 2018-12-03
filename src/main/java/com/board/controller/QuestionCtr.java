package com.board.controller;

import com.board.entity.Question;
import com.board.service.QuestionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by Kyudong on 2018. 11. 17..
 */
@RestController
@RequestMapping(path = "/question")
public class QuestionCtr {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    QuestionServiceImpl qService;

    // 전체 문제  가져오기 //
    @GetMapping(path = "/listAll")
    public List<Question> showList(Model model) {
        List<Question> questions = qService.findAll();
        log.info("nonono");
        return questions;
    }


    @GetMapping(path = "/{name}")
    public ResponseEntity<List<Map<String, Object>>> getName(@PathVariable("name") String name) {

        List<Map<String, Object>> getName = qService.findName2(name);
        return new ResponseEntity<List<Map<String, Object>>>(getName, HttpStatus.OK);
    }

    @GetMapping(path = "/name/{diff}")
    public ResponseEntity<List<Map<String, Object>>> getQuestions(@PathVariable("diff") int diff) {

        List<Map<String, Object>> getquestions = qService.findQuestions(diff);
        return new ResponseEntity<List<Map<String, Object>>>(getquestions, HttpStatus.OK);
    }
    /*@GetMapping(path = "/{name}")
    public String getName(@PathVariable("name") String name) {
        String findBoard = qService.findName2(name);

        return findBoard + "redirect:/board";
    }*/
}
