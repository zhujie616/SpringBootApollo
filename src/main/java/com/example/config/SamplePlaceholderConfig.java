package com.example.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * placeholder方式自动更新 @value注入的配置
 * 
 * @author zhujiejie@gongniu.cn
 */
@Component
public class SamplePlaceholderConfig {

	@Value("${username}")
	private String username;
	@Value("${password}")
	private String password;

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

	@Override
	public String toString() {
		return "SamplePlaceholderConfig [username=" + username + ", password=" + password + "]";
	}
}
