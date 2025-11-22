package backend.service;

import backend.entity.SchoolClass;

import java.util.List;
import java.util.Optional;

public interface I_SchoolClassService {
    List<SchoolClass> getAllClasses();

    Optional<SchoolClass> getClassById(Long id);

    SchoolClass createClass(String name, Long homeroomTeacherId);

    SchoolClass updateClass(Long id, SchoolClass newClass);

    void deleteClassByID(Long id);

    Optional<SchoolClass> findClassByName(String name);
}