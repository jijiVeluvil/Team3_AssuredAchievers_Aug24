
package DieticianPojo;

import lombok.Data;


public class UserLogin {

	private String password;
	private String userLoginEmail;

	public UserLogin(String userLoginEmail, String password) {
		this.userLoginEmail = userLoginEmail;
		this.password = password;
	}

}