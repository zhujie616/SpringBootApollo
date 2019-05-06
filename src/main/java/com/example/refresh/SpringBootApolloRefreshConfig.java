package com.example.refresh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.stereotype.Component;

import com.ctrip.framework.apollo.core.ConfigConsts;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import com.example.config.SampleConditionalConfig;

/**
 * ConditionalOnProperty 类型的配置变更刷新
 * @author zhujiejie@gongniu.cn
 *
 */
@ConditionalOnProperty("conditional.enable")
@Component
public class SpringBootApolloRefreshConfig {
	private static final Logger logger = LoggerFactory.getLogger(SpringBootApolloRefreshConfig.class);
	private final SampleConditionalConfig SampleConditionalConfig;
	private final RefreshScope refreshScope;

	public SpringBootApolloRefreshConfig(final SampleConditionalConfig SampleConditionalConfig, final RefreshScope refreshScope) {
		this.SampleConditionalConfig = SampleConditionalConfig;
		this.refreshScope = refreshScope;
	}

	@ApolloConfigChangeListener(value = { ConfigConsts.NAMESPACE_APPLICATION }, interestedKeyPrefixes = { "conditional.cache." })
	public void onChange(ConfigChangeEvent changeEvent) {
		logger.info("before refresh {}", SampleConditionalConfig.toString());
		refreshScope.refresh("SampleConditionalConfig");
		logger.info("after refresh {}", SampleConditionalConfig.toString());
	}
}
