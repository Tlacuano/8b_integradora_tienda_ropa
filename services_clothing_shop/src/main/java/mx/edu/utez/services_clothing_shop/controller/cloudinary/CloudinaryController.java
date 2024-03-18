package mx.edu.utez.services_clothing_shop.controller.cloudinary;

import mx.edu.utez.services_clothing_shop.service.cloudinary.CloudinaryService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("venta-ropa/api/images")
@CrossOrigin(origins = {"*"})
public class CloudinaryController {

    private final CloudinaryService cloudinaryService;

    public CloudinaryController(CloudinaryService cloudnaryService) {
        this.cloudinaryService = cloudnaryService;
    }


    @PostMapping("/upload-images")
    public ResponseEntity<Object> uploadImages(@RequestParam("files") List<MultipartFile> files) throws Exception {
        try {
            List<String> uploadResult = cloudinaryService.uploadImages(files);
            return ResponseEntity.ok(new CustomResponse<>(uploadResult, "success", false, 200));
        } catch (Exception e) {
            Logger.getLogger("Error while uploading images: " + e.getMessage());
            throw new RuntimeException("image.upload.error");
        }
    }


    @PostMapping("/upload-image")
    public ResponseEntity<Object> uploadImages(@RequestParam("file") MultipartFile  file) throws Exception {
        try{
            String url = cloudinaryService.uploadImage(file);
            return ResponseEntity.ok(new CustomResponse<>(url, "success", false, 200));
        }catch (Exception e){
            Logger.getLogger("Error while uploading image: " + e.getMessage());
            throw new RuntimeException("image.upload.error");
        }
    }
}
