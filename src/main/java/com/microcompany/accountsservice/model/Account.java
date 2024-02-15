package com.microcompany.accountsservice.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotBlank
    private String type;

    @DateTimeFormat
    @Pattern(regexp = "[0-9]{3}-[0-9]{3}-[0-9]{4}")
    Date openingDate;

    @Min(1)
    @NotBlank
    private int balance;

    @NotBlank
    private Long ownerId;

    @Transient
    Customer owner;


}
