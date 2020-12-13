package com.dragon88.userservice.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserReserve {
    private int id;
    private String name;
    private String email;
    private String bookedResponse;


    private List<SeatDTO> seats;

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
