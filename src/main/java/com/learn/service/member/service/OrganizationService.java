package com.learn.service.member.service;

import com.learn.service.member.dto.OrganizationCommandDto;
import com.learn.service.member.model.Organization;
import com.learn.service.member.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationService {
    private final OrganizationRepository organizationRepository;
    public Integer createNewOrganization(OrganizationCommandDto request){
        Objects.requireNonNull(OrganizationCommandDto.class, "Cannot be null");
        Organization organization = new Organization(request.name(), request.dateEstablished());
        log.info("payload {}", organization.toString());
        Organization saveOrganization = organizationRepository.save(organization);
        Integer id = saveOrganization.getId();
        log.info("id {}", id);
        return id;
    }

    public Optional<Organization> getOrganizationById(Integer id) {
        return  organizationRepository.findById(id);

    }
}
