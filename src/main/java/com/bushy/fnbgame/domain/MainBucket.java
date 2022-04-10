package com.bushy.fnbgame.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MainBucket extends Pit {

    public MainBucket(){
        super();
        this.stones = 0;
    }

    @Override
    public boolean isMainBucket() {
        return true;
    }
}
