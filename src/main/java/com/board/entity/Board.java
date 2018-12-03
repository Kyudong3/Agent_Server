package com.board.entity;

import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Kyudong on 2017. 11. 14..
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Board {

    private Integer board_seqNo;
    private String board_title;
    private String board_content;
    private int board_hitCount;
    private int board_likeCount;
    private Timestamp board_regDate;
    private int user_seqNo;
    private int board_region;
    private String like_seq;
    private int comment_cnt;
    //private String user_id;
    //boolean userLiked;

}
