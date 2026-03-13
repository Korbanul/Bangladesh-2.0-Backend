package com.bangladesh20.backend.Entity.Type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionType {
    CREATE_USER("user:create"),
    DELETE_USER("user:create"),
    USER_MANAGE("user:manage");

    private final String Permission;
}
