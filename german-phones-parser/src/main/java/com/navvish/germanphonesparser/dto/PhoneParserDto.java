package com.navvish.germanphonesparser.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public class PhoneParserDto {

    private String fileName;
    private Set<String> validPhoneNumbers;
    private String taskID;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Set<String> getValidPhoneNumbers() {
        return validPhoneNumbers;
    }

    public void setValidPhoneNumbers(Set<String> validPhoneNumbers) {
        this.validPhoneNumbers = validPhoneNumbers;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    @Override
    public String toString() {
        return "PhoneParserDto{" +
                "fileName='" + fileName + '\'' +
                ", validPhoneNumbers=" + validPhoneNumbers +
                ", taskID='" + taskID + '\'' +
                '}';
    }
}
