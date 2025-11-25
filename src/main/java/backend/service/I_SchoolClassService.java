package backend.service;

import backend.dto.schoolclass.CreateSchoolClassRequest;
import backend.entity.SchoolClass;

import java.util.List;
import java.util.Optional;

public interface I_SchoolClassService {
    List<SchoolClass> getAllClasses();

    Optional<SchoolClass> getClassById(Long id);

    SchoolClass createClass(String name, Long homeroomTeacherId);

    SchoolClass updateClass(Long id, CreateSchoolClassRequest request);

    void deleteClassById(Long id);

    Optional<SchoolClass> findClassByName(String name);

    List<SchoolClass> findClassesByTeacherId(Long teacherId);
}