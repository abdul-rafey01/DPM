package com.example.Doctor_Patient_Management.Service;

import com.example.Doctor_Patient_Management.GlobalExceptionHandler;
import com.example.Doctor_Patient_Management.Repository.CustomRepository;
import com.example.Doctor_Patient_Management.entity.Patient;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private CustomRepository customRepository;

    public Patient addPatient(Patient patient) {
        patient.setId(UUID.randomUUID().toString());
        validatePatient(patient);

        Document patientDocument = new Document("_id", patient.getId())
                .append("name", patient.getName())
                .append("age", patient.getAge())
                .append("doctorId", patient.getDoctorId());

        boolean isSaved = customRepository.saveDocument(patientDocument, "patients");

        if (!isSaved) {
            throw new RuntimeException("Failed to save patient");
        }

        return patient;
    }

    public List<Patient> getAllPatients() {
        Document queryDoc = new Document();

        List<Document> patientDocuments = customRepository.findDocuments("patients", queryDoc);

        return patientDocuments.stream().map(this::convertDocumentToPatient).collect(Collectors.toList());
    }

    public Optional<Patient> getPatientById(String id) {
        Document patientDoc = customRepository.findDocumentById("patients", id);
        if (patientDoc != null) {
            return Optional.of(convertDocumentToPatient(patientDoc));
        } else {
            return Optional.empty();
        }
    }

    public Patient updatePatient(String id, Patient patient) {
        patient.setId(id);
        validatePatient(patient);
        Document patientDocument = new Document("_id", patient.getId())
                .append("name", patient.getName())
                .append("age", patient.getAge())
                .append("doctorId", patient.getDoctorId());
        boolean isSaved = customRepository.saveDocument(patientDocument, "patients");

        if (!isSaved) {
            throw new RuntimeException("Failed to update patient");
        }

        return patient;
    }

    public String deletePatient(String id) {
        boolean isDeleted = customRepository.deleteDocument("patients", id);

        if (!isDeleted) {
            throw new RuntimeException("Failed to delete patient with id: " + id);
        }

        return "Patient successfully deleted";
    }

    public List<Patient> getPatientsByDoctorId(String doctorId) {
        Document queryDoc = new Document("doctorId", doctorId);

        List<Document> patientDocuments = customRepository.findDocuments("patients", queryDoc);

        return patientDocuments.stream().map(this::convertDocumentToPatient).collect(Collectors.toList());
    }

    private Patient convertDocumentToPatient(Document doc) {
        Patient patient = new Patient();
        patient.setId(doc.getString("_id"));
        patient.setName(doc.getString("name"));
        patient.setAge(doc.getInteger("age"));
        patient.setDoctorId(doc.getString("doctorId"));
        return patient;
    }

    private void validatePatient(Patient patient) {
        if (patient.getName().isEmpty()) {
            throw new GlobalExceptionHandler.InvalidFieldException("Patient Name is not provided");
        }
        if (patient.getDoctorId().isEmpty()) {
            throw new GlobalExceptionHandler.InvalidFieldException("Doctor ID is not provided");
        }
        if (patient.getAge() < 0) {
            throw new GlobalExceptionHandler.InvalidFieldException("Age should be greater than or equal to 0");
        }
    }
}
