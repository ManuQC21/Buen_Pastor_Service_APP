package BUEN_PASTOR.Controller;

import BUEN_PASTOR.Service.EmpleadoService;
import BUEN_PASTOR.utils.GenericResponse;
import BUEN_PASTOR.Entity.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping("/listar")
    public ResponseEntity<GenericResponse<List<Empleado>>> listarTodosLosEmpleados() {
        GenericResponse<List<Empleado>> response = empleadoService.listarTodosLosEmpleados();
        return ResponseEntity.ok(response);
    }
}
