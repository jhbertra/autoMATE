package com.automate.server.connectivity;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class ConnectivityEngineTest {

	private ConnectivityEngine engine;
	private Map<String, ClientId> pingedClients;
	private List<ClientId> disconnectedClietns;
	private boolean pingAllClientsCalled;
	private boolean connectionLostCalled;
	
	@Before
	public void setUp() {
		pingedClients = new HashMap<String, ClientId>();
		disconnectedClietns = new ArrayList<ClientId>();
		pingAllClientsCalled = false;
		connectionLostCalled = false;
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testConstructorSmallInterval() {
		engine = new ConnectivityEngine(0, createCallback(null, null));
	}

	@Test(expected=NullPointerException.class)
	public void testConstructorNullCallback() {
		engine = new ConnectivityEngine(5000, null);
	}
	
	@Test
	public void testConstructorOk() {
		engine = new ConnectivityEngine(5000, createCallback(null, null));
	}
	
	@Test
	public void testNoOnlineClients() {
		engine = new ConnectivityEngine(5000, createCallback(new Runnable() {
			@Override
			public void run() {
				fail("EngineCallback.connectionLost called.");
			}
		}, new Runnable() {
			@Override
			public void run() {
				pingAllClientsCalled = true;
			}
		}));
		engine.loopDelegate();
		assertTrue(pingAllClientsCalled);
	}
	
	@Test
	public void testOneOnlineClientResponds() {
		pingedClients.put("client1", new ClientId(ClientType.APP, "client1"));
		engine = new ConnectivityEngine(5000, createCallback(new Runnable() {
			@Override
			public void run() {
				connectionLostCalled = true;
			}
		}, new Runnable() {
			@Override
			public void run() {
				pingAllClientsCalled = true;
			}
		}));
		engine.loopDelegate();
		assertFalse(connectionLostCalled);
		assertTrue(pingAllClientsCalled);
		assertTrue(engine.ackPingReceivedFromClient("client1"));
		pingAllClientsCalled = false;
		engine.loopDelegate();
		assertFalse(connectionLostCalled);
		assertTrue(pingAllClientsCalled);
	}
	
	@Test
	public void testOneOnlineClientNoResponse() {
		pingedClients.put("client1", new ClientId(ClientType.APP, "client1"));
		engine = new ConnectivityEngine(5000, createCallback(new Runnable() {
			@Override
			public void run() {
				connectionLostCalled = true;
			}
		}, new Runnable() {
			@Override
			public void run() {
				pingAllClientsCalled = true;
			}
		}));
		engine.loopDelegate();
		assertFalse(connectionLostCalled);
		assertTrue(pingAllClientsCalled);
		pingAllClientsCalled = false;
		pingedClients.clear();
		engine.loopDelegate();
		assertTrue(connectionLostCalled);
		assertEquals(disconnectedClietns.size(), 1);
		assertEquals(disconnectedClietns.get(0).uid, "client1");
		assertTrue(pingAllClientsCalled);		
		assertFalse(engine.ackPingReceivedFromClient("client1"));
	}
	
	@Test
	public void testThreeOnlineClientsAllRespond() {
		pingedClients.put("client1", new ClientId(ClientType.APP, "client1"));
		pingedClients.put("client2", new ClientId(ClientType.NODE, "client2"));
		pingedClients.put("client3", new ClientId(ClientType.APP, "client3"));
		engine = new ConnectivityEngine(5000, createCallback(new Runnable() {
			@Override
			public void run() {
				connectionLostCalled = true;
			}
		}, new Runnable() {
			@Override
			public void run() {
				pingAllClientsCalled = true;
			}
		}));
		engine.loopDelegate();
		assertFalse(connectionLostCalled);
		assertTrue(pingAllClientsCalled);
		assertTrue(engine.ackPingReceivedFromClient("client1"));
		assertTrue(engine.ackPingReceivedFromClient("client2"));
		assertTrue(engine.ackPingReceivedFromClient("client3"));
		pingAllClientsCalled = false;
		engine.loopDelegate();
		assertFalse(connectionLostCalled);
		assertTrue(pingAllClientsCalled);
	}
	
	@Test
	public void testThreeOnlineClientsTwoRespond() {
		pingedClients.put("client1", new ClientId(ClientType.APP, "client1"));
		pingedClients.put("client2", new ClientId(ClientType.NODE, "client2"));
		pingedClients.put("client3", new ClientId(ClientType.APP, "client3"));
		engine = new ConnectivityEngine(5000, createCallback(new Runnable() {
			@Override
			public void run() {
				connectionLostCalled = true;
			}
		}, new Runnable() {
			@Override
			public void run() {
				pingAllClientsCalled = true;
			}
		}));
		engine.loopDelegate();
		assertFalse(connectionLostCalled);
		assertTrue(pingAllClientsCalled);
		assertTrue(engine.ackPingReceivedFromClient("client1"));
		assertTrue(engine.ackPingReceivedFromClient("client2"));
		pingAllClientsCalled = false;
		pingedClients.remove("client3");
		engine.loopDelegate();
		assertTrue(connectionLostCalled);
		assertEquals(disconnectedClietns.size(), 1);
		assertEquals(disconnectedClietns.get(0).uid, "client3");
		assertTrue(pingAllClientsCalled);
		assertFalse(engine.ackPingReceivedFromClient("client3"));
	}
	
	@Test
	public void testThreeOnlineClientsOneResponds() {
		pingedClients.put("client1", new ClientId(ClientType.APP, "client1"));
		pingedClients.put("client2", new ClientId(ClientType.NODE, "client2"));
		pingedClients.put("client3", new ClientId(ClientType.APP, "client3"));
		engine = new ConnectivityEngine(5000, createCallback(new Runnable() {
			@Override
			public void run() {
				connectionLostCalled = true;
			}
		}, new Runnable() {
			@Override
			public void run() {
				pingAllClientsCalled = true;
			}
		}));
		engine.loopDelegate();
		assertFalse(connectionLostCalled);
		assertTrue(pingAllClientsCalled);
		assertTrue(engine.ackPingReceivedFromClient("client1"));
		pingAllClientsCalled = false;
		pingedClients.remove("client2");
		pingedClients.remove("client3");
		engine.loopDelegate();
		assertTrue(connectionLostCalled);
		assertEquals(disconnectedClietns.size(), 2);
		assertTrue(pingAllClientsCalled);
		assertFalse(engine.ackPingReceivedFromClient("client2"));
		assertFalse(engine.ackPingReceivedFromClient("client3"));
	}
	
	private EngineCallback createCallback(final Runnable connectionLostCallback, final Runnable PingAllClientsCallback) {
		return new EngineCallback() {
			
			@Override
			public Map<String, ClientId> pingAllClients() {
				if(PingAllClientsCallback != null) {
					PingAllClientsCallback.run();
				}
				return pingedClients;
			}
			
			@Override
			public void connectionLost(List<ClientId> clientIds) {
				disconnectedClietns = clientIds;
				if(connectionLostCallback != null) {
					connectionLostCallback.run();
				}
			}
		};
	}

}
