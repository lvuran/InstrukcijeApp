package hr.tvz.zavrsni.service.implementation;

import hr.tvz.zavrsni.model.Request;
import hr.tvz.zavrsni.repository.RequestRepository;
import hr.tvz.zavrsni.service.RequestService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }


    @Override
    public Optional<Request> fetchRequestById(Long requestId) {
        return requestRepository.findById(requestId);
    }

    @Override
    public void update(Request request) {
        requestRepository.save(request);
    }
}
