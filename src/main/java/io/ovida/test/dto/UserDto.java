package io.ovida.test.dto;

import io.ovida.test.validation.ValidEmail;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDto {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1)
    private String firstName;

    @Size(min = 1)
    private String lastName;

    @ValidEmail
    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    private String email;

    @NotNull
    @Size(min = 1, max = 50)
    private String role;

    private UserDto(final Builder builder) {
        id = builder.id;
        firstName = builder.firstName;
        lastName = builder.lastName;
        email = builder.email;
        role = builder.role;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String role;

        private Builder() {
        }

        public Builder id(final Long val) {
            id = val;
            return this;
        }

        public Builder firstName(final String val) {
            firstName = val;
            return this;
        }

        public Builder lastName(final String val) {
            lastName = val;
            return this;
        }

        public Builder email(final String val) {
            email = val;
            return this;
        }

        public Builder role(final String val) {
            role = val;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }

    public @NotNull Long getId() {
        return id;
    }

    public @NotNull @Size(min = 1) String getFirstName() {
        return firstName;
    }

    public @Size(min = 1) String getLastName() {
        return lastName;
    }

    public @NotNull @Size(min = 1, message = "{Size.userDto.email}") String getEmail() {
        return email;
    }

    public @NotNull @Size(min = 1, max = 50) String getRole() {
        return role;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("UserDto [firstName=")
                .append(firstName)
                .append(", lastName=")
                .append(lastName)
                .append(", email=")
                .append(email).append("]");
        return builder.toString();
    }
}
