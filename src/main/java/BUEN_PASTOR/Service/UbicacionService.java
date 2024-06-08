package BUEN_PASTOR.Service;

import BUEN_PASTOR.Entity.Location;
import BUEN_PASTOR.Repository.UbicacionRepository;
import BUEN_PASTOR.utils.GenericResponse;
import BUEN_PASTOR.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UbicacionService {

    @Autowired
    private UbicacionRepository ubicacionRepository;

    // Agregar una nueva ubicación
    public GenericResponse<Location> agregarUbicacion(Location ubicacion) {
        try {
            Location savedLocation = ubicacionRepository.save(ubicacion);
            return new GenericResponse<>(Global.TIPO_CORRECTO, Global.RPTA_OK, "Ubicación agregada correctamente", savedLocation);
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Error al agregar la ubicación", null);
        }
    }

    // Editar una ubicación existente
    public GenericResponse<Location> editarUbicacion(Location ubicacion) {
        if (!ubicacionRepository.existsById(ubicacion.getId())) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Ubicación no encontrada", null);
        }
        try {
            Location updatedLocation = ubicacionRepository.save(ubicacion);
            return new GenericResponse<>(Global.TIPO_CORRECTO, Global.RPTA_OK, "Ubicación actualizada con éxito", updatedLocation);
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Error al actualizar la ubicación", null);
        }
    }

    // Eliminar una ubicación
    public GenericResponse<Void> eliminarUbicacion(Integer id) {
        if (!ubicacionRepository.existsById(id)) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Ubicación no encontrada", null);
        }
        try {
            ubicacionRepository.deleteById(id);
            return new GenericResponse<>(Global.TIPO_CORRECTO, Global.RPTA_OK, "Ubicación eliminada correctamente", null);
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Error al eliminar la ubicación", null);
        }
    }

    // Listar todas las ubicaciones
    public GenericResponse<List<Location>> listarTodasLasUbicaciones() {
        try {
            List<Location> ubicaciones = new ArrayList<>();
            ubicacionRepository.findAll().forEach(ubicaciones::add);
            return new GenericResponse<>(Global.TIPO_DATA, Global.RPTA_OK, Global.OPERACION_CORRECTA, ubicaciones);
        } catch (Exception e) {
            return new GenericResponse<>(Global.TIPO_RESULT, Global.RPTA_ERROR, Global.OPERACION_ERRONEA, null);
        }
    }

    // Obtener información de una ubicación específica
    public GenericResponse<Location> obtenerInformacionUbicacion(Integer id) {
        Optional<Location> ubicacion = ubicacionRepository.findById(id);
        if (ubicacion.isPresent()) {
            return new GenericResponse<>(Global.TIPO_CORRECTO, Global.RPTA_OK, "Información de la ubicación obtenida", ubicacion.get());
        } else {
            return new GenericResponse<>(Global.TIPO_ERROR, Global.RPTA_ERROR, "Ubicación no encontrada", null);
        }
    }
}
