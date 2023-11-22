package com.fciencias.freshbowl.controllers;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/tutorials")
public class TutorialsController {

    @GetMapping("/")
    public ModelAndView tutorials() {

        return new ModelAndView("tutorials/index");
    }

    @GetMapping("/{videoName}")
    public ResponseEntity<FileSystemResource> inventory(@PathVariable String videoName) {

        // Construye la ruta completa del video
        String videoPath = "/app/resources/video/" + videoName + ".mp4";
        FileSystemResource fileSystemResource = new FileSystemResource(new File(videoPath));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", videoName + ".mp4");

        try {
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(fileSystemResource.contentLength())
                    .body(fileSystemResource);
        } catch (IOException e) {

            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
