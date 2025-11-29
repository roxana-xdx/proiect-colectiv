package backend.service.impl;

import backend.repository.I_SubjectRepository;
import backend.repository.I_TeacherRepository; // NOU: Repository pentru Teacher
import backend.repository.I_SchoolClassRepository; // NOU: Repository pentru Class

import backend.entity.Schedule;
import backend.entity.Subject;
import backend.entity.Teacher;
import backend.entity.SchoolClass; // Numele entitatii de clasa
import backend.service.I_ScheduleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ScheduleService implements I_ScheduleService {
    private final backend.repository.I_SheduleRepository scheduleRepository;
    private final I_SubjectRepository subjectRepository;
    private final I_TeacherRepository teacherRepository;
    private final I_SchoolClassRepository schoolClassRepository;

    @Autowired
    public ScheduleService(backend.repository.I_SheduleRepository scheduleRepository,
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
        // Validare ore
        if (!startHour.isBefore(endHour)) {
            throw new IllegalArgumentException("Start hour must be before end hour.");
        }

        // Găsire entități relaționate
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found with id: " + teacherId));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with id: " + subjectId));
        SchoolClass schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new IllegalArgumentException("Class not found with id: " + classId));

        // Creare schedule
        Schedule schedule = new Schedule(teacher, subject, schoolClass, date, startHour, endHour);
        return scheduleRepository.save(schedule);
    }

    @Override
    @Transactional
    public Schedule updateSchedule(Long id, Long teacherId, Long subjectId, Long classId, LocalDate date, LocalTime startHour, LocalTime endHour) {
        Schedule existing = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found with id: " + id));

        // Validare ore dacă sunt furnizate
        if (startHour != null && endHour != null && !startHour.isBefore(endHour)) {
            throw new IllegalArgumentException("Start hour must be before end hour.");
        }

        // Actualizare teacher dacă este furnizat
        if (teacherId != null) {
            Teacher teacher = teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new IllegalArgumentException("Teacher not found with id: " + teacherId));
            existing.setTeacher(teacher);
        }

        // Actualizare subject dacă este furnizat
        if (subjectId != null) {
            Subject subject = subjectRepository.findById(subjectId)
                    .orElseThrow(() -> new IllegalArgumentException("Subject not found with id: " + subjectId));
            existing.setSubject(subject);
        }

        // Actualizare class dacă este furnizat
        if (classId != null) {
            SchoolClass schoolClass = schoolClassRepository.findById(classId)
                    .orElseThrow(() -> new IllegalArgumentException("Class not found with id: " + classId));
            existing.setClassEntity(schoolClass);
        }

        // Actualizare date dacă este furnizat
        if (date != null) {
            existing.setDate(date);
        }

        // Actualizare start hour dacă este furnizat
        if (startHour != null) {
            existing.setStart_hour(startHour);
        }

        // Actualizare end hour dacă este furnizat
        if (endHour != null) {
            existing.setEnd_hour(endHour);
        }

        return scheduleRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new RuntimeException("Schedule not found with ID: " + id);
        }
        scheduleRepository.deleteById(id);
    }

    @Override
    public Optional<Schedule> findScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    @Override
    public List<Schedule> findByClassId(Long classId) {
        return scheduleRepository.findByClassEntity_ClassId(classId);
    }


}