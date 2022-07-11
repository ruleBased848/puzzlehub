package com.rulebased848.puzzlehub.service;

import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.security.core.userdetails.*;
import com.rulebased848.puzzlehub.domain.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository repo;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user_ = repo.findByUsername(username);
        if (!user_.isPresent()) {
            throw new UsernameNotFoundException("Username not found.");
        }
        var user = user_.get();
        var userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
        userBuilder.password(user.getPassword());
        return userBuilder.build();
    }
}