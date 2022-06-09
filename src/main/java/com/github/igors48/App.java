package com.github.igors48;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortException;
import jssc.SerialPortList;

public class App
{
    public static void main(String[] args) {
        String[] names = SerialPortList.getPortNames();
        System.out.println(names);

        SerialPort port = new SerialPort("/dev/ttyUSB0");

        try {
            port.openPort();
            //Miniterm on /dev/ttyUSB0  115200,8,N,1 ---
            port.setParams(
                    SerialPort.BAUDRATE_115200,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE
            );

            port.addEventListener((SerialPortEvent event) -> {
                if (event.isRXCHAR()) {
                    try {
                        String s = port.readString();
                        System.out.print(s);
                    } catch (SerialPortException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
            // https://www.codeproject.com/Tips/801262/Sending-and-receiving-strings-from-COM-port-via-jS
            boolean result = port.writeString("Hurrah!");
            System.out.println("###Result " + result);
        } catch (SerialPortException e) {
            throw new RuntimeException(e);
        }
    }

}
