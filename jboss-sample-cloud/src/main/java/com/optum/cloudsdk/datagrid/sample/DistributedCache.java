package com.optum.cloudsdk.datagrid.sample;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*import java.util.logging.Level;
import java.util.logging.Logger;*/
import org.apache.log4j.Logger;
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
	private static final String ENV_VAR_JDG_SERVICE_NAME = "JDG_SERVICE_NAME";
	private static final String ENV_VAR_SUFFIX_HOTROD_SERVICE_PORT = "_HOTROD_SERVICE_PORT";
	private static final String ENV_VAR_SUFFIX_HOTROD_SERVICE_HOST = "_HOTROD_SERVICE_HOST";
	final static Logger logger = Logger.getLogger(DistributedCache.class);
	


    public RemoteCacheManager getCacheManager() throws Exception {
    	org.infinispan.client.hotrod.configuration.ConfigurationBuilder builder = new org.infinispan.client.hotrod.configuration.ConfigurationBuilder();
	//Another way to addserver to builder
        //.addServer().host("localhost").port(11322);
    
        logger.info("*** In the method get Cache Manager ***");
        builder
			.nearCache()
				.mode(NearCacheMode.LAZY)
				.maxEntries(500)
			.addServer()
			.host("10.1.54.14")
			.port(11333);
        System.out.println(" ## Just making sure ##");
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
           // Logger.getLogger(DistributedCache.class.getName()).log(Level.SEVERE, null, ex);
        	logger.error("Error occured:"+ex.getMessage());
        }
        // Obtain the default cache
        RemoteCache<String, String> cache = cacheManager.getCache();
        // Store the current node address in some random keys. 
        System.out.println("Data Saving to Cache.......");
        cache.put("value", "value");

        cacheManager.stop();
    }

    public void readData(String cacheName) {
        RemoteCacheManager cacheManager = null;
        try {
            cacheManager = getCacheManager();
        } catch (Exception ex) {
            //Logger.getLogger(DistributedCache.class.getName()).log(Level.SEVERE, null, ex);
        	logger.error("Error occured:"+ex.getMessage());
        }
        // Obtain the default cache
        RemoteCache<String, String> cache = cacheManager.getCache(cacheName);
        System.out.println(cache.values());
        cacheManager.stop();
    }
}