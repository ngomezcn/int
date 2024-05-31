/*
 *
 *  Copyright (c) 2018-2020 Givantha Kalansuriya, This source is a part of
 *   Staxrt - sample application source code.
 *   http://staxrt.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.staxrt.tutorial.controller;

import com.staxrt.tutorial.dto.entities.UserDTO;
import com.staxrt.tutorial.entity.QuestionTimeLimitEntity;
import com.staxrt.tutorial.entity.UserEntity;
import com.staxrt.tutorial.exception.ResourceNotFoundException;
import com.staxrt.tutorial.repository.UserRepository;
import com.staxrt.tutorial.services.AvatarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The type User controller.
 *
 * @author Givantha Kalansuriya
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  AvatarService avatarService;

  @GetMapping("/users")
  public List<UserEntity> getAllUsers() {
    List<UserEntity> a = userRepository.findAll();
     return a;
  }

  @GetMapping("/teacher-roster")
  public ResponseEntity<List<UserDTO>> getAllTimeLimits() {

      return ResponseEntity.ok(new ArrayList<>());
  }

  @GetMapping("/avatar/{email}")
  public ResponseEntity<Resource> getUserAvatar(@PathVariable String email) throws IOException, ResourceNotFoundException {

    Resource resource = avatarService.getAvatarImageByEmail(email);

    return ResponseEntity.ok()
            .contentType(MediaType.IMAGE_JPEG)
            .body(resource);
  }

  @PostMapping("/create-avatar")
  @ResponseBody
  public ResponseEntity<String> createAvatar() throws IOException {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserEntity user = (UserEntity) authentication.getPrincipal();


    avatarService.createAvatar(user);

    return ResponseEntity.ok().build();
  }
}
