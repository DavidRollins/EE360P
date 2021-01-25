//UT-EID=


import java.util.*;
import java.util.concurrent.*;

public class PSort extends RecursiveTask<Integer> {
  final int[] arr;
  final int begin;
  final int end;

  public PSort(int[] arr, int begin, int end) {
    this.arr = arr;
    this.begin = begin;
    this.end = end;
  }


  private static void insertSort(int[] A){
    int n = A.length;
    for (int i = 1; i < n; ++i) {
      int key = A[i];
      int j = i - 1;

      while (j >= 0 && A[j] > key) {
        A[j + 1] = A[j];
        j = j - 1;
      }
      A[j + 1] = key;
    }

  }

  // going to implement a last element pivot
  public static void parallelSort(int[] A, int begin, int end){
    PSort startThread = new PSort(A, begin, end);
    startThread.compute();
  }

  private static void swap(int[] A, int ind1, int ind2){
    int temp = A[ind1];
    A[ind1] = A[ind2];
    A[ind2] = temp;
  }

  private static Integer partition(int[] A, int begin, int end) {
    int pivot = A[end-1];

    int lowerIndex = begin-1;
    for(int i = begin; i <= end-1; i++){

      if(A[i] < pivot){
        lowerIndex ++;
        swap(A, lowerIndex, i);
      }

    }

    swap(A, lowerIndex+1, end-1);
    return (lowerIndex+1);

  }

  protected Integer compute() {
    if(end - begin < 16){
      insertSort(arr);
      return null;
    }
    if (begin < end){

      int partition = partition(arr, begin, end);
      PSort p1 = new PSort(arr, begin, partition-1);
      PSort p2 = new PSort(arr, partition, end);
      invokeAll(p1, p2);

    }
    return null;
  }
}
