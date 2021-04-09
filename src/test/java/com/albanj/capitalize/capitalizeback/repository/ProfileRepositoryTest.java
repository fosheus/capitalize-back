package com.albanj.capitalize.capitalizeback.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.albanj.capitalize.capitalizeback.entity.RefProfile;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository repo;

    @Test
    public void testGetProfile() {
        RefProfile profile = repo.findOneByLabel("USER");
        assertNotNull(profile);
    }

}
