package com.zhubajie.oabern.mock.demo.mockito.test.official;

import com.zhubajie.oabern.mock.demo.entity.Flower;
import com.zhubajie.oabern.mock.demo.entity.Scheduler;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

// use 'import static' import Mockito's static method
import static org.mockito.Mockito.*;

import static org.mockito.BDDMockito.*;

import static org.junit.Assert.*;

/**
 * the code is based on the official demo from <a href="http://site.mockito.org/">Mockito</a>
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
        // arrange
        final int NEW_NUMBER_OF_LEAFS = 10;
        Flower realFlower = new Flower();
        realFlower.setNumberOfLeafs(Flower.ORIGINAL_NUMBER_OF_LEAFS);
        Flower flowerSpy = spy(realFlower);     //spy
        willDoNothing().given(flowerSpy).setNumberOfLeafs(anyInt());

        // act
        flowerSpy.setNumberOfLeafs(NEW_NUMBER_OF_LEAFS);    //stubbed‚should do nothing

        // assert
        verify(flowerSpy).setNumberOfLeafs(NEW_NUMBER_OF_LEAFS);

        assertEquals(flowerSpy.getNumberOfLeafs(),Flower.ORIGINAL_NUMBER_OF_LEAFS);    //value was not changed

    }

    @Test
    public void testCustomArgumentMatcher() {
        final int NUM = 1;
        final Date date = new Date();
        final int hour = date.getHours();

        Scheduler schedulerMock = mock(Scheduler.class);
        given(schedulerMock.getNumberOfPlantsScheduledOnDate(
                argThat(haveHourFieldEqualTo(hour)))).willReturn(NUM);      //use argThat(.) and give the custom argument matcher

        int actual = schedulerMock.getNumberOfPlantsScheduledOnDate(new Date());

        verify(schedulerMock).getNumberOfPlantsScheduledOnDate(any());
        assertEquals(NUM, actual);
    }

    /**
     * with the util method to create a matcher
     * return custom ArgumentMatcher
     * @param hour
     * @return
     */
    private ArgumentMatcher haveHourFieldEqualTo(final int hour) {

        return new ArgumentMatcher() {

            @Override
            public boolean matches(Object argument) {
                return ((Date) argument).getHours() == hour;
            }

        };
    }

    @Test
    public void throwException() {
        Flower flowerMock = mock(Flower.class);
        given(flowerMock.getNumberOfLeafs()).willThrow(new NullPointerException());
        try {
            flowerMock.getNumberOfLeafs();
        } catch (NullPointerException e) {
            System.out.println("got NullException!");
        }

        verify(flowerMock).getNumberOfLeafs();
    }

}
