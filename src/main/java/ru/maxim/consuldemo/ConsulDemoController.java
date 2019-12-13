package ru.maxim.consuldemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RefreshScope
public class ConsulDemoController {
    @Value("${prop:null}")
    String value;

    @GetMapping("/")
    public ResponseEntity<?> root(){
        return new ResponseEntity<>("What did you expect to see here?", HttpStatus.I_AM_A_TEAPOT);
    }

    @GetMapping("/getConfigFromValue")
    public String getConfigFromValue() {
        return value;
    }

    @GetMapping("/hostname")
    public ResponseEntity<?> hostname(){
        try {
            return new ResponseEntity<>("You've hit "+InetAddress.getLocalHost().getHostName(), HttpStatus.OK);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something goes wrong. See logs.",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/OK")
    public ResponseEntity<?> ok() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @GetMapping("/error")
    public ResponseEntity<?> error() {
        return new ResponseEntity<>("Something goes wrong.", HttpStatus.BAD_REQUEST);
    }
}
