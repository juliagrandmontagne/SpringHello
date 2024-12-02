package fr.diginamic.hello.service;

import fr.diginamic.hello.Ville;
import fr.diginamic.hello.VilleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class VilleServiceTest {
    @Autowired
    private VilleRepository villeRepository;

    @Test
    public void testRepository() {
        Ville ville = new Ville("Paris", 2148000, "75");
        villeRepository.save(ville);
        List<Ville> villes = villeRepository.findAll();
        assertEquals(1, villes.size());
    }
}