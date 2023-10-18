package com.fastwon.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fastwon.board.domain.Board;
import com.fastwon.board.domain.Category;
import com.fastwon.board.service.BoardService;

@Controller
public class HomeController {
	@Autowired
	private BoardService boardService;
	
	
	@GetMapping("/")
	public String home(Model model) {
		Category c1 = Category.GAME;
		Category c2 = Category.FOOTBALL;
		Category c3 = Category.MUSIC;
		
		Page<Board> boardList1 = boardService.getMostViewedPostsInOneWeek(c1);
		Page<Board> boardList2 = boardService.getMostViewedPostsInOneWeek(c2);
		Page<Board> boardList3 = boardService.getMostViewedPostsInOneWeek(c3);
		Page<Board> boardList4 = boardService.getMostViewedPostsInOneWeek(null);
		
		model.addAttribute("boardList1", boardList1);
		model.addAttribute("boardList2", boardList2);
		model.addAttribute("boardList3", boardList3);
		model.addAttribute("boardList4", boardList4);
		
		return "home";
	}
}
