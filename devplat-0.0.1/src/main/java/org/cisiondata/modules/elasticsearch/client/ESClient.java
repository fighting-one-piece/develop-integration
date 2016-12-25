package org.cisiondata.modules.elasticsearch.client;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ESClient {
	
	/** 10库*/
	private TransportClient client10 = null;
	/** 105-108库*/
	private TransportClient client105 = null;
	/** 114库*/
	private TransportClient client114 = null;
	
	private ESClient() {
		initClient10();
		initClient105();
	}
	
	private static class ESClientHolder {
		private static final ESClient INSTANCE = new ESClient();
	}
	
	public static final ESClient getInstance() {
		return ESClientHolder.INSTANCE;
	}
	
	public Client getClient() {
		return getClient10();
	}
	
	public Client getClient10() {
		if (null == client10) initClient10();
		return client10;
	}
	
	public Client getClient105() {
		if (null == client105) initClient105();
		return client105;
	}
	
	public Client getClient114() {
		if (null == client114) initClient114();
		return client114;
	}
	
	public void closeClient(Client client) {
		client.close();
	}
	
	private void initClient10() {
		Settings settings = Settings.builder().put("cluster.name", "cisiondata")
				.put("client.transport.sniff", true).build();
		client10 = TransportClient.builder().settings(settings).build();
		List<EsServerAddress> esServerAddress = new ArrayList<EsServerAddress>();
		esServerAddress.add(new EsServerAddress("192.168.0.10", 9300));
		esServerAddress.add(new EsServerAddress("192.168.0.12", 9300));
		for (EsServerAddress address : esServerAddress) {
			client10.addTransportAddress(new InetSocketTransportAddress(
					new InetSocketAddress(address.getHost(), address.getPort())));
		}
	}
	
	private void initClient105() {
		Settings settings = Settings.builder().put("cluster.name", "youmeng")
				.put("client.transport.sniff", true).build();
		client105 = TransportClient.builder().settings(settings).build();
		List<EsServerAddress> esServerAddress = new ArrayList<EsServerAddress>();
		esServerAddress.add(new EsServerAddress("192.168.0.105", 9300));
		esServerAddress.add(new EsServerAddress("192.168.0.108", 9300));
		for (EsServerAddress address : esServerAddress) {
			client105.addTransportAddress(new InetSocketTransportAddress(
					new InetSocketAddress(address.getHost(), address.getPort())));
		}
	}
	
	private void initClient114() {
		Settings testSettings = Settings.builder().put("cluster.name", "youmeng")
				.put("client.transport.sniff", true).build();
		client114 = TransportClient.builder().settings(testSettings).build();
		List<EsServerAddress> testServerAddress = new ArrayList<EsServerAddress>();
		testServerAddress.add(new EsServerAddress("192.168.0.114", 9300));
		for (EsServerAddress address : testServerAddress) {
			client114.addTransportAddress(new InetSocketTransportAddress(
					new InetSocketAddress(address.getHost(), address.getPort())));
		}
	}
	
}

