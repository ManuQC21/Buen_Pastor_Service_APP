package BUEN_PASTOR.Controller;

import BUEN_PASTOR.Service.NotificationService;
import BUEN_PASTOR.utils.GenericResponse;
import BUEN_PASTOR.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Enviar notificación de pago a un docente específico
    @PostMapping("/enviar/{paymentId}")
    public ResponseEntity<GenericResponse<String>> enviarNotificacionPago(@PathVariable int paymentId) {
        GenericResponse<String> response = notificationService.enviarNotificacionPago(paymentId);
        return ResponseEntity.status(response.getRpta() == Global.RPTA_OK ? 200 : 400).body(response);
    }

    // Aceptar notificación de pago por parte del docente
    @PostMapping("/aceptar/{teacherId}/{paymentId}")
    public ResponseEntity<GenericResponse<String>> aceptarNotificacionPago(@PathVariable int teacherId, @PathVariable int paymentId) {
        GenericResponse<String> response = notificationService.aceptarNotificacionPago(teacherId, paymentId);
        return ResponseEntity.status(response.getRpta() == Global.RPTA_OK ? 200 : 400).body(response);
    }
}
