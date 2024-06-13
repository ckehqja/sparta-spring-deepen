package com.team13.fantree.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team13.fantree.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findAllByPostId(long id);
}
