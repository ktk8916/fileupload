package com.playdata.pip;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pip")
public class PipController {

    private final PipService pipService;

    @GetMapping("/video")
    public ResponseEntity<Resource> findVideo() {
        return pipService.findVideo();
    }

    @GetMapping
    public ResponseEntity<Resource> findAll() {
        return pipService.findAll();
    }

    @PostMapping
    public void upload(@RequestParam("file") MultipartFile file){
        pipService.upload(file);
    }

}
