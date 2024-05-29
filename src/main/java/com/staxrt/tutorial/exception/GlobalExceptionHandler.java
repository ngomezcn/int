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

package com.staxrt.tutorial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * The type Global exception handler.
 *
 * @author Givantha Kalansuriya
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UserAlreadyRegistered.class)
  public ResponseEntity<?> userAlreadyRegistered(
          UserAlreadyRegistered ex, WebRequest request) {
    ErrorResponse errorDetails =
            new ErrorResponse(new Date(), HttpStatus.CONFLICT.toString(), ex.getMessage(), request.getDescription(false), ex.getClass().getName());
    return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(UserAlreadyRegisteredAndVerifiedException.class)
  public ResponseEntity<?> userAlreadyRegisteredAndVerifiedException(
          UserAlreadyRegisteredAndVerifiedException ex, WebRequest request) {
    ErrorResponse errorDetails =
            new ErrorResponse(new Date(), HttpStatus.CONFLICT.toString(), ex.getMessage(), request.getDescription(false), ex.getClass().getName());
    return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<?> usernameNotFoundException(
          UsernameNotFoundException ex, WebRequest request) {
    ErrorResponse errorDetails =
            new ErrorResponse(new Date(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), request.getDescription(false), ex.getClass().getName());
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<?> badCredentialsExceptionException(
          BadCredentialsException ex, WebRequest request) {
    ErrorResponse errorDetails =
            new ErrorResponse(new Date(), HttpStatus.UNAUTHORIZED.toString(), ex.getMessage(), request.getDescription(false), ex.getClass().getName());
    return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
  }

   /**
   * Resource not found exception response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<?> resourceNotFoundException(
      ResourceNotFoundException ex, WebRequest request) {
    ErrorResponse errorDetails =
        new ErrorResponse(new Date(), HttpStatus.NOT_FOUND.toString(), ex.getMessage(), request.getDescription(false), ex.getClass().getName());
    return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
  }

  /**
   * User not activated exception response entity.
   *
   * @param ex the ex
   * @param request the requests
   * @return the response entity
   */
  @ExceptionHandler(DisabledException.class)
  public ResponseEntity<?> userNotActivatedException(
          DisabledException ex, WebRequest request) {
    ErrorResponse errorDetails =
            new ErrorResponse(new Date(), HttpStatus.FORBIDDEN.toString(), ex.getMessage(), request.getDescription(false), ex.getClass().getName());
    return new ResponseEntity<>(errorDetails, HttpStatus.FORBIDDEN);
  }

  /**
   * Globle excpetion handler response entity.
   *
   * @param ex the ex
   * @param request the request
   * @return the response entity
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
    ErrorResponse errorDetails =
        new ErrorResponse(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.toString() ,ex.getMessage(), request.getDescription(false), ex.getClass().getName());
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
