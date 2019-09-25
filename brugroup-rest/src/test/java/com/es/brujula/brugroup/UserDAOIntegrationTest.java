package com.es.brujula.brugroup;

import com.es.brujula.brugroup.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserDAOIntegrationTest {
    public static final String juan = "juan";
    public static final String password = "password";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserDAO dao;

    private User user1;

    @Before
    public void setUp() throws Exception {
        user1 = new User();
        user1.setUsername(juan);
        user1.setFullName(juan);
        user1.setPassword(password);
    }

    @Test
    public void whenFindByName_thenReturnUser() {
        entityManager.persist(user1);
        entityManager.flush();
        User found = dao.findFirstByUsername(user1.getUsername());
        assertThat(found.getUsername())
                .isEqualTo(user1.getUsername());
    }
}
