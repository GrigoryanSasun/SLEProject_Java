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
        int lastPivotIndex = 0;
        for (int j = 0; j < columnLength; j++) {
            Fraction currentFraction = fractionArray[lastPivotIndex][j];
            boolean pivotFound = false;
            int nextLineIndex = lastPivotIndex + 1;
            if (currentFraction.GetNumerator() == 0)
            {
                int nonZeroRowIndex = 0;
                for (int k=nextLineIndex;k<rowLength;k++)
                {
                    if (fractionArray[k][j].GetNumerator() != 0)
                    {
                        nonZeroRowIndex = k;
                        pivotFound = true;
                        break;
                    }
                }
                if (pivotFound) {
                    for (int k=j;k<columnLength;k++)
                    {
                        Fraction temp = fractionArray[lastPivotIndex][k];
                        fractionArray[lastPivotIndex][k] = fractionArray[nonZeroRowIndex][k];
                        fractionArray[nonZeroRowIndex][k] = temp;
                    }
                }
            }
            else {
                pivotFound = true;
            }
            if (pivotFound) {
                for (int k = nextLineIndex;k<rowLength;k++)
                {
                    Fraction fractionToCheck = fractionArray[k][j];
                    if (fractionToCheck.GetNumerator() != 0)
                    {
                        int newNumerator = - fractionToCheck.GetNumerator() * fractionArray[lastPivotIndex][j].GetDenominator();
                        int newDenominator = fractionToCheck.GetDenominator() * fractionArray[lastPivotIndex][j].GetNumerator();
                        Fraction fractionToMultiplyBy = new Fraction(newNumerator,newDenominator);
                        for (int t = j;t<columnLength;t++)
                        {
                            Fraction fractionToAdd = new Fraction(fractionArray[lastPivotIndex][t]);
                            fractionToAdd.Multiply(fractionToMultiplyBy);
                            fractionArray[k][t].Add(fractionToAdd);
                        }
                    }
                }
                if (lastPivotIndex == rowLength)
                {
                    break;
                }
                else {
                    lastPivotIndex++;
                }
            }
        }
        for (int i = 0; i < fractionArray.length; i++) {
            for (int j = 0; j < fractionArray[i].length; j++) {
                System.out.print(fractionArray[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
