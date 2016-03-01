package com.secretescapes.codingchallenge.mvc.validate;

import com.secretescapes.codingchallenge.mvc.form.object.AccountPayFormObject;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;

/**
 * Created by Nigel on 2016-03-01.
 */
@Component
public class AccountPayValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountPayFormObject.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AccountPayFormObject accountPayFormObject = (AccountPayFormObject) target;

        if(accountPayFormObject.getAmount() == null) {
            errors.rejectValue("amount", "amount.null", "Please enter an amount to transfer");
        } else {
            try {
                BigDecimal bd = new BigDecimal(accountPayFormObject.getAmount());
                if(bd.compareTo(new BigDecimal(0)) < 0) {
                    errors.rejectValue("amount", "amount.negative", "Cannot transfer a negative amount");
                }

                if(bd.compareTo(new BigDecimal(0)) == 0) {
                    errors.rejectValue("amount", "amount.negative", "Cannot transfer 0");
                }
            } catch (NumberFormatException e) {
                errors.rejectValue("amount", "amount.format", "Incorrect format");
            }
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "toAccount", "toAccount.invalid", "Please select an account to transfer to.");
    }

}
