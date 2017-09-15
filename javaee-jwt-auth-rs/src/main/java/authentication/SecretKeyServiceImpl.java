package authentication;

import java.security.Key;

import javax.enterprise.context.ApplicationScoped;

import io.jsonwebtoken.impl.crypto.MacProvider;

@ApplicationScoped
public class SecretKeyServiceImpl implements SecretKeyService {
	
	private Key key;
	
	public SecretKeyServiceImpl() {
		// アプリケーション起動の際、任意の「HMAC + SHA512」アルゴリズムの暗号化キーが生成される。
		// @PostConstructメソッドで対応できるかもしれない。
		key = MacProvider.generateKey();
	}
	
	@Override
	public Key getKey() {
		return key;
	}
}
