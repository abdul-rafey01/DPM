package com.example.Doctor_Patient_Management.Service;

import com.example.Doctor_Patient_Management.GlobalExceptionHandler;
import com.example.Doctor_Patient_Management.Repository.CustomRepository;
import com.example.Doctor_Patient_Management.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bson.Document;
import java.util.stream.Collectors;


@Service
public class DoctorService {



    @Autowired
    private CustomRepository customRepository;

    public Doctor addDoctor(Doctor doctor) {
        doctor.setId(UUID.randomUUID().toString());
        if (doctor.getName().isEmpty()){
            throw new GlobalExceptionHandler.InvalidFieldException("Doctor Name is not provided : ");
        }
        if (doctor.getSpecialization().isEmpty()){
            throw new GlobalExceptionHandler.InvalidFieldException("Doctor Specialization is not provided : ");
        }
        Document doctorDocument = new Document("_id", doctor.getId())
                .append("name", doctor.getName())
                .append("specialization", doctor.getSpecialization());

        boolean isSaved = customRepository.saveDocument(doctorDocument, "doctors");

        if (!isSaved) {
            throw new RuntimeException("Failed to save doctor");
        }

        return doctor;
    }

    public List<Doctor> getAllDoctors() {
        Document queryDoc = new Document();

        List<Document> doctorDocuments = customRepository.findDocuments("doctors", queryDoc);

        return doctorDocuments.stream().map(this::convertDocumentToDoctor).collect(Collectors.toList());
    }


    public Optional<Doctor> getDoctorById(String id) {
        Document doctorDoc = customRepository.findDocumentById("doctors", id);
        if (doctorDoc != null) {
            return Optional.of(convertDocumentToDoctor(doctorDoc));
        } else {
            return Optional.empty();
        }
    }

    public Doctor updateDoctor(String id, Doctor doctor) {
        doctor.setId(UUID.randomUUID().toString());
        if (doctor.getName().isEmpty()){
            throw new GlobalExceptionHandler.InvalidFieldException("Doctor Name is not provided : ");
        }
        if (doctor.getSpecialization().isEmpty()){
            throw new GlobalExceptionHandler.InvalidFieldException("Doctor Specialization is not provided : ");
        }
        Document doctorDocument = new Document("_id", doctor.getId())
                .append("name", doctor.getName())
                .append("specialization", doctor.getSpecialization());

        boolean isSaved = customRepository.saveDocument(doctorDocument, "doctors");

        if (!isSaved) {
            throw new RuntimeException("Failed to update doctor");
        }

        return doctor;
    }

    public String deleteDoctor(String id) {
        boolean isDeleted = customRepository.deleteDocument("doctors", id);

        if (!isDeleted) {
            throw new RuntimeException("Failed to delete doctor with id: " + id);
        }

        return "Doctor is successfully deleted";
    }


    private Doctor convertDocumentToDoctor(Document doc) {
        Doctor doctor = new Doctor();
        doctor.setId(doc.getString("_id"));
        doctor.setName(doc.getString("name"));
        doctor.setSpecialization(doc.getString("specialization"));
        return doctor;
    }
}