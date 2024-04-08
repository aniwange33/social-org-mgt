package com.learn.service;

import com.learn.service.member.dto.MemberCommandDto;
import com.learn.service.member.dto.MemberRepresentationDto;
import com.learn.service.member.model.Member;
import com.learn.service.member.model.Organization;
import com.learn.service.member.repository.MemberRepository;
import com.learn.service.member.repository.OrganizationRepository;
import com.learn.service.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    OrganizationRepository organizationRepository;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        memberService = new MemberService(memberRepository, organizationRepository);
    }




    @Test
    void testCreateANewMember() {
        // Arrange
        MemberCommandDto memberDto = getMemberDto();
        Organization organization =
                new Organization(1, "",LocalDate.of(2024, 01,01));
        when(organizationRepository.findById(1)).thenReturn(Optional.of(organization));

        Member savedMember = new Member();
        savedMember.setId(123L);
        when(memberRepository.save(any(Member.class))).thenReturn(savedMember);

        // Act
        Long memberId = memberService.createANewMember(memberDto);

        // Assert
        assertNotNull(memberId);
        assertEquals(savedMember.getId(), memberId);
        verify(organizationRepository, times(1)).findById(1);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void testCreateANewMemberOrganizationNotFound() {
        // Arrange
        MemberCommandDto memberDto = getMemberDto();
        when(organizationRepository.findById(1)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EntityNotFoundException.class, () -> memberService.createANewMember(memberDto));
        verify(organizationRepository, times(1)).findById(1);
        verify(memberRepository, never()).save(any(Member.class));
    }

    @Test
    void testCreateANewMemberDataAccessException() {
        // Arrange
        MemberCommandDto memberDto = getMemberDto();
        Organization organization =
                new Organization(1, "",LocalDate.of(2024, 01,01));
        when(organizationRepository.findById(1)).thenReturn(Optional.of(organization));

        when(memberRepository.save(any(Member.class))).thenThrow(new DataAccessException("Simulated database exception") {});

        // Act and Assert
        assertThrows(RuntimeException.class, () -> memberService.createANewMember(memberDto));
        verify(organizationRepository, times(1)).findById(1);
        verify(memberRepository, times(1)).save(any(Member.class));
    }

    @Test
    void testGetAMemberById() {
        Long memberId = 1L;
        AggregateReference<Organization, Integer> org
                = getOrganizationIntegerAggregateReference();
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
                org
        );

        Organization  organization = new Organization (1, "GMSB", LocalDate.of(2022, 2, 1));

        MemberRepresentationDto memberResponseDto = new MemberRepresentationDto(member,organization);

        // Mock the behavior of OrganizationRepository to return the organization with ID 1
        when(organizationRepository.findById(1)).thenReturn(Optional.of(organization));

        when(memberRepository.findById(memberResponseDto.member().getId())).thenReturn(Optional.of(member));

        MemberRepresentationDto result = memberService.getAMemberById(memberId);

        assertEquals(member, result.member());
        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    void testGetAMemberByIdNotFound() {
        Long memberId = 1L;

        when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> memberService.getAMemberById(memberId));
        verify(memberRepository, times(1)).findById(memberId);
    }

    @Test
    void testGetMembers() {
        AggregateReference<Organization, Integer> org
                = getOrganizationIntegerAggregateReference();
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
                org
        );

        List<Member> mockMembers = new ArrayList<>();
        mockMembers.add(member);
        when(memberRepository.findAll()).thenReturn(mockMembers);

        List<Member> result = memberService.getMembers();
        assertEquals(mockMembers, result);
        verify(memberRepository, times(1)).findAll();
    }
    private static AggregateReference<Organization, Integer> getOrganizationIntegerAggregateReference() {
        Organization organization =
                new Organization(1,"GMSB",LocalDate.of(2022, 2,1));
        AggregateReference<Organization, Integer> orgIntegerAggregateReference = AggregateReference.to(organization.getId());
        return orgIntegerAggregateReference;
    }
    private static MemberCommandDto getMemberDto() {
        MemberCommandDto member = new MemberCommandDto(
                "Jack",
                "Akume",
                "Akumeee",
                "123 Main St",
                "ID123456",
                "Abuja",
                "Taraku",
                LocalDate.parse("1990-01-01"),
                LocalDateTime.of(2022, 2, 14, 12, 30),
                null,
                1
        );
        return member;
    }
    // Write a test method for updateAMember
    @Test
    void testUpdateAMember() {
        Long memberId = 1L;
        MemberCommandDto memberDto = getMemberDto();
        AggregateReference<Organization, Integer> org
                = getOrganizationIntegerAggregateReference();
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
                org
        );
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        memberService.updateAMember(memberId, memberDto);
        verify(memberRepository, times(1)).findById(memberId);
        verify(memberRepository, times(1)).save(any(Member.class));
    }
    //test for updateAMember when member is not found
    @Test
    void testUpdateAMemberNotFound() {
        Long memberId = 1L;
        MemberCommandDto memberDto = getMemberDto();
        when(memberRepository.findById(memberId)).
                thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> memberService.updateAMember(memberId, memberDto));
        verify(memberRepository, times(1)).findById(memberId);
        verify(memberRepository, never()).save(any(Member.class));
    }
}
