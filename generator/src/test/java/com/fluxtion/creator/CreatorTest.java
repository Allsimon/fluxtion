/* 
 * Copyright (c) 2019, V12 Technology Ltd.
 * All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Server Side Public License, version 1,
 * as published by MongoDB, Inc.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Server Side Public License for more details.
 *
 * You should have received a copy of the Server Side Public License
 * along with this program.  If not, see 
 * <http://www.mongodb.com/licensing/server-side-public-license>.
 */
package com.fluxtion.creator;

import com.fluxtion.api.event.Event;
import com.fluxtion.builder.generation.GenerationContext;
import com.fluxtion.builder.node.SEPConfig;
import com.fluxtion.creator.MathFactory.FunctionCfg;
import com.fluxtion.generator.util.BaseSepTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author gregp
 */
public class CreatorTest extends BaseSepTest {

    /**
     * Test of createModel method, of class Creator.
     */
    @Test
//    @Ignore
    public void testCreateModel() throws Exception {
        FunctionCfg mathCfg = new FunctionCfg("max", "src::intValue");
        Node node = new Node("com.text.xx", "nodeId_1", true);
        node.setConfigBean(mathCfg);
        //
        Yaml beanLoader = new Yaml();
        String dump = beanLoader.dumpAsMap(node);
        System.out.println("dump:\n" + dump);

        Node loadedNode = beanLoader.loadAs(dump, Node.class);
        mathCfg = loadedNode.getConfigBean();
        System.out.println("config:" + mathCfg);
    }

    @Test
    public void parserTest() throws Exception {
        ConfigParser parser = new ConfigParser();
        String outPkg = GenerationContext.SINGLETON.getPackageName();
        String configDoc = ""
                + "outputPackage: " + outPkg + "\n"
                + "outputSepConfigClass: " + outPkg + ".MySepCfg\n"
                + "processorId: processor1\n"
                + "auditorClass: com.fluxtion.creator.TestAuditor\n"
                + "events: \n"
                + "  - type: com.config.MySepCfg.PnlEvent\n"
                + "    id: pnlEvent\n"
                + "  - type: CharEvent\n"
                + "    id: charEvent\n"
                + "nodes:\n"
                + "    - id: dataHandler\n"
                + "      type: com.config.DataHandler\n"
                + "      publicAccess: true\n"
                + "      events: \n"
                + "         - eventId: charEvent\n"
                + "           #filter: 10\n"
                + "    - id: calculator\n"
                + "      type: com.config.Calculator\n"
                + "      publicAccess: true\n"
                + "      nodes:\n"
                + "          - node: dataHandler\n"
                + "            name: data\n"
                + "";
        CreatorConfig cfg = parser.parse(configDoc);
        Creator instance = new Creator();
        instance.createModel(cfg);
        //
//        compileCfg.setCachedCompiler(GenerationContext.SINGLETON.getJavaCompiler());
        buildAndInitSep((Class<? extends SEPConfig>) Class.forName(cfg.getOutputSepConfigClass()));
        //
        TestAuditor auditor = getField("auditor");
        auditor.matchRegisteredNodes("dataHandler", "calculator");
        Assert.assertFalse(auditor.isProcessingComplete());
        //
        Event charEvent = (Event) Class.forName(outPkg + ".CharEvent").newInstance();
        Event pnl = (Event) Class.forName(outPkg + ".PnlEvent").newInstance();
        onEvent(charEvent);
        onEvent(pnl);
        onEvent(charEvent);
        Assert.assertTrue(auditor.isProcessingComplete());
        //match event stack
        auditor.matchEvents(charEvent.getClass(), charEvent.getClass());
        //match callback methods
        auditor.matchCallbackMethodOrderPartial("handlerCharEvent", "onEvent", "handlerCharEvent");
        auditor.matchCallbackMethod("handlerCharEvent", "onEvent", "handlerCharEvent", "onEvent");
    }

