import java.util.List;
import org.sql2o.*;

public class Doctor {
  private int id;
  private String name;
  private int specialtyId;

  public Doctor ( String name, int specialtyId) {
    this.id = 0;
    this.name = name;
    this.specialtyId = specialtyId;
  }

  public int getId() {
    return id;
  }


  public String getName () {
    return name;
  }

  public int getSpecialtyId () {
    return specialtyId;
  }

  public static List<Doctor> getAll() {
    String sql = "SELECT * FROM doctors";
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
      return this.getId() == newDoctor.getId() &&
             this.getName().equals(newDoctor.getName()) &&
             this.getSpecialtyId() ==  newDoctor.getSpecialtyId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO doctors (name, specialtyId) VALUES (:name, :specialtyId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("specialtyId", this.specialtyId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Doctor find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM doctors where id=:id";
      Doctor doctor = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Doctor.class);
      return doctor;
    }
  }

  public List<Patient> getPatients() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM patients where doctorId=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Patient.class);
    }
  }
}
