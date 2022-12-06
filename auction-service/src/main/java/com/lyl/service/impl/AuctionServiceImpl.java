package com.lyl.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyl.exception.recordException;
import com.lyl.mapper.AuctionDetailMapper;
import com.lyl.mapper.AuctionMapper;
import com.lyl.mapper.AuctionrecordMapper;
import com.lyl.pojo.*;
import com.lyl.service.AuctionService;
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
public class AuctionServiceImpl implements AuctionService {

	@Autowired
	private AuctionMapper am;

	@Autowired
	AuctionrecordMapper recordMapper;

	@Autowired
	AuctionDetailMapper auctionDetailMapper;


	@Override
	public void addAuction(Auction auction) {
		am.insert(auction);
	}

	@Override
	public List<Auction> findIngAuctions() {
		return auctionDetailMapper.findIngAuctions();
	}

	@Override
	public List<AuctionrecordDo> findEndAuctionDetailr() {
		return auctionDetailMapper.findEndAuctionDetailr();
	}

	@Override
	public void addAuctionRecord(Auctionrecord auctionrecord) throws Exception {
		Auction auction = auctionDetailMapper.findAuctionDetailById(auctionrecord.getAuctionid());

		//判断竞拍价格的合理性
		if (auctionrecord.getAuctionprice() == null) {
			throw new recordException("出价错误");
		}

		//要在竞拍规定时间内
		if (!auction.getAuctionstarttime().before(auctionrecord.getAuctiontime())) {
			throw new recordException("未到竞价时间");
		}
		if (!auction.getAuctionendtime().after(auctionrecord.getAuctiontime())) {
			throw new recordException("竞拍时间已过");
		}

		//如果没有竞拍记录，要高于起拍价
		if (auction.getRecordList() != null && auction.getRecordList().size() > 0) {
			//有竞拍记录则需要高于第一条竞拍记录价格。竞拍记录已按价格排好序
			if (auction.getRecordList().get(0).getAuctionprice().compareTo(auctionrecord.getAuctionprice()) >= 0) {
				throw new recordException("要高于最高竞价");
			}
		} else {
			//没有竞拍记录，为第一次出价，需要高于起拍价
			if (auction.getAuctionstartprice().compareTo(auctionrecord.getAuctionprice()) >= 0) {
				throw new recordException("要高于起拍价");
			}
		}

		//合理竞价添加至数据库
		recordMapper.insert(auctionrecord);

	}

	@Override
	public Auction findAuctionDetailById(Integer id) {
		return auctionDetailMapper.findAuctionDetailById(id);
	}

	@Override
	public Result selectAuctions(int pageNum, int pageSize, Auction auction) {
		PageHelper.startPage(pageNum, pageSize);
		List<Auction> auctions = selectAuctions(auction);
		PageInfo pageInfo = new PageInfo(auctions);

		Result result = new Result();
		result.setData(auctions);
		result.setPageInfo(pageInfo);
		return result;
	}

	@Override
	public void deleteAutionById(Integer id) {
		//要将Auction在两个表的信息删除
		AuctionrecordExample auctionrecordExample = new AuctionrecordExample();
		AuctionrecordExample.Criteria criteria = auctionrecordExample.createCriteria();
		criteria.andAuctionidEqualTo(id);
		recordMapper.deleteByExample(auctionrecordExample);

		am.deleteByPrimaryKey(id);

	}

	@Override
	public void updateAuction(Auction auction) {
		am.updateByPrimaryKey(auction);
	}

	@Override
	public Auction selectAuctionById(Integer id) {
		return am.selectByPrimaryKey(id);
	}

	@Override
	public List<Auction> selectAuctions(Auction auction) {
		AuctionExample auctionExample = new AuctionExample();
		AuctionExample.Criteria criteria = auctionExample.createCriteria();

		if (auction != null) {
			if (auction.getAuctionname() != null && !"".equals(auction.getAuctionname())) {
				criteria.andAuctionnameLike("%" + auction.getAuctionname() + "%");
			}
			if (auction.getAuctiondesc() != null && !"".equals(auction.getAuctiondesc())) {
				criteria.andAuctiondescLike("%" + auction.getAuctiondesc() + "%");
			}
			if (auction.getAuctionstarttime() != null) {
				criteria.andAuctionstarttimeGreaterThan(auction.getAuctionstarttime());
			}
			if (auction.getAuctionendtime() != null) {
				criteria.andAuctionendtimeGreaterThan(auction.getAuctionendtime());
			}
			if (auction.getAuctionstartprice() != null) {
				criteria.andAuctionstartpriceGreaterThan(auction.getAuctionstartprice());
			}
		}

		auctionExample.setOrderByClause("auctionstarttime desc");
		return am.selectByExample(auctionExample);
	}

}
