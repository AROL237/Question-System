package org.signing237.Service;

import org.signing237.DTO.CustomerRequest;
import org.signing237.DTO.ResponseDto;
import org.signing237.Model.Customers;

public interface MyService {

    Customers saveCustomer(CustomerRequest request);

    ResponseDto getCustomers(int size, int page) throws Exception;
}
