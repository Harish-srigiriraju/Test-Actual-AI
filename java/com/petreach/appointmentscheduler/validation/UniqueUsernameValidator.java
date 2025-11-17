package com.petreach.appointmentscheduler.validation;

import org.springframework.beans.factory.annotation.Autowired;

import com.petreach.appointmentscheduler.entity.user.User;
import com.petreach.appointmentscheduler.service.UserService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, Object> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(final UniqueUsername constraintAnnotation) {
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        String userName = (String) obj;
        return !userService.userExists(userName);
    }

}