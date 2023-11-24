package com.fciencias.freshbowl.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity(name = "resource_access")
public class ResourceAccess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int resourceId;

    @NotNull(message = "No se ha informado recurso")
    @NotBlank(message = "No se ha informado recurso")
    private String resourceUrl;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole roleId;

    private int accessType;

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public UserRole getRoleId() {
        return roleId;
    }

    public void setRoleId(UserRole roleId) {
        this.roleId = roleId;
    }

    public int getAccessType() {
        return accessType;
    }

    public void setAccessType(int accessType) {
        this.accessType = accessType;
    }

}
