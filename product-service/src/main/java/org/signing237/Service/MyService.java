package org.signing237.Service;

import org.signing237.Dto.ResponseDto;
import org.signing237.Exceptions.NotFoundException;
import org.signing237.Model.Products;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface MyService {

    String saveProduct(MultipartFile file) throws IOException;

    ResponseDto getPapers(int size, int page);

    Products getAPaper(UUID uuid) throws  NotFoundException;

    String addSolutionToPaper(UUID uuid, MultipartFile file);
}
