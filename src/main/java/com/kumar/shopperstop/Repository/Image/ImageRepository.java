package com.kumar.shopperstop.Repository.Image;

import com.kumar.shopperstop.Model.Image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByProductId(Long id);

}
