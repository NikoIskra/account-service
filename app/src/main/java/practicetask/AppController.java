package practicetask;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
    @GetMapping("/test")
    public ResponseEntity practiceResponse() {
        return ResponseEntity.ok().build();
    }
}
