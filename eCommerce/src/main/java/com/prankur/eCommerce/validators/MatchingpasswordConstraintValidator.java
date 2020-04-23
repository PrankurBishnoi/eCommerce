package com.prankur.eCommerce.validators;

import com.prankur.eCommerce.cos.PasswordCO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchingpasswordConstraintValidator implements ConstraintValidator<MatchingPassword,Object>
{
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context)
    {
        PasswordCO userDTO = (PasswordCO) value;

        Boolean result = userDTO.getPassword().equals(userDTO.getConfirmPassword());

        return result;
    }
}
