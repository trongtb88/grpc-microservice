package com.dragon88.dea.notificationservice.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SeatDTO {
    int rows;
    int cols;
    String status;

}
