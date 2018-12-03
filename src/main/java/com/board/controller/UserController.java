package com.board.controller;

import com.board.configuration.Duplicated;
import com.board.configuration.ResponseCode;
import com.board.configuration.UserSuccess;
import com.board.entity.User;
import com.board.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Kyudong on 2017. 11. 14..
 */

@RestController
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;


    ResponseCode responseCode = new ResponseCode();

    @GetMapping(path = "/list")
    public List<User> showAllUser(Model model) {
        List<User> users = userService.findAll();
        return users;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return null;
            //return list(model);
        }

        List<User> users = userService.findDuplicatedId(user.getUser_id());
        //String id = userService.findDupl(user.getUser_id());
        if (users.isEmpty()) {
            userService.addUser(user);
            return new ResponseEntity<>(new UserSuccess(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Duplicated(), HttpStatus.OK);
        }
    }
//        if(bindingResult.hasErrors()) {
//            return null;
//        }
//
//        String id = userService.findDupl(user.getUser_id());
//        if(id==null) {
//            userService.addUser(user);
//            return new ResponseEntity<Integer>(responseCode.CREATE_USER_SUCCESS, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<Integer>(responseCode.DUPLICATED_SEQ, HttpStatus.BAD_REQUEST);
//        }
//    }
}
