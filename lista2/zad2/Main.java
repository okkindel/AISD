import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String Name = "";
        int k = 0;
        Random rand = new Random();

        if (args.length == 0) {
            System.out.println("No parameters");
            System.exit(0);
        }

        if (args[0].equals("--stat")) {
            Name = args[1];
            k = Integer.parseInt(args[2]);
        } else {
            System.err.println("Wrong parameters.");
            System.exit(0);
        }
        PrintWriter saveFile = new PrintWriter(Name + ".txt");

        StringBuilder Num = new StringBuilder("No = \"");
        StringBuilder qsCompare = new StringBuilder("qC = \"");
        StringBuilder qsSwap = new StringBuilder("qS = \"");
        StringBuilder qsTime = new StringBuilder("qT = \"");
        StringBuilder isCompare = new StringBuilder("iC = \"");
        StringBuilder isSwap = new StringBuilder("iS = \"");
        StringBuilder isTime = new StringBuilder("iT = \"");
        StringBuilder msCompare = new StringBuilder("mC = \"");
        StringBuilder msSwap = new StringBuilder("mS = \"");
        StringBuilder msTime = new StringBuilder("mT = \"");

        for (int n = 100; n <= 10000; n = n + 100) {
            for (int i = 0; i < k; i++) {
                int[] array = new int[n];

                for (int j = 0; j < n; j++) {
                    array[j] = rand.nextInt(n);
                }
                int[] array1 = Arrays.copyOf(array, array.length);
                int[] array2 = Arrays.copyOf(array, array.length);
                int[] array3 = Arrays.copyOf(array, array.length);
                Num.append(Integer.toString(n)).append(" ");

                long startTimeI = System.nanoTime();
                InsertionSort is = new InsertionSort(array1, array1.length);
                isTime.append(System.nanoTime() - startTimeI).append(" ");
                isCompare.append(Integer.toString(is.counterC)).append(" ");
                isSwap.append(Integer.toString(is.counterS)).append(" ");

                long startTimeM = System.nanoTime();
                MergeSort ms = new MergeSort(array2, array2.length);
                msTime.append(System.nanoTime() - startTimeM).append(" ");
                msCompare.append(Integer.toString(ms.counterC)).append(" ");
                msSwap.append(Integer.toString(ms.counterS)).append(" ");

                long startTimeQ = System.nanoTime();
                QuickSort qs = new QuickSort(array3, array3.length);
                qsTime.append(System.nanoTime() - startTimeQ).append(" ");
                qsCompare.append(Integer.toString(qs.counterC)).append(" ");
                qsSwap.append(Integer.toString(qs.counterS)).append(" ");


            }
        }
        saveFile.write(Num + "\"\n" + qsCompare + "\"\n" + qsSwap + "\"\n" + qsTime + "\"\n" + msCompare + "\"\n" + msSwap + "\"\n" + msTime + "\"\n" + isCompare + "\"\n" + isSwap + "\"\n" + isTime + "\"\n");
        saveFile.close();
    }
}


class InsertionSort {
    int counterC = 0;
    int counterS = 0;

    InsertionSort(int[] array, int length) {
        insertionSort(array, length);
    }

    private void insertionSort(int[] array, int length) {
        for (int index = 1; index < length; index++) {
            int value = array[index];
            int i = index - 1;
            while (i >= 0) {
                    // System.err.println("compare: while (value <= array[i])");
                counterC++;
                if (value <= array[i]) {
                    array[i + 1] = array[i];
                    array[i] = value;
                        // System.err.println("swap: array[i + 1] = array[i]");
                    counterS++;
                }
                i--;
            }
        }
    }
}

class MergeSort {
    int counterC = 0;
    int counterS = 0;

    MergeSort(int[] array, int length) {
        mergeSort(array, length);
    }

    private void mergeSort(int[] array, int length) {

        if (length > 1) {
            int mid = length / 2;

            int[] left = Arrays.copyOfRange(array, 0, array.length / 2);
            int[] right = Arrays.copyOfRange(array, mid, array.length);

            mergeSort(left, mid);
            mergeSort(right, (length - mid));

            int i = 0, j = 0, k = 0;
            while (i < mid && j < (length - mid)) {
                if (left[i] < right[j]) {
//                    System.err.println("compare: if (left[i] < right[j])");
                    counterC++;
                    array[k] = left[i];
                    i++;
                } else {
//                    System.err.println("compare: if (left[i] < right[j])");
                    counterC++;
//                    System.err.println("pseudoswap");
                    counterS++;
                    array[k] = right[j];
                    j++;
                }
                k++;
            }
            while (i < mid) {
                array[k] = left[i];
                i++;
                k++;
            }
            while (j < (length - mid)) {
                array[k] = right[j];
                j++;
                k++;
            }
        }
    }
}

class QuickSort {
    int counterC = 0;
    int counterS = 0;

    QuickSort(int array[], int length) {
        quickSort(array, 0, length - 1);
    }

    private void quickSort(int array[], int begin, int length) {

        int i = begin, j = length;
        int pivot = array[begin + (length - begin) / 2];

        while (i <= j) {
            while (array[i] < pivot) {
                //               System.err.println("compare: while (array[i] < pivot)");
                counterC++;
                i++;
            }
            while (array[j] > pivot) {
//                System.err.println("compare: while (array[j] > pivot)");
                counterC++;
                j--;
            }
            if (i <= j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
//                System.err.println("swap: array[i] = array[j];");
                counterS++;
                i++;
                j--;
            }
        }
        if (begin < j) {
            quickSort(array, begin, j);
        }
        if (i < length) {
            quickSort(array, i, length);
        }

    }
}
