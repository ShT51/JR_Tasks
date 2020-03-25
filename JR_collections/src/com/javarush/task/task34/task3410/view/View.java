package com.javarush.task.task34.task3410.view;

import com.javarush.task.task34.task3410.controller.*;
import com.javarush.task.task34.task3410.model.GameObjects;

import javax.swing.*;

public class View extends JFrame {
    private Controller controller;
    private Field field;
    private EventListener eventListener;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void init() {
        field = new Field(this);
        add(field);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setTitle("Сокобан");
        setVisible(true);
    }

    public void setEventListener(EventListener eventListener) {
        field.setEventListener(eventListener);
    }

    public void update() {
        this.field.repaint();
    }

    public GameObjects getGameObjects() {
        return this.controller.getGameObjects();
    }

    public void completed(int level) {
        this.update();
        String message = String.format("Well Done! You have completed level %d", level);
        JOptionPane.showMessageDialog(this, message);
        controller.startNextLevel();
    }
}
