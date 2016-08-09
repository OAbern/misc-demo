package com.zhubajie.oabern.mock.demo.mockito.test.official;

import com.zhubajie.oabern.mock.demo.entity.Flower;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

// use 'import static' import Mockito's static method
import static org.mockito.Mockito.*;

import static org.mockito.BDDMockito.*;

import static org.junit.Assert.*;

/**
 * the code is based on the offical demo from <a href="http://site.mockito.org/">Mockito</a>
 *
 * Created by fengdi on 2016/8/8.
 */
public class MockitoTest {

    @Test
    public void testInteraction() {
        // mock creation
        List mockedList = mock(List.class);

        // using mock object - it does not throw any "unexpected interaction" exception
        mockedList.add("one");

        mockedList.add("two");
        mockedList.add("two");

        mockedList.clear();

        // selective, explicit, highly readable verification
        verify(mockedList).add("one");
        verify(mockedList, times(2)).add("two");
//        verify(mockedList, times(1)).add("two");      //it will make test fail
        verify(mockedList).clear();
    }

    @Test
    public void testStubbing() {
        final String param = "first";

        // you can mock concrete classes, not only interfaces
        LinkedList mockedList = mock(LinkedList.class);

        // stubbing appears before the actual execution
        when(mockedList.get(0)).thenReturn(param);

        // the following prints "first"
        System.out.println(mockedList.get(0));

        // the following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));

        assertEquals(mockedList.get(0), param);
    }

    @Test
    public void testStubbingByBDD() {
        final String param1 = "first";
        final String param2 = "seconed";
        final String param3 = "thrid";

        // you can mock concrete classes, not only interfaces
        LinkedList mockedList = mock(LinkedList.class);

        // stubbing appears before the actual execution
        // Stubbing Multiple Calls to the Same Method
        // stubbing by BDD
        given(mockedList.get(0)).willReturn(param1, param2, param3);

        // the following prints by giving parameter in order
        System.out.println(mockedList.get(0));
        System.out.println(mockedList.get(0));
        System.out.println(mockedList.get(0));

        // the following prints "null" because get(999) was not stubbed
        System.out.println(mockedList.get(999));

        //make the 4th call
        assertEquals(mockedList.get(0), param3);
    }

    @Test
    public void shouldStubMethodAndCallRealNotStubbedMethod(){
        final int NEW_NUMBER_OF_LEAFS = 10;

        Flower realFlower = new Flower();

        realFlower.setNumberOfLeafs(Flower.ORIGINAL_NUMBER_OF_LEAFS);

        Flower flowerSpy = spy(realFlower);

        willDoNothing().given(flowerSpy).setNumberOfLeafs(anyInt());

        flowerSpy.setNumberOfLeafs(NEW_NUMBER_OF_LEAFS);//stubbed‚should do nothing

        verify(flowerSpy).setNumberOfLeafs(NEW_NUMBER_OF_LEAFS);

        assertEquals(flowerSpy.getNumberOfLeafs(),Flower.ORIGINAL_NUMBER_OF_LEAFS);    //value was not changed

    }

}
