package com.team13.fantree.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.team13.fantree.dto.CommentRequestDto;
import com.team13.fantree.dto.CommentResponseDto;
import com.team13.fantree.security.UserDetailsImpl;
import com.team13.fantree.service.CommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
	private final CommentService commentService;
	private static final String DELETE_POST_SUCCESS_MESSAGE = "댓글이 삭제되었습니다";

	@PostMapping
	public ResponseEntity<CommentResponseDto> createComment(
		@RequestBody CommentRequestDto commentRequestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		CommentResponseDto commentResponseDto = commentService.createComment(
			commentRequestDto, userDetails.getUser());
		return ResponseEntity.status(HttpStatus.CREATED).body(commentResponseDto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<List<CommentResponseDto>> findAllComments(@PathVariable Long id) {

		List<CommentResponseDto> commentResponseDtoList = commentService.findAllByPosts(id);
		return ResponseEntity.ok().body(commentResponseDtoList);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CommentResponseDto> updateComment(
		@PathVariable long id, @RequestBody CommentRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		CommentResponseDto commentResponseDto = commentService.updateComment(
			id, requestDto, userDetails.getUser());
		return ResponseEntity.ok().body(commentResponseDto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteComment(@PathVariable long id,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		commentService.deleteComment(id, userDetails.getUser());
		return ResponseEntity.ok().body(DELETE_POST_SUCCESS_MESSAGE);
	}
}
