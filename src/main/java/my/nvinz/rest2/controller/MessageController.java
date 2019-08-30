package my.nvinz.rest2.controller;

import my.nvinz.rest2.exceptions.NotFoundException;
import my.nvinz.rest2.generator.VinGenerator;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("vin")
public class MessageController {

    private int counter = 1;
    private List<Map<String, String>> messages = new ArrayList<>();

    private Map<String, String> getMessage(@PathVariable String id) {
        return messages.stream()
                .filter(message -> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @GetMapping
    public List<Map<String, String>> list(){
        return messages;
    }

    /*@GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getMessage(id);
    }*/

    @GetMapping("{id}")
    public Map<String, String> create(){
        Map<String, String> message = new HashMap<>(){
            {
                put("id", String.valueOf(counter));
                put("vin", VinGenerator.generateVIN());
            }};
        counter++;
        messages.add(message);
        return message;
    }

}
