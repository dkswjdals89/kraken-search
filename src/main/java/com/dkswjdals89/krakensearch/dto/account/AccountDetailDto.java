package com.dkswjdals89.krakensearch.dto.account;

import com.dkswjdals89.krakensearch.domain.account.Account;
import com.dkswjdals89.krakensearch.domain.account.AccountRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountDetailDto implements UserDetails {
    private long id;
    private String userId;
    @JsonIgnore
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private AccountRole role;
    private Boolean activated;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public AccountDetailDto(Account account) {
        this.id = account.getId();
        this.userId = account.getUserId();
        this.password = account.getPassword();
        this.email = account.getEmail();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.createdDate = account.getCreatedDate();
        this.modifiedDate = account.getModifiedDate();
        this.activated = account.getActivated();
        this.role = account.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if (this.role != null) {
            grantedAuthorities.add(new SimpleGrantedAuthority(this.role.getKey()));
        }
        return grantedAuthorities;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return this.activated;
    }
}
