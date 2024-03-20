package com.learn.service;

import com.learn.service.member.model.Member;
import com.learn.service.member.model.Organization;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class MemberJsonTest {

    @Autowired
    private JacksonTester<Member> json;

    @Test
    void memberSerializationTest() throws IOException {
        AggregateReference<Organization, Integer> orgIntegerAggregateReference =
                getOrganizationIntegerAggregateReference();
        Member member = new Member(
                1L,
                "Doe",
                "John",
                "Johnny",
                "123 Main St",
                "ID123456",
                "Abuja",
                "Taraku",
                LocalDate.parse("1990-01-01"),
                LocalDateTime.of(2022, 2, 14, 12, 30),
                null,
                null
        );

        assertThat(json.write(member)).isStrictlyEqualToJson("member_expected.json");
        assertThat(json.write(member)).hasJsonPathNumberValue("@.id");
        assertThat(json.write(member)).extractingJsonPathNumberValue("@.id")
                .isEqualTo(1);
        assertThat(json.write(member)).hasJsonPathStringValue("@.lastName");
        assertThat(json.write(member)).extractingJsonPathStringValue("@.lastName")
                .isEqualTo("Doe");
    }

    private static AggregateReference<Organization, Integer> getOrganizationIntegerAggregateReference() {
        Organization organization =
                new Organization(1,"GMSB",LocalDate.of(2022, 2,1));
        AggregateReference<Organization, Integer> orgIntegerAggregateReference = AggregateReference.to(organization.id());
        return orgIntegerAggregateReference;
    }

    @Test
    void memberDeserializationTest() throws IOException {
        String expected = """
                {
                  "id": 1,
                  "lastName": "Doe",
                  "firstName": "John",
                  "nickName": "Johnny",
                  "address": "123 Main St",
                  "idNumber": "ID123456",
                  "currentTown": "Abuja",
                  "homeTown": "Taraku",
                  "birthOfBirth": "1990-01-01",
                  "createdOn": "2022-02-14T12:30:00",
                  "updatedOn": null,
                  "organization":null
                }
                """;
        Member member = new Member(
                1L,
                "Doe",
                "John",
                "Johnny",
                "123 Main St",
                "ID123456",
                "Abuja",
                "Taraku",
                LocalDate.parse("1990-01-01"),
                LocalDateTime.of(2022, 2, 14, 12, 30),
                null,
                null
        );
        assertThat(json.parse(expected).getObject().toString()).isEqualTo(member.toString());
        assertThat(json.parseObject(expected).getId()).isEqualTo(1);
        assertThat(json.parseObject(expected).getNickName()).isEqualTo("Johnny");
    }
}


