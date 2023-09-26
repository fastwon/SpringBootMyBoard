package com.fastwon.board.service;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.fastwon.board.domain.Board;
import com.fastwon.board.domain.PageNum;
import com.fastwon.board.domain.Search;
import com.google.firebase.auth.FirebaseAuthException;

public interface BoardService {

	void insertBoard(Board board);
	
	void updateBoard(Board board);
	
	void deleteBoard(Board board);
	
	Board getBoard(Board board);
	
	Page<Board> getBoardList(Search search, PageNum pn);
	
	Board getUpdateBoard(Board board);
	
	void uploadFiles(MultipartFile file, String nameFile) throws IOException, FirebaseAuthException;
	
	Page<Board> getMostViewedPostsInOneWeek();
}
