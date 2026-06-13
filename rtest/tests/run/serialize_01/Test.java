// Test serializing and deserializing a simple object.
import java.io.*;

public class Test implements Serializable {
  public String name;
  public int value;

  public Test() {
  }

  public Test(String name, int value) {
    this.name = name;
    this.value = value;
  }

  public String toString() {
    return name + ": " + value;
  }

  public static void main(String[] args) throws IOException, ClassNotFoundException {
    byte[] data = marshall(new Test("Beef", 0xBEEF));
    Test t = unmarshall(data);
    System.out.println(t);
  }

  static byte[] marshall(Test t) throws IOException {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    ObjectOutputStream oo = new ObjectOutputStream(out);
    oo.writeObject(t);
    return out.toByteArray();
  }

  static Test unmarshall(byte[] data) throws IOException, ClassNotFoundException {
    ByteArrayInputStream in = new ByteArrayInputStream(data);
    ObjectInputStream oi = new ObjectInputStream(in);
    return (Test) oi.readObject();
  }
}
