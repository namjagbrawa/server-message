package com.bingo.server.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Check {
	public static boolean isNull(List list) {
		return list == null || list.size() == 0 ? true : false;
	}

	public static boolean isNull(Long l) {
		return l == null || l == 0;
	}

	public static boolean isNull(String str) {
		return str == null || str.length() <= 0;
	}

	public static boolean isNullSet(Set set) {
		return set == null || set.size() == 0 ? true : false;
	}

	public static boolean isNull(byte[] bytes) {
		return bytes == null || bytes.length == 0 ? true : false;
	}

	public static boolean hasNull(Object... objects) {
		for (Object obj : objects) {
			if (obj == null) {
				return true;
			}
		}
		return false;
	}

	public static boolean allEmpty(String... strs) {
		for (String str : strs) {
			if (StringUtils.isNotEmpty(str)) {
				return false;
			}
		}
		return true;
	}

	public static boolean blankStr(String str) {
		return str == null || str.length() == 0 ? true : false;
	}

	public static boolean isBlank(String str) {
		return str == null || str.equals("") ? true : false;
	}

	public static boolean isNull(Object object) {
		return object == null ? true : false;
	}

	public static boolean isNullMap(Map map) {
		return map == null || map.size() == 0 ? true : false;
	}
}
