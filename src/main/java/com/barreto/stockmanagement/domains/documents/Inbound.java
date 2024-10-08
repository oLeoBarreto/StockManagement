package com.barreto.stockmanagement.domains.documents;

import com.barreto.stockmanagement.domains.AbstractDomain;
import com.barreto.stockmanagement.domains.Company;
import com.barreto.stockmanagement.domains.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Inbound extends AbstractDomain {
    @NotNull(message = "Product could not be null")
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull(message = "Quantity could not be null")
    @Positive(message = "Quantity price must be more than 0")
    private Float quantity;

    private DocumentStatus status;

    @NotNull(message = "Company id could not be null")
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "company_id")
    private Company company;

    public Inbound(Product product, Float quantity, Company company) {
        this.product = product;
        this.quantity = quantity;
        this.status = DocumentStatus.WAITING;
        this.company = company;
    }
}
