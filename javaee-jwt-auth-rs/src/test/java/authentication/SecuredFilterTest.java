package authentication;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Key;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

@RunWith(MockitoJUnitRunner.class)
public class SecuredFilterTest {
	
	@InjectMocks
	private SecuredFilter securedFilter;
	
	@Spy
	private SecretKeyService secretKeyService = new SecretKeyServiceImpl();
	
	private ContainerRequestContext requestContext;
	
	@Before
	public void setUp() {
		requestContext = mock(ContainerRequestContext.class);
	}
	
	@Test
	public void testFilter_authorized() throws Exception {
		// 正当な暗号化キーの場合
		Key key = secretKeyService.getKey();
		
		String token = Jwts.builder().setSubject("subj").signWith(SignatureAlgorithm.HS512, key).compact();
		when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);	
		
		securedFilter.filter(requestContext);
		verify(requestContext, times(0)).abortWith(any());
	}
	
	@Test
	public void testFilter_unauthorized() throws Exception {
		// 偽造された暗号化キーの場合
		// この場合、余所で生成されたキーを想定する。（issueのセキュリティーチェックは行っていない。）
		Key key = MacProvider.generateKey();
		
		String token = Jwts.builder().setSubject("subj").signWith(SignatureAlgorithm.HS512, key).compact();
		when(requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer " + token);	
		
		securedFilter.filter(requestContext);
		verify(requestContext).abortWith(any());
	}
}
