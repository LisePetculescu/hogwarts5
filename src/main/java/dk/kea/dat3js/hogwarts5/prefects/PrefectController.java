package dk.kea.dat3js.hogwarts5.prefects;


import dk.kea.dat3js.hogwarts5.students.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prefects")
public class PrefectController {

    private final PrefectService prefectService;

    public PrefectController(PrefectService prefectService) {
        this.prefectService = prefectService;
    }

    @GetMapping
    public List<Student> getAllPrefects() {
        return prefectService.getAllPrefects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getPrefectById(@PathVariable int id) {
        return ResponseEntity.of(prefectService.getPrefectById(id));
    }

    @GetMapping("/house/{house}")
    public List<Student> getAllPrefectsInHouse(@PathVariable String house) {
        return prefectService.getAllPrefectsInHouse(house);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Student> addPrefect(@PathVariable int id) {
        Student student = prefectService.addPrefect(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
       return ResponseEntity.status(HttpStatus.CREATED).body(student);

    }

    @DeleteMapping("/{id}")
    public void removePrefect(@PathVariable int id) {
        prefectService.removePrefect(id);
    }



}
