package com.lms.shoppingcart.image;

import com.lms.shoppingcart.dto.ImageDto;
import com.lms.shoppingcart.exception.ImageNotFoundException;
import com.lms.shoppingcart.product.Product;
import com.lms.shoppingcart.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final ProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()-> new ImageNotFoundException("image not found of Id "+ id ));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository ::delete,
                ()-> {throw new ImageNotFoundException("image Not found Of Id "+id);
        });
    }

    @Override
    public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);
        List<ImageDto> imageDtos = new ArrayList<>();
        for (MultipartFile file : files) {
            try{
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);
                String buildDownloadUrl = "/api/images/image/download/" ;
                String downloadUrl = buildDownloadUrl +image.getImageId();
                image.setDownloadUrl(downloadUrl);

               Image saveImage = imageRepository.save(image);

               saveImage.setDownloadUrl(buildDownloadUrl +saveImage.getImageId());
               imageRepository.save(saveImage);

               ImageDto imageDto = new ImageDto();

               imageDto.setImageId(saveImage.getImageId());
               imageDto.setImageName(saveImage.getFileName());
               imageDto.setDownloadUrl(saveImage.getDownloadUrl());

               imageDtos.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return imageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {

        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
