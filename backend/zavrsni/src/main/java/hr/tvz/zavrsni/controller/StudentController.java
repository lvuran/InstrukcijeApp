package hr.tvz.zavrsni.controller;


import hr.tvz.zavrsni.dto.LessonResponseDTO;
import hr.tvz.zavrsni.dto.StudentRequestDTO;
import hr.tvz.zavrsni.model.AppUser;
import hr.tvz.zavrsni.model.Lesson;
import hr.tvz.zavrsni.model.Request;
import hr.tvz.zavrsni.model.Teacher;
import hr.tvz.zavrsni.model.enumeration.Subject;

import hr.tvz.zavrsni.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final RequestService requestService;
    private final UserService userService;
    private final LessonService lessonService;
    private final TeacherService teacherService;
    public StudentController(StudentService studentService, RequestService requestService, UserService userService, LessonService lessonService, TeacherService teacherService) {
        this.studentService = studentService;
        this.requestService = requestService;
        this.userService = userService;
        this.lessonService = lessonService;
        this.teacherService = teacherService;
    }

    @GetMapping("/subjects")
    public ResponseEntity<List<Subject>> all(){

        System.out.println(studentService.getAllSubjects());
        return ResponseEntity.ok(studentService.getAllSubjects());
    }


    @GetMapping("/allrequests")
    @Secured({"ROLE_ADMIN", "ROLE_STUDENT"})
    public ResponseEntity<List<StudentRequestDTO>> allrequests(){
        List<Request> requests = studentService.getAllRequestsByStudent();
        List<StudentRequestDTO> dtos = requests.stream()
                .map(r -> new StudentRequestDTO(
                        r.getId(),
                        r.getSubject(),
                        r.getDescription(),
                        r.getTeacher().getId(),
                        r.getTeacher().getUser().getUsername(),
                        r.getActive(),
                        r.getStudent().getGrade()
                ))
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PatchMapping("/request/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_STUDENT"})
    public ResponseEntity<String>deactiate(@PathVariable("id") Long id){

        Optional<Request> existingRequest = requestService.fetchRequestById(id);
        if(existingRequest.isPresent()){
            Request request = existingRequest.get();
            request.setActive(false);
            requestService.update(request);
            System.out.println("deaktivirano");
            return ResponseEntity.ok("Request deactivated successfully");
        }

        return ResponseEntity.notFound().build();
    }



    @GetMapping("/alllessons")
    @Secured({"ROLE_ADMIN", "ROLE_STUDENT"})
    public ResponseEntity<List<LessonResponseDTO>> getLessons(){
        AppUser user = userService.loadCurrentUser();
        List<Lesson> lessons = studentService.getAllLessonsByStudent();
        List<LessonResponseDTO> dtos = lessons.stream().filter(r -> r.getActive()|| r.getInfo() != null)
                .map(r -> new LessonResponseDTO(
                        r.getId(),
                        r.getStudent().getId(),
                        r.getTeacher().getId(),
                        r.getTeacher().getUser().getUsername(),
                        r.getStudent().getUser().getUsername(),
                        r.getDateTime(),
                        r.getSubject(),
                        r.getType(),
                        r.getDescription(),
                        r.getDuration(),
                        r.getPrice(),
                        r.getInfo(),
                        r.getSeen(),
                        r.getActive(),
                        r.getLocation(),
                        r.getStudent().getGrade()
                ))
                .toList();
        return ResponseEntity.ok(dtos);
    }



    @PatchMapping("/lesson/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_STUDENT"})
    public ResponseEntity<String>deactiateLesson(@PathVariable("id") Long id){

        Optional<Lesson> existingLesson = lessonService.fetchLessonById(id);
        if(existingLesson.isPresent()){
            Lesson lesson = existingLesson.get();
            lesson.setActive(false);
            lessonService.update(lesson);
            System.out.println("deaktivirano");

            return ResponseEntity.ok("Lesson deactivated successfully");
        }

        return ResponseEntity.notFound().build();
    }



    @PatchMapping("/info/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_STUDENT"})
    public ResponseEntity<String>Seen(@PathVariable("id") Long id){

        Optional<Lesson> existingLesson = lessonService.fetchLessonById(id);
        if(existingLesson.isPresent()){
            Lesson lesson = existingLesson.get();
            lesson.setSeen(true);
            lesson.setActive(false);
            lessonService.update(lesson);
            System.out.println("Viđeno");

            return ResponseEntity.ok("Info seen successfully");
        }

        return ResponseEntity.notFound().build();
    }


    @PatchMapping("/rate/{id}/{rating}")
    @Secured({"ROLE_ADMIN", "ROLE_STUDENT"})
    public ResponseEntity<String>rate(@PathVariable("id") Long id, @PathVariable("rating") Integer rating){

        Optional<Teacher> existingTeacher = teacherService.fetchTeacherById(id);
        if(existingTeacher.isPresent()){
            Teacher teacher = existingTeacher.get();
            teacher.rate(rating);
            teacherService.update(teacher);
            System.out.println("ocjenjeno");

            return ResponseEntity.ok("Rating posted successfully");
        }

        return ResponseEntity.notFound().build();
    }

}
