package com.jh.sgs.core.general;

import com.jh.sgs.core.ContextManage;
import com.jh.sgs.core.InteractiveEvent;
import com.jh.sgs.core.Util;
import com.jh.sgs.core.enums.InteractiveEnum;
import com.jh.sgs.core.exception.SgsApiException;
import com.jh.sgs.core.interactive.Interactiveable;
import com.jh.sgs.core.pojo.Card;
import com.jh.sgs.core.pojo.CompletePlayer;
import com.jh.sgs.core.pojo.FalseCard;
import com.jh.sgs.core.pojo.Skill;
import com.jh.sgs.core.roundevent.ActiveSkillEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ZhaoYun extends BaseGeneral implements ActiveSkillEvent {

    boolean longDan = false;
    FalseCard falseCard;

    public ZhaoYun(CompletePlayer completePlayer) {
        super(completePlayer);
    }

    @Override
    public Interactiveable addSkillOption(Interactiveable interactiveable) {
        return new Interactiveable() {
            int a = 0;

            @Override
            public List<Card> handCard() {
                return interactiveable.handCard();
            }

            @Override
            public void cancelPlayCard() {
                longDan = false;
                interactiveable.cancelPlayCard();
            }

            @Override
            public void playCard(int id) {
                if (a == 0) interactiveable.playCard(id);
                else if (a == 1) {
                    Card card = Util.collectionCollectAndCheckId(getCompletePlayer().getHandCard(), id);
                    longDan(card);
                    try {
                        interactiveable.playCard(id);
                    } catch (SgsApiException e) {
                        getCompletePlayer().getHandCard().remove(falseCard);
                        getCompletePlayer().getHandCard().add(falseCard.getCard());
                        falseCard = null;
                        throw e;
                    }
                    a++;
                }
            }

            @Override
            public void cancel() {
                interactiveable.cancel();
            }

            @Override
            public InteractiveEvent.CompleteEnum complete() {
                if (interactiveable.complete() == InteractiveEvent.CompleteEnum.COMPLETE || a == 2)
                    return InteractiveEvent.CompleteEnum.COMPLETE;
                else if (interactiveable.complete() == InteractiveEvent.CompleteEnum.NOEXECUTE && a == 1)
                    return InteractiveEvent.CompleteEnum.PROGRESS;
                else if (interactiveable.complete() == InteractiveEvent.CompleteEnum.NOEXECUTE && a == 0)
                    return InteractiveEvent.CompleteEnum.NOEXECUTE;
                throw new SgsApiException("未知过程");
            }

            @Override
            public void setSkill(int id) {
                if (interactiveable.complete() == InteractiveEvent.CompleteEnum.COMPLETE)
                    throw new SgsApiException("正常出牌后，不可使用技能");
                Skill skill = Util.collectionCollectAndCheckId(showSkill(), id);
                longDan = !longDan;
                if (longDan) a = 1;
                else a = 0;
            }

            @Override
            public List<Skill> showSkill() {
                ArrayList<Skill> skills = new ArrayList<>();
                skills.add(getCompletePlayer().getCompleteGeneral().getGeneral().getSkills()[0]);
                return skills;
            }

            @Override
            public InteractiveEnum type() {
                return InteractiveEnum.ZYCP;
            }
        };
    }


    private void longDan(Card card) {
        if (longDan) {
            switch (card.getNameId()) {
                case 1:
                    falseCard = new FalseCard(card);
                    falseCard.setNameId(2);
                    break;
                case 2:
                    falseCard = new FalseCard(card);
                    falseCard.setNameId(1);
                    break;
                default:
                    throw new SgsApiException("选择的牌不是杀/闪");
            }
            Map<String, String> cardParameter = ContextManage.cardManage().getCardParameter(falseCard.getNameId());
            falseCard.setName(cardParameter.get("name"));
            falseCard.setName(cardParameter.get("remark"));
            getCompletePlayer().getHandCard().remove(card);
            getCompletePlayer().getHandCard().add(falseCard);
        }
    }
}
