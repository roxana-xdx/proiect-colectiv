package backend.controller;
import backend.dto.SchoolClassDTO;
import backend.dto.schoolclass.CreateSchoolClassRequest;
import backend.entity.SchoolClass;
import backend.mapper.SchoolClassMapper;
import backend.service.impl.SchoolClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/classes")
public class SchoolClassController {
    @Autowired
    private SchoolClassService classService;
    @GetMapping
    public ResponseEntity<List<SchoolClassDTO>> getAllClasses() {
        List<SchoolClassDTO> dtos = SchoolClassMapper.toDTOList(classService.getAllClasses());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{ClassName}")
    public ResponseEntity<SchoolClass> getClassByClassName(@PathVariable String ClassName) {
        return classService.findClassByName(ClassName)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<?> createClass(@RequestBody CreateSchoolClassRequest request) {
        try{
            SchoolClass created = classService.createClass(request.getName(), request.getHomeroom_teacher_id());
            SchoolClassDTO dto = SchoolClassMapper.toDTO(created);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating class: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateClass (@PathVariable Long class_id, @RequestBody SchoolClass c) {
        try {
            c.setClassId(class_id);
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