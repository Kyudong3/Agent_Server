package com.board.controller;

import com.board.entity.Board;
import com.board.entity.BoardComment;
import com.board.entity.LikeTable;
import com.board.entity.Region;
import com.board.service.BoardServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by Kyudong on 2017. 10. 30..
 */

@RestController
@RequestMapping(path = "/board")
public class BoardController {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BoardServiceImpl boardService;

    @GetMapping
    public String list(Model model) {
        List<Board> boardPosts = boardService.findAll();
        model.addAttribute("boardPosts", boardPosts);
        return "/board/list";
    }

    // 전체 게시판 글 가져오기 //
    @GetMapping(path = "/list")
    public List<Board> showList(Model model) {
        List<Board> boards = boardService.findAll();
        return boards;
    }

    @GetMapping(path = "/getList")
    public ResponseEntity<List<Board>> getList() {
        List<Board> board = boardService.findAll();

        return new ResponseEntity<List<Board>>(board,HttpStatus.OK);
    }

    // ** 지역에 맞게 게시판 가져오기 ** //
    @PostMapping(path = "/getRegionList")
    public ResponseEntity<List<Board>> getRegionList(@RequestBody Region region, BindingResult bindingResult, HttpSession session) {

        log.info(session.getAttribute("auth") + " ");

        if(bindingResult.hasErrors()) {
            return null;
        }
        List<Board> board = boardService.findRegion(region.getRegion());

        return new ResponseEntity<List<Board>>(board, HttpStatus.OK);
    }

    // ** 글에 맞게 댓글 가져오기 ** //
    @GetMapping(path = "/getComments/{seqNo}")
    public ResponseEntity<List<BoardComment>> getComment(@PathVariable("seqNo") int board_seqNo) {

        List<BoardComment> getComment = boardService.findComment(board_seqNo);

        return new ResponseEntity<List<BoardComment>>(getComment, HttpStatus.OK);
    }

    @PostMapping(path = "/get/userBoard/{seqNo}")
    public ResponseEntity<List<Board>> getUserBoard(@RequestBody Region region, @PathVariable("seqNo") int seqNo,
                                                    BindingResult bindingResult, HttpSession session) {

        if(bindingResult.hasErrors()) {
            return null;
        }

        List<Board> board = boardService.findEachUserBoard(region.getRegion(), seqNo, region.getBoard_seq());

        return new ResponseEntity<List<Board>>(board, HttpStatus.OK);
    }

    // ** 글 정보 가져오기 ** //
    @GetMapping(path = "/get/{seqNo}")
    public ResponseEntity<List<Board>> getSeqNoBoard(@PathVariable("seqNo") int seqNo) {

        List<Board> getBoard = boardService.getseqNoBoard(seqNo);
        //boardService.updateHitCountBoard(seqNo);
        boardService.updateNoticeCountBoard(seqNo);

        return new ResponseEntity<>(getBoard, HttpStatus.OK);
    }

    // 게시판 글쓰기 //
    @PostMapping(path = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createFree(@Valid @RequestBody Board board, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "hhhhhhh";
            //return list(model);
        }

        boardService.addBoard(board);
        return "redirect:/board " + board.getBoard_title();
    }

    // 댓글 쓰기 //
    @PostMapping(path = "/comment/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createComment(@Valid @RequestBody BoardComment comment, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "hhhhhh";
        }

        boardService.addComment(comment);
        return "redirect:/board " + comment.getComment_seqNo();
    }

    // ** 게시글 좋아요 판별 ** //
    @PostMapping(path = "/like/{boardSeqNo}")
    public ResponseEntity<List<LikeTable>> checkLike(@PathVariable("boardSeqNo") int board_seqNo, @Valid @RequestBody LikeTable likeTable, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return null;
        }

        List<LikeTable> likeUsers = boardService.checkUserLikeBoard(board_seqNo, likeTable.getUser_seqNo());
        if(likeUsers.isEmpty()) {
            boardService.updateHitCountBoard(board_seqNo);
            boardService.addLikeTable(board_seqNo, likeTable.getUser_seqNo());
        } else {
            boardService.downHitCountBoard(board_seqNo);
            boardService.deleteLikeTable(board_seqNo, likeTable.getUser_seqNo());
        }

        return new ResponseEntity<List<LikeTable>>(likeUsers, HttpStatus.OK);

    }


//
//    // seqNo에 해당하는 게시글 수정하기 //
//    @PostMapping(path = "/edit/{seqNo}")
//    public String update(@PathVariable("seqNo") int seqNo, @RequestBody BoardPost boardPost) {
//        BoardPost getBoard = boardPostService.findOne(seqNo);
//        if(getBoard == null) {
//            return "No seqNo Found!!";
//        }
//
//        getBoard.setUser_seq(boardPost.getUser_seq());
//        getBoard.setTitle(boardPost.getTitle());
//        getBoard.setContent(boardPost.getContent());
//        getBoard.setHit_count(boardPost.getHit_count());
//        getBoard.setLike_Count(boardPost.getLike_Count());
//        getBoard.setRegDate(boardPost.getRegDate());
//        getBoard.setUpdateDate(boardPost.getUpdateDate());
//
//        boardPostService.update(getBoard);
//
//        return "redirect:/board";
//    }
//
    // seqNo에 해당하는 게시글 삭제 //
    @DeleteMapping(path = "/delete/{seqNo}")
    public String deleteBoardseqNo(@PathVariable("seqNo") int seqNo) {
        String findBoard = boardService.findBoardBySeqNo(seqNo);

        if(findBoard==null) {
            return "no seqNo found!";
        }

        boardService.deleteBoardbySeqNo(seqNo);
        return "redirect:/board";
    }

}
