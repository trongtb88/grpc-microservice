package com.dragon88.dea.notificationservice.dto;



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
    private String email;
    private List<SeatDTO> seats;

    private String bookedResponse;

    public UserReserve() {
    }

    public UserReserve(int id, String name, String email, List<SeatDTO> seats, String bookedResponse) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.seats = seats;
        this.bookedResponse = bookedResponse;
    }
}
