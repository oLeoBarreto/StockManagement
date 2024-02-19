package com.barreto.stockmanagement.domains.documents;

import com.barreto.stockmanagement.domains.AbstractDomain;
import com.barreto.stockmanagement.domains.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Outbound extends AbstractDomain {
    @NotNull(message = "Product could not be null")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @NotNull(message = "Quantity could not be null")
    @Positive(message = "Quantity price must be more than 0")
    private Float quantity;

    private DocumentStatus status;

    public Outbound(Product product, Float quantity) {
        this.product = product;
        this.quantity = quantity;
        this.status = DocumentStatus.WAITING;
    }
}
