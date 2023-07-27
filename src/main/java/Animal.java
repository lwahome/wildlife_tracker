import org.sql2o.Connection;

import java.util.List;

public class Animal {
    private int id;
    private String name;
    public static final String TYPE = "Not Endangered";


    public Animal(String name) {
        this.name = name;
    }

    public static String getTYPE() {
        return TYPE;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void save() {
        try(Connection con = DB.sql2o.open())  {
            String sql = "INSERT INTO animals (name,type) VALUES (:name, :type)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("type", TYPE)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static Animal find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals where id=:id";
            Animal animal= con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Animal.class);
            return animal;
        }
    }

    @Override
    public boolean equals(Object otherAnimal){
        if (!(otherAnimal instanceof Animal)) {
            return false;
        } else {
            Animal newAnimal  = ( Animal) otherAnimal ;
            return this.getId() == newAnimal.id;
        }
    }

    public static List<Animal> all() {
        String sql = "SELECT * FROM animals where type = 'Not Endangered'";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Animal.class);
        }
    }

}