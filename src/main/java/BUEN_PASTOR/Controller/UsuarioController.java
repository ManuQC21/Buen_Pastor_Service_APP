package BUEN_PASTOR.Controller;

import BUEN_PASTOR.Entity.Member;
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
    public GenericResponse<Member> register(@RequestBody Member member) {
        return service.register(member);
    }

    @PostMapping("/login")
    public GenericResponse<Member> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        return service.login(email, password);
    }
}
