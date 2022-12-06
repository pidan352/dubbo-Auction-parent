package com.lyl.mapper;


import com.lyl.pojo.Auction;
import com.lyl.pojo.AuctionrecordDo;

import java.util.List;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2022/10/26
 */

public interface AuctionDetailMapper {

	Auction findAuctionDetailById(Integer id);

	List<AuctionrecordDo> findEndAuctionDetailr();

	List<Auction> findIngAuctions();
}
