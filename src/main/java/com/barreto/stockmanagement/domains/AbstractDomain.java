package com.barreto.stockmanagement.domains;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@MappedSuperclass
public class AbstractDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;
    @CreatedDate
    public Date createdAt;
    @UpdateTimestamp
    public Date updatedAt;
}
