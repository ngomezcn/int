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

package com.staxrt.tutorial.model;

import com.staxrt.tutorial.dto.AuthDTOS.AuthUserDTO;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
                                            
import javax.persistence.*;

import java.util.Date;


@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {

    public User(AuthUserDTO user) {
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.emailAddress = user.emailAddress;
        this.passwordHash = user.password; // ONLY DEVELOPMENT
        this.passwordSalt = user.password; // ONLY DEVELOPMENT
    }

    public User() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "password_salt", nullable = false)
    private String passwordSalt;

    @Column(name = "is_teacher", nullable = false)
    private Boolean isTeacher = false;

    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;


    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public void setTeacher(Boolean teacher) {
        isTeacher = teacher;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public Boolean getTeacher() {
        return isTeacher;
    }

    public Boolean getVerified() {
        return isVerified;
    }

  public long getId() {
        return id;
    }

  public void setId(long id) {
        this.id = id;
    }

  public String getFirstName() {
        return firstName;
    }

  public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

  public String getLastName() {
        return lastName;
    }

  public void setLastName(String lastName) {
        this.lastName = lastName;
    }

  public String getEmailAddress() {
        return emailAddress;
    }

  public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

  public Date getCreatedAt() {
        return createdAt;
    }

  public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

  public Date getUpdatedAt() {
        return updatedAt;
    }

  public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
