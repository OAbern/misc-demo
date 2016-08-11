package com.zhubajie.oabern.mock.demo.powermock.test.official;

import com.zhubajie.oabern.mock.demo.entity.MyClass;
import com.zhubajie.oabern.mock.demo.entity.MyFinalClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.anyInt;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * test for construction method
 *
 * <p>the code is based on the official demo from
 * <a href="https://github.com/jayway/powermock/wiki/MockitoUsage">PowerMock</a></p>
 *
 * Created by fengdi on 2016/8/11.
 */
@RunWith(PowerMockRunner.class)
// Notice: which class is here
@PrepareForTest(MyFinalClass.class)
public class ConstructionPowerMockTest {

    @Test(expected = NullPointerException.class)
    public void testConstructionWithNoArguments() throws Exception {
        whenNew(MyClass.class).withNoArguments().thenThrow(new NullPointerException());

        MyFinalClass myFinalClass = new MyFinalClass();
        myFinalClass.newMyClass();     // the method doing "new MyClass()"

        verifyNew(MyClass.class).withNoArguments();
    }

    @Test(expected = NullPointerException.class)
    public void testConstructionWithArguments() throws Exception {
        whenNew(MyClass.class).withArguments(anyInt()).thenThrow(new NullPointerException());

        MyFinalClass myFinalClass = new MyFinalClass();
        myFinalClass.newMyClass(5);     // the method doing "new MyClass()"

        verifyNew(MyClass.class).withArguments(5);
    }
}
