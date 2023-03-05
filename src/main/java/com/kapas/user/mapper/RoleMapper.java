package com.kapas.user.mapper;

import com.kapas.user.entity.Role;
import com.kapas.user.model.RoleRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class RoleMapper {

    @Mapping(target = "roleName", source = "roleRequest.roleName")
    @Mapping(target = "description", source = "roleRequest.description")
    @Mapping(target = "id", ignore= true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "creationTime", ignore = true)
    @Mapping(target = "modificationTime", ignore = true)
    public abstract Role roleRequestToRole(RoleRequest roleRequest);
}
