package com.bushy.fnbgame.controller.response;

import java.util.List;

public interface SideProjection {
    List<PitProjection> getPits();

    PitProjection getMainBucket();
}
