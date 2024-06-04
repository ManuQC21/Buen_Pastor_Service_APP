package BUEN_PASTOR.Service;


import BUEN_PASTOR.Entity.Foto;
import BUEN_PASTOR.Repository.FotoRepository;
import BUEN_PASTOR.utils.GenericResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import static BUEN_PASTOR.utils.Global.*;


@Service
@Transactional
public class FotoService {
    private FotoRepository repo;
    private AlmacenamientoFotoService storageService;

    public FotoService(FotoRepository repo, AlmacenamientoFotoService storageService) {
        this.repo = repo;
        this.storageService = storageService;
    }

    public GenericResponse<Iterable<Foto>> list() {
        return new GenericResponse<Iterable<Foto>>(TIPO_RESULT, RPTA_OK, OPERACION_CORRECTA, repo.list());
    }


    public GenericResponse find(Long aLong) {
        return null;
    }


    public GenericResponse save(Foto obj) {
        String fileName = (repo.findById(obj.getId())).orElse(new Foto()).getFileName();

        String originalFilename = obj.getFile().getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        fileName = storageService.storeFile(obj.getFile(), fileName);

        obj.setFileName(fileName);
        obj.setExtension(extension);

        return new GenericResponse(TIPO_DATA, RPTA_OK,OPERACION_CORRECTA,repo.save(obj));
    }

    public ResponseEntity<Resource> download(String completefileName, HttpServletRequest request) {
        Resource resource = storageService.loadResource(completefileName);
        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    public ResponseEntity<Resource> downloadByFileName(String fileName, HttpServletRequest request) {
        Foto doc = repo.findByFileName(fileName).orElse(new Foto());
        return download(doc.getCompleteFileName(), request);
    }


    public GenericResponse delete(Long aLong) {
        return null;
    }

    public HashMap<String, Object> validate(Foto obj) {
        return null;
    }
}