/**
 * Created by Toshiba on 4/16/2016.
 */
public class SLESolver {
    public Fraction[][] BringToReducedRowEchelonForm(Fraction[][] augmentedMatrix) {
        Fraction[][] rowEchelonForm = BringToRowEchelonForm(augmentedMatrix);
        boolean[] pivotArray = new boolean[rowEchelonForm[0].length - 1];
        int pivotCount = 0;
        for (int i = 0; i < rowEchelonForm.length; i++) {
            for (int j = 0; j < rowEchelonForm[i].length; j++) {
                if (rowEchelonForm[i][j].GetNumerator() != 0) {
                    pivotCount++;
                    pivotArray[j] = true;
                    Fraction multiplyBy = new Fraction(rowEchelonForm[i][j]);
                    multiplyBy.SetNumeratorAndDenominator(multiplyBy.GetDenominator(), multiplyBy.GetNumerator());
                    rowEchelonForm[i][j].SetNumeratorAndDenominator(1, 1);
                    for (int k = j + 1; k < rowEchelonForm[i].length; k++) {
                        rowEchelonForm[i][k].Multiply(multiplyBy);
                    }
                    break;
                }
            }
        }
        for (int i = pivotArray.length - 1; i >= 0; i--) {
            if (pivotArray[i] == true) {
                int row = --pivotCount;
                for (int j = row - 1; j >= 0; j--) {
                    Fraction fractionToAdd = new Fraction(rowEchelonForm[j][i]);
                    fractionToAdd.SetNumerator(-1 * fractionToAdd.GetNumerator());
                    for (int k = i; k <= pivotArray.length; k++) {
                        Fraction multiply = new Fraction(rowEchelonForm[row][k]);
                        multiply.Multiply(fractionToAdd);
                        rowEchelonForm[j][k].Add(multiply);
                    }
                }
            }
        }
        return rowEchelonForm;
    }

    public Fraction[][] BringToRowEchelonForm(Fraction[][] augmentedMatrix) {
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
                    for (int k = j; k < columnLength; k++) {
                        Fraction temp = matrixCopy[lastPivotIndex][k];
                        matrixCopy[lastPivotIndex][k] = matrixCopy[nonZeroRowIndex][k];
                        matrixCopy[nonZeroRowIndex][k] = temp;
                    }
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
                        for (int t = j; t < columnLength; t++) {
                            Fraction fractionToAdd = new Fraction(matrixCopy[lastPivotIndex][t]);
                            fractionToAdd.Multiply(fractionToMultiplyBy);
                            matrixCopy[k][t].Add(fractionToAdd);
                        }
                    }
                }
                if (lastPivotIndex == rowLength) {
                    break;
                } else {
                    lastPivotIndex++;
                }
            }
        }
        return matrixCopy;
    }
}
