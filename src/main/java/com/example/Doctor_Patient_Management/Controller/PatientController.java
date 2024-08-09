package com.example.Doctor_Patient_Management.Controller;

import com.example.Doctor_Patient_Management.entity.Patient;
import com.example.Doctor_Patient_Management.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.addPatient(patient);
    }

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public Optional<Patient> getPatientById(@PathVariable String id) {
        return patientService.getPatientById(id);
    }

    @PutMapping("/{id}")
    public Patient updatePatient(@PathVariable String id, @RequestBody Patient patient) {
        return patientService.updatePatient(id, patient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(@PathVariable String id) {
        String responseMessage = patientService.deletePatient(id);
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Patient> getPatientsByDoctorId(@PathVariable String doctorId) {
        return patientService.getPatientsByDoctorId(doctorId);
    }
}

