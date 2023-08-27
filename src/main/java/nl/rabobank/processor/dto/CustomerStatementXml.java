package nl.rabobank.processor.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.*;

import java.math.BigDecimal;

@Data
public class CustomerStatementXml {
    @JacksonXmlProperty(isAttribute = true)
    private int reference;

    @JacksonXmlProperty
    private String accountNumber;

    @JacksonXmlProperty
    private String description;

    @JacksonXmlProperty
    private BigDecimal startBalance;

    @JacksonXmlProperty
    private BigDecimal mutation;

    @JacksonXmlProperty
    private BigDecimal endBalance;

}
