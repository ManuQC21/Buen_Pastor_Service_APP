package BUEN_PASTOR.Controller;

import BUEN_PASTOR.Entity.Teacher;
import BUEN_PASTOR.Service.DocenteService;
import BUEN_PASTOR.utils.GenericResponse;
import BUEN_PASTOR.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/docente")
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    @PostMapping("/agregar")
    public ResponseEntity<GenericResponse<Teacher>> agregarDocente(@RequestBody Teacher docente) {
        GenericResponse<Teacher> response = docenteService.agregarDocente(docente);
        return ResponseEntity.status(response.getRpta() == Global.RPTA_OK ? 200 : 400).body(response);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<GenericResponse<Teacher>> editarDocente(@PathVariable Integer id, @RequestBody Teacher docente) {
        docente.setId(id);
        GenericResponse<Teacher> response = docenteService.editarDocente(docente);
        return ResponseEntity.status(response.getRpta() == Global.RPTA_OK ? 200 : 400).body(response);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<GenericResponse<Void>> eliminarDocente(@PathVariable Integer id) {
        GenericResponse<Void> response = docenteService.eliminarDocente(id);
        return ResponseEntity.status(response.getRpta() == Global.RPTA_OK ? 200 : 400).body(response);
    }

    @GetMapping("/listar")
    public ResponseEntity<GenericResponse<List<Teacher>>> listarTodosLosDocentes() {
        GenericResponse<List<Teacher>> response = docenteService.listarTodosLosDocentes();
        return ResponseEntity.status(response.getRpta() == Global.RPTA_OK ? 200 : 400).body(response);
    }

    @GetMapping("/detalles/{id}")
    public ResponseEntity<GenericResponse<Teacher>> obtenerDocentePorId(@PathVariable Integer id) {
        GenericResponse<Teacher> response = docenteService.obtenerDocentePorId(id);
        return ResponseEntity.status(response.getRpta() == Global.RPTA_OK ? 200 : 404).body(response);
    }
}
