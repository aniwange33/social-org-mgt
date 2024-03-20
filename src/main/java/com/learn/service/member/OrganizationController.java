package com.learn.service.member;

import com.learn.service.member.dto.OrganizationCommandDto;
import com.learn.service.member.model.Organization;
import com.learn.service.member.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;
    @PostMapping
    public ResponseEntity<String> createANewOrganization(@RequestBody OrganizationCommandDto organizationCommandDto, UriComponentsBuilder urc) {
        String path = "/organizations/{id}";
        Integer newOrganizationId = organizationService.createNewOrganization(organizationCommandDto);
        URI uri = urc.path(path)
                .buildAndExpand(newOrganizationId)
                .toUri();
        return ResponseEntity.created(uri).build();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Organization>> getOrganizationById(@PathVariable("id") Integer id) {
        Optional<Organization> organization = organizationService.getOrganizationById(id);
        return ResponseEntity.ok(organization);

    }
}
