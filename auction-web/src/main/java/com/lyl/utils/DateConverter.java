package com.lyl.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateConverter implements Converter<String, Date> {
	SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy/MM/dd");
	SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy年MM月dd日");

	@Override
	public Date convert(String time) {
		try {
			return sdf1.parse(time);
		} catch (ParseException e) {
			try {
				return sdf2.parse(time);
			} catch (ParseException e1) {
				try {
					return sdf3.parse(time);
				} catch (ParseException e2) {
				}
			}
		}
		 return null; //表示 springmvc可以接收null值 ，不报异常，程序继续，不会终止
//		throw new IllegalArgumentException(); // 类型转换失败，报400错误，程序终止
	}
}
