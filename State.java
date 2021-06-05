import java.util.LinkedList;

public class State {
    public Board b;
    public boolean WhiteTurn;
    int stateVal;
    //    public LegalMoves LMoves;
    public LinkedList<State> possibleresults;
    public LinkedList<State> captresult;

    //public State bestResult;


    public State(Board board, boolean Turn, int val) {
        this.b = board;
        WhiteTurn = Turn;
        this.stateVal=val;
        possibleresults = new LinkedList<State>();
        captresult = new LinkedList<State>();

    }

    public int translateX(String pos) {
        return pos.charAt(0) - 'A';
    }

    public int translateY(String pos) {
        return Character.getNumericValue(pos.charAt(1)) - 1;
    }

    public boolean openspot(Board board, int x, int y) {
        Piece p = board.B[x][y];
        if(p==null)
            return true;
        return false;
    }

    public boolean ifopponent(Board board, Piece capturer, int x, int y) {
//        System.out.println("The x:" + x + " y:" + y);
        Piece checkopp = board.B[x][y];
        if (checkopp == null){
            return false;
        }
        return capturer.white ^ checkopp.white;
    }


    public int checkcapture(Board board, Piece p) {
        int capval = 0;
        int x = translateX(p.position);
        int y = translateY(p.position);

        //check who's turn it is
        if (WhiteTurn && p.white) {
            //if king
            if (p.symbol == 'W') {
                //down left
                if (((x < (board.size - 2)) && (y > 1)) && (ifopponent(board, p, x + 1, y - 1)) && (board.B[x+2][y-2]==null)) {
                    //if the spot is not free, but is filled with opp piece and open spot after that
                    this.captresult.add(makeMove(this, x, y, x + 2, y - 2, true));
                    capval = 1;
                }
                //down right
                if (((x < (board.size - 2)) && (y < (board.size - 2))) && (ifopponent(board, p, x + 1, y + 1)) && (board.B[x+2][y+2]==null)) {
                    //if the spot is not free, but is filled with opp piece and open spot after that
                    this.captresult.add(makeMove(this, x, y, x + 2, y + 2, true));
                    capval = 1;
                }
            }
            //up left
            if (((x > 1) && (y > 1)) && (ifopponent(board, p, x - 1, y - 1)) && (board.B[x-2][y-2]==null)) {
                //if the spot is not free, but is filled with opp piece and open spot after that
                this.captresult.add(makeMove(this, x, y, x - 2, y - 2, true));
                capval = 1;
            }
            //up right
            if (((x > 1) && (y < (board.size - 2))) && (ifopponent(board, p, x - 1, y + 1)) && (board.B[x-2][y+2]==null)) {
                //if the spot is not free, but is filled with opp piece and open spot after that
                this.captresult.add(makeMove(this, x, y, x - 2, y + 2, true));
                capval = 1;
            }
        }
        //black's turn to capture
        else if (!WhiteTurn && !p.white) {
            if (p.symbol == 'B') {
                //up left
                if (((x > 1) && (y > 1)) && (ifopponent(board, p, x - 1, y - 1)) && (board.B[x-2][y-2]==null)) {
                    //if the spot is not free, but is filled with opp piece and open spot after that
                    this.captresult.add(makeMove(this, x, y, x - 2, y - 2, true));
                    capval = 1;
                }
                //up right
                if (((x > 1) && (y < board.size - 2)) && (ifopponent(board, p, x - 1, y + 1))) {
                    if((board.B[x-2][y+2]==null)) {
                        //if the spot is not free, but is filled with opp piece and open spot after that
                        this.captresult.add(makeMove(this, x, y, x - 2, y + 2, true));
                        capval = 1;
                    }
                }
            }
            //down left
            if (((x  < (board.size - 2)) && (y > 1)) && (ifopponent(board, p, x + 1, y - 1))) {
                if((board.B[x+2][y-2]==null)) {
                    //if the spot is not free, but is filled with opp piece and open spot after that
                    this.captresult.add(makeMove(this, x, y, x + 2, y - 2, true));
                    capval = 1;
                }
            }
            //down right
            if (((x < (board.size - 2)) && (y < (board.size - 2))) &&(ifopponent(board, p, x + 1, y + 1))) {
                if(board.B[x+2][y+2]==null) {
                    //if the spot is not free, but is filled with opp piece and open spot after that
                    this.captresult.add(makeMove(this, x, y, x + 2, y + 2, true));
                    capval = 1;
                }
            }
        }
        return capval;
    }

