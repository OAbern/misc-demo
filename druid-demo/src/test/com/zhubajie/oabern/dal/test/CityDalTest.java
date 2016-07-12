package com.zhubajie.oabern.dal.test;

import com.zhubajie.oabern.bean.City;
import com.zhubajie.oabern.dal.CityMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/7/8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/applicationContext.xml")
public class CityDalTest {
    @Resource
    public CityMapper cityMapper;

    @Test
//    @Ignore
    public void selectLimitTest() {
        final int minLimit = 0;
        final int maxLimit = 10;
        List<City> cityList = cityMapper.findByLimit(minLimit, maxLimit);
        int i = 0;
        for (City c : cityList) {
            i++;
            System.out.println(i + ":" + c.getName());
        }
        assert cityList.size() == maxLimit-minLimit+1;
    }

    @Test
    public void countTest() {
        int count = cityMapper.countNum();
        System.out.println(count);
    }
}
