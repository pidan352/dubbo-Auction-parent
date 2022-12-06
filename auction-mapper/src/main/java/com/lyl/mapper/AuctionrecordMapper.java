package com.lyl.mapper;


import com.lyl.pojo.Auctionrecord;
import com.lyl.pojo.AuctionrecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuctionrecordMapper {
    int countByExample(AuctionrecordExample example);

    int deleteByExample(AuctionrecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Auctionrecord record);

    int insertSelective(Auctionrecord record);

    List<Auctionrecord> selectByExample(AuctionrecordExample example);

    Auctionrecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Auctionrecord record, @Param("example") AuctionrecordExample example);

    int updateByExample(@Param("record") Auctionrecord record, @Param("example") AuctionrecordExample example);

    int updateByPrimaryKeySelective(Auctionrecord record);

    int updateByPrimaryKey(Auctionrecord record);
}