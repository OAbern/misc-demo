package com.bern.ajax.demo.service;

import java.util.ArrayList;
import java.util.List;

import com.bern.ajax.demo.bean.Address;

public class AddressService {
	public List<Address> init() {
		Address bj = new Address(1, 0, "北京");
		Address sh = new Address(2, 0, "上海");
		Address tj = new Address(3, 0, "天津");
		Address cq = new Address(4, 0, "重庆");
		bj.getAddresses().add(new Address(5, 1, "朝阳区"));
		bj.getAddresses().add(new Address(6, 1, "宣武区"));
		sh.getAddresses().add(new Address(7, 2, "浦东新区"));
		sh.getAddresses().add(new Address(8, 2, "黄埔区"));
		tj.getAddresses().add(new Address(9, 3, "大港区"));
		tj.getAddresses().add(new Address(10, 3, "塘沽区"));
		cq.getAddresses().add(new Address(11, 4, "南岸区"));
		cq.getAddresses().add(new Address(12, 4, "渝中区"));
		List<Address> ads = new ArrayList<Address>();
		ads.add(bj);
		ads.add(sh);
		ads.add(tj);
		ads.add(cq);
		return ads;
	}
}
