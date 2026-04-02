package hr.tvz.zavrsni.service.implementation;

import hr.tvz.zavrsni.model.Lesson;
import hr.tvz.zavrsni.repository.LessonRepository;
import hr.tvz.zavrsni.service.LessonService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }


    @Override
    public Optional<Lesson> fetchLessonById(Long lessonId) {
        return lessonRepository.findById(lessonId);
    }

    @Override
    public void update(Lesson lesson) {
        lessonRepository.save(lesson);
    }
}
