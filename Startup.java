/**
 * Created by Grigoryan on 09.04.2016.
 */
public class Startup {
    public static void main(String[] args)
    {
        String[][] matrixString = {
                {"1","1","3","1","6"},
                {"2", "-1", "0", "1", "-1"},
                {"-3", "2", "1", "-2", "1"},
                {"4","1","6","1","3"}
        };
        int rowLength = matrixString.length;
        Fraction[][] fractionArray = new Fraction[rowLength][];
        int columnLength = 0;
        for (int i=0;i<rowLength;i++)
        {
            columnLength = matrixString[0].length;
            fractionArray[i] = new Fraction[columnLength];
            for(int j=0;j<columnLength;j++)
            {
                fractionArray[i][j] = Fraction.ParseFraction(matrixString[i][j]);
            }
        }
        int lastPivotIndex = 0;
        for (int j=0;j < columnLength; j++)
        {
            for (int i=0;i < rowLength; i++)
            {
                Fraction currentFraction = fractionArray[i][j];
                boolean pivotFound = false;
                if (currentFraction.GetNumerator() == 0)
                {
                    for (int k = i+1; k < rowLength;k++)
                    {
                        if (fractionArray[k][j].GetNumerator() != 0 )
                        {
                            pivotFound = true;
                            for (int t = 0;t<columnLength;t++)
                            {
                                Fraction c = fractionArray[i][t];
                                fractionArray[k][t] = fractionArray[i][t];
                                fractionArray[i][t] = c;
                            }
                            break;
                        }
                    }
                }
                else {
                    pivotFound = true;
                }
                if (pivotFound)
                {
                    for (int k=lastPivotIndex+1;i<rowLength;k++)
                    {
                        if (fractionArray[k][j].GetNumerator() !=0)
                        {
                            int numerator = -fractionArray[k][j].GetNumerator() * fractionArray[lastPivotIndex][j].GetDenominator();
                            int denominator = fractionArray[k][j].GetDenominator() * fractionArray[lastPivotIndex][j].GetNumerator();
                            Fraction fractionToMultiplyBy = new Fraction(numerator,denominator);
                            for (int t = j; t<columnLength;t++)
                            {

                            }
                        }
                    }
                }
            }
        }
        for (int i=0;i<fractionArray.length;i++)
        {
            for(int j=0;j<fractionArray[i].length;j++)
            {
                System.out.print(fractionArray[i][j] + " ");
            }
            System.out.println();
        }
    }
}
