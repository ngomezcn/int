package com.staxrt.tutorial.services;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class AvatarService {

    public void createDefaultAvatar(String username, String uploadDir) throws IOException
    {
        String imageUrl = "https://ui-avatars.com/api/?background=0D8ABC&color=fff&name=" + username + "&length=2&bold=true"; // TODO: Obtener desdte application.properties

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.exchange(imageUrl, HttpMethod.GET, null, byte[].class);
        byte[] imageBytes = response.getBody();

        String filename = username + ".png";
        FileOutputStream fos = new FileOutputStream(uploadDir + filename);
        fos.write(imageBytes);
        fos.close();
    }

    public Resource getAvatarImageByUsername(String imageName, String uploadDir) throws IOException
    {
        Path imagePath = Paths.get(uploadDir).resolve(imageName).normalize();
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return resource;
        }
        return null;
    }
}
