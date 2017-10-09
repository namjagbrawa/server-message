/*
package com.bingo.server.hall.process;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.rpc.bean.Packet;
import com.alibaba.dubbo.rpc.support.LeastActiveLoadBalanceSupport;
import com.bingo.server.bean.ServerType;
import com.bingo.server.business.GameMsg;
import com.bingo.server.business.HallMsg;
import com.bingo.cmd.*;
import com.bingo.database.provider.*;
import com.bingo.server.database.provider.*;
import com.bingo.server.hall.bean.*;
import com.server.hall.bean.*;
import com.bingo.server.utils.HttpClient;
import com.bingo.server.utils.RegistryServerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.bingo.cmd.CmdNoOuterClass.CmdNo.*;
import static com.bingo.cmd.Define.BindRoomStateDef.Bind_OK_VALUE;
import static com.bingo.cmd.Define.BindRoomStateDef.UnBinding_VALUE;
import static com.bingo.cmd.Define.FriendState.FS_DeskWait;
import static com.bingo.cmd.Define.FriendState.FS_Idle;
import static com.bingo.cmd.ErrorCodeOuterClass.ErrorCode.*;
import static com.bingo.cmd.MjDefine.MjGameRuler.*;

*/
/**
 * Created by ZhangGe on 2017/6/20.
 *//*

@Service
public class HallMsgProcess extends AbstractProcess implements HallMsg {

    private static final String HALL_MSG = HallMsg.class.getName();
    private static final String GAME_MSG = GameMsg.class.getName();

    private static final String GUEST_ACCOUNT_PASSWORD = "369";
    private static int GUEST_ACCOUNT_COUNT = 0;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private CUserProvider CUserProvider;
    @Autowired
    private RoomProvider roomProvider;
    @Autowired
    private MailProvider mailProvider;
    @Autowired
    private ItemProvider itemProvider;
    @Autowired
    private CFriendProvider CFriendProvider;
    @Autowired
    private RouterProvider routerProvider;
    @Autowired
    private ConfigProvider configProvider;


    @Override
    public void onGateMSG(Packet req) {
        if (req == null) {
            logger.error("packet is null.");
            return;
        }

        int code = req.getCode();
        if (code == CmdNo_Login_Enter_Req_VALUE) {
            onEnterGame(req);
            ServerStatistics.increOnlineNum();
            return;
        }

        MjUser mjUser = MjUserMgr.getInstance().get(req.getUserId());
        if (mjUser == null) {
            logger.error("玩家不存在, {}", req.getUserId());
            return;
        }
        switch (code) {
            case CmdNo_MailList_Req_VALUE:
                GameCmd.MyMailListReq myMailListReq = (GameCmd.MyMailListReq) CmdParse.parse(req);
                onMailListReq(req, mjUser, myMailListReq);
                break;
            case CmdNo_ReadMail_Req_VALUE:
                GameCmd.MailReadReq mailReadReq = (GameCmd.MailReadReq) CmdParse.parse(req);
                onMailReadReq(req, mjUser, mailReadReq);
                break;
            case CmdNo_PickMail_Req_VALUE:
                GameCmd.MailPickupAttachReq mailPickupAttachReq = (GameCmd.MailPickupAttachReq) CmdParse.parse(req);
                onMailPickReq(req, mjUser, mailPickupAttachReq);
                break;
            case CmdNo_BagItemList_Req_VALUE:
                GameCmd.MyBagItemListRsp myBagItemListRsp = (GameCmd.MyBagItemListRsp) CmdParse.parse(req);
                onMyBagReq(req, mjUser, myBagItemListRsp);
                break;
            case CmdNo_VerifyCode_Req_VALUE:
                GameCmd.GetVerifyCodeReq verifyCodeReq = (GameCmd.GetVerifyCodeReq) CmdParse.parse(req);
                onVerifyCodeReq(req, mjUser, verifyCodeReq);
                break;
            case CmdNo_BindPhone_Req_VALUE:
                GameCmd.BindPhoneReq bindPhoneReq = (GameCmd.BindPhoneReq) CmdParse.parse(req);
                onBindPhoneReq(req, mjUser, bindPhoneReq);
                break;
            case CmdNo_Certification_Req_VALUE:
                GameCmd.CertificationReq certificationReq = (GameCmd.CertificationReq) CmdParse.parse(req);
                onCertification(req, mjUser, certificationReq);
                break;
            case CmdNo_ModifyInfo_Req_VALUE:
                GameCmd.ModifyUserInfoReq userInfoReq = (GameCmd.ModifyUserInfoReq) CmdParse.parse(req);
                onModifyUserInfo(req, mjUser, userInfoReq);
                break;
            case CmdNo_UserRegionSetup_Req_VALUE:
                GameCmd.UserRegionSetupReq regionSetupReq = (GameCmd.UserRegionSetupReq) CmdParse.parse(req);
                onSetRegionReq(req, mjUser, regionSetupReq);
                break;
            case CmdNo_AwardConfig_Req_VALUE:
                GameCmd.GetAwardConfigReq awardConfigReq = (GameCmd.GetAwardConfigReq) CmdParse.parse(req);
                onGetAwardConfig(req, mjUser, awardConfigReq);
                break;
            case CmdNo_Lottery_Req_VALUE:
                onLotteryReq(req, mjUser);
                break;
            case CmdNo_PickupAward_Req_VALUE:
                GameCmd.PickupAwardReq pickupAwardReq = (GameCmd.PickupAwardReq) CmdParse.parse(req);
                onPickAwardReq(req, mjUser, pickupAwardReq);
                break;
            case CmdNo_GetInviteeList_Req_VALUE:
                onGetInviteeListReq(req, mjUser);
                break;
            case CmdNo_BindInviteCode_Req_VALUE:
                GameCmd.BindInviteCodeReq bindInviteCodeReq = (GameCmd.BindInviteCodeReq) CmdParse.parse(req);
                onBindInviteCodeReq(req, mjUser, bindInviteCodeReq);
                break;
            case CmdNo_PickInviteAward_Req_VALUE:
                GameCmd.PickInviteAwardReq pickInviteAqardReq = (GameCmd.PickInviteAwardReq) CmdParse.parse(req);
                onPickInviteAwardReq(req, mjUser, pickInviteAqardReq);
                break;
            case CmdNo_OtherPlayer_Req_VALUE:
                GameCmd.OtherPlayerInfoReq otherPlayerInfoReq = (GameCmd.OtherPlayerInfoReq) CmdParse.parse(req);
                onViewOtherPlayerReq(req, mjUser, otherPlayerInfoReq);
                break;
            case CmdNo_MyFriends_Req_VALUE:
                onGetMyFriendsReq(req, mjUser);
                break;
            case CmdNo_AddFriend_Req_VALUE:
                GameCmd.AddFriendReq addFriendReq = (GameCmd.AddFriendReq) CmdParse.parse(req);
                onAddFriendReq(req, mjUser, addFriendReq);
                break;
            case CmdNo_DelFriend_Req_VALUE:
                GameCmd.DelFriendReq delFriendReq = (GameCmd.DelFriendReq) CmdParse.parse(req);
                onDelFriendReq(req, mjUser, delFriendReq);
                break;
            case CmdNo_ApplyFriend_Req_VALUE:
                GameCmd.ApplyFriendReq applyFriendReq = (GameCmd.ApplyFriendReq) CmdParse.parse(req);
                onApplyFriendReq(req, mjUser, applyFriendReq);
                break;
            case CmdNo_InviteGame_Req_VALUE:
                GameCmd.InviteFriendGameReq inviteFriendGameReq = (GameCmd.InviteFriendGameReq) CmdParse.parse(req);
                onInviteGameReq(req, mjUser, inviteFriendGameReq);
                break;
            case CmdNo_RefuseInvite_Req_VALUE:
                GameCmd.FefuseFriendReq fefuseFriendReq = (GameCmd.FefuseFriendReq) CmdParse.parse(req);
                onRefuseInviteReq(req, mjUser, fefuseFriendReq);
                break;
            case CmdNo_JoinFriendMjDesk_Req_VALUE:
                GameCmd.JoinFriendMjDeskReq joinFriendMjDeskReq = (GameCmd.JoinFriendMjDeskReq) CmdParse.parse(req);
                onJoinFriendDeskReq(req, mjUser, joinFriendMjDeskReq);
                break;
            case CmdNo_RecvDeskJoin_Req_VALUE:
                GameCmd.RecvDeskJoinReq recvDeskJoinReq = (GameCmd.RecvDeskJoinReq) CmdParse.parse(req);
                onRecvDeskJoinReq(req, mjUser, recvDeskJoinReq);
                break;
            case MjCmd_FRoomPlayerInfo_Req_VALUE:
                MjCmd.FMjRoomPlayerInfoReq fMjRoomPlayerInfoReq = (MjCmd.FMjRoomPlayerInfoReq) CmdParse.parse(req);
                onMjUserInfo(req, mjUser, fMjRoomPlayerInfoReq);
                break;
            case MjCmd_FRoomInfo_Req_VALUE:
                onHallInfoReq(req, mjUser);
                break;
            case MjCmd_FRoomSearch_Req_VALUE:
                MjCmd.FMjRoomSearchReq fMjRoomSearchReq = (MjCmd.FMjRoomSearchReq) CmdParse.parse(req);
                onSearchReq(req, mjUser, fMjRoomSearchReq);
                break;
            case MjCmd_FRoomBind_Req_VALUE:
                MjCmd.FMjRoomBindReq fMjRoomBindReq = (MjCmd.FMjRoomBindReq) CmdParse.parse(req);
                onBindReq(req, mjUser, fMjRoomBindReq);
                break;
            case MjCmd_FRoomUnBind_Req_VALUE:
                MjCmd.FMjRoomUnBindReq fMjRoomUnBindReq = (MjCmd.FMjRoomUnBindReq) CmdParse.parse(req);
                onUnBindReq(req, mjUser, fMjRoomUnBindReq);
                break;
            case MjCmd_FRoomTuijian_Req_VALUE:
                onTuijianReq(req, mjUser);
                break;
            case MjCmd_FRoomEnter_Req_VALUE:
                MjCmd.FMjRoomEnterReq fMjRoomEnterReq = (MjCmd.FMjRoomEnterReq) CmdParse.parse(req);
                onEnterReq(req, mjUser, fMjRoomEnterReq);
                break;
            case MjCmd_FRoomLeave_Req_VALUE:
                MjCmd.FMjRoomLeaveReq fMjRoomLeaveReq = (MjCmd.FMjRoomLeaveReq) CmdParse.parse(req);
                onLeaveReq(req, mjUser, fMjRoomLeaveReq);
                break;
            case MjCmd_RulerConfig_Req_VALUE:
                onRulerConfigReq(req, mjUser);
                break;
            case MjCmd_FRoomChangeBoard_Req_VALUE:
                MjCmd.FMjRoomChangeBoardReq fMjRoomChangeBoardReq = (MjCmd.FMjRoomChangeBoardReq) CmdParse.parse(req);
                onChangeBoardReq(req, mjUser, fMjRoomChangeBoardReq);
                break;
            case MjCmd_FRoomMyRoom_Req_VALUE:
                onViewMjRoomReq(req, mjUser);
                break;
            case MjCmd_FRoomDeskRecord_Req_VALUE:
                MjCmd.FMjRoomViewDeskRecordReq fMjRoomViewDeskRecordReq = (MjCmd.FMjRoomViewDeskRecordReq) CmdParse.parse(req);
                onViewDeskRecordReq(req, mjUser, fMjRoomViewDeskRecordReq);
                break;
            case MjCmd_ViewMyRecord_Req_VALUE:
                MjCmd.FMjRoomMyRecordReq fMjRoomMyRecordReq = (MjCmd.FMjRoomMyRecordReq) CmdParse.parse(req);
                onViewMyMjRecordsReq(req, mjUser, fMjRoomMyRecordReq);
                break;
            case MjCmd_FRoomPushGift_Req_VALUE:
                MjCmd.FRoomPushGiftReq fRoomPushGiftReq = (MjCmd.FRoomPushGiftReq) CmdParse.parse(req);
                onPushGiftReq(req, mjUser, fRoomPushGiftReq);
                break;
            case MjCmd_FRoomReciveGift_Req_VALUE:
                MjCmd.FRoomReciveGiftReq fRoomReciveGiftReq = (MjCmd.FRoomReciveGiftReq) CmdParse.parse(req);
                onRecvGiftReq(req, mjUser, fRoomReciveGiftReq);
                break;
            case MjCmd_NewDesk_Req_VALUE:
                MjCmd.MjNewDeskReq mjNewDeskReq = (MjCmd.MjNewDeskReq) CmdParse.parse(req);
                onNewDesk(req, mjUser, mjNewDeskReq);
                break;
            case MjCmd_JoinDesk_Req_VALUE:
                MjCmd.MjJoinDeskReq mjJoinDeskReq = (MjCmd.MjJoinDeskReq) CmdParse.parse(req);
                onJoinDesk(req, mjUser, mjJoinDeskReq);
                break;
            case MjCmd_QuerySaleData_Req_VALUE:
                onViewSaleDataReq(req, mjUser);
                break;
            case MjCmd_MyRecordOp_Req_VALUE:
                MjCmd.FMjRoomMyRecordOpReq fMjRoomMyRecordOpReq = (MjCmd.FMjRoomMyRecordOpReq) CmdParse.parse(req);
                onReadDelUserRecord(req, mjUser, fMjRoomMyRecordOpReq);
                break;
            case MjCmd_DeskReconect_Req_VALUE:
                MjCmd.MjDeskReconectReq mjDeskReconectReq = (MjCmd.MjDeskReconectReq) CmdParse.parse(req);
                onDeskReconect(req, mjUser, mjDeskReconectReq);
                break;
            case CmdNo_GoodsList_Req_VALUE:
                onGoodsListReq(req, mjUser);
                break;
            case CmdNo_BuyGoods_Req_VALUE:
                GameCmd.BuyGoodsReq buyGoodsReq = (GameCmd.BuyGoodsReq) CmdParse.parse(req);
                onBuyGoodsReq(req, mjUser, buyGoodsReq);
                break;
            case MjCmd_OfficialGroup_Req_VALUE:
                onGetOfficialGroupsReq(req, mjUser);
                break;
            case MjCmd_PushMoneyBag_Req_VALUE:
                MjCmd.PushMoneyBagReq pushMoneyBagReq = (MjCmd.PushMoneyBagReq) CmdParse.parse(req);
                onPushMoneyBag(req, mjUser, pushMoneyBagReq);
                break;
            default:
                break;
        }
    }


    @Override
    public void onGameMSG(Packet packet) {

    }

    private void onEnterGame(Packet req) {
        long userId = req.getUserId();
        MjUser mjUser = MjUserMgr.getInstance().get(userId);

        if (mjUser != null) {
            String gateUrl = req.getSrc();
            if (!mjUser.getGateUrl().equals(gateUrl)) {
                CoreCmd.PlayerOffline.Builder respGate = CoreCmd.PlayerOffline.newBuilder();
                respGate.setUserId(userId);
                req.setSrc(mjUser.getGateUrl());
                sendToGate(req, CmdNo_PlayerOffline_VALUE, respGate.build());
                req.setSrc(gateUrl);
            }
            MjUserMgr.getInstance().remove(req.getUserId());
        }

        mjUser = newMjUser(userId);

        if (mjUser == null) {
            logger.error("创建玩家失败，req : " + req);
        }

        Define.UserInfo userInfo = CUserProvider.getUserInfo(userId);
        Define.FMjRoomPlayerInfo fMjRoomPlayerInfo = roomProvider.getRoom(userId);

        mjUser.setOffline(false);
        mjUser.setBaseInfo(userInfo);

        MailMgr mailMgr = mjUser.getMailMgr();
        mailMgr.setOwnerId(userId);
        List<Define.MailInfo> mailList = mailProvider.getMailList(userId);
        if (mailList != null && mailList.size() > 0) {
            for (Define.MailInfo mailInfo : mailList) {
                mailMgr.put(mailInfo.getMailID(), mailInfo);
            }
        }

        Bag bag = mjUser.getBag();
        bag.setOwnerId(userId);
        List<Define.BagItem> itemList = itemProvider.getItemList(userId);
        if (itemList != null && itemList.size() > 0) {
            for (Define.BagItem bagItem : itemList) {
                bag.put(bagItem.getItemGUID(), bagItem);
            }
        }

        FriendMgr friendMgr = mjUser.getFriendMgr();
        friendMgr.setOwnerId(userId);
        List<Define.MyFriend> friendList = CFriendProvider.getFriendList(userId);
        if (friendList != null && friendList.size() > 0) {
            for (Define.MyFriend myFriend : friendList) {
                Define.MyFriend.Builder fBuilder = Define.MyFriend.newBuilder().mergeFrom(myFriend);
                long fUserId = fBuilder.getUserID();
                MjUser f = MjUserMgr.getInstance().get(fUserId);
                if (f != null) {
                    fBuilder.setState(FS_Idle);
                    if (fBuilder.getDeskID() != 0) {
                        fBuilder.setState(FS_DeskWait);
                    }
                }
                friendMgr.put(fUserId, fBuilder.build());
            }
        }

        Define.FMjRoomPlayerInfo playerInfo = mjUser.getPlayerInfo();
        if (playerInfo != null && playerInfo.getOwnerDeskID() > 0) {
            Desk desk = DeskMgr.getInstance().get(playerInfo.getOwnerDeskID());
            if (desk == null) {
                mjUser.setPlayerInfo(playerInfo.toBuilder().setOwnerDeskID(0).build());
            }
        }

        routerProvider.setUserRouter(userId, ServerType.MJHALLSERVER, HALL_MSG);
        routerProvider.setUserRouter(userId, ServerType.GATESERVER, req.getSrc());

        LoginCmd.EnterGameRsp.Builder respBuilder = LoginCmd.EnterGameRsp.newBuilder();
        respBuilder.setResult(ErrCode_Success_VALUE);
        respBuilder.setUserId(userId);
        sendToGate(req, CmdNo_Login_Enter_Rsp_VALUE, respBuilder.build());

        GameCmd.UserDataNotify.Builder notifyBuilder = GameCmd.UserDataNotify.newBuilder();
        notifyBuilder.setUserData(mjUser.getBaseInfo());
        sendToGate(req, CmdNo_Login_UserData_Notify_VALUE, notifyBuilder.build());

        MjUser existMjUser = MjUserMgr.getInstance().get(userId);
        if (existMjUser != null) {
            MjCmd.FMjRoomPlayerInfoRsp.Builder playInfoRspBuilder = MjCmd.FMjRoomPlayerInfoRsp.newBuilder();
            playInfoRspBuilder.setUserID(userId);
            playInfoRspBuilder.setPlayerInfo(existMjUser.getPlayerInfo());
            sendToGate(req, MjCmd_FRoomPlayerInfo_Rsp_VALUE, playInfoRspBuilder.build());
        }

        if (mjUser != null) {
            MjCmd.FMjRoomPlayerInfoRsp.Builder playInfoRspBuilder = MjCmd.FMjRoomPlayerInfoRsp.newBuilder();
            playInfoRspBuilder.setUserID(userId);
            playInfoRspBuilder.setPlayerInfo(mjUser.getPlayerInfo());
            sendToGate(req, MjCmd_FRoomPlayerInfo_Rsp_VALUE, playInfoRspBuilder.build());
        }

        for (Define.MyFriend friend : friendMgr.getFriendList().values()) {
            MjUser f = MjUserMgr.getInstance().get(friend.getUserID());
            if (f != null) {
                Define.MyFriend mine = f.getFriendMgr().get(f.getBaseInfo().getUserID());
                if (mine != null) {
                    Define.MyFriend.Builder fBuilder = Define.MyFriend.newBuilder().mergeFrom(mine);
                    fBuilder.setState(FS_Idle);
                    fBuilder.setDeskID(0);
                    GameCmd.UpdateFriendStatus.Builder updateFriendBuilder = GameCmd.UpdateFriendStatus.newBuilder();
                    updateFriendBuilder.setFriend(fBuilder.build());
                    sendToGate(req, CmdNo_UpdateFriend_Notify_VALUE, updateFriendBuilder.build());
                    logger.info("玩家：{} 的好友：{}, 状态变更为：{}", userId, f.getBaseInfo().getUserID(), FS_Idle);
                }
            }
        }
        logger.info("玩家：{} 进入游戏成功", userId);

        // 首次登陆检查
        // checkFirstLogin(mjUser);
        // 推送数据
        // pMjUser -> PushActivityList();
        // pMjUser -> PushNoticeMsg();

        // 用户重连
        int ownerDeskID = mjUser.getPlayerInfo().getOwnerDeskID();
        if (ownerDeskID > 0) {
            Desk desk = DeskMgr.getInstance().get(ownerDeskID);
            if (desk != null) {
                logger.info("玩家重登陆，进入牌桌,USERID={},DeskID={}", userId, ownerDeskID);
                Packet toGame = new Packet();
                toGame.setUserId(userId);
                toGame.put("deskId", ownerDeskID);
                toGame.put("gateId", req.getSrc());
                toGame.setDst(mjUser.getGateUrl());
                sendToGame(toGame, MjCmd_H2G_DeskReconect_Req_VALUE);
            }
        }

        MjUserMgr.getInstance().put(userId, mjUser);
    }

    private MjUser newMjUser(long userId) {
        logger.info("玩家进入HallServer，[UID:%ld]", userId);
        MjUser mjUser = MjUserMgr.getInstance().get(userId);
        if (mjUser != null) {
            return mjUser;
        } else {
            mjUser = new MjUser();
            MjUserMgr.getInstance().put(userId, mjUser);
            return mjUser;
        }
    }

    private void onMailListReq(Packet req, MjUser mjUser, GameCmd.MyMailListReq myMailListReq) {
        GameCmd.MyMailListRsp.Builder respB = GameCmd.MyMailListRsp.newBuilder();
        int index = 0;
        for (Define.MailInfo mailInfo : mjUser.getMailMgr().getMailList().values()) {
            respB.setMailList(++index, mailInfo);
        }
        sendToGate(req, CmdNo_MailList_Rsp_VALUE, respB.build());
    }

    private void onMailReadReq(Packet req, MjUser mjUser, GameCmd.MailReadReq mailReadReq) {
        GameCmd.MailReadRsp.Builder respB = GameCmd.MailReadRsp.newBuilder();
        respB.setResultCode(ErrCode_Success_VALUE);
        long mailID = mailReadReq.getMailID();
        if (mailID <= 0) {
            logger.error("mail id is wrong");
            respB.setResultCode(-1);
        }
        Define.MailInfo mailInfo = mjUser.getMailMgr().get(mailID);
        if (mailInfo == null) {
            logger.error("mail is not exist, mail id : {}", mailID);
            respB.setResultCode(-1);
        }

        mailInfo.toBuilder().setIsRead(true);
        mjUser.getMailMgr().put(mailID, mailInfo);
        mailProvider.readMail(mailID);
        sendToGate(req, CmdNo_ReadMail_Rsp_VALUE, respB.build());
    }

    private void onMailPickReq(Packet req, MjUser mjUser, GameCmd.MailPickupAttachReq mailPickupAttachReq) {
        GameCmd.MailPickupAttachRsp.Builder respB = GameCmd.MailPickupAttachRsp.newBuilder();
        respB.setResultCode(ErrCode_Success_VALUE);
        long mailID = mailPickupAttachReq.getMailID();
        if (mailID <= 0) {
            logger.error("mail id is wrong");
            respB.setResultCode(-1);
        }
        Define.MailInfo mailInfo = mjUser.getMailMgr().get(mailID);
        if (mailInfo == null) {
            logger.error("mail is not exist, mail id : {}", mailID);
            respB.setResultCode(-1);
        }
        mailInfo.toBuilder().setIsPickup(true);
        mjUser.getMailMgr().put(mailID, mailInfo);
        mailProvider.pickMail(mailID);
        sendToGate(req, CmdNo_PickMail_Rsp_VALUE, respB.build());
    }

    private void onMyBagReq(Packet req, MjUser mjUser, GameCmd.MyBagItemListRsp myBagItemListRsp) {
        GameCmd.MyBagItemListRsp.Builder respB = GameCmd.MyBagItemListRsp.newBuilder();
        int index = 0;
        for (Define.BagItem bagItem : mjUser.getBag().getItemList().values()) {
            respB.setItemList(++index, bagItem);
        }
        sendToGate(req, CmdNo_BagItemList_Rsp_VALUE, respB.build());
    }

    private void onVerifyCodeReq(Packet req, MjUser mjUser, GameCmd.GetVerifyCodeReq verifyCodeReq) {
        String phoneNo = verifyCodeReq.getPhoneNo();
        if (phoneNo.length() != 11) {
            logger.error("非法的手机号：phone:" + phoneNo);
        }

        // 获取短信发送配置
        String url = null;
        String account = null;
        String password = null;
        String content = null;

        // 如果配置信息不存在，退出
        long now = System.currentTimeMillis();
        // 检查验证码是否超时，可以使用Guava Cache实现
        */
