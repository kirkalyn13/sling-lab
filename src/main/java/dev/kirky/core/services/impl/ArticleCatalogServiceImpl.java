package dev.kirky.core.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import dev.kirky.core.services.ArticleCatalogService;

@Component(service = ArticleCatalogService.class)
public class ArticleCatalogServiceImpl implements ArticleCatalogService {

    private static final String ARTICLE_TYPE = "sling-lab/components/article";

    @Reference
    private ResourceResolverFactory resolverFactory;

    @Override
    public List<String> titles() {
        Map<String, Object> auth =
                Map.of(ResourceResolverFactory.SUBSERVICE, "catalog");
        List<String> titles = new ArrayList<>();
        try (ResourceResolver resolver = resolverFactory.getServiceResourceResolver(auth)) {
            Resource root = resolver.getResource("/content");
            if (root != null) {
                for (Resource child : root.getChildren()) {
                    if (child.isResourceType(ARTICLE_TYPE)) {
                        titles.add(child.getValueMap().get("title", child.getName()));
                    }
                }
            }
        } catch (LoginException e) {
            return Collections.emptyList();
        }
        return titles;
    }
}