package com.jh.sgs.base.pool;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TPool<T> {
    T pool;

    public boolean isEmpty(){
        return pool==null;
    }
}
