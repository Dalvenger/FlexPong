package com.example.banana.testdevice2;

public interface SuperpoweredUSBAudioHandler {
    void onUSBAudioDeviceAttached(int deviceID);
    void onUSBMIDIDeviceAttached(int deviceID);
    void onUSBDeviceDetached(int deviceID);
}


