package com.jh.sgs.core.card;

import com.jh.sgs.core.exception.DesktopException;

public interface Executable {

    /**
     * desktop执行入口，注意：请确保整个执行过程中无其他非设计异常，设计异常为无法完成执行流程的异常，如未选择目标，未满足条件。
     */
    void execute() throws DesktopException;
}
