## 3장 람다 표현식
 
### 3.1 람다란 무엇인가?
- 람다 표현식은 메서드로 전달할 수 있는 익명 함수를 단순화한 것이라고 할 수 있다.
- 람다의 특징
  - 익명 : 보통의 메서드와 달리 이름이 없으므로 익명이라 표현한다.
  - 함수 : 람다는 메서드처럼 특정 클래스에 종속되지 않으므로 함수라고 부른다.
  - 전달 : 람다 표현식을 메서드 인수로 전달하거나 변수로 저장할 수 있다.
  - 간결성 : 익명 클래스처럼 많은 자질구레한 코드를 구현할 필요가 없다.
- 람다 표현식은 파라미터, 화살표, 바디로 이루어진다.
  - (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight())
  - (String s) -> s.length()
  - (Apple a) -> a.getWeight() > 150
  - (int x, int y) -> {System.out.println();}
  - () -> 42
  - (parameters) -> expression or (parameters) -> { statements; }
- 람다의 사용 예제
  - 불리언 표현식 : (List<String> list) -> list.isEmpty()
  - 객체 생성 : () -> new Apple(10)
  - 객체에서의 소비 : (Apple a) -> {System.out.println(a.getWeight());}
  - 객체에서 선택/추출 : (String s) -> s.length()
  - 두 값을 조합 : (int a, int b) -> a*b
  - 두 객체 비교 : (Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight())
  
### 3.2 어디에, 어떻게 람다를 사용할까?
- 함수형 인터페이스라는 문맥에서 람다 표현식을 사용할 수 있다.
- 함수형 인터페이스는 오직 하나의 추상 메서드만 지정한다.
  - public interface Predicate<T> { boolean test (T t); }
  - Comparator, Runnable, ActionListener, Callable, PrivilegedAction
- 람다 표현식으로 함수형 인터페이스의 추상 메서드 구현을 직접 전달할 수 있으므로 전체 표현식을 함수형 인터페이스의 인스턴스로 취급할수 있다.
- 람다 표현식의 시그니처를 서술하는 메서드를 함수 디스크립터라고 부른다.
- @FunctionalInterface : 함수형 인터페이스에 추가할 수 있는 어노테이션

### 3.3 람다 활용 : 실행 어라운드 패턴
- 자원 처리에 사용하는 순환 패턴은 자원을 열고 처리한 다음에 자원을 닫는 순서로 이루어진다. 
- 실제 자원을 처리하는 코드를 설정과 정리 두 과정이 둘러싸는 형태를 갖는다. 쉽게 말하자면, 하나의 로직을 수행할 때 첫번째로 초기화/준비 코드가 수행되고 마지막에 정리/마무리 코드가 실행된다.

### 3.4 함수형 인터페이스 사용
- Predicate
  - test 라는 추상 메서드를 정의하여 test는 제네릭 형식 T의 객체를 인수로 받아 불리언을 반환한다.
- Consumer
  - 제네릭 형식 T 객체를 받아서 void를 반환하는 accept라는 추상 메서드를 정의한다.
  - T 형식의 객체를 인수로 받아서 어떤 동작을 수행하고 싶을 때 Consumer 인터페이스를 사용할 수 있다.
  - Integer 리스트를 인수로 받아서 각 항목을 어떤 동작을 수행하는 메서드를 정의할 때 활용할 수 있다.
- Function
  - 제네릭 형식 T를 인수로 받아서 제네릭 형식 R 객체를 반환하는 추상 메서드 apply를 정의한다.
  - 입력을 출력으로 매핑하는 람다를 정의할 때 Function 인터페이스를 활용할 수 있다.
  - 사과의 무게 정보를 추출하거나 문자열을 길이와 매핑할 때 사용할 수 있다.
- 기본형 특화
  - 자바의 모든 형식은 참조형(Byte, Integer, Object, List), 기본형(int, double, byte, char)이 있다.
  - 박싱 : 기본형을 참조형으로 변환하는 기능
  - 언박싱 : 참조형을 기본형으로 변환하는 기능

