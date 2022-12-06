package com.lyl.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.lyl.pojo.*;
import com.lyl.service.AuctionService;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 功能：
 *
 * @author 林亦亮
 * @version 1.0
 * @date 2022/10/25
 */

@Controller
public class AuctionController {

	@Reference
	private AuctionService as;


	private static final int PAGE_SIZE = 15;


	@RequestMapping("/queryAuctions")
	public ModelAndView queryAuctions(
			@ModelAttribute("condition") Auction auction,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum) {
		ModelAndView mv = new ModelAndView();

		//PageHelper.startPage(pageNum, PAGE_SIZE);

		Result result = as.selectAuctions(pageNum, PAGE_SIZE, auction);
		//PageInfo pageInfo = new PageInfo(list);

		Auctionuser user = (Auctionuser) SecurityUtils.getSubject().getPrincipal();


		mv.addObject("user", user);
		mv.addObject("page", result.getPageInfo());
		mv.addObject("auctionList", result.getData());

		mv.setViewName("index");
		return mv;
	}

	@RequestMapping("/publishAuctions")
	public String publishAuctions(@Validated @ModelAttribute("auctionkk") Auction auction, BindingResult bindingResult,
								  HttpSession session, MultipartFile pic, Model model, HttpServletRequest request) {
		//有错误，将信息一同回到页面
		if (bindingResult.hasErrors()) {
			//获取所有错误信息
			List<FieldError> errorList = bindingResult.getFieldErrors();
			for (FieldError error : errorList) {
				//返回字段名对应的错误,方便于做错误信息的显示
				model.addAttribute(error.getField(), error.getDefaultMessage());
			}
			return "addAuction";
		}


		//先处理上传的文件
		try {
			String path = "e:\\upload";
			System.err.println(path);

			File file = new File(path, pic.getOriginalFilename());
			pic.transferTo(file);

			auction.setAuctionpic(pic.getOriginalFilename());
			auction.setAuctionpictype(pic.getContentType());
		} catch (IOException e) {
			e.printStackTrace();
		}

		//将拍品信息存入数据库
		as.addAuction(auction);

		return "redirect:/queryAuctions";
	}


	@RequestMapping("/toUpdate")
	public ModelAndView toUpdate(Integer id) {
		ModelAndView mv = new ModelAndView();

		Auction auction = as.selectAuctionById(id);

		mv.addObject("auction", auction);
		mv.setViewName("updateAuction");
		return mv;
	}

	@RequestMapping("/toDelete")
	public ModelAndView toDelete(Integer id) {
		ModelAndView mv = new ModelAndView();

		Auction auction = as.selectAuctionById(id);

		as.deleteAutionById(id);

		mv.setViewName("redirect:/queryAuctions");
		return mv;
	}


	@RequestMapping("/submitUpdateAuction")
	public ModelAndView submitUpdateAuction(@ModelAttribute @Validated Auction auction, BindingResult bindingResult,
											Model model, HttpSession session, MultipartFile pic) {
		ModelAndView mv = new ModelAndView();

		//有错误，将信息一同回到页面
		if (bindingResult.hasErrors()) {
			//获取所有错误信息
			List<FieldError> errorList = bindingResult.getFieldErrors();
			for (FieldError error : errorList) {
				//返回字段名对应的错误,方便于做错误信息的显示
				model.addAttribute(error.getField(), error.getDefaultMessage());
			}
			mv.setViewName("updateAuction");
			return mv;
		}


		//处理图片
		try {
			//删除原来的图片
			if (pic.getSize() > 0) {
				String path = session.getServletContext().getRealPath("upload");

				File oldfile = new File(path, auction.getAuctionpic());
				if (oldfile.exists()) {
					oldfile.delete();
				}

				File file = new File(path, pic.getOriginalFilename());
				pic.transferTo(file);

				auction.setAuctionpic(pic.getOriginalFilename());
				auction.setAuctionpictype(pic.getContentType());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		//更改数据库信息
		as.updateAuction(auction);

		mv.setViewName("redirect:/queryAuctions");
		return mv;
	}


	@RequestMapping("/toAuctionDetail")
	public ModelAndView toAuctionDetail(Integer id) {
		ModelAndView mv = new ModelAndView();

		Auction auction = as.findAuctionDetailById(id);

		mv.addObject("auctionDetail", auction);
		mv.setViewName("auctionDetail");

		return mv;
	}


	@RequestMapping("/saveAuctionRecord")
	public ModelAndView saveAuctionRecord(Auctionrecord auctionrecord, HttpSession session) throws Exception {
		ModelAndView mv = new ModelAndView();

		Auctionuser user = (Auctionuser) SecurityUtils.getSubject().getPrincipal();

		auctionrecord.setAuctiontime(new Date());
		auctionrecord.setUserid(user.getUserid());
		as.addAuctionRecord(auctionrecord);

		mv.setViewName("redirect:/toAuctionDetail?id=" + auctionrecord.getAuctionid());

		return mv;
	}


	@RequestMapping("/torecordResult")
	public ModelAndView torecordResult() {
		ModelAndView mv = new ModelAndView();

		List<AuctionrecordDo> endAuction = as.findEndAuctionDetailr();
		List<Auction> IngAuction = as.findIngAuctions();


		mv.addObject("endAuction", endAuction);
		mv.addObject("IngAuction", IngAuction);


		mv.setViewName("recordResult");

		return mv;
	}


}
