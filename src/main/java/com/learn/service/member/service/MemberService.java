package com.learn.service.member.service;

import com.learn.service.member.dto.MemberCommandDto;
import com.learn.service.member.dto.MemberRepresentationDto;
import com.learn.service.member.model.Member;
import com.learn.service.member.model.Organization;
import com.learn.service.member.repository.MemberRepository;
import com.learn.service.member.repository.OrganizationRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final OrganizationRepository organizationRepository;

    public MemberService(MemberRepository memberRepository, OrganizationRepository organizationRepository) {
        this.memberRepository = memberRepository;
        this.organizationRepository = organizationRepository;
    }


    public Long createANewMember(MemberCommandDto memberDto) {
        Objects.requireNonNull(memberDto, "memberDto must not be null");
        // Validate or set default values for memberDto properties if needed
        Integer orgId = memberDto.orgId();
        Organization organization = organizationRepository.findById(orgId)
                .orElseThrow(() -> new EntityNotFoundException("Organization not found for ID: " + memberDto.orgId()));

        AggregateReference<Organization, Integer> org = AggregateReference.to(organization.getId());

        Member member = new Member();
        BeanUtils.copyProperties(memberDto, member);
        member.setCreatedOn(LocalDateTime.now());
        member.setOrganization(org);

        try {
            Member savedMember = memberRepository.save(member);
            return savedMember.getId();
        } catch (DataAccessException ex) {
            // Handle database-related exceptions (e.g., ConstraintViolationException) appropriately
            ex.getStackTrace();
            throw new RuntimeException("Failed to save member", ex);
        }
    }


    public MemberRepresentationDto getAMemberById(Long requestId) {
        String MemberNotFoundMessage = "Member with ID " + requestId + " not found.";
        String OrgNotFoundMessage = "Organization with ID " + requestId + " not found.";
        Member member = memberRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException(MemberNotFoundMessage));
        AggregateReference<Organization, Integer> organizationAg = member.getOrganization();
        if (organizationAg != null) {
            Integer id = organizationAg.getId();
            assert id != null;
            Organization organization =
                    organizationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(OrgNotFoundMessage));
            return new MemberRepresentationDto(member, organization);
        }
        return new MemberRepresentationDto(member, null);

    }


    public List<Member> getMembers() {
        return StreamSupport.stream(memberRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

    }

    public void updateAMember(Long requestId, MemberCommandDto request) {
        Member member = memberRepository.findById(requestId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found for ID: " + requestId));
        BeanUtils.copyProperties(request, member);
        member.setUpdatedOn(LocalDateTime.now());
        memberRepository.save(member);

    }
}
