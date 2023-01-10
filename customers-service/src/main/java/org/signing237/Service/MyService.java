package org.signing237.Service;

import org.signing237.DTO.CustomerRequest;
import org.signing237.Model.Customers;

public interface MyService {

    Customers saveCustomer(CustomerRequest request);
}
