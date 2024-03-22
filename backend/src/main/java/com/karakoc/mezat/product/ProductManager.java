package com.karakoc.mezat.product;


import com.karakoc.mezat.cloudinary.entity.Image;
import com.karakoc.mezat.cloudinary.repository.ImageRepository;
import com.karakoc.mezat.cloudinary.service.CloudinaryService;
import com.karakoc.mezat.cloudinary.service.ImageService;
import com.karakoc.mezat.exceptions.general.BadRequestException;
import com.karakoc.mezat.exceptions.general.ForbiddenException;
import com.karakoc.mezat.exceptions.general.NotfoundException;
import com.karakoc.mezat.user.User;
import com.karakoc.mezat.user.UserDTO;
import com.karakoc.mezat.user.UserRepository;
import com.karakoc.mezat.user.roles.UserRole;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.karakoc.mezat.product.Product.productDTOS;
import static com.karakoc.mezat.product.Product.productToDTO;
import static com.karakoc.mezat.user.User.onlyAdminAndUserIsPresentValidation;import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Service
@AllArgsConstructor
@Slf4j
public class ProductManager implements ProductService{
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService; // CloudinaryService'e bağlantı
    private final ImageRepository imageRepository;


    public ProductDTO createProduct(CreateProductRequest request) {
        Optional<User> admin = userRepository.findUserByToken(request.getAdminToken());
        onlyAdminAndUserIsPresentValidation(admin);

        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setProductTitle(request.getProductTitle().toUpperCase());
        product.setPrice(request.getPrice());

        try {
            if (request.getMultipartFile().isEmpty()) {
                throw new BadRequestException("Empty file.");
            }

            // Cloudinary'ye yükle
            Map uploadResult = cloudinaryService.upload(request.getMultipartFile());
            String photoPath = (String) uploadResult.get("url"); // Cloudinary'den gelen URL'yi al
            product.setImageCloudId((String) uploadResult.get("public_id"));
            product.setImageName((String) uploadResult.get("original_filename"));
            product.setPhotoPath(photoPath); // Ürünün fotoğraf yolunu ayarla
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }

        Product savedProduct = productRepository.save(product);
        return productToDTO(savedProduct);
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

    public Page<ProductDTO> getAllProductsPageable(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);
        List<ProductDTO> productDTOList = productDTOS(productPage.getContent());
        return new PageImpl<>(productDTOList, pageable, productPage.getTotalElements());
    }
    public List<ProductDTO> getAll(){
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
    public void deleteAll(){
        productRepository.deleteAll();
    }
}
