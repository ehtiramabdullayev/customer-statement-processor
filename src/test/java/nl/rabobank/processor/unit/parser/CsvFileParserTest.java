package nl.rabobank.processor.unit.parser;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import nl.rabobank.processor.dto.CustomerStatement;
import nl.rabobank.processor.mapper.CustomerStatementCsvToDTOMapper;
import nl.rabobank.processor.parser.CsvFileParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CsvFileParserTest {

    @InjectMocks
    private CsvFileParser csvFileParser;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        CustomerStatementCsvToDTOMapper customerStatementCsvToDTOMapper = new CustomerStatementCsvToDTOMapper();
        CsvMapper csvMapper = new CsvMapper();
        csvFileParser = new CsvFileParser(customerStatementCsvToDTOMapper, csvMapper);
    }

    @Test
    public void testParseFile_Success() {
        String csvContent = """
                Reference,Account Number,Description,Start Balance,Mutation,End Balance
                183398,NL56RABO0149876948,Clothes from Richard de Vries,33.34,5.55,38.89
                112806,NL27SNSB0917829871,Subscription from Jan Dekker,28.95,-19.44,9.51
                110784,NL93ABNA0585619023,Subscription from Richard Bakker,13.89,-46.18,-32.29""";

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.csv",
                "text/csv",
                csvContent.getBytes()
        );

        List<CustomerStatement> result = csvFileParser.parseFile(mockFile);

        assertFalse(result.isEmpty());
        assertEquals(3, result.size());
    }

    @Test
    public void testParseFile_HasEndBalancedFailedRecord() {
        String csvContent = "Reference,AccountNumber,Description,StartBalance,Mutation,EndBalance\n" +
                "12345,NL12ABCD1234567890,Description,100.0,-20.0,5.0";

        MockMultipartFile mockFile = new MockMultipartFile(
                "file",
                "test.csv",
                "text/csv",
                csvContent.getBytes()
        );

        List<CustomerStatement> result = csvFileParser.parseFile(mockFile);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }
}