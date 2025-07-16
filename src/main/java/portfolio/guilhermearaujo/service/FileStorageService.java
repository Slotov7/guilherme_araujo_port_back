package portfolio.guilhermearaujo.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class FileStorageService {

    private final Cloudinary cloudinary;

    public FileStorageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

        public String uploadImage(MultipartFile file) throws IOException {
        Map<?, ?> result = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        return (String) result.get("secure_url");
    }

//    public Map<String, Object> uploadCv(MultipartFile file) throws IOException {
//        return cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
//                "resource_type", "raw",  // Informa que é um ficheiro genérico, não uma imagem a ser otimizada.
//                "public_id", "portfolio_cv.pdf", // Dá um NOME/ID FIXO para o ficheiro no Cloudinary.
//                "overwrite", true          // Garante que o ficheiro antigo com este nome seja substituído.
//        ));
//    }
}
