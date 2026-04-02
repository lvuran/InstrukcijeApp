package hr.tvz.zavrsni.service.implementation;

import hr.tvz.zavrsni.model.Location;
import hr.tvz.zavrsni.repository.LocationRepository;
import hr.tvz.zavrsni.service.LocationService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LocationServiceImpl implements LocationService {


    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }


    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }
}
