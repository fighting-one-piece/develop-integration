package org.cisiondata.modules.quartz.task;

import org.cisiondata.modules.quartz.service.IQuartzService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class DefaultJob extends QuartzJobBean {
	
	private static final Logger LOG = LoggerFactory.getLogger(DefaultJob.class);
	
	private int injectValue1 = 0;
	
	private String injectValue2 = null;
	
	private IQuartzService quartzService = null;

	@Override
	protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			SchedulerContext schedulerContext = jobExecutionContext.getScheduler().getContext();
			ApplicationContext ac = (ApplicationContext) schedulerContext.get("applicationContext");
			IQuartzService quartzService = ac.getBean("quartzService", IQuartzService.class);
			System.out.println("AC SchedulerBusiness: " + quartzService);
			System.out.println("!!!SchedulerBusiness!!!: " + quartzService);
			System.out.println("!!!injectValue1!!!: " + injectValue1);
			System.out.println("!!!injectValue2!!!: " + injectValue2);
		} catch (SchedulerException e) {
			LOG.error(e.getMessage(), e);
		}
	}
	
	
	public int getInjectValue1() {
		return injectValue1;
	}

	public void setInjectValue1(int injectValue1) {
		this.injectValue1 = injectValue1;
	}

	public String getInjectValue2() {
		return injectValue2;
	}

	public void setInjectValue2(String injectValue2) {
		this.injectValue2 = injectValue2;
	}

	public IQuartzService getQuartzService() {
		return quartzService;
	}

	public void setQuartzService(IQuartzService quartzService) {
		this.quartzService = quartzService;
	}

}
