package com.microcompany.accountsservice.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")

@XmlRootElement
@Schema(name = "Tipo de cuenta", description = "Representa el tipo de cuenta.")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "cuenta id", description = "Identificador de la cuenta basado en números enteros positivos", example = "2")
    private Long id;

    @Column
    @NotBlank
    @Schema(name = "Tipo de cuenta", description = "Representa el tipo de cuenta del cliente", example = "Cuenta ahorro")
    private String type;

    @Column
    @Schema(name = "Fecha de apertura", description = "Fecha de apertura de la cuenta", example = "2024-02-16")
    private Date openingDate;

    @Column
    @Min(1)
    @NotNull
    @Schema(name = "Saldo", description = "Saldo de la cuenta", minimum = "1")
    private int balance;

    @Column
    @NotNull
    @Schema(name = "ID del propietario", description = "ID del propietario de la cuenta")
    private Long ownerId;

    @Transient
    private Customer owner;
}
