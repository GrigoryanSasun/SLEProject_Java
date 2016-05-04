import com.sun.javaws.exceptions.InvalidArgumentException;

/**
 * Created by Grigoryan on 09.04.2016.
 */
public class Startup {
    public static void main(String[] args) {
        String[][] matrixString = {
                {"1", "2"}
        };
        int rowLength = matrixString.length;
        Fraction[][] fractionArray = new Fraction[rowLength][];
        int columnLength = 0;
        for (int i = 0; i < rowLength; i++) {
            columnLength = matrixString[0].length;
            fractionArray[i] = new Fraction[columnLength];
            for (int j = 0; j < columnLength; j++) {
                fractionArray[i][j] = Fraction.ParseFraction(matrixString[i][j]);
            }
        }
        SLESolver solver = null;
        try
        {
            solver = new SLESolver(new ConsoleSolutionWriter());
        }
        catch(InvalidArgumentException exc)
        {
            System.out.println("error");
        }
        solver.SolveByGaussJordanElimination(fractionArray);
    }
}
