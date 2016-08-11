package com.zhubajie.oabern.mock.demo.powermock.test.official;

import com.zhubajie.oabern.mock.demo.entity.MyClass;
import com.zhubajie.oabern.mock.demo.entity.MyFinalClass;
import com.zhubajie.oabern.mock.demo.entity.StaticExample;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

// use static import to import all static method
import java.io.IOException;

import static org.junit.Assert.*;

import static org.mockito.BDDMockito.*;

import static org.powermock.api.mockito.PowerMockito.*;

/**
 * the code is based on the official demo from <a href="https://github.com/jayway/powermock/wiki/MockitoUsage">PowerMock</a>
 *
 * Created by fengdi on 2016/8/10.
 */
// the @RunWith and @PrepareForTest is required
@RunWith(PowerMockRunner.class)
@PrepareForTest({StaticExample.class, MyClass.class, MyFinalClass.class})
public class PowerMockitoTest {

    @Test
    public void testCallStaticMethod() {
        final int RETURN_VALUE = 1;
        mockStatic(StaticExample.class);
        given(StaticExample.firstStaticMethod(anyInt())).willReturn(RETURN_VALUE);

        int actual =  StaticExample.firstStaticMethod(5);

//        verifyStatic(times(2));       // it will make test fail
        verifyStatic();
        // IMPORTANT:  Call the static method you want to verify
        StaticExample.firstStaticMethod(5);

        assertEquals(RETURN_VALUE, actual);
    }

    @Test
    public void testCallStaticMethodWithArgumentMatcher() {
        final int RETURN_VALUE = 1;
        mockStatic(StaticExample.class);
        given(StaticExample.firstStaticMethod(anyInt())).willReturn(RETURN_VALUE);

        int actual =  StaticExample.firstStaticMethod(5);

        verifyStatic();
        StaticExample.firstStaticMethod(anyInt());

        verifyStatic(never());
        StaticExample.secondStaticMethod(1);

        assertEquals(RETURN_VALUE, actual);
    }

    @Test(expected = NullPointerException.class)
    public void testThrowExceptionWithVoidStaticMethod() {
        mockStatic(StaticExample.class);
        PowerMockito.doThrow(new NullPointerException()).when(StaticExample.class);
        StaticExample.isVoidStaticMethod();     //call the method you are stubbing

        StaticExample.isVoidStaticMethod();

        verifyStatic();
        StaticExample.isVoidStaticMethod();
    }

    @Test
    public void testCallPrivateMethod() throws Exception{
        final int RETURN_VALUE = 1;
        StaticExample staticExampleMock = PowerMockito.spy(new StaticExample());
        // For private methods use PowerMockito.when
        when(staticExampleMock, "isPrivateMethod", anyInt()).thenReturn(RETURN_VALUE);

        int actual = staticExampleMock.callPrivateMethod(5);

        verifyPrivate(staticExampleMock).invoke("isPrivateMethod", anyInt());
        assertEquals(RETURN_VALUE, actual);
    }


}
