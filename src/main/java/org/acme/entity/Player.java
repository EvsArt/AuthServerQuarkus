package org.acme.entity;

import io.smallrye.common.constraint.NotNull;

public record Player(
        Long id,
        @NotNull String login,
        @NotNull String passwordHash
) {
}
