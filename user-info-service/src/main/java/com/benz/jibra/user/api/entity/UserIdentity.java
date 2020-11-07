package com.benz.jibra.user.api.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserIdentity implements Serializable {

    private long userId;
    private String nicOrPassport;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserIdentity that = (UserIdentity) o;
        return userId == that.userId &&
                Objects.equals(nicOrPassport, that.nicOrPassport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, nicOrPassport);
    }
}