package authentication;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.net.URI;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@RunWith(MockitoJUnitRunner.class)
public class UserResourceTest {

	// JAX-RSリソースをテストするために、grizzlyなどのJAX-RSプロバイダーが必要になる。
	
	@InjectMocks
	private UserResource userResource = new UserResource();
	
	@Mock
	private UriInfo uriInfo;
	
	@Spy
	private SecretKeyService secretKeyService = new SecretKeyServiceImpl();
	
	@Before
	public void setUp() {
		// 適当なURLを割り当てる。
		when(uriInfo.getAbsolutePath()).thenReturn(URI.create("/test"));
	}
	
	@Test
	public void testJwtAuthentication() {
		Response response = userResource.authenticateUser("a", "b");
		
		String authorization = response.getHeaderString(HttpHeaders.AUTHORIZATION);
		
		String token = authorization.substring("Bearer".length()).trim();
		System.out.println(token);
		
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKeyService.getKey()).parseClaimsJws(token);
			System.out.println(claims);
			assertTrue(true);
		} catch (Exception e) {
			fail("認証失敗!");
		}
	}
}
