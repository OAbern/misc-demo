package com.zhubajie.oabern.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhubajie.oabern.bean.City;
import com.zhubajie.oabern.dal.CityMapper;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by fengdi on 2016/7/11.
 */
@Controller
@RequestMapping("/info")
public class InfoController {

    @Resource
    public CityMapper cityMapper;

    @RequestMapping("/city/{cityname}")
    public void getCityInfoByName(@PathVariable("cityname")String cityName, HttpServletResponse response) throws IOException {
        String resultStr;

        if (cityName == null || cityName.trim().equals("")) {
            resultStr = "invalid parameter!";
        }else {
            List<City> cityList = cityMapper.findCityInfoByName(cityName);
            if (CollectionUtils.isEmpty(cityList)) {
                resultStr = "Not a city Named '" + cityName + "' in database!";
            }else {
                resultStr = JSON.toJSONString(cityList, SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect);	//开启null值输出，关掉循环引用检测
            }
        }

        try {
            response.getWriter().write(resultStr);
        } finally {
            response.getWriter().close();
        }

    }
}
