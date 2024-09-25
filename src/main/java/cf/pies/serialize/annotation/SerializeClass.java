package cf.pies.serialize.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Must be used by any class that is going to be serialized.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SerializeClass {
    int version() default 1;
}
