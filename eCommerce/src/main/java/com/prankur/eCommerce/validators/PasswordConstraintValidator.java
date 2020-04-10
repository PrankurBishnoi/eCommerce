package com.prankur.eCommerce.validators;

import org.passay.*;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword,String>
{
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context)
    {
        PasswordValidator validator = new PasswordValidator(
                new LengthRule(8,15),
                new CharacterRule(EnglishCharacterData.LowerCase,1),
                new CharacterRule(EnglishCharacterData.UpperCase,1),
                new CharacterRule(EnglishCharacterData.Digit,1),
                new CharacterRule(EnglishCharacterData.Special,1)
        );
        RuleResult result = validator.validate(new PasswordData(value));
        if (result.isValid())
            return true;
        else
            return false;
    }
}
