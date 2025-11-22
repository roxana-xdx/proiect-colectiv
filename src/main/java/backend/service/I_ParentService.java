package backend.service;

import backend.entity.Parent;

import java.util.List;
import java.util.Optional;

public interface I_ParentService {
    List<Parent> getAllParents();
    Optional<Parent> getParentById(Long id);
    Optional<Parent> getParentByEmail(String email);
    Parent createParent(String email);
    void deleteParent(Long id);
}