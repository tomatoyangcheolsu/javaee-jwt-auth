package authentication;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.mockito.Spy;

public class EchoResourceTest {

	@Spy
	private EchoResource echoResource = new EchoResource();
	
	private static final String MSG_DEFAULT = "no message";
	private static final String MSG_SAMPLE = "sample text";
	
	@Test
	public void testEcho_nullMsg() {
		Response response = echoResource.echo(null);
		assertThat(response.getEntity(), is(MSG_DEFAULT));
	}
	
	@Test
	public void testEcho_hasMsg() {
		Response response = echoResource.echo(MSG_SAMPLE);
		assertThat(response.getEntity(), is(MSG_SAMPLE));
	}
	
	@Test
	public void testEchoWithJWTToken_nullMsg() {
		Response response = echoResource.echoWithJWTToken(null);
		assertThat(response.getEntity(), is(MSG_DEFAULT));
	}
	
	@Test
	public void testEchoWithJWTToken_hasMsg() {
		Response response = echoResource.echoWithJWTToken(MSG_SAMPLE);
		assertThat(response.getEntity(), is(MSG_SAMPLE));
	}
}
