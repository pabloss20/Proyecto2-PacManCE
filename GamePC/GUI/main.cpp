#include <iostream>
#include <limits.h>

// Matriz de juego (la matriz se coloca acá debido a que no se pudo pasar como argumento ya sea en el constructor o método)
int matrix[7][7] =
        {
                { 1, 1, 0, 0, 0, 1, 1},
                { 0, 1, 1, 1, 1, 1, 1},
                { 1, 0, 0, 1, 0, 1, 1},
                { 0, 1, 1, 1, 0, 0, 1},
                { 0, 1, 0, 1, 1, 1, 1},
                { 0, 1, 0, 0, 1, 0, 0},
                { 1, 0, 1, 1, 1, 1, 1}
        };

// Clase Bactracking la cual encuentra la ruta de menor costo
class Backtracking{

public:

    int *start; // inicio
    int *end;   // fin
    int visited[7][7] = {0}; // matriz de 0 el cual marca las posiciones visitadas
    int shortLength = INT_MAX; // la ruta más corta - contador
    int length=0; // contador
    bool hasPath = false; // verifica si hay ruta o no

    // Constructor de la clase
    Backtracking(int start[], int end[])
    {
        this->start = start;
        this->end = end;
    }

    // Método el cual inicia el proceso de búsqueda de la ruta
    void findPath(){
        visit(start[0], start[1]);
    }

    // Método para visitar una posicion y realizar otro movimiento recursivamente
    void visit(int x, int y){

        // Caso base - alcanza la posicion de destino
        if(x == end[0] && y == end[1]){

            // actualiza la ruta - marca true si hay una posible ruta
            hasPath=true;

            // Almacena el minimo de la ruta
            if(length < shortLength)
                shortLength= length;

            // Realiza el bactracking para explorar más ruta
            return;
        }

        // Marca la posicion actual como visitada
        visited[x][y] = 1;

        // incremente la ruta actual +1
        length++;

        // Right
        if(canVisit(x+1, y)){
            visit(x+1, y);
            std::cout << "Right " << " " << x << " " << y << std::endl;
            // Agregar la cola acá para que agregue la posicion
        }

        // Down
        if(canVisit(x, y+1)){
            visit(x, y+1);
            std::cout << "Down" << " " << x << " " << y << std::endl;
            // Agregar la cola acá para que agregue la posicion
        }

        // Left
        if(canVisit(x-1, y)){
            visit(x-1, y);
            std::cout << "Left " << " " <<  x << " " << y << std::endl;
            // Agregar la cola acá para que agregue la posicion
        }

        // Up
        if(canVisit(x, y-1)){
            visit(x, y-1);
            std::cout << "Up " << " " << x << " " << y << std::endl;
            // Agregar la cola acá para que agregue la posicion
        }

        //Backtrack by unvisiting the current cell and
        //decrementing the value of current path length
        visited[x][y] = 0;
        length--;
    }

    // Analiza si la posicion i j es una posicion valida o no
    bool canVisit(int x, int y){
        // Columnas en la matriz
        int m=sizeof(matrix[0])/sizeof(matrix[0][0]);
        // Filas en la matriz
        int n=sizeof(matrix)/sizeof(matrix[0]);
        // Analiza los extremos de la matriz
        if(x<0 || y<0 || x>=m || y>=n)
            return false;
        // Analiza si la posicion ya fue visitada o si es 0
        if(matrix[x][y]==0 || visited[x][y]==1)
            return false;
        return true;
    }
};
//Driver Code
int main() {

    // Valores para el constructor de la clase - posicion inicial hasta la posicion final
    int start[] = {0, 0};
    int end[] = {6, 6};

    Backtracking backtracking(start, end);
    backtracking.findPath();

    // Muestra el largo de la ruta si la encontró
    if(backtracking.hasPath)
        std::cout << "Valor de la ruta más corta: " << backtracking.shortLength;
    else
        std::cout << "No existe ruta";
}