/*if (now >= (m_VerifyTime+15*60))
        {
            int32 nFirstID = rand() % 10;
            nFirstID = nFirstID == 0 ? 1 : nFirstID;
            int32 nCode = nFirstID * 1000 + (rand() % 10) * 100 + (rand() % 10) * 10 + rand() % 10;
            m_VerifyCode = nCode;
            m_VerifyTime = nNowTime;
        }*//*


        // 如果存在，则使用原来的，如果超时，则新创建
        String code = null;
        content = String.format("%s[%d]", content, code);
        try {
            content = URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("短信内容encode错误" + content, e);
            return;
        }

        HttpClient.sendRequestByGet(
                String.format("%s?OperID=%s&OperPass=%s&SendTime=&ValidTime=&AppendID=1234&DesMobile=%s&Content=%s&ContentType=8",
                        url, account, password, phoneNo, content));
    }

    private void onBindPhoneReq(Packet req, MjUser mjUser, GameCmd.BindPhoneReq bindPhoneReq) {
        GameCmd.BindPhoneRsp.Builder respB = GameCmd.BindPhoneRsp.newBuilder();
        respB.setResultCode(ErrCode_Success_VALUE);

        String phone = bindPhoneReq.getPhoneNo();
        String code = bindPhoneReq.getVerifyCode();

        if (StringUtils.isBlank(phone) || phone.length() != 11) {
            logger.error("手机号码非法={}", phone);
            respB.setResultCode(-1);
        }

        String rightcode = null;
        if (StringUtils.isBlank(code) || phone.length() != 4 || code != rightcode) {
            logger.error("非法验证码={}, 正确验证码={}", code, rightcode);
            respB.setResultCode(-1);
        }

        mjUser.setBaseInfo(mjUser.getBaseInfo().toBuilder().setPhoneNo(phone).build());
        CUserProvider.updatePhone(mjUser.getBaseInfo().getUserID(), phone);
        sendToGate(req, CmdNo_BindPhone_Rsp_VALUE, respB.build());
    }

    private void onCertification(Packet req, MjUser mjUser, GameCmd.CertificationReq certificationReq) {
        GameCmd.CertificationRsp.Builder respB = GameCmd.CertificationRsp.newBuilder();
        respB.setResultCode(ErrCode_Success_VALUE);

        String realName = certificationReq.getRealName();
        String idCard = certificationReq.getIDCard();
        if (StringUtils.isBlank(realName) || realName.length() <= 0
                || StringUtils.isBlank(idCard) || idCard.length() != 15 || idCard.length() != 18) {
            logger.error("姓名为空或者身份证号非法, name :" + realName + " idCard : " + idCard);
            respB.setResultCode(-1);
        }

        CUserProvider.updateCertification(mjUser.getBaseInfo().getUserID(), realName, idCard);
        sendToGate(req, CmdNo_Certification_Rsp_VALUE, respB.build());

    }

    private void onModifyUserInfo(Packet req, MjUser mjUser, GameCmd.ModifyUserInfoReq userInfoReq) {
        GameCmd.ModifyUserInfoRsp.Builder respB = GameCmd.ModifyUserInfoRsp.newBuilder();
        respB.setResult(ErrCode_Success_VALUE);

        String name = userInfoReq.getName();
        Define.UserInfo baseUserInfo = mjUser.getBaseInfo().toBuilder().setName(name).build();
        mjUser.setBaseInfo(baseUserInfo);

        CUserProvider.updateName(mjUser.getBaseInfo().getUserID(), name);

        sendToGate(req, CmdNo_ModifyInfo_Rsp_VALUE, respB.build());
    }

    private void onSetRegionReq(Packet req, MjUser mjUser, GameCmd.UserRegionSetupReq regionSetupReq) {
        GameCmd.UserRegionSetupRsp.Builder respB = GameCmd.UserRegionSetupRsp.newBuilder();
        respB.setResultCode(ErrCode_Success_VALUE);

        Define.UserInfo newUserInfo = mjUser.getBaseInfo().toBuilder().setRegionID(regionSetupReq.getRegionID()).build();
        mjUser.setBaseInfo(newUserInfo);

        CUserProvider.updateRegion(regionSetupReq.getRegionID());
        sendToGate(req, CmdNo_UserRegionSetup_Rsp_VALUE, respB.build());
    }

    private void onGetAwardConfig(Packet req, MjUser mjUser, GameCmd.GetAwardConfigReq awardConfigReq) {
        GameCmd.GetAwardConfigRsp.Builder respB = GameCmd.GetAwardConfigRsp.newBuilder();

        // 获取awardList
        Define.AwardConfig.Builder awardConfigB = Define.AwardConfig.newBuilder();

        // sendToGate();
    }

    private void onLotteryReq(Packet req, MjUser mjUser) {
        GameCmd.LotteryRsp.Builder respB = GameCmd.LotteryRsp.newBuilder();
        respB.setResultCode(ErrCode_Success_VALUE);
        if (mjUser.getBaseInfo().getLotteryTimes() > 0) {
            logger.info("重复抽奖，今日已经抽奖,玩家 : {}", mjUser.getBaseInfo().getUserID());
            respB.setResultCode(ErrCode_Sign_Repeated_VALUE);
            sendToGate(req, CmdNo_Lottery_Rsp_VALUE, respB.build());
        }

        */
