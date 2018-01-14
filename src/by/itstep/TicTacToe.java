package by.itstep;

import java.util.Scanner;

public class TicTacToe {

    public static int winWeight = 99999;
    //умная игра компьютера
    public static void main(String[] args) {
        char[][] gameField = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
        showGameField(gameField);
        int result;
        char symbol = 'x';
        do {
            if (symbol == 'x') {
                makeMove(gameField, symbol);
            }else{
                makeSmartMove(gameField, symbol, '0');
            }
            symbol = symbol == 'x' ? '0' : 'x';
            showGameField(gameField);
            result = checkField(gameField);
        } while (result == 0);
        switch (result) {
            case 1:
                System.out.println(" Победил х");
                break;
            case 2:
                System.out.println(" Победил 0");
                break;
            case 3:
                System.out.println(" Ничья");

        }
    }
    // Комп ходящий рандомно
    public static void main1(String[] args) {
        char[][] gameField = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
        showGameField(gameField);
        int result;
        char symbol = 'x';
        do {
            if (symbol == 'x') {
                makeMove(gameField, symbol);
            }else{
                makeRandomMove(gameField, symbol);
            }
            symbol = symbol == 'x' ? '0' : 'x';
            showGameField(gameField);
            result = checkField(gameField);
        } while (result == 0);
        switch (result) {
            case 1:
                System.out.println(" Победил х");
                break;
            case 2:
                System.out.println(" Победил 0");
                break;
            case 3:
                System.out.println(" Ничья");

        }
    }

    // Два компа ходящих рандомно (c паузой)
    public static void main2(String[] args) {
        char[][] gameField = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
        showGameField(gameField);
        int result;
        char symbol = 'x';
        do {
            try {
                Thread.sleep(1000);
                makeRandomMove(gameField, symbol);
            } catch (InterruptedException e) {
            e.printStackTrace();
        }
            symbol = symbol == 'x' ? '0' : 'x';
            showGameField(gameField);
            result = checkField(gameField);
        } while (result == 0);
        switch (result) {
            case 1:
                System.out.println(" Победил х");
                break;
            case 2:
                System.out.println(" Победил 0");
                break;
            case 3:
                System.out.println(" Ничья");

        }
    }
 public static void makeSmartMove(char [][] gameField, char symbol,char opponentSymbol){
        int maxWeight = 0;
        int targetX = 0;
        int targetY = 0;
        for(int i = 0; i < gameField.length; i++) {
            for(int j = 0 ; j < gameField[i].length; j++) {
                if(gameField[i][j] != ' ') {
                    continue;
                }
                int weight = checkFieldWeight(gameField, i, j, symbol, opponentSymbol);
                if(weight == winWeight) {
                    targetX = i;
                    targetY = j;
                    break;
                }
                if(weight > maxWeight) {
                    maxWeight = weight;
                    targetX = i;
                    targetY = j;
                }
            }
        }

        gameField[targetX][targetY] = symbol;

 }

