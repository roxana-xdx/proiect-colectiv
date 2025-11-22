package backend.service;

import backend.entity.SchoolClass;

import java.util.List;
import java.util.Optional;

public interface I_SchoolClassService {
    public List<SchoolClass> getAllClasses();

    public Optional<SchoolClass> getClassById(Long id);

    public SchoolClass createClass(String name, Long homeroomTeacherId);

    SchoolClass updateClass(Long id, SchoolClass newClass);

    public void deleteClassByID(Long id);

    Optional<SchoolClass> findClassByName(String name);
}