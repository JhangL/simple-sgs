package com.jh.sgs.ui;

import com.jh.sgs.base.pojo.Card;
import com.jh.sgs.base.pojo.ShowCompletePlayer;
import com.jh.sgs.base.pojo.ShowPlayCardAbility;
import com.jh.sgs.base.pojo.ShowPlayer;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TcpObject implements Serializable {
    public static final int S_CLIENT = 1;
    public static final int S_SERVER = 2;
    public static final int T_GM = 1;
    public static final int T_PM = 2;
    public static final int T_SP = 3;
    public static final int T_SCP = 4;
    public static final int T_I = 5;
    public static final int T_OP = 6;
    public static final int T_V = 7;
    public static final int T_M = 7;
    public static final int O_INOUT = 1;
    public static final int O_TOF = 2;
    public static final int O_CANCEL = 3;
    public static final int O_HANDCARD = 4;
    public static final int O_WAIT = 5;
    public static final int O_WAITS = 6;
    public static final int O_ABILITY = 7;
    public static final int O_CHOOSE = 8;

    private int source;
    private int type;
    private String globalMessage;
    private String playerMessage;
    private String message;
    private ShowPlayer showPlayer;
    private ShowCompletePlayer showCompletePlayer;
    private List<ShowPlayCardAbility> showPlayCardAbility;
    private List<Card> choose;
    private int index;
    private String value;
    private int operate;
}
