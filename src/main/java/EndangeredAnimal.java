import org.sql2o.Connection;

import java.util.List;

public class EndangeredAnimal extends Animal {
    private int id;
    private String health;
    private String age;
    public static final String TYPE = "Endangered";


    public EndangeredAnimal(String name,String health, String age) {
        super(name);
        this.age = age;
        this.health = health;
    }

    public String getHealth() {
        return health;
    }

    public String getAge() {
        return age;
    }

    @Override
    public int getId() {
        return id;
    }

    public static String getTYPE() {
        return TYPE;
    }

    public static List<EndangeredAnimal> allEndangeredAnimals() {
        String sql = "SELECT * FROM animals where type='Endangered'";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(EndangeredAnimal.class);
        }
    }

    public void save() {
        try(Connection con = DB.sql2o.open())  {
            String sql = "INSERT INTO animals (name, type, health ,age) VALUES (:name, :type, :health , :age)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", super.getName())
                    .addParameter("type",TYPE)
                    .addParameter("health",this.health)
                    .addParameter("age",this.age)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static EndangeredAnimal find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals where id=:id";
            EndangeredAnimal endangeredAnimal= con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(EndangeredAnimal.class);
            return endangeredAnimal;
        }
    }

    @Override
    public boolean equals(Object otherEndangeredAnimal){
        if (!(otherEndangeredAnimal instanceof EndangeredAnimal)) {
            return false;
        } else {
            EndangeredAnimal newEndangeredAnimal  = ( EndangeredAnimal) otherEndangeredAnimal ;
            return super.getId() == newEndangeredAnimal.id;
        }
    }
}