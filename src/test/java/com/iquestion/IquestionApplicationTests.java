package com.iquestion;

import com.iquestion.mapper.QuestionMapper;
import com.iquestion.mapper.UserMapper;
import com.iquestion.pojo.Question;
import com.iquestion.pojo.User;
import com.iquestion.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IquestionApplicationTests {


	@Autowired
	UserMapper userMapper;

	@Autowired
	QuestionMapper questionMapper;

//	@Test
//	public void contextLoads() {
//
//		User user = new User();
//		user.setId(1);
//		user.setName("caicai");
//		user.setPassword("...");
//		user.setSalt(".l");
//		user.setHeadUrl("helel");
//
//		userMapper.insert(user);
//
//		System.out.println("hehhe");
//	}

	@Test
	public void testQuestion(){


		for(int i = 0; i < 10; i++){

			Question question = new Question();
			question.setCommentCount(0);
			question.setContent(".kfjdkgjeoigiwjagkijwegjqaweg");
			question.setCreatedDate(new Date());
			question.setTitle("政经！！！");
			question.setUserId(i);
			System.out.println("lalallll");
			questionMapper.insert(question);

		}

	}

}
