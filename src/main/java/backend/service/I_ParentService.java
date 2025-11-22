package backend.service;

import backend.entity.Parent;

import java.util.List;
import java.util.Optional;

public interface I_ParentService {
    public List<Parent> getAllParents();
    public Optional<Parent> getParentById(Long id);
    public Optional<Parent> getParentByEmail(String email);
    public Parent createParent(String email);
    //    public Parent updateParent(String email, Parent parent);
    void deleteParent(Long id);
}