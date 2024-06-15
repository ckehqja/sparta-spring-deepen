package com.team13.fantree.dto;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class SignUpRequestDtoTest {

	private static ValidatorFactory factory;
	private static Validator validator;

	@BeforeAll
	public static void init() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@DisplayName("validation test")
	@Test
	void test1() {
		// given
		SignUpRequestDto requestDto = new SignUpRequestDto("saf","asdf","asdf", "asdf", "asdfds");

		// when

		// then
		Set<ConstraintViolation<SignUpRequestDto>> validate = validator.validate(requestDto);

		for (ConstraintViolation<SignUpRequestDto> violation : validate) {
			System.err.println(violation.getMessage());
		}

	}
}