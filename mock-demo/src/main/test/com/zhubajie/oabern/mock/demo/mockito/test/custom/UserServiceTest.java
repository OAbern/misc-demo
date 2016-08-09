package com.zhubajie.oabern.mock.demo.mockito.test.custom;

import com.zhubajie.oabern.mock.demo.dal.UserDao;
import com.zhubajie.oabern.mock.demo.entity.User;
import com.zhubajie.oabern.mock.demo.service.UserService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

// use 'import static' import Mockito's static method
import static org.mockito.Mockito.*;

import static org.mockito.BDDMockito.*;

import static org.junit.Assert.*;

/**
 * Created by fengdi on 2016/8/9.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    UserDao userDaoMock;

    /**
     * @InjectMocks 不会将userServiceSpy映射成真正的mock对象，因此 verify 交互行为(interactions)时，会报错！
     * @Spy 可以将userServiceSpy包装成一个代理对象，这样可以 verify 对象的交互行为(interactions)
     */
    @InjectMocks
    @Spy
    UserService userServiceSpy;

    @Mock
    User userMock;

    @BeforeClass
    public static void setUp() {

    }

    @Test
    public void testAddUserFail() {
        // arrage
        final boolean EXPECTED = false;
        given(userDaoMock.findUserByUserName(anyString())).willReturn(userMock);  // 找到同名用户
        given(userDaoMock.addUser(isA(User.class))).willReturn(true);

        // act
        boolean actual = userServiceSpy.addUser(userMock);

        // assert
        assertEquals(EXPECTED, actual);
    }

    @Test
    public void testAddUserSuccess() {
        // arrage
        final boolean EXPECTED = true;
        given(userDaoMock.findUserByUserName(anyString())).willReturn(null);    // 没有同名用户
        given(userDaoMock.addUser(isA(User.class))).willReturn(EXPECTED);

        // act
        boolean actual = userServiceSpy.addUser(userMock);

        // assert
        verify(userServiceSpy).addUser(isA(User.class));
        assertEquals(EXPECTED, actual);
    }
}
