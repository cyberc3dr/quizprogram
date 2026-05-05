package ru.cyberc3dr.quiz.tree;

/**
 * Интерфейс узла-контейнера.
 */
public interface Container extends Node {
    void add(Node node);

    void remove(Node node);
}
