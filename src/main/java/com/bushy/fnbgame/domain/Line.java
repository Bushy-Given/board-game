package com.bushy.fnbgame.domain;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class Line {
    private List<Pit> pits;
    private MainBucket mainBucket;

    public Line(){
        this.pits = IntStream.range(0, 6).mapToObj(i -> new Pit()).collect(Collectors.toList());
        this.mainBucket = new MainBucket();
    }

    public boolean hasCleanLine() {
        return pits.stream().allMatch(pit -> pit.getStones().equals(0));
    }

    public void finish() {
        this.getMainBucket().setStones(this.getMainBucket().getStones() + this.pits.stream().mapToInt(Pit::getStones).sum());
        this.pits.forEach(pit -> pit.setStones(0));
    }
}
