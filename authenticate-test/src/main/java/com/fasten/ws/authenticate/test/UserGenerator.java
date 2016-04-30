package com.fasten.ws.authenticate.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

public class UserGenerator {

	public static List<User> generateUsers(int numberOfUsers) {
		List<User> users = new ArrayList<User>();
		for (int i = 0 ; i < numberOfUsers; i++) {
			String random = RandomStringUtils.random(8, true, true);
			users.add(new User(random + "@is_test.ru", random));
		}
		return users;
		
	}
}