    private static int checkFieldWeight(char[][] gameField, int i, int j, char symbol, char opponentSymbol) {
     //check win condition
        gameField[i][j] = symbol;
        int result = checkField(gameField);
        if(result == 2){
            return winWeight;
        }
        int opponentResult = checkField(gameField);
        gameField[i][j] = opponentSymbol;
        if(result == 1){
            return winWeight;
        }
        gameField[i][j] = ' ';
        boolean isCenter = false;
        boolean isCorner = false;
        int initialWeight = 620;
        if(i == 1 && j == 1)
        {
            isCenter = true;
        } else if(i == j || (i == 0 && j == gameField.length) || (i == 0 && j == gameField.length)) {
            initialWeight = 930;
            isCorner = true;
        }
        
        if(isCenter) {
            return winWeight;
        }
        for(int k = 0; k < gameField[i].length; k++) {
            if(gameField[i][k] == opponentSymbol) {
                initialWeight -= 310;
                break;
            }
            if(gameField[i][k] == ' ') {
                initialWeight -= 100;
            }
        }

        for(int k = 0; k < gameField.length; k++) {
            if(gameField[k][j] == opponentSymbol) {
                initialWeight -= 310;
                break;
            }
            if(gameField[k][j] == ' ') {
                initialWeight -= 100;
            }
        }

        if(isCorner) {
            //position itself is empty
            boolean opponentSymbolMet = false;
            initialWeight -= 100;
            if(gameField[1][1] == ' ') {
                initialWeight -= 100;
            } else if(gameField[1][1] ==  opponentSymbol) {
                initialWeight -= 210;
                opponentSymbolMet = true;
            }
            if(!opponentSymbolMet) {
                int oppositeCornerX = 0;
                int oppositeCornerY = 0;
                oppositeCornerX = i == 0 ? gameField.length - 1 : 0;
                oppositeCornerY = j == 0 ? gameField.length - 1 : 0;
                if (gameField[oppositeCornerX][oppositeCornerY] == ' ') {
                    initialWeight -= 100;
                } else if (gameField[oppositeCornerX][oppositeCornerY] == opponentSymbol) {
                    initialWeight -= 210;
                }
            }
        }

        return initialWeight;
    }

    public static void showGameField(char[][] gameField) {
        System.out.println(gameField[0][0] + "|" + gameField[0][1] + "|" + gameField[0][2]);
        System.out.println("-----");
        System.out.println(gameField[1][0] + "|" + gameField[1][1] + "|" + gameField[1][2]);
        System.out.println("-----");
        System.out.println(gameField[2][0] + "|" + gameField[2][1] + "|" + gameField[2][2]);
    }

    // мы знаем чем мы ходим х или 0, надо узнать куда ставить
    public static void makeMove(char[][] gameField, char symbol) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Сейчас ходит " + symbol);
        int n;
        int m;
        int number;
        do {
            number = scanner.nextInt();
            n = 2 - (number - 1) / 3;
            m = (number - 1) % 3;
        } while (number < 1 || number > 9 || gameField[n][m] != ' ');
        gameField[n][m] = symbol;
    }

    //компьютер ходит
    public static void makeRandomMove(char[][] gameField, char symbol) {
        System.out.println("Сейчас ходит " + symbol);
        int n;
        int m;
        int number;
        do {
            number = 1 + (int)(Math.random() * 9);
            n = 2 - (number - 1) / 3;
            m = (number - 1) % 3;
        } while (number < 1 || number > 9 || gameField[n][m] != ' ');
        gameField[n][m] = symbol;
    }


    public static int checkField(char[][] gameField) {
        for (int i = 0; i < 3; i++) {
            // проверили все строки
            if (gameField[i][0] == gameField[i][1]
                    && gameField[i][0] == gameField[i][2]) {
                if (gameField[i][0] == 'x') {
                    return 1;
                }
                if (gameField[i][0] == '0') {
                    return 2;
                }
            }
            // проверили все столбцы
            if (gameField[0][i] == gameField[1][i]
                    && gameField[0][i] == gameField[2][i]) {
                if (gameField[0][i] == 'x') {
                    return 1;
                }
                if (gameField[0][i] == '0') {
                    return 2;
                }
            }
        }

        // диагонали
        if (gameField[0][0] == gameField[1][1] && gameField[0][0] == gameField[2][2]) {
            if (gameField[0][0] == 'x') {
                return 1;
            }
            if (gameField[0][0] == '0') {
                return 2;
            }
        }
        if (gameField[2][0] == gameField[1][1] && gameField[2][0] == gameField[0][2]) {
            if (gameField[2][0] == 'x') {
                return 1;
            }
            if (gameField[2][0] == '0') {
                return 2;
            }
        }
        // ничья
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (gameField[i][j] == ' ')
                    return 0; // продолжаем
        return 3;


    }
}
