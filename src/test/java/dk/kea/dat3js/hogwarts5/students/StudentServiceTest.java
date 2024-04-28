package dk.kea.dat3js.hogwarts5.students;

import dk.kea.dat3js.hogwarts5.house.House;
import dk.kea.dat3js.hogwarts5.house.HouseRepository;
import dk.kea.dat3js.hogwarts5.house.HouseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(StudentService.class)
class StudentServiceTest {

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private HouseService houseService;

    @MockBean
    private HouseRepository houseRepository;

    List<Student> students = new ArrayList<>();

    @BeforeEach
    void setUp() {
        if (!students.isEmpty()) {
            students.clear();
        }
        makeMockHouses();

        when(studentRepository.findById(anyInt())).thenAnswer(i -> {
            int id = i.getArgument(0);
            return students.stream().filter(s -> s.getId() == id).findFirst();
        });

        when(studentRepository.findAllByIsPrefectTrueAndHouseName(any())).thenAnswer(i -> {
            String houseName = i.getArgument(0);
            return students.stream().filter(s -> s.getHouse().getName().equals(houseName) && s.isPrefect()).toList();

        });

        when(studentRepository.save(any(Student.class))).thenAnswer(i -> i.getArgument(0));

//        when(studentRepository.findById(1)).thenReturn(Optional.of(new Student(1,"Harry", "James", "Potter",  houseRepository.findById("Gryffindor").get(), 5,true, "male")));
//        when(studentRepository.findById(2)).thenReturn(Optional.of(new Student(2,"Hermione", "Jean", "Granger",  houseRepository.findById("Gryffindor").get(), 5, true, "female")));
//        when(studentRepository.findById(3)).thenReturn(Optional.of(new Student(3,"Ron", "Bilius", "Weasley",  houseRepository.findById("Gryffindor").get(), 5, false, "male")));
//        when(studentRepository.findById(4)).thenReturn(Optional.of(new Student(4,"Draco", "Lucius", "Malfoy",  houseRepository.findById("Slytherin").get(), 5, false, "male")));
//        when(studentRepository.findById(5)).thenReturn(Optional.of(new Student(5,"Luna", "", "Lovegood", houseRepository.findById("Hufflepuff").get(), 5, true, "Female")));
//
//        when(studentRepository.findAllByIsPrefectTrueAndHouseName(studentToUpdate.getHouse().getName()));
//        when(studentRepository.findAllByIsPrefectTrueAndHouseName("Gryffindor")).thenReturn(List.of(students.get(0)));
//        when(studentRepository.findAllByIsPrefectTrueAndHouseName("Hufflepuff")).thenReturn(List.of(students.get(1)));
//        when(studentRepository.findAllByIsPrefectTrueAndHouseName("Slytherin")).thenReturn(List.of(students.get(2)));
//        when(studentRepository.findAllByIsPrefectTrueAndHouseName("Ravenclaw")).thenReturn(List.of(students.get(3)));




    }

    public void makeMockHouses() {
        when(houseRepository.findById("Gryffindor")).thenReturn(Optional.of(new House("Gryffindor", "Godric Gryffindor", new String[]{"red", "gold"})));
        when(houseRepository.findById("Hufflepuff")).thenReturn(Optional.of(new House("Hufflepuff", "Helga Hufflepuff", new String[]{"yellow", "black"})));
        when(houseRepository.findById("Ravenclaw")).thenReturn(Optional.of(new House("Ravenclaw", "Rowena Ravenclaw", new String[]{"blue", "silver"})));
        when(houseRepository.findById("Slytherin")).thenReturn(Optional.of(new House("Slytherin", "Salazar Slytherin", new String[]{"green", "silver"})));
    }



    @Test
    void updatePrefectTogglePrefectStatusToFalse() {

        // arrange
        StudentService studentService = new StudentService(studentRepository, houseService);
        students.add(new Student(1, "Harry", "James", "Potter", houseRepository.findById("Gryffindor").get(), 5, true, "male"));

        // act
        Optional<StudentResponseDTO> result = studentService.updatePrefect(1);

        // assert
        assertTrue(result.isPresent());
        assertFalse(result.get().isPrefect());
    }

