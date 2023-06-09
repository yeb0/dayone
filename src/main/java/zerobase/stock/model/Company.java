package zerobase.stock.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 굳이 entity 냅두고 따로 model 로 빼서 쓰는 이유?
 * entity 는 db와 직접적으로 mapping 되는 클래스이기때문에
 * entity 인스턴스를 service 코드 내부에서 데이터를 주고 받기 위한 용도로 쓰거나
 * 이 과정에서 데이터를 변경하고 이러한 로직이 들어가게되면
 * entity 의 역할을 벗어날 수가 있음. ( cf) 재사용성 ! )
 * 이렇게 역할을 벗어나버리면 좋지 못함. 중요한 것은 코드가 본래 자신의 역할에만 충실해야한다는 것.
 * 한 코드가 이것저것 다 맡아버리면 코드가 복잡하고, 사이드이펙트 발생 가능성.
 * 유지보수 힘들어지고.. 안 좋음.
 * 그래서 dto 나 dao 가 나오는 것.
 */

/**
 * 클래스 바깥에서 멤버변수를 임의로 변경하면 안되는 객체가 있을 수 있음.
 * 근데 아무 생각 없이 그냥 @Data 를 붙여버리면 그 안에 있는 @Setter 로 인해
 * 외부에서 멤버변수에 접근해서 Setter 로 값을 바꾸는 것을 허용해버리게 되는 것이다.
 * equal 메서드 같은 경우에서도, 이후에 객체 비교가 중요한 연산에서 내가 원하는 대로
 * 연산하는지 확인해줘야만 한다.
 * 예를 들어,
 * 핸드폰 번호 / 이름 / 성 이 있다면 핸드폰 번호와 이름이 같다면 같다고 보려 하는데
 * 010-1234-1234 / 상훈 / 우
 * 010-1234-1234 / 상훈 / 김
 * 자동생성된 로직에서는 이 객체가 가지고 있는 데이터 중에
 * 번호, 이름, 성까지 모두 같아야 같다고 처리해버린다면 우리가 원하는 값이 안 나올 것.
 * 그래서 따로 equals 나 hashcode 를 따로 직접 구현해야함.
 * 아니면 @Data 를 쓰더라도 내가 원하는 대로 로직이 흘러가는지 꼭 확인할 필요가 있을 것이다.
 * 그래서 @Data 를 남발하는 것은 지양해야만 한다.
 */

/**
 * @Builder ?
 * 디자인 패턴임 !
 * 왜 사용하나?
 * 이걸 안 쓰고 그냥 멤버변수들을 생성자로 파라미터 값에 넣게 되면은
 * 이게 뭘 뜻하는지 이해하기 어려울 것이다. (실수할 수 있음)
 * Test test1 = new Test(1, true); <- 1, true 가 뭐지...
 * 하지만 Builder 패턴을 이용하게 된다면
 * Test test1 = Test.builder()
 *                  .isTest(true)
 *                  .testNumber(1)
 *                  .build();
 * 확실히 알기 편하게 됨.
 * 그리고 순서에 의존하지 않아도 괜찮다.
 * 어떤 값에 어떤 값이 들어가고 하는지 직관적이며 알아보기 편하다.
 * null 값을 넣더라도 그냥 안 넣어버리면 그만. 내가 넣고 싶은 값만 초기화시키면 되는 것
 */


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    private String ticker;
    private String name;

}
