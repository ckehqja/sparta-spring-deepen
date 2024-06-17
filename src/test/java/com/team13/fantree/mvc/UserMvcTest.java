package com.team13.fantree.mvc;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team13.fantree.config.WebSecurityConfig;
import com.team13.fantree.controller.UserController;
import com.team13.fantree.dto.ProfileRequestDto;
import com.team13.fantree.dto.SignUpRequestDto;
import com.team13.fantree.dto.WithDrawUserRequestDto;
import com.team13.fantree.entity.User;
import com.team13.fantree.jwt.JwtTokenHelper;
import com.team13.fantree.security.UserDetailsImpl;
import com.team13.fantree.service.PostService;
import com.team13.fantree.service.UserService;

@ActiveProfiles("test")
@WebMvcTest(
	controllers = {UserController.class},
	excludeFilters = {
		@ComponentScan.Filter(
			type = FilterType.ASSIGNABLE_TYPE,
			classes = WebSecurityConfig.class
		)
	}
)
class UserMvcTest {
	private MockMvc mvc;

	private static Principal mockPrincipal;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	UserService userService;

	@MockBean
	PostService postService;

	@MockBean
	JwtTokenHelper jwtTokenHelper;

	final String USERNAME = "testUser123456";
	final String PW = "1234qweasdzxcQWE!@#";
	final String NAME = "name123";
	final String EMAIL = "test123@test.com";
	final String HEAD_LINE = "headline";
	final String EDIT_PW = "edit4qweasdzxcQWE!@#";
	final String EDIT_HEAD = "edit head";
	final String EDIT_NAME = "edit name";
	final String REFRESH_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE3MTg1MDgxNjEsImV4cCI6MTcxOTcxNzc2MX0.MQ4Wwmp8cBsZ6EpumV9LZ3SA3zA8oPXCfaaHrdsZ8l4";
	final String ACCESS_TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmbHRuc2FoMTIzNDUxMTEiLCJzdGF0dXMiOiJOT0FVVEhfVVNFUiIsImV4cCI6MTcxODUzMzA0MiwiaWF0IjoxNzE4NTMxMjQyfQ.eqKtvrfjeN1p3p_pfORz9QKZ3AWGwmiH6R1fGULpDJ8";

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context)
			.apply(springSecurity(new MockSpringSecurityFilter()))
			.build();

		this.mockUserSetup();
	}

	private void mockUserSetup() {
		// Mock 테스트 유져 생성
		User testUser = new User(USERNAME, PW, NAME, EMAIL, HEAD_LINE);
		UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
		mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
	}

	@Test
	@DisplayName("회원 가입 요청 처리")
	void test1() throws Exception {
		// given

		SignUpRequestDto requestDto = new SignUpRequestDto(USERNAME, PW, NAME, EMAIL, HEAD_LINE);

		String userInfo = objectMapper.writeValueAsString(requestDto);
		// when - then
		mvc.perform(post("/users")
				.content(userInfo)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
			)
			.andExpect(status().isCreated())
			.andDo(print());
	}

	@DisplayName("회원 탈퇴")
	@Test
	void test2() throws Exception {
		// given

		WithDrawUserRequestDto requestDto = new WithDrawUserRequestDto(PW);

		String userInfo = objectMapper.writeValueAsString(requestDto);
		// when - then
		mvc.perform(delete("/users")
				.content(userInfo)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.principal(mockPrincipal)
			)
			.andExpect(status().isOk());
	}

	@DisplayName("회원  수정")
	@Test
	void test3() throws Exception {
		// given
		ProfileRequestDto requestDto = new ProfileRequestDto(PW, EDIT_PW, EDIT_NAME, EDIT_HEAD);
		// when

		String userInfo = objectMapper.writeValueAsString(requestDto);
		// when - then
		mvc.perform(put("/users")
				.content(userInfo)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.principal(mockPrincipal)
			)
			.andExpect(status().isOk());

	}

	@DisplayName("프로필 조회")
	@Test
	void test4() throws Exception {
		// given

		// when

		// then
		mvc.perform(get("/users")
				.header(JwtTokenHelper.AUTHORIZATION_HEADER, ACCESS_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.principal(mockPrincipal)
			)
			.andExpect(status().isOk());
	}

	@DisplayName("토큰 재발급")
	@Test
	void test5() throws Exception {
		// given

		// when

		// then
		mvc.perform(get("/refresh")
				.header(JwtTokenHelper.AUTHORIZATION_HEADER, ACCESS_TOKEN)
				.header(JwtTokenHelper.REFRESH_TOKEN_HEADER, REFRESH_TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.principal(mockPrincipal)
			)
			.andExpect(status().isOk());

	}
}