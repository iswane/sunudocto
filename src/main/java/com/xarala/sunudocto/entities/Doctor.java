package com.xarala.sunudocto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
//@DiscriminatorValue("DOCTOR")
public class Doctor extends Person{
    private String speciality;
    private String structure;
    private String rib;

    @OneToMany
    private Set<Patient> patients;
}
