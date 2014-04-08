import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: nicksirock
 * Date: 11/27/13
 * Time: 7:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class NimGraphGame {
    int [][] adjacencyMatrix;
    DirectedGraph<NimPosition> graph;
    ArrayList<VertexInterface<NimPosition>> vertexMapping;
    NimPosition start;
    NimGraphGame()
    {
        this.graph = new DirectedGraph<NimPosition>();
        this.vertexMapping = new ArrayList<VertexInterface<NimPosition>>();
        setPilesAndGraph();
        setAdjacencyMatrix();
        setWinLosses();
        play();
    }
    void setPilesAndGraph()
    {
        Scanner scan = new Scanner(System.in);

        System.out.println("Enter number for first pile: ");
        int a = scan.nextInt();
        System.out.println("Enter number for second pile: ");
        int b = scan.nextInt();
        System.out.println("Enter number for third pile: ");
        int c = scan.nextInt();
        if (a >= 0 && b >= 0 && c >= 0)
        {
            setGraph(a, b, c);
            this.start = new NimPosition(a,b,c);
        }
        else
        {
            System.out.println("Each pile size must be positive");
            setPilesAndGraph();
        }
    }
    void setGraph(int first ,int second, int third)
    {
        for (int i = 0; i <= first; i++)
        {
            for (int j = 0; j <= second; j++)
            {
                for (int k = 0; k <= third; k++)
                {
                    NimPosition position = new NimPosition(i,j,k);
                    this.graph.addVertex(position);
                    for (VertexInterface<NimPosition> vertex : this.vertexMapping)
                    {
                        NimPosition verPos = vertex.getLabel();
                        if ((i == verPos.getFirstStack() && j == verPos.getSecondStack())||(i == verPos.getFirstStack() && k == verPos.getThirdStack())||(j == verPos.getSecondStack() && k == verPos.getThirdStack()))
                        {
                            this.graph.addEdge(position,verPos);
                        }
                    }
                    this.vertexMapping.add(new Vertex<NimPosition>(position));
                }
            }
        }
    }
    void setAdjacencyMatrix ()
    {
        this.adjacencyMatrix = this.graph.getAdjacencyMatrix();
    }
    void displayMatrix()
    {
        for (int i = 0; i < this.adjacencyMatrix.length; i++)
        {
            for (int j = 0; j < this.adjacencyMatrix.length; j++)
            {
                System.out.print(this.adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    void setWinLosses()
    {
        this.vertexMapping = this.graph.getMapping();
        boolean done = false;
        while (!done)
        {
            done = true;

            for (int i = 0; i < this.vertexMapping.size(); i++)
            {
                boolean check = true;

                VertexInterface<NimPosition> vertex =  this.vertexMapping.get(i);

                for (int j = 0; j < this.vertexMapping.size() && check; j++)
                {
                    if (this.adjacencyMatrix[i][j] == 1)
                    {
                        VertexInterface<NimPosition> other = vertexMapping.get(j);
                        if (other.getLabel().isLoss())
                        {
                            vertex.getLabel().setToWin();
                        }

                    }
                }
                if (!vertex.getLabel().isLoss() && !vertex.getLabel().isWin())
                {
                    done = false;
                }

            }


            for (int i = 0; i < this.vertexMapping.size(); i++)
            {
                boolean check = true;

                VertexInterface<NimPosition> vertex =  this.vertexMapping.get(i);
                for (int j = 0; j < this.vertexMapping.size() && check; j++)
                {
                    if (this.adjacencyMatrix[i][j] == 1)
                    {
                        VertexInterface<NimPosition> other = this.vertexMapping.get(j);
                        if (other.getLabel().isLoss())
                        {
                            check = false;
                        }

                    }
                }
                if (check)
                {
                    vertex.getLabel().setToLoss();
                }
                if (!vertex.getLabel().isLoss() && !vertex.getLabel().isWin())
                {
                    done = false;
                }
            }

        }
    }
    VertexInterface<NimPosition> getMove(int a)
    {
        VertexInterface<NimPosition> current = this.vertexMapping.get(a);
        NimPosition currentNim = current.getLabel();
        if (currentNim.isWin())
        {
            Iterator<VertexInterface<NimPosition>> sides = current.getNeighborIterator();
            while (sides.hasNext())
            {
                VertexInterface<NimPosition> side = sides.next();
                NimPosition sideNim = side.getLabel();
                if (sideNim.isLoss())
                {
                    return side;
                }
            }
            return null;
        }
        else
        {
            return current.getUnvisitedNeighbor();
        }
    }
    boolean getFirst()
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Who would you like to go first? Enter me, computer, or random.");

        String input = scanner.next();
        input = input.toLowerCase();
        if (input.equals("me"))
        {
            return false;
        }
        if (input.equals("computer"))
        {
            return true;
        }
        if (input.equals("random"))
        {
            Random rand = new Random();
            return rand.nextBoolean();
        }
        return true;
    }
    void displayTurn(boolean turn)
    {
        if (turn)
        {
            System.out.println("It is now the computer's turn.");
        }
        else
        {
            System.out.println("It is now the player's turn.");
        }
    }
    VertexInterface<NimPosition> playerTurn(VertexInterface<NimPosition> currentVertex)
    {
        NimPosition current = currentVertex.getLabel();
        Scanner scan = new Scanner(System.in);
        int pile = -1;
        int max = -1;
        while (pile < 1 || pile > 3 || max <= 0)
        {
            System.out.println("Which pile would you like to remove from? Enter 1 2 or 3.");

            pile = scan.nextInt();
            if (pile == 1)
            {
                max = current.getFirstStack();
            }
            else if (pile == 2)
            {
                max = current.getSecondStack();
            }
            else if (pile == 3)
            {
                max = current.getThirdStack();
            }
            else
            {
                System.out.println("Please enter a valid pile");
            }
            if (max == 0)
            {
                System.out.println("Pile "+pile+" has zero chips, please choose a pile that contains chips.");
            }

        }
        int amount = -1;
        while (amount < 1 || amount > max)
        {
            System.out.println("There are " + max + " chips in pile " + pile);
            System.out.println("How many chips would you like to remove?");
            amount = scan.nextInt();
        }
        int a,b,c;
        switch (pile)
        {
            case 1:
                a = max - amount;
                b = current.getSecondStack();
                c = current.getThirdStack();
                break;
            case 2:
                a = current.getFirstStack();
                b = max - amount;
                c = current.getThirdStack();
                break;
            case 3:
                a = current.getFirstStack();
                b = current.getSecondStack();
                c = max - amount;
                break;
            default:
                a = 0;
                b = 0;
                c = 0;
                System.out.println("THIS SHOULDN'T BE ON THE SCREEN");
                break;
        }
        NimPosition nimPosition = new NimPosition(a,b,c);
        for (int i = 0; i < this.vertexMapping.size(); i++)
        {
            VertexInterface<NimPosition> now = this.vertexMapping.get(i);
            NimPosition nowNim = now.getLabel();
            if (nowNim.equals(nimPosition))
            {
                return now;
            }
        }
        System.out.println("Not a valid move");
        return playerTurn(currentVertex);
    }
    void play()
    {

        boolean turn = getFirst();
        VertexInterface<NimPosition> currentVertex = vertexMapping.get((vertexMapping.size()-1));
        NimPosition current = currentVertex.getLabel();
        System.out.println("Starting piles: "+current.toStringJustNumbers());
        boolean done = false;

        while  (!done)
        {
            displayTurn(turn);
            if (turn)
            {
                currentVertex = getMove(this.vertexMapping.indexOf(currentVertex));
                current = currentVertex.getLabel();
            }
            else
            {
                currentVertex = playerTurn(currentVertex);
                current = currentVertex.getLabel();
            }
            System.out.println("The piles are now: " + current.toStringJustNumbers());
            turn = !turn;
            done = checkWin(currentVertex);
        }
        if (turn)
        {
            System.out.println("The player wins!");
        }
        else
        {
            System.out.print("The computer wins!");
        }
    }
    boolean checkWin(VertexInterface<NimPosition> currentVertex)
    {
        NimPosition current = currentVertex.getLabel();
        if (current.getFirstStack() != 0)
        {
            return false;
        }
        if (current.getSecondStack() != 0)
        {
            return false;
        }
        return current.getThirdStack() == 0;
    }

    public static void main(String[] args) {
        new NimGraphGame();
    }
}
