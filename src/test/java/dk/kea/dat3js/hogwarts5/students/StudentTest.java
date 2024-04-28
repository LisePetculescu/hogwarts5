package dk.kea.dat3js.hogwarts5.students;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {



    @Test
    void getFullNameWithoutMiddleName() {
        // arrange
        Student student = new Student("Harry", "Potter", null, 1);

        // act
        String fullName = student.getFullName();

        // assert
        assertEquals("Harry Potter", fullName);
    }

    @Test
    void getFullNameWithMiddleName() {
        // arrange
        Student student = new Student("Harry", "James", "Potter", null, 1);

        // act
        String fullName = student.getFullName();

        // assert
        assertEquals("Harry James Potter", fullName);
    }

    @Test
    void setFullNameWithMiddleName() {
        // arrange
        Student student = new Student("first", "middle",  "last", null, 1);

        // act
        student.setFullName("Ron Bilius Weasley");

        // assert
        assertEquals("Ron", student.getFirstName());
        assertEquals("Bilius", student.getMiddleName());
        assertEquals("Weasley", student.getLastName());
    }


    @Test
    void setFullNameWithoutMiddleName() {
        // arrange
        Student student = new Student("first",  "last", null, 1);

        // act
        student.setFullName("Ron Weasley");

        // assert
        assertEquals("Ron", student.getFirstName());
        assertEquals("Weasley", student.getLastName());
    }

    @Test
    void setFullNameWithMiddleNameNull() {
        // arrange
        Student student = new Student("first", null, "last", null, 1);

        // act
        student.setFullName("Ron Weasley");

        // assert
        assertEquals("Ron", student.getFirstName());
        assertNull(student.getMiddleName());
        assertEquals("Weasley", student.getLastName());
    }

@Test
    void setFullNameWithoutLastName() {
        // arrange
        Student student = new Student("first",  "last", null, 1);

        // act
        student.setFullName("Ron");

        // assert
        assertEquals("Ron", student.getFirstName());
        assertNull(student.getLastName());
    }

    @Test
    void setFullNameWithMultipleMiddleNames() {
        // arrange
        Student student = new Student("first", "middle", "last", null, 1);

        // act
        student.setFullName("Albus Percival Wulfric Brian Dumbledore");

        // assert
        assertEquals("Albus", student.getFirstName());
        assertEquals("Percival Wulfric Brian", student.getMiddleName());
        assertEquals("Dumbledore", student.getLastName());
    }

    @Test
    void setFullNameWithEmptyString() {
        // arrange
        Student student = new Student("first", "middle", "last", null, 1);

        // act
        student.setFullName("");

        // assert
        assertEquals("first", student.getFirstName());
        assertEquals("middle", student.getMiddleName());
        assertEquals("last", student.getLastName());
    }

    @Test
    void setFullNameWithNull() {
        // arrange
        Student student = new Student("first", "middle", "last", null, 1);

        // act
        student.setFullName(null);

        // assert
        assertEquals("first", student.getFirstName());
        assertEquals("middle", student.getMiddleName());
        assertEquals("last", student.getLastName());

    }

//    @Test
//    void updatePrefectMaxTwoPersons() {
//        // arrange
//        Student student1 = new Student("harr", "jam", "pott", gryffindor, 1, true, "male");
//        Student student2 = new Student("ron", "bil", "weas", gryffindor, 1, true, "male");
//        Student student3 = new Student("herm", "jean", "grang", gryffindor, 1, true, "female");
//
//
//
//        // act
//        student1.setIsPrefect(true);
//        student2.setIsPrefect(true);
//        student3.setIsPrefect(true);
//
//
//        // assert
//        assertTrue(student1.getIsPrefect());
//        assertTrue(student2.getIsPrefect());
//        assertFalse(student3.getIsPrefect());
//
//
//    }



}