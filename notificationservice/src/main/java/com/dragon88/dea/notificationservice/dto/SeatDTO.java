package com.dragon88.dea.notificationservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SeatDTO {
    int rows;
    int cols;
    String status;

    public SeatDTO() {

    }

    public SeatDTO(int rows, int cols, String status) {
        this.rows = rows;
        this.cols = cols;
        this.status = status;
    }
}
