package com.authority.schedule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * controller基本测试类
 * 
 * @author Administrator
 * @date 2011-12-7 下午4:17:51
 */
@ContextConfiguration(locations = { "classpath:/config/spring/spring*.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class ScheduleTest {

	@Test
	public void testSimple() throws Exception {
		System.out.println("schedule");
	}

}
