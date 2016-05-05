import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Grigoryan on 05.05.2016.
 */
public class ConsoleSLEReader implements  ISLEReader {

    private int InputInteger(String errorMessage)
    {
        Scanner scanner = new Scanner(System.in);
        int number = 0;
        boolean failed = true;
        while (failed)
        {
            try
            {
                number = scanner.nextInt();
                failed = false;
            }
            catch(InputMismatchException exc)
            {
                System.out.print(errorMessage);
                scanner.nextLine();
            }
        }
        return number;
    }

    public Fraction[][] ReadSLEAugmentedMatrix()
    {
        int rowCount, columnCount;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input the number of rows of the augmented matrix: ");
        String wrongRowCountErrorMessage = "Please input integer for the number of rows: ";
        rowCount = InputInteger(wrongRowCountErrorMessage);
        while (rowCount <= 0)
        {
            System.out.print("The number of rows should be positive: ");
            rowCount = InputInteger(wrongRowCountErrorMessage);
        }
        System.out.print("Input the number of columns of the augmented matrix: ");
        String wrongColumnCountErrorMessage = "Please input integer for the number of columns: ";
        columnCount = InputInteger(wrongColumnCountErrorMessage);
        while (columnCount <= 1)
        {
            System.out.print("The number of columns should be at least 2: ");
            columnCount = InputInteger(wrongColumnCountErrorMessage);
        }
        Fraction[][] augmentedMatrix = new Fraction[rowCount][];
        for (int i=0;i<rowCount;i++)
        {
            augmentedMatrix[i] = new Fraction[columnCount];
            for (int j=0;j<columnCount;j++)
            {
                System.out.print("Input [" + (i+1) + "][" + (j+1) + "]: ");
                while (true)
                {
                    try {
                        augmentedMatrix[i][j] = Fraction.ParseFraction(scanner.nextLine());
                        break;
                    }
                    catch (InvalidFractionException exc)
                    {
                        System.out.print("Invalid fraction, input again: ");
                    }
                    catch (Error exc)
                    {
                        System.out.print(exc.getMessage() + ": ");
                    }
                }
            }
        }
        return augmentedMatrix;
    }
}
