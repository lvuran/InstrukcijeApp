package hr.tvz.zavrsni.repository;

import hr.tvz.zavrsni.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository  extends JpaRepository<Lesson, Long> {
    List<Lesson> findByTeacherId(long id);
    List<Lesson> findByStudentId(long id);
}
