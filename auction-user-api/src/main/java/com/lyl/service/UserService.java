package com.lyl.service;


import com.lyl.pojo.Auctionuser;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2022/10/25
 */

public interface UserService {
	Auctionuser findUserByUsername(String username);

	void addUser(Auctionuser user);

	boolean checkUsername(String username);
}
