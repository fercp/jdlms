# Simple DLMS client/server

A very simple dlms server & client implementation based on <b>openmuc jdlms library</b>.

## Building the executable

```shell
  mvn clean package
```


## Usage

```
Usage: [-hV] [--server] [-d=<deviceId>] [-i=<instanceId>]
[-m=<manufacturerId>] [-n=<deviceName>] [-p=<port>] [-v=<value>]
A very simple DLMS application
-d, --device=<deviceId>   logical device id
-h, --help                Show this help message and exit.
-i, --id=<instanceId>     instance id
-m, --manufacturer=<manufacturerId>
manufacturer id of logical device
-n, --name=<deviceName>   logical device name
-p, --port=<port>         port of which server listening
--server              run server
-v, --value=<value>       value server returning to clients
-V, --version             Print version information and exit.
```


1. Start server

```shell
     java -jar target/jdlms-1.0.jar --server
```

2. Test client

```shell
     java -jar target/jdlms-1.0.jar 
```

## Options

```shell
     java -jar target/jdlms-1.0.jar --help
```