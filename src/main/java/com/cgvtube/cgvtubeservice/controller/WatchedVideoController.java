package com.cgvtube.cgvtubeservice.controller;

import com.cgvtube.cgvtubeservice.payload.response.PageResponseDTO;
import com.cgvtube.cgvtubeservice.payload.response.WatchedVideoDTO;
import com.cgvtube.cgvtubeservice.service.impl.VideoWatchedServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/watched-videos")
@CrossOrigin(origins = "http://localhost:3000")
public class WatchedVideoController {
    private final VideoWatchedServiceImpl videoWatchedService;

    @GetMapping("/{userId}")
    public ResponseEntity<PageResponseDTO<WatchedVideoDTO>> getWatchedVideos(@PathVariable Long userId, Pageable pageable) {
        Pageable pageableRequest = PageRequest.of(pageable.getPageNumber(),pageable.getPageSize(), Sort.Direction.DESC,"watchedAt");
        PageResponseDTO<WatchedVideoDTO> watchedVideos = videoWatchedService.findAllWatchedVideo(userId, pageableRequest);
        return new ResponseEntity<>(watchedVideos, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteWatchedVideosByUserId(@PathVariable Long userId) {
        try {
            videoWatchedService.deleteWatchedVideosByUserId(userId);
            return ResponseEntity.ok("All watched videos for the user deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Failed to delete watched videos: " + e.getMessage());
        }
    }

    @DeleteMapping("/{userId}/videos/{videoId}")
    public ResponseEntity<?> deleteWatchedVideo(@PathVariable Long userId, @PathVariable Long videoId) {
        try {
            videoWatchedService.deleteWatchedVideo(userId, videoId);
            return ResponseEntity.ok("Video watched deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Failed to delete video watched. Error: " + e.getMessage());
        }
    }
}
