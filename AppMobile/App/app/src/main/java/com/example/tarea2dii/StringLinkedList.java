package com.example.tarea2dii;

public class StringLinkedList {

    // Atributos de la clase StringLinkedList
    private Node head; private int size;

    // Clase privada Node
    private class Node
    {
        // Atributos de la clase Node
        private String data; private Node next;
        // Constructor de la clase Node
        public Node(String data) { this.data = data; this.next = null; }
    }

    // Constructor de la clase StringLinkedList
    public StringLinkedList() { head = null; size = 0; }

    // Método encargado de verificar si la lista estsá vacía
    public boolean isEmpty() { return  head == null;}

    // Método que se encarga de retornar el tamaño de la lista
    public int getSize(){ return size; }

    // Método que se encarga de insertar nodos en la lista
    public void insert(String data)
    {
        Node new_node = new Node(data);

        // Si la lista está vacía
        if (isEmpty()) {head = new_node;}
        // Si la lista posee elementos
        else
        {
            Node currentNode = head;
            while (currentNode.next != null) {currentNode = currentNode.next;}
            currentNode.next = new_node;
        }
        size++;
    }

    // Método que se encarga de mostrar los elementos
    public String get(int index)
    {
        // Si sobrepasa el rango de la lista
        if (index < 0 || index >= size) {throw new IndexOutOfBoundsException();}

        Node currentNode = head;
        for (int i = 0; i < index; i++) {currentNode = currentNode.next;}
        return currentNode.data;
    }

    // Método que se encarga de eliminar elementos de la lista
    public String remove(int index)
    {
        // Si sobrepasa el rango de la lista
        if (index < 0 || index >= size) {throw new IndexOutOfBoundsException();}
        String removed_data;
        if (index == 0) {removed_data = head.data; head = head.next;}
        else
        {
            Node currentNode = head;
            for (int i = 0; i < index - 1; i++) {currentNode = currentNode.next;}
            removed_data = currentNode.next.data;
            currentNode.next = currentNode.next.next;
        }
        size--;
        return removed_data;
    }

    public String remove() {
        return remove(size - 1);
    }
}
