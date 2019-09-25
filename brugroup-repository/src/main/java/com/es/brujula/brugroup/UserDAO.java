package com.es.brujula.brugroup;

import com.es.brujula.brugroup.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserDAO extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM USER WHERE US_USERNAME = :username", nativeQuery = true)
    User findFirstByUsername(@Param("username") String username);
}
