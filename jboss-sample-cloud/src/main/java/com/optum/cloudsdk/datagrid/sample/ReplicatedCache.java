package com.optum.cloudsdk.datagrid.sample;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Set;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;

import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.Configuration;

/**
 *
 * @author
 */
public class ReplicatedCache {
private Logger log;
    public DefaultCacheManager getCacheManager() throws Exception {
        DefaultCacheManager manager ;
           GlobalConfiguration glob = new GlobalConfigurationBuilder().clusteredDefault() // Builds a default clustered
                                                                                           // configuration
                    .transport().addProperty("configurationFile", "jgroups-udp.xml") // provide a specific JGroups configuration
                    .globalJmxStatistics().allowDuplicateDomains(true).enable() // This method enables the jmx statistics of
                    // the global configuration and allows for duplicate JMX domains
                    .build(); // Builds the GlobalConfiguration object
            Configuration loc = new ConfigurationBuilder().jmxStatistics().enable() // Enable JMX statistics
                    .clustering().cacheMode(CacheMode.REPL_SYNC) // Set Cache mode to DISTRIBUTED with SYNCHRONOUS replication
                    .hash().numOwners(2) // Keeps two copies of each key/value pair
                    .expiration().lifespan(60 * 1000) // Set expiration - cache entries expire after some time (given by
                    // the lifespan parameter) and are removed from the cache (cluster-wide).
                    .build();
            manager = new DefaultCacheManager(glob, loc, true);
            return manager;
    }

    public void saveData(String inputKey, String inputValue) {
        // Setup up a clustered cache manager
        GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();
        // Make the default cache a distributed synchronous one
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.clustering().cacheMode(CacheMode.REPL_SYNC);
        // Initialize the cache manager
        DefaultCacheManager cacheManager = null;
        try {
            cacheManager = getCacheManager();
        } catch (Exception ex) {
            Logger.getLogger(DistributedCache.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Obtain the default cache
        Cache<String, String> cache = cacheManager.getCache();
        // Store the current node address in some random keys. 

        cache.put(inputKey, inputValue);

        log.info("put: " + inputKey + " " + inputValue);
        cacheManager.stop();
    }

    public void readData() {
        DefaultCacheManager cacheManager = null;
        StringBuffer allKeyValues = new StringBuffer();
        String message;
        try {
            cacheManager = getCacheManager();
        } catch (Exception ex) {
            Logger.getLogger(DistributedCache.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Obtain the default cache
        Cache<String, String> cache = cacheManager.getCache();
                Set<String> keySet = cache.keySet();
        for (String key : keySet) {

            String value = cache.get(key);
            log.info("key: " + key + " value: " + value);

            allKeyValues.append(key + "=" + value + ", ");
        } // for

        if (allKeyValues == null || allKeyValues.length() == 0) {
            message = "Nothing in the Cache";
        } else {
            // remote trailing comma
            allKeyValues.delete(allKeyValues.length() - 2, allKeyValues.length());
            message = allKeyValues.toString();
        }
    }
}
