//package io.renren.smpp;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import com.logica.smpp.Data;
//import com.logica.smpp.Session;
//import com.logica.smpp.TCPIPConnection;
//import com.logica.smpp.pdu.BindReceiver;
//import com.logica.smpp.pdu.BindRequest;
//import com.logica.smpp.pdu.BindResponse;
//import com.logica.smpp.pdu.DeliverSM;
//import com.logica.smpp.pdu.PDU;
//
//@Component
//@Order(value = 1)
//public class SimpleSMSReceiver implements ApplicationRunner {
//	private static final Logger logger = LoggerFactory.getLogger(SimpleSMSReceiver.class);
//
//	/** 
//	 * Parameters used for connecting to SMSC (or SMPPSim)
//	 */
//	private Session session = null;
//	private String ipAddress = "10.12.51.27";
//	private int port = 5016;
//	private String systemId = "RockyMobi";
//	private String password = "r0ckY$tL";
//
//	//	private String rangeString = "4265";
//
//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		SimpleSMSReceiver.SmartRebindMain();
//	}
//
//	public static void main(String[] args) {
//		SimpleSMSReceiver.SmartRebindMain();
//	}
//
//	public static void SmartRebindMain() {
//		logger.info("Sms receiver starts");
//
//		SimpleSMSReceiver objSimpleSMSReceiver = new SimpleSMSReceiver();
//		objSimpleSMSReceiver.bindToSmsc();
//
//		try {
//			while (true) {
//				logger.info("objSimpleSMSReceiver receiveSms ......");
//				objSimpleSMSReceiver.receiveSms();
//				//				Thread.sleep(100);
//			}
//		} catch (Throwable t) {
//			logger.error("objSimpleSMSReceiver receiveSms error", t);
//		}
//	}
//
//	private void bindToSmsc() {
//		try {
//			// setup connection
//			TCPIPConnection connection = new TCPIPConnection(ipAddress, port);
//			connection.setReceiveTimeout(20 * 1000);
//			session = new Session(connection);
//
//			// set request parameters
//			BindRequest request = new BindReceiver();
//			request.setSystemId(systemId);
//			request.setPassword(password);
//			//			request.setAddressRange(rangeString);
//
//			// send request to bind
//			BindResponse response = session.bind(request);
//			if (response.getCommandStatus() == Data.ESME_ROK) {
//				logger.info("Sms receiver is connected to SMPPSim.");
//			}
//		} catch (Exception e) {
//			logger.error("Sms receiver is error to SMPPSim.", e);
//		}
//	}
//
//	private void receiveSms() {
//		try {
//
//			PDU pdu = session.receive(1500);
//
//			if (pdu != null) {
//				DeliverSM sms = (DeliverSM) pdu;
//
//				if ((int) sms.getDataCoding() == 0) {
//					//message content is English
//					logger.info("***** New Message Received *****");
//					logger.info("From: " + sms.getSourceAddr().getAddress());
//					logger.info("To: " + sms.getDestAddr().getAddress());
//					logger.info("Content: " + sms.getShortMessage());
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
