import java.util.List;
import org.sql2o.*;

public class Patient {
  private String name;
  private String birthDate;

  public Patient (String name, String birthDate) {
    this.name = name;
    this.birthDate = birthDate;
  }

  public String getName () {
    return name;
  }

  public String getBirthDate () {
    return birthDate;
  }

  public static List<Patient> getAll() {
    String sql = "SELECT name, birthDate FROM patients";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Patient.class);
    }
  }

  @Override
  public boolean equals(Object otherPatient) {
    if (!(otherPatient instanceof Patient)) {
      return false;
    } else {
      Patient newPatient = (Patient) otherPatient;
      return this.getName().equals(newPatient.getName()) &&
             this.getBirthDate().equals(newPatient.getBirthDate());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patients (name, birthDate) VALUES (:name, :birthDate)";
      con.createQuery(sql)
        .addParameter("name", this.name)
        .addParameter("birthDate", this.birthDate)
        .executeUpdate();
    }
  }
}
