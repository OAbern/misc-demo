import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import com.kingdee.bern.bean.User;
import com.kingdee.bern.dao1.UserDao1;
import com.kingdee.bern.dao2.UserDao2;

public class UserDaoTest {
		private static UserDao1 userDao1;
		private static UserDao2 userDao2;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext context = new FileSystemXmlApplicationContext("resources/spring-mybatis.xml");
		Map<String, UserDao1> map = context.getBeansOfType(UserDao1.class);
		userDao1 = map.get("userDao1");
		
		Map<String, UserDao2> map2 = context.getBeansOfType(UserDao2.class);
		userDao2 = map2.get("userDao2");
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void test() {
		User user1 = userDao1.findUser(1);
		User user2 = userDao2.findUser(2);
		assertEquals(user1.getId(), 1);
		assertEquals(user2.getId(), 2);
	}

}
