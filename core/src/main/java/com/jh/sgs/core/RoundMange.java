package com.jh.sgs.core;

import com.jh.sgs.pojo.CompletePlayer;

public class RoundMange {



    private Desk desk;
    private Desktop desktop;

    public RoundMange(Desk desk) {
        this.desk = desk;
        desktop=new Desktop();
    }

    public void begin() {
        CompletePlayer completePlayer = desk.get();
        RoundProcess roundProcess = new RoundProcess(completePlayer);

    }


    class Desktop{


    }

    class RoundProcess{
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
}
