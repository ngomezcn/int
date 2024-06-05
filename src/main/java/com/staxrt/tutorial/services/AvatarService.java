package com.staxrt.tutorial.services;

import com.staxrt.tutorial.entity.UserEntity;
import com.staxrt.tutorial.exception.ResourceNotFoundException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class AvatarService {

    @Value("${upload.dir}")
    private String uploadDir;

    public static String generateHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void createAvatar(UserEntity user) throws IOException {
        String imageUrl = "https://ui-avatars.com/api/?background=0D8ABC&color=fff&name=" + user.getEmail() + "&length=2&bold=true"; // TODO: Obtener desdte application.properties

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<byte[]> response = restTemplate.exchange(imageUrl, HttpMethod.GET, null, byte[].class);
        byte[] imageBytes = response.getBody();

        String filename = generateHash(user.getEmail()) + ".png";
        FileOutputStream fos = new FileOutputStream(uploadDir + filename);
        fos.write(imageBytes);
        fos.close();
    }

    public Resource getAvatarImageByEmail(String imageName) throws IOException, ResourceNotFoundException {

        Path imagePath = Paths.get(uploadDir).resolve(generateHash(imageName) + ".png").normalize();
        Resource resource = new UrlResource(imagePath.toUri());

        if (resource.exists() && resource.isReadable()) {
            return resource;
        }
        throw new ResourceNotFoundException("");
    }
}
