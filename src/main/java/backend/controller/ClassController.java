package backend.controller;
import backend.entity.Clasa;
import backend.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/classes")
public class ClassController {
    @Autowired
    private ClassService classService;
    @GetMapping
    public List<Clasa> getAllClasses() {
        return classService.getAllClasses();
    }

    @GetMapping("/{ClassName}")
    public ResponseEntity<Clasa> getClassByClassName(@PathVariable String ClassName) {
        return classService.findClassByName(ClassName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateClass (@PathVariable Long class_id, @RequestBody Clasa c) {
        try {
            c.setId(class_id);
            classService.updateClass(class_id, c);
            return ResponseEntity.ok("Class updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{class_id}")
    public ResponseEntity<String> deleteClass(@PathVariable Long class_id) {
        try {
            classService.deleteClassByID(class_id);
            return ResponseEntity.ok("Class deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
