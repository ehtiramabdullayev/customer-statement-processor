package nl.rabobank.processor.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import nl.rabobank.processor.api.model.response.FailedRecordListResponse;
import nl.rabobank.processor.service.CustomerStatementProcessorService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static nl.rabobank.processor.util.Constants.INSIDE_CONTROLLER_ENDPOINT;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/process")
public class ProcessingController {
    private final CustomerStatementProcessorService customerStatementProcessorService;

    public ProcessingController(CustomerStatementProcessorService customerStatementProcessorService) {
        this.customerStatementProcessorService = customerStatementProcessorService;
    }

    @Operation(summary = "Process the customer statement")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bank Statement processed"),
            @ApiResponse(responseCode = "400", description = "Processing of bank statement failed")
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = {"multipart/form-data"})
    public FailedRecordListResponse uploadFile(@RequestPart("file") MultipartFile file) {
        log.info(INSIDE_CONTROLLER_ENDPOINT);
        return customerStatementProcessorService.processCustomerStatement(file);
    }
}
