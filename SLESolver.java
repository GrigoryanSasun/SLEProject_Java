import com.sun.javaws.exceptions.InvalidArgumentException;

/**
 * Created by Toshiba on 4/16/2016.
 */
public class SLESolver {

    private ISolutionWriter _solutionWriter;

    public void SetSolutionWriter(ISolutionWriter writer) throws InvalidArgumentException {
        if (writer == null) {
            throw new InvalidArgumentException(new String[]{"writer"});
        }
        _solutionWriter = writer;
    }

    private Fraction[][] BringToReducedRowEchelonForm(Fraction[][] rowEchelonForm) {
        int rowCount = rowEchelonForm.length;
        int columnCount = rowEchelonForm[0].length;
        Fraction[][] reducedRowEchelonForm = new Fraction[rowCount][];
        for (int i = 0; i < rowCount; i++) {
            reducedRowEchelonForm[i] = new Fraction[columnCount];
            for (int j = 0; j < columnCount; j++) {
                reducedRowEchelonForm[i][j] = new Fraction(rowEchelonForm[i][j]);
            }
        }
        boolean[] pivotArray = new boolean[columnCount - 1];
        int pivotCount = 0;
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (reducedRowEchelonForm[i][j].GetNumerator() != 0) {
                    pivotCount++;
                    pivotArray[j] = true;
                    if ((reducedRowEchelonForm[i][j].GetDenominator() != 1) || (reducedRowEchelonForm[i][j].GetNumerator() != 1))
                    {
                        Fraction multiplyBy = new Fraction(reducedRowEchelonForm[i][j]);
                        multiplyBy.SetNumeratorAndDenominator(multiplyBy.GetDenominator(), multiplyBy.GetNumerator());
                        reducedRowEchelonForm[i][j].SetNumeratorAndDenominator(1, 1);
                        _solutionWriter.WriteLine("R" + (i + 1) + " * (" + multiplyBy.toString() + ")");
                        for (int k = j + 1; k < reducedRowEchelonForm[i].length; k++) {
                            reducedRowEchelonForm[i][k].Multiply(multiplyBy);
                        }
                        PrintMatrix(reducedRowEchelonForm);
                    }
                    break;
                }
            }
        }
        for (int i = pivotArray.length - 1; i >= 0; i--) {
            if (pivotArray[i] == true) {
                int row = --pivotCount;
                for (int j = row - 1; j >= 0; j--) {
                    if (reducedRowEchelonForm[j][i].GetNumerator() != 0 ) {
                        Fraction fractionToAdd = new Fraction(reducedRowEchelonForm[j][i]);
                        fractionToAdd.SetNumerator(-1 * fractionToAdd.GetNumerator());
                        _solutionWriter.WriteLine("R" + (row + 1) + " * (" + fractionToAdd.toString() + ")" + " + R" + (j + 1));
                        for (int k = i; k <= pivotArray.length; k++) {
                            Fraction multiply = new Fraction(reducedRowEchelonForm[row][k]);
                            multiply.Multiply(fractionToAdd);
                            reducedRowEchelonForm[j][k].Add(multiply);
                        }
                        PrintMatrix(reducedRowEchelonForm);
                    }
                }
            }
        }
        return reducedRowEchelonForm;
    }

    private void PrintMatrix(Fraction[][] matrixToPrint) {
        int[] columnLengths = new int[matrixToPrint[0].length];
        for (int j = 0; j < matrixToPrint[0].length; j++) {
            int maxColumnLength = 1;
            for (int i = 0; i < matrixToPrint.length; i++) {
                int currentFractionLength = matrixToPrint[i][j].toString().length();
                if (currentFractionLength > maxColumnLength) {
                    maxColumnLength = currentFractionLength;
                }
            }
            columnLengths[j] = maxColumnLength;
        }
        for (int i = 0; i < matrixToPrint.length; i++) {
            for (int j = 0; j < matrixToPrint[i].length; j++) {
                int currentFractionLength = matrixToPrint[i][j].toString().length();
                int spaceCount = columnLengths[j] - currentFractionLength;
                for (int k = 0; k < spaceCount; k++) {
                    _solutionWriter.Write(" ");
                }
                _solutionWriter.Write(matrixToPrint[i][j].toString() + "\t");
            }
            _solutionWriter.WriteLine("");
        }
    }

    private Fraction[][] BringToRowEchelonForm(Fraction[][] augmentedMatrix) {
        int rowLength = augmentedMatrix.length;
        int columnLength = augmentedMatrix[0].length;
        Fraction[][] matrixCopy = new Fraction[rowLength][];
        for (int i = 0; i < rowLength; i++) {
            matrixCopy[i] = new Fraction[columnLength];
            for (int j = 0; j < columnLength; j++) {
                matrixCopy[i][j] = new Fraction(augmentedMatrix[i][j]);
            }
        }
        int lastPivotIndex = 0;
        for (int j = 0; j < columnLength; j++) {
            Fraction currentFraction = matrixCopy[lastPivotIndex][j];
            boolean pivotFound = false;
            int nextLineIndex = lastPivotIndex + 1;
            if (currentFraction.GetNumerator() == 0) {
                int nonZeroRowIndex = 0;
                for (int k = nextLineIndex; k < rowLength; k++) {
                    if (matrixCopy[k][j].GetNumerator() != 0) {
                        nonZeroRowIndex = k;
                        pivotFound = true;
                        break;
                    }
                }
                if (pivotFound) {
                    _solutionWriter.WriteLine("R" + (lastPivotIndex + 1) + "<->" + "R" + (nonZeroRowIndex + 1));
                    for (int k = j; k < columnLength; k++) {
                        Fraction temp = matrixCopy[lastPivotIndex][k];
                        matrixCopy[lastPivotIndex][k] = matrixCopy[nonZeroRowIndex][k];
                        matrixCopy[nonZeroRowIndex][k] = temp;
                    }
                    PrintMatrix(matrixCopy);
                }
            } else {
                pivotFound = true;
            }
            if (pivotFound) {
                for (int k = nextLineIndex; k < rowLength; k++) {
                    Fraction fractionToCheck = matrixCopy[k][j];
                    if (fractionToCheck.GetNumerator() != 0) {
                        int newNumerator = -fractionToCheck.GetNumerator() * matrixCopy[lastPivotIndex][j].GetDenominator();
                        int newDenominator = fractionToCheck.GetDenominator() * matrixCopy[lastPivotIndex][j].GetNumerator();
                        Fraction fractionToMultiplyBy = new Fraction(newNumerator, newDenominator);
                        _solutionWriter.WriteLine("R" + (lastPivotIndex + 1) + "*(" + fractionToMultiplyBy.toString() + ") + " + "R" + (k + 1));
                        for (int t = j; t < columnLength; t++) {
                            Fraction fractionToAdd = new Fraction(matrixCopy[lastPivotIndex][t]);
                            fractionToAdd.Multiply(fractionToMultiplyBy);
                            matrixCopy[k][t].Add(fractionToAdd);
                        }
                        PrintMatrix(matrixCopy);
                    }
                }
                lastPivotIndex++;
                if (lastPivotIndex == rowLength) {
                    break;
                }
            }
        }
        return matrixCopy;
    }

    public void SolveByGaussJordanElimination(Fraction[][] augmentedMatrix) {
        _solutionWriter.StartWriting();
        int rowCount = augmentedMatrix.length;
        int columnCount = augmentedMatrix[0].length;
        _solutionWriter.WriteLine("We have the following augmented matrix:");
        PrintMatrix(augmentedMatrix);
        _solutionWriter.WriteLine("To bring it to row-echelon form, let\'s perform the following elementary row operations:");
        Fraction[][] rowEchelonForm = BringToRowEchelonForm(augmentedMatrix);
        int pivotCount = 0;
        boolean[] pivotArray = new boolean[columnCount - 1];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount - 1; j++) {
                if (rowEchelonForm[i][j].GetNumerator() != 0) {
                    pivotArray[j] = true;
                    pivotCount++;
                    break;
                }
            }
        }
        boolean hasSolution = true;
        for (int i = pivotCount; i < rowCount; i++) {
            if (rowEchelonForm[i][columnCount - 1].GetNumerator() != 0) {
                hasSolution = false;
                break;
            }
        }
        if (!hasSolution) {
            _solutionWriter.WriteLine("The rank of the coefficients matrix is not equal to the rank of the augmented matrix => the system does not have a solution!");
        } else {
            _solutionWriter.WriteLine("Now bring the matrix to reduced row-echelon form by performing the following row-elementary operations");
            Fraction[][] reducedRowEchelonForm = BringToReducedRowEchelonForm(rowEchelonForm);
            _solutionWriter.WriteLine("The solution of the system is: ");
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount - 1; j++) {
                    if (reducedRowEchelonForm[i][j].GetNumerator() == 1) {
                        _solutionWriter.Write("x" + (j + 1) + " = " + reducedRowEchelonForm[i][columnCount - 1]);
                        for (int k = j + 1; k < columnCount - 1; k++) {
                            if (reducedRowEchelonForm[i][k].GetNumerator() != 0) {
                                Fraction coefficient = new Fraction(reducedRowEchelonForm[i][k]);
                                coefficient.SetNumerator(-1 * coefficient.GetNumerator());
                                _solutionWriter.Write(" + (" + coefficient + ") * x" + (k + 1));
                            }
                        }
                        _solutionWriter.WriteLine("");
                        break;
                    }
                }
            }
        }
        _solutionWriter.EndWriting();
    }

    public SLESolver(ISolutionWriter solutionWriter) throws InvalidArgumentException {
        SetSolutionWriter(solutionWriter);
    }

}
