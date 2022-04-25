package com.navvish.germanphonesparser.dao;

import com.navvish.germanphonesparser.PhoneParserDto;
import com.navvish.germanphonesparser.exception.TaskNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class PhoneParserDao {

    static ConcurrentMap<String, PhoneParserDto> validPhoneNumberCache = new ConcurrentHashMap<>();

    public PhoneParserDto getPhoneNumbersByTaskId(String taskId) {
        return Optional.ofNullable(validPhoneNumberCache.get(taskId))
                .orElseThrow(() -> new TaskNotFoundException(taskId +" not found"));

    }

    public Set<String> getAllTaskIds() {
        Set<String> validNumbers = validPhoneNumberCache.keySet();
        if (validNumbers.isEmpty()) {
            throw new TaskNotFoundException("Task not available");
        }
        return validNumbers;
    }

    public String saveNumbersAndReturnTaskId(PhoneParserDto phoneParserDto) {
        validPhoneNumberCache.put(phoneParserDto.getTaskID(), phoneParserDto);
        return phoneParserDto.getTaskID();
    }

    public String deleteByTaskId(String taskId) {
        PhoneParserDto phoneParserDto = Optional.ofNullable(validPhoneNumberCache.remove(taskId))
                .orElseThrow(() -> new TaskNotFoundException(taskId + " not found"));
        return phoneParserDto.getTaskID();

    }

    public Optional<String> checkIfFileAlreadyProcessed(String fileName) {

        if (!validPhoneNumberCache.isEmpty()) {
            Collection<PhoneParserDto> values = validPhoneNumberCache.values();
            return Optional.of(values.stream()
                    .filter(phoneDto -> phoneDto.getFileName().equalsIgnoreCase(fileName))
                    .map(PhoneParserDto::getTaskID)
                    .collect(Collectors.joining()));

        }
        return Optional.empty();
    }

}