    public void findM() {
        Board board = this.b;
        Piece[][] B = board.B;
        for (int i = 0; i < board.size; i++) {
            for (int j = 0; j < board.size; j++) {
                Piece current = B[i][j];
                if (current != null) {
                    int x = translateX(current.position);
                    int y = translateY(current.position);

                    int cap = checkcapture(board, current);
                    if (cap != 0)
                        continue;

                    //check who's turn it is
                    if (WhiteTurn && current.white) {
                        if (current.symbol == 'W') {
                            //if the piece is a King, move in either of the 4 diagonals
                            if ((x != board.size - 1 && y != board.size - 1) && board.B[x+1][y+1]==null ) {
                                this.possibleresults.add(makeMove(this, x, y, x + 1, y + 1, false));
                            }
                            if ((x != board.size - 1 && y != 0) &&board.B[x+1][y-1]==null ) {
                                this.possibleresults.add(makeMove(this, x, y, x + 1, y - 1, false));
                            }
                        }
                        if ((x != 0 && y != 0) && board.B[x-1][y-1]==null) {
                            this.possibleresults.add(makeMove(this, x, y, x - 1, y - 1, false));
                        }
                        if ((x != 0 && y != board.size - 1) && board.B[x - 1][y + 1]==null) {

                            this.possibleresults.add(makeMove(this, x, y, x - 1, y + 1, false));
                        }

                    }
                    //if blacks turn
                    else if (!WhiteTurn && !current.white) {
                        if (current.symbol == 'B') {
                            //if the piece is a King, move in either of the 4 diagonals
                            if ((x != 0 && y != 0) && board.B[x-1][y-1]==null) { //somethings wrong with this cond
                                this.possibleresults.add(makeMove(this, x, y, x - 1, y - 1, false));
                            }

                            if ((x != 0 && y != board.size - 1) && board.B[x-1][y+1]==null) {
                                this.possibleresults.add(makeMove(this, x, y, x - 1, y + 1, false));
                            }

                        }
                        //down right
                        if (x != (board.size - 1) && y != (board.size - 1) && board.B[x+1][y+1]==null) {
                            this.possibleresults.add(makeMove(this, x, y, x + 1, y + 1, false));
                        }
                        //down left
                        if ((x != board.size - 1 && y != 0) && board.B[x+1][y-1]==null) {
                            this.possibleresults.add(makeMove(this, x, y, x + 1, y - 1, false));
                        }
                    }

                }
            }
        }



        //goes thru the list of possible results and removes the children with less val
        if(!this.captresult.isEmpty()) {
            this.possibleresults=this.captresult;
        }

    }

    //returns the result State of any action/move
    public State makeMove(State s, int startx, int starty, int endx, int endy, boolean capture) {

        Board curr = s.b;

        Board b = this.copyBoard(curr);
        Piece p = b.B[startx][starty];

        b.B[endx][endy] = p;
        b.B[startx][starty] = null;
        if(p==null){
            return null;
        }

        StringBuilder newpos = new StringBuilder("");
        newpos.append((char)('A'+endx));
        newpos.append(endy+1);

        p.position=newpos.toString();

        int sval = 0;

        if (capture) {
            b.B[(startx + endx) / 2][(starty + endy) / 2] = null;
            sval=1;
        }

        //checks if the piece should be king'd
        if(p.white && endx == 0) {
            p.symbol = 'W';
            p.king=true;
        }
        else if(!p.white && endx == curr.size-1) {
            p.symbol = 'B';
            p.king=true;
        }

        return new State(b, !s.WhiteTurn, sval);
    }


    public Board copyBoard(Board b) {
        Board newB = new Board(b.size);

        for (int i = 0; i < b.size; i++) {
            for (int j = 0; j < b.size; j++) {
                Piece p = b.B[i][j];

                if (b.B[i][j] != null) {
                    Piece newP = p.copyPiece(p);
                    newB.B[i][j] = newP;
                }
            }
        }
        return newB;
    }

    public boolean inResult(State user) {
        if (user.WhiteTurn == this.WhiteTurn) {
            //supposed to be opposite
            return false;
        }

        for (State possres : this.possibleresults) {
            boolean inside = true;
            for (int i = 0; i < this.b.size; i++) {
                for (int j = 0; j < this.b.size; j++) {
                    Piece p = possres.b.B[i][j];

                    if (p == null && user.b.B[i][j] == null) {
                        continue;
                    } else if (p == null || user.b.B[i][j] == null) {
                        inside = false;
                    } else {
                        if (possres.b.B[i][j].symbol != user.b.B[i][j].symbol) {
                            inside = false;
                        }

                    }

                }
            }
            if (inside) {
                return true;
            }
        }
        return false;
    }


    public void printResults() {

        for (State possres : this.possibleresults) {
            System.out.println("A possible board is: ");
            Board bo = possres.b;
            bo.printBoard();
        }
    }
}
