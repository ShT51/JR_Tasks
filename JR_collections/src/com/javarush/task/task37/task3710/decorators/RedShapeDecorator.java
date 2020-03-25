package com.javarush.task.task37.task3710.decorators;

import com.javarush.task.task37.task3710.shapes.Shape;

public class RedShapeDecorator extends ShapeDecorator {
    protected Shape shape;

    public RedShapeDecorator(Shape shape) {
        super(shape);
        this.shape = shape;
    }

    @Override
    public void draw() {
        setBorderColor(this.shape);
        shape.draw();
    }

    private void setBorderColor(Shape shape) {
        System.out.printf("Setting the border color for %s to red.\n", shape.getClass().getSimpleName());
    }
}
