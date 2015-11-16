package com.adobe.acs.commons.httpcache.config;

import com.adobe.acs.commons.httpcache.keys.CacheKey;
import org.apache.sling.api.SlingHttpServletRequest;

/**
 * Configuration for Http cache. Multiple configs can be supplied. Request uri, authentication details, aem user group
 * details, cache store details and invalidation JCR path details are captured through configs. <ul><li>Blacklisted URIs
 * evaluated after evaluating the request URIs and hence have an overriding effect.</li><li>User groups are applied only
 * for configs that need authenticated requests. </li><li>User group list represents logical OR condition and at least
 * one of the groups should be present in the request.</li><li>If 2 cache configs with the same url pattern and
 * different list of groups present, one with high match score of groups with request would be considered. In case of
 * levelled score, cache will be rejected.</li><li>Cache invalidation path is the JCR path expressed in REGEX
 * pattern.</li></ul>
 */
public interface HttpCacheConfig {
    /**
     * Name of the configured cache store.
     *
     * @return
     */
    String getCacheStoreName();

    /**
     * Determins if this Http Cache Config should try to create a cache entry for the http request's response.
     *
     * @param request the request
     * @return true if the response should be cached, false if it should not be cached.
     */
    boolean accepts(SlingHttpServletRequest request);

    /**
     * @return true if this config is considered valid and processable by the HttpCacheEngine
     */
    boolean isValid();

    /**
     * Creates the CacheKey object using the CacheKeyFactory associated with this HttpCacheConfig factory instance.
     * @param request the request to create the CacheKey for
     * @return the CacheKey
     */
    CacheKey buildCacheKey(SlingHttpServletRequest request);

    /**
     * @return true if this has been configured to invalidate the cache entirely for any invalidation. false if each
     * CacheKey should be checked for invalidation.
     */
    boolean isInvalidateAll();

    /**
     * Determines if a JCR path is a candidate for invalidating this cache.
     * @param path the jcr path
     * @return true if this config can be invalidated by a change to this path
     */
    boolean canInvalidate(String path);
}

