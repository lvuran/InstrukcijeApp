package hr.tvz.zavrsni.service;

import hr.tvz.zavrsni.dto.LessonDTO;
import hr.tvz.zavrsni.dto.RequestDTO;
import hr.tvz.zavrsni.dto.TeacherResponseDTO;
import hr.tvz.zavrsni.model.Lesson;
import hr.tvz.zavrsni.model.Request;
import hr.tvz.zavrsni.model.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {
    List<TeacherResponseDTO> getAllTeachers();
    Request request(RequestDTO requestDTO);
    List<Request> getAllRequests();
    public List<Request> getAllRequestsByTeacher();
    List<Lesson> getAllLessonsByTeacher();
    Optional<Teacher> fetchTeacherById(Long teacherId);
    Lesson createlesson(LessonDTO lessonDTO);

    void update(Teacher teacher);
}
