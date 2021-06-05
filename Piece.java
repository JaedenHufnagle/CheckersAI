public class Piece {
    boolean white;
    boolean king;
    char symbol;
    String position;

    public Piece(boolean white, String position) {
        this.white = white;
        this.king = false;
        if(white)
            this.symbol= 'w';
        else
            this.symbol='b';
        this.position = position;
    }

    public String getPosition() {
        return this.position;
    }

    public char getSymbol() {
        return this.symbol;
    }

    public boolean getTeam() {return this.white; }

    public Piece copyPiece(Piece p) {
        Piece newP = new Piece(p.white, p.position);
        if(p.king) {
            newP.king=true;
            if(p.white)
                newP.symbol= 'W';
            else
                newP.symbol='B';
        }
        return newP;
    }


//    public void findnextMoves() {
//        this.validMoves.clear();
//        char column = this.position.charAt(0);
//        char row = this.position.charAt(1);
//
//        int x = column - 'A';
//        int y = Character.getNumericValue(row) - 1;
//
//        if(this.symbol=='W'||this.symbol=='B') {
//            if(x>0) {
//                if(positionPiece(x-1, y-1)) {
//
//                }
//
//            }
//
//        }
//        else if(this.symbol == 'w') {
//
//        }
//        else if(this.symbol == 'b'){
//
//        }
//        else {
//            System.out.println("Something is wrong with reading symbol");
//        }
//
//
//
//        if(this.white){
//
//        }
//
//        System.out.println("The piece " + this.symbol + " is at " + column + " " + row + " with (" + x + "," + y + ")");
//
//
//
//
//
//
//
//    }

}
