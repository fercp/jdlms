package com.fersoft;

import org.openmuc.jdlms.*;
import org.openmuc.jdlms.datatypes.DataObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.Callable;

public class Client implements Callable<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    @CommandLine.Option(names = {"-h", "--host"}, description = "server address", defaultValue = "127.0.0.1")
    private String host;

    @CommandLine.Option(names = {"-p", "--port"}, description = "server port", defaultValue = "5555")
    private Integer port;

    @CommandLine.Option(names = {"-i", "--id"}, description = "instance id", defaultValue = "0.0.0.2.1.255")
    private String instanceId;


    @Override
    public Integer call() throws Exception {
        InetAddress inetAddress = InetAddress.getByName(host);

        TcpConnectionBuilder connectionBuilder = new TcpConnectionBuilder(inetAddress)
                .setPort(port)
                .setRawMessageListener(rawMessageData -> logger.debug("Message received {}", rawMessageData));

        try (DlmsConnection dlmsConnection = connectionBuilder.build()) {
            GetResult result = dlmsConnection.get(new AttributeAddress(1, instanceId, 2));

            if (result.getResultCode() == AccessResultCode.SUCCESS) {
                DataObject resultData = result.getResultData();
                logger.info("Value received {}", resultData.getValue().toString());
            }
        } catch (IOException e) {
            logger.error("Exception during call", e);
            return -1;
        }
        return 0;
    }
}
