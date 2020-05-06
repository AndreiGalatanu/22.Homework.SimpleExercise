package fasttrackit.com.service;

import fasttrackit.com.pojo.Vacation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VacationService {
    private final List<Vacation> vacations = new ArrayList<>();

    public VacationService(FileReader reader) {
        reader.read()
                .forEach(this::add);
    }

    // get all
    public List<Vacation> getAll() {
        return Collections.unmodifiableList(vacations);
    }

    //create
    public Vacation add(Vacation vacation) {
        Vacation newVacation = new Vacation(vacations.size() + 1, vacation.getLocation(), vacation.getPrice(), vacation.getDuration());
        vacations.add(newVacation);
        return newVacation;
    }

    //get
    public Vacation getOrThrow(final int id) {
        return vacations.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Could not find the id nr: " + id));
    }

    //update
    public Vacation update(int id, Vacation vacation) {
        Vacation vacationToReplace = vacations.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Could not find the person you try to update by id :" + id));
        vacations.remove(vacationToReplace);
        Vacation newVacation = new Vacation(id, vacation.getLocation(), vacation.getPrice(), vacation.getDuration());
        vacations.add(id - 1, newVacation);
        return newVacation;

    }

    //delete
    public Vacation delete(int id) {
        Vacation vacationToDelete = getOrThrow(id);
        vacations.remove(vacationToDelete);
        return vacationToDelete;
    }

    //get all vacations for location X
    public List<Vacation> getFromLocation(final String location) {
        return vacations.stream()
                .filter(v -> v.getLocation().equalsIgnoreCase(location))
                .collect(Collectors.toList());
    }

    //GET VACATION THAT DON'T HAVE PRICE HIGHER THEN MAX PRICE
    public List<Vacation> maxPrice(int maxprice) {
        return vacations.stream()
                .filter(v -> v.getPrice() < maxprice)
                .collect(Collectors.toList());

    }


}
