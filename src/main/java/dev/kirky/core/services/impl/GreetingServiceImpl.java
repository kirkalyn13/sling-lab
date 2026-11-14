package dev.kirky.core.services.impl;

import org.osgi.service.component.annotations.Component;
import dev.kirky.core.services.GreetingService;

@Component(service = GreetingService.class)
public class GreetingServiceImpl implements GreetingService {

    @Override
    public String greet(String name) {
        return "Good day, " + name;
    }
}