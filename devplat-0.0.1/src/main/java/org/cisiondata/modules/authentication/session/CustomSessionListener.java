package org.cisiondata.modules.authentication.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

public class CustomSessionListener implements SessionListener {

	@Override
	public void onStart(Session session) {
		System.out.println("session start: " + session.getId());
	}
	
	@Override
	public void onExpiration(Session session) {
		System.out.println("session expiration: " + session.getId());
	}

	@Override
	public void onStop(Session session) {
		System.out.println("session stop: " + session.getId());
	}
	
}
