package com.lyl.pojo;

import java.math.BigDecimal;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2022/10/27
 */

public class AuctionrecordDo extends Auction {
	private BigDecimal auctionprice;
	private String username;

	public BigDecimal getAuctionprice() {
		return auctionprice;
	}

	public void setAuctionprice(BigDecimal auctionprice) {
		this.auctionprice = auctionprice;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
