package cf.pies.serialize.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used on any fields that should be serialized with the object.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SerializeField {
}
