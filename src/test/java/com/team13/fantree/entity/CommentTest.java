package com.team13.fantree.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentTest {

	@DisplayName("like ++")
	@Test
	void test1() {
		// given
		Post post = new Post();
		User user = new User();

		Comment comment = new Comment(post, user, "dd");

		// when
		comment.UpLikeCount();

		// then
		assertEquals(comment.getLikeCount(), 1);

	}

}