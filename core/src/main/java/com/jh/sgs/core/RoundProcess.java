package com.jh.sgs.core;

import com.jh.sgs.pojo.CompletePlayer;

public class RoundProcess{
    CompletePlayer completePlayer;

    public RoundProcess(CompletePlayer completePlayer) {
        this.completePlayer = completePlayer;
    }
    void process(){
        start();
        decide();
        obtainCard();
        playCard();
        discardCard();
        end();
    }

    void start(){

    }

    void decide(){

    }

    void obtainCard(){

    }
    void playCard(){

    }
    void discardCard(){

    }
    void end(){

    }
}