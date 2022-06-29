package com.example.demo.app.config;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * バリデート用
 *
 */
public class ConfirmValidator implements ConstraintValidator<Confirm, Object> {

	private String field;
	private String fieldConfirm;
	private String message;
	
	@Override
	public void initialize(Confirm constraintAnnotation) {
		// TODO 初期化
		field = constraintAnnotation.field();
		fieldConfirm = "confirm" + StringUtils.capitalize(field);
		message = constraintAnnotation.message();
	}
	
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		// TODO 自動生成されたメソッド・スタブ
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);
		Object field1Value = beanWrapper.getPropertyValue(field);
		Object confirmFieldValue = beanWrapper.getPropertyValue(fieldConfirm);
        boolean matched = ObjectUtils.nullSafeEquals(field1Value,
                confirmFieldValue);
        if (matched) {
            return true;
        } else {
            context.disableDefaultConstraintViolation(); // (3)
            context.buildConstraintViolationWithTemplate(message)
                    .addNode(field).addConstraintViolation(); // (4)
            return false;
        }
	}

}