/*int32 nCount = GameConfig::getInstance().m_AwardList.size();
        int32 nIndex = rand() % nCount;
        int32 nAwardID = GameConfig::getInstance().m_AwardList[nIndex].awardid();
        pMjUser->m_PickupAwardID = nAwardID;
        pMjUser->AddLotteryTimes();
        rsp.set_awardid(nAwardID);
        pMjUser->sendCmd2User(&rsp, Msg::CmdNo_Lottery_Rsp);
        return true;*//*

    }

    private void onPickAwardReq(Packet req, MjUser mjUser, GameCmd.PickupAwardReq pickupAwardReq) {
        GameCmd.PickupAwardRsp.Builder respB = GameCmd.PickupAwardRsp.newBuilder();
        respB.setResultCode(ErrCode_Success_VALUE);
        if (mjUser.getPickupAwardID() != pickupAwardReq.getAwardID()) {
            logger.error("玩家非法领取奖品,User:{},AwardID={},RealAwardID={}", mjUser.getBaseInfo().getUserID(), pickupAwardReq.getAwardID(), mjUser.getPickupAwardID());
            respB.setResultCode(-1);
            sendToGate(req, CmdNO_PickupAward_Rsp_VALUE, respB.build());
        }

        */
/*//*
/ 奖品兑现
        Msg::AwardConfig* pAwardConfig = GameConfig::getInstance().GetAwardConfig(pMjUser->m_PickupAwardID);
        if (pAwardConfig)
        {
            pMjUser->addTickets(pAwardConfig->awardcount());
        }
        pMjUser->m_PickupAwardID = 0;
        pMjUser->sendCmd2User(&rsp, Msg::CmdNO_PickupAward_Rsp);
        return true;*//*

    }

    private void onGetInviteeListReq(Packet req, MjUser mjUser) {
        GameCmd.GetInviteeListRsp.Builder respB = GameCmd.GetInviteeListRsp.newBuilder();
        respB.setResultCode(ErrCode_Success_VALUE);

        long userID = mjUser.getBaseInfo().getUserID();

        Map<String, Object> inviteeList = CUserProvider.loadInviteeList(userID);
        int totalTickets = (int) inviteeList.get("totalTickets");
        List<Define.InviteeFriendInfo> list = (List<Define.InviteeFriendInfo>) inviteeList.get("inviteeFriendInfoList");

        respB.setTotalTickets(totalTickets);
        respB.setInviteeCount(list.size());
        for (int i = 0; i < list.size(); i++) {
            respB.setInviteeList(i, list.get(i));
        }

        sendToGate(req, CmdNo_GetInviteeList_Rsp_VALUE, respB.build());
    }

    private void onBindInviteCodeReq(Packet req, MjUser mjUser, GameCmd.BindInviteCodeReq bindInviteCodeReq) {
        GameCmd.BindInviteCodeRsp.Builder respB = GameCmd.BindInviteCodeRsp.newBuilder();
        respB.setResultCode(ErrCode_Success_VALUE);

        if (bindInviteCodeReq.getInviteCode().length() != 8) {
            respB.setResultCode(ErrCode_BindInviteCode_Failed_VALUE);
            logger.error("非法邀请码,{}", bindInviteCodeReq.getInviteCode());
            sendToGate(req, CmdNo_BindInviteCode_Rsp_VALUE, respB.build());
        }

        // todo 校验邀请码是否是全数字

        if (mjUser.getBaseInfo().getInviteCode().length() > 0) {
            respB.setResultCode(ErrCode_HasBind_InviteCode_VALUE);
            sendToGate(req, CmdNo_BindInviteCode_Rsp_VALUE, respB.build());
            return;
        }

        if (mjUser.getBaseInfo().getInviteCode().equals(bindInviteCodeReq.getInviteCode())) {
            respB.setResultCode(ErrCode_BindInviteCode_Failed_VALUE);
            sendToGate(req, CmdNo_BindInviteCode_Rsp_VALUE, respB.build());
            return;
        }

        boolean bindResult = CUserProvider.bindInviteCode(mjUser.getBaseInfo().getUserID(), bindInviteCodeReq.getInviteCode());

        Map<String, Object> rtmCfg = configProvider.getRtmCfg(1004);
        if (rtmCfg == null || rtmCfg.size() <= 0) {
            logger.error("1004参数不存在");
            return;
        }
        sendToGate(req, CmdNo_BindInviteCode_Rsp_VALUE, respB.build());

        // 绑定存储过程已经持久化修改，这里只需要修改运行时数据即可
        Define.UserInfo.Builder baseInfoB = mjUser.getBaseInfo().toBuilder().setBeInviteeCode(bindInviteCodeReq.getInviteCode());

        int initData = (int) rtmCfg.get("initData");
        baseInfoB.setTickets(mjUser.getBaseInfo().getTickets() + initData);

        mjUser.setBaseInfo(baseInfoB.build());

        syncMoney(req, mjUser.getBaseInfo());
    }

    private void onPickInviteAwardReq(Packet req, MjUser mjUser, GameCmd.PickInviteAwardReq pickInviteAqardReq) {
        GameCmd.PickInviteAwardRsp.Builder respB = GameCmd.PickInviteAwardRsp.newBuilder();
        respB.setResultCode(ErrCode_Success_VALUE);

        Map<String, Object> rtmCfg = configProvider.getRtmCfg(1004);
        if (rtmCfg == null || rtmCfg.size() <= 0) {
            logger.error("1004参数不存在");
            return;
        }

        boolean result = CUserProvider.updateInviteeRecord(mjUser.getBaseInfo().getUserID(), pickInviteAqardReq.getInviteeUseID(), (Integer) rtmCfg.get("initData"));
        if (!result) {
            respB.setResultCode(ErrCode_PickInviteAward_NotUser_VALUE);
            sendToGate(req, CmdNo_PickInviteAward_Rsp_VALUE, respB.build());
            return;
        }

        int initData = (int) rtmCfg.get("initData");
        mjUser.setBaseInfo(mjUser.getBaseInfo().toBuilder().setTickets(mjUser.getBaseInfo().getTickets() + initData).build());

        respB.setInviteeUserID(mjUser.getBaseInfo().getUserID());
        respB.setInviteeStatus(Define.PickupAwardStatus.Pickuped);
        sendToGate(req, CmdNo_PickInviteAward_Rsp_VALUE, respB.build());
    }

    private void onViewOtherPlayerReq(Packet req, MjUser mjUser, GameCmd.OtherPlayerInfoReq otherPlayerInfoReq) {
        GameCmd.OtherPlayerInfoRsp.Builder respB = GameCmd.OtherPlayerInfoRsp.newBuilder();
        if (mjUser != null) {
            respB.setBaseInfo(mjUser.getBaseInfo());
            respB.setMjDataInfo(mjUser.getPlayerInfo());
        } else {
            Define.UserInfo userInfo = CUserProvider.loadUser(mjUser.getBaseInfo().getUserID(), mjUser.getBaseInfo());
            Define.FMjRoomPlayerInfo fMjRoomPlayerInfo = CUserProvider.queryUser(mjUser.getBaseInfo().getUserID());
            respB.setBaseInfo(userInfo);
            respB.setMjDataInfo(fMjRoomPlayerInfo);
        }
        sendToGate(req, CmdNo_OtherPlayer_Rsp_VALUE, respB.build());
    }

    private void onGetMyFriendsReq(Packet req, MjUser mjUser) {
        GameCmd.MyFriendsListRsp.Builder respB = GameCmd.MyFriendsListRsp.newBuilder();
        respB.setResultCode(0);
        Collection<Define.MyFriend> values = mjUser.getFriendMgr().getFriendList().values();
        int count = 0;
        for (Define.MyFriend myFriend : values) {
            respB.setFriendList(count++, myFriend);
        }
        sendToGate(req, CmdNo_MyFirends_Rsp_VALUE, respB.build());
    }

    private void onAddFriendReq(Packet req, MjUser mjUser, GameCmd.AddFriendReq addFriendReq) {
        GameCmd.AddFriendRsp.Builder respB = GameCmd.AddFriendRsp.newBuilder();
        respB.setResultCode(ErrCode_Success_VALUE);
        MjUser dstUser = MjUserMgr.getInstance().get(addFriendReq.getDestID());
        if (dstUser != null) {
            Define.MyFriend.Builder friendB = Define.MyFriend.newBuilder()
                    .setUserID(addFriendReq.getUserID())
                    .setName(mjUser.getBaseInfo().getName())
                    .setHeadURL(mjUser.getBaseInfo().getHeadURL())
                    .setIsTemp(true);
            dstUser.getFriendMgr().put(friendB.getUserID(), friendB.build());
            boolean result = CFriendProvider.addFriend(dstUser.getBaseInfo().getUserID(), friendB.getUserID(), friendB.getName(), friendB.getHeadURL(), true);
            if (!result) {
                respB.setResultCode(-1);
            } else {
                GameCmd.MyFriendsListRsp.Builder friendList = GameCmd.MyFriendsListRsp.newBuilder();
                friendList.addFriendList(friendB.build());
                sendToGate(req, CmdNo_MyFirends_Rsp_VALUE, friendList.build());
            }
        } else {
            boolean result = CFriendProvider.addFriend(dstUser.getBaseInfo().getUserID(), mjUser.getBaseInfo().getUserID(), mjUser.getBaseInfo().getName(), mjUser.getBaseInfo().getHeadURL(), true);
            if (!result) {
                respB.setResultCode(-1);
            }
        }
        sendToGate(req, CmdNo_AddFriend_Rsp_VALUE, respB.build());
    }

    private void onDelFriendReq(Packet req, MjUser mjUser, GameCmd.DelFriendReq delFriendReq) {
        MjUser dstUser = MjUserMgr.getInstance().get(delFriendReq.getDestID());
        if (dstUser != null) {
            dstUser.getFriendMgr().remove(mjUser.getBaseInfo().getUserID());
            CUserProvider.delFriend(dstUser.getFriendMgr().getOwnerId(), delFriendReq.getDestID());
            GameCmd.DelFriendNotify.Builder notify = GameCmd.DelFriendNotify.newBuilder();
            notify.setUserID(delFriendReq.getDestID());
            notify.setDestID(delFriendReq.getUserID());
            sendToGate(req, CmdNo_DelFriend_Notify_VALUE, notify.build());
        } else {
            CUserProvider.delFriend(delFriendReq.getDestID(), delFriendReq.getUserID());
        }
        GameCmd.DelFriendRsp.Builder respB = GameCmd.DelFriendRsp.newBuilder();
        respB.setResultCode(ErrCode_Success_VALUE);
        sendToGate(req, CmdNo_DelFriend_Rsp_VALUE, respB.build());
    }

    private void onApplyFriendReq(Packet req, MjUser mjUser, GameCmd.ApplyFriendReq applyFriendReq) {
        GameCmd.ApplyFriendRsp.Builder respB = GameCmd.ApplyFriendRsp.newBuilder();
        respB.setResultCode(ErrCode_Success_VALUE);

        if (applyFriendReq.getIsConfirm() == false) {
            mjUser.getFriendMgr().remove(mjUser.getBaseInfo().getUserID());
            CUserProvider.delFriend(mjUser.getFriendMgr().getOwnerId(), applyFriendReq.getDestID());
        } else {
            Define.MyFriend friend = mjUser.getFriendMgr().get(applyFriendReq.getDestID());
            friend.toBuilder().setIsTemp(false).setState(FS_Idle);
            boolean result = CUserProvider.applyFriend(mjUser.getFriendMgr().getOwnerId(), mjUser.getBaseInfo().getUserID());
            if (result) {
                GameCmd.MyFriendsListRsp.Builder listRsp = GameCmd.MyFriendsListRsp.newBuilder();
                Define.MyFriend myFriend = mjUser.getFriendMgr().get(applyFriendReq.getDestID());
                listRsp.addFriendList(myFriend);

                MjUser friendUser = MjUserMgr.getInstance().get(applyFriendReq.getDestID());
                if (friendUser != null) {
                    Define.MyFriend.Builder friendB = Define.MyFriend.newBuilder();
                    friendB.setUserID(mjUser.getBaseInfo().getUserID());
                    friendB.setName(mjUser.getBaseInfo().getName());
                    friendB.setHeadURL(mjUser.getBaseInfo().getHeadURL());
                    friendB.setState(FS_Idle);
                    friendUser.getFriendMgr().put(friendB.getUserID(), friendB.build());

                    sendToGate(req, CmdNo_MyFirends_Rsp_VALUE, listRsp.build());
                } else {
                    CFriendProvider.addFriend(applyFriendReq.getDestID(), applyFriendReq.getUserID(), mjUser.getBaseInfo().getName(), mjUser.getBaseInfo().getHeadURL(), false);
                }
            } else {
                respB.setResultCode(-1);
            }
            sendToGate(req, CmdNo_ApplyFriend_Rsp_VALUE, respB.build());
        }
    }

    private void onInviteGameReq(Packet req, MjUser mjUser, GameCmd.InviteFriendGameReq inviteFriendGameReq) {
        */
