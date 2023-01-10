package org.signing237.Controller;

import org.signing237.Exceptions.NotFoundException;
import org.signing237.Model.Product;
import org.signing237.Service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/papers")
public class ProductController {

    @Autowired
    private MyService myService;

    @PostMapping
    public ResponseEntity<String> saveProduct(@RequestParam("file")MultipartFile file) throws IOException {
        String uploadFile =myService.saveProduct(file);

        return ResponseEntity.ok(uploadFile);
    }

    @GetMapping("/{size}/{page}")
    public ResponseEntity<?> getProducts(
            @PathVariable int size,
            @PathVariable int page
            ){
         return  ResponseEntity.status(HttpStatus.OK).body(myService.getPapers(size, page));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getAProduct (@PathVariable("id") UUID uuid) throws NotFoundException{
       Product product=  myService.getAPaper(uuid);
       return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(product.getType())).body(product.getFile());

    }
}
