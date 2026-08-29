variable "USER_ID" {
  default = "1000"
}

variable "GROUP_ID" {
  default = "1000"
}

variable "JDK6_URL" {
  default = "https://cdn.azul.com/zulu/bin/zulu6.22.0.3-jdk6.0.119-linux_x64.tar.gz"
}

group "default" {
  targets = ["test5", "test6", "test8", "test11"]
}

target "test" {
  matrix = {
    item = [
      { level = "5", jdk = "8", jdk6 = true },
      { level = "6", jdk = "8", jdk6 = true },
      { level = "8", jdk = "8", jdk6 = false },
      { level = "11", jdk = "11", jdk6 = false },
    ]
  }
  name = "test${item.level}"
  target = "test"
  args = {
    JDK_VERSION = item.jdk
    TEST_LEVEL = item.level
    JDK6_URL = item.jdk6 ? JDK6_URL : ""
    ANT_FLAGS = item.jdk6 ? "-Dbootclasspath=/opt/jdk6/jre/lib/rt.jar -DjavacExec=/opt/jdk6/bin/javac" : ""
    USER_ID = USER_ID
    GROUP_ID = GROUP_ID
  }
  tags = ["extendj-test${item.level}-run"]
}
