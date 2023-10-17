package com.fastwon.board.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "board")
@Entity
public class Comment {
	@Id
	@GeneratedValue
	private Long cmtId;
	
	private String cmtWriter;
	
	private String cmtContent;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date cmtCreateDate = new Date();

	@ManyToOne
	@JoinColumn(name="seq", nullable = false, updatable = false)
	private Board board;
	
	public void setBoard(Board board) {
		this.board = board;
		board.getCommentList().add(this);
	}
}
