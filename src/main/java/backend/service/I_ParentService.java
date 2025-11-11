package backend.service;

import backend.dto.ParentDTO;
import backend.entity.Parent;

import java.util.List;

public interface I_ParentService {
    public List<Parent> getAllParents();
    public Parent getParentByEmail(String email);
    public Parent createParent(Parent parent);
    public Parent updateParent(String email, Parent parent);
    public void deleteParent(String email);
}
