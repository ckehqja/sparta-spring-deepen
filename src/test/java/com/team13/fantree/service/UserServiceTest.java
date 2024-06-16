package com.team13.fantree.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.team13.fantree.dto.ProfileRequestDto;
import com.team13.fantree.dto.ProfileResponseDto;
import com.team13.fantree.dto.SignUpRequestDto;
import com.team13.fantree.entity.User;
import com.team13.fantree.entity.UserStatusEnum;
import com.team13.fantree.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 서버의 PORT 를 랜덤으로 설정합니다.
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // 테스트 인스턴스의 생성 단위를 클래스로 변경합니다.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@Transactional
class UserServiceTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	MailSendService mailSendService;
	@Autowired
	private UserService userService;

	final long USER_ID = 35L;
	final String USERNAME = "testUser";
	final String SIGNUP_USERNAME = "testUser1";
	final String PW = "1234qweasdzxcQWE!@#";
	final String NAME = "name123";
	final String EMAIL = "test123@test.com";
	final String SIGNUP_EMAIL = "test1231@test.com";
	final String HEAD_LINE = "headline";
	final String EDIT_PW = "edit4qweasdzxcQWE!@#";
	final String EDIT_HEAD = "edit head";
	final String EDIT_NAME = "edit name";
	final String REFRESH_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTg1MDgxNjEsImV4cCI6MTcxOTcxNzc2MX0.MQ4Wwmp8cBsZ6EpumV9LZ3SA3zA8oPXCfaaHrdsZ8l4";

	@DisplayName("회원가입")
	@Test
	void test1() {
		// given
		SignUpRequestDto requestDto = new SignUpRequestDto(SIGNUP_USERNAME, PW, NAME, SIGNUP_EMAIL, HEAD_LINE);

		// when
		ProfileResponseDto responseDto = userService.signup(requestDto);

		// then
		assertEquals(requestDto.getUsername(), responseDto.getUsername());
		assertEquals(requestDto.getEmail(), responseDto.getEmail());
		assertEquals(requestDto.getName(), responseDto.getName());

	}

	@DisplayName("회원 수정 - 이름과 헤드라인만 수정")
	@Test
	void test2() {
		// given

		// when
		ProfileResponseDto update = userService.update(USER_ID, new ProfileRequestDto(
			null, null, EDIT_NAME, EDIT_HEAD));

		// then
		assertEquals(update.getName(), EDIT_NAME);
		assertEquals(update.getHeadline(), EDIT_HEAD);
	}

	@DisplayName("회원 수정 - 비밀번호 수정")
	@Test
	void test3() {
		// given

		// when
		ProfileResponseDto update = userService.update(USER_ID, new ProfileRequestDto(
			PW, EDIT_PW, null, null));
		User user = userRepository.findById(USER_ID).get();

		// then
		assertTrue(passwordEncoder.matches(EDIT_PW, user.getPassword()));
	}

	@DisplayName("탈퇴")
	@Test
	void tset4() {
		// given

		// when
		userService.withDraw(USER_ID, PW);
		// then
		User user = userRepository.findById(USER_ID).get();
		assertEquals(user.getStatus(), UserStatusEnum.NON_USER);
	}

	@DisplayName("유저 조회")
	@Test
	void test5() {
		// given

		// when
		ProfileResponseDto profile = userService.getProfile(USER_ID);
		// then
		assertEquals(profile.getUsername(), USERNAME);
		assertEquals(profile.getEmail(), EMAIL);
		assertEquals(profile.getName(), NAME);
		assertEquals(profile.getHeadline(), HEAD_LINE);

	}

	@DisplayName("리플레시 토큰 확인")
	@Test
	void test6(){
		// given

		// when

		// then
		assertDoesNotThrow(() -> userService.refreshTokenCheck(USERNAME, REFRESH_TOKEN));

	}

}