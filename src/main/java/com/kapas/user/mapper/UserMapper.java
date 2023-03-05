package com.kapas.user.mapper;

import com.kapas.user.entity.Role;
import com.kapas.user.entity.User;
import com.kapas.user.model.UserRequest;
import com.kapas.user.model.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public abstract class UserMapper {

    @Mapping(target = "userName", source = "userRequest.userName")
    @Mapping(target = "firstName", source = "userRequest.firstName")
    @Mapping(target = "lastName", source = "userRequest.lastName")
    @Mapping(target = "email", source = "userRequest.email")
    @Mapping(target = "mobile", source = "userRequest.mobile")
    @Mapping(target = "password", source = "userRequest.password")
    @Mapping(target = "isActive", source = "userRequest.isActive")
    @Mapping(target = "description", source = "userRequest.description")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", source="user.createdBy")
    @Mapping(target = "creationTime", source = "user.creationTime")
    @Mapping(target = "modificationTime", source = "user.modificationTime")
    @Mapping(target = "modifiedBy", source = "user.modifiedBy")
    public abstract User userRequesttoUser(UserRequest userRequest, User user, Role role);

    public abstract UserResponse userToUserResponse(User user);

}
