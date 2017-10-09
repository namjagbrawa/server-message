package com.bingo.server.game.ddz.service;

import com.bingo.server.game.bean.DdzConstant;
import com.bingo.server.game.bean.Desk;
import com.bingo.server.utils.Check;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ZhangGe on 2017/7/21.
 */
@Service
public class DealService {

    private static final Logger logger = LoggerFactory.getLogger(DealService.class);

    private static final Random random = new Random();
    private static long seed = System.nanoTime();

    public void deal(Desk desk) {
        byte[] cards1 = new byte[17];
        byte[] cards2 = new byte[17];
        byte[] cards3 = new byte[17];

        byte[] handCards = new byte[3];
        List<Byte> otherCards = new ArrayList<>();
        Map<Byte, List<List<Byte>>> bombCardsMap = new HashMap<>();

        int minBomb = desk.getDdzRule().getMinBomb();
        int maxBomb = desk.getDdzRule().getMaxBomb();

        int weight1 = desk.getUsers().get(0).getWeight();
        int weight2 = desk.getUsers().get(1).getWeight();
        int weight3 = desk.getUsers().get(2).getWeight();

        random.setSeed(++seed);
        if (random.nextInt(100) <= 40) {
            maxBomb = 0;
        }
        int bomb = minBomb;

        int pro = 30;
        for (int i = minBomb; i < maxBomb; i++) {
            random.setSeed(++seed);
            if (random.nextInt(100) <= pro) {
                pro -= 13;
                bomb++;
            }
        }
        if (bomb > 12) {
            bomb = 12;
        }
        // 获取初始化Faces
        List<Byte> faces = getFaces();
        //炸弹牌面在初始化牌中的起始索引
        List<Byte> bombFaceIndexInCards = new ArrayList<>(bomb);
        //选出炸弹牌面在初始化牌中的起始索引
        for (int i = 0; i < bomb; i++) {
            random.setSeed(++seed);
            int cardFacesRandomIndex = random.nextInt(faces.size());
            Byte cardFacesRandom = faces.get(cardFacesRandomIndex);
            bombFaceIndexInCards.add((byte) ((cardFacesRandom - 2) * 4));
            faces.remove(cardFacesRandomIndex);
        }
        // 获取初始化Cards
        byte[] cards = getCards();
        if (!Check.isNull(desk.getExcepts())) {
            cards = exceptCards(desk.getExcepts(), cards);
        }
        for (byte index = 0, il = (byte) cards.length; index < il; ) {
            if (true == bombFaceIndexInCards.contains(index)) {
                byte needCopyNumber = (byte) ((index == (byte) 52) ? 2 : 4);
                List<Byte> bombCards = new ArrayList<>();
                for (byte i = 0; i < needCopyNumber; i++) {
                    bombCards.add(cards[index++]);
                }
                byte chairId;
                // 获取中炸弹的椅子号
                random.setSeed(++seed);
                int randomValue = random.nextInt(weight1 + weight2 + weight3);
                if (randomValue < weight1) {
                    weight1 = bombCardsMap.containsKey((byte) 0) && 3 == bombCardsMap.get((byte) 0).size() ? 0 : weight1 / 2;
                    chairId = 0;
                } else if (randomValue < weight1 + weight2) {
                    weight2 = bombCardsMap.containsKey((byte) 1) && 3 == bombCardsMap.get((byte) 1).size() ? 0 : weight2 / 2;
                    chairId = 1;
                } else {
                    weight3 = bombCardsMap.containsKey((byte) 2) && 3 == bombCardsMap.get((byte) 2).size() ? 0 : weight3 / 2;
                    chairId = 2;
                }
                if (false == bombCardsMap.containsKey(chairId)) {
                    bombCardsMap.put(chairId, new ArrayList<List<Byte>>());
                }
                bombCardsMap.get(chairId).add(bombCards);
            } else {
                otherCards.add(cards[index++]);
            }
        }
        //洗除炸弹牌余下的牌
        for (int i = otherCards.size() - 1; i > 0; i--) {
            random.setSeed(++seed);
            int t = (random.nextInt() >>> 1) % i;
            byte c = otherCards.get(t);
            otherCards.set(t, otherCards.get(i));
            otherCards.set(i, c);
        }

        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 3; j++) {
                cards1[i] = getCard((byte) j, bombCardsMap, otherCards);
                cards2[i] = getCard((byte) j, bombCardsMap, otherCards);
                cards3[i] = getCard((byte) j, bombCardsMap, otherCards);
            }
        }

        handCards[0] = getCard((byte) -1, bombCardsMap, otherCards);
        handCards[1] = getCard((byte) -1, bombCardsMap, otherCards);
        handCards[2] = getCard((byte) -1, bombCardsMap, otherCards);

        desk.getUsers().get(0).setCards(cards1);
        desk.getUsers().get(1).setCards(cards2);
        desk.getUsers().get(2).setCards(cards3);

        desk.setHandCards(handCards);
    }

    public byte getCard(byte chairId, Map<Byte, List<List<Byte>>> bombCardsMap, List<Byte> otherCards) {
        //存在该椅子
        if (true == bombCardsMap.containsKey(chairId)) {
            List<List<Byte>> bombCards = bombCardsMap.get(chairId);
            if (false == bombCards.isEmpty()) {
                List<Byte> l = bombCards.get(0);
                if (false == l.isEmpty()) {
                    byte card = l.remove(0);
                    if (true == l.isEmpty()) {
                        bombCards.remove(0);
                    }
                    return card;
                }
            }
        }
        if (false == otherCards.isEmpty()) {
            return otherCards.remove(0);
        } else {
            return -1;
        }
    }

    private byte[] exceptCards(byte[] except, byte[] cards) {
        if (Check.isNull(except)) {
            return cards;
        }else {
            int exceptLength = except.length;
            byte[] bytes = new byte[cards.length - exceptLength];

            List<Byte> byteList = new ArrayList<>();
            for (int i = 0; i< except.length ; i ++) {
                byteList.add(except[i]);
            }

            int i = 0;
            int j = 0;
            while (i < bytes.length) {
                if (byteList.contains(cards[i])) {
                    j++;
                    continue;
                } else {
                    bytes[i] = cards[j];
                    i++;
                    j++;
                }
            }
            return bytes;
        }
    }

    private byte[] getCards() {
        byte[] cards = new byte[DdzConstant.poker.length];
        System.arraycopy(DdzConstant.poker, 0, cards, 0, DdzConstant.poker.length);
        return cards;
    }

    private List<Byte> getFaces() {
        List<Byte> faces = new ArrayList<>();
        for (byte face : DdzConstant.faces) {
            faces.add(face);
        }
        return faces;
    }


}
