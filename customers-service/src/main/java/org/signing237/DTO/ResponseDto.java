package org.signing237.DTO;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private List<CustomerResponse> customers=new ArrayList<>();
}
