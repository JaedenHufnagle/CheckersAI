import java.util.Random;
import java.util.Scanner;

public class Game {
    Board board;
    int AItype;
    Scanner scan;
    boolean AIWhite;
    int hdepth;

    public Game() {
        setUpGame();
    }

    public void setUpGame() {
        System.out.println("Welcome to my Checkers Game");
        System.out.println("Choose the board you'd like to play:");
        System.out.println("1. Small 4x4 Board");
        System.out.println("2. Standard 8x8 Board");

        scan = new Scanner(System.in);
        int size = 0;
        if(scan.next().charAt(0)=='1') {
            size = 4;
        }
        else {
            size = 8;
        }

        System.out.println("Please choose which AI algoritmn: [0,1,2,3]");
        System.out.println("0. Random");
        System.out.println("1. MinMax");
        System.out.println("2. MinMax + alpha-beta");
        System.out.println("3. H-MinMax");
        int ai = scan.nextInt();
        if(ai>=0 && ai <= 3) {
            this.AItype=ai;
            if(ai==3){
                System.out.println("Please enter depth cutoff");
                this.hdepth=scan.nextInt();
            }
        }
        else {
            System.out.println("Invalid input!");
        }

        System.out.println("Please choose if you'd like to be black or white: [b,w]");
        String playerchoice = scan.next();
        if(playerchoice.equals("b")) {
            this.AIWhite=true;
        }
        else if(playerchoice.equals("w")){
            this.AIWhite=false;
        }
        else {
            System.out.println("Invalid input!");
        }

        board = new Board(size);
        board.setupBoard();

//        for(int i = 0; i < board.size; i++) {
////            char row = (char) ('A' + i);
////            System.out.print(row + "|");
//            for (int j = 0; j < board.size; j++)
//                System.out.print(i + "-" + j  + "|");
////                System.out.print(row + "-" + (j + 1) + "|");
//            System.out.println();
//        }

    /*
    here is where we ask about AI type lol
     */

    //    scan.close();


    }




    public void gameLoop() {
        Scanner s = new Scanner(System.in);
        Board b = board;

        State start = new State(b, false, 0);
       
        start.findM();
        NodeStates root=null;
        if(this.AItype!=3) {
            root = new NodeStates(start, 0);
            root.findChildren(root.state);
        }
        else{
            root = new NodeStates(start,0,this.hdepth);
            root.findChildren(root.state);
        }

        int moves=0;

        while(true) {

            root.state.possibleresults.clear();
            root.state.findM();


            if(root.state.possibleresults.isEmpty()) {
                root.state.b.printBoard();
                if (root.state.WhiteTurn) {
                    System.out.println("The winner is: Black");
                } else {
                    System.out.println("The winner is: White");
                }
                break;
            }

            if(b.size==8 && moves==51) {
                root.state.b.printBoard();
                System.out.println("Turn Number 50: Draw");
                break;
            }
            else if(b.size==4 && moves==11) {
                root.state.b.printBoard();
                System.out.println("Turn number 10: Draw");
                break;
            }

            boolean legalmo = false;

            while(!legalmo) {
                root.state.b.printBoard();
                System.out.println("Turn No. " + moves);


                //this activates if it is AI's turn
                if(root.state.WhiteTurn == this.AIWhite) {
                    System.out.println("It is the AI's turn");
                    root = AIchoice(root);
                    legalmo = true;

                }
                //players turn
                else {
                    if(root.state.WhiteTurn)
                        System.out.println("White, input your turn");
                    else
                        System.out.println("Black, input your turn");


                    String move = s.next();
                    String sp = move.substring(0, 2).toUpperCase();
                    String ep = move.substring(3, 5).toUpperCase();
                    System.out.println(sp +" - "+ ep);

                    System.out.println("input: " + sp + " to "+ep);

                    int sx = root.state.translateX(sp);
                    int sy = root.state.translateY(sp);
                    int ex = root.state.translateX(ep);
                    int ey = root.state.translateY(ep);

                    System.out.println("The move: "+"(" + sx + ","+sy+") - ("+ ex + ","+ey+")");
                    boolean cap = false;
                    if(sx != ex + 1 && sx != ex - 1) {
                        cap = true;
                    }

                    State newS = root.state.makeMove(root.state, sx, sy, ex, ey, cap);

                    if(newS==null){
                        System.out.println("Invalid move: please try again:");
                        continue;
                    }

                    if(root.NodeinChildren(newS)) {
                        root = new NodeStates(newS, 0);
                        legalmo = true;
                    }
                    else {
                        System.out.println("Invalid move: please try again:");
                        continue;
                    }
                }

            }
            moves++;
        }
        s.close();
    }

