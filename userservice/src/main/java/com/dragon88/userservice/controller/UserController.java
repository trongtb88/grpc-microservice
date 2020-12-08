package com.dragon88.userservice.controller;


import com.dragon88.gen.proto.ReserveSeatResponse;
import com.dragon88.userservice.dto.UserReserve;
import com.dragon88.userservice.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/reserves/new")
    @ApiOperation(value = "Reserve list seat for 1 user", notes = "Reserve list seat for 1 user.")
    public String reserve(@RequestBody UserReserve userReserve,
                                       HttpServletRequest request, HttpServletResponse response) {
        ReserveSeatResponse reserveSeatResponse = userService.reserve(userReserve);
        return reserveSeatResponse.getMessage();
    }

    @GetMapping(path="/reserves/get", produces = "application/json")
    @ApiOperation(value = "Get list seat for 1 user", notes = "Get list seat for 1 user.")
    public String getReserves(
                                                         HttpServletRequest request, HttpServletResponse response) {
        return "OK";
    }


}
