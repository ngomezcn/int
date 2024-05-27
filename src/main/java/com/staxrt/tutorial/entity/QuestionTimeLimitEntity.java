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

package com.staxrt.tutorial.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;


@Entity
@Table(name = "question_time_limits")
@EntityListeners(AuditingEntityListener.class)
public class QuestionTimeLimitEntity implements Comparable<QuestionTimeLimitEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "time_in_seconds", nullable = false, unique = true)
    private int time_in_seconds;

    public int getTime_in_seconds() {
        return time_in_seconds;
    }

    public void setTime_in_seconds(int time_in_seconds) {
        this.time_in_seconds = time_in_seconds;
    }

    @Override
    public int compareTo(QuestionTimeLimitEntity other) {
        return Integer.compare(this.time_in_seconds, other.time_in_seconds);
    }
}
