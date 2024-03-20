package com.learn.service.member.repository;

import com.learn.service.member.model.Organization;
import org.springframework.data.repository.CrudRepository;

public interface OrganizationRepository extends CrudRepository<Organization, Integer> {
}
