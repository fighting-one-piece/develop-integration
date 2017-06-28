package org.cisiondata.modules.quartz.factory;

import java.util.Map;

import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

public class SchedulerFactoryExtBean extends SchedulerFactoryBean {
	
	private Logger LOG = LoggerFactory.getLogger(SchedulerFactoryExtBean.class);
	
	private String schedulerContextAsMapText = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (isAutoStartup()) {
			super.afterPropertiesSet();
		}
	}
	
	@Override
	public void setSchedulerContextAsMap(Map<String, ?> schedulerContextAsMap) {
		if (schedulerContextAsMapText.indexOf(":") != -1) {
			String[] kvs = schedulerContextAsMapText.indexOf(",") == -1 ? 
					new String[]{schedulerContextAsMapText} : schedulerContextAsMapText.split(",");
			for (int i = 0, len = kvs.length; i < len; i++) {
				String kv = kvs[i];
				LOG.info("scheduler context map: {}", kv);
			}
		}
		super.setSchedulerContextAsMap(schedulerContextAsMap);
	}
	
	@Override
	public void setTriggers(Trigger... triggers) {
		super.setTriggers(triggers);
	}

	public String getSchedulerContextAsMapText() {
		return schedulerContextAsMapText;
	}

	public void setSchedulerContextAsMapText(String schedulerContextAsMapText) {
		this.schedulerContextAsMapText = schedulerContextAsMapText;
	}
	
}
