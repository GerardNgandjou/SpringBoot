
// package com.example.vote.onlinevote.model;

// import java.util.Collection;
// import java.util.Collections;

// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;

// @Getter
// @Setter
// @AllArgsConstructor
// @NoArgsConstructor
// public class LoginPrincipal implements UserDetails {

//     private Login login;

//     @Override
//     public Collection<? extends GrantedAuthority> getAuthorities() { 
//         return Collections.singleton(new SimpleGrantedAuthority("USER")); 
//         // This should return the authorities granted to the user
//     }

//     @Override
//     public String getPassword() {
//         return login.getPassword();
//     }

//     @Override
//     public String getUsername() {
//         return login.getUsername();
//     }

//     @Override
//     public boolean isAccountNonLocked() {
//         return true;
//     }

//     @Override
//     public boolean isEnabled() {
//         return true;
//     }

//     @Override
//     public boolean isAccountNonExpired() {
//         return true;    
//     }

//     @Override
//     public boolean isCredentialsNonExpired() {
//         return true;    
//     }

// }
