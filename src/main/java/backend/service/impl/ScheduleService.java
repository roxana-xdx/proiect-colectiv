package backend.service.impl;

import backend.Repository.I_SheduleRepo;
import backend.Repository.I_SubjectRepository;
import backend.Repository.I_TeacherRepository; // NOU: Repository pentru Teacher
import backend.Repository.I_SchoolClassRepository; // NOU: Repository pentru Class

import backend.dto.ScheduleDTO;
import backend.entity.Schedule;
import backend.entity.Subject;
import backend.entity.Teacher;
import backend.entity.SchoolClass; // Numele entitatii de clasa
import backend.mapper.ScheduleMapper;
import backend.service.I_ScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class ScheduleService implements I_ScheduleService {

    private final I_SheduleRepo ScheduleRepository;
    private final I_SubjectRepository subjectRepository;
    private final I_TeacherRepository teacherRepository; // NOU
    private final I_SchoolClassRepository schoolClassRepository; // NOU

    // Constructorul injectează toate cele 4 Repository-uri
    public ScheduleService(
            I_SheduleRepo ScheduleRepository,
            I_SubjectRepository subjectRepository,
            I_TeacherRepository teacherRepository,
            I_SchoolClassRepository schoolClassRepository) {
        this.ScheduleRepository = ScheduleRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.schoolClassRepository = schoolClassRepository;
    }

    // ===================================================================
    // METODA UTILA DE PREGĂTIRE (Transformă DTO + ID-uri în Entități)
    // ===================================================================

    /**
     * În Service, Maparea DTO la Entitate necesită căutarea tuturor Entităților relaționate
     * pe baza ID-urilor primite în DTO.
     */
    private Schedule mapDtoToEntity(ScheduleDTO dto) {
        // 1. Caută Entitatea Teacher
        Teacher teacherEntity = teacherRepository.findById(dto.getTeacher_id())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with ID: " + dto.getTeacher_id()));

        // 2. Caută Entitatea Subject
        Subject subjectEntity = subjectRepository.findById(dto.getSubject_id())
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with ID: " + dto.getSubject_id()));

        // 3. Caută Entitatea SchoolClass
        SchoolClass classEntity = schoolClassRepository.findById(dto.getClass_id())
                .orElseThrow(() -> new IllegalArgumentException("Class not found with ID: " + dto.getClass_id()));

        // 4. Folosește Mapper-ul pentru a asambla Entitatea finală
        return ScheduleMapper.toEntity(dto, teacherEntity, subjectEntity, classEntity);
    }


    // ===================================================================
    // IMPLEMENTAREA METODELOR I_ScheduleService
    // ===================================================================

    @Override
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> entities = ScheduleRepository.findAll();
        return ScheduleMapper.toDTOList(entities);
    }

    @Override
    public ScheduleDTO getScheduleById(Long id) {
        Schedule entity = ScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));
        return ScheduleMapper.toDTO(entity);
    }

    @Override
    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {
        // Validare de business: oră de început înainte de sfârșit
        if (!scheduleDTO.getStart_hour().isBefore(scheduleDTO.getEnd_hour())) {
            throw new IllegalArgumentException("Start hour must be before end hour.");
        }

        // Mapează și validează existența entităților relaționate
        Schedule scheduleEntity = mapDtoToEntity(scheduleDTO);

        Schedule savedEntity = ScheduleRepository.save(scheduleEntity);
        return ScheduleMapper.toDTO(savedEntity);
    }

    @Override
    public void updateSchedule(ScheduleDTO scheduleDTO) {
        Long id = scheduleDTO.getId();
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null for update operation.");
        }

        // Obține toate entitățile relaționate necesare pentru update
        Teacher teacherEntity = teacherRepository.findById(scheduleDTO.getTeacher_id())
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with ID: " + scheduleDTO.getTeacher_id()));
        Subject subjectEntity = subjectRepository.findById(scheduleDTO.getSubject_id())
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with ID: " + scheduleDTO.getSubject_id()));
        SchoolClass classEntity = schoolClassRepository.findById(scheduleDTO.getClass_id())
                .orElseThrow(() -> new IllegalArgumentException("Class not found with ID: " + scheduleDTO.getClass_id()));


        ScheduleRepository.findById(id)
                .map(existingEntity -> {
                    // Validare de business: oră de început înainte de sfârșit
                    if (!scheduleDTO.getStart_hour().isBefore(scheduleDTO.getEnd_hour())) {
                        throw new IllegalArgumentException("Start hour must be before end hour.");
                    }

                    // Actualizează Entitatea existentă
                    existingEntity.setTeacher(teacherEntity);
                    existingEntity.setSubject(subjectEntity);
                    existingEntity.setClassEntity(classEntity);
                    existingEntity.setDate(scheduleDTO.getDate());
                    existingEntity.setStart_hour(scheduleDTO.getStart_hour());
                    existingEntity.setEnd_hour(scheduleDTO.getEnd_hour());

                    return ScheduleRepository.save(existingEntity);
                })
                .orElseThrow(() -> new RuntimeException("Schedule not found with ID: " + id));
    }

    @Override
    public void deleteSchedule(Long id) {
        if(!ScheduleRepository.existsById(id)){
            throw new RuntimeException("Schedule not found with ID: " + id);
        }
        ScheduleRepository.deleteById(id);
    }

    @Override
    public List<ScheduleDTO> findByClassId(Long classId) {
        // Metoda aceasta va funcționa acum, deoarece se bazează pe Entitatea Class (SchoolClass)
        // care este deja legată corect prin JPA.
        List<Schedule> entities = ScheduleRepository.findByClass_id(classId);
        return ScheduleMapper.toDTOList(entities);
    }
}