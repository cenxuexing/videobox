package io.renren.utils.sms.smpp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * #%L
 * ch-smpp
 * %%
 * Copyright (C) 2009 - 2015 Cloudhopper by Twitter
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.cloudhopper.commons.charset.CharsetUtil;
import com.cloudhopper.commons.util.windowing.WindowFuture;
import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppClient;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.EnquireLink;
import com.cloudhopper.smpp.pdu.EnquireLinkResp;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.pdu.SubmitSm;
import com.cloudhopper.smpp.pdu.SubmitSmResp;
import com.cloudhopper.smpp.type.Address;

/**
 *
 * @author joelauer (twitter: @jjlauer or <a href="http://twitter.com/jjlauer" target=window>http://twitter.com/jjlauer</a>)
 */
public class SmppClient {
	private static final Logger logger = LoggerFactory.getLogger(SmppClient.class);

	public static void main(String[] args) {
		SmppClient.sendSmppSms("ntcsms", "10.26.240.160", 5016, "Test420", "Test420", "999109", "9969000000000000000", "hello,this is rockmobi.");
	}

	public static void sendSmppSms(String name, String host, int port, String systemId, String password, String fromCode, String toCode, String content) {

		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

		ScheduledThreadPoolExecutor monitorExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1, new ThreadFactory() {
			private AtomicInteger sequence = new AtomicInteger(0);

			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r);
				t.setName("SmppClientSessionWindowMonitorPool-" + sequence.getAndIncrement());
				return t;
			}
		});

		DefaultSmppClient clientBootstrap = new DefaultSmppClient(Executors.newCachedThreadPool(), 1, monitorExecutor);

		DefaultSmppSessionHandler sessionHandler = new ClientSmppSessionHandler();

		SmppSessionConfiguration config0 = new SmppSessionConfiguration();
		config0.setWindowSize(1);
		config0.setName(name);

		config0.setHost(host);
		config0.setPort(2776);
		config0.setConnectTimeout(10000);
		config0.setSystemId(systemId);
		config0.setPassword(password);
		config0.getLoggingOptions().setLogBytes(true);
		config0.setType(SmppBindType.TRANSCEIVER);

		// to enable monitoring (request expiration)
		config0.setRequestExpiryTimeout(30000);
		config0.setWindowMonitorInterval(15000);
		config0.setCountersEnabled(true);

		//
		// create session, enquire link, submit an sms, close session
		//
		SmppSession session0 = null;

		try {
			// create session a session by having the bootstrap connect a
			// socket, send the bind request, and wait for a bind response
			session0 = clientBootstrap.bind(config0, sessionHandler);

			System.out.println("Press any key to send enquireLink #1");
			System.in.read();

			// demo of a "synchronous" enquireLink call - send it and wait for a response
			EnquireLinkResp enquireLinkResp1 = session0.enquireLink(new EnquireLink(), 10000);
			logger.info("enquire_link_resp #1: commandStatus [" + enquireLinkResp1.getCommandStatus() + "=" + enquireLinkResp1.getResultMessage() + "]");

			@SuppressWarnings("rawtypes")
			WindowFuture<Integer, PduRequest, PduResponse> future0 = session0.sendRequestPdu(new EnquireLink(), 10000, true);
			if (!future0.await()) {
				logger.error("Failed to receive enquire_link_resp within specified time");
			} else if (future0.isSuccess()) {
				EnquireLinkResp enquireLinkResp2 = (EnquireLinkResp) future0.getResponse();
				logger.info("enquire_link_resp #2: commandStatus [" + enquireLinkResp2.getCommandStatus() + "=" + enquireLinkResp2.getResultMessage() + "]");
			} else {
				logger.error("Failed to properly receive enquire_link_resp: " + future0.getCause());
			}

			byte[] textBytes = CharsetUtil.encode(content, CharsetUtil.CHARSET_GSM);

			SubmitSm submit0 = new SubmitSm();

			submit0.setSourceAddress(new Address((byte) 0x03, (byte) 0x00, fromCode));
			submit0.setDestAddress(new Address((byte) 0x01, (byte) 0x01, toCode));
			submit0.setShortMessage(textBytes);

			SubmitSmResp submitResp = session0.submit(submit0, 10000);

			logger.info("sendWindow.size: {}", session0.getSendWindow().getSize());
			logger.info("submitResp: {}", submitResp.toString());

			session0.unbind(5000);
		} catch (Exception e) {
			logger.error("", e);
		}

		if (session0 != null) {
			logger.info("Cleaning up session... (final counters)");
			if (session0.hasCounters()) {
				logger.info("tx-enquireLink: {}", session0.getCounters().getTxEnquireLink());
				logger.info("tx-submitSM: {}", session0.getCounters().getTxSubmitSM());
				logger.info("tx-deliverSM: {}", session0.getCounters().getTxDeliverSM());
				logger.info("tx-dataSM: {}", session0.getCounters().getTxDataSM());
				logger.info("rx-enquireLink: {}", session0.getCounters().getRxEnquireLink());
				logger.info("rx-submitSM: {}", session0.getCounters().getRxSubmitSM());
				logger.info("rx-deliverSM: {}", session0.getCounters().getRxDeliverSM());
				logger.info("rx-dataSM: {}", session0.getCounters().getRxDataSM());
			}

			session0.destroy();
		}
		logger.info("Shutting down client bootstrap and executors...");
		clientBootstrap.destroy();
		executor.shutdownNow();
		monitorExecutor.shutdownNow();
		logger.info("Done. Exiting");
	}

	/**
	 * Could either implement SmppSessionHandler or only override select methods
	 * by extending a DefaultSmppSessionHandler.
	 */
	public static class ClientSmppSessionHandler extends DefaultSmppSessionHandler {

		public ClientSmppSessionHandler() {
			super(logger);
		}

		@Override
		public void firePduRequestExpired(@SuppressWarnings("rawtypes") PduRequest pduRequest) {
			logger.warn("PDU request expired: {}", pduRequest);
		}

		@Override
		public PduResponse firePduRequestReceived(@SuppressWarnings("rawtypes") PduRequest pduRequest) {
			PduResponse response = pduRequest.createResponse();

			// do any logic here

			return response;
		}

	}

}
