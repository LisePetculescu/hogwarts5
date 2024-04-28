package dk.kea.dat3js.hogwarts5.prefects;


import dk.kea.dat3js.hogwarts5.students.Student;
import dk.kea.dat3js.hogwarts5.students.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrefectService {

    private final StudentRepository studentRepository;

    public PrefectService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public List<Student> getAllPrefects() {
        return studentRepository.findAllByPrefectTrue();
    }

    public Optional<Student> getPrefectById(int id) {

        Optional<Student> optionalPrefect = studentRepository.findById(id);
        if (optionalPrefect.isPresent() && optionalPrefect.get().isPrefect()) {
            return optionalPrefect;
        } else {
            return Optional.empty();
        }
    }

    public void addPrefect(int id) {
        Optional<Student> student = studentRepository.findById(id);
        student.ifPresent(s -> {
            s.setPrefect(true);
            studentRepository.save(s);
        });
    }

    public List<Student> getAllPrefectsInHouse(String house) {
        return studentRepository.findAllByPrefectTrueAndHouseName(house);
    }

    public void removePrefect(int id) {
        Optional<Student> student = studentRepository.findById(id);
        student.ifPresent(s -> {
            s.setPrefect(false);
            studentRepository.save(s);
        });
    }
}
