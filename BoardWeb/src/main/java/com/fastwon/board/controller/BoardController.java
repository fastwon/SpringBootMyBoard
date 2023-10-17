package com.fastwon.board.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fastwon.board.domain.Board;
import com.fastwon.board.domain.Category;
import com.fastwon.board.domain.Comment;
import com.fastwon.board.domain.PageNum;
import com.fastwon.board.domain.Search;
import com.fastwon.board.persistence.BoardRepository;
import com.fastwon.board.security.SecurityUser;
import com.fastwon.board.service.BoardService;
import com.fastwon.board.service.CommentService;
import com.google.firebase.auth.FirebaseAuthException;

@Controller
@RequestMapping("/board/")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping("/getBoardList")
	public String getBoardList(Model model, Search search, PageNum pn, @RequestParam(value = "category", required = false) Category category) {
		if(search.getSearchCondition() == null)
			search.setSearchCondition("TITLE");
		if(search.getSearchKeyword() == null)
			search.setSearchKeyword("");
		
		Page<Board> boardList;
	    
	    if(category != null) {
	        boardList = boardService.getBoardList2(search, pn, category);
	        model.addAttribute("category", category);
	    } else {
	        boardList = boardService.getBoardList(search, pn);
	    }
	    
		model.addAttribute("boardList", boardList);
		model.addAttribute("gsc", search.getSearchCondition());
		model.addAttribute("gsk", search.getSearchKeyword());
		
		// totalPages와 currentPage를 추가
	    int totalPages = boardList.getTotalPages();
	    if (totalPages == 0) {
			totalPages = 1;
		}
	    int currentPage = boardList.getNumber() + 1; // getPageNumber는 0부터 시작하기 때문에 +1

	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("currentPage", currentPage);
		
		return "board/getBoardList";
	}
	
	@GetMapping("/getBoard")
	public String getBoard(Board board, Model model, PageNum pn, Integer chkUpdate) {
		Page<Comment> commentList = commentService.getCommentList(pn, board);
		model.addAttribute("board", boardService.getBoard(board));
		model.addAttribute("commentList", commentList);

		if(chkUpdate == null) {
			chkUpdate = -1;
		}
		
		model.addAttribute("chkUpdate", chkUpdate);
		
		
		// totalPages와 currentPage를 추가
	    int totalPages = commentList.getTotalPages();
	    if (totalPages == 0) {
			totalPages = 1;
		}
	    int currentPage = commentList.getNumber() + 1; // getPageNumber는 0부터 시작하기 때문에 +1

	    model.addAttribute("totalPages", totalPages);
	    model.addAttribute("currentPage", currentPage);
		return "board/getBoard";
	}
	
	@GetMapping("/insertBoard")
	public String insertBoardView(Model model, @RequestParam(value = "category", required = false) Category category) {
		model.addAttribute("category", category);
		return "board/insertBoard";
	}
	
	@PostMapping("/insertBoard")
	public String insertBoard(Board board, @RequestParam("photo") MultipartFile photo, @AuthenticationPrincipal SecurityUser principal) {
	    board.setMember(principal.getMember());
	    
	    // 사진 파일 처리 로직
	    if (!photo.isEmpty()) { // 파일이 비어있지 않다면 처리
	        // 원하는 경로에 파일을 저장
	        String fileName = StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename())) + System.currentTimeMillis();
	        
	        try {
	            // Firebase Storage에 이미지 업로드하고 URL 받아오기
	            String imageUrl = "https://firebasestorage.googleapis.com/v0/b/fastwonboard.appspot.com/o/" + fileName + "?alt=media";
        		boardService.uploadFiles(photo, fileName);
	            
	            // board 객체에 이미지 URL을 저장하는 필드를 추가
	            board.setPhotoUrl(imageUrl);
	            
	        } catch (IOException | FirebaseAuthException e) {
	            // 파일 저장 중 오류 처리
	            e.printStackTrace();
	        }
	    }
	    
	    boardService.insertBoard(board);
	    return "redirect:getBoardList";
	}

	
	@GetMapping("/updateBoard")
	public String updateBoardView(Board board, Model model, @AuthenticationPrincipal SecurityUser principal) {
		Board findBoard = boardService.getUpdateBoard(board);

		if(!findBoard.getMember().getId().equals(principal.getMember().getId())) {
			return "system/accessDenied";
		}
		
		model.addAttribute("board", findBoard);
		return "board/updateBoard";
	}
	
	@PutMapping("/updateBoard")
	public String updateBoard(Board board, @RequestParam("photo") MultipartFile photo) {
		
		// 사진 파일 처리 로직
        if (!photo.isEmpty()) { // 파일이 비어있지 않다면 처리
            // 원하는 경로에 파일을 저장
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(photo.getOriginalFilename())) + System.currentTimeMillis();
            // 경로 확인 및 생성
//            Path path = Paths.get("src/main/resources/static/uploads/" + fileName);
            try {
            	String imageUrl = "https://firebasestorage.googleapis.com/v0/b/fastwonboard.appspot.com/o/" + fileName + "?alt=media";
        		boardService.uploadFiles(photo, fileName);         	          	
        		
        		//board 객체에 이미지 URL을 저장하는 필드를 추가
        		board.setPhotoUrl(imageUrl);
            } catch (IOException | FirebaseAuthException e) {
                // 파일 저장 중 오류 처리
                e.printStackTrace();
            }

        } else {
        	Board findBoard = boardService.getUpdateBoard(board);
        	board.setPhotoUrl(findBoard.getPhotoUrl());
        }
		
		boardService.updateBoard(board);
		return "redirect:getBoardList";
	}
	
	@DeleteMapping("/deleteBoard")
	public String deleteBoard(Board board) {
		boardService.deleteBoard(board);
		return "redirect:getBoardList";
	}
}
