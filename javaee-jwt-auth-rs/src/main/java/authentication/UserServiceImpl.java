package authentication;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserServiceImpl implements UserService {

	/**
	 * 注意! 認証処理は必ず成功する。
	 * @param login ログイン資格名
	 * @param password　パスワード
	 * @throws Exception 認証処理の例外
	 */
	@Override
	public void authenticate(String login, String password) throws Exception {
		// TODO 処理の詳細を記述すべき
	}
}
