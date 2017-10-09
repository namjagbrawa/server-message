package com.bingo.server.game.landlord.service;

import com.bingo.server.database.model.Role;
import com.bingo.server.game.landlord.cache.RoleCache;
import com.bingo.server.game.landlord.config.GlobleConfig;
import com.bingo.server.game.landlord.platform.Platform;
import com.bingo.server.game.landlord.platform.SignalTrigger;
import com.bingo.server.game.landlord.scheduler.SchedulerManager;
import com.bingo.server.game.landlord.template.EntityRunnable;
import com.bingo.server.game.landlord.template.Function;
import com.bingo.server.game.landlord.template.ObserveBaseService;
import com.bingo.server.game.landlord.util.base.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class GmService extends ObserveBaseService implements GmProvider {

	@Autowired
	private SchedulerManager schedulerManager;

	@Override
	public void init() {

		Function function = new Function() {

			@Override
			public Object apply(Object... params) {
				GlobleConfig.set(GlobleConfig.GlobleEnum.LOGIN, false);

				System.out.println("port close");

				// everybodyOffline();

				// 定时器全部停止
				try {
					schedulerManager.shutdown(1, TimeUnit.SECONDS);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				/*try {
					gameDB.shutdown(1, TimeUnit.SECONDS, new Function() {

						@Override
						public Object apply(Object... params) {
							// 数据保存
							System.out.println();
							System.out.println("start game all data save");
							// 循环性数据存储
							ExecutorService service = loopSaveData(true, true, 2);

							service.shutdown();
							try {
								while (!service.awaitTermination(1, TimeUnit.SECONDS)) {
								}
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							// 一次性数据存储
							onceSaveData();

							System.out.println();
							System.out.println("game all data save SUCCESS");
							System.exit(0);
							return null;
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}*/

				return null;
			}

		};

		// 命令关闭信号
		try {
			System.out.println(Platform.getOS());
			if (Platform.getOS() == Platform.OS.WIN)
				SignalTrigger.setSignCallback("INT", function);
			else if (Platform.getOS() == Platform.OS.LINUX)
				SignalTrigger.setSignCallback("ABRT", function);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (Platform.getOS() == Platform.OS.WIN) {
			new Thread(new EntityRunnable<Function>(function) {

				private Scanner in = new Scanner(System.in);

				@Override
				public void run(Function function) {
					while (true) {
						try {
							String command = in.nextLine();
							if (!StringUtils.isNullOrEmpty(command)) {
								if (command.equals("exit")) {
									function.apply();
								} else if (command.equals("save")) {
									for (Role role : RoleCache.getRoleMap().values()) {
										//SessionCloseHandler.roleDataCache2DB((Role) roleInterface, true);
										System.out.println(role.getUsername() + " force Save");
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

			}).start();

		}
	}

	/**
	 * 所有人下线
	 * 
	 * @author wcy 2016年12月9日
	 */
	/*private void everybodyOffline() {
		// 所有人下线
		Collection<IoSession> allSession = SessionCache.getAllSession();
		Iterator<IoSession> it = allSession.iterator();
		while (it.hasNext()) {
			it.next().close(true);
		}

	}*/

	@Override
	public void loopSaveData(boolean mustSave) {
		loopSaveData(mustSave, false, 0);
	}

	private ExecutorService loopSaveData(final boolean mustSave, boolean thread, int threadSize) {
		ExecutorService executorService = null;
		if (thread) {
			executorService = Executors.newScheduledThreadPool(threadSize);
		}
		for (Role role : RoleCache.getRoleMap().values()) {
			if (executorService != null) {
				executorService.submit(new EntityRunnable<Role>(role) {

					@Override
					public void run(Role role) {
						saveRoleData(role, mustSave);
					}
				});
			} else {
				saveRoleData(role, mustSave);
			}
		}

		return executorService;

	}

	private void saveRoleData(Role role, boolean mustSave) {
		try {
			if (mustSave)
				System.out.println("id:" + role.getId() + ",account:" + role.getUsername() + ",name:"
						+ role.getNickName() + "] save success");
			// SessionCloseHandler.roleDataCache2DB(role, mustSave);
		} catch (Exception e) {
			System.out.println("Role: " + role.getId() + " saveError!");
			e.printStackTrace();
		}
	}

	private void onceSaveData() {
		
	}
	

}
