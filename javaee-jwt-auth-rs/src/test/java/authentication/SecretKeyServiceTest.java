package authentication;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Key;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SecretKeyServiceTest {

	@Mock
	private Key key;
	
	@Mock
	private SecretKeyService secretKeyService;
	
	@Before
	public void setUp() {
		when(secretKeyService.getKey()).thenReturn(key);
	}
	
	@Test
	public void testGetKey() {
		Key key = secretKeyService.getKey();
		
		verify(secretKeyService).getKey();
		assertThat(key, is(this.key));
	}
}
