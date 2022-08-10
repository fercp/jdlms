package com.fersoft;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "dlms", mixinStandardHelpOptions = true, version = "1.0",
        description = "A very simple DLMS application")
public class DlmsApplication implements Callable<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(DlmsApplication.class);

    @CommandLine.Option(names = {"--server"}, description = "run server")
    private Boolean server;

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


    private String[] args;

    public DlmsApplication(String... args) {
        this.args = args;
    }

    public static void main(String... args) {
        new CommandLine(new DlmsApplication(args)).execute(args);
    }

    @Override
    public Integer call() {
        if (Boolean.TRUE.equals(server)) {
            return new CommandLine(new Server()).execute(Arrays.copyOfRange(args, 1, args.length));
        }
        return new CommandLine(new Client()).execute(args);
    }

}
