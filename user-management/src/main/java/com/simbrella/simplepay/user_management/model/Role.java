package com.simbrella.simplepay.user_management.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

import static com.simbrella.simplepay.user_management.model.Permission.ADMIN_CREATE;
import static com.simbrella.simplepay.user_management.model.Permission.ADMIN_DELETE;
import static com.simbrella.simplepay.user_management.model.Permission.ADMIN_READ;
import static com.simbrella.simplepay.user_management.model.Permission.ADMIN_UPDATE;
import static com.simbrella.simplepay.user_management.model.Permission.MANAGER_CREATE;
import static com.simbrella.simplepay.user_management.model.Permission.MANAGER_DELETE;
import static com.simbrella.simplepay.user_management.model.Permission.MANAGER_READ;
import static com.simbrella.simplepay.user_management.model.Permission.MANAGER_UPDATE;

@Getter
@RequiredArgsConstructor
public enum Role {

    USER(Collections.emptySet()),
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    ADMIN_CREATE,
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    ),
    MANAGER(
            Set.of(
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_DELETE,
                    MANAGER_CREATE
            )
    );

    private final Set<Permission> permissions;
}
