/**
 * Created by Grigoryan on 04.05.2016.
 */
public class ConsoleSolutionWriter implements ISolutionWriter
{
    public void StartWriting()
    {
        System.out.println("");
    }

    public void EndWriting()
    {
        System.out.println("--------------");
    }

    public void Write(String stringToWrite)
    {
        System.out.print(stringToWrite);
    }

    public void WriteLine(String stringToWrite)
    {
        System.out.println(stringToWrite);
    }

}
