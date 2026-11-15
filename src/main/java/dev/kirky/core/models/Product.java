package dev.kirky.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        resourceType = "sling-lab/components/product",
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(name = "jackson", extensions = "json")
public class Product {

    @ValueMapValue
    private String name;

    @ValueMapValue
    private String type;

    @ValueMapValue
    private int rating;

    @Self
    private Article article;

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getRating() {
        return rating;
    }

    public Article getArticle() {
        return article;
    }
}