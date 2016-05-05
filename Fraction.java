/**
 * Created by Grigoryan on 09.04.2016.
 */
public class Fraction {
    private int _numerator;
    private int _denominator;

    // Reduces the fraction
    private void Reduce()
    {
        int firstNumber = _numerator;
        int secondNumber = _denominator;
        while (firstNumber % secondNumber != 0)
        {
            int remainder = firstNumber % secondNumber;
            firstNumber = secondNumber;
            secondNumber = remainder;
        }
        _numerator = _numerator / secondNumber;
        _denominator = _denominator / secondNumber;
    }


    public int GetNumerator()
    {
        return _numerator;
    }

    public int GetDenominator()
    {
        return _denominator;
    }

    public Fraction SetNumerator(int newNumerator)
    {
        _numerator = newNumerator;
        Reduce();
        return this;
    }

    public Fraction SetDenominator(int newDenominator) throws Error
    {
        if (newDenominator == 0)
        {
            throw new Error("Denominator cannot be 0!");
        }
        _denominator = newDenominator;
        Reduce();
        return this;
    }

    public Fraction SetNumeratorAndDenominator(int numerator, int denominator)
    {
        _numerator = numerator;
        SetDenominator(denominator);
        return this;
    }


    public Fraction Add(Fraction fractionToAdd)
    {
        int newNumerator = _numerator * fractionToAdd.GetDenominator() + _denominator * fractionToAdd.GetNumerator();
        int newDenominator = _denominator * fractionToAdd.GetDenominator();
        this.SetNumeratorAndDenominator(newNumerator, newDenominator);
        return this;
    }

    public Fraction Multiply(Fraction fractionToMultiplyBy)
    {
        _numerator = _numerator * fractionToMultiplyBy.GetNumerator();
        SetDenominator(_denominator * fractionToMultiplyBy.GetDenominator());
        return this;
    }

    public Fraction Subtract(Fraction fractionToSubtract)
    {
        Fraction minusFraction = new Fraction(fractionToSubtract);
        minusFraction.Multiply(new Fraction(-1, 1));
        this.Add(minusFraction);
        return this;
    }

    public String toString()
    {
        if (_numerator % _denominator == 0)
        {
            return String.valueOf(_numerator/_denominator);
        }
        else
        {
            String sign = "";
            if (_numerator / _denominator < 0) {
                sign = "-";
            }
            return sign + String.valueOf(Math.abs(_numerator)) + "/" + String.valueOf(Math.abs(_denominator));
        }
    }

    public static Fraction ParseFraction(String stringToParse) throws InvalidFractionException
    {
        int newNumerator, newDenominator;
        int divisionIndex = stringToParse.indexOf('/');
        try
        {
            if (divisionIndex == -1)
            {
                newNumerator = Integer.parseInt(stringToParse);
                newDenominator = 1;
            }
            else
            {
                String numeratorString = stringToParse.substring(0,divisionIndex);
                String denominatorString = stringToParse.substring(divisionIndex + 1,stringToParse.length());
                newNumerator = Integer.parseInt(numeratorString);
                newDenominator = Integer.parseInt(denominatorString);
            }
        }
        catch (NumberFormatException exc)
        {
            throw new InvalidFractionException();
        }
        return new Fraction(newNumerator,newDenominator);
    }




    public Fraction(Fraction newFraction)
    {
        _numerator = newFraction.GetNumerator();
        _denominator = newFraction.GetDenominator();
    }

    public Fraction(int numerator, int denominator)
    {
        _numerator = numerator;
        SetDenominator(denominator);
    }


}
