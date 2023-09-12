package practicetask;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController implements DefaultApi {
    @Override
    public ResponseEntity<Void> apiV1HealthcheckGet() throws Exception {
        return ResponseEntity.ok().build();
    }
}
