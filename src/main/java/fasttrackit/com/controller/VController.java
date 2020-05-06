package fasttrackit.com.controller;
import fasttrackit.com.pojo.Vacation;
import fasttrackit.com.service.VacationService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController

@RequestMapping("/vacations")
public class VController {
    private final VacationService service;

    public VController(VacationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Vacation> getAll() {
        return service.getAll();
    }

    @GetMapping("/get/{id}")
    Vacation getById(@PathVariable int id) {
        return service.getOrThrow(id);
    }

    @PostMapping
    public Vacation addVacation(@RequestBody Vacation vacation) {
        return service.add(vacation);
    }

    @PutMapping("{id}")
    public Vacation repplace(@PathVariable int id, @RequestBody Vacation vacation) {
        return service.update(id, vacation);
    }

    @DeleteMapping("{id}")
    public Vacation deleteVacation(@PathVariable int id) {
        return service.delete(id);
    }

    @GetMapping()
    List<Vacation> getToMaX(@RequestParam(required = false) Integer maxPrice) {
        if (maxPrice == null) {
            return service.getAll();
        } else {
            return service.maxPrice(maxPrice);
        }
    }

    @GetMapping("/{location}/list")
    List<Vacation> getFromLocation(@RequestParam(required = false) String location) {
        return service.getFromLocation(location);
    }


}
