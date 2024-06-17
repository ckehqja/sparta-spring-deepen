package com.team13.fantree.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.team13.fantree.dto.ProfileRequestDto;
import com.team13.fantree.dto.ProfileResponseDto;
import com.team13.fantree.entity.User;
import com.team13.fantree.exception.MismatchException;
import com.team13.fantree.repository.UserRepository;

@ExtendWith(MockitoExtension.class) // @Mock 사용을 위해 설정합니다.
class UserServiceUnitTest {

	@Mock
	UserRepository userRepository;

	@Mock
	MailSendService mailSendService;

	User user;

	// @DisplayName("회원가입")
	// @Test
	// @Transactional
	// void test1() {
	// 	// given
	// 	SignUpRequestDto requestDto = new SignUpRequestDto().builder()
	// 		.username("userna1me11123AA")
	// 		.password("123qew1asdZXC!@#")
	// 		.name("user")
	// 		.email("user11@e1mail.com")
	// 		.headline("test").build();
	//
	// 	UserService userService = new UserService(userRepository, passwordEncoder, mailSendService);
	//
	// 	// when
	// 	ProfileResponseDto responseDto = userService.signup(requestDto);
	//
	// 	// then
	// 	User user = userRepository.findById(responseDto.getId()).orElse(null);
	// 	assertEquals(responseDto.getUsername(), user.getUsername());
	// 	assertEquals(responseDto.getEmail(), user.getEmail());
	// 	assertEquals(responseDto.getHeadline(), user.getHeadline());
	// 	assertEquals(responseDto.getName(), user.getName());
	// }

	// @Rollback
	// @DisplayName("탈퇴")
	// @Test
	// void test2() {
	// 	// given
	//
	// 	User user = new User("userna1me11123AA1", passwordEncoder.encode("1234"), "user",
	// 		"user@gmail.com22", "test");
	// 	UserService userService = new UserService(userRepository, passwordEncoder, mailSendService);
	// 	given(userRepository.findById(1L)).willReturn(Optional.of(user));
	//
	// 	// when
	// 	userService.withDraw(1L,  "1234");
	// 	// then
	// 	assertEquals(UserStatusEnum.NON_USER, user.getStatus());
	// }

	/**
	 * 비밀번호 변경확인 못함
	 * passEncoder 문제;;;
	 */
	@DisplayName("프로필 수정")
	@Test
	void test3() {
		// given
		User user = new User("userna1me11123AA1", "1234", "user",
			"user@gmail.com22", "test");
		UserService userService = new UserService(userRepository, passwordEncoder, mailSendService);
		given(userRepository.findById(1L)).willReturn(Optional.of(user));
		// when
		ProfileResponseDto updateDto = userService.update(1L, new ProfileRequestDto(null, null, "editUser", "edit headline"));
		user = userRepository.findById(1L).get();

		// then
		assertEquals(updateDto.getName(), user.getName());
		assertEquals(updateDto.getHeadline(), user.getHeadline());

	}

	@DisplayName("프로필 조회")
	@Test
	void test4() {
		// given
			User user = new User("userna1me11123AA1", "1234", "user",
				"user@gmail.com22", "test");
			UserService userService = new UserService(userRepository, passwordEncoder, mailSendService);
			given(userRepository.findById(1L)).willReturn(Optional.of(user));

		// when
		ProfileResponseDto profile = userService.getProfile(1L);
		// then
		assertNotNull(profile.getId());

	}

	@DisplayName("리플레시 토큰 확인")
	@Test
	void test5() {
		// given
		User user = new User("userna1me11123AA1", "1234", "user",
			"user@gmail.com22", "test");
		user.saveRefreshToken("refreshToken");
		UserService userService = new UserService(userRepository, passwordEncoder, mailSendService);
		given(userRepository.findByUsername(user.getUsername())).willReturn(Optional.of(user));

		// when

		// then
		assertThrows(MismatchException.class,
			() -> userService.refreshTokenCheck(
				user.getUsername(), "accessToken"));
	}


}