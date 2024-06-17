package com.team13.fantree.mvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
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
import com.team13.fantree.controller.PostController;
import com.team13.fantree.controller.UserController;
import com.team13.fantree.dto.PostRequestDto;
import com.team13.fantree.dto.PostResponseDto;
import com.team13.fantree.entity.Post;
import com.team13.fantree.entity.User;
import com.team13.fantree.jwt.JwtTokenHelper;
import com.team13.fantree.security.UserDetailsImpl;
import com.team13.fantree.service.PostService;
import com.team13.fantree.service.UserService;

@ActiveProfiles("test")
@WebMvcTest(
	controllers = {UserController.class, PostController.class},
	excludeFilters = {
		@ComponentScan.Filter(
			type = FilterType.ASSIGNABLE_TYPE,
			classes = WebSecurityConfig.class
		)
	}
)
class PostMvcTest {
	private MockMvc mvc;

	private static Principal mockPrincipal;

	private static User testUser;

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

	final long USER_ID = 35L;
	final String USERNAME = "testUser123456";
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
		testUser = new User(USERNAME, PW, NAME, EMAIL, HEAD_LINE);
		UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
		mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "", testUserDetails.getAuthorities());
	}

	@Test
	@DisplayName("글 등록")
	void test1() throws Exception {
		// given
		PostRequestDto requestDto = new PostRequestDto("create post");

		String postInfo = objectMapper.writeValueAsString(requestDto);
		given(postService.createPost(requestDto, testUser))
			.willReturn(new PostResponseDto(
				new Post("dsfs", testUser)));

		// when - then
		mvc.perform(post("/posts")
				.content(postInfo)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.principal(mockPrincipal)
			)
			.andExpect(status().isCreated())
			.andDo(print());
	}

	@DisplayName("페이징으로 글 가져오기")
	@Test
	void test2() throws Exception {
		// given

		// when

		// then
		mvc.perform(get("/posts"))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@DisplayName("좋아요 순으로 가져요기")
	@Test
	void test3() throws Exception {
		// given

		// when

		// then
		mvc.perform(get("/posts/likes"))
			.andExpect(status().isOk())
			.andDo(print());
	}


	@DisplayName("프로필 조회")
	@Test
	void test44() throws Exception {
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

	@DisplayName("기간별 조회하기")
	@Test
	void test4() throws Exception {
		// given

		// when

		// then
		mvc.perform(get("/posts/period?startDate=2024-06-08&endDate=2024-06-10"))
			.andExpect(status().isOk())
			.andDo(print());
	}

	@DisplayName("글 단권 단회")
	@Test
	void test5() throws Exception {
		// given

		// when

		// then
		mvc.perform(get("/posts/2"))
			.andExpect(status().isOk())
			.andDo(print());
	}



}