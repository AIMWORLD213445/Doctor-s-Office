import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class PatientTest {
  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/doctors_office_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deletePatientsQuery = "DELETE FROM patients;";
      String deleteDoctorsQuery = "DELETE FROM doctors *;";
      String deleteSpecialtysQuery = "DELETE FROM specialties *;";
      con.createQuery(deletePatientsQuery).executeUpdate();
      con.createQuery(deleteDoctorsQuery).executeUpdate();
    }
  }

  @Test
  public void getId_patientsInstantiateWithAnId_1() {
    Patient testPatient = new Patient("House", "1/1/2016", 1);
    testPatient.save();
    assertTrue(testPatient.getId() > 0);
  }


  @Test
  public void save_savesIntoDatabase_true() {
    Patient myPatient = new Patient("Zack", "09/19/1987", 1);
    myPatient.save();
    assertTrue(Patient.getAll().get(0).equals(myPatient));
  }

  @Test
  public void save_assignsIdToObject() {
    Patient myPatient = new Patient("House", "1/1/2016", 1);
    myPatient.save();
    Patient savedPatient = Patient.getAll().get(0);
    assertEquals(myPatient.getId(), savedPatient.getId());
  }

  @Test
  public void save_savesDoctorIdIntoDB_true() {
    Specialty mySpecialty = new Specialty("surgery");
    mySpecialty.save();
    Doctor myDoctor = new Doctor("House", mySpecialty.getId());
    myDoctor.save();
    Patient myPatient = new Patient("Zack", "09/19/1987", myDoctor.getId());
    myPatient.save();
    Patient savedPatient = Patient.find(myPatient.getId());
    assertEquals(savedPatient.getDoctorId(), myDoctor.getId());
  }

}
