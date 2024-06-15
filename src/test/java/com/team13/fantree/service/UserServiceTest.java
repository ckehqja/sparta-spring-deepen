package com.team13.fantree.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.team13.fantree.dto.ProfileResponseDto;
import com.team13.fantree.dto.SignUpRequestDto;
import com.team13.fantree.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 서버의 PORT 를 랜덤으로 설정합니다.
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스의 생성 단위를 클래스로 변경합니다.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	MailSendService mailSendService;
	@Autowired
	private UserService userService;

	@DisplayName("회원가입")
	@Test
	void test1() {
		// given
		SignUpRequestDto requestDto = new SignUpRequestDto("user", "1234", "name", "userq@gmail.com", "headline");

		// when
		ProfileResponseDto responseDto = userService.signup(requestDto);

		// then
		assertEquals(requestDto.getUsername(), responseDto.getUsername());
		assertEquals(requestDto.getEmail(), responseDto.getEmail());
		assertEquals(requestDto.getName(), responseDto.getName());

	}

	@DisplayName("회원 수정")
	@Test
	void test2() {
		// given

		// when

		// then

	}

}