# sling-lab

Magnolia-to-AEM study lab on Apache Sling. Sling Models and OSGi services, packaged as a bundle and rendered through HTL on the Sling Starter, with a simple article component that also exports JSON.

## Why

Coming from Magnolia CMS, this project maps familiar concepts to the open-source stack AEM is built on (Sling, OSGi, Oak), without needing an AEM licence.

## What it shows

| Concept | Where |
|---|---|
| OSGi DS components | `GreetingServiceImpl`, `ReadingTimeServiceImpl` |
| Sling Model with injection | `Article` (`@ValueMapValue`, `@OSGiService`) |
| Multi-value property injection | `Article.tags` (`String[]`) |
| Model Exporter (JSON) | `Article`, via `.model.json` |
| Resource type resolution | `/content/test-article` → `/apps/sling-lab/components/article` |
| Selector-based script | `print.html` vs `hello.html` (reference example) |
| Bundle packaging | `bnd` + Maven |

`Hello` is kept as the minimal reference model.

## Magnolia to AEM mapping

| Magnolia | Sling / AEM |
|---|---|
| Template ID (`mgnl:template`) | `sling:resourceType` |
| RenderingModel | Sling Model |
| Freemarker | HTL |
| Guice / providers | OSGi DS |
| Module | OSGi bundle |

## Structure

```
sling-lab/
├── pom.xml
├── bnd.bnd
└── src/main/java/dev/kirky/core/
    ├── models/
    │   ├── Hello.java
    │   └── Article.java
    └── services/
        ├── GreetingService.java
        ├── ReadingTimeService.java
        └── impl/
            ├── GreetingServiceImpl.java
            └── ReadingTimeServiceImpl.java
```

## Run

```bash
docker run -d --name sling-lab -p 8080:8080 \
  -v sling-data:/opt/sling/launcher apache/sling:14
```

Open `http://localhost:8080`. Local credentials: `admin` / `admin`.

## Build and deploy

1. `mvn clean package`
2. Open `/system/console/bundles`, then Install/Update, tick Start, and upload `target/*.jar`.
3. Confirm the bundle is Active.

## Content setup

### Article (main example)

- Script: `/apps/sling-lab/components/article/article.html` (`nt:file`).
- Content: `/content/test-article` (`nt:unstructured`) with:

| Property | Type | Example |
|---|---|---|
| `sling:resourceType` | String | `sling-lab/components/article` |
| `title` | String | `Test Article` |
| `author` | String | `Kirk Santos` |
| `body` | String | Any text |
| `tags` | String[] | `sling`, `osgi` |

`readingTime` is computed by `ReadingTimeService` from `body`, so it is not stored on the node.

```html
<sly data-sly-use.article="dev.kirky.core.models.Article">
  <article>
    <h1>${article.title}</h1>
    <p>By ${article.author} &middot; ${article.readingTime} min read</p>
    <p>${article.body}</p>
    <ul data-sly-list.tag="${article.tags}">
      <li>${tag}</li>
    </ul>
  </article>
</sly>
```

### Hello (reference)

- `/content/test` (`nt:unstructured`), with `sling:resourceType = test/hello` and an optional `title`.
- `/apps/test/hello/hello.html` and `print.html` (`nt:file`).

## Verify

| Check | URL |
|---|---|
| Article page | `/content/test-article.html` |
| Article JSON | `/content/test-article.model.json` |
| Hello page | `/content/test.html` |
| Selector variant | `/content/test.print.html` |
| Model registered | `/system/console/status-slingmodels` |
| Components active | `/system/console/components` |
| Resolution | `/system/console/jcrresolver` |
| Script chosen | `/system/console/requests` |

## Least privilege check

The `catalog` sub-service maps to the `article-reader` service user (read-only on `/content`). To prove access is enforced by the repository:

1. Create a `no-access` service user and deny `jcr:read` on `/content` (repoinit).
2. Remap `dev.kirky.sling-lab:catalog` to `no-access`.
3. `/content/test-list.html` shows "No articles found."
4. Remap back to `article-reader` and both titles return.

Note: Without the explicit deny, `no-access` could still read `/content` through default permissions in the Starter.

## Gotchas

- Requesting a script's own path (`/apps/.../article.html`) serves the raw file. Always request the content path.
- A "Resource dumped by HtmlRenderer" page means the node resolved but has no `sling:resourceType`, or no script matched.
- Multi-value properties must be stored as `String[]`, or `tags` comes out empty.
- A model needs `resourceType` in `@Model` for the exporter to bind.

## Roadmap

- [x] Sling Model Exporter (`.model.json`)
- [x] `sling:resourceSuperType` inheritance
- [x] Service user instead of admin session
- [ ] Run-mode OSGi config

## Notes

The Sling Starter is a learning environment. It has no Dispatcher or AEM authoring UI, and it is not production-ready.