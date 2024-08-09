package com.example.Doctor_Patient_Management.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "patients")
public class Patient {
    @Id
    private String id;
    @NotEmpty(message = "Name is required")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    @Min(value = 0, message = "Age should be greater than or equal to 0")
    private Integer age;
    @NotEmpty(message = "Doctor ID is required")
    private String doctorId;
}