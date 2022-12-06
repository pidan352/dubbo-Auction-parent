package com.lyl.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.lyl.mapper.AuctionuserMapper;
import com.lyl.pojo.Auctionuser;
import com.lyl.pojo.AuctionuserExample;
import com.lyl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2022/10/25
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private AuctionuserMapper am;

	@Override
	public boolean checkUsername(String username) {
		AuctionuserExample example = new AuctionuserExample();
		AuctionuserExample.Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<Auctionuser> list = am.selectByExample(example);
		if (list != null && list.size() > 0) {
			return false;
		}
		return true;
	}

	@Override
	public void addUser(Auctionuser user) {
		user.setUserisadmin(0);
		am.insertSelective(user);
	}

	@Override
	public Auctionuser findUserByUsername(String username) {
		AuctionuserExample example = new AuctionuserExample();
		AuctionuserExample.Criteria criteria = example.createCriteria();
		criteria.andUsernameEqualTo(username);
		List<Auctionuser> list = am.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
