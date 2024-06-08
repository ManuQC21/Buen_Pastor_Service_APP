package BUEN_PASTOR.Entity;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Setter
@Getter
@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "El ambiente no puede ser nulo")
    @Size(max = 255, message = "El ambiente no puede tener más de 255 caracteres")
    @Column(name = "room", nullable = false, length = 255)
    private String room;

    @NotNull(message = "La ubicación física no puede ser nula")
    @Size(max = 255, message = "La ubicación física no puede tener más de 255 caracteres")
    @Column(name = "physical_location", nullable = false, length = 255)
    private String physicalLocation;

    public Location() {
    }

    public Location(Integer id) {
        this.id = id;
    }

    public Location(Integer id, String room, String physicalLocation) {
        this.id = id;
        this.room = room;
        this.physicalLocation = physicalLocation;
    }
}
