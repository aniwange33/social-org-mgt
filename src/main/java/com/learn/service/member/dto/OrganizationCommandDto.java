package com.learn.service.member.dto;

import io.micrometer.common.util.StringUtils;

import java.time.LocalDate;

public record OrganizationCommandDto(String name, LocalDate dateEstablished) {
    public OrganizationCommandDto {
        if (StringUtils.isBlank(name) || dateEstablished == null) {
            throw new IllegalArgumentException("""
                    Some required fields are missing or invalid. Please ensure that both the Name 
                    and the Date Established are filled out correctly. Thank you!
                    """);
        }

    }
}
