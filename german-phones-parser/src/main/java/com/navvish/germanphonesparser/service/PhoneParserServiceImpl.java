package com.navvish.germanphonesparser.service;

import com.navvish.germanphonesparser.PhoneParserDto;
import com.navvish.germanphonesparser.dao.PhoneParserDao;
import com.navvish.germanphonesparser.exception.ValidPhoneNumbersNotFound;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PhoneParserServiceImpl implements PhoneParserService{

    private final PhoneParserDao phoneParserDao;
    private static final String ERROR_MESSAGE = "Valid phone numbers not found. Please upload another file.";

    @Override
    public Set<String> filterValidPhoneNumbers(Stream<String> stream) {
        Set<String> validData = stream.map(line -> line.replaceAll("\\s", ""))
                .filter(line -> (Pattern.matches("^[+]49\\d{11}$", line)
                 || Pattern.matches("^0049\\d{11}$", line)))
                .collect(Collectors.toSet());
        if(validData.isEmpty()){
            throw new ValidPhoneNumbersNotFound(ERROR_MESSAGE);
        }
        return validData;

    }

    @Override
    public PhoneParserDto getPhoneNumbersByTaskId(String taskId) {
        return phoneParserDao.getPhoneNumbersByTaskId(taskId);
    }

    @Override
    public String getTaskId(String fileName, Stream<String> lines){
        Set<String> validPhoneNumbers = filterValidPhoneNumbers(lines);
        PhoneParserDto phone = PhoneParserDto.builder()
                .fileName(fileName)
                .validPhoneNumbers(validPhoneNumbers)
                .taskID(createTaskID())
                .build();
        return phoneParserDao.saveNumbersAndReturnTaskId(phone);

    }

    @Override
    public Set<String> getAllTasks() {
        return phoneParserDao.getAllTaskIds();
    }

    @Override
    public String deleteTask(String taskId) {
        return phoneParserDao.deleteByTaskId(taskId);
    }

    @Override
    public Optional<String> isFileAlreadyProcessed(String fileName) {
        return phoneParserDao.checkIfFileAlreadyProcessed(fileName);
    }

}