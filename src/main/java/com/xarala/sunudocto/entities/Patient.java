package com.xarala.sunudocto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
//@DiscriminatorValue("PATIENT")
public class Patient extends Person {
    private String occupation;
    private String address;
    private String cardNumber;

    @ManyToOne
    private Doctor doctor;
}
