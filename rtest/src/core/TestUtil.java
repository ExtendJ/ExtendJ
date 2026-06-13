/* Copyright (c) 2016, The JastAdd Team
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

// Inspired by Google Truth.
// We use this lightweight implementation to get some useful assertions without having to
// include lots of dependencies. This makes the Ant build simpler.
// This is mostly copied directly from the JastAdd regression tests framework.
public class TestUtil {
  public static TestSubject testThat(Object o) {
    return new TestSubject(o);
  }

  public static <T> IterableTestSubject<T> testThat(Iterable<T> o) {
    return new IterableTestSubject<T>(o);
  }

  public static class TestSubject {
    private final Object subject;

    public TestSubject(Object obj) {
      this.subject = obj;
    }

    public boolean equals(Object expected) {
      assertEquals(expected, subject);
      return true;
    }

    public void isSame(Object expected) {
      assertSame(expected, subject);
    }

    public void isSameAs(Object expected) {
      assertSame(expected, subject);
    }

    public void isEqualTo(Object expected) {
      assertEquals(expected, subject);
    }

    public void isNotNull() {
      assertNotNull(subject);
    }
  }

  public static class IterableTestSubject<T> extends TestSubject {
    private final Iterable<T> subject;

    public IterableTestSubject(Iterable<T> iter) {
      super(iter);

      this.subject = iter;
    }

    /** Test that the subject is empty. */
    public void isEmpty() {
      if (subject.iterator().hasNext()) {
        error(toString() + " not empty when expected to be empty");
      }
    }

    public void hasSize(int size) {
      assertEquals("Wrong collection size.", size, size());
    }

    private int size() {
      int size = 0;
      Iterator<T> iter = subject.iterator();
      while (iter.hasNext()) {
        iter.next();
        size += 1;
      }
      return size;
    }

    /**
     * Test that the subject contains the same elements as the expected
     * elements, independent of order.
     */
    public void containsExactly(String message, Iterable<T> expected) {
      if (!isEqualCollection(subject, expected)) {
        String newln = System.getProperty("line.separator", "\n");
        StringBuilder expectedSb = new StringBuilder();
        StringBuilder actualSb = new StringBuilder();
        expectedSb.append("[");
        actualSb.append("[");
        Map<Object, Integer> map = new java.util.HashMap<Object, Integer>();
        for (Object o : subject) {
          Integer val = map.get(o);
          int count = (val == null) ? 0 : val;
          map.put(o, count + 1);
        }
        for (Object o : expected) {
          Integer val = map.get(o);
          int count;
          boolean missing = false;
          if (val != null) {
            count = val;
            if (count == 0) {
              missing = true;
            } else {
              map.put(o, count - 1);
            }
          } else {
            missing = true;
          }
          expectedSb.append(newln);
          if (missing) {
            expectedSb.append("--  ");
          } else {
            expectedSb.append("    ");
          }
          expectedSb.append("" + o);
        }
        for (Object o : subject) {
          Integer val = map.get(o);
          int count = (val == null) ? 0 : val;
          boolean extra = count != 0;
          actualSb.append(newln);
          if (extra) {
            actualSb.append("++  ");
          } else {
            actualSb.append("    ");
          }
          actualSb.append("" + o);
        }
        if (expectedSb.length() > 1) {
          expectedSb.append(newln);
          expectedSb.append("]");
        }
        if (actualSb.length() > 1) {
          actualSb.append(newln);
          actualSb.append("]");
        }
        error("%s Expected: %s, but was: %s", message, expectedSb.toString(), actualSb.toString());
      }
    }

    /**
     * Test that the subject contains the same elements as the expected
     * elements, independent of order.
     */
    public void containsExactly(T... expected) {
      ArrayList<T> list = new ArrayList<T>();
      for (T item : expected) {
        list.add(item);
      }
      if (!isEqualCollection(subject, list)) {
        error("Collections are not equal. Expected: %s, was: %s", list, subject);
      }
    }

    // See http://llbit.se/?p=2009 for algorithm description.
    private static boolean isEqualCollection(Iterable<?> a, Iterable<?> b) {
      Map<Object, Integer> map = new java.util.HashMap<Object, Integer>();
      for (Object o : a) {
        Integer val = map.get(o);
        int count = (val == null) ? 0 : val;
        map.put(o, count + 1);
      }
      for (Object o : b) {
        Integer val = map.get(o);
        int count;
        if (val != null) {
          count = val;
          if (count == 0) {
            return false;
          }
        } else {
          return false;
        }
        map.put(o, count - 1);
      }
      for (Integer count : map.values()) {
        if (count != 0) {
          return false;
        }
      }
      return true;
    }

    @Override public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("[");
      Iterator<T> iter = subject.iterator();
      while (iter.hasNext()) {
        if (sb.length() > 1) {
          sb.append(", ");
        }
        sb.append("" + iter.next());
      }
      return sb.append("]").toString();
    }
  }

  public static void error(String message, Object... args) {
    if (args != null) {
      fail(String.format(message, args));
    } else {
      fail(message);
    }
  }

}
