package com.dragon88.userservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatDTO {
    int rows;
    int cols;
    String status;

    public SeatDTO() {

    }
}
