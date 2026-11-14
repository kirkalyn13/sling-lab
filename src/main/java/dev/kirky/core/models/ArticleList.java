package dev.kirky.core.models;

import java.util.List;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import dev.kirky.core.services.ArticleCatalogService;

@Model(
        adaptables = Resource.class,
        resourceType = "sling-lab/components/article-list",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = "jackson", extensions = "json")
public class ArticleList {

    @OSGiService
    private ArticleCatalogService catalog;

    public List<String> getTitles() {
        return catalog.titles();
    }
}