package com.example.Doctor_Patient_Management.Controller;


import com.example.Doctor_Patient_Management.entity.Doctor;
import com.example.Doctor_Patient_Management.entity.Patient;
import com.example.Doctor_Patient_Management.Service.DoctorService;
import com.example.Doctor_Patient_Management.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @PostMapping
    public Doctor createDoctor(@RequestBody Doctor doctor) {

        return doctorService.addDoctor(doctor);

    }

    @GetMapping
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/{id}")
    public Optional<Doctor> getDoctorById(@PathVariable String id) {
        return doctorService.getDoctorById(id);
    }

    @PutMapping("/{id}")
    public Doctor updateDoctor(@PathVariable String id, @RequestBody Doctor doctor) {
        return doctorService.updateDoctor(id, doctor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable String id) {
        String responseMessage = doctorService.deleteDoctor(id);
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/{id}/patients")
    public List<Patient> getPatientsByDoctorId(@PathVariable String id) {
        return patientService.getPatientsByDoctorId(id);
    }
}