### 3.5 형식 검사, 형식 추론, 제약
- 람다가 사용되는 콘텍스트를 이용해서 람다의 형식을 추론할 수 있다.
- 어떤 콘텍스트에서 기대되는 람다 표현식의 형식을 대상 형식이라고 부른다.
- List<Apple> heavierThan150 = filter(inventory, (Apple apple) -> apple.getWeight() > 150);
  1. filter 메서드의 선언을 확인한다.
  2. filter 메서드는 두 번째 파라미터로 Predicate<Apple> 형식(대상 형식)을 기대한다.
  3. Predicate<Apple>은 test라는 한 개의 추상 메서드를 정의하는 함수형 인터페이스다.
  4. test 메서드는 Apple을 받아 boolean을 반환하는 함수 디스크립터를 묘사한다.
  5. filter 메서드로 전달된 인수는 이와 같은 요구사항을 만족해야 한다.
- 대상 형식이라는 특징 때문에 같은 람다 표현식이라도 호환되는 추상 메서드를 가진 다른 함수형 인터페이스로 사용될 수 있다.

### 3.6 메서드 참조
- 메서드 참조를 이용하면 기존의 메서드 정의를 재활용해서 람다처럼 전달할 수 있다.
- invetory.sort((Apple a1, Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
- invetory.sort(comparing(Apple::getWeight))
- 메서드 참조는 특정 메서드만을 호출하는 람다의 축약형이라고 생각할 수 있다.
- 메서드 참조는 3가지 유형으로 구분될 수 있다.
  1. 정적 메서드 참조 : Integer의 parseInt 메서드는 Integer::parseInt로 표현
  2. 다양한 형식의 인스턴스 메서드 참조 : String의 length 메서드는 String::length로 표현
  3. 기존 객체의 인스턴스 메서드 참조 : Transaction 객체를 할당받는 expensive 지역변수가 있으면, expensive::getValue로 표현
- Class:new 처럼 클래스명과 new 키워드를 이용해서 기존 생성자의 참조를 만들 수 있다.
  - Supplier<Apple> c1 = () -> new Apple(); Apple a1 = c1.get();
  - Supplier<Apple> c1 = Apple::new; Apple a1 = c1.get();

### 3.7 람다, 메서드 참조 활용하기

### 3.8 람다 표현식을 조합할 수 있는 유용한 메서드
- 함수를 여러개를 조합할 수 있다.
- Comparator 조합
  1. 역정렬 : inventory.sort(comparing(Apple::getWeight).reversed());
  2. Comperator 연결 : inventory.sort(comparing(Apple::getWeight).reversed().thenComparing(Apple::getCountry));
- Predicate 조합 : .and.or 사용
- Fucntion 조합 : .andThen.compose 사용

### 마치며
- 람다 표현식은 익명 함수의 일종이다. 이름은 없지만, 파라미터 리시트 바디 반환 형식을 가지며 예외를 던질 수 있다.
- 람다 표현식으로 간결한 코드를 구현할 수 있다.
- 함수형 인터페이스는 하나의 추상 메서드만을 정의하는 인터페이스다.
- 함수형 인터페이스를 기대하는 곳에서만 람다 표현식을 사용할 수 있다.
- 람다 표현식을 이용해서 함수형 인터페이스의 추상 메서드를 즉석으로 제공할 수 있으며, 람다 표현식 전체가 함수형 인터페이스의 인스턴스로 취급된다.
- 실행 어라운드 패턴을 람다와 활용하면 유연성과 재사용성을 추가로 얻을 수 있다.
- 람다 표현식의 기대 형식을 대상 형식이라고 한다.
- 메서드 참조를 이용하면 기존의 메서드 구현을 재사용하고 직접 전달할 수 있다.
- Comparator, Predicate, Function 같은 함수형 인터페이스는 람다 표현식을 조합할 수 있는 다양한 디폴트 메서드를 제공한다.