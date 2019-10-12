package io.renren.smpp;

import com.cloudhopper.smpp.SmppBindType;
import com.cloudhopper.smpp.SmppSession;
import com.cloudhopper.smpp.SmppSessionConfiguration;
import com.cloudhopper.smpp.impl.DefaultSmppClient;
import com.cloudhopper.smpp.impl.DefaultSmppSessionHandler;
import com.cloudhopper.smpp.pdu.DeliverSm;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import io.renren.common.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class SmppReceiverMessage extends DefaultSmppSessionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmppReceiverMessage.class);

    /**
     * @param args
     */
    private SmppSession session = null;
    /*private String ipAddress = "10.0.21.175";
    private String systemId = "smppclient1";
    private String password = "password";
    private int port = 2775;*/
    private static String host = "10.12.51.27";
    private static String systemId = "RockyMobi";
    private static String password = "r0ckY$tL";
    private static int port = 5016;

    public static void main(String[] args) {
        System.out.println("Program starts...");
        SmppReceiverMessage objSimpleCloudHopperReceiver = new SmppReceiverMessage();
        objSimpleCloudHopperReceiver.bindToSMSC();
        objSimpleCloudHopperReceiver.waitForExitSignal();
    }

    private void bindToSMSC() {
        DefaultSmppClient smppClient = new DefaultSmppClient();

        SmppSessionConfiguration config0 = new SmppSessionConfiguration();
        config0.setHost(host);
        config0.setPort(port);
        config0.setSystemId(systemId);
        config0.setPassword(password);
        config0.setType(SmppBindType.RECEIVER);

        try {
            this.session = smppClient.bind(config0, this);
            System.out.println("Connected to SMSC...");
            System.out.println("Ready to receive PDU...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public PduResponse firePduRequestReceived(PduRequest pduRequest) {
        PduResponse response = pduRequest.createResponse();
        DeliverSm sms = (DeliverSm) pduRequest;

        if ((int)sms.getDataCoding() == 0 ) {
            //message content is English
            System.out.println("***** New English Message Received *****");
            System.out.println("From: " + sms.getSourceAddress().getAddress());
            System.out.println("To: " + sms.getDestAddress().getAddress());
            System.out.println("Content: " + new String(sms.getShortMessage()));
            String msisdn = sms.getDestAddress().getAddress();
            String shortMessage = new String(sms.getShortMessage());
            if (shortMessage.toUpperCase().startsWith("SUB")) {
                //订阅消息
                LoggerUtils.info(LOGGER, "receive sub message from msisdn:"+msisdn+",content:"+shortMessage);

            }else if(shortMessage.toUpperCase().startsWith("UNSUB")) {
                //取消订阅
                LoggerUtils.info(LOGGER, "receive unsub message from msisdn:"+msisdn+",content:"+shortMessage);

            }else if (shortMessage.equalsIgnoreCase("RENEW")) {
                //续订
                LoggerUtils.info(LOGGER, "receive renew message from msisdn:"+msisdn+",content:"+shortMessage);
                //修改订单信息

            }else {//无用的消息，需给用户回复
                LoggerUtils.info(LOGGER, "receive invalid message from msisdn:"+msisdn+",content:"+shortMessage);
            }
        }
        return response;
    }

    private void waitForExitSignal() {

        System.out.println("Press any key to exit");
        try {
            System.in.read();
            this.session.unbind(0);
            System.out.println("System terminated");
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
