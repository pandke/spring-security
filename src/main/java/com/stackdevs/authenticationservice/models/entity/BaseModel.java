package com.stackdevs.authenticationservice.models.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serial;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@MappedSuperclass
@ToString
public class BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1000L;

    @CreatedDate
    @Column(name = "created_at")
    @JsonProperty("created_by")
    private ZonedDateTime createdAt;

    @CreatedBy
    @Column(name = "created_by")
    @JsonProperty("created_by")
    private Long createdBy;

    @LastModifiedDate
    @Column(name = "updated_at")
    @JsonProperty("updated_at")
    private ZonedDateTime updatedAt;

    @LastModifiedBy
    @Column(name = "updated_by")
    @JsonProperty("updated_by")
    private Long updatedBy;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "user_id", insertable = false, updatable = false)
    @JsonProperty("created_by")
    private User userCreatedBy;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by", referencedColumnName = "user_id",  insertable = false, updatable = false)
    @JsonProperty("updated_by")
    private User userUpdatedBy ;
}
