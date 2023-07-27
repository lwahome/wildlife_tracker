import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {
    private Location location,location1;

    @BeforeEach
    public void setUp() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", "kosgei", "12345678");
        location = new Location("Zone A");
        location1 = new Location("Zone B");
    }

    @AfterEach
    public void tearDown() {
        try (Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM locations *;";
            con.createQuery(sql).executeUpdate();
        }
    }

    @Test
    public void location_instantiatesCorrectly() {
        assertNotNull(location);
    }

    @Test
    public void location_getsName_true() {
        assertEquals("Zone A", location.getName());
    }

    @Test
    public void all_returnsAllInstancesOfLocationss_true() {
        location.save();
        location1.save();
        assertEquals(Location.all().get(0), location);
        assertEquals(Location.all().get(1), location1);
    }

    @Test
    public void save_assignsIdToObject() {
        location.save();
        Location savedLocation= Location.all().get(0);
        assertEquals(location.getId(), savedLocation.getId());
    }

    @Test
    public void finds_location_true()
    {
        location.save();
        assertEquals(location, Location.find(location.getId()));
    }

    @Test
    public void false_when_not_instance()
    {
        Animal animal = new Animal("Lion");
        assertNotEquals(location,animal);
    }
}