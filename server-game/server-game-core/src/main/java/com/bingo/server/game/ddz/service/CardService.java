package com.bingo.server.game.ddz.service;

import com.bingo.server.game.bean.User;
import com.bingo.server.game.bean.enums.CardType;
import com.bingo.server.game.provider.bean.DdzRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.bingo.server.game.bean.enums.CardType.*;

@Service
public class CardService {

    private final static Logger logger = LoggerFactory.getLogger(CardService.class);

    // 检测所选牌型是否合法, 返回出牌的类型
    public CardType getCardType(DdzRule ddzRule, short[] cards) {
        int cardNumber = cards.length; // 出牌的总个数
        short[] playCard = getCardsNumArray(cards); // 出牌各牌的个数数组
        int startCard = 0;
        int i = 0;
        // 找开始的牌
        for (i = 0; i < playCard.length; i++) {
            if (playCard[i] > 0) {
                startCard = i;
                break;
            }
        }
        switch (cardNumber) {
            case 1:
                return Single_Card; // 单牌
            case 2:
                if (startCard == 16) { // 小王开始
                    return Super_Boom_Card;
                } else if ((playCard[startCard] == 2)) {
                    return Double_Card;
                }
                break;
            case 3: // 三条
                if ((playCard[startCard] == 3)) {
                    return Three_Card;
                }
                break;
            case 4:
                if ((playCard[startCard] == 4)) {
                    // 炸弹
                    return Four_Card;
                } else { // 3带1
                    for (i = startCard; i < playCard.length; i++) {
                        if (playCard[i] >= 3) {
                            startCard = i;
                            return Three_With_One_Card;
                        }
                    }
                }
                break;
            case 5: // 单顺，三带二(无)
                if (IsLinkCard(startCard, playCard, cardNumber, 1)) {
                    return Link_Card;
                } else if (!ddzRule.isThreeCardNoPair()) {
                    boolean Temp_IsHaveTwo = false;
                    for (i = startCard; i < playCard.length; i++) {
                        if (playCard[i] == 2)
                            Temp_IsHaveTwo = true;
                    }
                    for (i = startCard; i < playCard.length; i++) {
                        if (playCard[i] >= 3 && Temp_IsHaveTwo) {
                            startCard = i;
                            return Three_With_Two_Card;// THREE_WITH_TWO_CARD;去掉3带2
                        }
                    }
                }
                break;
            case 7:
            case 11:// 单顺
                if (IsLinkCard(startCard, playCard, cardNumber, 1))
                    return Link_Card;
                break;
            case 6: // 4种情况，单顺，双顺，三，4带2
                if (IsLinkCard(startCard, playCard, cardNumber, 1))
                    return Link_Card;
                else if (IsLinkCard(startCard, playCard, cardNumber, 2))
                    return Double_Link_Card;
                else if (IsLinkCard(startCard, playCard, cardNumber, 3))
                    return Three_Link_Card;
                else {
                    for (i = startCard; i < 16; i++) {
                        if (playCard[i] == 4) {
                            startCard = i;
                            return Four_With_Two_Card;
                        }
                    }
                }
                break;
            case 8: // 四种情况，单顺，双顺，6带2(444555+79 )，4带2( 4444 ＋ 55 ＋ 77 )
                if (IsLinkCard(startCard, playCard, cardNumber, 1))
                    return Link_Card;
                else if (IsLinkCard(startCard, playCard, cardNumber, 2))
                    return Double_Link_Card;
                else {
                    for (i = startCard; i < 16; i++) {
                        if (playCard[i] >= 3) {
                            startCard = i;
                            break;
                        }
                    }
                    int temp_two = 0;
                    int temp_four = 0;
                    for (i = 0; i < 16; i++) {
                        if (playCard[i] == 2) {
                            temp_two++;
                        } else if (playCard[i] == 4) {
                            temp_four++;
                        }
                    }
                    if (temp_two == 2 && temp_four == 1) {
                        return Four_With_Four_Card;
                    }
                    if (IsLinkWithCard(startCard, playCard, cardNumber, 3))// ???
                        return Three_With_Link_Card;
                }
                break;
            case 9: // 两种情况,单顺，三顺
                if (IsLinkCard(startCard, playCard, cardNumber, 1))
                    return Link_Card;
                else if (IsLinkCard(startCard, playCard, cardNumber, 3))
                    return Three_Link_Card;
                break;
            case 10: // 三种情况，单顺，双顺，6带4
                if (IsLinkCard(startCard, playCard, cardNumber, 1))
                    return Link_Card;
                else if (IsLinkCard(startCard, playCard, cardNumber, 2))
                    return Double_Link_Card;
                else if (!ddzRule.isThreeCardNoPair()) {
                    for (i = startCard; i < 14; i++) {
                        if (playCard[i] >= 3) {
                            startCard = i;
                            break;
                        }
                    }
                    if (IsLinkWithTwoCard(startCard, playCard, cardNumber, 3)) {
                        //return THREE_WITH_TWO_CARD;// THREE_WITH_LINK_TWO_CARD;暂时去掉
                        return Three_With_Link_Two_Card;
                    }
                }
                break;
            case 12: // 四种情况,单顺，双顺，三，9带3
                if (IsLinkCard(startCard, playCard, cardNumber, 1))
                    return Link_Card;
                else if (IsLinkCard(startCard, playCard, cardNumber, 2))
                    return Double_Link_Card;
                else if (IsLinkCard(startCard, playCard, cardNumber, 3))
                    return Three_Link_Card;
                else {
                    for (i = startCard; i < 13; i++) {
                        if (playCard[i] >= 3) {
                            startCard = i;
                            break;
                        }
                    }
                    if (IsLinkWithCard(startCard, playCard, cardNumber, 3))
                        return Three_With_Link_Card;
                }
                break;
            case 14: // 双顺
                if (IsLinkCard(startCard, playCard, cardNumber, 2))
                    return Double_Link_Card;
                break;
            case 15: // 三顺,9带6
                if (IsLinkCard(startCard, playCard, cardNumber, 3)) {
                    return Three_Link_Card;
                } else if (!ddzRule.isThreeCardNoPair()) {
                    for (i = startCard; i < 13; i++) {
                        if (playCard[i] >= 3) {
                            startCard = i;
                            break;
                        }
                    }
                    if (IsLinkWithTwoCard(startCard, playCard, cardNumber, 3)) {
                        return Three_With_Two_Card;
                    }
                }
                break;
            case 16: // 12带4 ,双顺
                if (IsLinkCard(startCard, playCard, cardNumber, 2)) {
                    return Double_Link_Card;
                } else {
                    for (i = startCard; i < 12; i++) {
                        if (playCard[i] >= 3) {
                            startCard = i;
                            break;
                        }
                    }
                    if (IsLinkWithCard(startCard, playCard, cardNumber, 3)) {
                        return Three_With_Link_Card;
                    }
                }
                break;
            case 18:// 双顺，3顺
                if (IsLinkCard(startCard, playCard, cardNumber, 2)) {
                    return Double_Link_Card;
                } else if (IsLinkCard(startCard, playCard, cardNumber, 3)) {
                    return Three_Link_Card;
                }
                break;
            case 20: // 15带5，双顺
                if (IsLinkCard(startCard, playCard, cardNumber, 2)) {
                    return Double_Link_Card;
                } else {
                    for (i = startCard; i < 11; i++) {
                        if (playCard[i] >= 3) {
                            startCard = i;
                            break;
                        }
                    }
                    if (IsLinkWithCard(startCard, playCard, cardNumber, 3)) {
                        return Three_With_Link_Card;
                    }
                }
                if (ddzRule.isThreeCardNoPair()) {
                    for (i = startCard; i < 13; i++) {
                        if (playCard[i] >= 3) {
                            startCard = i;
                            break;
                        }
                    }
                    if (IsLinkWithTwoCard(startCard, playCard, cardNumber, 3))
                        return Three_With_Link_Two_Card;
                }
                break;
            default:
                return No_Card_Type;
        }
        return No_Card_Type;
    }

