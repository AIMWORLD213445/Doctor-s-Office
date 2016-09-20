import java.util.List;
import org.sql2o.*;

public class Patient {
  private String name;
  private String birthDate;
  private int id;
  private int doctorId;

  public Patient (String name, String birthDate, int doctorId) {
    this.name = name;
    this.birthDate = birthDate;
    this.doctorId = doctorId;
  }

  public int getId() {
    return id;
  }

  public String getName () {
    return name;
  }

  public String getBirthDate () {
    return birthDate;
  }

  public int getDoctorId () {
    return doctorId;
  }

  public static List<Patient> getAll() {
    String sql = "SELECT * FROM patients";
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
      return this.getId() == newPatient.getId() &&
             this.getName().equals(newPatient.getName()) &&
             this.getBirthDate().equals(newPatient.getBirthDate()) &&
             this.getDoctorId() == newPatient.getDoctorId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO patients (name, birthDate, doctorId) VALUES (:name, :birthDate, :doctorId)";
      this.id = (int) con.createQuery(sql,true)
        .addParameter("name", this.name)
        .addParameter("birthDate", this.birthDate)
        .addParameter("doctorId", this.doctorId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Patient find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patients where id=:id";
      Patient patient = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Patient.class);
      return patient;
    }
  }
}
