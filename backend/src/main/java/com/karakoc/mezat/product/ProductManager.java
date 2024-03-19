package com.karakoc.mezat.product;


import com.karakoc.mezat.exceptions.general.BadRequestException;
import com.karakoc.mezat.exceptions.general.ForbiddenException;
import com.karakoc.mezat.exceptions.general.NotfoundException;
import com.karakoc.mezat.user.User;
import com.karakoc.mezat.user.UserDTO;
import com.karakoc.mezat.user.UserRepository;
import com.karakoc.mezat.user.roles.UserRole;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.karakoc.mezat.product.Product.productDTOS;
import static com.karakoc.mezat.product.Product.productToDTO;
import static com.karakoc.mezat.user.User.onlyAdminAndUserIsPresentValidation;

@Service
@AllArgsConstructor
public class ProductManager implements ProductService{
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductDTO createProduct(CreateProductRequest request) {
        Optional<User> admin = userRepository.findUserByToken(request.getAdminToken());
        onlyAdminAndUserIsPresentValidation(admin);


        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setProductTitle(request.getProductTitle());
        product.setPrice(request.getPrice());

        try {
            if (request.getMultipartFile().isEmpty()) {
                throw new BadRequestException("Empty file.");
            }

            String originalFilename = request.getMultipartFile().getOriginalFilename();
            Path destination = Paths.get("rootDir").resolve(originalFilename).normalize().toAbsolutePath();

            // Dosya isminde benzersizlik kontrolü
            int fileNumber = 0;
            String fileNameWithoutExtension = FilenameUtils.removeExtension(originalFilename);
            String fileExtension = FilenameUtils.getExtension(originalFilename);
            while (Files.exists(destination)) {
                fileNumber++;
                String numberedFileName = fileNameWithoutExtension + "_" + fileNumber + "." + fileExtension;
                destination = Paths.get("rootDir").resolve(numberedFileName).normalize().toAbsolutePath();
            }

            Files.copy(request.getMultipartFile().getInputStream(), destination);
            product.setPhotoPath("C:/Users/emirhan karakoc/Downloads/mezat/mezat/rootDir/" + destination.getFileName().toString());
        } catch (IOException e) {
            throw new BadRequestException("Store exception");
        }

        Product product1 = productRepository.save(product);
        return productToDTO(product1);
    }

    public ProductDTO getProductById(String productId){
        Optional<Product> product = productRepository.findById(productId);
        ProductDTO dto;
        if (product.isPresent()){
            dto = productToDTO(product.get());
            return dto;
        }
        else {
            throw new NotfoundException("Product not found.");
        }
    }

    public List<ProductDTO> getAllProducts() {
        return productDTOS(productRepository.findAll());
    }


    public ProductDTO deleteProductById(String id, String adminToken) {
        Optional<User> admin = userRepository.findUserByToken(adminToken);
        onlyAdminAndUserIsPresentValidation(admin);

        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()){
            productRepository.delete(product.get());
            return productToDTO(product.get());
        }
        else {
            throw new NotfoundException("Product not found.");
        }
    }
}
