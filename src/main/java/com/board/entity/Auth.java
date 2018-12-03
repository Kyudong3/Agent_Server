package com.board.entity;

import lombok.*;

/**
 * Created by Kyudong on 2017. 11. 17..
 */
//@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Auth {

    private String auth_id;
    private String auth_password;

}
