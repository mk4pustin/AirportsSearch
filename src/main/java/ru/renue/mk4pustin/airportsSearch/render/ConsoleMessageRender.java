package ru.renue.mk4pustin.airportsSearch.render;

public class ConsoleMessageRender implements MessageRender {
    @Override
    public void render(String message) {
        System.out.println(message);
    }
}
