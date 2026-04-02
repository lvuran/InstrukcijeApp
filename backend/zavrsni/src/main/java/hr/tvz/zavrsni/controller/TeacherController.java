package hr.tvz.zavrsni.controller;



import hr.tvz.zavrsni.dto.*;
import hr.tvz.zavrsni.model.AppUser;
import hr.tvz.zavrsni.model.Lesson;
import hr.tvz.zavrsni.model.Request;
import hr.tvz.zavrsni.model.Teacher;
import hr.tvz.zavrsni.service.LessonService;
import hr.tvz.zavrsni.service.RequestService;
import hr.tvz.zavrsni.service.TeacherService;
import hr.tvz.zavrsni.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    private final TeacherService teacherService;
    private final RequestService requestService;
    private final UserService userService;
    private final LessonService lessonService;
    public TeacherController(TeacherService teacherService, RequestService requestService, UserService userService, LessonService lessonService) {
        this.teacherService = teacherService;
        this.requestService = requestService;
        this.userService = userService;
        this.lessonService = lessonService;
    }


    @GetMapping("/all")
    public ResponseEntity<List<TeacherResponseDTO>> all(){
        return

                ResponseEntity.ok(teacherService.getAllTeachers());
    }


    @PostMapping("/request")
    @Secured({"ROLE_ADMIN", "ROLE_STUDENT"})
    public ResponseEntity<Request>register(@RequestBody RequestDTO requestDTO){
        return ResponseEntity.ok(teacherService.request(requestDTO));
    }

    @GetMapping("/allrequests")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<List<StudentRequestDTO>> allrequests(){
        List<Request> requests = teacherService.getAllRequestsByTeacher();
        List<StudentRequestDTO> dtos = requests.stream()
                .map(r -> new StudentRequestDTO(
                        r.getId(),
                        r.getSubject(),
                        r.getDescription(),
                        r.getStudent().getId(),
                        r.getStudent().getUser().getUsername(),
                        r.getActive(),
                        r.getStudent().getGrade()
                ))
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PatchMapping("/request/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
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


    @GetMapping("/user")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<TeacherResponseDTO> teacherInfo(){
        AppUser user = userService.loadCurrentUser();
        Optional<Teacher> teacher = teacherService.fetchTeacherById(user.getId());
        if (teacher.isPresent()){
       TeacherResponseDTO dto = new TeacherResponseDTO(
               teacher.get().getId(),
               teacher.get().getUser().getUsername(),
               teacher.get().getAbout(),
               teacher.get().getSubjects(),
               teacher.get().getDuration(),
               teacher.get().getPrice(),
               teacher.get().getType(),
               teacher.get().getOcjena(),
               teacher.get().getUser().getAddress(),
               teacher.get().getUser().getPhoneNum(),
               teacher.get().getUser().getEmail(),
               teacher.get().getUser().getLocation(),
                teacher.get().getRatings());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/lesson")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<Lesson> createLesson(@RequestBody LessonDTO lessonDTO){
        System.out.println(lessonDTO.getDateTime());
        return ResponseEntity.ok(teacherService.createlesson(lessonDTO));
    }

    @GetMapping("/alllessons")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<List<LessonResponseDTO>> getLessons(){
        AppUser user = userService.loadCurrentUser();
        List<Lesson> lessons = teacherService.getAllLessonsByTeacher();
        List<LessonResponseDTO> dtos = lessons.stream().filter(r -> r.getActive())
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
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
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



    @PatchMapping("/info")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<String>setInfo(@RequestBody InfoDTO dto){

        Optional<Lesson> existingLesson = lessonService.fetchLessonById(dto.getId());
        if(existingLesson.isPresent()){
            Lesson lesson = existingLesson.get();
            lesson.setInfo(dto.getInfo());
            lesson.setActive(false);
            lesson.setSeen(false);
            lessonService.update(lesson);
            System.out.println("deaktivirano informacije spremljene");
            return ResponseEntity.ok("Request deactivated successfully info sent");
        }

        return ResponseEntity.notFound().build();
    }

}
