package com.optum.cloudsdk.datagrid.sample;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.context.Flag;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.NearCacheMode;
import org.infinispan.client.hotrod.marshall.ProtoStreamMarshaller;

/**
 *
 * @author
 */
public class DistributedCache {

    public RemoteCacheManager getCacheManager() throws Exception {
    	org.infinispan.client.hotrod.configuration.ConfigurationBuilder builder = new org.infinispan.client.hotrod.configuration.ConfigurationBuilder();
	//Another way to addserver to builder
        //.addServer().host("localhost").port(11322);
        
        builder
			.nearCache()
				.mode(NearCacheMode.LAZY)
				.maxEntries(500)
			.addServer()
			.host("10.1.35.23")
			.port(11333);
			return new RemoteCacheManager(builder.build(), true);
    }

    public void saveData(String inputKey, String inputValue) {
        // Setup up a clustered cache manager
        GlobalConfigurationBuilder global = GlobalConfigurationBuilder.defaultClusteredBuilder();
        // Make the default cache a distributed synchronous one
        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.clustering().cacheMode(CacheMode.DIST_SYNC);
        // Initialize the cache manager
        RemoteCacheManager cacheManager = null;
        try {
            cacheManager = getCacheManager();
        } catch (Exception ex) {
            Logger.getLogger(DistributedCache.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Obtain the default cache
        RemoteCache<String, String> cache = cacheManager.getCache();
        // Store the current node address in some random keys. 
        System.out.println("Data Saving to Cache.......");
        cache.put(inputKey, inputValue);

        cacheManager.stop();
    }

    public void readData(String cacheName) {
        RemoteCacheManager cacheManager = null;
        try {
            cacheManager = getCacheManager();
        } catch (Exception ex) {
            Logger.getLogger(DistributedCache.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Obtain the default cache
        RemoteCache<String, String> cache = cacheManager.getCache(cacheName);
        System.out.println(cache.values());
        cacheManager.stop();
    }
}