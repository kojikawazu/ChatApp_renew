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

	/** 値A */
	private String field;
	
	/** 値B */
	private String fieldConfirm;
	
	/** メッセージ */
	private String message;
	
	/**
	 * 初期化
	 * @param constraintAnnotation
	 */
	@Override
	public void initialize(Confirm constraintAnnotation) {
		// 初期化
		field = constraintAnnotation.field();
		fieldConfirm = "confirm" + StringUtils.capitalize(field);
		message = constraintAnnotation.message();
	}
	
	/**
	 * バリデート処理
	 * @param value
	 * @param context
	 */
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		// バリデート処理
		BeanWrapper beanWrapper = new BeanWrapperImpl(value);
		Object field1Value = beanWrapper.getPropertyValue(field);
		Object confirmFieldValue = beanWrapper.getPropertyValue(fieldConfirm);
        
		// マッチング
		boolean matched = ObjectUtils.nullSafeEquals(field1Value,
                confirmFieldValue);
        if (matched) {
        	// 一致
            return true;
        } else {
        	// 不一致
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
            		//.addConstraintViolation();
                    .addNode(field).addConstraintViolation();
            return false;
        }
	}

}
