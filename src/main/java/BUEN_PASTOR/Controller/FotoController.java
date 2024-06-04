package BUEN_PASTOR.Controller;


import BUEN_PASTOR.Entity.Foto;
import BUEN_PASTOR.Service.FotoService;
import BUEN_PASTOR.utils.GenericResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("fotos")
public class FotoController {
    private FotoService service;

    public FotoController(FotoService service) {
        this.service = service;
    }

    @GetMapping
    public GenericResponse list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public GenericResponse find(@PathVariable Long id) {
        return null;
    }

    @GetMapping("/descargar/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName, HttpServletRequest request) {
        return service.downloadByFileName(fileName, request);
    }

    @PostMapping
    public GenericResponse save(@ModelAttribute Foto obj) {
        return service.save(obj);
    }

    public GenericResponse update(Long aLong, Foto obj) {
        return null;
    }

    public GenericResponse delete(Long aLong) {
        return null;
    }
}