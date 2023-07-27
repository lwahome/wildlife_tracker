import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

public class SightingTest {
    private Sighting sighting,sighting2;

    @BeforeEach
    public void setUp() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", "kosgei", "12345678");
        sighting = new Sighting ("Ranger 1","Zone A",1);
        sighting2 = new Sighting( "Ranger 2", "Zone B" ,1);


    }
    @AfterEach
    public void tearDown() {
        try (Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM sightings *;";
            con.createQuery(sql).executeUpdate();
        }
    }

    @Test
    public void sighting_getsAnimalId_1()
    {
        assertEquals(1,sighting.getAnimalId());
    }


    @Test
    public void sighting_instantiatesCorrectly() {
        assertNotNull(sighting);
    }

    @Test
    public void sighting_getsLocationId_Zone_A() {
        assertEquals("Zone A", sighting.getLocation());
    }

    @Test
    public void ranger_getsRangerName_1() {
        assertEquals("Ranger 1", sighting.getRanger());
    }

    @Test
    public void all_returnsAllInstancesOfSightings_true() {
        sighting.save();
        sighting2.save();
        assertEquals(Sighting.all().get(0), sighting);
        assertEquals(Sighting.all().get(1), sighting2);
    }

    @Test
    public void save_assignsIdToObject() {
        sighting.save();
        Sighting savedSighting = Sighting.all().get(0);
        assertEquals(sighting.getId(), savedSighting.getId());
    }

    @Test
    public void finds_sighting_true()
    {
        sighting.save();
        assertEquals(sighting, Sighting.find(sighting.getId()));
    }

    @Test
    public void notInstanceOfSighting_false()
    {
        Animal animal = new Animal("Lion");
        assertNotEquals(sighting, animal);
    }

}