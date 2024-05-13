package dk.sdu.mmmi.cbse.scoringsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RequestMapping("/scoresystem")
@RestController
@CrossOrigin
public class ScoringSystemController {

    private long score = 0;

    public static void main(String[] args) {
        SpringApplication.run(ScoringSystemController.class, args);
    }

    @GetMapping("/score/get")
    public long getScore() {
        return this.score;
    }

    @PutMapping("/score/add/{points}")
    public long addToScore(@PathVariable(value = "points") long points) {
        this.score += points;

        return this.score;
    }

    @PutMapping("/score/set/{newScore}")
    public long updateScore(@PathVariable(value = "newScore") long newScore) {
        this.score = newScore;

        return this.score;
    }
}
