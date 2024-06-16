package com.team13.fantree.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.team13.fantree.dto.CommentRequestDto;
import com.team13.fantree.dto.CommentResponseDto;
import com.team13.fantree.entity.User;
import com.team13.fantree.repository.CommentRepository;
import com.team13.fantree.repository.PostRepository;
import com.team13.fantree.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 서버의 PORT 를 랜덤으로 설정합니다.
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스의 생성 단위를 클래스로 변경합니다.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@Transactional
class CommentServiceTest {

	@Autowired
	CommentService commentService;

	@Autowired
	CommentRepository commentRepository;

	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository;

	static User user;

	final long USER_ID = 2L;
	final long POST_ID = 10L;
	final long COMMENT_ID = 1L;
	final String COMMENT = "comment";
	final String EDIT_COMMENT = "edit comment";

	@DisplayName("댓글 작성")
	@Test
	void test1() {
		// given
		user = userRepository.findById(USER_ID).get();

		// when
		CommentResponseDto responseDto = commentService.createComment(new CommentRequestDto(POST_ID, COMMENT), user);

		// then
		assertEquals(responseDto.getContent(), COMMENT);
		assertEquals(responseDto.getPostId(), POST_ID);
		assertEquals(responseDto.getUserId(), USER_ID);
	}

	@DisplayName("특정 게시글에 달리 댓글 보기")
	@Test
	void test2() {
		// given

		// when
		List<CommentResponseDto> responseDtoList = commentService.findAllByPosts(POST_ID);

		// then
		for (CommentResponseDto responseDto : responseDtoList) {
			assertEquals(responseDto.getPostId(), POST_ID);
		}

	}

	@DisplayName("댓글 수정")
	@Test
	void test3() {
		// given

		// when
		CommentResponseDto responseDto = commentService.updateComment(COMMENT_ID,
			new CommentRequestDto(POST_ID, EDIT_COMMENT), user);
		// then
		assertEquals(responseDto.getContent(), EDIT_COMMENT);

	}

	@DisplayName("댓글 삭제")
	@Test
	void tset4() {
		// given

		// when
		commentService.deleteComment(COMMENT_ID, user);
		// then
		assertNull(commentRepository.findById(COMMENT_ID).orElse(null));
	}

}