
package com.mnt.preg.web.anoton;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FrontCache {

    String space() default "default";

    // 当值为jsp时 前面加 /page/ 后面加 .jsp
    String type() default "string";
}
