mport org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class RangerTest {
    private Ranger ranger, ranger2;;
    @BeforeEach
    public void setUp() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", "kosgei", "12345678");
        ranger = new Ranger("John" ,"Doe",1);
        ranger2 = new Ranger("Jane" ,"Doe",2);
    }

    @AfterEach
    public void tearDown() {
        try (Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM rangers *;";
            con.createQuery(sql).executeUpdate();
        }
    }

    @Test
    public void ranger_instantiatesCorrectly() {
        assertNotNull(ranger);
    }

    @Test
    public void ranger_getsFirstName_John() {
        assertEquals("John", ranger.getFirstName());
    }

    @Test
    public void ranger_getsLastName_doe() {
        assertEquals("Doe", ranger.getLastName());
    }

    @Test
    public void ranger_badgeNumber_1() {
        assertEquals(1, ranger.getBadgeNumber());
    }

    @Test
    public void all_returnsAllInstancesOfRangers_true() {
        ranger.save();
        ranger2.save();
        assertEquals(Ranger.all().get(0), ranger);
        assertEquals(Ranger.all().get(1), ranger2);
    }

    @Test
    public void save_assignsIdToObject() {
        ranger.save();
        Ranger savedRanger = Ranger.all().get(0);
        assertEquals(ranger.getId(), savedRanger.getId());
    }

    @Test
    public void delete_deletesRanger_true() {
        ranger.save();
        int newRangerId = ranger.getId();
        ranger.delete();
        assertNull(Ranger.find(newRangerId));
    }

    @Test
    public void getSightings_retrievesALlSightingsFromDatabase_sightingList() {
        ranger.save();

        Sighting sighting = new Sighting("Zone A", "Ranger 1",1);
        sighting.save();
        Sighting sighting2 = new Sighting("Zone A", "Ranger 1",2);
        sighting2.save();

        Sighting[] sightings = new Sighting[]{sighting, sighting2};

        assertTrue(ranger.getSightings().containsAll(Arrays.asList(sightings)));
    }

    @Test
    public void equals_return_false_when_not_its_instance_true()
    {
        Animal animal = new Animal("Lion");
        assertNotEquals(ranger, animal);
    }

}