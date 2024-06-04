package BUEN_PASTOR.Service;

import BUEN_PASTOR.Entity.Ubicacion;
import BUEN_PASTOR.Repository.UbicacionRepository;
import BUEN_PASTOR.utils.GenericResponse;
import BUEN_PASTOR.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UbicacionService {

    @Autowired
    private UbicacionRepository ubicacionRepository;

    public GenericResponse<List<Ubicacion>> listarTodasLasUbicaciones() {
        try {
            List<Ubicacion> ubicaciones = new ArrayList<>();
            ubicacionRepository.findAll().forEach(ubicaciones::add);
            return new GenericResponse<>(Global.TIPO_DATA, Global.RPTA_OK, Global.OPERACION_CORRECTA, ubicaciones);
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_RESULT, Global.RPTA_ERROR, Global.OPERACION_ERRONEA, null);
        }
    }
}
