package com.example.refresh;
import java.util.HashSet;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.stereotype.Component;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.example.config.SampleConditionalConfig;

/**
 * ConditionalOnProperty 类型的配置变更刷新
 * 
 * @author zhujiejie@gongniu.cn
 *
 */
@ConditionalOnProperty("conditional.enable")
@Component
public class SpringBootApolloRefreshConfig {
	private static final Logger logger = LoggerFactory.getLogger(SpringBootApolloRefreshConfig.class);
	private final SampleConditionalConfig SampleConditionalConfig;
	private final RefreshScope refreshScope;

	private Config config;
	private Set<String> interestedKeysSet;
	private Set<String> interestedKeyPrefixesSet;

	// value = { ConfigConsts.NAMESPACE_APPLICATION }, interestedKeyPrefixes = {
	// "conditional.cache." }
	public SpringBootApolloRefreshConfig(final SampleConditionalConfig SampleConditionalConfig,
			final RefreshScope refreshScope, @Value("${interestedKeys:default}") String interestedKeys,
			@Value("${interestedKeyPrefixes:default}") String interestedKeyPrefixes,
			@Value("${namespace:application}") String namespace) {
		// TODO:校验传入的参数
		this.SampleConditionalConfig = SampleConditionalConfig;
		this.refreshScope = refreshScope;

		this.interestedKeysSet = null;
		if (!"default".equals(interestedKeys)) {
			interestedKeysSet = new HashSet<>();
			String[] splitInterestedKeys = interestedKeys.split(",");
			for (String interestedKey : splitInterestedKeys) {
				interestedKeysSet.add(interestedKey);
			}
		}
		this.interestedKeyPrefixesSet = null;
		if (!"default".equals(interestedKeyPrefixes)) {
			interestedKeyPrefixesSet = new HashSet<>();
			String[] splitInterestedKeyPrefixes = interestedKeyPrefixes.split(",");
			for (String interestedKeyPrefix : splitInterestedKeyPrefixes) {
				interestedKeyPrefixesSet.add(interestedKeyPrefix);
			}
		}
		this.config = ConfigService.getConfig(namespace);
		addListener2Config();
	}

	/**
	 * 增加监听配置
	 */
	private void addListener2Config() {
		this.config.addChangeListener(new ConfigRefreshListener(), interestedKeysSet, interestedKeyPrefixesSet);
	}

	/**
	 * 监听器
	 * 
	 * @author zhujiejie@gongniu.cn
	 *
	 */
	private class ConfigRefreshListener implements ConfigChangeListener {

		@Override
		public void onChange(ConfigChangeEvent changeEvent) {
			String namespace = changeEvent.getNamespace();

			System.out.println("namespace = " + namespace);

			/**
			 * 解决conditionOnProperties的情况，bean改变了，实例化肯定也要重新变化
			 */
			logger.info("before refresh {}", SampleConditionalConfig.toString());
//			refreshScope.refresh("SampleConditionalConfig");
			refreshScope.refreshAll();
			logger.info("after refresh {}", SampleConditionalConfig.toString());

			Set<String> changedKeys = changeEvent.changedKeys();
			for (String key : changedKeys) {
				String property = config.getProperty(key, "default");
				System.out.println("property = " + property);
			}
		}

	}

	public String getProperty(String key, String defaultValue) {
		return config.getProperty(key, defaultValue);
	}

	public Integer getIntProperty(String key, Integer defaultValue) {
		return config.getIntProperty(key, defaultValue);
	}

	public Long getLongProperty(String key, Long defaultValue) {
		return config.getLongProperty(key, defaultValue);
	}

	public Short getShortProperty(String key, Short defaultValue) {
		return config.getShortProperty(key, defaultValue);
	}

	public Float getFloatProperty(String key, Float defaultValue) {
		return config.getFloatProperty(key, defaultValue);
	}

	public Double getDoubleProperty(String key, Double defaultValue) {
		return config.getDoubleProperty(key, defaultValue);
	}

	public long getDurationProperty(String key, long defaultValue) {
		return config.getDurationProperty(key, defaultValue);
	}
}
