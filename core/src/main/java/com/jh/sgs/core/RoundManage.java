package com.jh.sgs.core;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RoundManage {


    private Desk desk;
    private Desktop desktop;

    private RoundProcess[] roundProcesses;

    public RoundManage(Desk desk) {
        this.desk = desk;
        desktop = new Desktop();
        roundProcesses = new RoundProcess[desk.size()];
    }

    public void init() {
        desk.foreach((integer, completePlayer) -> roundProcesses[integer] = completePlayer.getGeneral().getBaseGeneral().roundProcess(completePlayer));
    }

    public void begin() {
        int index = desk.index();
        try {
            while (true) {
                try {
                    roundProcesses[index].process();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                index = desk.nextOnDesk();
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("游戏结束");
        }
    }


    class Desktop {


    }

}
