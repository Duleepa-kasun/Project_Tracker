package org.wso2.projecttracker.bean;

import javax.xml.bind.annotation.XmlRootElement;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@XmlRootElement
public class User {
    private Integer userId;
    private String name;
    private String username;
    private String password;
    private String email;
    private String role;
    private String token;
    
    

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setRole(String role) {
        this.role = role;
    }

    public void hashPassword(){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            byte byteData[] = md.digest();
            md.update(this.getPassword().getBytes());

            //convert the byte to hex format method 2
            StringBuffer hexString = new StringBuffer();
            for (int i=0;i<byteData.length;i++) {
                String hex=Integer.toHexString(0xff & byteData[i]);
                if(hex.length()==1) hexString.append('0');
                hexString.append(hex);
            }

            this.setPassword(hexString.toString());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
