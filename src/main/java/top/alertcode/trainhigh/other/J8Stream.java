package top.alertcode.trainhigh.other;

import com.alibaba.fastjson.JSON;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Data;

/**
 * The type J 8 stream.
 *
 * @author alertcode
 * @date 2018 -09-11
 * @copyright alertcode.top
 */
public class J8Stream {


  /**
   * The entry point of application.
   *
   * @param args
   *     the input arguments
   */
  public static void main(String[] args) {

    flatMapStream();
    mapStream();
    foreachStream();
    peekStream();
    reduceStream();
    limitSkipStream();
    filterStream();
    distinctStream();
    mergeStream();
  }

  /**
   * flatMap 操作.
   */
  public static void flatMapStream() {
    Stream<List<Integer>> inputStream = Stream.of(
        Arrays.asList(1),
        Arrays.asList(2, 3),
        Arrays.asList(4, 5, 6)
    );
    List<Integer> collect = inputStream.flatMap((childList) -> childList.stream())
        .collect(Collectors.toList());
    System.out.println(JSON.toJSONString(collect));
  }

  /**
   * foreach 操作.
   */
  public static void foreachStream() {
    Arrays.asList(4, 5, 6).stream().forEach(System.out::print);
    System.out.println();
  }

  /**
   * map 操作
   */
  public static void mapStream() {
    List<Integer> collect = Arrays.asList(4, 5, 6).stream().map(e -> ++e).collect(Collectors.toList());
    System.out.println(JSON.toJSONString(collect));
  }

  /**
   * reduce 操作
   */
  public static void reduceStream() {
    int sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
    int sumValue1 = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
    System.out.println(sumValue + "::" + sumValue1);
  }

  /**
   * filter操作
   */
  public static void filterStream() {
    List<Integer> collect = Arrays.asList(1, 2, 3, 4, 5, 6).stream().filter(e -> e == 3)
        .collect(Collectors.toList());
    System.out.println(JSON.toJSONString(collect));
  }

  /**
   * skip limit 操作
   */
  public static void limitSkipStream() {
    List<Integer> list = Stream.iterate(0, item -> item + 1).limit(10).skip(3).collect(Collectors.toList());
    System.out.println(JSON.toJSONString(list));
  }

  /**
   * peek 操作 跟map操作类似，产生Consumer消费者
   */
  public static void peekStream() {
    List<String> collect = Stream.of("one", "two", "three", "four")
        .filter(e -> e.length() > 3)
        .peek(e -> System.out.println("Filtered value: " + e))
        .map(String::toUpperCase)
        .peek(e -> System.out.println("Mapped value: " + e))
        .collect(Collectors.toList());
    System.out.println(JSON.toJSONString(collect));
  }

  /**
   * distinct 操作.
   */
  public static void distinctStream() {
    List<Integer> collect = Arrays.asList(1, 2, 3, 3, 3, 4, 4).stream().distinct()
        .collect(Collectors.toList());
    System.out.println(JSON.toJSONString(collect));
  }

  /**
   * 两个list 合并操作 stream.
   */
  public static void mergeStream() {

    Set<User> collect = Stream.iterate(1, item -> item + 1).map(e -> {
      User user = new User();
      user.setUserId(e);
      user.setValue("value" + e);
      return user;
    }).limit(10).skip(5).collect(Collectors.toSet());
    System.out.println(JSON.toJSONString(collect));

    List<User> collect1 = Stream.iterate(6, item -> item + 1).map(e -> {
      User user = new User();
      user.setUserId(e);
      user.setUserName("userName" + e);
      return user;
    }).limit(10).collect(Collectors.toList());
    System.out.println(JSON.toJSONString(collect1));
    List<User> collect2 = collect1.stream().peek(e -> collect.forEach(x -> {
      if (x.getUserId().equals(e.getUserId())) {
        e.setValue(x.getValue());
      }
    })).filter(e -> Objects.nonNull(e.getValue())).collect(Collectors.toList());
    System.out.println(JSON.toJSONString(collect2));
  }
}


/**
 * The type User.
 */
@Data
class User {

  private Integer userId;
  private String userName;
  private String value;
}
