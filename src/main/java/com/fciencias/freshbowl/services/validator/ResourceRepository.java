package com.fciencias.freshbowl.services.validator;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.fciencias.freshbowl.models.ResourceAccess;
import com.fciencias.freshbowl.models.UserRole;

@Repository
@Component
public interface ResourceRepository extends JpaRepository<ResourceAccess, Integer> {

    public List<ResourceAccess> findByResourceUrl(String resourceUrl);

    public List<ResourceAccess> findByRoleId(UserRole roleId);

    public ResourceAccess findByResourceUrlAndRoleId(String resourceUrl, UserRole roleId);
}
