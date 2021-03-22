package com.lrm;

import com.lrm.dao.BlogRepository;
import com.lrm.dao.ContentRepository;
import com.lrm.dao.UserRepository;
import com.lrm.po.Blog;
import com.lrm.po.Content;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ContentRepository contentRepository;


	@Autowired
	private BlogRepository blogRepository;



	@Test
	public void contextLoads() {

	}


	@Test
	public void select(){
		//获得博客内容的ID
		List<String> contents =
				contentRepository.findByContentLike("Spring").stream().map(Content::getId).distinct().collect(Collectors.toList());
		//获取所有的博客
		blogRepository.findByContentIdIn(contents).stream().map(Blog::getId).forEach(System.out::println);


	}


}
