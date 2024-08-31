package com.kumar.shopperstop.Service.Image;


import com.kumar.shopperstop.DTO.ImageDTO;
import com.kumar.shopperstop.Exceptions.ImageNotFoundException;
import com.kumar.shopperstop.Exceptions.ProductNotFoundException;
import com.kumar.shopperstop.Model.Image.Image;
import com.kumar.shopperstop.Model.Product.Product;
import com.kumar.shopperstop.Repository.Image.ImageRepository;
import com.kumar.shopperstop.Service.Product.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ImageServiceImplementation implements ImageService{
    /**
     * @param id
     * @return
     */


    private final ImageRepository imageRepository;

    private final ProductService productService;

    @Override
    public Image getImageById(Long id) throws ImageNotFoundException {
        Image image=imageRepository.findById(id).orElseThrow(()->new ImageNotFoundException("Given image not found"));

        return image;
    }

    /**
     * @param id
     */
    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository::delete,()->{
                    new ImageNotFoundException("Given image not found");
                });
    }

    /**
     * @param files
     * @param productId
     * @return
     */
    @Override
    public List<ImageDTO> saveImage(List<MultipartFile> files, Long productId) throws ProductNotFoundException {

        Product product=productService.getProductById(productId);
        List<ImageDTO> savedImageDTO=new ArrayList<>();
        for(MultipartFile file:files){
            try{
                Image image=new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(image.getProduct());


                String buildDownloadUrl="/api/v1/images/image/download/";
                String downloadUrl=buildDownloadUrl+"/api/v1/images/image/download/"+image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage=imageRepository.save(image);
                savedImage.setDownloadUrl("/api/v1/images/image/download/"+savedImage.getId());
                imageRepository.save(savedImage);

                ImageDTO imageDTO=new ImageDTO();
                imageDTO.setImageId(savedImage.getId());
                imageDTO.setImageName(savedImage.getFileName());
                imageDTO.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDTO.add(imageDTO);


            }
            catch(IOException|SQLException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDTO;

    }

    /**
     * @param file
     * @param id
     */
    @Override
    public void updateImage(MultipartFile file, Long id) throws ImageNotFoundException{

        Image image=getImageById(id);

        try{
            image.setFileName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        }
        catch(IOException | SQLException e){
            throw new RuntimeException(e);
        }



    }
}
