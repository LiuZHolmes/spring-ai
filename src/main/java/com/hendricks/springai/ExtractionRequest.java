package com.hendricks.springai;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ExtractionRequest {
    private String subject;
    private String body;
}