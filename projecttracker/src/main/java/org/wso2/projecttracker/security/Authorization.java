package org.wso2.projecttracker.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.wso2.projecttracker.bean.User;
import org.wso2.projecttracker.dao.UserDAO;
import org.wso2.projecttracker.daoImpl.UserDAOImpl;

public class Authorization {

	public static String valid(User loginUser)
			throws UnsupportedEncodingException, NoSuchAlgorithmException {
		String username = loginUser.getUsername();
		String password = loginUser.getPassword();

		UserDAO userDAO = new UserDAOImpl();
		User user = userDAO.getUserByUsername(username);

		if (user != null && username.equals(user.getUsername())
				&& encrypt(password).equals(user.getPassword())) {
			String code = nextSessionId();
			String token = encrypt(username + password + code);
			userDAO.setToken(token, user.getUserId());
			return code+":"+user.getRole();
		}
		return "auth_fail";
	}

    public synchronized static String encrypt(String plaintext)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
                MessageDigest md = MessageDigest.getInstance("SHA1");
                md.reset();
                byte[] buffer = plaintext.getBytes();
                md.update(buffer);
                byte[] digest = md.digest();

                String hexStr = "";
                for (int i = 0; i < digest.length; i++) {
                    hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
                }
                return hexStr;
    }

	private static SecureRandom random = new SecureRandom();

	public static String nextSessionId() {
		return new BigInteger(130, random).toString(32);
	}

	public static int getuserlevel(String userToken) {
		UserDAO userDAO = new UserDAOImpl();
		User user = userDAO.getUserByToken(userToken);

		String token = user.getToken();

		if (userToken.equals(token)) {
			if (user.getRole().equals("admin")) {
				return 2;
			} else {
				return 1;
			}
		}
		return 0;
	}
}
