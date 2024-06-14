package com.team13.fantree.dto;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class EmailCheckRequestDtoTest {
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
	       EmailCheckRequestDto emailCheckRequestDto = new EmailCheckRequestDto();
		// when

		// then
		Set<ConstraintViolation<EmailCheckRequestDto>> validate = validator.validate(emailCheckRequestDto);

		for (ConstraintViolation<EmailCheckRequestDto> violation : validate) {
			System.err.println(violation.getMessage());
		}

	}

}