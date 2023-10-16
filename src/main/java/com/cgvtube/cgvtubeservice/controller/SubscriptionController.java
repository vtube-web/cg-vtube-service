package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.response.ResponseDto;
import com.cgvtube.cgvtubeservice.service.SubscriptionService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subscriber")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    @PostMapping("/{channelId}")
    public ResponseEntity<ResponseDto> subscribe (@PathVariable Long channelId, HttpSession session) {
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = subscriptionService.subscribe(currentUser, channelId);
        if (responseDto.getStatus() == "200") {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{channelId}")
    public ResponseEntity<?> deleteSubscribe (@PathVariable Long channelId, HttpSession session) {
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = subscriptionService.removeSubscribed(currentUser, channelId);
        if (responseDto.getStatus() == "200") {
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/list-channel")
    public ResponseEntity<ResponseDto> getListChannels(HttpSession session) {
        UserDetails currentUser = (UserDetails) session.getAttribute("currentUser");
        ResponseDto responseDto = subscriptionService.getListChannels(currentUser);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
