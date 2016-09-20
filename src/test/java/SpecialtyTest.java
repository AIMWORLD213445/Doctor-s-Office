import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class SpecialtyTest {
  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/doctors_office_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteDoctorsQuery = "DELETE FROM doctors *;";
      String deleteSpecialtysQuery = "DELETE FROM specialties *;";
      String deletePatientsQuery = "DELETE FROM patients;";
      con.createQuery(deleteDoctorsQuery).executeUpdate();
      con.createQuery(deleteSpecialtysQuery).executeUpdate();
    }
  }

  @Test
  public void getId_doctorsInstantiateWithAnId_1() {
    Specialty testSpecialty = new Specialty("surgeon");
    testSpecialty.save();
    assertTrue(testSpecialty.getId() > 0);
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Specialty mySpecialty = new Specialty("Differential Diagnosis");
    mySpecialty.save();
    assertTrue(Specialty.getAll().get(0).equals(mySpecialty));
  }

  @Test
  public void save_assignsIdToObject() {
    Specialty mySpecialty = new Specialty("surgeon");
    mySpecialty.save();
    Specialty savedSpecialty = Specialty.getAll().get(0);
    assertEquals(mySpecialty.getId(), savedSpecialty.getId());
  }

  @Test
  public void find_returnsSpecialtyWithSameId_secondSpecialty() {
    Specialty firstSpecialty = new Specialty("surgeon");
    firstSpecialty.save();
    Specialty secondSpecialty = new Specialty("pediatrics");
    secondSpecialty.save();
    assertEquals(Specialty.find(secondSpecialty.getId()), secondSpecialty);
  }

  @Test
  public void getDoctors_retrievesAllDoctorsFromDatabase_patientsList() {
    Specialty mySpecialty = new Specialty("surgeon");
    mySpecialty.save();
    Doctor firstDoctor = new Doctor("Zack", mySpecialty.getId());
    firstDoctor.save();
    Doctor secondDoctor = new Doctor("House", mySpecialty.getId());
    secondDoctor.save();
    Doctor[] patients = new Doctor[] { firstDoctor, secondDoctor };
    assertTrue(mySpecialty.getDoctors().containsAll(Arrays.asList(patients)));
  }
}
