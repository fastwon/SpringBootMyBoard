package com.fastwon.board.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fastwon.board.domain.Board;
import com.fastwon.board.domain.Category;
import com.fastwon.board.domain.PageNum;
import com.fastwon.board.domain.QBoard;
import com.fastwon.board.domain.Search;
import com.fastwon.board.persistence.BoardRepository;
import com.fastwon.board.persistence.CommentRepository;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.querydsl.core.BooleanBuilder;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Autowired
	private CommentRepository commentRepo;

	@Override
	public void insertBoard(Board board) {
		boardRepo.save(board);
	}
	
	@Override
	public Board getUpdateBoard(Board board) {
		Board findBoard = boardRepo.findById(board.getSeq()).get();
		
		return findBoard;
	}
	
	@Override
	public void updateBoard(Board board) {
		Board findBoard = boardRepo.findById(board.getSeq()).get();
		
		findBoard.setTitle(board.getTitle());
		findBoard.setContent(board.getContent());
		
		boardRepo.save(findBoard);
		
	}
	
	@Override
	@Transactional
	public void deleteBoard(Board board) {
		Board board1 = boardRepo.findById(board.getSeq()).get();
		if(!board1.getCommentList().isEmpty()) {
			commentRepo.deleteAll(board1.getCommentList());
		}
		boardRepo.deleteById(board.getSeq());
	}
	
	@Override
	public Board getBoard(Board board) {
		Board findBoard = boardRepo.findById(board.getSeq()).get();
		findBoard.setCnt(findBoard.getCnt() + 1);
		
		// 조회수 추가를 위해 db에 저장
		boardRepo.save(findBoard);
		
		return findBoard;
	}
	
	// 카테고리 추가로 현재 미사용
	/*
	 * @Override public Page<Board> getBoardList(Search search, PageNum pn) {
	 * BooleanBuilder builder = new BooleanBuilder();
	 * 
	 * QBoard qboard = QBoard.board;
	 * 
	 * if(search.getSearchCondition().equals("TITLE")) {
	 * builder.and(qboard.title.like("%" + search.getSearchKeyword() + "%")); } else
	 * if(search.getSearchCondition().equals("CONTENT")) {
	 * builder.and(qboard.content.like("%" + search.getSearchKeyword() + "%")); }
	 * else if(search.getSearchCondition().equals("WRITER")) {
	 * builder.and(qboard.member.name.like("%" + search.getSearchKeyword() + "%"));
	 * }
	 * 
	 * Pageable pageable = PageRequest.of(pn.getNum()-1, 10, Sort.Direction.DESC,
	 * "createDate"); return boardRepo.findAll(builder, pageable); }
	 */
	
	@Value("${app.firebase-bucket}")
	private String firebaseBucket;


	@Override
	public void uploadFiles(MultipartFile file, String nameFile) throws IOException {
		
		Bucket bucket = StorageClient.getInstance().bucket(firebaseBucket);


    	InputStream content = new ByteArrayInputStream(file.getBytes());

		bucket.create(nameFile.toString(), content, file.getContentType());

	}

	/*
	 * @Override public Page<Board> getMostViewedPostsInOneWeek() { Date oneWeekAgo
	 * = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000); PageRequest
	 * pageRequest = PageRequest.of(0, 3); // 첫 페이지의 상위 3개 게시글을 가져오도록 설정
	 * 
	 * return boardRepo.findTopByOrderByViewCountDescAndCreatedAtAfter(oneWeekAgo,
	 * pageRequest); }
	 */
	
	@Override
	public Page<Board> getMostViewedPostsInOneWeek(Category category) {
		Date oneWeekAgo = new Date(System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000);
		PageRequest pageRequest = PageRequest.of(0, 3); // 첫 페이지의 상위 3개 게시글을 가져오도록 설정

		if(category != null) {
			return boardRepo.findTopByCategoryAndCreateDateAfterOrderByViewCountDesc(category, oneWeekAgo, pageRequest);
		} else {
			return boardRepo.findByCategoryIsNullAndCreateDateAfterOrderByViewCountDesc(oneWeekAgo, pageRequest);
		}
	}

	
	@Override
	public Page<Board> getBoardList2(Search search, PageNum pn, Category category) {
		BooleanBuilder builder = new BooleanBuilder();
		
		QBoard qboard = QBoard.board;
		
		if(category == null) {
			builder.and(qboard.category.isNull());
		} else {
			builder.and(qboard.category.eq(category));
		}
		
		if(search.getSearchCondition().equals("TITLE")) {
			builder.and(qboard.title.like("%" + search.getSearchKeyword() + "%"));
		} else if(search.getSearchCondition().equals("CONTENT")) {
			builder.and(qboard.content.like("%" + search.getSearchKeyword() + "%"));
		} else if(search.getSearchCondition().equals("WRITER")) {
		    builder.and(qboard.member.name.like("%" + search.getSearchKeyword() + "%"));
		}
		
		Pageable pageable = PageRequest.of(pn.getNum()-1, 10, Sort.Direction.DESC, "createDate");
		return boardRepo.findAll(builder, pageable);
	}
	
}
