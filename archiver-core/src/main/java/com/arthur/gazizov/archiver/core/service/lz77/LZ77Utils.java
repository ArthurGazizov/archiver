package com.arthur.gazizov.archiver.core.service.lz77;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class LZ77Utils {
  private static int[] calculatePrefixArray(ByteBuffer buffer) {
    int[] prefixArray = new int[buffer.size()];
    prefixArray[0] = 0;
    for (int i = 1, j = 0; i < prefixArray.length; i++) {
      while (!buffer.get(i).equals(buffer.get(j))) {
        if (j == 0) {
          break;
        }
        j = prefixArray[j - 1];
      }
      if (buffer.get(i).equals(buffer.get(j))) {
        prefixArray[i] = j + 1;
        j++;
      } else {
        prefixArray[i] = j;
      }
    }
    return prefixArray;
  }

  public static int indexOfSubArray(ByteBuffer slidingWindow, ByteBuffer buffer) {
    int[] prefixArr = calculatePrefixArray(buffer);
    int indexToReturn = -1;
    for (int m = 0, s = 0; m < slidingWindow.size(); m++) {
      if (buffer.get(s).equals(slidingWindow.get(m))) {
        s++;
      } else {
        if (s != 0) {
          s = prefixArr[s - 1];
          m--;
        }
      }
      if (s == buffer.size()) {
        indexToReturn = m - buffer.size() + 1;
        break;
      }
    }

    return indexToReturn;
  }
}
