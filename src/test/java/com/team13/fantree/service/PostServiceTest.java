package com.team13.fantree.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.team13.fantree.dto.PostRequestDto;
import com.team13.fantree.dto.PostResponseDto;
import com.team13.fantree.entity.User;
import com.team13.fantree.jwt.JwtTokenHelper;
import com.team13.fantree.repository.PostRepository;
import com.team13.fantree.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 서버의 PORT 를 랜덤으로 설정합니다.
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스의 생성 단위를 클래스로 변경합니다.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostServiceTest {

	@Autowired
	PostService postService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PostRepository postRepository;

	User user;
	PostRequestDto postRequestDto = new PostRequestDto();

	@Autowired
	JwtTokenHelper jwtTokenHelper;

	public static long postId;

	@DisplayName("글 생성")
	@Test
	@Order(1)
	void test1() {
		// given
		postRequestDto.setContent("post content");
		user = userRepository.findById(1L).orElse(null);

		// when
		PostResponseDto post = postService.createPost(postRequestDto, user);
		System.out.println("post.getId() = " + post.getId());
		postId = post.getId();

		// then
		assertNotNull(post.getId());
		assertEquals(postRequestDto.getContent(), post.getContent());
		assertEquals(user.getUsername(), post.getUsername());

	}

	@DisplayName("글 수정")
	@Test
	@Order(2)
	void test2() {
		// given
		postRequestDto.setContent("post edit");
		user = userRepository.findById(1L).orElse(null);
		// when
		PostResponseDto post = postService.updatePost(postId, postRequestDto, user);
		System.out.println("post.getId() = " + post.getId());

		// then
		assertEquals(post.getContent(), postRequestDto.getContent());

	}

	@DisplayName("글 삭제")
	@Test
	@Order(3)
	void test3() {
		// given
		User user = userRepository.findById(1L).orElse(null);

		// when
		postService.deletePost(postId, user);

		// then
		assertEquals(postRepository.findById(1L).orElse(null), null);

	}

	@Transactional
	@DisplayName("글 페이징 생성순 내림차순 정렬 조회")
	@Test
	void test4() {
		// given

		// when
		List<PostResponseDto> allPosts = postService.findAllPosts(1, 10);
		// then
		assertTrue(allPosts.size() == 10);

	}

	@Test
	void test5() {
		// given

		// when
		List<PostResponseDto> allPostsLikes = postService.findAllPostsLikes(0, 10);

		// then
		assertTrue(allPostsLikes.size() == 10);
		assertThat(allPostsLikes).isSortedAccordingTo(
			Comparator.comparingLong(PostResponseDto::getLikeCount).reversed());
	}

	@DisplayName("글 기간조회")
	@Test
	void test6() {
		// given
		List<PostResponseDto> allPostsPeriod = postService.findAllPostsPeriod(
			LocalDate.of(2024, 06, 11),
			LocalDate.of(2024, 06, 12), 0, 10);
		// when

		// then

	}

}