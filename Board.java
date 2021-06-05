public class Board {
    Piece[][] B;
    int size;

    public Board(int size) {
        B = new Piece[size][size];
        this.size = size;
    }

    public String makeMove(int r, int c) {
        StringBuilder move = new StringBuilder("");
        move.append(r+1);
        move.append('A'+c);
        return move.toString();
    }

    public void setupBoard() {
        if (this.size == 4) {
            B[3][0] = new Piece(true, "D1");
            B[3][2] = new Piece(true, "D3");
            B[0][1] = new Piece(false, "A2");
            B[0][3] = new Piece(false, "A4");

        } else {

            B[5][0] = new Piece(true, "F1");
            B[7][0] = new Piece(true, "H1");
            B[6][1] = new Piece(true, "G2");
            B[5][2] = new Piece(true, "F3");
            B[7][2] = new Piece(true, "H3");
            B[6][3] = new Piece(true, "G4");
            B[5][4] = new Piece(true, "F5");
            B[7][4] = new Piece(true, "H5");
            B[6][5] = new Piece(true, "G6");
            B[5][6] = new Piece(true, "F7");
            B[7][6] = new Piece(true, "H7");
            B[6][7] = new Piece(true, "G8");
            B[0][1] = new Piece(false, "A2");
            B[0][3] = new Piece(false, "A4");
            B[0][5] = new Piece(false, "A6");
            B[0][7] = new Piece(false, "A8");
            B[1][0] = new Piece(false, "B1");
            B[1][2] = new Piece(false, "B3");
            B[1][4] = new Piece(false, "B5");
            B[1][6] = new Piece(false, "B7");
            B[2][1] = new Piece(false, "C2");
            B[2][3] = new Piece(false, "C4");
            B[2][5] = new Piece(false, "C6");
            B[2][7] = new Piece(false, "C8");
        }
    }


    public void printBoard() {
        int size = this.size;
        System.out.println("The Board: ");
        if (size == 4) {
            System.out.println("  1 2 3 4 ");
            System.out.println(" +-+-+-+-+");
        } else if (size == 8) {
            System.out.println("  1 2 3 4 5 6 7 8");
            System.out.println(" +-+-+-+-+-+-+-+-+");
        }

        for (int i = 0; i < size; i++) {
            char row = (char) ('A' + i);
            System.out.print(row + "|");
            for (int j = 0; j < size; j++) {


                char sym = ' ';
                if (this.B[i][j] != null) {
                    sym = this.B[i][j].getSymbol();
                }


                System.out.print(sym + "|");

            }
            System.out.println();
        }
        if (size == 4) {
            System.out.println(" +-+-+-+-+");
        } else if (size == 8) {
            System.out.println(" +-+-+-+-+-+-+-+-+");
        }
    }

    public String toString(){
        return this.B.toString();
    }

}
