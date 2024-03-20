package com.learn.service.member.model;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;


@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString
public class Organization {
    @Id
    private Integer id;
    private String name;
    private LocalDate establishmentDate;

    public Organization(String name, LocalDate establishmentDate) {
        this.name = name;
        this.establishmentDate = establishmentDate;
    }
}

