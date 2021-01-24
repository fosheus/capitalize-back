package com.albanj.capitalize.capitalizeback.repository;

import com.albanj.capitalize.capitalizeback.entity.ApplicationUser;
import com.albanj.capitalize.capitalizeback.entity.RefProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository repo;

    @Autowired
    private ApplicationUserRepository userRepo;

    @Test
    public void testGetProfile() {
        RefProfile profile = repo.findOneByLabel("USER");
        assertNotNull(profile);
    }

}
