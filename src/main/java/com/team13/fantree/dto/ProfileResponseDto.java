package com.team13.fantree.dto;

import com.team13.fantree.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProfileResponseDto {
	private long id;
	private String username;
	private String name;
	private String email;
	private String headline;

	public ProfileResponseDto(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.name = user.getName();
		this.email = user.getEmail();
		this.headline = user.getHeadline();
	}

}
