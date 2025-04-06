package com.hendricks.springai;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SummaryResultDto {
    private List<String> who;
    private String what;
    private String when;
    private String why;
    private String how;
    private String where;
}
