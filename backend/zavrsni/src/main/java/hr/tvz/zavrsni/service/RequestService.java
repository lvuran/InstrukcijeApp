package hr.tvz.zavrsni.service;

import hr.tvz.zavrsni.model.Request;

import java.util.Optional;

public interface RequestService {
    Optional<Request> fetchRequestById(Long requestId);

    void update(Request request);
}