    @Test
    void updatePrefectTogglePrefectStatusToTrueOnlyOneStudent() {

        // arrange
        StudentService studentService = new StudentService(studentRepository, houseService);
        students.add(new Student(1, "Harry", "James", "Potter", houseRepository.findById("Gryffindor").get(), 5, false, "male"));

        // act
        Optional<StudentResponseDTO> result = studentService.updatePrefect(1);

        // assert
        assertTrue(result.isPresent());
        assertTrue(result.get().isPrefect());

    }

    @Test
    void updatePrefectTogglePrefectStatusToTrueOnlyOneStudentWrongSchoolYear() {

        // arrange
        StudentService studentService = new StudentService(studentRepository, houseService);
        Student harry = new Student(1, "Harry", "James", "Potter", houseRepository.findById("Gryffindor").get(), 4, false, "male");
        students.add(harry);

        // act
        Optional<StudentResponseDTO> result = studentService.updatePrefect(1);

        // assert
        assertFalse(result.isPresent());
        assertFalse(harry.isPrefect());

    }

    @Test
    void updatePrefectTogglePrefectStatusToTrueTwoPrefectsSameGender() {

        // arrange
        StudentService studentService = new StudentService(studentRepository, houseService);
        Student harry = new Student(1, "Harry", "James", "Potter", houseRepository.findById("Gryffindor").get(), 5, true, "male");
        students.add(harry);
        Student ron = new Student(2, "Ron", "Bilius", "Weasley", houseRepository.findById("Gryffindor").get(), 5, false, "male");
        students.add(ron);

        // act
        Optional<StudentResponseDTO> result2 = studentService.updatePrefect(2);

        // assert
        assertFalse(result2.isPresent());
        assertFalse(ron.isPrefect());
    }

    @Test
    void updatePrefectTogglePrefectStatusToTrueTwoPrefectsNotSameGender() {

        // arrange
        StudentService studentService = new StudentService(studentRepository, houseService);
        Student harry = new Student(1, "Harry", "James", "Potter", houseRepository.findById("Gryffindor").get(), 5, true, "male");
        students.add(harry);
        Student hermione = new Student(2, "Hermione", "Jean", "Granger", houseRepository.findById("Gryffindor").get(), 5, false, "female");
        students.add(hermione);

        // act
        Optional<StudentResponseDTO> result = studentService.updatePrefect(2);

        // assert
        assertTrue(result.isPresent());
        assertTrue(hermione.isPrefect());

    }

    @Test
    void updatePrefectTogglePrefectStatusToTrueThreePrefectsNotSameGender() {

        // arrange
        StudentService studentService = new StudentService(studentRepository, houseService);
        Student harry = new Student(1, "Harry", "James", "Potter", houseRepository.findById("Gryffindor").get(), 5, true, "male");
        students.add(harry);
        Student hermione = new Student(2, "Hermione", "Jean", "Granger", houseRepository.findById("Gryffindor").get(), 5, true, "female");
        students.add(hermione);
        Student petl = new Student(3, "Peter", "Heronimous", "Lind", houseRepository.findById("Gryffindor").get(), 7, false, "non-binary");
        students.add(petl);

        // act
        Optional<StudentResponseDTO> result = studentService.updatePrefect(3);

        // assert
        assertFalse(result.isPresent());
        assertFalse(petl.isPrefect());

    }

    @Test
    void updatePrefectTogglePrefectStatusToTrueThreePrefectsDifferentHouses() {

        // arrange
        StudentService studentService = new StudentService(studentRepository, houseService);
        Student harry = new Student(1, "Harry", "James", "Potter", houseRepository.findById("Gryffindor").get(), 5, true, "male");
        students.add(harry);
        Student hermione = new Student(2, "Hermione", "Jean", "Granger", houseRepository.findById("Gryffindor").get(), 5, true, "female");
        students.add(hermione);
        Student luna = new Student(3, "Luna", "", "Lovegood", houseRepository.findById("Hufflepuff").get(), 5, false, "female");
        students.add(luna);

        // act
        Optional<StudentResponseDTO> result = studentService.updatePrefect(3);

        // assert
        assertTrue(result.isPresent());
        assertTrue(luna.isPrefect());

    }

}