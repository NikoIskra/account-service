/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (6.6.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package account;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
public interface HealthcheckApi {

    /**
     * GET /api/v1/healthcheck
     * Healthcheck endpoint
     *
     * @return OK (status code 200)
     */
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/api/v1/healthcheck"
    )
    ResponseEntity<Void> apiV1HealthcheckGet(
        
    ) throws Exception;

}
