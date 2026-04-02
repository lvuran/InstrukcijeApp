package hr.tvz.zavrsni.service.implementation;

import hr.tvz.zavrsni.dto.RoleDTO;
import hr.tvz.zavrsni.dto.StudentDTO;
import hr.tvz.zavrsni.dto.TeacherDTO;
import hr.tvz.zavrsni.repository.StudentRepository;
import hr.tvz.zavrsni.repository.TeacherRepository;
import hr.tvz.zavrsni.service.UserService;
import hr.tvz.zavrsni.model.AppUser;
import hr.tvz.zavrsni.model.Student;
import hr.tvz.zavrsni.model.Teacher;
import hr.tvz.zavrsni.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    public UserServiceImpl(UserRepository userRepository, TeacherRepository teacherRepository, StudentRepository studentRepository) {
        this.userRepository = userRepository;

        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<AppUser> fetchUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public AppUser fetchUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    //TODO: OBRISATI SVE USER RELATED STVARI U BAZI KOJE SE TICU TOG KORISNIKA!
    @Transactional
    public void delete(AppUser appUser) {
        userRepository.delete(appUser);

    }

    @Override
    public List<AppUser> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public AppUser loadCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();


        if (authentication instanceof OAuth2AuthenticationToken oauthToken) {

            String email = oauthToken.getPrincipal().getAttribute("email");


            return userRepository.findByEmail(email);
        }
        return null;
    }

    @Override
    public AppUser changeUserRole(RoleDTO newRole) {
        AppUser user = loadCurrentUser();
        user.setRole(newRole.getRole());

        return userRepository.save(user);
    }

    @Override
    public Teacher registerTeacher(TeacherDTO teacher) {
        System.out.println(teacher);
        AppUser user = loadCurrentUser();
        Teacher teacher1 = new Teacher();
        user.setAddress(teacher.getAddress());
        user.setLocation(teacher.getLocation());
        user.setPhoneNum(teacher.getTelephone());
        userRepository.save(user);
        teacher1.setDuration(teacher.getDuration());

        teacher1.setPrice(teacher.getPrice());
        teacher1.setDuration(teacher.getDuration());
        teacher1.setType(teacher.getType());

        teacher1.setSubjects(teacher.getSubjects());
        teacher1.setAbout(teacher.getAbout());
        teacher1.setUser(user);
        teacher1.setOcjena(0);
        teacher1.setRatings(0);
        teacher1.setRatings(0);
        System.out.println(teacher1.toString());

        Teacher savedTeacher = teacherRepository.save(teacher1);
        refreshAuthentication(user);
        return savedTeacher;
    }

    @Override
    public Student registerStudent(StudentDTO student) {
        AppUser user = loadCurrentUser();
        Student student1 = new Student();
        user.setAddress(student.getAddress());
        user.setLocation(student.getLocation());
        user.setPhoneNum(student.getTelephone());
        userRepository.save(user);
        student1.setGrade(student.getGrade());


        student1.setSubjects(student.getSubjects());

        student1.setUser(user);
        Student savedStudent = studentRepository.save(student1);
        refreshAuthentication(user);
        return savedStudent;
    }


    private void refreshAuthentication(AppUser user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof OAuth2AuthenticationToken oldAuth) {
            OidcUser oldPrincipal = (OidcUser) oldAuth.getPrincipal();


            GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().name());


            OidcUser oidcUser = new DefaultOidcUser(
                    Collections.singletonList(authority),
                    oldPrincipal.getIdToken(),
                    "email"
            );

            OAuth2AuthenticationToken newAuth =
                    new OAuth2AuthenticationToken(oidcUser, oidcUser.getAuthorities(), oldAuth.getAuthorizedClientRegistrationId());

            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
}}
