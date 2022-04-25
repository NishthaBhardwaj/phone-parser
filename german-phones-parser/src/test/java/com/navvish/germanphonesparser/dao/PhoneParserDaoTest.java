package com.navvish.germanphonesparser.dao;

import com.navvish.germanphonesparser.PhoneParserDto;
import com.navvish.germanphonesparser.exception.TaskNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.HashSet;
import java.util.Optional;
import java.util.HashMap;

class PhoneParserDaoTest {

    private final PhoneParserDao phoneParserDao = new PhoneParserDao();

    @BeforeEach
    void dataInitializer() {
        Set<String> validPhoneNumbers = new HashSet<>();
        validPhoneNumbers.add("+4934567231429");
        validPhoneNumbers.add("004934567231429");
        PhoneParserDao.validPhoneNumberCache.put("T-123456", PhoneParserDto.builder().
                fileName("phone_numbers_1.txt")
                .taskID("T-123456")
                .validPhoneNumbers(validPhoneNumbers).build());
        PhoneParserDao.validPhoneNumberCache.put("T-123457", PhoneParserDto.builder().
                fileName("phone_numbers_2.txt")
                .taskID("T-123457")
                .validPhoneNumbers(validPhoneNumbers).build());

    }

    @Test
    @DisplayName("When File Name Exists in Cache")
    void checkIfFileAlreadyProcessed() {
        Optional<String> optional = phoneParserDao.checkIfFileAlreadyProcessed("phone_numbers_2.txt");
        String actualTaskId = optional.orElse("");
        Assertions.assertEquals("T-123457", actualTaskId);

    }

    @Test
    @DisplayName("When File Name Does Not Exist in Cache")
    void checkIfFileAlreadyProcessedNotExist() {
        Optional<String> optional = phoneParserDao.checkIfFileAlreadyProcessed("phone_numbers_3.txt");
        String actualTaskId = optional.orElse(StringUtils.EMPTY);
        Assertions.assertEquals(StringUtils.EMPTY, actualTaskId);

    }

    @Test
    @DisplayName("When Task is Present in Cache")
    void getPhoneNumbersByTaskId() {
        PhoneParserDto expected = PhoneParserDao.validPhoneNumberCache.get("T-123457");
        PhoneParserDto actual = phoneParserDao.getPhoneNumbersByTaskId("T-123457");
        Assertions.assertSame(expected, actual);

    }

    @Test
    @DisplayName("When Task is Not Present in Cache")
    void getPhoneNumbersByTaskId_NotExist() {
        String taskId = "T-123457";
        phoneParserDao.getPhoneNumbersByTaskId(taskId);
        Assertions.assertThrows(TaskNotFoundException.class,
                () -> {
                    throw new TaskNotFoundException(taskId + " not found");
                });

    }

    @Test
    void getAllTaskIds() {
        Assertions.assertEquals(phoneParserDao.getAllTaskIds(), PhoneParserDao.validPhoneNumberCache.keySet());
    }

    @Test
    @DisplayName("Should throw Task Not Found Exception")
    void getAllTaskIdsThrowException() {
        PhoneParserDao.validPhoneNumberCache.putAll(new HashMap<>());
        phoneParserDao.getAllTaskIds();
        Assertions.assertThrows(TaskNotFoundException.class,
                () -> {
                    throw new TaskNotFoundException("Task not available");
                });
    }

}