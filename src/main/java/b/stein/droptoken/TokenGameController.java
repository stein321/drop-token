package b.stein.droptoken;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenGameController {

    @RequestMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

}
