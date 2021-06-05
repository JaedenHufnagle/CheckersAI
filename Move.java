import java.util.LinkedList;

public class Move {
    int startx, starty, endx, endy;
    int value;
//    State result;
//    LinkedList<Piece> captured;

    public Move(State current, int sx, int sy, int ex, int ey) {
        startx = sx;
        starty = sy;
        endx = ex;
        endy = ey;
//        captured = new LinkedList<Piece>();
        value = findValue(current.b);
        System.out.println("New move created");

//        this.result = getNext(current);
    }

    public boolean sameMove(Move m1) {
        return (m1.startx==this.startx)&&(m1.starty==this.starty)&&(m1.endx==this.endx)&&(m1.endy==this.endy);
    }

    public int translateX(String pos)  {
        return pos.charAt(0) - 'A';
    }

    public int translateY(String pos)  {
        return Character.getNumericValue(pos.charAt(1)) - 1;
    }

//    //creates the child state
//    public State getNext(State current){
//        State n = current;
//        Board next = n.makeMove(current.b, this);
//        return new State(next, !n.WhiteTurn);
//    }

    public int findValue(Board B) {


        return 0;
    }

    public String toString() {
        return "(" + startx + "," + starty + ") - (" + endx + "," + endy + ")";
    }

}
