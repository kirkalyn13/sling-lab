package dev.kirky.core.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import dev.kirky.core.services.PreviewEnvService;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import dev.kirky.core.services.ReadingTimeService;

@Model(
        adaptables = Resource.class,
        resourceType = "sling-lab/components/article",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = "jackson", extensions = "json")
public class Article {

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String author;

    @ValueMapValue
    private String body;

    @ValueMapValue
    private String[] tags;

    @OSGiService
    private ReadingTimeService readingTimeService;

    @OSGiService
    private PreviewEnvService previewEnvService;

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getBody() {
        return body;
    }

    public List<String> getTags() {
        return tags == null ? Collections.emptyList() : Arrays.asList(tags);
    }

    public int getReadingTime() {
        return readingTimeService.minutes(body);
    }

    public boolean isPreview() {
        return previewEnvService.isPreview();
    }
}