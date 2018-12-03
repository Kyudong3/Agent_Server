package com.board.entity;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by Kyudong on 2018. 2. 16..
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BoardComment {

    private Integer comment_seqNo;
    private String comment_content;
    private Timestamp comment_regDate;
    private int board_seqNo;

}
