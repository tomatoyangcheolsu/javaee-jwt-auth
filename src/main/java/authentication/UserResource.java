package authentication;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	@Context
	private UriInfo uriInfo;
	
	@Inject
	private SecretKeyService secretKeyService;
	
	private final Logger logger = LoggerFactory.getLogger(UserResource.class);
	
	/**
	 * @param login ログイン資格名
	 * @param password　パスワード
	 * @return　
	 * 認証が成功した場合、200 OK ( Authorization: Bearer [トークン] )
	 * 認証が失敗した場合、401 Unauthorized
	 */
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response authenticateUser(@FormParam("login") String login,
			@FormParam("password") String password) {
		try {
			authenticate(login, password);
			
			String token = issueToken(login);
			
			// Bearer token認可を応答ヘッダに保存する。
			return Response.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + token).build();
		} catch (Exception e) {
			logger.info("Error!", e);
			return Response.status(Response.Status.UNAUTHORIZED).build();
		}
	}

	/**
	 * 注意! 認証処理は必ず成功する。
	 * @param login ログイン資格名
	 * @param password　パスワード
	 * @throws Exception 認証処理の例外
	 */
	private void authenticate(String login, String password) throws Exception{
		
	}

	/**
	 * トークンを提供する。
	 * @param login
	 * @return
	 */
	private String issueToken(String login) {
		Key key = secretKeyService.getKey();
		String jwtToken = Jwts.builder()
				.setSubject(login)
				.setIssuer(uriInfo.getAbsolutePath().toString())
				.setIssuedAt(new Date())
				.setExpiration(toDate(LocalDateTime.now().plusMinutes(60L)))
				.signWith(SignatureAlgorithm.HS512, key)
				.compact();
		return jwtToken;
	}

	/**
	 * 注意! ロケールに対応していないため、修正が必要
	 * @param localDateTime ローカル日時(java8)
	 * @return java Date
	 */
	private Date toDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.toInstant(ZoneOffset.ofHours(9)));
	}
}
