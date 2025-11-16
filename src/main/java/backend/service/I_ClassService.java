package backend.service;

import backend.entity.Clasa;

import java.util.List;
import java.util.Optional;

public interface I_ClassService {
    public List<Clasa> getAllClasses();

    public Optional<Clasa> getClassById(Long id);

    public Clasa createClass(String name, Long homeroomTeacherId);

    Clasa updateClass(Long id, Clasa newClass);

    public void deleteClassByID(Long id);

    Optional<Clasa> findClassByName(String name);
}
