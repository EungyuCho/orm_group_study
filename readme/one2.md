<h1><b>JPA 시작 🎮</b></h1>
<hr/><br/>
<a href="https://github.com/EungyuCho/orm_group_study/blob/master/README.md">목차로 돌아가기</a> 🏃 
<h1>JPA 매핑 어노테이션</h1>
<h3>@Entity</h3>
이 클래스를 테이블과 매핑한다고 JPA에 알려준다.<br>
<h3>@Table</h3>
엔티티 클래스에 매핑 할 테이블 정보를 알려준다. default는 클래스 이름을 테이블 이름으로 매핑한다.<br>
<h3>@Id</h3>
엔티티 클래스의 필드를 기본 키에 매핑한다. @Id가 사용된 필드를 식별자 필드라 한다.<br>
<h3>@Column</h3>
필드를 컬럼에 매핑한다.<br>
<h3>매핑 정보가 없는 필드</h3>
어노테이션을 생략 시 컬럼명으로 매핑합다. 만약 대소문자를 구분하는 데이터베이스의 경우 name속성을 사용해서 명시적으로 매핑하여야 한다.<br>
<br>
<hr>
<h2>어플리케이션 개발</h2>
<hr>
<pre><code>
public class JpaMain {
    <br>
    public static void main(String[] args) {
        // [엔티티 매니저 팩토리] - 생성
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        <br>
        // [엔티티 매니저] - 생성
        EntityManager em = emf.createEntityManager();
        // [트랜잭션] - 획득
        EntityTransaction tx = em.getTransaction();
        <br>
        try {
            tx.begin();         //[트랜잭션] - 시작
            logic(em);          //비즈니스 로직 실행
            tx.commit();        //[트랜잭션] - 커밋
        } catch (Exception e) {
            tx.rollback();      //[트랜잭션] - 롤백
        } finally {
            em.close();         //[엔티티 매니저] - 종료
        }
        emf.close();            //[엔티티 매니저 팩토리] - 종료
    }
    private static void logic(EntityManager em) { ... }
</code></pre>
코드는 크게 3부분으로 나뉘어 있다.<br>
1. 엔티티 매니저 설정<br>
2. 트랜잭션 관리<br>
3. 비즈니스 로직<br>
설정을 하나씩 살펴보자.<br>
<hr>
<h2>엔티티 매니저 설정</h2><hr>
JPA를 시작하려면 persistence.xml의 설정 정보를 사용해서 엔티티 매니저 팩토리를 생성한다.<br>
<code> EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");</code><br>
위 코드는 META-INF/persistence.xml에서 이름이 jpabook인 영속성 유닛(persistence-unit)을 찾아서 엔티티 매니저 팩토리를 생성한다.<br>
엔티티 매니저 팩토리를 생성하는 비용이 크므로 어플리케이션에서 공유하여 사용하여야 한다.<br>
<hr>
<h2>엔티티 매니저 생성</h2>
<hr>
<code>EntityManager em = emf.createEntityManager()</code>
팩토리에서 엔티티 매니저를 생성한다.<br>
JPA의 기능은 대부분 엔티티 매니저를 이용해서 사용되며 대표적으로 DB에 CRUD가 가능하다.<br>
엔티티 매니저는 DB 커넥션과 밀접한 고나계가 있으므로 스레드간 공유하거나 재사용하면 안된다.<br>
<hr>
<h2>종료</h2>
<hr>
사용이 끝난 엔티티 매니저는 종료해줘야한다.<br>
<code>em.close();</code><br>
어플리케이션을 종료할 때 엔티티 매니저 팩토리도 종료해줘야 한다.<br>
<code>emf.clsoe();</code><br>
<hr>
<h1>트랜잭션 관리</h1>
JPA를 사용할 시 트랜잭션을 항상 시작하고 비즈니스 로직을 실행해야한다.<br>
트랜잭션은 엔티티 매니저에서 받아온다.
<pre><code>EntityTransaction tx = em.getTransaction();<br>
try {
    tx.begin();         //[트랜잭션] - 시작
    logic(em);          //비즈니스 로직 실행
    tx.commit();        //[트랜잭션] - 커밋
} catch (Exception e) {
    tx.rollback();      //[트랜잭션] - 롤백
}
</code></pre>
트랜잭션 API를 사용해서 로직이 정상적으로 동작 시 commit 실패 시 rollback 한다.<br>
<hr>
<h1>비즈니스 로직</h1>
비즈니스 로직은 엔티티 매니저를 통해서 등록, 수정, 삭제, 조회 작업이 일어난다.<br><br>
<h4>1.등록</h4>
<pre><code>
        String id = "id1";
        Member member = new Member(id);
        member.setUsername("지한");
        member.setAge(2);
        //등록
        em.persist(member);
</code></pre>
persist 메소드에 엔티티를 넘겨주면 JPA가 매핑정보를 보고 SQL을 만들어 데이터베이스에 전달한다.<br>
<h4>2.수정</h4>
<code>member.setAge(20);</code><br>
엔티티의 값만 변경하더라도 JPA는 엔티티를 추적해서 UPDATE SQL을 데이터베이스에 전달한다.<br>
<h4>3.삭제</h4>
<code>em.remove(member);</code><br>
remove() 메소드에 삭제할 엔티티를 넘겨주면 JPA는 DELETE SQL을 데이터베이스에 전달한다.<br>
<h4>4.한 건 조회</h4>
<code>Member findMember = em.find(Member.class, id);</code><br>
find()메소드는 조회할 엔티티 타입과 @Id로 맵핑한 식별자 값으로 엔티티 하나를 조회하는 조회 메소드이다.<br>
SELECT SQL을 생성해서 조회하고 조회한 결과 엔티티를 반환한다.<br>
<hr>
<h1>JPQL</h1>
하나 이상의 회원목록을 조회하는 코드를 살펴보자.<br>
<pre><code>
//목록 조회
List<Member> members =
        em.createQuery("select m from Member m", Member.class).getResultList();
System.out.println("members.size = " + members.size());
</code></pre>
테이블이 아닌 엔티티를 대상으로 검색하려면 모든 데이터를 불러와서 엔티티 객체로 변경한 후 검색해야 하는데 거의 불가능하므로,<br>
SQL을 사용해야하는데 JPQL(Java Presistence Query Language)라는 쿼리 언어로 이 문제를 해결한다.<br>
<br>
JPQL은 엔티티 객체를 대상으로 쿼리한다.<br>
위의 쿼리 예제도 Member 엔티티 객체를 조회한다. JPQL을 사용하려면 em.createQuery(JPQL, 반환타입) 메소드를 실행해서 쿼리를 생성한 후 쿼리객체의 getResultList() 메소드를 호출해서 반환받을 수 있다.<br>
JPA는 다음 JPQL을 분석해서 적절한 SQL을 만들어서 데이터베이스에서 데이터를 조회한다. => <code>SELECT M.ID, M.NAME, M.AGE FROM MEMBER M</code><br>
<hr>
<h1>정리</h1>
JPA를 사용하기위한 환경을 설정하고 CRUD를 하는 간단한 어플리케이션을 작성하였다.<br>
코드량이 줄어들었으며 SQL에 의존적이지도 않은 코드를 만들 수 있었다. 다음 파트에서는 JPA의 핵심 기능인 영속성 관리에 대해서 알아보자.