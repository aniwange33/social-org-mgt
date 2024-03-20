package com.learn.service.member.dto;

import java.time.LocalDate;

public record OrganizationCommandDto(String name, LocalDate dateEstablished) {
}
