/**
 * Created by Grigoryan on 09.04.2016.
 */
public class Startup {
    public static void main(String[] args) {
        String[][] matrixString = {
                {"1", "1", "3", "1", "6"},
                {"2", "-1", "0", "1", "-1"},
                {"-3", "2", "1", "-2", "1"},
                {"4", "1", "6", "1", "3"}
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
        SLESolver solver = new SLESolver();
        Fraction[][] rowEchelonForm = solver.BringToReducedRowEchelonForm(fractionArray);
        for (int i = 0; i < rowEchelonForm.length; i++) {
            for (int j = 0; j < rowEchelonForm[i].length; j++) {
                System.out.print(rowEchelonForm[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
