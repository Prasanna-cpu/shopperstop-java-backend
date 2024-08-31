package com.kumar.shopperstop.Service.Image;

import com.kumar.shopperstop.DTO.ImageDTO;
import com.kumar.shopperstop.Exceptions.ImageNotFoundException;
import com.kumar.shopperstop.Exceptions.ProductNotFoundException;
import com.kumar.shopperstop.Model.Image.Image;
import com.kumar.shopperstop.Model.Product.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    Image getImageById(Long id) throws ImageNotFoundException;

    void deleteImageById(Long id);

    List<ImageDTO> saveImage(List<MultipartFile> files, Long productId) throws ProductNotFoundException;

    void updateImage(MultipartFile file,Long id) throws ImageNotFoundException;

}
