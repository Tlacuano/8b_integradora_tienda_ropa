package mx.edu.utez.services_clothing_shop.service.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public List<String> uploadImages(List<MultipartFile> files) {
        try {
            List<String> uploadResults = new ArrayList<>();
            for (MultipartFile file : files) {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                uploadResults.add(uploadResult.get("url").toString());
            }
            return uploadResults;
        } catch (IOException e) {
            throw new CustomException("image.upload.error");
        }
    }

    public String uploadImage(MultipartFile file) {
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            throw new CustomException("image.upload.error");
        }
    }
}
