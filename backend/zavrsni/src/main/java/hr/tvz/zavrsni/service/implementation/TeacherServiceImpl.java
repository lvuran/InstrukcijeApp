package hr.tvz.zavrsni.service.implementation;

import hr.tvz.zavrsni.dto.LessonDTO;
import hr.tvz.zavrsni.dto.RequestDTO;
import hr.tvz.zavrsni.dto.TeacherResponseDTO;
import hr.tvz.zavrsni.model.*;
import hr.tvz.zavrsni.repository.LessonRepository;
import hr.tvz.zavrsni.repository.RequestRepository;
import hr.tvz.zavrsni.repository.StudentRepository;
import hr.tvz.zavrsni.repository.TeacherRepository;
import hr.tvz.zavrsni.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final UserServiceImpl userService;
    private final TeacherRepository teacherRepository;
    private final RequestRepository requestRepository;
    private final StudentRepository studentRepository;
    private final LessonRepository lessonRepository;

    public TeacherServiceImpl(UserServiceImpl userService, TeacherRepository teacherRepository, RequestRepository requestRepository, StudentRepository studentRepository, LessonRepository lessonRepository) {
        this.userService = userService;
        this.teacherRepository = teacherRepository;
        this.requestRepository = requestRepository;
        this.studentRepository = studentRepository;
        this.lessonRepository = lessonRepository;
    }


    @Override
    public List<TeacherResponseDTO> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        List<TeacherResponseDTO> dtos = new ArrayList<>();
        if(userService.loadCurrentUser() != null) {
            Location location = userService.loadCurrentUser().getLocation();
           teachers = teachers.stream()
                    .sorted(Comparator.comparingDouble(
                            t -> distance(location, t.getUser().getLocation())
                    ))
                    .toList();
        }
        dtos = teachers.stream()
                .map(t -> new TeacherResponseDTO(
                        t.getId(),
                        t.getUser().getUsername(),
                        t.getAbout(),
                        t.getSubjects(),
                        t.getDuration(),
                        t.getPrice(),
                        t.getType(),
                        t.getOcjena(),
                        t.getUser().getAddress(),
                        t.getUser().getPhoneNum(),
                        t.getUser().getEmail(),
                        t.getUser().getLocation(),
                        t.getRatings()
                ))
                .toList();

        return dtos;

    }

    @Override
    public Request request(RequestDTO requestDTO) {
        Request request = new Request();
        Teacher teacher = teacherRepository.findById(requestDTO.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        request.setTeacher(teacher);
        request.setDescription(requestDTO.getDescription());
        request.setSubject(requestDTO.getSubject());
        AppUser user = userService.loadCurrentUser();
        request.setActive(true);
        Student student = studentRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        request.setStudent(student);
        System.out.println("dto");
        System.out.println(requestDTO.getDescription());
        System.out.println("request");
        System.out.println(request.getDescription());

        return requestRepository.save(request);
    }

    @Override
    public List<Request> getAllRequests() {
        List<Request> requests = new ArrayList<>();
        requests = requestRepository.findAll();
        System.out.println(requests.getFirst().getDescription());
            return requestRepository.findAll();


    }

    @Override
    public List<Request> getAllRequestsByTeacher(){
        List<Request> requests = new ArrayList<>();
        requests = requestRepository.findByTeacherId(userService.loadCurrentUser().getId());
        return requests;
    }

    @Override
    public List<Lesson> getAllLessonsByTeacher() {
        List<Lesson> lessons = new ArrayList<>();
        lessons = lessonRepository.findByTeacherId(userService.loadCurrentUser().getId());
        return lessons;
    }

    @Override
    public Optional<Teacher> fetchTeacherById(Long teacherId) {
        return teacherRepository.findById(teacherId);
    }

    @Override
    public Lesson createlesson(LessonDTO lessonDTO) {
        Lesson lesson = new Lesson();
        AppUser appUser= userService.loadCurrentUser();
        Optional<Teacher> teacher = teacherRepository.findById(appUser.getId());
        Optional<Student> student = studentRepository.findById(lessonDTO.getStudentId());
        if (teacher.isPresent() && student.isPresent()) {
            lesson.setTeacher(teacher.get());
            lesson.setStudent(student.get());
            lesson.setActive(true);
            lesson.setDescription(lessonDTO.getDescription());
            lesson.setDateTime(lessonDTO.getDateTime());
            lesson.setDuration(teacher.get().getDuration());
            lesson.setInfo(null);
            lesson.setLocation(teacher.get().getUser().getAddress());
            lesson.setPrice(teacher.get().getPrice());
            lesson.setSubject(lessonDTO.getSubject());
            lesson.setType(lessonDTO.getType());

        }
        return lessonRepository.save(lesson);
    }

    @Override
    public void update(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    private double distance(Location a, Location b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }


}
