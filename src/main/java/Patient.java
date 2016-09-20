import java.time.LocalDateTime;

public class Patient {
  private String name;
  private LocalDateTime birthDate;

  public Patient (String name, LocalDateTime birthDate) {
    this.name = name;
    this.birthDate = birthDate;
  }

  public String getName () {
    return name;
  }

  public LocalDateTime getBirthDate () {
    return birthDate;
  }
}
