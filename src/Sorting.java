import java.util.LinkedList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Depei Yu
 * @version 1.0
 * @userid dyu79
 * @GTID 903312858
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement selection sort.
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new java.lang.IllegalArgumentException("The array cannot be null.");
        }

        if (comparator == null) {
            throw new java.lang.IllegalArgumentException("The comparator cannot be null.");
        }

        for (int i = 0; i < arr.length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            T temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new java.lang.IllegalArgumentException("The array cannot be null.");
        }

        if (comparator == null) {
            throw new java.lang.IllegalArgumentException("The comparator cannot be null.");
        }

        boolean swapsMade = true;
        int startInd = 0;
        int lastForwardSwap = 0;
        int endInd = arr.length - 1;
        int lastBackwardSwap = arr.length - 1;

        while (swapsMade) {
            swapsMade = false;
            for (int i = startInd; i < endInd; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T temp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = temp;
                    swapsMade = true;
                    lastForwardSwap = i;
                }
            }
            endInd = lastForwardSwap;
            if (swapsMade) {
                swapsMade = false;
                for (int j = endInd; j > startInd; j--) {
                    if (comparator.compare(arr[j - 1], arr[j]) > 0) {
                        T temp4 = arr[j];
                        arr[j] = arr[j - 1];
                        arr[j - 1] = temp4;
                        swapsMade = true;
                        lastBackwardSwap = j;
                    }
                }
                startInd = lastBackwardSwap;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new java.lang.IllegalArgumentException("The array cannot be null.");
        }

        if (comparator == null) {
            throw new java.lang.IllegalArgumentException("The comparator cannot be null.");
        }

        if (arr.length <= 1) {
            return;
        }
        int midpoint = (arr.length / 2);

        T[] leftArray = (T[]) (new Object[midpoint]);
        T[] rightArray = (T[]) (new Object[arr.length - midpoint]);

        for (int i = 0; i < leftArray.length; i++) {
            leftArray[i] = arr[i];
        }
        for (int j = 0; j < rightArray.length; j++) {
            rightArray[j] = arr[j + leftArray.length];
        }

        mergeSort(leftArray, comparator);
        mergeSort(rightArray, comparator);

        int leftIndex = 0;
        int rightIndex = 0;
        int currentIndex = 0;

        while ((leftIndex < midpoint) && (rightIndex < (arr.length - midpoint))) {
            if (comparator.compare(leftArray[leftIndex], rightArray[rightIndex]) <= 0) {
                arr[currentIndex] = leftArray[leftIndex];
                leftIndex += 1;
            } else {
                arr[currentIndex] = rightArray[rightIndex];
                rightIndex += 1;
            }
            currentIndex += 1;
        }
        while (leftIndex < midpoint) {
            arr[currentIndex] = leftArray[leftIndex];
            leftIndex += 1;
            currentIndex += 1;
        }
        while (rightIndex < (arr.length - midpoint)) {
            arr[currentIndex] = rightArray[rightIndex];
            rightIndex += 1;
            currentIndex += 1;
        }
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param k          the index to retrieve data from + 1 (due to
     *                   0-indexing) if the array was sorted; the 'k' in "kth
     *                   select"; e.g. if k == 1, return the smallest element
     *                   in the array
     * @param arr        the array that should be modified after the method
     *                   is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @return the kth smallest element
     * @throws java.lang.IllegalArgumentException if the array or comparator
     *                                            or rand is null or k is not
     *                                            in the range of 1 to arr
     *                                            .length
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (arr == null) {
            throw new java.lang.IllegalArgumentException("The array cannot be null.");
        }

        if (comparator == null) {
            throw new java.lang.IllegalArgumentException("The comparator cannot be null.");
        }

        if (rand == null) {
            throw new java.lang.IllegalArgumentException("The rand cannot be null.");
        }

        if ((k < 1) || (k > arr.length)) {
            throw new java.lang.IllegalArgumentException("k has to be between 1 and the array length.");
        }

        return kthSelectHelper(k, arr, 0, arr.length - 1, rand, comparator);
    }

    /**
     * The helper method for kth select.
     * @param k The index to retrieve the data from + 1
     * @param arr The array that should be modified after the method
     * @param left The start index for quick sort
     * @param right The end index for quick sort
     * @param rand The Random object used to select pivots
     * @param comparator The Comparator used to compare the data in arr
     * @return the kth smallest element
     * @param <T> data type to sort
     */
    private static <T> T kthSelectHelper(int k, T[] arr, int left, int right, Random rand, Comparator<T> comparator) {

        int pivotIndex = rand.nextInt(right - left + 1) + left;
        T pivotValue = arr[pivotIndex];
        T temp1 = arr[left];
        arr[left] = arr[pivotIndex];
        arr[pivotIndex] = temp1;
        int leftIndex = left + 1;
        int rightIndex = right;

        while (leftIndex <= rightIndex) {
            while ((leftIndex <= rightIndex) && (comparator.compare(arr[leftIndex], pivotValue) <= 0)) {
                leftIndex += 1;
            }
            while ((leftIndex <= rightIndex) && (comparator.compare(arr[rightIndex], pivotValue) >= 0)) {
                rightIndex -= 1;
            }
            if (leftIndex <= rightIndex) {
                T temp = arr[leftIndex];
                arr[leftIndex] = arr[rightIndex];
                arr[rightIndex] = temp;
                leftIndex += 1;
                rightIndex -= 1;
            }
        }
        T temp2 = arr[left];
        arr[left] = arr[rightIndex];
        arr[rightIndex] = temp2;

        if (rightIndex == (k - 1)) {
            return arr[rightIndex];
        } else if (rightIndex > (k - 1)) {
            return kthSelectHelper(k, arr, left, rightIndex - 1, rand, comparator);
        } else {
            return kthSelectHelper(k, arr, rightIndex + 1, right, rand, comparator);
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new java.lang.IllegalArgumentException("The array cannot be null.");
        }

        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<>();
        }

        int iterations = 0;
        int largestMagnitude = 0;
        for (int j = 0; j < arr.length; j++) {
            if ((arr[j] == Integer.MAX_VALUE) || (arr[j] == Integer.MIN_VALUE)) {
                largestMagnitude = Integer.MAX_VALUE;
            } else if (Math.abs(arr[j]) > largestMagnitude) {
                largestMagnitude = Math.abs(arr[j]);
            }
        }

        while (largestMagnitude > 0) {
            iterations += 1;
            largestMagnitude = largestMagnitude / 10;
        }

        int divider = 1;
        for (int k = 0; k < iterations; k++) {
            for (int x = 0; x < arr.length; x++) {
                int temp = arr[x] / divider;
                int digit = temp % 10;
                if (buckets[digit + 9] == null) {
                    buckets[digit + 9] = new LinkedList<>();
                }
                buckets[digit + 9].add(arr[x]);
            }
            int index = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (bucket.size() > 0) {
                    arr[index++] = bucket.pop();
                }
            }
            divider = divider * 10;
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException("Data cannot be null.");
        }
        int[] arrayToReturn = new int[data.size()];
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(data);
        for (int i = 0; i < arrayToReturn.length; i++) {
            arrayToReturn[i] = minHeap.poll();
        }
        return arrayToReturn;
    }
}
