package org.hibernate.annotations;

/**
 * Created by kaustubh on 7/21/16.
 */

import com.hibernate.myexperiments.custom.convertor.SQLConverterWithSchema;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface CustomFormula {
    /**
     * The formula string.
     */
    Class<? extends Object> convertor() default SQLConverterWithSchema.class;
    String value();
}
