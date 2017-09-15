package authentication;

import static java.lang.annotation.RetentionPolicy.*;
import static java.lang.annotation.ElementType.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * この注釈をRestfulリソースやメソッドに宣言するだけで、
 * セキュリティーチェックを行うフィルターに連携可能である。
 */
@javax.ws.rs.NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface Secured {

}
