package com.learn.service.member.dto;

import io.micrometer.common.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record MemberCommandDto(
        String lastName,
        String firstName,
        String nickName,
        String address,
        String idNumber,
        String currentTown,
        String homeTown,
        LocalDate birthOfBirth,
        LocalDateTime createdOn,
        LocalDateTime updatedOn,
        Integer orgId
) {

    public MemberCommandDto {
        if (StringUtils.isBlank(lastName) || StringUtils.isBlank(firstName) || StringUtils.isBlank(address)) throw
                new IllegalArgumentException("""
                        We noticed that some required fields are missing. Please ensure that all of the following 
                        fields are filled out correctly: Last Name, First Name, and Address. Thank you!
                        firstName: %s,""".formatted (firstName));
    }



}
