import java.util.List;
import org.sql2o.*;

public class Doctor {
  private String name;
  private String specialty;

  public Doctor (String name, String specialty) {
    this.name = name;
    this.specialty = specialty;
  }

  public String getName () {
    return name;
  }

  public String getSpecialty () {
    return specialty;
  }

  public static List<Doctor> getAll() {
    String sql = "SELECT name, specialty FROM doctors";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Doctor.class);
    }
  }

  @Override
  public boolean equals(Object otherDoctor) {
    if (!(otherDoctor instanceof Doctor)) {
      return false;
    } else {
      Doctor newDoctor = (Doctor) otherDoctor;
      return this.getName().equals(newDoctor.getName()) &&
             this.getSpecialty().equals(newDoctor.getSpecialty());
    }
  }
  
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO doctors (name, specialty) VALUES (:name, :specialty)";
      con.createQuery(sql)
        .addParameter("name", this.name)
        .addParameter("specialty", this.specialty)
        .executeUpdate();
    }
  }
}
