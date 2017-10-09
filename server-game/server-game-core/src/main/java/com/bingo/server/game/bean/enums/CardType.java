package com.bingo.server.game.bean.enums;

/**
 * Created by ZhangGe on 2017/7/21.
 */
public enum CardType {
    No_Card_Type, // 没有对应的牌型
    Single_Card, // 单 牌
    Double_Card, // 对 牌 数值相同的2张牌
    Three_Card, // 三 条 数值相同的3张牌.如3个4
    Three_With_One_Card, // 三带一数值相同的3张牌＋单牌。如777＋5
    Three_With_Two_Card, // 三带2 // 数值相同的3张牌+对牌。777+99
    Four_Card, // 炸 弹 4张同数值牌
    Four_With_Two_Card, // 四带二 // 炸弹＋两手牌。如aaaa+3+5、7777+88+jj
    Four_With_Four_Card, // 4带4 炸弹
    Link_Card, // 单 顺 // 5张(含)以上连续单牌。如8910jqka。不包括2点和双王。
    Double_Link_Card, // 双 顺 // 3对(含)以上连续对牌。如334455
    Three_Link_Card, // 三顺 // 2个(含)以上连续三条。如jjjqqq
    Three_With_Link_Two_Card, // 飞机带翅 // 三顺＋同数量的对牌 // 如888999 4466
    Three_With_Link_Card, // 飞机带翅膀 // 三顺＋同数量的单牌jjjqqqkkk // 678
    Super_Boom_Card  // 火 箭 即双王(大王 小王)
}

// 3 4 5 6 7 8 9 10 11J 12Q 13K 14A 15_2 16小王 17大王
// 数字比较:大王＞小王＞2＞A＞K＞Q＞J＞10＞9＞8＞7＞6＞5＞4＞3

// noCard, // 没有出牌
// singleCard, // 单 牌
// doubleCard, // 对 牌 数值相同的2张牌
// threeCard, // 三 条 数值相同的3张牌.如3个4
// threeWithOneCard, // 三带一数值相同的3张牌＋单牌。如777＋5
// threeWithTwoCard, // 三带2 // 数值相同的3张牌+对牌。777+99
// fourCard, // 炸 弹 4张同数值牌
// fourWithTwoCard, // 四带二 // 炸弹＋两手牌。如aaaa+3+5、7777+88+jj
// fourWithFourCard, // 4带4 炸弹
// linkCard, // 单 顺 // 5张(含)以上连续单牌。如8910jqka。不包括2点和双王。
// doubleLinkCard, // 双 顺 // 3对(含)以上连续对牌。如334455
// threeLinkCard, // 三顺 // 2个(含)以上连续三条。如jjjqqq
// threeWithLinkTwoCard, // 飞机带翅 // 三顺＋同数量的对牌 // 如888999 4466
// threeWithLinkCard, // 飞机带翅膀 // 三顺＋同数量的单牌jjjqqqkkk // 678
// superBoomCard  // 火 箭 即双王(大王 小王)