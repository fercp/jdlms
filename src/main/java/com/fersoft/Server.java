package com.fersoft;

import org.openmuc.jdlms.*;
import org.openmuc.jdlms.datatypes.DataObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.util.Collections;
import java.util.concurrent.Callable;

/**
 * Sample DLMS Server
 */
@CommandLine.Command(name = "server", mixinStandardHelpOptions = true, version = "1.0",
        description = "A very simple DLMS server implementation")
public class Server implements Callable<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(Server.class);
    @CommandLine.Option(names = {"-p", "--port"}, description = "port of which server listening", defaultValue = "5555")
    private Integer port;

    @CommandLine.Option(names = {"-v", "--value"}, description = "value server returning to clients", defaultValue = "1")
    private Long value;

    @CommandLine.Option(names = {"-d", "--device"}, description = "logical device id", defaultValue = "1")
    private Integer deviceId;

    @CommandLine.Option(names = {"-i", "--id"}, description = "instance id", defaultValue = "0.0.0.2.1.255")
    private String instanceId;

    @CommandLine.Option(names = {"-n", "--name"}, description = "logical device name", defaultValue = "test-device")
    private String deviceName;

    @CommandLine.Option(names = {"-m", "--manufacturer"}, description = "manufacturer id of logical device", defaultValue = "FER")
    private String manufacturerId;

    private LogicalDevice getLogicalDevice() {
        LogicalDevice logicalDevice = new LogicalDevice(deviceId, deviceName, manufacturerId, deviceId);
        logicalDevice.registerCosemObject(Collections.singletonList(new Data(new Interceptor(), instanceId, value)));
        return logicalDevice;
    }

    @Override
    public Integer call() throws Exception {
        logger.info("Starting server on port:{} deviceId:{} deviceName:{} manufacturer:{}", port, deviceId, deviceName, manufacturerId);
        logger.info("Value :{}", value);
        LogicalDevice logicalDevice = getLogicalDevice();
        try (DlmsServer server = DlmsServer.tcpServerBuilder().setTcpPort(port).registerLogicalDevice(logicalDevice).build()) {
            logger.info("Server started...");
            while (true) {
                Thread.sleep(100L);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Exception in the main loop", e);
        }
        logger.info("Exiting server.");
        return 0;
    }

    static class Interceptor implements DlmsInterceptor {

        @Override
        public DataObject intercept(DlmsInvocationContext ctx) throws DlmsAccessException {
            return ctx.proceed();
        }
    }
}
