package com.bingo.server.game.landlord.cache;


import com.bingo.server.database.model.Role;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class RoleCache {
	/** 所有玩家缓存信息 */
	private static ConcurrentMap<Long/* roleId */, Role> roleMap = new ConcurrentHashMap<>();
	private static ConcurrentMap<String, Role> roleAccountMap = new ConcurrentHashMap<>();
	/** 姓名集合 */
	private static Map<String, String> nameSet = new ConcurrentHashMap<>();
	/** 帐号集合 */
	private static Map<String, String> accountSet = new ConcurrentHashMap<>();

	/**
	 * 根据id获取角色缓存
	 * 
	 * @param id
	 * @return
	 */
	public static Role getRoleById(long id) {
		return getRoleMap().get(id);
	}

	/**
	 * 更具账号获取角色缓存
	 * 
	 * @param account
	 * @return
	 */
	public static Role getRoleByAccount(String account) {
		return roleAccountMap.get(account);
	}

	public static Map<String, String> getNameSet() {
		return nameSet;
	}

	public static Map<String, String> getAccountSet() {
		return accountSet;
	}

	public static ConcurrentMap<Long, Role> getRoleMap() {
		return roleMap;
	}

	public static synchronized void putNewRole(Role role) {
		roleMap.put(role.getId(), role);
		roleAccountMap.put(role.getUsername(), role);
		accountSet.put(role.getUsername(), role.getUsername());
		nameSet.put(role.getNickName(), role.getNickName());
	}

	public static void putRoleCache(Role role) {
		if(!roleMap.containsKey(role.getId())){
			roleMap.put(role.getId(), role);
			roleAccountMap.put(role.getUsername(), role);
			accountSet.put(role.getUsername(), role.getUsername());
			nameSet.put(role.getNickName(), role.getNickName());
		}
	}

	public static ConcurrentMap<String, Role> getRoleAccountMap() {
		return roleAccountMap;
	}

}
