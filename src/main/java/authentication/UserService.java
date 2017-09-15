package authentication;

public interface UserService {
	
	/**
	 * パスワードベースの認証処理
	 * @param login ログイン資格名
	 * @param password　パスワード
	 * @throws Exception 認証処理の例外
	 */
	void authenticate(String login, String password) throws Exception;
}
