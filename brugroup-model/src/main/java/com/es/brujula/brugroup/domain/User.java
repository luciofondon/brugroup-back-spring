package com.es.brujula.brugroup.domain;

import com.es.brujula.brugroup.domain.common.BaseEntity;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "USER")
public class User extends BaseEntity {

    @Column(name = "US_FULLNAME")
    private String fullName;

    @Column(name = "US_USERNAME")
    private String username;

    @Column(name = "US_PASSWORD")
    private String password;

    @Column(name = "US_LASTUPDATE")
    private LocalDateTime lastUpdate;

}
