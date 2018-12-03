package com.board.service;

import com.board.entity.Board;
import com.board.entity.BoardComment;
import com.board.entity.LikeTable;

import java.util.List;

/**
 * Created by Kyudong on 2017. 11. 14..
 */
public interface BoardService {

    public List<Board> findAll();

    public List<Board> findRegion(int region);

    public List<Board> getseqNoBoard(int seqNo);

    public int updateHitCountBoard(int seqNo);

    public int findByUserSeq(int userSeq);

    public int addBoard(Board board);

    public String findBoardBySeqNo(int seqNo);

    public int deleteBoardbySeqNo(int seqNo);

    public int deleteBoarduserseqNo(Board board);

    // 조회수 올라가는 쿼리 //
    public int updateNoticeCountBoard(int seqNo);

    // 좋아요 추출 쿼리 //
    public List<LikeTable> checkUserLikeBoard(int board_seqNo, int user_seqNo);

    // 좋아요 내리는 쿼리 //
    public int downHitCountBoard(int seqNo);

    // 좋아요 추가 쿼리 //
    public int addLikeTable(int board_id, int user_seqNo);

    // 좋아요 삭제 쿼리 //
    public int deleteLikeTable(int board_id, int user_seqNo);

    // 유저당 게시판 가져오기 //
    public List<Board> findEachUserBoard(int region, int seqNo, int offset);

    // 지역당 댓글 가져오기 //
    public List<BoardComment> findComment(int board_seqNo);

    // 댓글 작성 //
    public int addComment(BoardComment comment);

}
