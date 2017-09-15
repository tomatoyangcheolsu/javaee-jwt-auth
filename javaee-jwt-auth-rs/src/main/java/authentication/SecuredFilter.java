package authentication;

import java.io.IOException;
import java.security.Key;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Jwts;

/**
 * セキュリティーチェックが必要なRestfulリソースまたはメソッドを呼び出す前に、
 * セキュリティーチェックを行うための処理
 */
@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class SecuredFilter implements ContainerRequestFilter {
	private final Logger logger = LoggerFactory.getLogger(SecuredFilter.class);
	
	@Inject
	private SecretKeyService secretKeyService;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		
		if (authorizationHeader == null) {
			// ヘッダーに認証情報が存在しない場合、401 Unauthorizedを出力する。
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
			return;
		}
		
		String token = authorizationHeader.substring("Bearer".length()).trim();
		
		try {
			Key key = secretKeyService.getKey();
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			// トークンが有効な場合、認可される。
			logger.info("#### valid token : {}", token);
		} catch (Exception e) {
			// トークンが無効な場合、例外が発生するため、処理を中断させ401 Unauthorizedを返却すればいい。
			logger.error("#### invalid token : {}", token);
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
	}

}
