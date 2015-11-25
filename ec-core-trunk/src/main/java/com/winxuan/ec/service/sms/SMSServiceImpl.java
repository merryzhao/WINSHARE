package com.winxuan.ec.service.sms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.winxuan.framework.sms.SMSService;

/**
 * 短信实现类
 * @author heyadong
 * @version 1.0, 2012-10-22 下午01:20:55
 */
@Service("smsService")
public class SMSServiceImpl extends SMSService implements InitializingBean{
    private static final Log LOG = LogFactory.getLog(SMSServiceImpl.class);
    
    @Value("${core.smsQueueAddress}")
    private String smsQueueAddress;
    
    @Value("${core.smsQueueName}")
    private String smsQueueName;
    

    @Override
    public void afterPropertiesSet() throws Exception {
        if (smsQueueAddress == null || smsQueueName == null) {
            LOG.warn("The QueueAddress is null or QueueName is null");
        }
        ActiveMQConnectionFactory mqConnectionFactory = new ActiveMQConnectionFactory(smsQueueAddress);
        ActiveMQQueue mqQueue = new ActiveMQQueue(smsQueueName);
        JmsTemplate jmsTemplate = new JmsTemplate(mqConnectionFactory);
        jmsTemplate.setDefaultDestination(mqQueue);
        this.setJmsTemplate(jmsTemplate);
        this.setSmsDestination(mqQueue);
        
    }

}
