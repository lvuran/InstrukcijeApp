package hr.tvz.zavrsni.service.implementation;


import hr.tvz.zavrsni.model.Lesson;
import hr.tvz.zavrsni.model.Request;
import hr.tvz.zavrsni.model.Student;
import hr.tvz.zavrsni.model.enumeration.Subject;
import hr.tvz.zavrsni.repository.LessonRepository;
import hr.tvz.zavrsni.repository.RequestRepository;
import hr.tvz.zavrsni.repository.StudentRepository;
import hr.tvz.zavrsni.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {

    private final UserServiceImpl userService;
    private final StudentRepository studentRepository;
    private final RequestRepository requestRepository;
    private final LessonRepository lessonRepository;
    public StudentServiceImpl(UserServiceImpl userService, StudentRepository studentRepository, RequestRepository requestRepository, LessonRepository lessonRepository) {
        this.userService = userService;
        this.studentRepository = studentRepository;
        this.requestRepository = requestRepository;
        this.lessonRepository = lessonRepository;
    }

    @Override
    public List<Subject> getAllSubjects() {
        List<Subject> subjects = new ArrayList<>();
        if (userService.loadCurrentUser() != null) {
            Long id = userService.loadCurrentUser().getId();
            Student student = studentRepository.getReferenceById(id);
            subjects = student.getSubjects();
        } else {
         subjects = Arrays.asList(Subject.values());

        }
        return subjects;
    }

    @Override
    public List<Request> getAllRequests() {
        List<Request> requests = new ArrayList<>();
        return requestRepository.findAll();

    }

    @Override
    public List<Request> getAllRequestsByStudent() {
        List<Request> requests = new ArrayList<>();
        requests = requestRepository.findByStudentId(userService.loadCurrentUser().getId());
        return requests;
    }

    @Override
    public List<Lesson> getAllLessonsByStudent() {
        List<Lesson> lessons = new ArrayList<>();
        lessons = lessonRepository.findByStudentId(userService.loadCurrentUser().getId());
        return lessons;
    }

    @Override
    public Optional<Student> fetchStudentById(Long studentId) {
        return studentRepository.findById(studentId);
    }
}