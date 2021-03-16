package root.config.hibernate_second_level_of_cache_config;

import java.util.Map;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.hibernate.RedissonRegionFactory;

public class CustomRegionFactory extends RedissonRegionFactory {
	private static final long serialVersionUID = 1L;

	@Override
	protected RedissonClient createRedissonClient(Map properties) {
		String address = "redis://127.0.0.1:6379";
        Config config = new Config();
        config.useSingleServer()
       		  .setConnectionMinimumIdleSize(3)
       		  .setAddress(address);
        return Redisson.create(config);
	}

}
