package com.patika.paycorecreditsystemproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "credit_apply")
public class CreditApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_national_id",referencedColumnName = "national_id")
    private Customer customer;

    @NotNull
    @Column(name = "is_application_approved")
    private boolean isApplicationApproved;

    @Column(name = "credit_limit")
    private int creditLimit;

    @Column(name = "apply_date")
    private Date applyDate;

}
