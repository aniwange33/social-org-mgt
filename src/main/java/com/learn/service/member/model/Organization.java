package com.learn.service.member.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDate;

public record Organization (@Id Integer id, String name, LocalDate establishmentDate){

}

