package com.dragon88.userservice.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class UserReserve {
    private int id;
    private String name;
    private List<SeatDTO> seats;
}
