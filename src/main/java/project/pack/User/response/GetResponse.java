package project.pack.User.response;

import project.pack.User.enums.Role;

import java.util.UUID;

public record GetResponse(
        UUID id,
        String username,
        Role role
) {}
