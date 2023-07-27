import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    Animal animal,animal1;

    @BeforeEach
    public void setUp() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", "kosgei", "12345678");
        animal = new Animal("Buffalo");
        animal1 = new Animal("Zebra");
    }

    @AfterEach
    public void tearDown() {
        try (Connection con = DB.sql2o.open()) {
            String sql = "DELETE FROM animals *;";
            con.createQuery(sql).executeUpdate();
        }

    }


    @Test
    public void animal_instantiatesCorrectly() {
        assertNotNull(animal);
    }

    @Test
    public void animal_getsName_Lion() {
        assertEquals("Buffalo", animal.getName());
    }

    @Test
    public void all_returnsAllInstancesOfAnimal_true() {
        animal.save();
        animal1.save();
        assertEquals(Animal.all().get(0), animal);
        assertEquals(Animal.all().get(1), animal1);
    }

    @Test
    public void save_assignsIdToObject() {
        animal.save();
        Animal savedAnimal = Animal.all().get(0);
        assertEquals(animal.getId(), savedAnimal.getId());
    }

    @Test
    public void getsType_Not_Endangered()
    {
        assertEquals("Not Endangered", Animal.getTYPE());
    }

    @Test
    public void find_returns_animal()
    {
        animal.save();
        assertEquals(animal , Animal.find(animal.getId()));
    }

    @Test
    public void return_false_when_not_instance_true()
    {
        Location location = new Location("Zone A");
        assertNotEquals(animal, location);
    }

}