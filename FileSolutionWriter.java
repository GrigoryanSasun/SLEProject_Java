/**
 * Created by Grigoryan on 05.05.2016.
 */
import java.io.*;

public class FileSolutionWriter implements ISolutionWriter {

    private String _filePath;
    private PrintWriter _fileWriter;

    public void StartWriting()
    {
        try {
            _fileWriter = new PrintWriter(new FileWriter(_filePath,true));
            _fileWriter.println("");
        }
        catch(IOException exc)
        {
            throw new Error(exc.getMessage());
        }
    }

    public void Write(String stringToWrite)
    {
        if (_fileWriter == null)
        {
            throw new Error("StartWriting should be called before calling Write");
        }
        _fileWriter.print(stringToWrite);
    }

    public void WriteLine(String stringToWrite)
    {
        if (_fileWriter == null)
        {
            throw new Error("StartWriting should be called before calling WriteLine");
        }
        _fileWriter.println(stringToWrite);
    }

    public void EndWriting()
    {
        _fileWriter.println("--------------");
        _fileWriter.close();
    }


    public FileSolutionWriter(String filePath)
    {
        _filePath = filePath;
    }

}
