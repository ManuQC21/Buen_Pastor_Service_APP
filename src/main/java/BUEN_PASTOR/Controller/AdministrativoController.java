package BUEN_PASTOR.Controller;

import BUEN_PASTOR.Entity.Administrative;
import BUEN_PASTOR.Service.AdministrativoService;
import BUEN_PASTOR.utils.GenericResponse;
import BUEN_PASTOR.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/administrativo")
public class AdministrativoController {

    @Autowired
    private AdministrativoService administrativoService;

    @PostMapping("/agregar")
    public ResponseEntity<GenericResponse<Administrative>> agregarAdministrativo(@RequestBody Administrative administrativo) {
        GenericResponse<Administrative> response = administrativoService.agregarAdministrativo(administrativo);
        return ResponseEntity.status(response.getRpta() == Global.RPTA_OK ? 200 : 400).body(response);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<GenericResponse<Administrative>> editarAdministrativo(@PathVariable Integer id, @RequestBody Administrative administrativo) {
        administrativo.setId(id);
        GenericResponse<Administrative> response = administrativoService.editarAdministrativo(administrativo);
        return ResponseEntity.status(response.getRpta() == Global.RPTA_OK ? 200 : 400).body(response);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<GenericResponse<Void>> eliminarAdministrativo(@PathVariable Integer id) {
        GenericResponse<Void> response = administrativoService.eliminarAdministrativo(id);
        return ResponseEntity.status(response.getRpta() == Global.RPTA_OK ? 200 : 400).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<GenericResponse<List<Administrative>>> listarTodosLosAdministrativos() {
        GenericResponse<List<Administrative>> response = administrativoService.listarTodosLosAdministrativos();
        return ResponseEntity.status(response.getRpta() == Global.RPTA_OK ? 200 : 400).body(response);
    }
}
