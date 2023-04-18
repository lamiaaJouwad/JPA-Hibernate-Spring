package com.example.hospital;

import com.example.hospital.entities.*;
import com.example.hospital.repositories.ConsultationRepository;
import com.example.hospital.repositories.MedecinRepository;
import com.example.hospital.repositories.PatientRepository;
import com.example.hospital.repositories.RendezVousRepository;
import com.example.hospital.service.IHospitalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class HospitalApplication {

	public static void main(String[] args) {
		SpringApplication.run(HospitalApplication.class, args);
	}
	@Bean
	CommandLineRunner start(IHospitalService hospitalService ,
							PatientRepository patientRepository,
							MedecinRepository medecinRepository,
							RendezVousRepository rendezVousRepository){
	return args -> {
		Stream.of("lamiaa", "abir", "yasmine")
				.forEach(name -> {
					;
					Patient patient = new Patient();
					patient.setNom(name);
					patient.setDateNaissance(new Date());
					patient.setMalade(false);
					hospitalService.savePatient(patient);

				});
		Stream.of("lamiaa1", "abir1", "yasmine1")
				.forEach(name -> {
					Medecin medecin = new Medecin();
					medecin.setNom(name);
					medecin.setEmail(name +"gmail.com");
					medecin.setSpecialite(Math.random()>0.5?"Cardio":"Dentiste");
					hospitalService.saveMedecin(medecin);
				});
		Patient patient = patientRepository.findById(1L).orElse(null);
		Patient patient1=patientRepository.findByNom("lamiaa");
		Medecin medecin=medecinRepository.findByNom("lamiaa1");

		RendezVous rendezVous = new RendezVous();
		rendezVous.setDate(new Date());
		rendezVous.setStatus(StatusRDV.PENDING);
		rendezVous.setMedecin(medecin);
		rendezVous.setPatient(patient);
		RendezVous saveRDV = hospitalService.saveRDV(rendezVous);
		System.out.println(saveRDV.getId());


		RendezVous rendezVous1=rendezVousRepository.findAll().get(0);
		Consultation consultation = new Consultation();
		consultation.setDateConsultation(new Date());
		consultation.setRendezVous(rendezVous1);
		consultation.setRapport("Rapport de la consultation .....");
		hospitalService.saveConsultation(consultation);
	};
	}
}
