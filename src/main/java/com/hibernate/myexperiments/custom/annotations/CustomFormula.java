package com.hibernate.myexperiments.custom.annotations;

/**
 * Created by kaustubh on 7/21/16.
 */

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
    String value();
}
