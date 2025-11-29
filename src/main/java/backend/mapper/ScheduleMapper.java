package backend.mapper;

import backend.dto.ScheduleDTO;
import backend.entity.Schedule;
import java.util.List;
import java.util.stream.Collectors;

public final class ScheduleMapper {
    private ScheduleMapper() {}

    public static ScheduleDTO toDTO(Schedule schedule) {
        return ScheduleDTO.toDTO(schedule);
    }

    public static Schedule toEntity(ScheduleDTO scheduleDTO) {
        if(scheduleDTO == null) return null;
        return scheduleDTO.toEntity();
    }

    public static List<ScheduleDTO> toDTOList(List<Schedule> scheduleList) {
        return scheduleList.stream().map(ScheduleMapper::toDTO).collect(Collectors.toList());
    }

    public static List<Schedule> toEntityList(List<ScheduleDTO> scheduleDTOList) {
        return scheduleDTOList.stream().map(ScheduleMapper::toEntity).collect(Collectors.toList());
    }
}