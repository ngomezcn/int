package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.services.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/images")
public class ImagesController {

    @Value("${upload.dir}")
    String uploadDir;

    AvatarService avatarService = new AvatarService();

    @PostMapping("/create-avatar")
    @ResponseBody
    public ResponseEntity<String> createAvatar() throws IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        avatarService.createDefaultAvatar(username, uploadDir);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) throws IOException {

        Resource resource = avatarService.getAvatarImageByUsername(imageName, uploadDir);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }
}