    public void playCard(User user, byte[] cards) {
        if (cards == null) {
            return;
        }
        List<Byte> bytes = new ArrayList<>();
        for (byte b : cards) {
            bytes.add(b);
        }
        byte[] userCards = user.getCards();
        int length = userCards.length - bytes.size();
        if (length <= 0) {
            user.setCards(new byte[0]);
        } else {
            byte[] newCards = new byte[length];
            int except = 0;
            for (int i = 0, j = 0; i < userCards.length; i++) {
                if (bytes.contains(userCards[i])) {
                    except++;
                    continue;
                } else {
                    newCards[j] = userCards[i];
                    j++;
                }
            }
            if (except < bytes.size()) {
                logger.error("用户无此牌, cards : " + cards + " userCards : " + userCards);
            }
        }
    }

    public static short[] getCardsNumArray(short[] array) {
        short[] tempNumArray = new short[18];
        for (int i = 0; i < array.length; i++) {
            int n = array[i] & 0x0F;
            int hua = ((array[i] & 0xF0) >>> 4);
            if (n == 2) {
                n = 15;
            } else if (n == 15) {
                n = hua == 4 ? 16 : 17;
            }
            tempNumArray[n]++;
        }
        return tempNumArray;
    }

    // 顺**********************************
    public static boolean IsLinkCard(int Start_Card, short[] ShowOut_Card,
                                     int ShowOut_Num, int aType) {
        int temp_link_length = 0;
        int temp_limit_length = Start_Card + ShowOut_Num / aType;// 3,10
        if (temp_limit_length > 15) // ???A 14 2_15
            return false;
        for (int i = Start_Card; i < temp_limit_length; i++) {
            if (ShowOut_Card[i] >= aType) {
                temp_link_length++;
            }
        }
        if (temp_link_length == (ShowOut_Num / aType)) {
            return true;
        } else {
            return false;
        }
    }

    // 31 顺带**********************************
    public static boolean IsLinkWithCard(int Start_Card, short[] ShowOut_Card,
                                         int ShowOut_Num, int aType) {
        int temp_limit_length = Start_Card + ShowOut_Num / (aType + 1);// 3,10
        int temp_max_len = 0;
        for (int i = Start_Card; i < temp_limit_length && i < 15; i++) {
            if (ShowOut_Card[i] >= aType) {
                temp_max_len++;
            }
        }
        if (temp_max_len == (ShowOut_Num / (aType + 1)))
            return true;
        else
            return false;
    }

    // 分步检验顺带2**********************************
    public static boolean IsLinkWithTwoCard(int Start_Card, short[] ShowOut_Card,
                                            int ShowOut_Num, int aType) {
        int temp_i = 0;
        int temp_limit_length = Start_Card + ShowOut_Num / (aType + 1);// 3,10
        int temp_max_len = 0;
        for (int i = Start_Card; i < temp_limit_length && i < 15; i++) {
            if (ShowOut_Card[i] >= aType) {
                temp_max_len++;
            }
        }
        for (int i = 0; i < 16; i++) {
            if (ShowOut_Card[i] == 2) {
                temp_i++;
            }

        }
        if (temp_max_len == (ShowOut_Num / (aType + 2))
                && temp_i == ShowOut_Num / (aType + 2))
            return true;
        else
            return false;
    }


}
