package ie.lyit.app.web.rest;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 */
@Controller
public class ClientForwardController {

    /**
     * Forwards any unmapped paths (except those containing a period) to the client {@code index.html}.
     * @return forward to client {@code index.html}.
     */
    @GetMapping(value = "/**/{path:[^\\.]*}")
    @ApiOperation(value = "Forward a path", notes = "Allows you to forward from any unmapped path")
    public String forward() {
        return "forward:/";
    }
}
