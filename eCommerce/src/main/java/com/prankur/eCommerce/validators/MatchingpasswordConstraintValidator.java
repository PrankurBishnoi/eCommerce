package com.prankur.eCommerce.validators;

import com.prankur.eCommerce.dtos.PasswordDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchingpasswordConstraintValidator implements ConstraintValidator<MatchingPassword,Object>
{
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context)
    {
        PasswordDTO userDTO = (PasswordDTO) value;

        Boolean result = userDTO.getPassword().equals(userDTO.getConfirmPassword());

        return result;
    }
}
