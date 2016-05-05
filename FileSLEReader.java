/**
 * Created by Grigoryan on 05.05.2016.
 */
import java.io.*;
import java.util.ArrayList;

public class FileSLEReader implements ISLEReader {

    private String _filePath;

    public Fraction[][] ReadSLEAugmentedMatrix() {
        try {
            BufferedReader fileInput = new BufferedReader(new FileReader(_filePath));
            try
            {
                boolean allLinesRead = false;
                int lastFoundColumnCount = 0;
                ArrayList<Fraction[]> fractionList = new ArrayList<Fraction[]>();
                while(!allLinesRead)
                {
                    String fractionsRow = fileInput.readLine();
                    if (fractionsRow == null)
                    {
                        allLinesRead = true;
                    }
                    else {
                        String[] fractionsStringArray = fractionsRow.split(" ");
                        if (lastFoundColumnCount > 0)
                        {
                            if (fractionsStringArray.length != lastFoundColumnCount)
                            {
                                throw new Error("Invalid augmented matrix");
                            }
                        }
                        else if (fractionsStringArray.length < 2)
                        {
                            throw new Error("Invalid augmented matrix");
                        }
                        else {
                            lastFoundColumnCount = fractionsStringArray.length;
                        }
                        Fraction[] fractionRow = new Fraction[lastFoundColumnCount];
                        for (int i=0;i<fractionsStringArray.length;i++)
                        {
                            Fraction parsedFraction = Fraction.ParseFraction(fractionsStringArray[i]);
                            fractionRow[i] = parsedFraction;
                        }
                        fractionList.add(fractionRow);
                    }
                }
                if (fractionList.size() == 0)
                {
                    throw new Error("The augmented matrix should have at least 1 row");
                }
                Fraction[][] result = new Fraction[fractionList.size()][];
                return fractionList.toArray(result);
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
