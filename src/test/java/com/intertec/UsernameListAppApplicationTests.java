package com.intertec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsernameListAppApplicationTests {

	private static final Log LOG = LogFactory.getLog(UsernameListAppApplicationTests.class);

	@Test
	public void test() { LOG.info("Test UsernameListAppApplicationTests..."); }

}
