package org.signing237.Service;

import org.signing237.Exceptions.NotFoundException;
import org.signing237.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface MyService {

    String saveProduct(MultipartFile file) throws IOException;

    Page getPapers(int size, int page);

    Product getAPaper(UUID uuid) throws  NotFoundException;
}
