package com.horntvedt.case1.integrasjon.config;
import javax.inject.Inject;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.LoggingLevel;
import org.apache.camel.component.jms.JmsConfiguration;
import org.apache.camel.spring.spi.TransactionErrorHandlerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ActivemqConfiguration {

    @Value("${amq.concurrent-consumers}")
    private int amqConcurrentConsumers;

    @Bean
    @Inject
    public PlatformTransactionManager jmsTransactionManager(PooledConnectionFactory pooledConnectionFactory) {
        return new JmsTransactionManager(pooledConnectionFactory);
    }

    @Bean
    @Inject
    public TransactionErrorHandlerBuilder transactionErrorHandlerBuilder(
            PlatformTransactionManager jmsTransactionManager) {

        TransactionErrorHandlerBuilder transactionErrorHandlerBuilder = new TransactionErrorHandlerBuilder();
        transactionErrorHandlerBuilder.setTransactionManager(jmsTransactionManager);
        return transactionErrorHandlerBuilder;
    }

    @Bean
    @Inject
    public ActiveMQComponent activemq(PooledConnectionFactory pooledConnectionFactory,
                                      PlatformTransactionManager jmsTransactionManager) {
        pooledConnectionFactory.setReconnectOnException(true);
        ActiveMQComponent aqComponent = new ActiveMQComponent();
        JmsConfiguration jmsConfig = new JmsConfiguration();
        jmsConfig.setErrorHandlerLogStackTrace(true);
        jmsConfig.setTransacted(true);
        jmsConfig.setErrorHandlerLoggingLevel(LoggingLevel.INFO);
        jmsConfig.setConcurrentConsumers(amqConcurrentConsumers);
        jmsConfig.setAsyncConsumer(true);
        aqComponent.setConfiguration(jmsConfig);
        aqComponent.setTransactionManager(jmsTransactionManager);
        aqComponent.setCacheLevelName("CACHE_CONSUMER");
        aqComponent.setConnectionFactory(pooledConnectionFactory);
        return aqComponent;
    }
}