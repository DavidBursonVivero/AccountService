package com.microcompany.accountsservice.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    /*@DateTimeFormat //(pattern = "yyyy-mm-dd")
    @Pattern(regexp = "[0-9]{4}-[0-9]{2}-[0-9]{2}")*/
    Date openingDate;

    @Min(1)
    @NotNull
    private int balance;

    @NotNull
    private Long ownerId;

    @Transient
    Customer owner;


}
