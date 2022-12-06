package com.lyl.shiro;

import com.alibaba.dubbo.config.annotation.Reference;
import com.lyl.pojo.Auctionuser;
import com.lyl.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2022/11/16
 */

public class ShiroRealm extends AuthorizingRealm {

	@Reference
	private UserService userService;


	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		System.out.println(username);

		Auctionuser loginUser = null;
		try {
			loginUser = userService.findUserByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (loginUser == null) {
			return null;
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(loginUser, loginUser.getUserpassword(),
																	 "ShiroRealm");

		return info;
	}
}
