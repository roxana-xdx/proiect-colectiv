package backend.service;

import backend.dto.ParentDTO;
import backend.entity.Parent;

import java.util.List;
import java.util.Optional;

public interface I_ParentService {
    public List<Parent> getAllParents();
    public Optional<Parent> getParentByEmail(String email);
    public Parent createParent(String email);
    public Optional<Parent> getParentById(Long id);
    void deleteParent(Long id);
}