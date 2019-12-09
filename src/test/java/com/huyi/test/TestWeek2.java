package com.huyi.test;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.huyi.User;
import com.utils.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:redis.xml")
public class TestWeek2 {

	@SuppressWarnings("rawtypes")
	@Autowired
	RedisTemplate redisTemplate;
	
	//模拟生成5万条User对象
	@Test
	public void testUser(){
		for (int i = 0; i < 50000; i++) {
			User user = new User();
			
			//ID使用1-5万的顺序号
			user.setId(i);
			//姓名使用3个随机汉字模拟
			user.setName(StringUtil.getRandomChinese(3));
			//性别在女和男两个值中随机
			user.setGender(StringUtil.getSex());
			//邮箱以3-20个随机字母
			user.setEmail(StringUtil.getMail());
			//生日要模拟18-70岁之间，即日期从1949年到2001年之间
			user.setBirthday(StringUtil.getBirthday());
			System.out.println(user);
		}
	}
	
	//使用JDK系列化方式保存5万个user随机对象到Redis，并计算耗时
	@SuppressWarnings("unchecked")
	@Test
	public void testJDK(){
		long start = System.currentTimeMillis();
		for (int i = 0; i < 50000; i++) {
			User user = new User();
			
			//ID使用1-5万的顺序号
			user.setId(i);
			//姓名使用3个随机汉字模拟
			user.setName(StringUtil.getRandomChinese(3));
			//性别在女和男两个值中随机
			user.setGender(StringUtil.getSex());
			//邮箱以3-20个随机字母
			user.setEmail(StringUtil.getMail());
			//生日要模拟18-70岁之间，即日期从1949年到2001年之间
			user.setBirthday(StringUtil.getBirthday());
			
			redisTemplate.opsForValue().set("user"+i, user);
		}
		
		long end = System.currentTimeMillis();
		System.out.println("使用了JDK系列化方式");
		System.out.println("保存了五万条数据");
		System.out.println("使用了"+(end-start)+"毫秒");
	}
	
	
	//使用JSON系列化方式保存5万个user随机对象到Redis，并计算耗时
	@SuppressWarnings("unchecked")
	@Test
	public void testJSON(){
		long start = System.currentTimeMillis();
		for (int i = 0; i < 50000; i++) {
			User user = new User();
			
			//ID使用1-5万的顺序号
			user.setId(i);
			//姓名使用3个随机汉字模拟
			user.setName(StringUtil.getRandomChinese(3));
			//性别在女和男两个值中随机
			user.setGender(StringUtil.getSex());
			//邮箱以3-20个随机字母
			user.setEmail(StringUtil.getMail());
			//生日要模拟18-70岁之间，即日期从1949年到2001年之间
			user.setBirthday(StringUtil.getBirthday());
			
			redisTemplate.opsForValue().set("user"+i, user);
		}
		
		long end = System.currentTimeMillis();
		System.out.println("使用了JSON系列化方式");
		System.out.println("保存了五万条数据");
		System.out.println("使用了"+(end-start)+"毫秒");
	}
		
	
	//使用Redis的Hash类型保存5万个user随机对象到Redis，并计算耗时
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void testHash(){
		long start = System.currentTimeMillis();
		HashMap<String,User> map = new HashMap<String,User>();
		for (int i = 0; i < 50000; i++) {
			User user = new User();
			
			//ID使用1-5万的顺序号
			user.setId(i);
			//姓名使用3个随机汉字模拟
			user.setName(StringUtil.getRandomChinese(3));
			//性别在女和男两个值中随机
			user.setGender(StringUtil.getSex());
			//邮箱以3-20个随机字母
			user.setEmail(StringUtil.getMail());
			//生日要模拟18-70岁之间，即日期从1949年到2001年之间
			user.setBirthday(StringUtil.getBirthday());
			map.put("user"+i, user);
		}
		redisTemplate.opsForHash().putAll("users", map);
		long end = System.currentTimeMillis();
		System.out.println("使用了hash系列化方式");
		System.out.println("保存了五万条数据");
		System.out.println("使用了"+(end-start)+"毫秒");
	}
	
	
	//对数据进行测试
	public static void main(String[] args) {
		System.out.println(StringUtil.getBirthday());
	}
}
