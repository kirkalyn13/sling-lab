package dev.kirky.core.services.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import dev.kirky.core.services.GreetingService;

@Component(service = GreetingService.class)
@Designate(ocd = GreetingServiceImpl.Config.class)
public class GreetingServiceImpl implements GreetingService {

    @ObjectClassDefinition(name = "Sling Lab Greeting Service")
    public @interface Config {
        @AttributeDefinition(name = "Prefix")
        String prefix() default "Good day";
    }

    private volatile String prefix = "Good day";

    @Activate
    @Modified
    protected void activate(Config config) {
        this.prefix = config.prefix();
    }

    @Override
    public String greet(String name) {
        return prefix + ", " + name;
    }
}