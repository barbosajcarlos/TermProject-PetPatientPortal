package com.app.ppportal.model;

import java.io.Serializable;
import java.util.ArrayList;

public class PetData implements Serializable {
    private String id;
    private String petCategory;
    private String petName;
    private String petDOB;
    private String petPhoneNumber;
    private String petEmergencyContact;
    private String petHomeAddress;
    private String petHeight;
    private String petWeight;
    private String petTemperature;
    private String petKnownAllergies;
    private String petImmunizationHistory;
    private ArrayList<VisitHistoryData> petReasonForVisit;

    public String getPetImage() {
        return petImage;
    }

    public void setPetImage(String petImage) {
        this.petImage = petImage;
    }

    private String petImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPetCategory() {
        return petCategory;
    }

    public void setPetCategory(String petCategory) {
        this.petCategory = petCategory;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetDOB() {
        return petDOB;
    }

    public void setPetDOB(String petDOB) {
        this.petDOB = petDOB;
    }

    public String getPetPhoneNumber() {
        return petPhoneNumber;
    }

    public void setPetPhoneNumber(String petPhoneNumber) {
        this.petPhoneNumber = petPhoneNumber;
    }

    public String getPetEmergencyContact() {
        return petEmergencyContact;
    }

    public void setPetEmergencyContact(String petEmergencyContact) {
        this.petEmergencyContact = petEmergencyContact;
    }

    public String getPetHomeAddress() {
        return petHomeAddress;
    }

    public void setPetHomeAddress(String petHomeAddress) {
        this.petHomeAddress = petHomeAddress;
    }

    public String getPetHeight() {
        return petHeight;
    }

    public void setPetHeight(String petHeight) {
        this.petHeight = petHeight;
    }

    public String getPetWeight() {
        return petWeight;
    }

    public void setPetWeight(String petWeight) {
        this.petWeight = petWeight;
    }

    public String getPetTemperature() {
        return petTemperature;
    }

    public void setPetTemperature(String petTemperature) {
        this.petTemperature = petTemperature;
    }

    public String getPetKnownAllergies() {
        return petKnownAllergies;
    }

    public void setPetKnownAllergies(String petKnownAllergies) {
        this.petKnownAllergies = petKnownAllergies;
    }

    public String getPetImmunizationHistory() {
        return petImmunizationHistory;
    }

    public void setPetImmunizationHistory(String petImmunizationHistory) {
        this.petImmunizationHistory = petImmunizationHistory;
    }

    public ArrayList<VisitHistoryData> getPetReasonForVisit() {
        return petReasonForVisit;
    }

    public void setPetReasonForVisit(ArrayList<VisitHistoryData> petReasonForVisit) {
        this.petReasonForVisit = petReasonForVisit;
    }
}
