package hr.tvz.zavrsni.service;

import hr.tvz.zavrsni.model.Lesson;

import java.util.Optional;

public interface LessonService {
    Optional<Lesson> fetchLessonById(Long lessonId);

    void update(Lesson lesson);
}
