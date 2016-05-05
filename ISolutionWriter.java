/**
 * Created by Grigoryan on 04.05.2016.
 */
public interface ISolutionWriter
{
    void StartWriting();
    void Write(String stringToWrite);
    void WriteLine(String stringToWrite);
    void EndWriting();
}
