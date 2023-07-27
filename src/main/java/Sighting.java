import org.sql2o.Connection;

import java.util.List;

public class Sighting {
    private int id;
    private String location;
    private String ranger;
    private int animalId;

    public Sighting(String ranger,String location, int animalId) {
        this.location = location;
        this.ranger = ranger;
        this.animalId = animalId;
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getRanger() {
        return ranger;
    }

    public int getAnimalId() {
        return animalId;
    }

    public static List<Sighting> all() {
        String sql = "SELECT * FROM sightings";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Sighting.class);
        }
    }

    public void save() {
        try(Connection con = DB.sql2o.open())  {
            String sql = "INSERT INTO sightings (location, ranger , animalid) VALUES (:location, :ranger, :animalId)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("location", this.location)
                    .addParameter("ranger",this.ranger)
                    .addParameter("animalId", this.animalId)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static Sighting find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM sightings where id=:id";
            Sighting sighting= con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Sighting.class);
            return sighting;
        }
    }

    @Override
    public boolean equals(Object otherSighting){
        if (!(otherSighting instanceof Sighting)) {
            return false;
        } else {
            Sighting newSighting = (Sighting) otherSighting;
            return this.getId() == newSighting.id;
        }
    }

}