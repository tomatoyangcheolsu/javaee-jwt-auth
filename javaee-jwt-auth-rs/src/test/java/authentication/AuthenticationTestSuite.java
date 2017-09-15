package authentication;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	SecuredFilterTest.class,
	SecretKeyServiceTest.class,
	UserServiceTest.class,
	UserResourceTest.class,
	EchoResourceTest.class
})
public class AuthenticationTestSuite {

}
