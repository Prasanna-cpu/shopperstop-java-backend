package com.kumar.shopperstop.Controller;


import com.kumar.shopperstop.DTO.ImageDTO;
import com.kumar.shopperstop.Exceptions.ImageNotFoundException;
import com.kumar.shopperstop.Exceptions.ProductNotFoundException;
import com.kumar.shopperstop.Model.Image.Image;
import com.kumar.shopperstop.Repository.Image.ImageRepository;
import com.kumar.shopperstop.Response.ApiResponse;
import com.kumar.shopperstop.Service.Image.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final ImageRepository imageRepository;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {

        try{
            List<ImageDTO> imageDTOS=imageService.saveImage(files,productId);

            return ResponseEntity.ok(
                    new ApiResponse("Uploaded successfully",imageDTOS)
            );
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Upload failed",e.getMessage()));
        }

    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<? extends Resource> downloadImage(@PathVariable Long imageId) throws SQLException, ImageNotFoundException {
        Image image=imageService.getImageById(imageId);
        try{
            ByteArrayResource resource=new ByteArrayResource(image.getImage().getBytes(1,(int) image.getImage().length()));
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+image.getFileName()+"\"")
                    .body(resource);
        }
        catch(Exception e){
            ByteArrayResource errorResource = new ByteArrayResource(new byte[0]); // Empty resource
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"error.txt\"")
                    .body(errorResource);
        }
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable MultipartFile file, @PathVariable Long imageId) throws ImageNotFoundException {

        Image image=imageService.getImageById(imageId);

        try{
            imageService.updateImage(file,imageId);
            return ResponseEntity.ok(
                    new ApiResponse("Updated successfully",image)
            );
        }
        catch(Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Update failed",e.getMessage()));
        }


    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) throws ImageNotFoundException {
        Image image=imageService.getImageById(imageId);
        try{
            imageRepository.deleteById(imageId);

            return ResponseEntity.ok(
                    new ApiResponse("Deleted successfully",image)
            );
        }
        catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Deletion failed",e.getMessage()));
        }

    }







}
