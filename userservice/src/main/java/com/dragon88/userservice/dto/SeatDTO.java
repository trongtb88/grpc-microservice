package com.dragon88.userservice.dto;

import lombok.Data;

@Data
public class SeatDTO {
    int rows;
    int cols;
    String status;

    @Override
    public String toString() {
        return "SeatDTO{" +
            "rows=" + rows +
            ", cols=" + cols +
            ", status='" + status + '\'' +
            '}';
    }
}
