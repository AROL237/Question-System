package org.signing237.ServiceImp;

import lombok.extern.slf4j.Slf4j;
import org.signing237.Dto.ProductResponse;
import org.signing237.Dto.ResponseDto;
import org.signing237.Exceptions.NotFoundException;
import org.signing237.Model.Products;
import org.signing237.Model.Solutions;
import org.signing237.Repository.ProductRepository;
import org.signing237.Service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MyServiceImp implements MyService {

    @Autowired
    private ProductRepository productRepository;
    @Override
    public String saveProduct(MultipartFile file) throws IOException {
        Products products = Products.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .file(file.getBytes())
                .size(file.getSize())
                .build();
        try {
            Products savedProducts = productRepository.save(products);
            return "file uploaded successfully:"+ savedProducts.getName();
        }
        catch (Exception e){
            return  "failed\n message: "+e.getMessage();
        }
    }

    @Override
    public ResponseDto getPapers(int size, int page) {
        try {
            Pageable pageable = PageRequest.of(page,size);
            Page<Products> result = productRepository.findAll(pageable);

                List<ProductResponse> responses =   new ArrayList<>();
            if (!result.isEmpty()) {
                responses = result.stream().map(product -> ProductResponse.builder()
                        .uuid(product.getUuid())
                        .name(product.getName())
                        .type(product.getType())
                        .size(product.getSize())
                        .build()
                ).collect(Collectors.toList());
            }
            Map<String ,Object> response= new HashMap<>();
            response.put("content",responses);
            response.put("total elements",result.getTotalElements());
            response.put("total pages",result.getTotalPages());
            response.put("number of elements",result.getNumberOfElements());
            response.put("size",result.getSize());
            response.put("pageable",result.getPageable());

                return ResponseDto.builder().papers(response).build();
        }
        catch (Exception ex){
            log.error("error: {}",ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public Products getAPaper(UUID uuid) throws NotFoundException {
        if ( productRepository.findById(uuid).isPresent())
        return productRepository.findById(uuid).get();
        else
            throw new NotFoundException("file not found");
    }

    @Override
    public String addSolutionToPaper(UUID uuid, MultipartFile file) {
            String message= null;
        try {
            Optional<Products> product = productRepository.findById(uuid);
            if (product.isPresent()) {
                log.info("Product: {} found 👍.", uuid);
                Solutions solution = Solutions.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .size(file.getSize())
                        .file(file.getBytes())
                        .build();
                product.get().setSolutionsId(solution);
                productRepository.save(product.get());
                message = "success";
            }
        } catch (IOException e) {
            log.error("process failed : {}",e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
        return message;
    }
}
