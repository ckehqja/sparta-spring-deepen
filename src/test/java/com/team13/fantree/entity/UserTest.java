package com.team13.fantree.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

	User user = new User("user", "pw", "user", "user@test.com", "testing");

	@DisplayName("탈퇴")
	@Test
	void test1() {
		// given

		// when
	user.withDraw();
		// then
		assertEquals(user.getStatus(), UserStatusEnum.NON_USER);

	}


	@DisplayName("로그아웃")
	@Test
	void test2() {
		// given
	       user.setRefreshToken("test");
		// when
	user.logout();
		// then
		assertEquals(user.getRefreshToken(), null);

	}


	@DisplayName("회원 수정")
	@Test
	void test3() {
		// given

		// when
	user.update(Optional.of("edit name"), Optional.of("edit hl"), Optional.of("edit pw"));
		// then
		assertEquals(user.getName(), "edit name");
		assertEquals(user.getHeadline(), "edit hl");
		assertEquals(user.getPassword(), "edit pw");
	}


	// @DisplayName("상태 시간 변경")
	// @Test
	// void test4() {
	// 	// given
	//
	// 	// when
	// user.userStatusUpdate();
	// 	// then
	// 	LocalDateTime statusUpdate = user.getStatusUpdate();
	// 	System.out.println("statusUpdate = " + statusUpdate);
	//
	// }

}