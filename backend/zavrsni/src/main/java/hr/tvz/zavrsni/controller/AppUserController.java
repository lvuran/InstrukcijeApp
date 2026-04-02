package hr.tvz.zavrsni.controller;

import hr.tvz.zavrsni.dto.RoleDTO;
import hr.tvz.zavrsni.dto.StudentDTO;
import hr.tvz.zavrsni.dto.TeacherDTO;
import hr.tvz.zavrsni.model.AppUser;
import hr.tvz.zavrsni.model.Student;
import hr.tvz.zavrsni.model.Teacher;
import hr.tvz.zavrsni.repository.exception.InputIsNullException;
import hr.tvz.zavrsni.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class AppUserController {

    private final UserService userService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Authentication authentication;

    public AppUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/current-role")
    public ResponseEntity<String> getCurrentRole() {
        AppUser user = userService.loadCurrentUser();
        if (user == null) {
            return ResponseEntity.ok("NOTLOGGED");
        }
        return ResponseEntity.ok(user.getRole().name());
    }

    @GetMapping("/id/{userId}")
    @Secured("ROLE_ADMIN")
    public AppUser getUserById(@PathVariable Long userId)
    {
        if(userService.fetchUserById(userId).isEmpty())
            throw new InputIsNullException("Korisnik ne postoji.");
        
        return userService.fetchUserById(userId).orElseThrow();
    }
    
    @GetMapping("/email/{userEmail}")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public AppUser getUserByEmail(@PathVariable String userEmail){
        if(userService.fetchUserByEmail(userEmail) == null){
            throw new InputIsNullException("Korisnik ne postoji.");
        }
        return userService.fetchUserByEmail(userEmail);
    }


    @DeleteMapping("/{userId}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> delete(@PathVariable("userId") Long id){
        Optional<AppUser> existingUser = userService.fetchUserById(id);
        if(existingUser.isPresent()){

            this.userService.delete(existingUser.get());
            return ResponseEntity.ok("User deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    @Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
    public ResponseEntity<List<AppUser>> all(){
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/current")
    public ResponseEntity<AppUser> current() {
        return  ResponseEntity.ok(userService.loadCurrentUser());
    }



    @PatchMapping("/role/change")
    @Secured({"ROLE_ADMIN", "ROLE_UNDECIDED"})
    public ResponseEntity<AppUser> changeRole(@RequestBody RoleDTO newRole){
        return ResponseEntity.ok(userService.changeUserRole(newRole));
    }

    @PatchMapping("/teacher/register")
    @Secured({"ROLE_ADMIN", "ROLE_UNDECIDED"})
    public ResponseEntity<Teacher> register(@RequestBody TeacherDTO teacher){
        return ResponseEntity.ok(userService.registerTeacher(teacher));

    }


    @PatchMapping("/student/register")
    @Secured({"ROLE_ADMIN", "ROLE_UNDECIDED"})
    public ResponseEntity<Student> register(@RequestBody StudentDTO student){
        return ResponseEntity.ok(userService.registerStudent(student));

    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

}
