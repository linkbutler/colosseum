/*
 * Copyright (c) 2014-2015 University of Ulm
 *
 * See the NOTICE file distributed with this work for additional information
 * regarding copyright ownership.  Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package models.generic;

import com.google.common.collect.ImmutableList;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import models.Cloud;
import models.CloudCredential;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by daniel on 22.09.15.
 */
@Entity public abstract class RemoteResourceInCloud extends RemoteResource {

    @ManyToOne(optional = false) private Cloud cloud;
    @Nullable @Column(nullable = true) private String providerId;
    @Nullable @Column(nullable = true) private String swordId;
    @ManyToMany private List<CloudCredential> cloudCredentials;
    @ManyToOne private CloudCredential owner;

    /**
     * No-args constructor for hibernate.
     */
    protected RemoteResourceInCloud() {
    }

    public RemoteResourceInCloud(Cloud cloud) {
        super(null);
        this.cloud = cloud;
    }

    public RemoteResourceInCloud(@Nullable String remoteId, @Nullable String providerId,
        @Nullable String swordId, Cloud cloud, @Nullable CloudCredential owner) {
        super(remoteId);
        this.providerId = providerId;
        this.swordId = swordId;
        this.cloud = cloud;
        this.owner = owner;
    }

    public Cloud cloud() {
        return cloud;
    }

    public List<CloudCredential> cloudCredentials() {
        if (cloudCredentials == null) {
            cloudCredentials = Collections.emptyList();
        }
        return ImmutableList.copyOf(cloudCredentials);
    }

    public void addCloudCredential(CloudCredential cloudCredential) {
        this.cloudCredentials.add(cloudCredential);
    }

    public Optional<CloudCredential> owner() {
        return Optional.ofNullable(owner);
    }

    public void bindOwner(CloudCredential owner) {
        checkNotNull("Setting a null owner is not allowed");
        if (this.owner != null) {
            throw new IllegalStateException("Changing the owner is not allowed.");
        }
        this.owner = owner;
    }

    public Optional<String> providerId() {
        return Optional.ofNullable(providerId);
    }

    public Optional<String> swordId() {
        return Optional.ofNullable(swordId);
    }

    public void bindProviderIds(String swordId, String providerId) {
        checkNotNull(swordId, "Binding null swordId is not allowed");
        checkNotNull(providerId, "Binding null providerId is not allowed");
        if (this.providerId != null) {
            throw new IllegalStateException("Changing the providerId is not allowed.");
        }
        if (this.swordId != null) {
            throw new IllegalStateException("Changing the swordId is not allowed.");
        }
        this.providerId = providerId;
        this.swordId = swordId;
    }
}
