package org.signing237.ServiceImp;

import org.signing237.Exceptions.NotFoundException;
import org.signing237.Model.Product;
import org.signing237.Repository.ProductRepository;
import org.signing237.Service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class MyServiceImp implements MyService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public String saveProduct(MultipartFile file) throws IOException {
        Product product = Product.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .file(file.getBytes())
                .size(file.getSize())
                .build();
        try {
            Product savedProduct = productRepository.save(product);
            return "file uploaded successfully:"+savedProduct.getName();
        }
        catch (Exception e){
            return  "failed\n message: "+e.getMessage();
        }
    }

    @Override
    public Page getPapers(int size, int page) {
         return productRepository.findAll(PageRequest.of(page,size));

    }

    @Override
    public Product getAPaper(UUID uuid) throws NotFoundException {
        if ( productRepository.findById(uuid).isPresent())
        return productRepository.findById(uuid).get();
        else
            throw new NotFoundException("file not found");
    }
}
