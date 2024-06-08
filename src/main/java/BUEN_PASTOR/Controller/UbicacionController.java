package BUEN_PASTOR.Controller;

import BUEN_PASTOR.Entity.Location;
import BUEN_PASTOR.Service.UbicacionService;
import BUEN_PASTOR.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("ubicaciones")
public class UbicacionController {

    @Autowired
    private UbicacionService ubicacionService;

    @GetMapping("/listar")
    public ResponseEntity<GenericResponse<List<Location>>> listarTodasLasUbicaciones() {
        GenericResponse<List<Location>> response = ubicacionService.listarTodasLasUbicaciones();
        return ResponseEntity.ok(response);
    }
}
