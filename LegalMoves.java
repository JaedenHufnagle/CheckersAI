import java.util.LinkedList;

public class LegalMoves {
    public LinkedList<Move> Moves;
    public int value;

    public LegalMoves() {
        Moves = new LinkedList();
        value = 0;
    }

    public void addMove(Move m) {
        if(m.value>this.value) {
            System.out.println("erase");
            Moves.clear();
        }
        else if(m.value<this.value)
            return;
        Moves.add(m);
    }

    public String toString() {
        return Moves.toString();
    }

}
