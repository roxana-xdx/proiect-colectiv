package backend.service.impl;

import backend.entity.Schedule;
import backend.entity.Subject;
import backend.entity.Teacher;
import backend.entity.SchoolClass;
import backend.entity.validation.ScheduleValidator; // Adaugat
import backend.repository.I_ScheduleRepository;
import backend.repository.I_SubjectRepository;
import backend.repository.I_TeacherRepository;
import backend.repository.I_SchoolClassRepository;
import backend.service.I_ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Validated
@Transactional(readOnly = true)
public class ScheduleService implements I_ScheduleService {

    private final I_ScheduleRepository scheduleRepository; // Nume corectat
    private final I_SubjectRepository subjectRepository;
    private final I_TeacherRepository teacherRepository;
    private final I_SchoolClassRepository schoolClassRepository;

    @Autowired
    public ScheduleService(I_ScheduleRepository scheduleRepository,
                           I_SubjectRepository subjectRepository,
                           I_TeacherRepository teacherRepository,
                           I_SchoolClassRepository schoolClassRepository) {
        this.scheduleRepository = scheduleRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.schoolClassRepository = schoolClassRepository;
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    @Override
    @Transactional
    public Schedule createSchedule(Long teacherId, Long subjectId, Long classId, LocalDate date, LocalTime startHour, LocalTime endHour) {
        ScheduleValidator.validateDateAndTime(date, startHour, endHour);

        ScheduleValidator.validateExistence(teacherId, subjectId, classId, teacherRepository, subjectRepository, schoolClassRepository);

        Teacher teacher = teacherRepository.getReferenceById(teacherId);
        Subject subject = subjectRepository.getReferenceById(subjectId);
        SchoolClass schoolClass = schoolClassRepository.getReferenceById(classId);

        Schedule schedule = new Schedule(teacher, subject, schoolClass, date, startHour, endHour);
        return scheduleRepository.save(schedule);
    }

    @Override
    @Transactional
    public Schedule updateSchedule(Long id, Long teacherId, Long subjectId, Long classId, LocalDate date, LocalTime startHour, LocalTime endHour) {
        Schedule existing = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Schedule not found with id: " + id));

        LocalDate newDate = date != null ? date : existing.getDate();
        LocalTime newStartHour = startHour != null ? startHour : existing.getStartHour();
        LocalTime newEndHour = endHour != null ? endHour : existing.getEndHour();

        ScheduleValidator.validateDateAndTime(newDate, newStartHour, newEndHour);

        ScheduleValidator.validateExistence(teacherId, subjectId, classId, teacherRepository, subjectRepository, schoolClassRepository);

        if (teacherId != null) {
            existing.setTeacher(teacherRepository.getReferenceById(teacherId));
        }
        if (subjectId != null) {
            existing.setSubject(subjectRepository.getReferenceById(subjectId));
        }
        if (classId != null) {
            existing.setClassEntity(schoolClassRepository.getReferenceById(classId));
        }
        if (date != null) {
            existing.setDate(date);
        }
        if (startHour != null) {
            existing.setStartHour(startHour);
        }
        if (endHour != null) {
            existing.setEndHour(endHour);
        }

        return scheduleRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new IllegalStateException("Schedule not found with ID: " + id); // Modificat la IllegalStateException (pentru 404)
        }
        scheduleRepository.deleteById(id);
    }

    @Override
    public List<Schedule> findByClassId(Long classId) {
        if (classId == null) {
            throw new IllegalArgumentException("Class ID cannot be null");
        }
        return scheduleRepository.findByClassEntity_ClassId(classId);
    }

    @Override
    public List<Schedule> findByTeacherId(Long teacherId) {
        if (teacherId == null) {
            throw new IllegalArgumentException("Teacher ID cannot be null");
        }
        return scheduleRepository.findByTeacher_Id(teacherId);
    }
}