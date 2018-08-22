package io.moonlight.utils.test.algorithm;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author qt
 * @version 1.0  createAt 2018/8/22
 */
public class Sort {

    public static void bubbleSort(int[] nums) {

        if (nums == null || nums.length == 0) {
            return;
        }

        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = nums.length - 1; j > i; j--) {
                if (nums[j] < nums[j - 1]) {
                    int t = nums[j - 1];
                    nums[j - 1] = nums[j];
                    nums[j] = t;
                }
            }
        }
    }

    public static void selectionSort(int[] nums) {
        int len = nums.length;
        int minIndex, temp;
        for (int i = 0; i < nums.length - 1; i++) {
            minIndex = i;
            for (int j = i + 1; j < len; j++) {
                if (nums[j] < nums[minIndex]) {
                    minIndex = j;
                }
            }
            temp = nums[i];
            nums[i] = nums[minIndex];
            nums[minIndex] = temp;
        }
    }

    public static void shellSort(int[] nums) {
        int len = nums.length;
        int temp, gap = 1;
        while (gap < len / 2) {
            gap = gap * 2 + 1;
        }
        System.out.println(gap);
        for (; gap > 0; gap = (int) Math.floor(gap / 3)) {
            System.out.println("gap=" + gap);
            for (int i = gap; i < len; i++) {

                temp = nums[i];
                int j = i - gap;
                System.out.println("j=" + j + " i=" + i);
                for (; j > 0 && nums[j] > temp; j -= gap) {
                    nums[j + gap] = nums[j];
                }
                nums[j + gap] = temp;
            }
            System.out.println();
            System.out.println(Arrays.toString(nums));
        }
    }


    public static int[] mergeSort(int[] nums) {
        int middle = nums.length / 2;
        int[] left = Arrays.copyOfRange(nums, 0, middle);
        int[] right = Arrays.copyOfRange(nums, middle, nums.length);
        return merger(mergeSort(left), mergeSort(right));
    }

    private static int[] merger(int[] nums1, int[] nums2) {
        int i = 0, j = 0, k = 0;
        int[] result = new int[nums1.length + nums2.length];
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] <= nums2[j]) {
                result[k++] = nums1[i++];
            } else {
                result[k++] = nums2[j++];
            }
        }
        while (i < nums1.length) {
            result[k++] = nums1[i++];
        }
        while (i < nums2.length) {
            result[k++] = nums2[j++];
        }
        return result;
    }


    public static void quickSort(int[] nums, int l, int h) {
        if (nums.length <= 0)
            return;
        if (l > h)
            return;
        int left = l;
        int right = h;
        int temp = nums[left];

        while (left < right) {
            //坑2：从后向前找到比基准小的元素，插入到基准位置坑1中
            while (left < right && nums[right] >= temp) {
                right--;
            }
            nums[left] = nums[right];
            //坑3：从前往后找到比基准大的元素，放到刚才挖的坑2中
            while (left < right && nums[left] <= temp) {
                left++;
            }
            nums[right] = nums[left];
        }
        nums[left] = temp;
        quickSort(nums, l, left - 1);
        quickSort(nums, left + 1, h);
    }

    @Test
    public void test1() {
        int[] nums = new int[]{0, 4, 6, 2, 1};
        Sort.shellSort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
