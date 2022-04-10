package com.bushy.fnbgame.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.With;

@With
@Data
@AllArgsConstructor
public class Pit {
    protected Integer stones;

    public Pit(){
        this.stones = 6;
    }

    public boolean isMainBucket(){
        return false;
    }
}
