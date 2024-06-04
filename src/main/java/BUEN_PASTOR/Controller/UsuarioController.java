package BUEN_PASTOR.Controller;

import BUEN_PASTOR.Entity.usuario;
import BUEN_PASTOR.Service.UsuarioService;
import BUEN_PASTOR.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/registro")
    public GenericResponse<usuario> register(@RequestBody usuario usuario) {
        return service.register(usuario);
    }

    @PostMapping("/login")
    public GenericResponse<usuario> login(@RequestParam("correo") String correo, @RequestParam("clave") String clave) {
        return service.login(correo, clave);
    }
}
