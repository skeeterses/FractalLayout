// Look at the Java Documentation on Integer for a guideline of how to
// implement the various operations of Complex since Java doesn't allow for
// operator overloading.

import java.lang.*;

class Complex{
  double real, imaginary;
  
  Complex()
  {
     real = 0.0;
     imaginary = 0.0;
  }

  Complex(double r, double i)
  {
     real = r;
     imaginary = i;
  }
  
  Complex(Complex input)
  {
     real = input.real;
     imaginary = input.imaginary;
  }
  
  Complex add(Complex input)
  {
     Complex result = new Complex();
     result.real = real + input.real;
     result.imaginary = imaginary + input.imaginary;
     return result;
  }

  Complex subtract(Complex input)
  {
     Complex result = new Complex();
     result.real = real - input.real;
     result.imaginary = imaginary - input.imaginary;
     return result;
  }

  Complex multiply(Complex input)
  {
    Complex result = new Complex();
    result.real = real * input.real - imaginary * input.imaginary;
    result.imaginary = real * input.imaginary + imaginary * input.real;
    return result;
  }

  Complex divide(Complex input)
  {
     double temp;
     Complex result = new Complex();
     temp = input.real * input.real + input.imaginary * input.imaginary;
     result.real = (real * input.real + imaginary * input.imaginary)/temp;
     result.imaginary = (imaginary * input.real - real * input.imaginary)/temp;
     return result;
  }

  Complex multiply(double input)
  {
    Complex result = new Complex();
    result.real = real * input;
    result.imaginary = imaginary * input;
    return result;
  }

  Complex normal()
  {
    double temp;
    Complex result = new Complex();
    temp = real * real + imaginary * imaginary;
    result.real = real / temp;
    result.imaginary = -imaginary / temp;
    return result;
  }

  void assign(double inputReal, double inputImaginary)
  {
     real = inputReal;
     imaginary = inputImaginary;
  }

  void assign(Complex input)
  {
     real = input.real;
     imaginary = input.imaginary;
  }

  double r()
  {
     return real;
  }
  
  double i()
  {
     return imaginary;
  }

  boolean equals(Complex input)
  {
     if ((Math.abs(input.real - real) < 0.01) && 
		     (Math.abs(input.imaginary - imaginary) < 0.01))
	     return true;
     else
	     return false;
  }

  // The C++ version is not mathematically correct and may have to be
  // re-written to work in Java
  boolean notEquals(Complex input)
  {
      if ((input.real - real) == (input.imaginary - imaginary))
	      return false;
      else
	      return true;
  }

  void output()
  {
    System.out.println("Real = " + real + ", Imaginary = " + imaginary);
  }
}
