package com.navvish.germanphonesparser.service;

import com.navvish.germanphonesparser.PhoneParserDto;

import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface PhoneParserService {

    Set<String> filterValidPhoneNumbers(Stream<String> stream);
    PhoneParserDto getPhoneNumbersByTaskId(String taskId);
    String getTaskId(String fileName, Stream<String> lines);
    Set<String> getAllTasks();
    String deleteTask(String taskId);
    Optional<String> isFileAlreadyProcessed(String fileName);

    // TODO
    default String createTaskID() {
        Supplier<String> randomSupplier = () -> "T" + new Random().nextInt();
        return randomSupplier.get();

    }
}