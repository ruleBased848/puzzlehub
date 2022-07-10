package com.rulebased848.puzzlehub.domain;

import java.util.*;
import org.springframework.data.repository.*;

public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findByUsername(String username);
}