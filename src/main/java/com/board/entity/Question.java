package com.board.entity;

import lombok.*;

/**
 * Created by Kyudong on 2018. 11. 17..
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Question {

    // ** q = question ** //

    private Integer seqNo;
    // ㅇㅣ름 //
    private String tag;
    // 난이도 //
    private Integer difficulty;
    // 옵션 1 //
    private String option1;
    // 옵션 2 //
    private String option2;
    // 옵션 3 //
    private String option3;

    // 정답 //
    private Integer answer;

}
