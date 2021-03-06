/* 
 *  Copyright (C) [2016]-[2017] V12 Technology Limited
 *  
 *  This software is subject to the terms and conditions of its EULA, defined in the
 *  file "LICENCE.txt" and distributed with this software. All information contained
 *  herein is, and remains the property of V12 Technology Limited and its licensors, 
 *  if any. This source code may be protected by patents and patents pending and is 
 *  also protected by trade secret and copyright law. Dissemination or reproduction 
 *  of this material is strictly forbidden unless prior written permission is 
 *  obtained from V12 Technology Limited.  
 */
package com.fluxtion.ext.declarative.builder.helpers;

import com.fluxtion.api.annotations.Initialise;
import com.fluxtion.api.annotations.OnEvent;

/**
 *
 * @author Greg Higgins
 */
public class TestResultListener {
    public Object testResult;
    public boolean receivedNotification;

    public TestResultListener(Object testResult) {
        this.testResult = testResult;
    }

    public TestResultListener() {
    }
    
    @OnEvent
    public void onEvent(){
        receivedNotification = true;
    }
    
    public void reset(){
        receivedNotification = false;
    }
    
    @Initialise
    public void init(){
        receivedNotification = false;
    }
    
}
