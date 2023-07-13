package com.fastwon.board.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

import com.fastwon.board.domain.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long>, QuerydslPredicateExecutor<Comment> {
	
	@Query("SELECT c FROM Comment c")
	Page<Comment> getCommentList(Pageable pageable);
	
	

}
