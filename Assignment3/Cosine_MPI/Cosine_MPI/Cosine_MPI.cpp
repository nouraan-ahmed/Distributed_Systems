#include "mpi.h"
#include <stdio.h>
#include <math.h>
#include <iostream>

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
    int upper_i = 0, num_processes;
    int myRank;
    long double local_cosine = 0;
    long double total_cosine = 0;
    long double starttime, endtime;
    double angle = 0;
    long double duration = 0;

    // Initialize the MPI environment
    MPI_Init(NULL, NULL);
    // Get the number of processes
    MPI_Comm_size(MPI_COMM_WORLD, &num_processes);
    // Get the rank of the process
    MPI_Comm_rank(MPI_COMM_WORLD, &myRank);

    //getInput(myRank, num_processes,&upper_i,&angle);

    int dest;
    if (myRank == 0) {
        printf("Enter upper limit \n");
        std::cin >> upper_i;
        printf("Enter angle \n");
        std::cin >> angle;
        for (dest = 1; dest < num_processes; dest++) {
            MPI_Send(&upper_i, 1, MPI_DOUBLE, dest, 0, MPI_COMM_WORLD);
            MPI_Send(&angle, 1, MPI_DOUBLE, dest, 0, MPI_COMM_WORLD);
        }
    }
    else {
        MPI_Recv(&upper_i, 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        MPI_Recv(&angle, 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
    }

    angle = angle * (3.14 / 180);

    starttime = MPI_Wtime();
    for (int k = myRank; k < upper_i; k += num_processes) {
        local_cosine += (powl(-1, k) * powl(angle, 2 * k)) / (factorial(2 * k));
    }
    if (myRank != 0) {
        MPI_Send(&local_cosine, 1, MPI_DOUBLE, 0, 0, MPI_COMM_WORLD);
    }
    else {
        total_cosine = local_cosine;
        for (int source = 1; source < num_processes; source++) {
            MPI_Recv(&local_cosine, 1, MPI_DOUBLE, source, 0, MPI_COMM_WORLD, MPI_STATUSES_IGNORE);
            total_cosine += local_cosine;
        }
    }
    endtime = MPI_Wtime();
    duration = endtime - starttime;

    if (myRank == 0) {
        printf("The Output Of the Cosine Function is : %f \n", local_cosine);
        printf("The Time Taken is : %f \n", duration);
    }

    // Finalize the MPI environment.
    MPI_Finalize();
}