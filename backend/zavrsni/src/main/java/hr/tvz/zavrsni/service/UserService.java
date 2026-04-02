package hr.tvz.zavrsni.service;

import hr.tvz.zavrsni.dto.RoleDTO;
import hr.tvz.zavrsni.dto.StudentDTO;
import hr.tvz.zavrsni.dto.TeacherDTO;
import hr.tvz.zavrsni.model.AppUser;
import hr.tvz.zavrsni.model.Student;
import hr.tvz.zavrsni.model.Teacher;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<AppUser> fetchUserById(Long userId);

    AppUser fetchUserByEmail(String userEmail);

    void delete(AppUser appUser);

    List<AppUser> findAllUsers();

    AppUser loadCurrentUser();

    AppUser changeUserRole(RoleDTO newRole);

    Teacher registerTeacher(TeacherDTO teacher);

    Student registerStudent(StudentDTO student);

}
