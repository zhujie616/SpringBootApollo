package com.example.config;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
  *  测试@ConditionalOnProperty这种情况配置的刷新
 * @author zhujiejie@gongniu.cn
 *
 */
@ConditionalOnProperty("conditional.enable")
@ConfigurationProperties(prefix = "conditional.cache")
@Component("SampleConditionalConfig")
@RefreshScope
public class SampleConditionalConfig {

  private static final Logger logger = LoggerFactory.getLogger(SampleConditionalConfig.class);

  private int expireSeconds;
  private String clusterNodes;
  private int commandTimeout;

  private Map<String, String> someMap = Maps.newLinkedHashMap();
  private List<String> someList = Lists.newLinkedList();

  @PostConstruct
  private void initialize() {
    logger.info(
        "SampleRedisConfig initialized - expireSeconds: {}, clusterNodes: {}, commandTimeout: {}, someMap: {}, someList: {}",
        expireSeconds, clusterNodes, commandTimeout, someMap, someList);
  }

  public void setExpireSeconds(int expireSeconds) {
    this.expireSeconds = expireSeconds;
  }

  public void setClusterNodes(String clusterNodes) {
    this.clusterNodes = clusterNodes;
  }

  public void setCommandTimeout(int commandTimeout) {
    this.commandTimeout = commandTimeout;
  }

  public Map<String, String> getSomeMap() {
    return someMap;
  }

  public List<String> getSomeList() {
    return someList;
  }

  @Override
  public String toString() {
    return String.format(
        "[SampleRedisConfig] expireSeconds: %d, clusterNodes: %s, commandTimeout: %d, someMap: %s, someList: %s",
            expireSeconds, clusterNodes, commandTimeout, someMap, someList);
  }
}
