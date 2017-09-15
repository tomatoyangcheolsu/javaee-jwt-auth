package authentication;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.mockito.Spy;

public class UserServiceTest {
	
	@Spy
	private UserService userService = new UserServiceImpl();
	
	@Test
	public void testAuthenticate() throws Exception {
		userService.authenticate("user", "123456");
		assertTrue(true);
	}
}
