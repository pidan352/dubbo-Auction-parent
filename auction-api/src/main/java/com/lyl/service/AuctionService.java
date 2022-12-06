package com.lyl.service;


import com.lyl.pojo.Auction;
import com.lyl.pojo.Auctionrecord;
import com.lyl.pojo.AuctionrecordDo;
import com.lyl.pojo.Result;

import java.util.List;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2022/10/25
 */

public interface AuctionService {
	List<Auction> selectAuctions(Auction auction);

	Result selectAuctions(int pageNum, int pageSize, Auction auction);

	void addAuction(Auction auction);

	Auction selectAuctionById(Integer id);

	void updateAuction(Auction auction);

	void deleteAutionById(Integer id);

	Auction findAuctionDetailById(Integer id);

	void addAuctionRecord(Auctionrecord auctionrecord) throws Exception;

	List<AuctionrecordDo> findEndAuctionDetailr();

	List<Auction> findIngAuctions();
}
