package com.board.entity;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

    private int user_seqNo;
    private String user_id;
    private String user_password;
    private String user_phoneNumber;
    private String user_address;

    public User(String user_id, String user_password) {
        this.user_id = user_id;
        this.user_password = user_password;
    }
}
