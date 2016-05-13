/**
 * Created by Grigoryan on 12.05.2016.
 */
public class SLEContainer {

    private Fraction[][] _sle;

    public void SetSLE(Fraction[][] sle)
    {
        if (sle == null)
        {
            throw new IllegalArgumentException("sle should not be null!");
        }
        _sle = sle;
    }

    public Fraction[][] GetSLE()
    {
        return _sle;
    }


    public SLEContainer(Fraction[][] sle)
    {
        SetSLE(sle);
    }

}
