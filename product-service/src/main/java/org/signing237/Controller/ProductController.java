package org.signing237.Controller;

import org.signing237.Exceptions.NotFoundException;
import org.signing237.Model.Products;
import org.signing237.Service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public ResponseEntity<?> getProducts(
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int page
            ){
         return  ResponseEntity.status(HttpStatus.OK).body(myService.getPapers(size, page));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getAProduct (@PathVariable("id") UUID uuid) throws NotFoundException{
       Products products =  myService.getAPaper(uuid);
       return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(products.getType())).body(products.getFile());

    }

    @PostMapping("{uuid}/solution")
    public ResponseEntity<?> addSolutionToPaper(
            @RequestParam("file") MultipartFile file,
            @PathVariable UUID uuid
            ){
       String result = myService.addSolutionToPaper(uuid,file);

       return ResponseEntity.ok(result);
    }
}
