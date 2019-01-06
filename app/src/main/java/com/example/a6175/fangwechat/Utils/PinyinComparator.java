package com.example.a6175.fangwechat.Utils;

import com.example.a6175.fangwechat.bean.User;


import java.util.Comparator;

public class PinyinComparator implements Comparator {

	@Override
	public int compare(Object arg0, Object arg1) {
		// 按照名字排序
		User user0 = (User) arg0;
		User user1 = (User) arg1;
		String catalog0 = "";
		String catalog1 = "";

		if (user0 != null && user0.getNickname() != null
				&& user0.getNickname().length() > 1)
			catalog0 = PingYinUtil.converterToFirstSpell(user0.getNickname())
					.substring(0, 1);

		if (user1 != null && user1.getNickname() != null
				&& user1.getNickname().length() > 1)
			catalog1 = PingYinUtil.converterToFirstSpell(user1.getNickname())
					.substring(0, 1);
		int flag = catalog0.compareTo(catalog1);
		return flag;

	}

}
