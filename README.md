# sling-lab

Magnolia-to-AEM study lab on Apache Sling. A minimal Sling Model and OSGi service, packaged as a bundle and rendered through HTL on the Sling Starter.

## Why

Coming from Magnolia CMS, this project maps familiar concepts to the open-source stack AEM is built on (Sling, OSGi, Oak), without needing an AEM licence.

## What it shows

| Concept | Where |
|---|---|
| OSGi DS component | `GreetingServiceImpl` |
| Sling Model with injection | `Hello` (`@ValueMapValue`, `@OSGiService`) |
| Resource type resolution | `/content/test` → `/apps/test/hello` |
| Selector-based script | `print.html` vs `hello.html` |
| Bundle packaging | `bnd` + Maven |

## Magnolia to AEM mapping

| Magnolia | Sling / AEM |
|---|---|
| Template ID on node | `sling:resourceType` |
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
    ├── models/Hello.java
    └── services/
        ├── GreetingService.java
        └── impl/GreetingServiceImpl.java
```

## Run

```bash
docker run -d --name sling-lab -p 8080:8080 \
  -v sling-lab-data:/opt/sling/launcher apache/sling:14
```

Open `http://localhost:8080`. Local credentials: `admin` / `admin`.

## Build and deploy

1. `mvn clean package`
2. Open `/system/console/bundles`, then Install/Update, tick Start, and upload `target/*.jar`.
3. Confirm the bundle is Active.

## Content setup

- `/content/test` (`nt:unstructured`), with `sling:resourceType = test/hello` and an optional `title`.
- `/apps/test/hello/hello.html` and `print.html` (`nt:file`).

```html
<sly data-sly-use.hello="dev.kirky.core.models.Hello">
  <h1>${hello.message}</h1>
</sly>
```

## Verify

| Check | URL |
|---|---|
| Rendered page | `/content/test.html` |
| Selector variant | `/content/test.print.html` |
| Model registered | `/system/console/status-slingmodels` |
| Component active | `/system/console/components` |
| Resolution | `/system/console/jcrresolver` |
| Script chosen | `/system/console/requests` |

## Roadmap

- [ ] Sling Model Exporter (`.model.json`)
- [ ] `sling:resourceSuperType` inheritance
- [ ] Service user instead of admin session
- [ ] Run-mode OSGi config

## Notes

The Sling Starter is a learning environment. It has no Dispatcher or AEM authoring UI, and it is not production-ready.