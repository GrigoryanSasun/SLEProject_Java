/**
 * Created by Grigoryan on 04.05.2016.
 */
public class ConsoleSolutionWriter implements ISolutionWriter
{
    public void StartWriting()
    {}

    public void EndWriting()
    {}

    public void Write(String stringToWrite)
    {
        System.out.print(stringToWrite);
    }

    public void WriteLine(String stringToWrite)
    {
        System.out.println(stringToWrite);
    }

}
