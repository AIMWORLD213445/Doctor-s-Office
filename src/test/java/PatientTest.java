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
      String deletePatientsQuery = "DELETE FROM patients *;";
      // String deleteDoctorsQuery = "DELETE FROM doctors *;";
      con.createQuery(deletePatientsQuery).executeUpdate();
      // con.createQuery(deleteDoctorsQuery).executeUpdate();
    }
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Patient myPatient = new Patient("Zack", "09/19/1987");
    myPatient.save();
    assertTrue(Patient.getAll().get(0).equals(myPatient));
  }
}
