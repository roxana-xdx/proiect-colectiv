package backend.dto;

import backend.entity.SchoolClass;

public class SchoolClassDTO {
    private Long classId;
    private String className;
    private Long homeroomTeacherId;

    public SchoolClassDTO() {
    }

    public SchoolClassDTO(Long classId, String className, Long homeroomTeacherId) {
        this.classId = classId;
        this.className = className;
        this.homeroomTeacherId = homeroomTeacherId;
    }

    public static SchoolClassDTO fromEntity(SchoolClass c) {
        if (c == null) return null;
        Long teacherId = c.getHomeroomTeacherId();
        return new SchoolClassDTO(c.getClassId(), c.getClassName(), teacherId);
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getHomeroomTeacherId() {
        return homeroomTeacherId;
    }

    public void setHomeroomTeacherId(Long homeroomTeacherId) {
        this.homeroomTeacherId = homeroomTeacherId;
    }
}