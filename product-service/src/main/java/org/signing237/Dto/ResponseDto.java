package org.signing237.Dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Builder
@Data
public class ResponseDto {
    private Map<String,Object> papers;
}
