package com.team13.fantree.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post")
public class Post extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id")
	private User user;

	private String content;

	private Long likeCount;

	public Post(String content, User user) {
		this.content = content;
		this.user = user;
		// this.likeCount = (long) (Math.random()* 1_000_000_000); //좋아요 조작
		this.likeCount = 0L;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void UpLikeCount() {
		likeCount++;
	}
}
