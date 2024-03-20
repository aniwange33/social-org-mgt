package com.learn.service.member.dto;

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
){

}
