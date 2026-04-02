package hr.tvz.zavrsni.service;

import hr.tvz.zavrsni.model.Lesson;
import hr.tvz.zavrsni.model.Request;
import hr.tvz.zavrsni.model.Student;
import hr.tvz.zavrsni.model.enumeration.Subject;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<Subject> getAllSubjects();

    List<Request> getAllRequests();
    List<Request> getAllRequestsByStudent();
    List<Lesson> getAllLessonsByStudent();
    Optional<Student> fetchStudentById(Long studentId);

}
