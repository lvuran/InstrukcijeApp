package hr.tvz.zavrsni.repository;

import hr.tvz.zavrsni.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    List<Request> findByTeacherId(long id);
    List<Request> findByStudentId(long id);

}
