package com.zhubajie.oabern.robot;

import com.zhubajie.oabern.bean.City;
import com.zhubajie.oabern.dal.CityMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by fengdi on 2016/7/11.
 */
@Component
public class SQLExector {
    @Resource
    CityMapper cityMapper;
    final int fixedNum = 2;
    final ExecutorService fixedThreadPool = Executors.newFixedThreadPool(fixedNum);
//    final ExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);

    /**
     * 创建根据limit随意查询的代理类
     * @return
     */
    public Runnable createProxy1(String proxyName) {
        return new DalProxy() {
            {
                setProxyName(proxyName);
            }

            @Override
            public void doCRUD() {
                int num = cityMapper.countNum();
                int limit1 = new Random().nextInt(num);
                int limit2 = new Random().nextInt(num);
                int minLimit, maxLimit;
                if(limit1 < limit2) {
                    minLimit = limit1;
                    maxLimit = limit2;
                }else {
                    minLimit = limit2;
                    maxLimit = limit1;
                }
                List<City> cityList = cityMapper.findByLimit(minLimit, maxLimit);
                System.out.println("SQL: the number of select limit's result :" + cityList.size());
            }
        };
    }

    /**
     * 创建根据城市名查询的代理类
     * @return
     */
    public Runnable createProxy2(String proxyName) {
        return new DalProxy() {
            String[] nameArray = {"Groningen", "Tilburg", "Kabul", "Haag", "San Juan del Monte", "Sekondi-Takoradi"};
            int cursor = 0;

            {
                setProxyName(proxyName);
            }

            @Override
            public void doCRUD() {
                cursor = ++cursor % nameArray.length;
                String cityName = nameArray[cursor];
                List<City> cityList = cityMapper.findCityInfoByName(cityName);
                System.out.println("SQL: the number of selectByCityName's result :" + cityList.size());
            }
        };
    }

    public boolean execute() {
        fixedThreadPool.execute(createProxy1("P1"));
        System.out.println("-------------------1-------------------");
        fixedThreadPool.execute(createProxy2("P3"));
        System.out.println("-------------------2-------------------");
        fixedThreadPool.execute(createProxy1("P2"));
        System.out.println("-------------------3-------------------");
        fixedThreadPool.execute(createProxy2("P4"));
        System.out.println("-------------------4-------------------");

        return true;
    }


    class DalProxy implements Runnable {
        String proxyName;

        public void doCRUD() {
            System.out.println("do nothing!");
        }

        public void run() {
            int i = 0;
            while(++i < 10) {
                doCRUD();
                try {
                    Thread.sleep(1000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(proxyName + " is done!");
        }

        public String getProxyName() {
            return proxyName;
        }

        public void setProxyName(String proxyName) {
            this.proxyName = proxyName;
        }
    }
}
