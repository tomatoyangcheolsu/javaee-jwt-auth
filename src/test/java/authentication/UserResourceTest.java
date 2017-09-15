package authentication;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Objects;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

	// JAX-RSリソースをテストするために、grizzlyなどのJAX-RSプロバイダーが必要になる。
	
	@InjectMocks
	private UserResource userResource = new UserResource();
	
	@Mock
	private UriInfo uriInfo;
	
	@Spy
	private SecretKeyService secretKeyService = new SecretKeyServiceImpl();
	
	@Mock
	private UserService userService;
	
	private AuthenticateAnswer authenticateAnswer = new AuthenticateAnswer();
	
	@Before
	public void setUp() throws Exception {
		// 適当なURLを割り当てる。
		when(uriInfo.getAbsolutePath()).thenReturn(URI.create("/test"));
		
		doAnswer(authenticateAnswer).when(userService).authenticate(anyString(), anyString());
	}
	
	@Test
	public void testAuthenticateUser_ok() throws Exception {
		Response response = userResource.authenticateUser("user", "123456");
		verify(userService).authenticate("user", "123456");
		assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
	}
	
	
	@Test
	public void testJwtAuthentication_unauthorized() throws Exception {
		Response response = userResource.authenticateUser("user", "000000");
		assertThat(response.getStatus(), is(Response.Status.UNAUTHORIZED.getStatusCode()));
	}
	
	/**
	 * 認証の答案クラス<br>
	 * login=user&password=123456の場合のみ成功する。
	 * @throws Exception
	 */
	private static class AuthenticateAnswer implements Answer<Void> {

		@Override
		public Void answer(InvocationOnMock invocation) throws Throwable {
			String login    = invocation.getArgumentAt(0, String.class);
			String password = invocation.getArgumentAt(1, String.class);
			
			if (Objects.equals(login, "user") && Objects.equals(password, "123456")) {
				// 何もしない。
			} else {
				throw new Exception("Unauthorized!");
			}
			
			return null;
		}
		
	}
}
