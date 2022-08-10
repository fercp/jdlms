package com.fersoft;

import org.openmuc.jdlms.*;
import org.openmuc.jdlms.datatypes.DataObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CosemClass(id = 1, version = 0)
public class Data extends CosemInterfaceObject {
    private static final Logger logger = LoggerFactory.getLogger(Data.class);

    @CosemAttribute(id = 2, type = DataObject.Type.LONG64)
    private final DataObject value;

    public Data(DlmsInterceptor interceptor, String instanceId, Long value) {
        super(instanceId, interceptor);
        this.value = DataObject.newInteger64Data(value);
    }

    public DataObject getValue() {
        return value;
    }

    @CosemMethod(id = 1)
    public void operate() throws IllegalMethodAccessException {
        logger.info("Operation method");
    }

}
