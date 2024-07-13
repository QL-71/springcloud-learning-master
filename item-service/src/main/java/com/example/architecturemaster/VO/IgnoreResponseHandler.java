package com.example.architecturemaster.VO;

import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.ANNOTATION_TYPE, ElementType.METHOD,ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreResponseHandler {

}