/*MjUser dstUser = MjUserMgr.getInstance().get(inviteFriendGameReq.getUserID());
        GameCmd.InviteFriendGameRsp.Builder respB = GameCmd.InviteFriendGameRsp.newBuilder();
        respB.setResultCode(ErrCode_Success_VALUE);
        int inDeskID = dstUser.getInDeskID();
        if (inDeskID > 0) {
            respB.setResultCode(ErrCode_Friend_InDesk_VALUE);
            sendToGate(req, CmdNo_InviteGame_Rsp_VALUE, respB.build());
            return;
        }
        GameCmd.InviteFriendGameNotify.Builder notify = GameCmd.InviteFriendGameNotify.newBuilder();
        notify.setDeskID(inviteFriendGameReq.getDeskID());
        notify.setUserID(mjUser.getBaseInfo().getUserID());
        if (mjUser.getPlayerInfo().getBindRoomID() > 0) {
            FriendRoomMgr.
        }*//*

    }

    private void onRefuseInviteReq(Packet req, MjUser mjUser, GameCmd.FefuseFriendReq fefuseFriendReq) {

    }

    private void onJoinFriendDeskReq(Packet req, MjUser mjUser, GameCmd.JoinFriendMjDeskReq joinFriendMjDeskReq) {

    }

    private void onRecvDeskJoinReq(Packet req, MjUser mjUser, GameCmd.RecvDeskJoinReq recvDeskJoinReq) {

    }

    private void onMjUserInfo(Packet req, MjUser mjUser, MjCmd.FMjRoomPlayerInfoReq fMjRoomPlayerInfoReq) {

    }

    private void onHallInfoReq(Packet req, MjUser mjUser) {
        Map<String, Object> rtmCfg = configProvider.getRtmCfg(1005);
        String hallDesc = null;
        if (rtmCfg != null && rtmCfg.size() > 0) {
            hallDesc = JSONUtil.toJson(rtmCfg);
        }
        if (hallDesc != null) {
            MjCmd.FMjRoomDescInfoRsp fMjRoomDescInfoRsp = MjCmd.FMjRoomDescInfoRsp.newBuilder().setDescInfo(hallDesc).build();
            sendToGate(req, MjCmd_FRoomInfo_Rsp_VALUE, fMjRoomDescInfoRsp);
        }
    }

    private void onSearchReq(Packet req, MjUser mjUser, MjCmd.FMjRoomSearchReq msg) {
        MjCmd.FMjRoomSearchRsp.Builder resp = MjCmd.FMjRoomSearchRsp.newBuilder();
        resp.setResultCode(ErrCode_Success_VALUE);

        int lastSearchTime = mjUser.getLastSearchTime();
        int now = (int) (System.currentTimeMillis() / 1000);

        if (now - lastSearchTime < 3) {
            return;
        }
        mjUser.setLastSearchTime(now);
        int msgCounter = 0;
        if (msg.getRoomKey().length() == 0) {
            List<Define.FMjRoom> mjRooms = roomProvider.queryRoomByRegionId(msg.getRegionID(), msg.getSortType());
            for (int i = 0; i < mjRooms.size(); i++) {
                FriendRoom friendRoom = FriendRoomMgr.getInstance().get(mjRooms.get(i).getRoomID());
                Define.FMjRoom fMjRoom = mjRooms.get(i);
                resp.addRoomList(i, mjRooms.get(i));
                Define.FMjRoom newMjRoom = fMjRoom.toBuilder().setOnlineNum(friendRoom.getPlayerList().size()).build();
                mjRooms.set(i, newMjRoom);
                msgCounter++;
                if (msgCounter >= 5) {
                    msgCounter = 0;
                    if (i == mjRooms.size() - 1) {
                        resp.setIsFinish(true);
                    } else {
                        resp.setIsFinish(false);
                    }
                    sendToGate(req, MjCmd_FRoomSearch_Rsp_VALUE, resp.build());
                    resp.clearRoomList();
                }
            }
            if (resp.getRoomListList().size() > 0) {
                resp.setIsFinish(true);
                sendToGate(req, MjCmd_FRoomSearch_Rsp_VALUE, resp.build());
            }
        }

        String roomKey = msg.getRoomKey();
        boolean numberic = true;
        */
