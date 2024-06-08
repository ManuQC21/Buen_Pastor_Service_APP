package BUEN_PASTOR.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "equipment")
public class Equipment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "El tipo de equipo no puede ser nulo")
    @Size(max = 255, message = "El tipo de equipo no puede tener más de 255 caracteres")
    @Column(name = "equipment_type", nullable = false, length = 255)
    private String equipmentType;

    @Size(max = 255, message = "El código de barras no puede tener más de 255 caracteres")
    @Column(name = "barcode", length = 255)
    private String barcode;

    @Size(max = 255, message = "El código patrimonial no puede tener más de 255 caracteres")
    @Column(name = "asset_code", length = 255)
    private String assetCode;

    @NotNull(message = "La descripción no puede ser nula")
    @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @NotNull(message = "El estado no puede ser nulo")
    @Size(max = 255, message = "El estado no puede tener más de 255 caracteres")
    @Column(name = "status", nullable = false, length = 255)
    private String status;

    @NotNull(message = "La fecha de compra no puede ser nula")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @NotNull(message = "La marca no puede ser nula")
    @Size(max = 255, message = "La marca no puede tener más de 255 caracteres")
    @Column(name = "brand", nullable = false, length = 255)
    private String brand;

    @NotNull(message = "El modelo no puede ser nulo")
    @Size(max = 255, message = "El modelo no puede tener más de 255 caracteres")
    @Column(name = "model", nullable = false, length = 255)
    private String model;

    @NotNull(message = "El nombre del equipo no puede ser nulo")
    @Size(max = 255, message = "El nombre del equipo no puede tener más de 255 caracteres")
    @Column(name = "equipment_name", nullable = false, length = 255)
    private String equipmentName;

    @NotNull(message = "El número de orden no puede ser nulo")
    @Size(max = 255, message = "El número de orden no puede tener más de 255 caracteres")
    @Column(name = "order_number", nullable = false, length = 255)
    private String orderNumber;

    @NotNull(message = "El número de serie no puede ser nulo")
    @Size(max = 255, message = "El número de serie no puede tener más de 255 caracteres")
    @Column(name = "serial", nullable = false, length = 255)
    private String serial;

    @NotNull(message = "El responsable no puede ser nulo")
    @ManyToOne
    @JoinColumn(name = "responsible_id", nullable = false)
    private Teacher responsible;

    @NotNull(message = "La ubicación no puede ser nula")
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    public Equipment(int id) {
        this.id = id;
    }

    public Equipment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public @NotNull(message = "El tipo de equipo no puede ser nulo") @Size(max = 255, message = "El tipo de equipo no puede tener más de 255 caracteres") String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(@NotNull(message = "El tipo de equipo no puede ser nulo") @Size(max = 255, message = "El tipo de equipo no puede tener más de 255 caracteres") String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public @Size(max = 255, message = "El código de barras no puede tener más de 255 caracteres") String getBarcode() {
        return barcode;
    }

    public void setBarcode(@Size(max = 255, message = "El código de barras no puede tener más de 255 caracteres") String barcode) {
        this.barcode = barcode;
    }

    public @Size(max = 255, message = "El código patrimonial no puede tener más de 255 caracteres") String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(@Size(max = 255, message = "El código patrimonial no puede tener más de 255 caracteres") String assetCode) {
        this.assetCode = assetCode;
    }

    public @NotNull(message = "La descripción no puede ser nula") @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres") String getDescription() {
        return description;
    }

    public void setDescription(@NotNull(message = "La descripción no puede ser nula") @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres") String description) {
        this.description = description;
    }

    public @NotNull(message = "El estado no puede ser nulo") @Size(max = 255, message = "El estado no puede tener más de 255 caracteres") String getStatus() {
        return status;
    }

    public void setStatus(@NotNull(message = "El estado no puede ser nulo") @Size(max = 255, message = "El estado no puede tener más de 255 caracteres") String status) {
        this.status = status;
    }

    public @NotNull(message = "La fecha de compra no puede ser nula") LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(@NotNull(message = "La fecha de compra no puede ser nula") LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public @NotNull(message = "La marca no puede ser nula") @Size(max = 255, message = "La marca no puede tener más de 255 caracteres") String getBrand() {
        return brand;
    }

    public void setBrand(@NotNull(message = "La marca no puede ser nula") @Size(max = 255, message = "La marca no puede tener más de 255 caracteres") String brand) {
        this.brand = brand;
    }

    public @NotNull(message = "El modelo no puede ser nulo") @Size(max = 255, message = "El modelo no puede tener más de 255 caracteres") String getModel() {
        return model;
    }

    public void setModel(@NotNull(message = "El modelo no puede ser nulo") @Size(max = 255, message = "El modelo no puede tener más de 255 caracteres") String model) {
        this.model = model;
    }

    public @NotNull(message = "El nombre del equipo no puede ser nulo") @Size(max = 255, message = "El nombre del equipo no puede tener más de 255 caracteres") String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(@NotNull(message = "El nombre del equipo no puede ser nulo") @Size(max = 255, message = "El nombre del equipo no puede tener más de 255 caracteres") String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public @NotNull(message = "El número de orden no puede ser nulo") @Size(max = 255, message = "El número de orden no puede tener más de 255 caracteres") String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(@NotNull(message = "El número de orden no puede ser nulo") @Size(max = 255, message = "El número de orden no puede tener más de 255 caracteres") String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public @NotNull(message = "El número de serie no puede ser nulo") @Size(max = 255, message = "El número de serie no puede tener más de 255 caracteres") String getSerial() {
        return serial;
    }

    public void setSerial(@NotNull(message = "El número de serie no puede ser nulo") @Size(max = 255, message = "El número de serie no puede tener más de 255 caracteres") String serial) {
        this.serial = serial;
    }

    public @NotNull(message = "El responsable no puede ser nulo") Teacher getResponsible() {
        return responsible;
    }

    public void setResponsible(@NotNull(message = "El responsable no puede ser nulo") Teacher responsible) {
        this.responsible = responsible;
    }

    public @NotNull(message = "La ubicación no puede ser nula") Location getLocation() {
        return location;
    }

    public void setLocation(@NotNull(message = "La ubicación no puede ser nula") Location location) {
        this.location = location;
    }
}
