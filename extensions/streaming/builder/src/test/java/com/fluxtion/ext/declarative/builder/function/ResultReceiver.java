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
package com.fluxtion.ext.declarative.builder.function;

/**
 *
 * @author greg
 */
public class ResultReceiver {
    
    private char myChar;
    private byte myByte;
    private short myShort;
    private int myInt;
    private long myLong;
    private float myFloat;
    private double myDouble;

    public void setMyFloat(float myFloat) {
        this.myFloat = myFloat;
    }

    public void setMyChar(char myChar) {
        this.myChar = myChar;
    }

    public void setMyInt(int myInt) {
        this.myInt = myInt;
    }

    public void setMyByte(byte myByte) {
        this.myByte = myByte;
    }

    public void setMyShort(short myShort) {
        this.myShort = myShort;
    }

    public void setMyLong(long myLong) {
        this.myLong = myLong;
    }

    public void setMyDouble(double myDouble) {
        this.myDouble = myDouble;
    }

    public char getMyChar() {
        return myChar;
    }

    public byte getMyByte() {
        return myByte;
    }

    public short getMyShort() {
        return myShort;
    }

    public int getMyInt() {
        return myInt;
    }

    public long getMyLong() {
        return myLong;
    }

    public float getMyFloat() {
        return myFloat;
    }

    public double getMyDouble() {
        return myDouble;
    }
    
    
    
}
