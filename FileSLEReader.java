/**
 * Created by Grigoryan on 05.05.2016.
 */
import java.io.*;
import java.util.ArrayList;

public class FileSLEReader implements ISLEReader {

    private String _filePath;

    public SLEContainer[] ReadSLEAugmentedMatrices() {
        try
        {
            BufferedReader fileInput = new BufferedReader(new FileReader(_filePath));
            try
            {
                boolean allSLEsRead = false;
                ArrayList<SLEContainer> sleContainers = new ArrayList<SLEContainer>();
                ArrayList<Fraction[]> fractionList = new ArrayList<>();
                int lastFoundColumnCount = 0;
                while (!allSLEsRead) {
                    String fractionsRow = fileInput.readLine();
                    if (fractionsRow == null)
                    {
                        if (fractionList.size() != 0)
                        {
                            Fraction[][] result = new Fraction[fractionList.size()][];
                            result = fractionList.toArray(result);
                            sleContainers.add(new SLEContainer(result));
                        }
                        allSLEsRead = true;
                    }
                    else if (fractionsRow.equals("--"))
                    {
                        if (fractionList.size() != 0)
                        {
                            Fraction[][] result = new Fraction[fractionList.size()][];
                            result = fractionList.toArray(result);
                            sleContainers.add(new SLEContainer(result));
                        }
                        fractionList.clear();
                        lastFoundColumnCount = 0;
                    }
                    else
                    {
                        String[] fractionsStringArray = fractionsRow.split(" ");
                        if (lastFoundColumnCount > 0) {
                            if (fractionsStringArray.length != lastFoundColumnCount)
                            {
                                throw new Error("Matrix #" + (sleContainers.size() + 1) + ": Invalid augmented matrix");
                            }
                        }
                        else if (fractionsStringArray.length < 2)
                        {
                            throw new Error("Matrix #" + (sleContainers.size() + 1) + ": Number of columns should be at least 2");
                        }
                        else
                        {
                            lastFoundColumnCount = fractionsStringArray.length;
                        }
                        Fraction[] fractionRow = new Fraction[lastFoundColumnCount];
                        for (int i = 0; i < fractionsStringArray.length; i++)
                        {
                            Fraction parsedFraction = Fraction.ParseFraction(fractionsStringArray[i]);
                            fractionRow[i] = parsedFraction;
                        }
                        fractionList.add(fractionRow);
                    }
                }
                SLEContainer[] result = new SLEContainer[sleContainers.size()];
                result = sleContainers.toArray(result);
                return result;
            }
            finally
            {
                fileInput.close();
            }
        }
        catch(Throwable exc)
        {
            throw new Error(exc.getMessage());
        }
    }

    public FileSLEReader(String filePath)
    {
        _filePath = filePath;
    }

}
