import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.Arrays;

public class DoctorTest {
  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/doctors_office_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deletePatientsQuery = "DELETE FROM patients *;";
      String deleteDoctorsQuery = "DELETE FROM doctors *;";
      String deleteSpecialtysQuery = "DELETE FROM specialties *;";
      con.createQuery(deletePatientsQuery).executeUpdate();
      con.createQuery(deleteDoctorsQuery).executeUpdate();
    }
  }

  @Test
  public void getId_doctorsInstantiateWithAnId_1() {
    Specialty mySpecialty = new Specialty("surgery");
    mySpecialty.save();
    Doctor testDoctor = new Doctor("House", mySpecialty.getId());
    testDoctor.save();
    assertTrue(testDoctor.getId() > 0);
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Specialty mySpecialty = new Specialty("surgery");
    mySpecialty.save();
    Doctor myDoctor = new Doctor("House", mySpecialty.getId());
    myDoctor.save();
    assertTrue(Doctor.getAll().get(0).equals(myDoctor));
  }

  @Test
  public void save_assignsIdToObject() {
    Specialty mySpecialty = new Specialty("surgery");
    mySpecialty.save();
    Doctor myDoctor = new Doctor("House", mySpecialty.getId());
    myDoctor.save();
    Doctor savedDoctor = Doctor.getAll().get(0);
    assertEquals(myDoctor.getId(), savedDoctor.getId());
  }

  @Test
  public void find_returnsDoctorWithSameId_secondDoctor() {
    Specialty mySpecialty = new Specialty("surgery");
    mySpecialty.save();
    Doctor firstDoctor = new Doctor("Strangelove", mySpecialty.getId());
    firstDoctor.save();
    Doctor secondDoctor = new Doctor("Livingstone", mySpecialty.getId());
    secondDoctor.save();
    assertEquals(Doctor.find(secondDoctor.getId()), secondDoctor);
  }

  @Test
  public void getPatients_retrievesAllPatientsFromDatabase_patientsList() {
    Specialty mySpecialty = new Specialty("surgery");
    mySpecialty.save();
    Doctor myDoctor = new Doctor("Strangelove", mySpecialty.getId());
    myDoctor.save();
    Patient firstPatient = new Patient("Zack", "09/19/1987", myDoctor.getId());
    firstPatient.save();
    Patient secondPatient = new Patient("House", "1/1/2016", myDoctor.getId());
    secondPatient.save();
    Patient[] patients = new Patient[] { firstPatient, secondPatient };
    assertTrue(myDoctor.getPatients().containsAll(Arrays.asList(patients)));
  }
}
