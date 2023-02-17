package com.xarala.sunudocto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SunudoctoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SunudoctoApplication.class, args);
    }

  /*  @Bean
    CommandLineRunner runner(
            DoctorRepository doctorRepository,
            PatientRepository patientRepository
    ) {
        return args -> {
            List<String> speciality = List.of("Laborentin", "Dentiste", "Generaliste", "Opticien", "Pédiatre", "Génycologue", "Audioprothesiste", "Chirurgien", "Ophtalmologue", "Radiologue");
            List<Doctor> doctors = new ArrayList<>();
            List<Patient> patients = new ArrayList<>();

            for (int i = 0; i < 10; i++) {
                Doctor doctor = new Doctor();
                doctor.setFirstName("Doctor" + i);
                doctor.setLastName("Doctor" + i);
                doctor.setRib("1" + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i + i);
                doctor.setMail("doctor" + i + "@sunudoctor.sn");
                if (i % 2 == 0) doctor.setGender(Gender.MALE);
                else doctor.setGender(Gender.FEMALE);
                doctor.setSpeciality(speciality.get(i));
                doctor.setPhone("77 123 45 6" + i);
                doctor.setCreatedAt(LocalDateTime.now().toString());
                doctor.setLatitude(14.761052580936733+i);
                doctor.setLongitude(-17.44077120309451+i);
                doctors.add(doctor);
            }

            for (int i = 0; i < 10; i++) {
                Patient patient = new Patient();
                patient.setFirstName("Patient" + i);
                patient.setLastName("Patient" + i);
                patient.setCardNumber("1" + i + i + i + " " + i + i + i + i + " " + i + i + i + i + " " + i + i + i + i);
                patient.setMail("doctor" + i + "@sunudoctor.sn");
                if (i % 2 == 0) patient.setGender(Gender.MALE);
                else patient.setGender(Gender.FEMALE);
                patient.setAddress("Rue Xarala n°" + i+1);
                patient.setPhone("77 123 45 6" + i);
                patient.setCreatedAt(LocalDateTime.now().toString());
                patient.setLatitude(14.761052580936733+i);
                patient.setLongitude(-17.44077120309451+i);
                patients.add(patient);
            }

             doctorRepository.saveAll(doctors);
             patientRepository.saveAll(patients);
        };
    }*/

}