    @Test
    public void predefinedEvent() throws Exception {
        ConfigParser parser = new ConfigParser();
        String outPkg = GenerationContext.SINGLETON.getPackageName();
        String configDoc = ""
                + "outputPackage: " + outPkg + "\n"
                + "outputSepConfigClass: " + outPkg + ".MySepCfg\n"
                + "processorId: processor1\n"
                + "auditorClass: com.fluxtion.creator.TestAuditor\n"
                + "events: \n"
                + "  - type: com.fluxtion.creator.MyPredefinedEvent\n"
                + "    id: myEvent\n"
                + "nodes:\n"
                + "    - id: dataHandler\n"
                + "      type: com.config.DataHandler\n"
                + "      publicAccess: true\n"
                + "      events: \n"
                + "         - eventId: myEvent\n"
                + "    - id: myProcessor\n"
                + "      type: com.fluxtion.creator.MyPredefinedNode\n"
                + "      publicAccess: true\n"
                + "      nodes:\n"
                + "          - node: dataHandler\n"
                + "            name: parent\n"
                + "";
        CreatorConfig cfg = parser.parse(configDoc);
        Creator instance = new Creator();
        instance.createModel(cfg);
        //
//        compileCfg.setCachedCompiler(GenerationContext.SINGLETON.getJavaCompiler());
        buildAndInitSep((Class<? extends SEPConfig>) Class.forName(cfg.getOutputSepConfigClass()));
        TestAuditor auditor = getField("auditor");
        auditor.matchRegisteredNodes("dataHandler", "myProcessor");
        Assert.assertFalse(auditor.isProcessingComplete());
        //
        onEvent(new MyPredefinedEvent());
        onEvent(new MyPredefinedEvent());
        Assert.assertTrue(auditor.isProcessingComplete());
        //match event stack
        auditor.matchEvents(MyPredefinedEvent.class, MyPredefinedEvent.class);
        //match callback methods
        auditor.matchCallbackMethodOrderPartial("handlerMyPredefinedEvent", "process");
        auditor.matchCallbackMethod("handlerMyPredefinedEvent", "process", "handlerMyPredefinedEvent", "process");
    }

    @Test
    public void factoryMethod() throws Exception {
        ConfigParser parser = new ConfigParser();
        String outPkg = GenerationContext.SINGLETON.getPackageName();
        String configDoc = ""
                + "outputPackage: " + outPkg + "\n"
                + "outputSepConfigClass: " + outPkg + ".MySepCfg\n"
                + "processorId: processor1\n"
                + "auditorClass: com.fluxtion.creator.TestAuditor\n"
                + "events: \n"
                + "  - type: com.fluxtion.creator.MyPredefinedEvent\n"
                + "    id: myEvent\n"
                + "nodes:\n"
                + "    - id: dataHandler\n"
                + "      type: com.config.DataHandler\n"
                + "      publicAccess: true\n"
                + "      events: \n"
                + "         - eventId: myEvent\n"
                + "    - id: myProcessor\n"
                + "      type: com.fluxtion.creator.MyPredefinedNode\n"
                + "      publicAccess: true\n"
                + "      nodes:\n"
                + "          - node: dataHandler\n"
                + "            name: parent\n"
                + "    - id: max\n"
                + "      type: com.fluxtion.creator.MathFactory$Value\n"
                + "      factoryType: com.fluxtion.creator.MathFactory\n"
                + "      publicAccess: true\n"
                + "      configBean: !!com.fluxtion.creator.MathFactory$FunctionCfg\n"
                + "          functionName: max\n"
                + "          methodRef: myProcessor\n"
                + "";
        CreatorConfig cfg = parser.parse(configDoc);
        Creator instance = new Creator();
        instance.createModel(cfg);
        //
//        compileCfg.setCachedCompiler(GenerationContext.SINGLETON.getJavaCompiler());
        buildAndInitSep((Class<? extends SEPConfig>) Class.forName(cfg.getOutputSepConfigClass()));
        TestAuditor auditor = getField("auditor");
        auditor.matchRegisteredNodes("dataHandler", "myProcessor", "max");
        Assert.assertFalse(auditor.isProcessingComplete());
        //
        onEvent(new MyPredefinedEvent());
        onEvent(new MyPredefinedEvent());
        Assert.assertTrue(auditor.isProcessingComplete());
        //match event stack
        auditor.matchEvents(MyPredefinedEvent.class, MyPredefinedEvent.class);
        //match callback methods
        auditor.matchCallbackMethodOrderPartial("handlerMyPredefinedEvent", "process");
        auditor.matchCallbackMethod("handlerMyPredefinedEvent", "process", "evauateMax", "handlerMyPredefinedEvent", "process", "evauateMax");
    }

    public static class MyNode {

        private String name;
        private Map config;

        public MyNode() {
            config = new HashMap();
        }

        public Map getConfig() {
            return config;
        }

        public void setConfig(Map config) {
            this.config = config;
        }

        /**
         * Get the value of name
         *
         * @return the value of name
         */
        public String getName() {
            return name;
        }

        /**
         * Set the value of name
         *
         * @param name new value of name
         */
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "MyNode{" + "name=" + name + ", config=" + config + '}';
        }

    }

}
