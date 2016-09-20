import java.util.List;
import org.sql2o.*;

public class Specialty {
  private int id;
  private String name;



  public Specialty ( String name) {
    this.id = 0;
    this.name = name;
  }

  public int getId() {
    return id;
  }


  public String getName () {
    return name;
  }

  public static List<Specialty> getAll() {
    String sql = "SELECT * FROM specialties";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Specialty.class);
    }
  }

  @Override
  public boolean equals(Object otherSpecialty) {
    if (!(otherSpecialty instanceof Specialty)) {
      return false;
    } else {
      Specialty newSpeciality = (Specialty) otherSpecialty;
      return this.getId() == newSpeciality.getId() &&
             this.getName().equals(newSpeciality.getName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO specialties (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Specialty find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM specialties where id=:id";
      Specialty specialty = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Specialty.class);
      return specialty;
    }
  }

  public List<Doctor> getDoctors() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM doctors where specialtyId=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Doctor.class);
    }
  }
}
