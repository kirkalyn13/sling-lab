package dev.kirky.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import dev.kirky.core.services.GreetingService;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class Hello {

    @ValueMapValue
    private String title;

    @OSGiService
    private GreetingService greetingService;

    public String getMessage() {
        String name = (title == null || title.isBlank()) ? "Kirk" : title;
        return greetingService.greet(name);
    }
}