package com.barreto.stockmanagement.domains;

import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Product extends AbstractDomain {
    public String name;
    public String description;
    public BigDecimal unitPrice;
    public String supplier;
    public String category;
}
