package com.fluxtion.builder.util;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class ResourceUtil {

  public String readText(@NotNull String resourceName) throws IOException {
    StringWriter sw = new StringWriter();
    Reader isr = new InputStreamReader(getInputStream(resourceName), UTF_8);
    try {
      char[] chars = new char[8 * 1024];
      int len;
      while ((len = isr.read(chars)) > 0) {
        sw.write(chars, 0, len);
      }
    } finally {
      close(isr);
    }
    return sw.toString();
  }

  private void close(@Nullable Closeable closeable) {
    if (closeable != null) {
      try {
        closeable.close();
      } catch (IOException e) {
        System.err.println("Failed to close " + closeable + e.getMessage());
      }
    }
  }

  private InputStream getInputStream(@NotNull String filename) throws FileNotFoundException {
    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
    InputStream is = contextClassLoader.getResourceAsStream(filename);
    if (is != null) {
      return is;
    }
    InputStream is2 = contextClassLoader.getResourceAsStream('/' + filename);
    if (is2 != null) {
      return is2;
    }
    return new FileInputStream(filename);
  }

}
