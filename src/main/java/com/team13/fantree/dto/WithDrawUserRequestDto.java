package com.team13.fantree.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WithDrawUserRequestDto {
	@NotBlank(message = "비밀번호 입력하세요")
	String password;
}
