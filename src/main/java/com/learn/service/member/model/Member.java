package com.learn.service.member.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    private Long id;
    private String lastName;
    private String firstName;
    private String nickName;
    private String address;
    private String idNumber;
    private String currentTown;
    private String homeTown;
    private LocalDate birthOfBirth;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    private AggregateReference<Organization, Integer> organization;
}
        

