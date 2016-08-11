package com.zhubajie.oabern.mock.demo.powermock.test.official;

import com.zhubajie.oabern.mock.demo.entity.MyClass;
import com.zhubajie.oabern.mock.demo.entity.MyFinalClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

// use static import to import all static method
import static org.junit.Assert.*;

import static org.mockito.BDDMockito.*;

import static org.powermock.api.mockito.PowerMockito.*;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * test for final class and final method
 *
 * <p>the code is based on the official demo from
 * <a href="https://github.com/jayway/powermock/wiki/MockitoUsage">PowerMock</a></p>
 *
 * Created by fengdi on 2016/8/11.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({MyClass.class, MyFinalClass.class})
public class FinalPowerMockTest {

    @Test
    public void spyStubVerifyFinalMethod() throws Exception {
        final int EXPECTED_VALUE = 1;
        MyClass myClassSpy = spy(new MyClass());

        when(myClassSpy, "isFinalMethod", anyInt()).thenReturn(EXPECTED_VALUE);

        int actual = myClassSpy.isFinalMethod(5);

        verify(myClassSpy).isFinalMethod(5);
        assertEquals(EXPECTED_VALUE, actual);

    }

    @Test
    public void mockStubVerifyFinalMethod() throws Exception {
        final int EXPECTED_VALUE = 1;

        MyClass myClassMock = mock(MyClass.class);
        when(myClassMock.isFinalMethod(5)).thenReturn(EXPECTED_VALUE);

        int actual = myClassMock.isFinalMethod(5);

        verify(myClassMock).isFinalMethod(5);
        assertEquals(EXPECTED_VALUE, actual);

    }

    @Test
    public void testFinalClassMethod() {
        final int EXPECTED_VALUE = 1;

        MyFinalClass myFinalClassMock = mock(MyFinalClass.class);
        // both OK to stub
//        PowerMockito.doReturn(EXPECTED_VALUE).when(myFinalClassMock).normalMethod(anyInt());
        when(myFinalClassMock.normalMethod(anyInt())).thenReturn(EXPECTED_VALUE);

        int actual = myFinalClassMock.normalMethod(5);

        verify(myFinalClassMock).normalMethod(5);
        assertEquals(EXPECTED_VALUE, actual);
    }

    @Test
    public void testFinalClassNativeMethod() {
        final int EXPECTED_VALUE = 1;

        MyFinalClass myFinalClassMock = mock(MyFinalClass.class);
        // both OK to stub
        PowerMockito.doReturn(EXPECTED_VALUE).when(myFinalClassMock).isNativeMethod();
        when(myFinalClassMock.isNativeMethod()).thenReturn(EXPECTED_VALUE);

        int actual = myFinalClassMock.isNativeMethod();

        verify(myFinalClassMock).isNativeMethod();
        assertEquals(EXPECTED_VALUE, actual);
    }
}
