package com.learn.service.member.service;

import com.learn.service.member.dto.OrganizationCommandDto;
import com.learn.service.member.model.Organization;
import com.learn.service.member.repository.OrganizationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class OrganizationServiceTest {


    @Test
    public void testCreateNewOrganization() {
        // Mock dependencies
        OrganizationRepository organizationRepository = Mockito.mock(OrganizationRepository.class);
        OrganizationCommandDto request = new OrganizationCommandDto("TestOrg", LocalDate.now());

        // Mock repository behavior
        Organization savedOrganization = new Organization(1, request.name(), request.dateEstablished());
        when(organizationRepository.save(Mockito.any(Organization.class))).thenReturn(savedOrganization);

        // Create service instance
        OrganizationService organizationService = new OrganizationService(organizationRepository);

        // Invoke the method
        Integer newOrgId = organizationService.createNewOrganization(request);

        // Verify the result
        assertEquals(1, newOrgId.intValue()); // Assuming the save operation returns an organization with ID 1
    }

    @Test
    public void testGetOrganizationById() {
        // Mock dependencies
        OrganizationRepository organizationRepository = Mockito.mock(OrganizationRepository.class);

        // Mock repository behavior
        Organization organization = new Organization(1, "TestOrg", LocalDate.now());
        when(organizationRepository.findById(1)).thenReturn(Optional.of(organization));

        // Create service instance
        OrganizationService organizationService = new OrganizationService(organizationRepository);

        // Invoke the method
        Optional<Organization> retrievedOrg = organizationService.getOrganizationById(1);

        // Verify the result
        assertEquals(organization, retrievedOrg.orElse(null));
    }
}
