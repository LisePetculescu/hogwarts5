package dk.kea.dat3js.hogwarts5.students;

import dk.kea.dat3js.hogwarts5.house.House;
import dk.kea.dat3js.hogwarts5.shared.PersonWithNames;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
public class Student implements PersonWithNames {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String firstName;
  private String middleName;
  private String lastName;
  @ManyToOne
  private House house;
  private Integer schoolYear; // 1-7
  private Boolean isPrefect;
  private String gender;

  public Student() {
  }

  public Student(String firstName, String lastName, House house, int schoolYear, boolean isPrefect, String gender) {
    this(firstName, null, lastName, house, schoolYear, isPrefect, gender);
  }

  public Student(String firstName, String middleName, String lastName, House house, int schoolYear, boolean isPrefect, String gender) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.house = house;
    this.schoolYear = schoolYear;
    this.isPrefect = isPrefect;
    this.gender = gender;
  }

  public Student(String firstName, String lastName, House house, int schoolYear ) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.house = house;
    this.schoolYear = schoolYear;

  }

  public Student(String firstName, String middleName, String lastName, House house, int schoolYear) {
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.house = house;
    this.schoolYear = schoolYear;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Student student = (Student) o;
    return Objects.equals(getFirstName(), student.getFirstName()) && Objects.equals(getMiddleName(), student.getMiddleName()) && Objects.equals(getLastName(), student.getLastName()) && Objects.equals(getHouse().getName(), student.getHouse().getName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(getFirstName(), getMiddleName(), getLastName(), getHouse().getName());
  }

}
