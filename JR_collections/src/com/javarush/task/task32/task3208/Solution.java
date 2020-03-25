package com.javarush.task.task32.task3208;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* 
RMI-2
*/
public class Solution {
    public static Registry registry;
    static Animal dogService, catService = null;

    // Pretend we're starting an RMI client as the CLIENT_THREAD thread
    public static Thread CLIENT_THREAD = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                for (String bindingName : registry.list()) {
                    Animal service = (Animal) registry.lookup(bindingName);
                    service.printName();
                    service.speak();
                }
            } catch (RemoteException | NotBoundException e) {
                e.printStackTrace();
            }
        }
    });

    // Pretend we're starting an RMI server as the SERVER_THREAD thread
    public static Thread SERVER_THREAD = new Thread(new Runnable() {
        @Override
        public void run() {
            //напишите тут ваш код
            try {
                Map<String, Remote> uniqNames = new HashMap<>();
                dogService = new Dog("Killer");
                catService = new Cat("Assassin");
                 final String UNIC_DOG_NAME = "digital.dog";
                 final String UNIC_CAT_NAME = "digital.cat";

                Remote dogStub = UnicastRemoteObject.exportObject(dogService, 2099);
                Remote catStub = UnicastRemoteObject.exportObject(catService, 2099);
                uniqNames.put(UNIC_CAT_NAME, catStub);
                uniqNames.put(UNIC_DOG_NAME, dogStub);

                registry = LocateRegistry.createRegistry(2099);
                for (Map.Entry<String, Remote> pair : uniqNames.entrySet()) {
                    registry.bind(pair.getKey(), pair.getValue());
                }

            } catch (RemoteException | AlreadyBoundException e) {
                e.printStackTrace();
            }
        }
    });

    public static void main(String[] args) throws InterruptedException, NoSuchObjectException {
        // Starting an RMI server thread
        SERVER_THREAD.start();
        Thread.sleep(1000);
        // Start the client
        CLIENT_THREAD.start();
        Thread.sleep(1000);
        UnicastRemoteObject.unexportObject(dogService, true);
        UnicastRemoteObject.unexportObject(catService, true);
    }
}