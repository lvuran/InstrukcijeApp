package hr.tvz.zavrsni.controller;


import hr.tvz.zavrsni.model.Location;
import hr.tvz.zavrsni.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/location")
public class LocationController {
    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Location>> all(){
        return ResponseEntity.ok(locationService.getAllLocations());
    }
}
