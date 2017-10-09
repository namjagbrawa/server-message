package com.bingo.server.utils;

import com.bingo.server.exception.ServerException;
import com.bingo.server.msg.MSG;
import com.bingo.server.msg.NOTIFY;
import com.bingo.server.msg.RESP;
import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;

/**
 * 不包括Message中的sequence字段
 * Created by ZhangGe on 2017/7/14.
 */
public class MSGBuilder {

    public static final String Response = "Response";
    public static final String Request = "Request";
    public static final String Notification = "Notification";
    public static final String Point = ".";
    public static final String Underline = "_";

    // 增加ResponseMessage Sequence字段值, Build Message返回消息
    public static MSG.Message buildMessage(MSG.Message reqMsg, MSG.Message.Builder responseMessage) {
        return responseMessage.setSequence(reqMsg.getSequence()).build();
    }

    // 生成Notification消息
    public static MSG.Message.Builder notification(MSG.MsgCode msgCode, Message innerNotification) {
        NOTIFY.Notification.Builder notification = getNotification(msgCode, innerNotification);
        return getNotificationMessage(msgCode, notification.build());
    }

    // 生成Response失败返回消息
    public static MSG.Message.Builder fail(MSG.MsgCode msgCode, String errorMSG) {
        RESP.Response.Builder response = RESP.Response.newBuilder()
                .setResult(false)
                .setLastResponse(true)
                .setErrorDescribe(errorMSG);
        return getResponseMessage(msgCode, response.build());
    }

    // 生成没有消息体的Response Message消息
    public static MSG.Message.Builder success(MSG.MsgCode msgCode) {
        return MSG.Message.newBuilder().setResponse(RESP.Response.newBuilder().setResult(true).setLastResponse(true).build());
    }

    // 生成没有消息体的Response Message消息
    public static MSG.Message.Builder successNotLast(MSG.MsgCode msgCode) {
        return MSG.Message.newBuilder().setResponse(RESP.Response.newBuilder().setResult(true).setLastResponse(false).build());
    }

    // 生成Response成功返回消息
    public static MSG.Message.Builder success(MSG.MsgCode msgCode, Message innerResponse) {
        RESP.Response.Builder response = getResponse(msgCode, innerResponse);
        response.setResult(true).setLastResponse(true);
        return getResponseMessage(msgCode, response.build());
    }

    // 生成Response成功返回消息，不是最后一个返回消息
    public static MSG.Message.Builder successNotLast(MSG.MsgCode msgCode, Message innerResponse) {
        RESP.Response.Builder response = getResponse(msgCode, innerResponse);
        response.setResult(true).setLastResponse(false);
        return getResponseMessage(msgCode, response.build());
    }

    // 生成Message Notification消息
    private static MSG.Message.Builder getNotificationMessage(MSG.MsgCode msgCode, NOTIFY.Notification notification) {
        return MSG.Message.newBuilder()
                .setMsgCode(msgCode)
                .setNotification(notification);
    }

    // 生成Message Response消息
    private static MSG.Message.Builder getResponseMessage(MSG.MsgCode msgCode, RESP.Response response) {
        return MSG.Message.newBuilder()
                .setMsgCode(msgCode)
                .setResponse(response);
    }

    // 根据Notification类型消息生成Notification Builder, MsgCode 用来做校验
    private static NOTIFY.Notification.Builder getNotification(MSG.MsgCode msgCode, Message innerNotification) {
        String descriptionName = getDescriptionName(innerNotification, Notification);
        String descriptionTypeName = getDescriptionName(msgCode);
        if (!descriptionName.equals(descriptionTypeName)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "消息和消息类型不相同, 请确认后重试, msgCode : " + msgCode + " message : " + innerNotification);
        }
        return getNotification(descriptionName, innerNotification);
    }

    // 根据Response类型消息生成Response Builder, MsgCode 用来做校验
    private static RESP.Response.Builder getResponse(MSG.MsgCode msgCode, Message innerResponse) {
        String descriptionName = getDescriptionName(innerResponse, Response);
        String descriptionTypeName = getDescriptionName(msgCode);
        if (!descriptionName.equals(descriptionTypeName)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "消息和消息类型不相同, 请确认后重试, msgCode : " + msgCode + " message : " + innerResponse);
        }
        return getResponse(descriptionName, innerResponse);
    }

    private static RESP.Response.Builder getResponse(String descriptionName, Message message) {
        String fieldName = getFieldName(descriptionName, Response);
        Descriptors.Descriptor descriptor = RESP.Response.getDescriptor();
        Descriptors.FieldDescriptor fieldDescriptor = descriptor.findFieldByName(fieldName);

        RESP.Response.Builder builder = RESP.Response.newBuilder().setField(fieldDescriptor, message);
        if (Check.isNull(builder)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "错误的Response获取, descriptionName : " + descriptionName + " message" + message);
        }
        return builder;
    }

    private static NOTIFY.Notification.Builder getNotification(String descriptionName, Message message) {
        String fieldName = getFieldName(descriptionName, Notification);
        Descriptors.Descriptor descriptor = NOTIFY.Notification.getDescriptor();
        Descriptors.FieldDescriptor fieldDescriptor = descriptor.findFieldByName(fieldName);

        NOTIFY.Notification.Builder builder = NOTIFY.Notification.newBuilder().setField(fieldDescriptor, message);
        if (Check.isNull(builder)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "错误的Notification获取, descriptionName : " + descriptionName + " message" + message);
        }
        return builder;
    }

    private static String getDescriptionName(Message message, String messageType) {
        Descriptors.Descriptor responseType = message.getDescriptorForType();
        String descriptionName = responseType.getName();
        if (Check.isNull(descriptionName)) {
            throw new ServerException(MSG.MsgCode.SYSTEM_ERROR, "错误的Proto消息生成, message : " + messageType + " messageType : " + messageType + " descriptionName : " + descriptionName);
        }
        return descriptionName;
    }

    private static String getDescriptionName(MSG.MsgCode msgCode) {
        Descriptors.EnumValueDescriptor valueDescriptor = msgCode.getValueDescriptor();
        String valueDescriptorName = valueDescriptor.getName();
        String descriptionName = valueDescriptorName.replace(Underline, "");
        return descriptionName;
    }

    private static String getFieldName(String descriptionName, String messageType) {
        String description = descriptionName.replace(messageType, "");
        String typeName = description.substring(0, 1).toLowerCase() + description.substring(1, description.length());
        return typeName;
    }

    public static void main(String[] args) {
        RESP.WeixinLoginResponse.Builder builder = RESP.WeixinLoginResponse.newBuilder().setUserId(123);
        MSG.Message.Builder success = MSGBuilder.success(MSG.MsgCode.Weixin_Login_Response, builder.build());

    }

}
