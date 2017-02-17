package org.cisiondata.modules.auth.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.cisiondata.modules.abstr.entity.PKAutoEntity;
import org.cisiondata.modules.auth.session.OnlineSession;

/**
 * 当前在线会话
 */
@Entity
@Table(name = "T_USER_ONLINE")
public class UserOnline extends PKAutoEntity<Long> {
	
	private static final long serialVersionUID = 1L;

    /** 用户会话id ===> uid*/
	@Column(name = "SESSION_ID")
    private String sessionId = null;
    /** 当前登录的用户Id */
    @Column(name = "USER_ID")
    private Long userId = 0L;
    /** */
    @Column(name = "USER_NAME")
    private String username = null;
    /** 用户主机地址 */
    @Column(name = "HOST")
    private String host = null;
    /** 用户登录时系统IP */
    @Column(name = "SYSTEM_HOST")
    private String systemHost = null;
    /** 用户浏览器类型 */
    @Column(name = "USER_AGENT")
    private String userAgent = null;
    /** 在线状态 */
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private OnlineSession.OnlineStatus status = OnlineSession.OnlineStatus.on_line;
    /** session创建时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "START_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime = null;
    /** session最后访问时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "LAST_ACCESS_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastAccessTime = null;
    /** 超时时间 */
    @Column(name = "TIMEOUT")
    private Long timeout = null;
    /** 备份的当前用户会话 */
    @Column(name = "SESSION")
    private OnlineSession session = null;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public Long getTimeout() {
        return timeout;
    }

    public void setTimeout(Long timeout) {
        this.timeout = timeout;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public OnlineSession.OnlineStatus getStatus() {
        return status;
    }

    public void setStatus(OnlineSession.OnlineStatus status) {
        this.status = status;
    }

    public OnlineSession getSession() {
        return session;
    }

    public void setSession(OnlineSession session) {
        this.session = session;
    }

    public String getSystemHost() {
        return systemHost;
    }

    public void setSystemHost(String systemHost) {
        this.systemHost = systemHost;
    }

    public static final UserOnline fromOnlineSession(OnlineSession session) {
        UserOnline online = new UserOnline();
        online.setSessionId(String.valueOf(session.getId()));
        online.setUserId(session.getUserId());
        online.setUsername(session.getUsername());
        online.setStartTime(session.getStartTimestamp());
        online.setLastAccessTime(session.getLastAccessTime());
        online.setTimeout(session.getTimeout());
        online.setHost(session.getHost());
        online.setUserAgent(session.getUserAgent());
        online.setSystemHost(session.getSystemHost());
        online.setSession(session);
        return online;
    }


}
