package backend.service;

import backend.dto.ParentDTO;
import backend.entity.Parent;

import java.util.List;

public interface I_ParentService {
    public List<Parent> getAllPupils();
    public ParentDTO getParentById(Long id);
    public ParentDTO createParent(ParentDTO parentDTO);
    public void updateParent(ParentDTO parentDTO);
    public void deleteParent(Long id);

    void deleteParent(String email);

    ParentDTO findParentByEmail(String email);
}
