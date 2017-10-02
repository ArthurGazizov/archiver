package com.arthur.gazizov.archiver.core.service.lz77;

import com.arthur.gazizov.archiver.core.service.AbstractDecompressor;
import com.arthur.gazizov.archiver.core.utils.ByteUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Arthur Gazizov (Cinarra Systems)
 * Created on 02.10.17.
 */
public class DecompressorLZ77Impl extends AbstractDecompressor {
  @Override
  protected byte[] process(byte[] bytes) {
    return process(CodeSequence.loadCodesFromBytes(bytes));
  }

  private byte[] process(CodeSequence codes) {
    List<Byte> bytes = new ArrayList<>();
    for (Code code : codes) {
      if (code.getOffset() >= 0) {
        for (int i = 0; i < code.getLength(); i++) {
          bytes.add(bytes.get(code.getOffset() + i));
        }
      }
      bytes.add(code.getLastValue());
    }
    return ByteUtils.unboxing(bytes);
  }
}
