package ie.lyit.app.config;

import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tech.jhipster.config.JHipsterConstants;
import tech.jhipster.config.JHipsterProperties;

/**
 * Static resources config class
 *
 * @author smallwindow21 team
 */
@Configuration
@Profile({ JHipsterConstants.SPRING_PROFILE_PRODUCTION })
public class StaticResourcesWebConfiguration implements WebMvcConfigurer {

    /**
     * Resource locations
     */
    protected static final String[] RESOURCE_LOCATIONS = new String[] {
        "classpath:/static/app/",
        "classpath:/static/content/",
        "classpath:/static/i18n/",
    };
    /**
     * Resource paths
     */
    protected static final String[] RESOURCE_PATHS = new String[] { "/app/*", "/content/*", "/i18n/*" };

    private final JHipsterProperties jhipsterProperties;

    /**
     * Class constructor
     * @param jHipsterProperties -
     */
    public StaticResourcesWebConfiguration(JHipsterProperties jHipsterProperties) {
        this.jhipsterProperties = jHipsterProperties;
    }

    /**
     * Add resource handlers
     * @param registry -
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        ResourceHandlerRegistration resourceHandlerRegistration = appendResourceHandler(registry);
        initializeResourceHandler(resourceHandlerRegistration);
    }

    /**
     *
     * @param registry -
     * @return -
     */
    protected ResourceHandlerRegistration appendResourceHandler(ResourceHandlerRegistry registry) {
        return registry.addResourceHandler(RESOURCE_PATHS);
    }

    /**
     * -
     * @param resourceHandlerRegistration -
     */
    protected void initializeResourceHandler(ResourceHandlerRegistration resourceHandlerRegistration) {
        resourceHandlerRegistration.addResourceLocations(RESOURCE_LOCATIONS).setCacheControl(getCacheControl());
    }

    /**
     * -
     * @return -
     */
    protected CacheControl getCacheControl() {
        return CacheControl.maxAge(getJHipsterHttpCacheProperty(), TimeUnit.DAYS).cachePublic();
    }

    /**
     * -
     * @return -
     */
    private int getJHipsterHttpCacheProperty() {
        return jhipsterProperties.getHttp().getCache().getTimeToLiveInDays();
    }
}
