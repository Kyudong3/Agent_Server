package com.board.service;

import com.board.entity.Board;
import com.board.entity.BoardComment;
import com.board.entity.LikeTable;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import static java.time.LocalTime.now;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    SimpleDateFormat dateFormat;
    SimpleDateFormat dateFormat2;


    private static final RowMapper<Board> boardRowMapper = (resultSet, i) -> {
        Integer id = resultSet.getInt("board_seqNo");
        String title = resultSet.getString("board_title");
        String content = resultSet.getString("board_content");
        Integer hitCount = resultSet.getInt("board_hitCount");
        Integer likeCount = resultSet.getInt("board_likeCount");
        Timestamp regDate = resultSet.getTimestamp("board_regDate");
        Integer userSeq = resultSet.getInt("user_seqNo");
        int board_region = resultSet.getInt("board_region");
        String like_seq = resultSet.getString("like_seq");
        int comment_cnt = resultSet.getInt("comment_cnt");
        //String user_id = resultSet.getString("user_id");
        //boolean userLiked = resultSet.getBoolean("board_userLiked");
        return new Board(id, title, content, hitCount, likeCount, regDate, userSeq, board_region, like_seq, comment_cnt);
    };

    private static final RowMapper<LikeTable> likeRowMapper = ((resultSet, i) -> {
        Integer user_seqNo = resultSet.getInt("user_seqNo");
        //Integer board_id = resultSet.getInt("board_id");

        return new LikeTable(user_seqNo);
    });

    private static final RowMapper<BoardComment> CommentRowMapper = (((resultSet, i) -> {
        Integer comment_seqNo = resultSet.getInt("comment_seqNo");
        String comment_content = resultSet.getString("comment_content");
        Timestamp regDate = resultSet.getTimestamp("comment_regDate");
        Integer board_seqNo = resultSet.getInt("board_seqNo");

        return new BoardComment(comment_seqNo, comment_content, regDate, board_seqNo);
    }));

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // 게시판 전체 찾기 //
    @Override
    public List<Board> findAll() {
        List<Board> board = jdbcTemplate.query(
                "SELECT * from board", boardRowMapper);
        return board;
    }

    // ** 유저당 게시판 불러오기 ** //
    @Override
    public List<Board> findEachUserBoard(int region, int seqNo, int board_seq) {
       List<Board> board = jdbcTemplate.query(
               "select b.user_seqNo, b.board_seqNo, b.board_title, b.board_content, b.board_hitCount, b.board_likeCount," +
                       "b.board_regDate, b.board_region, (select id from likeTable where board_id = b.board_seqNo and user_seqNo = " +
                       seqNo + " Limit 1) As like_seq, (select count(*) from boardComment where board_seqNo = b.board_seqNo) AS comment_cnt" +
                       " from board As b where b.board_region = " + region + " and b.board_seqNo < " + board_seq + " order by board_seqNo desc limit 20", boardRowMapper
       );
       return board;
    }

    @Override
    public List<Board> findRegion(int region) {
        List<Board> board = jdbcTemplate.query(
                "Select * from board where board_region = " + region , boardRowMapper
        );

        return board;
    }

    // 댓글 불러오기 //
    @Override
    public List<BoardComment> findComment(int board_seqNo) {
        List<BoardComment> boardComments = jdbcTemplate.query(
                "Select comment_seqNo, comment_content, comment_regDate, board_seqNo from boardComment where board_seqNo = " + board_seqNo , CommentRowMapper
        );

        return boardComments;
    }

    // 댓글 작성하기 //
    @Override
    public int addComment(BoardComment comment) {
        dateFormat2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String query = "insert into boardComment (comment_content, comment_regDate, board_seqNo)" +
                "values(?,?,?)";
        return jdbcTemplate.update(query, comment.getComment_content(), dateFormat2.format(comment.getComment_regDate()), comment.getBoard_seqNo());
    }

    @Override
    public List<Board> getseqNoBoard(int seqNo) {
        List<Board> board = jdbcTemplate.query(
                "select b.board_seqNo, b.board_title, b.board_content, b.board_hitCount, b.board_likeCount, b.user_seqNo, b.board_regDate, b.board_region, b.like_seq," +
                        " (SELECT count(*) from boardComment where board_seqNo = b.board_seqNo ) As comment_cnt from board As b where board_seqNo = " + seqNo , boardRowMapper
        );

        return board;
    }

    // 유저seq 를 통해 게시판 찾기 //
    @Override
    public int findByUserSeq(int userSeq) {
        String query = "select board_seqNo from board where userSeq = ?";
        return jdbcTemplate.update(query, userSeq);
    }

    // 게시판 추가하기 //
    @Override
    public int addBoard(Board board) {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        //dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String query = "insert into board (board_title, board_content, board_hitCount, board_likeCount, board_regDate, user_seqNo, board_region, like_seq, comment_cnt) " +
                "values(?,?,?,?,?,?,?,?,?)";

        return jdbcTemplate.update(query, board.getBoard_title(), board.getBoard_content(), board.getBoard_hitCount(), board.getBoard_likeCount(),
                dateFormat.format(board.getBoard_regDate()), board.getUser_seqNo(), board.getBoard_region(), "null", 0);
    }

    //dateFormat.format(board.getBoard_regDate())

    // seqNo 를 통해 계시판 찾기 //
    @Override
    public String findBoardBySeqNo(int seqNo) {
        String query = "select board_seqNo, board_content, board_hitCount, board_likeCount, board_regDate from board where board_seqNo = ?";
        return jdbcTemplate.queryForObject(query, new Object[]{seqNo}, String.class);
    }

    // seqNo 를 통해 게시판 삭제 //
    @Override
    public int deleteBoardbySeqNo(int seqNo) {
        String query = "delete from board where board_seqNo = ?";
        return jdbcTemplate.update(query, seqNo);
    }

    // User_seqNo 를 통해 게시판 삭제 //
    @Override
    public int deleteBoarduserseqNo(Board board) {
        String query = "delete from board where user_seq = ?";
        return jdbcTemplate.update(query, board.getUser_seqNo());
    }

    // board_seqNo 와 user_seqNo 를 통해 유저가 게시글을 좋아요 했는지 판별하는 함수 //
    @Override
    public List<LikeTable> checkUserLikeBoard(int board_seqNo, int user_seqNo) {
        List<LikeTable> likeUsers = jdbcTemplate.query("select * from likeTable where board_id = " + board_seqNo + " and user_seqNo = " +
                user_seqNo, likeRowMapper);
        return likeUsers;
    }

    // 조회수 올라가는 함수 //
    @Override
    public int updateNoticeCountBoard(int seqNo) {
        String query = "update board set board_hitCount = board_hitCount+1 where board_seqNo = " + seqNo;
        return jdbcTemplate.update(query);
    }

    // 좋아요 올리게 하는 함수 //
    @Override
    public int updateHitCountBoard(int seqNo) {
        String query = "update board set board_likeCount = board_likeCount+1 where board_seqNo = " + seqNo;
        return jdbcTemplate.update(query);
    }

    // 좋아요 내리게 하는 함수 //
    @Override
    public int downHitCountBoard(int seqNo) {
        String query = "update board set board_likeCount = board_likeCount-1 where board_seqNo = " + seqNo;
        return jdbcTemplate.update(query);
    }

    // 좋아요 테이블에 추가하는 함수 //
    @Override
    public int addLikeTable(int board_id, int user_seqNo) {
        String query = "insert into likeTable (board_id, user_seqNo)" +
                "values(?,?)";

        return jdbcTemplate.update(query, board_id, user_seqNo);
    }

    // 좋아요 테이블에서 삭제하는 함수 //
    @Override
    public int deleteLikeTable(int board_id, int user_seqNo) {
        String query = "delete from likeTable where board_id = " + board_id + " and user_seqNo = " + user_seqNo;

        return jdbcTemplate.update(query);
    }

}
