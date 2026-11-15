package dev.kirky.core.services.impl;

import dev.kirky.core.services.PreviewEnvService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@Component(service = PreviewEnvService.class)
@Designate(ocd = PreviewEnvServiceImpl.Config.class)
public class PreviewEnvServiceImpl implements PreviewEnvService {
    @ObjectClassDefinition(name = "Sling Lab Preview Env Service")
    public @interface Config {
        @AttributeDefinition(name = "Env")
        String env() default "Preview";
    }

    private volatile String env = "author";

    @Activate
    @Modified
    protected void activate(PreviewEnvServiceImpl.Config config) {
        this.env = config.env();
    }

    @Override
    public boolean isPreview() {
        return env.equalsIgnoreCase("author") || env.equalsIgnoreCase("preview");
    }
}
