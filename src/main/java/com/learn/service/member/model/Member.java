package com.learn.service.member.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Setter
@Getter
@ToString
@NoArgsConstructor
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

    public Member(
            Long id,
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
            AggregateReference<Organization, Integer> organization) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.nickName = nickName;
        this.address = address;
        this.idNumber = idNumber;
        this.currentTown = currentTown;
        this.homeTown = homeTown;
        this.birthOfBirth = birthOfBirth;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.setOrganization(organization);
    }
}
        

