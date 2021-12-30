#include <stdio.h>
#include <time.h>
#include <math.h>

// iterative factorial
long double factorial(unsigned long n)
{
    long double res = 1, i;
    for (i = 2; i <= n; i++)
        res *= i;
    return res;
}

int main(int argc, char** argv)
{
    int upper_i = 0;
    double angle = 0;
    long double time_taken = 0;
    long double cosine = 0;
    printf("Enter Upper value of i: ");
    scanf_s("%d", &upper_i);
    printf("Enter Angle: ");
    scanf_s("%lf", &angle);
    angle = angle * (3.14 / 180);
    clock_t t;
    t = clock();

    for (int i = 0; i < upper_i; i++)
    {
        cosine += (powl(-1, i) * powl(angle, 2 * i)) / (factorial(2 * i));
    }
    printf("Cosine value is %f \n", cosine);
    t = clock() - t;
    time_taken = ((double)t) / CLOCKS_PER_SEC; // in seconds

    printf("fun() took %f seconds to execute \n", time_taken);
}