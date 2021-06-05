import java.util.LinkedList;

public class NodeStates {
    State state;
    LinkedList<NodeStates> children;
    int nodeVal;
    int depth;
    int winlosedraw;
    boolean terminal;
    int maxdepth;

    public NodeStates (State s, int depth){
        this.state=s;
        this.depth=depth; 
        this.terminal=false; 
        children = new LinkedList<NodeStates>();

        if(s.b.size == 4) {
        	maxdepth = 10;
		}
        else if(s.b.size == 8) {
			maxdepth = 50;
		}
       
        this.nodeVal = findVal(s);
    }

    public NodeStates (State s, int depth, int maxdepth){
    	this.state=s;
    	this.depth=depth;
    	this.terminal=false;
    	children= new LinkedList <NodeStates>();
    	this.maxdepth=maxdepth;
	}


    //uses heuristics to get the node's value
    //Checks for value of white minus black that will show if it benefits white or not
    //AI will make choice if it's color is white or black
    public int findVal(State state) {
    	if((state.possibleresults.isEmpty() || this.depth >= maxdepth) && !this.terminal) {
    		return utility();
    	}
    	int white=0;
    	int black=0;
    	for(int i=0;i<state.b.size;i++) {
    		for(int j=0;j<state.b.size;j++) {
    			if(state.b.B[i][j]!=null) {
    				if(state.b.B[i][j].white) {
    					white++;
    				}
    				else if(!state.b.B[i][j].white) {
    					black++;
    				}
    			}
    		}
    	}
    	return white-black;
    }

	public int utility() {
		this.terminal = true;
		//need to handle draws
		//if the curr state is whites turn, then black wins
		if(this.depth == maxdepth) {
			this.nodeVal = findVal(this.state);
			winlosedraw = 0;
		}
		if(state.WhiteTurn){
			this.nodeVal = Integer.MIN_VALUE;
			winlosedraw = -1;
		}

			//else white wins
		else {
			this.nodeVal = Integer.MAX_VALUE;
			winlosedraw = 1;
		}

		return winlosedraw;
	}
    
    public void findChildren(State state) {
		NodeStates child=null;
    	
    	for(State s : state.possibleresults){
    		child= new NodeStates(s, depth+1);
    		s.findM();
    		this.children.add(child);
    	}
    	if(child!=null && child.depth<maxdepth) {
    		child.findChildren(child.state);
    	}
    }


    public boolean NodeinChildren(State s) {
    	return this.state.inResult(s);
	}

    public void printNodes() {
    	for(NodeStates n : this.children) {
    		n.printNodes();
    	}

    	this.state.b.printBoard();
    }

}