    //this function allows the AI to chose which algorithm to use
    public NodeStates AIchoice(NodeStates ns) {
        NodeStates result = null;

        if(this.AItype==0) {
            ns.findChildren(ns.state);
            Random dice = new Random();
            int index = dice.nextInt(ns.children.size());
            return ns.children.get(index);
        }
        else if(this.AItype==1) {
        	//Minimax method here
        	ns.findChildren(ns.state);
        	return Minimax(ns);

        }
        else if(this.AItype==2) {
        	//Minimax with a-b pruning
            ns.findChildren(ns.state);
            return AlphaBeta(ns);
        }
        else if(this.AItype==3) {
        	//H-Minimax
            ns.findChildren(ns.state);
            return H_Minimax(ns);

        }

        return result;
    }

    public NodeStates Minimax(NodeStates root){
        NodeStates best=null;

        int v;
        int besti;

        if(AIWhite) {
            v=Integer.MIN_VALUE;
            besti=Integer.MIN_VALUE;
        }
        else {
            v=Integer.MAX_VALUE;
            besti=Integer.MAX_VALUE;
        }

        for(NodeStates c : root.children){
            //if the AI is white, then it wants the max utility value
            if(AIWhite) {
                v=Math.max(v, Min_Node(c));
                if(v>besti){
                    besti=v;
                    best=c;
                }
            }
            //if the AI is black, then it wants the min utility value
            else {
                v=Math.min(v, Max_Node(c));
                if(v<besti){
                    besti=v;
                    best=c;
                }
            }

        }
        return best;
    }

    public int Max_Node(NodeStates ns){
        if(ns.terminal)return ns.winlosedraw;
        int v=Integer.MIN_VALUE;
        for(NodeStates c: ns.children){
            v= Math.max(v, Min_Node (c));
        }
        return v;
    }

    public int Min_Node(NodeStates ns){
        if(ns.terminal)return ns.winlosedraw;
        int v=Integer.MAX_VALUE;
        for(NodeStates c: ns.children){
            v=Math.min(v, Max_Node(c));
        }
        return v;
    }

    public NodeStates AlphaBeta(NodeStates ns){
        NodeStates best=null;
        int v;
        int besti;
        if(AIWhite){
            v=Integer.MIN_VALUE;
            besti=Integer.MIN_VALUE;
        }
        else{
            v=Integer.MAX_VALUE;
            besti=Integer.MAX_VALUE;
        }

        int alpha=Integer.MIN_VALUE;
        int beta=Integer.MAX_VALUE;

        for(NodeStates c: ns.children){

            if(AIWhite){
                v=Math.max(v,Alpha_Min(c,alpha,beta));
                if(v>besti){
                    besti=v;
                    best=c;
                }
            }
            else{
                v=Math.min(v,Alpha_Max(c,alpha,beta));
                if(v<besti){
                    besti=v;
                    best=c;
                }
            }
        }
        return best;
    }

    public int Alpha_Max(NodeStates ns,int alpha,int beta){
        if(ns.terminal)return ns.winlosedraw;
        int v=Integer.MIN_VALUE;
        for(NodeStates c: ns.children){
            v=Math.max(v, Alpha_Min(c,alpha,beta));
            if(v>=beta){
                return v;
            }
            alpha=Math.max(alpha,v);
        }
        return v;
    }

    public int Alpha_Min(NodeStates ns, int alpha, int beta){
        if(ns.terminal)return ns.winlosedraw;
        int v=Integer.MAX_VALUE;
        for(NodeStates c: ns.children){
            v=Math.min(v, Alpha_Max(c,alpha,beta));
            if(v<=alpha){
                return v;
            }
            beta=Math.min(beta,v);
        }
        return v;
    }

    public NodeStates H_Minimax(NodeStates ns){
        NodeStates best=null;
        int v;
        int besti;

        if(AIWhite){
            v=Integer.MIN_VALUE;
            besti=Integer.MIN_VALUE;
        }
        else{
            v=Integer.MAX_VALUE;
            besti=Integer.MAX_VALUE;
        }

        for(NodeStates c: ns.children){
            if(AIWhite){
                v=Math.max(v,H_Min(c));
                if(v>besti){
                    besti=v;
                    best=c;
                }
            }
            else{
                v=Math.min(v, H_Max(c));
                if(v<besti){
                    besti=v;
                    best=c;
                }
            }
        }
        return best;
    }

    public int H_Min(NodeStates ns){
        if(ns.terminal)return ns.nodeVal;
        int v=Integer.MAX_VALUE;
        for(NodeStates c: ns.children){
            v=Math.min(v,H_Max(c));
        }
        return v;
    }

    public int H_Max(NodeStates ns){
        if(ns.terminal)return ns.nodeVal;
        int v=Integer.MIN_VALUE;
        for(NodeStates c: ns.children){
            v=Math.max(v,H_Min(c));
        }
        return v;
    }

    
}
