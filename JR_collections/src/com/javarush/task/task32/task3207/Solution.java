package com.javarush.task.task32.task3207;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/* 
К серверу по RMI
*/
public class Solution {
    public static final String UNIC_BINDING_NAME = "double.string";
    public static Registry registry;

    // Pretend we're starting an RMI client as the CLIENT_THREAD thread
    public static Thread CLIENT_THREAD = new Thread(new Runnable() {
        @Override
        public void run() {
            final DoubleString service;
            try {
                service = (DoubleString)registry.lookup(UNIC_BINDING_NAME);
                System.out.println(service.doubleString("Dump and "));
            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        }
    });

    public static void main(String[] args) {
        // Pretend we're starting an RMI server as the main thread
        Remote stub = null;
        final DoubleString service;
        try {
            registry = LocateRegistry.createRegistry(2099);
            service = new DoubleStringImpl();

            stub = UnicastRemoteObject.exportObject(service, 0);
            registry.bind(UNIC_BINDING_NAME, stub);

            CLIENT_THREAD.start();
            CLIENT_THREAD.join();

            UnicastRemoteObject.unexportObject(service, true);

        } catch (RemoteException | AlreadyBoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}