/*for (int i = 0; i < roomKey.length(); i++) {
            if ()
        }*//*

        // todo 校验roomKey是否全是数字
        if (numberic) {
            if (roomKey.length() == 11) {
                Define.FMjRoom fMjRoom = roomProvider.queryRoomByPhone(roomKey);
                if (fMjRoom != null) {
                    resp.addRoomList(fMjRoom);
                }
            } else {
                int id = Integer.valueOf(msg.getRoomKey());
                Define.FMjRoom fMjRoom = roomProvider.queryRoomById(id);
                if (fMjRoom != null) {
                    resp.addRoomList(fMjRoom);
                }
            }
        } else {
            List<Define.FMjRoom> mjRooms = roomProvider.queryRoomByName(msg.getRoomKey());
            int count = mjRooms.size();
            for (int i = 0; i < count; i++) {
                resp.setRoomList(i, mjRooms.get(i));
            }
        }
        sendToGate(req, MjCmd_FRoomSearch_Rsp_VALUE, resp.build());
    }

    private void onBindReq(Packet req, MjUser mjUser, MjCmd.FMjRoomBindReq msg) {
        FriendRoom friendRoom = FriendRoomMgr.getInstance().get(msg.getRoomID());
        if (friendRoom == null) {
            friendRoom = loadFriendRoomFromDb(msg.getRoomID());
            if (friendRoom == null) {
                logger.error("麻将馆不存在,id : {}", msg.getRoomID());
                return;
            }
        }

        MjCmd.FMjRoomBindRsp.Builder rsp = MjCmd.FMjRoomBindRsp.newBuilder();
        rsp.setRoomID(msg.getRoomID());
        rsp.setUserID(mjUser.getBaseInfo().getUserID());
        if (mjUser.getPlayerInfo().getBindRoomID() > 0) {
            rsp.setResultCode(1);
        } else {
            if (friendRoom.getMemberList().containsKey(mjUser.getBaseInfo().getUserID())) {
                logger.warn("玩家已经绑定该麻将馆，USER={},RID={]", mjUser.getBaseInfo().getUserID(), friendRoom.getRoomInfo().getRoomID());
                rsp.setResultCode(-1);
            } else {
                FriendPlayerInfo fPlayer = new FriendPlayerInfo();
                fPlayer.setUserId(mjUser.getBaseInfo().getUserID());
                friendRoom.getMemberList().put(mjUser.getBaseInfo().getUserID(), fPlayer);
                rsp.setResultCode(ErrCode_Success_VALUE);
                mjUser.setPlayerInfo(mjUser.getPlayerInfo().toBuilder().setBindRoomID(msg.getRoomID())
                        .setBindRoomState(Bind_OK_VALUE).build());
                roomProvider.updateMjUserAttr(mjUser.getPlayerInfo());
                if (mjUser.getPlayerInfo().getTerminateRoomTime() == 0) {
                    rsp.setIsFirst(true);
                }
                syncMjUser2Client(req, mjUser);
                sendToStat(CoreCmd.StatRoomBindLogReq.newBuilder().setUserID(mjUser.getBaseInfo().getUserID()).setUserName(mjUser.getBaseInfo().getName())
                        .setAgentID(friendRoom.getRoomInfo().getAgentID()).build());
            }
        }
        sendToGate(req, MjCmd_FRoomBind_Rsp_VALUE, rsp.build());
        if (rsp.getResultCode() == ErrCode_Success_VALUE && rsp.getIsFirst()) {
            mjUser.setBaseInfo(mjUser.getBaseInfo().toBuilder().setTickets(10).build());
        }
    }

    private void onUnBindReq(Packet req, MjUser mjUser, MjCmd.FMjRoomUnBindReq msg) {
        FriendRoom friendRoom = FriendRoomMgr.getInstance().get(msg.getRoomID());
        if (friendRoom == null) {
            friendRoom = loadFriendRoomFromDb(msg.getRoomID());
            if (friendRoom == null) {
                logger.error("麻将馆不存在,id : {}", msg.getRoomID());
                return;
            }
        }

        MjCmd.FMjRoomUnBindRsp.Builder rsp = MjCmd.FMjRoomUnBindRsp.newBuilder().setRoomID(msg.getRoomID()).setUserID(mjUser.getBaseInfo().getUserID());
        //0.检查绑定状态
        if (mjUser.getPlayerInfo().getBindRoomState() == Bind_OK_VALUE) {
            //2. 设置绑定状态
            mjUser.setPlayerInfo(mjUser.getPlayerInfo().toBuilder().setBindRoomState(UnBinding_VALUE).build());
            //3. 通知客户端同步Info
            rsp.setResultCode(ErrCode_Success_VALUE);
            syncMjUser2Client(req, mjUser);
            roomProvider.updateMjUserAttr(mjUser.getPlayerInfo());
            //4.响应客户端请求
            sendToGate(req, MjCmd_FRoomUnBind_Rsp_VALUE, rsp.build());
            roomProvider.addUnbinLog(mjUser.getBaseInfo().getUserID(), friendRoom.getRoomInfo().getAgentID(), mjUser.getBaseInfo().getName());
        } else {
            rsp.setResultCode(-1);
            sendToGate(req, MjCmd_FRoomUnBind_Rsp_VALUE, rsp.build());
        }
    }

    private void onTuijianReq(Packet req, MjUser mjUser) {
        MjCmd.FMjRoomTuijianRsp.Builder rsp = MjCmd.FMjRoomTuijianRsp.newBuilder();
        List<Define.FMjRoom> mjRooms = roomProvider.queryRoomRecommand();
        for (int i = 0; i < mjRooms.size(); i++) {
            rsp.addRoomList(i, mjRooms.get(i));
        }
        sendToGate(req, MjCmd_FRoomTuijian_Rsp_VALUE, rsp.build());
    }

    private void onEnterReq(Packet req, MjUser mjUser, MjCmd.FMjRoomEnterReq msg) {
        FriendRoom friendRoom = FriendRoomMgr.getInstance().get(msg.getRoomID());
        if (friendRoom == null) {
            friendRoom = loadFriendRoomFromDb(msg.getRoomID());
            if (friendRoom == null) {
                logger.error("麻将馆不存在,id : {}", msg.getRoomID());
                return;
            }
        }
        MjCmd.FMjRoomEnterRsp.Builder rsp = MjCmd.FMjRoomEnterRsp.newBuilder();
        rsp.setRoomID(msg.getRoomID());
        rsp.setUserID(mjUser.getBaseInfo().getUserID());

        FriendPlayerInfo fPlayer = new FriendPlayerInfo();
        fPlayer.setUserId(mjUser.getBaseInfo().getUserID());
        friendRoom.getPlayerList().put(fPlayer.getUserId(), fPlayer);

        mjUser.setInRoomID(msg.getRoomID());
        rsp.setResultCode(ErrCode_Success_VALUE);

        sendToGate(req, MjCmd_FRoomEnter_Rsp_VALUE, rsp.build());
    }

    private void onLeaveReq(Packet req, MjUser mjUser, MjCmd.FMjRoomLeaveReq msg) {
        FriendRoom friendRoom = FriendRoomMgr.getInstance().get(msg.getRoomID());
        if (friendRoom == null) {
            friendRoom = loadFriendRoomFromDb(msg.getRoomID());
            if (friendRoom == null) {
                logger.error("麻将馆不存在,id : {}", msg.getRoomID());
                return;
            }
        }
        MjCmd.FMjRoomLeaveRsp.Builder rsp = MjCmd.FMjRoomLeaveRsp.newBuilder();
        rsp.setRoomID(msg.getRoomID());
        rsp.setUserID(mjUser.getBaseInfo().getUserID());
        friendRoom.getPlayerList().remove(mjUser.getBaseInfo().getUserID());

        mjUser.setInRoomID(0);
        rsp.setResultCode(ErrCode_Success_VALUE);
        sendToGate(req, MjCmd_FRoomLeave_Rsp_VALUE, rsp.build());
    }

    private void onRulerConfigReq(Packet req, MjUser mjUser) {
        MjCmd.MjRulerConfigRsp.Builder rsp = MjCmd.MjRulerConfigRsp.newBuilder();
        List<Define.MjGameConfig> mjGameConfigs = configProvider.getMjGameConfigList();
        for (int i = 0; i < mjGameConfigs.size(); i++) {
            rsp.clear();
            rsp.addMjGameList(i, mjGameConfigs.get(i));
            sendToGate(req, MjCmd_RulerConfig_Rsp_VALUE, rsp.build());
        }
        rsp.clear();
        List<Define.RulerTypeConfig> rulerTypeConfigs = configProvider.getRulerTypeList();
        for (int i = 0; i < rulerTypeConfigs.size(); i++) {
            rsp.clear();
            rsp.addRulerTypeList(i, rulerTypeConfigs.get(i));
        }
        sendToGate(req, MjCmd_RulerConfig_Rsp_VALUE, rsp.build());
    }

    private void onChangeBoardReq(Packet req, MjUser mjUser, MjCmd.FMjRoomChangeBoardReq msg) {
        FriendRoom friendRoom = FriendRoomMgr.getInstance().get(msg.getRoomID());
        if (friendRoom == null) {
            logger.error("非法麻将馆={}", msg.getRoomID());
            return;
        }

        MjCmd.FMjRoomChangeBoardRsp.Builder rsp = MjCmd.FMjRoomChangeBoardRsp.newBuilder();
        rsp.setResultCode(ErrCode_Success_VALUE);
        if (msg.getBoardKey().length() == 0) {
            rsp.setResultCode(ErrCode_FRoom_BoardInvalid_VALUE);
            sendToGate(req, MjCmd_FRoomChangeBoard_Rsp_VALUE, rsp.build());
        }
        sendToGate(req, MjCmd_FRoomChangeBoard_Rsp_VALUE, rsp.build());

        String boardKey = msg.getBoardKey();
        friendRoom.setRoomInfo(friendRoom.getRoomInfo().toBuilder().setBoardID(boardKey).build());
        roomProvider.updateFRoomBroad(msg.getRoomID(), boardKey);

        MjCmd.FMjRoomChangeBoardNotify.Builder notify = MjCmd.FMjRoomChangeBoardNotify.newBuilder();
        notify.setRoomID(msg.getRoomID());
        notify.setBoardKey(msg.getBoardKey());

        sendToGate(mjUser.getBaseInfo().getUserID(), MjCmd_FRoomChangeBoard_Notify_VALUE, rsp.build());
    }

    private void onViewMjRoomReq(Packet req, MjUser mjUser) {
        MjCmd.MjFMjRoomRsp.Builder rsp = MjCmd.MjFMjRoomRsp.newBuilder();
        rsp.setResultCode(ErrCode_Success_VALUE);

        int roomId = mjUser.getPlayerInfo().getOwnerRoomID();
        FriendRoom friendRoom = FriendRoomMgr.getInstance().get(roomId);
        if (friendRoom == null) {
            logger.error("非法麻将馆={}", roomId);
            return;
        }

        rsp.setMjRoomInfo(friendRoom.getRoomInfo());
        List<Integer> deskList = friendRoom.getDeskList();
        for (int i = 0; i < deskList.size(); i++) {
            Desk desk = DeskMgr.getInstance().get(deskList.get(i));
            if (desk != null) {
                rsp.addMjDeskList(desk.getDeskData());
            }
        }
        logger.info("麻将馆：{} 当前牌桌数：{},{}", roomId, deskList.size(), rsp.toString());

        sendToGate(req, MjCmd_FRoomMyRoom_Rsp_VALUE, rsp.build());
    }

    private void onViewDeskRecordReq(Packet req, MjUser mjUser, MjCmd.FMjRoomViewDeskRecordReq msg) {
        MjCmd.FMjRoomViewDeskRecordRsp.Builder rsp = MjCmd.FMjRoomViewDeskRecordRsp.newBuilder();
        rsp.setResultCode(ErrCode_Success_VALUE);

        Define.MjDeskRecord mjDeskRecord = roomProvider.queryMjDeskRecord(msg.getDeskID());
        rsp.addRecordList(mjDeskRecord);

        sendToGate(req, MjCmd_FRoomDeskRecord_Rsp_VALUE, rsp.build());
    }

    private void onViewMyMjRecordsReq(Packet req, MjUser mjUser, MjCmd.FMjRoomMyRecordReq msg) {
        if (mjUser == null) {
            logger.error("非法的玩家请求");
            return;
        }

        MjCmd.FMjRoomMyRecordRsp.Builder rsp = MjCmd.FMjRoomMyRecordRsp.newBuilder();
        List<Define.MjDeskRecord> mjDeskRecords = roomProvider.queryUserMjRecord(mjUser.getBaseInfo().getUserID());
        if (mjDeskRecords != null) {
            int msgCount = 0;
            int count = mjDeskRecords.size();
            for (int i = 0; i < count; i++) {
                rsp.addRecordList(i, mjDeskRecords.get(i));
                for (int j = 0; j < mjDeskRecords.get(i).getBoutsRecordList().size(); j++) {
                    Define.MjBureauDetialInfo.Builder boutsRecordB = mjDeskRecords.get(i).getBoutsRecord(j).toBuilder();
                    Define.BestMjRecord.Builder infoB = boutsRecordB.getMjRecords().toBuilder().clear();
                    boutsRecordB.setMjRecords(infoB.build());
                    for (int k = 0; k < boutsRecordB.getBureauInfo().getNDetailScoreList().size(); k++) {
                        boutsRecordB.setMjRecords(boutsRecordB.getMjRecords().toBuilder().setScoreChange(k, boutsRecordB.getBureauInfo().getNDetailScore(k).getScore()).build());
                    }
                }
                logger.info("战绩牌桌：{}", mjDeskRecords.get(i).getDeskID());
                msgCount++;
                if (msgCount >= 4) {
                    if (i == count - 1) {
                        rsp.setIsFinish(true);
                    } else {
                        rsp.setIsFinish(false);
                    }
                    sendToGate(req, MjCmd_ViewMyRecord_Rsp_VALUE, rsp.build());
                    msgCount = 0;
                    rsp.clearRecordList();
                }
            }
            if (rsp.getRecordListList().size() > 0) {
                rsp.setIsFinish(true);
                sendToGate(req, MjCmd_ViewMyRecord_Rsp_VALUE, rsp.build());
            }
        } else {
            rsp.setIsFinish(true);
            sendToGate(req, MjCmd_ViewMyRecord_Rsp_VALUE, rsp.build());
        }
    }

    private void onPushGiftReq(Packet req, MjUser mjUser, MjCmd.FRoomPushGiftReq msg) {
        MjCmd.FRoomPushGiftRsp.Builder rsp = MjCmd.FRoomPushGiftRsp.newBuilder();
        rsp.setResultCode(ErrCode_Success_VALUE);

        int roomID = mjUser.getPlayerInfo().getOwnerRoomID();
        if (roomID == 0) {
            // 不是馆长
            rsp.setResultCode(ErrCode_FRoom_NotOwner_VALUE);
            sendToGate(req, MjCmd_FRoomPushGift_Rsp_VALUE, rsp.build());
        }

        // 检查房卡是否足够
        Define.GiftDef giftDef = configProvider.getGiftDef(msg.getGiftID());
        int giftPrice = giftDef.getGiftPrice();
        int tickets = mjUser.getBaseInfo().getTickets();
        if (tickets < giftPrice) {
            logger.error("赠送礼包房卡不足，NEED={},HAS={}", giftDef, tickets);
            rsp.setResultCode(ErrCode_Tickets_NotEnough_VALUE);
            sendToGate(req, MjCmd_FRoomPushGift_Rsp_VALUE, rsp.build());
            return;
        }

        MjUser dstUser = MjUserMgr.getInstance().get(msg.getDstUserID());
        if (dstUser == null) {
            logger.error("赠送礼包，对方不在线, packet : {}", req);
            return;
        }

        if (dstUser.getPlayerInfo().getBindRoomID() != mjUser.getPlayerInfo().getOwnerRoomID() && dstUser.getPlayerInfo().getBindRoomID() > 0) {
            // 对方已经绑定其他麻将馆，不可赠送
            rsp.setResultCode(ErrCode_FRoom_HasBindRoom_VALUE);
            sendToGate(req, MjCmd_FRoomPushGift_Rsp_VALUE, rsp.build());
            return;
        }

        boolean inBag = false;
        subTickets(mjUser, giftDef.getGiftPrice());
        syncMoney(req, mjUser.getBaseInfo());

        if (dstUser.getPlayerInfo().getBindRoomID() == mjUser.getPlayerInfo().getOwnerRoomID()) {
            // 关联自己麻将馆的玩家，礼包房卡直接进包
            dstUser.setBaseInfo(dstUser.getBaseInfo().toBuilder().setTickets(giftDef.getGiftPrice()).build());
            inBag = true;
        }

        MjCmd.FRoomPushGiftNotify.Builder notify = MjCmd.FRoomPushGiftNotify.newBuilder();

        notify.setSrcUserID(mjUser.getBaseInfo().getUserID());
        notify.setDstUserID(msg.getDstUserID());
        notify.setGiftID(msg.getGiftID());
        notify.setSrcName(mjUser.getBaseInfo().getName());
        notify.setHasInBag(inBag);
        sendToGate(dstUser.getBaseInfo().getUserID(), MjCmd_FRoomPushGfit_Notify_VALUE, notify.build());
    }

    private void onRecvGiftReq(Packet req, MjUser mjUser, MjCmd.FRoomReciveGiftReq msg) {
        if (mjUser.getPlayerInfo().getBindRoomID() > 0 || mjUser.getPlayerInfo().getOwnerRoomID() > 0) {
            logger.error("已经有关联房间，领取礼包是非法逻辑");
            return;
        }

        Define.GiftDef giftDef = configProvider.getGiftDef(msg.getGiftID());
        if (giftDef == null) {
            logger.error("非法礼包={}", msg.getGiftID());
            return;
        }

        MjUser dstUser = MjUserMgr.getInstance().get(msg.getDstUserID());
        if (dstUser == null) {
            logger.error("对方玩家不在线");
            return;
        }
        if (msg.getDstUserID() == mjUser.getBaseInfo().getUserID()) {
            logger.error("非法礼包领取逻辑，同一个人");
            return;
        }

        if (msg.getIsApply()) {
            mjUser.setBaseInfo(mjUser.getBaseInfo().toBuilder().setTickets(giftDef.getGiftPrice()).build());
            return;
        }

        MjCmd.FRoomRecvieGiftNotify.Builder notify = MjCmd.FRoomRecvieGiftNotify.newBuilder();
        notify.setGiftID(msg.getGiftID());
        notify.setIsApply(msg.getIsApply());
        notify.setSrcUserID(msg.getSrcUserID());
        notify.setSrcName(mjUser.getBaseInfo().getName());
        sendToGate(req, MjCmd_FRoomReciveGift_Rsp_VALUE, notify.build());
    }

    private void onNewDesk(Packet req, MjUser mjUser, MjCmd.MjNewDeskReq msg) {
        MjCmd.MjNewDeskRsp.Builder rsp = MjCmd.MjNewDeskRsp.newBuilder();
        if (mjUser.getPlayerInfo().getOwnerDeskID() > 0) {
            logger.error("牌桌已存在");
            rsp.setResultCode(ErrCode_FRoom_HasDesk_VALUE);
            sendToGate(req, MjCmd_NewDesk_Rsp_VALUE, rsp.build());
            return;
        }

        // 局数规则，扣除房卡
        int bouts = 0;
        int rulerCount = msg.getDeskInfo().getMjRulerCount();
        for (int i = 0; i < rulerCount; i++) {
            if (msg.getDeskInfo().getMjRuler(i) == MjRulerType_Bureau_4_VALUE) {
                bouts = 4;
            } else if (msg.getDeskInfo().getMjRuler(i) == MjRulerType_Bureau_8_VALUE) {
                bouts = 8;
            } else if (msg.getDeskInfo().getMjRuler(i) == MjRulerType_Bureau_16_VALUE) {
                bouts = 16;
            } else if (msg.getDeskInfo().getMjRuler(i) == MjRulerType_Bureau_1_VALUE) {
                bouts = 1;
            } else if (msg.getDeskInfo().getMjRuler(i) == MjRulerType_Bureau_2_VALUE) {
                bouts = 2;
            } else if (msg.getDeskInfo().getMjRuler(i) == MjRulerType_Bureau_3_VALUE) {
                bouts = 3;
            }
        }

        if (bouts == 0) {
            logger.error("建桌非法申请，没有指定局数");
            rsp.setResultCode(ErrCode_FRoom_InvalidRuler_NeedBouts_VALUE);
            sendToGate(req, MjCmd_NewDesk_Rsp_VALUE, rsp.build());
            return;
        }

        int boutsCfgId = 100100 + bouts;
        Map<String, Object> rtmCfg = configProvider.getRtmCfg(boutsCfgId);
        if (rtmCfg == null) {
            logger.error("{}参数不存在", boutsCfgId);
            return;
        }
        int needTicket = (int) rtmCfg.get("initData");

        if (mjUser.getBaseInfo().getTickets() < needTicket) {
            logger.error("建桌失败，房卡不足");
            rsp.setResultCode(ErrCode_Tickets_NotEnough_VALUE);
            sendToGate(req, MjCmd_NewDesk_Rsp_VALUE, rsp.build());
        }

        int id = roomProvider.genDeskId();
        Desk desk = new Desk();
        desk.setDeskID(id);
        DeskMgr.getInstance().put(id, desk);

        int deskGuid = roomProvider.addMjDeskRecord(desk.getDeskData().getDeskID(), desk.getDeskData().getOwnerUserID(), desk.getDeskCfgInfo().getMjGameType().getNumber(),
                desk.getDeskCfgInfo().getViewScore());

        Define.MjDeskData mjDeskData = Define.MjDeskData.newBuilder()
                .setDeskID(id)
                .setOwnerUserID(mjUser.getBaseInfo().getUserID())
                .setMaxBouts(bouts)
                .setViewRecord(msg.getDeskInfo().getViewScore())
                // 開桌不扣房卡，開局扣房卡
                .setNeedTickets(needTicket)
                .setDeskGUID(deskGuid)
                .setOwnerName(mjUser.getBaseInfo().getName()).build();
        Define.MjDeskInfo mjDeskInfo = msg.getDeskInfo().toBuilder()
                .setOwnerUserID(mjUser.getBaseInfo().getUserID())
                .setDeskGUID(deskGuid)
                .setOwnerName(mjUser.getBaseInfo().getName()).build();

        desk.setDeskData(mjDeskData);
        desk.setDeskCfgInfo(mjDeskInfo);
        mjUser.setPlayerInfo(mjUser.getPlayerInfo().toBuilder().setOwnerDeskID(id).build());

        List<String> providers = RegistryServerInfo.getProviders(GAME_MSG);
        String serverUrl = LeastActiveLoadBalanceSupport.doSelect(providers);

        desk.setGameUrl(serverUrl);
        CUserProvider.setUserRouter(mjUser.getBaseInfo().getUserID(), ServerType.GAMESERVER, serverUrl);

        int agentId = 0;
        if (msg.getRoomID() > 0) {
            logger.info("麻将馆建桌,ROOMID={}", msg.getRoomID());
            FriendRoom friendRoom = FriendRoomMgr.getInstance().get(msg.getRoomID());
            if (friendRoom != null) {
                friendRoom.getDeskList().add(desk.getDeskID());
                desk.setRoomID(msg.getRoomID());
                agentId = friendRoom.getRoomInfo().getAgentID();
            }
        }

        // 创建一条牌桌记录数据库

        Define.MjDeskRecord mjDeskRecord = Define.MjDeskRecord.newBuilder()
                .setDeskID(desk.getDeskID())
                .setMaxBouts(desk.getDeskData().getMaxBouts())
                .setMjGameType(desk.getDeskCfgInfo().getMjGameType().getNumber())
                .setRecordTime((int) System.currentTimeMillis() / 1000)
                .setOwnerUserID(desk.getDeskData().getOwnerUserID())
                .setDeskGUID(deskGuid).build();
        desk.setRecords(mjDeskRecord);

        // 通知GameServer，创建牌桌
        MjCmd.MjH2GNewDeskReq.Builder reqGame = MjCmd.MjH2GNewDeskReq.newBuilder();
        reqGame.setUserID(msg.getUserID());
        //reqGame.setGateID(mjUser.getGateUrl())
        //reqGame.setHallID()
        reqGame.setDeskInfo(desk.getDeskCfgInfo().toBuilder().setDeskID(desk.getDeskID()));
        byte[] bytes = reqGame.build().toByteArray();
        Packet packet = new Packet();
        packet.setBytes(bytes);
        sendToGame(packet, MjCmd_H2G_NewDesk_Req_VALUE);

        // 数据统计
        CoreCmd.StatDeskLogReq.Builder stat = CoreCmd.StatDeskLogReq.newBuilder();
        // stat.setUserID()
        stat.setUserName(mjUser.getBaseInfo().getName());
        stat.setDeskID(desk.getDeskID());
        stat.setMjType(desk.getDeskData().getMjType());
        // MjGameRulerConfig mjGameRulerConfig = configProvider.getMjRulerConfig(desk.getDeskData().getMjType());
        // stat.setMjName(desk.getD);
        stat.setAgentID(agentId);
        stat.setMaxBouts(desk.getDeskData().getMaxBouts());
        sendToStat(stat.build());
        logger.info("通知GS创建牌桌");
    }


    private void onJoinDesk(Packet req, MjUser mjUser, MjCmd.MjJoinDeskReq mjJoinDeskReq) {

    }

    private void onViewSaleDataReq(Packet req, MjUser mjUser) {

    }

    private void onReadDelUserRecord(Packet req, MjUser mjUser, MjCmd.FMjRoomMyRecordOpReq fMjRoomMyRecordOpReq) {

    }

    private void onDeskReconect(Packet req, MjUser mjUser, MjCmd.MjDeskReconectReq mjDeskReconectReq) {
    }

    private void onGoodsListReq(Packet req, MjUser mjUser) {
    }

    private void onBuyGoodsReq(Packet req, MjUser mjUser, GameCmd.BuyGoodsReq buyGoodsReq) {

    }


    private void onGetOfficialGroupsReq(Packet req, MjUser mjUser) {

    }

    private void onPushMoneyBag(Packet req, MjUser mjUser, MjCmd.PushMoneyBagReq pushMoneyBagReq) {


    }

    // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // // //

    private void syncMoney(Packet req, Define.UserInfo userInfo) {
        GameCmd.SyncMoney.Builder syncMoneyB = GameCmd.SyncMoney.newBuilder();
        syncMoneyB.setMoney(userInfo.getMoney());
        syncMoneyB.setDiamond(userInfo.getDiamond());
        syncMoneyB.setBindTickets(userInfo.getBindTickets());
        syncMoneyB.setTickets(userInfo.getTickets());
        syncMoneyB.setUserId(userInfo.getUserID());
        sendToGate(req, CmdNo_SyncMoney_Notify_VALUE, userInfo);

        CUserProvider.updateMoney(userInfo.getUserID(), userInfo.getMoney(), userInfo.getDiamond(), userInfo.getTickets(), userInfo.getBindTickets());
    }

    private FriendRoom loadFriendRoomFromDb(int roomId) {
        Define.FMjRoom fMjRoom = roomProvider.queryRoomById(roomId);
        if (fMjRoom == null) {
            logger.error("麻将馆不存在,id : {}", roomId);
            return null;
        }
        FriendRoom friendRoom = new FriendRoom();
        friendRoom.setRoomInfo(fMjRoom);
        FriendRoomMgr.getInstance().put(roomId, friendRoom);
        return friendRoom;
    }

    private void syncMjUser2Client(Packet req, MjUser mjUser) {
        MjCmd.FMjRoomPlayerInfoRsp fMjRoomPlayerInfoRsp = MjCmd.FMjRoomPlayerInfoRsp.newBuilder()
                .setUserID(mjUser.getBaseInfo().getUserID())
                .setPlayerInfo(mjUser.getPlayerInfo()).build();
        sendToGate(req, MjCmd_FRoomPlayerInfo_Rsp_VALUE, fMjRoomPlayerInfoRsp);
    }

    private void subTickets(MjUser mjUser, int tickets) {
        int needTickets = tickets;
        if (mjUser.getBaseInfo().getBindTickets() > 0) {
            int bindTickets = mjUser.getBaseInfo().getBindTickets();
            if (bindTickets >= needTickets) {
                bindTickets = bindTickets - needTickets;
                mjUser.setBaseInfo(mjUser.getBaseInfo().toBuilder().setBindTickets(bindTickets).build());
                needTickets = 0;
            } else {
                mjUser.setBaseInfo(mjUser.getBaseInfo().toBuilder().setBindTickets(0).build());
                needTickets = needTickets - bindTickets;
            }
        }
        if (needTickets > 0) {
            int newTicket = mjUser.getBaseInfo().getTickets();
            newTicket = newTicket - needTickets;
            newTicket = newTicket < 0 ? 0 : newTicket;
            mjUser.setBaseInfo(mjUser.getBaseInfo().toBuilder().setTickets(newTicket).build());
        }
    }

    private boolean checkParam(Object... obj) {
        for (Object o : obj) {
            if (o == null) {
                logger.error("param error, some obj is null");
                return false;
            }
        }
        return true;
    }
}
*/
