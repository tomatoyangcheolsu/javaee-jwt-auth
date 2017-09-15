package authentication;

import java.security.Key;

public interface SecretKeyService {

	/**
	 * 暗号化キーを取得する。
	 * @return　暗号化キー
	 */
	Key getKey();

}
