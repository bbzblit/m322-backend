package dev.bbzblit.m120.models.validation;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.server.ResponseStatusException;

import dev.bbzblit.m120.models.AppUser;
import dev.bbzblit.m120.repository.AppUserRepository;
import dev.bbzblit.m120.service.AppUserService;

public class UniqueNameValidator implements ConstraintValidator<UniqueUsername, String> {

	@Autowired
	AppUserRepository appUserRepository;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		System.out.println("Okay");
		if (!value.matches("^[a-zA-Z0-9_\\-]+$")) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("Your username can only contain characters, numbers and _-");
			return false;
		}
		Optional<AppUser> appUser = this.appUserRepository.getByUsername(value);
				
		if (appUser.isPresent()) {
			return false;
		}

		return true;
	}

}
