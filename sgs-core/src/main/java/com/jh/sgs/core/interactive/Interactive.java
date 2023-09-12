package com.jh.sgs.core.interactive;

import com.jh.sgs.core.enums.InteractiveEnum;

/**
 * 流程执行方法集，在各需要玩家响应的阶段，会产生一个流程方法集，该阶段会实现本次响应需要的方法，以供流程正常完整执行（目前无法得知具体实现的方法）
 */
public interface Interactive extends XZYX, CP, QP, XZMB, GHCQSSQYXZP, WGFDXZP, JNCP, TOF ,XZP,JNXZP,GX{
    InteractiveEnum type();


}
