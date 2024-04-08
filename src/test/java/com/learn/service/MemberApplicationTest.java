package com.learn.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.service.member.MemberController;
import com.learn.service.member.dto.MemberCommandDto;
import com.learn.service.member.dto.MemberRepresentationDto;
import com.learn.service.member.model.Member;
import com.learn.service.member.model.Organization;
import com.learn.service.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc
class MemberApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @Test
    void createANewMember() throws Exception {
        MemberCommandDto memberDto = new MemberCommandDto(
                "Doe",
                "John",
                "Johnny",
                "123 Main St",
                "ID123456",
                "Abuja",
                "Taraku",
                LocalDate.parse("1990-01-01"),
                null,
                null,
                1
        );
        Long memberId = 1L; // Assuming member ID returned by service

        when(memberService.createANewMember(any(MemberCommandDto.class))).thenReturn(memberId);

        mockMvc.perform(MockMvcRequestBuilders.post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void getAMember() throws Exception {
        Long memberId = 1L; // Assuming member ID
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

        Organization organization = new Organization(1, "GMSB", LocalDate.of(2022, 2, 1));

        MemberRepresentationDto memberResponseDto = new MemberRepresentationDto(member, organization);
        // Assuming member response DTO

        when(memberService.getAMemberById(memberId)).thenReturn(memberResponseDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/members/{requestId}", memberId))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getMembers() throws Exception {
        when(memberService.getMembers()).thenReturn(Collections.emptyList()); // Assuming no members returned
        mockMvc.perform(MockMvcRequestBuilders.get("/members"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //write a test for updating a member
    @Test
    void updateAMember() throws Exception {
        MemberCommandDto memberDto = new MemberCommandDto(
                "Doe",
                "John",
                "Johnny",
                "123 Main St",
                "ID123456",
                "Abuja",
                "Taraku",
                LocalDate.parse("1990-01-01"),
                null,
                null,
                1
        );
        Long memberId = 1L; // Assuming member ID returned by service

        mockMvc.perform(MockMvcRequestBuilders.put("/members/{requestId}", memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberDto)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }





}
