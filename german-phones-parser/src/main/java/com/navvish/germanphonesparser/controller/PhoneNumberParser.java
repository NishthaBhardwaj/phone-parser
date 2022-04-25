package com.navvish.germanphonesparser.controller;


import com.navvish.germanphonesparser.PhoneParserDto;
import com.navvish.germanphonesparser.service.PhoneParserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Set;
import java.util.stream.Stream;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/german-phones-parser")
public class PhoneNumberParser {

    private final PhoneParserService phoneParserService;

    private static final String FILE_EMPTY_MESSAGE = "You must select a file!";
    private static final String FILE_NOT_SUPPORTED_MESSAGE = "Only Text format is supported!";
    private static final String CONTENT_TYPE_SUPPORTED = "text/plain";

    @ApiOperation(value = "Please upload a file in text format(Other formats are not supported)",
            notes = "Submit the file which contains German phone numbers. " +
                    " and get the Resource URL in response headers named Location. " +
                    " Use this URL to getlet the valid German phone numbers.")
    @PostMapping(path = "upload-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestPart("file") @ApiParam(value = "Provide File", required = true) MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            return getResponseEntity(FILE_EMPTY_MESSAGE,HttpStatus.BAD_REQUEST);
        }
        log.info("File Content Type {}", file.getContentType());

        if (!file.getContentType().contains(CONTENT_TYPE_SUPPORTED)) {
            return getResponseEntity(FILE_NOT_SUPPORTED_MESSAGE,HttpStatus.BAD_REQUEST);
        }
        String resourcePath = "/{taskId}";
        String fileName = file.getOriginalFilename();

        String existTaskID = phoneParserService.isFileAlreadyProcessed(fileName).orElse(StringUtils.EMPTY);
        log.debug("{} File is already processed and {} is associated with it ", fileName, existTaskID);

        if (StringUtils.isEmpty(existTaskID)) {
            log.info("Single file processing!");
            try (InputStream inputStream = file.getInputStream()) {
                Stream<String> lines = new BufferedReader(new InputStreamReader(inputStream))
                        .lines();

                //Set<String> validPhones = phoneParserService.filterValidPhoneNumbers(lines);
                String taskId = phoneParserService.getTaskId(file.getOriginalFilename(), lines);
                log.debug("{} file got attached with taskId {} ", fileName, taskId);
                return createLocation(resourcePath, taskId);
            }

        } else {
            log.info("This file is already processed : {} and attached taskId is {} .", fileName, existTaskID);
            return createLocation(resourcePath, existTaskID);
        }

    }

    @ApiOperation(value = "Get valid phone number by taskId.",
            notes = "The TaskId which you received in response headers named Location.")
    @GetMapping("/{taskId}")
    public PhoneParserDto getPhoneNumbers(@PathVariable @ApiParam(value = "Please provide taskId", required = true) String taskId) {
        return phoneParserService.getPhoneNumbersByTaskId(taskId);

    }

    @ApiOperation(value = "Get all the tasks.",
            notes = "All available tasks in the system.")
    @GetMapping("/tasks")
    public Set<String> getAllTasks() {
        return phoneParserService.getAllTasks();
    }

    @ApiOperation(value = "Delete task by taskId.")
    @DeleteMapping("/{taskId}")
    public String deleteTask(@PathVariable String taskId) {
        return phoneParserService.deleteTask(taskId);

    }

    private ResponseEntity<String> createLocation(String path, String resource) {
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(path)
                .buildAndExpand(resource).toUri();
        return ResponseEntity.created(location).build();

    }

    private ResponseEntity<String> getResponseEntity(String message, HttpStatus httpStatus) {
        return new ResponseEntity<String>(message, httpStatus);

    }
}