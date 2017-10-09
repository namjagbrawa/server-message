package com.bingo.server.game.landlord.service;

import com.bingo.server.database.model.Role;
import com.bingo.server.game.landlord.config.GlobleConfig;
import com.bingo.server.game.landlord.template.ObserveBaseService;
import com.bingo.server.game.landlord.util.HttpConnection;
import com.bingo.server.game.landlord.util.base.TimeUtils;
import com.bingo.server.msg.Error;
import com.bingo.server.msg.MoneyExchange;
import com.bingo.server.msg.ServerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MoneyExchangeService extends ObserveBaseService implements MoneyExchangeProvider {

	
	@Autowired
	private RoleService roleService;

	@Override
	public void newRoleInit(Role role) {
		role.setMoneyExchangeTime(new Date());
	}

	@Override
	public ServerMessage.SC moneyExchange(Role role, boolean add, int num) {
		System.out.println("@@@"+role.getUsername());
		int max = role.getMoneyExchangeNumber();
		String today = TimeUtils.getCurrentTimeStr();
		String time = TimeUtils.getTimeStr(role.getMoneyExchangeTime().getTime());
		int randiooMoney = num;
		if(num % 1000 != 0 || num * 0.001 < 1){
			return ServerMessage.SC.newBuilder().setMoneyExchangeResponse(MoneyExchange.MoneyExchangeResponse.newBuilder().setErrorCode(Error.ErrorCode.MONEY_NUM_ERROR.getNumber()).build()).build();
		}
		
		if(add){			
			num *= 0.1;
		}else{
			//TODO 每天5W
			
			if(time == null){
				max = num;
			}else{
				time = time.substring(0, 10);				
				if(today.equals(time)){
					if(max + num > 50000){
						return ServerMessage.SC.newBuilder().setMoneyExchangeResponse(MoneyExchange.MoneyExchangeResponse.newBuilder().setErrorCode(Error.ErrorCode.MONEY_NUM_ERROR.getNumber()).build()).build();
					}
					else{
						max += num;
					}
				}
				else{
					max = num;
					if(num > 50000){
						return ServerMessage.SC.newBuilder().setMoneyExchangeResponse(MoneyExchange.MoneyExchangeResponse.newBuilder().setErrorCode(Error.ErrorCode.MONEY_NUM_ERROR.getNumber()).build()).build();
					}
				}
			}
			
			
			num *= 0.085;
			
		}
		if(exchangeMoney(role, num, add)){
			role.setMoney(role.getMoney()+(add?1:-1)*randiooMoney);
			if(!add){
				role.setMoneyExchangeTime(TimeUtils.getTime(time));
				role.setMoneyExchangeNumber(max);
			}
    		return ServerMessage.SC.newBuilder().setMoneyExchangeResponse(MoneyExchange.MoneyExchangeResponse.newBuilder().setErrorCode(Error.ErrorCode.OK.getNumber()).setRoleData(roleService.getRoleData(role))).build();
    	}else{
    		return ServerMessage.SC.newBuilder().setMoneyExchangeResponse(MoneyExchange.MoneyExchangeResponse.newBuilder().setErrorCode(Error.ErrorCode.NO_MONEY.getNumber())).build();
    	}
    	
		
	}
	
	public  boolean exchangeMoney(Role role, int money, boolean add){
		return true;
		/*String url = GlobleConfig.String("URL")+"/gateway/MaJiang/changeMoney.php?key=f4f3f65d6d804d138043fbbd1843d510&&id=";
    	String tpram = "";
    	tpram = tpram.concat(role.getUsername());
    	tpram = tpram.concat("&&money_num="+money);
    	tpram = tpram.concat("&&type="+(add?0:1));
    	System.out.println("URL"+url);
    	System.out.println("PRAM"+tpram);
    	HttpConnection conn = new HttpConnection(url,tpram);
    	try {
			conn.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(conn.result+""+conn.result.getClass().getName()+(conn.result.charAt(0) == '1'));
    	if(conn.result.charAt(0) == '1'){
    		return true;
    	}else{
    		return false;
    	}*/
	}

	

}
