<h1><b>엔티티 매핑 🧲</b></h1>
<hr/><br/>
<a href="https://github.com/EungyuCho/orm_group_study/blob/master/README.md">목차로 돌아가기</a> 🏃 
<h3>4장에서는 엔티티와 테이블을 매핑하는 어노테이션에 대해서 알아보자</h3>
대표적인 엔티티 매핑 어노테이션은 아래와 같다
<ol>
    <li>객체와 테이블 매핑: <code>@Entity, @Table</code></li>
    <li>기본 키 매핑: <code>@Id</code></li>
    <li>필드와 컬럼 매핑: <code>@Column</code></li>
    <li>연관관계 매핑: <code>@ManyToOne, @JoinColumn</code></li>
</ol>
<hr>
<h3>1. <code>@Entity</code></h3>
JPA를 사용해서 매핑할 클래스는 <code>@Entity</code>어노테이션을 필수로 붙여야 한다.<br>
<hr>
<h5>1-1. <code>@Entity</code> 속성 정리</h5>
<ol>
    <li>name<blockquote>JPA에서 사용할 엔티티 이름을 지정한다. 만약 다른 패키지에 같은 이름의 엔티티 클래스가 있다면 이름을 지정해서 충돌하지 않도록 바꿔줘야 한다.<br>
    기본값 : 클래스 이름을 그대로 사용</blockquote></li>
</ol>
<h5>1-2. 주의사항</h5>
<ol>
    <li>기본 생성자는 필수다(파라미터가 없는 public 또는 protected 생성자)</li>
    <li>final 클래스, enum, interface, inner 클래스에는 사용할 수 없다.</li>
    <li>저장할 필드에 final을 사용하면 안된다.</li>
</ol>
<h3>2. <code>@Table</code></h3>
<code>@Table</code>어노테이션은 엔티티와 매핑할 테이블을 지정한다. 생략 시 엔티티 이름을 테이블 이름으로 사용한다.<br>
<hr>
<h5>1-1. <code>@Entity</code> 속성 정리</h5>
<ol>
    <li>name<blockquote>매핑할 테이블 이름<br>
    기본값 : 엔티티 이름을 사용</blockquote></li>
    <li>catalog<blockquote>catalog 기능이 있는 데이터베이스에서 catalog를 매핑한다.</blockquote></li>
    <li>schema<blockquote>schema 기능이 있는 데이터베이스에서 schema를 매핑한다.</blockquote></li>
</ol>
<h3>3. 다양한 매핑 사용</h3>
기존 회원 관리 프로그램에서 다음 요구사항들이 추가되었다.
<ol>
    <li>회원은 일반 회원과 관리자로 구분해야 한다.</li>
    <li>회원 가입일과 수정일이 있어야 한다.</li>
    <li>회원을 설명할 수 있는 필드가 있어야 한다. 이 필드는 길이 제한이 없다.</li>
</ol>
<pre><code>package jpabook.start;<br>
import javax.persistence.*;
import java.util.Date;
@Entity
@Table(name = "MEMBER")
public class Member {
    @Id
    @Column(name = "ID")
    private final String id;    //아이디<br>
    @Column(name = "NAME")
    private String username;        //이름<br>
    private Integer age;        //나이<br>
    //== 추가==
    @Enumerated(EnumType.STRING)
    private RoleType roleType; //①<br>
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;//②<br>
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;//③<br>
    @Lob
    private String description;
    //Getter, Setter
    ...
}<br>
package jpabook.start;<br>
public enumRoleType {
    ADMIN, USER
}</code></pre>
다음 코드들을 분석해 보자<br>
① roleType : 자바의 enumd을 사용해서 일반회원은 USER, 관리자는 ADMIN으로 구분했다. 자바의 enum을 사용하려면 <code>@Enumerted</code>어노테이션을 사용하면 된다.<br>
② createdDate, lastModifiedDate : 자바의 날짜 타입은 <code>@Temporal</code>을 사용해서 매핑한다.<br>
③ description : 회원을 설명하는 필드는 길이제한이 없으므로 VARCHAR가 아닌 CLOB 타입으로 저장해야한다. <code>@Lob</code>을 사용하면 CLOB,BLOB타입을 매핑할 수 있다.<br>
<h3>4. 데이터베이스 스키마 자동 생성</h3>
JPA는 persistence설정에서 스키마 자동생성 속성을 추가해서 데이터베이스를 자동생성 할 수 있다.<br>
<code>&lt;Property name="hibernate.hbmddl.auto" value="create" /&gt;</code>
이 속성을 사용하면 데이터베이스 테이블을 자동으로 생성한다. <code>hibernate.show_sql</code> 속성을 true로 사용하면 DDL을 콘솔에 출력 할 수 있다. ==> <code>&lt;Property name="hibernate.show_sql"
 value="true" /&gt;</code><br>
어플리케이션을 실행하면 다음과 같은 DDL이 출력되면서 테이블이 생성된다.<br>
<pre><code>Hibernate:
    drop table MEMBER if exists
Hibernate:
    create table MEMBER    {
        ID varchar(255) not null,
        NAME varchar(255),
        age integer,
        roleType varchar(255),
        createDate timestamp,
        lastModifiedDate timestamp,
        description clob,
        primary key (ID)
    }</code></pre> 
위처럼 테이블이 정상적으로 생성되지만 자동생성기능이 만든 DDL은 완벽하지는 않으므로 개발환경에서 사용하거나 매핑을 어떻게 해야할 지 확인하는 정도로만 사용한다.<br>
<h5>1-1. hdm2ddl 속성 정리</h5>
<ol>
    <li>create<blockquote>기존 테이블을 삭제하고 새로 생성한다. DROP + CREATE</blockquote></li>
    <li>create-drop<blockquote>create 속성에 추가로 어플리케이션을 종료할 때 생성한 DDL을 제거한다. DROP + CREATE + DROP</blockquote></li>
    <li>update<blockquote>데이터베이스 테이블과 엔티티 매핑정보를 비교해서 변경 사항만 수정한다.</blockquote></li>
    <li>validate<blockquote>데이터베이스 테이블과 엔티티 매핑정보를 비교해서 차이가 있으면 경고를 남기고 어플리케이션을 실행하지 않는다. </blockquote></li>
    <li>none<blockquote>자동 생성 기능을 사용하지 않으려면 hbm2ddl.auto 속성 자체를 삭제하거나 유효하지 않은 옵션 값을 주면 된다.</blockquote></li>    
</ol>
<h3>5. DDL 생성 기능</h3>
회원 이름은 필수로 입력되어야 하고 10자를 초과하면 안 된다는 제약조건이 추가되었다. 스키마 자동 생성하기를 통해 만들어지는 DDL에 제약조건을 추가해보자.<br>
<pre><code>@Entity
@Table(name="MEMBER")
public class Member{<br>
    @Id
    @Column(name = "ID")
    private String id;<br>
    @Column(name= "NAME", nullable = false, length = 10)    //추가
    private String username;
    ...
}</code></pre>
nullable을 false로 설정하면 DDL에 not null 제약조건을 추가할 수 있고, length 속성 값을 사용해서 문자의 크기를 지정할 수 있다.
<br>
이번에는 유니크 제약조건을 만들어보자
<pre><code>@Entity
@Table(name = "MEMBER", uniqueConstraints = {@UniqueConstraint(
        name = "NAME_AGE_UNIQUE",
        columnNames = {"NAME", "AGE"} )})
public class Member {
    ...
}</code></pre>
<code>@Table</code>에 <code>@uniqueContraint</code> 속성을 추가해서 제약조건을 설정 할 수 있다.<br>
name에 제약조건 이름, columnNames에 제약조건 Column을 설정하면 된다.<br>
위 속성을 추가하면
<pre><code>ALTER TABLE MEMBER
    ADD CONSTRAINT NAME_AGE_UNIQUE UNIQUE (NAME, AGE)</code></pre>
제약조건이 추가된다.
<h3>6. 기본 키 매핑</h3>
JPA에서 기본키의 생성전략은 다음과 같다<br>
<ol>
    <li>직접 할당 : 기본 키를 어플리케이션에서 직접 할당한다.</li>
    <li>자동 생성: 대리 키 사용 방식<blockquote>
    - IDENTITY: 기본 키 생성을 데이터베이스에 위임한다.<br>
    - SEQUENCE: 데이터베이스 시퀀스를 사용해서 기본 키를 할당한다.<br>
    - TABLE: 키 생성 테이블을 사용한다.</blockquote></li>
</ol>
<h5>6-1. 기본 키 할당 전략</h5>
기본키를 직접 할당하려면 @Id로 매핑하면 되고 적용가능한 자바 타입은 아래와 같다.
<ol>
    <li>자바 기본형</li>
    <li>자바 래퍼(Wrapper)형</li>
    <li>String</li>
    <li>java.util.Date</li>
    <li>java.sql.Date</li>
    <li>java.math.BigDecimal</li>
    <li>java.math.BigInteger</li>
</ol>
다음은 <code>em.persist()</code>메소드로 기본키를 직접 할당하는 방법이다.
<pre><code>Board board = new Board();
board.setId("id1")  //기본 키 직접 할당
em.persist(board);</code></pre> 
<h5>6-2. IDENTITY 전략</h5>
IDENTITY는 기본 키 생성을 데이터베이스에 위임하는 전략이다.<br>
주로 MySQL, PostgresSQL, SQL Server, DB2에서 사용한다.<br>
MySQL을 기준으로 AUTO_INCREMENT 기능을 수행하는 예제를 보자
<pre><code>CREATE TABLE <BOARD>{
    ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    DATA VARCHAR(255)
};<br>
INSERT INTO BOARD(DATA) VALUES('A');
INSERT INTO BOARD(DATA) VALUES('B');
</BOARD></code></pre>
해당 결과를 보면 ID가 자동으로 입력되며 AUTO_INCREMENT가 적용된다.<br>
또 위와같이 식별자 생성 전략이 있는 경우
<pre><code>@Entity
public class Borad{<br>
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    ...
}</code></pre>
위와 같이 <code>@GeneratedValue</code>의 strategy 속성을 IDENTITY로 설정하여야 한다.
<blockquote>위 전략은 데이터베이스에 저장해야 식별자를 구할 수 있으므로 <code>em.persist()</code>코드를 호출하는 즉시  INSERT SQL이 데이터베이스에 전달한다. 즉 쓰기 지연이 동작하지않는다.</blockquote>
<h5>6-3. SEQUENCE 전략</h5>
데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트이다.<br>
SEQUENCE 전략은 이 시퀀스를 사용해서 기본 키를 생성하는데 주로 사용하는 데이터베이스는 Oracle, PostgresSQL, DB2, H2 데이터베이스가 있다.<br>
시퀀스 사용 예제를 보자.
<pre><code>//DDL
CREATE TABLE BOARD{
    ID BIGINT NOT NULL PRIMARY KEY,
    DATA VARCHAR(255)
}<br>
//시퀀스 생성
CREATE SEQUENCE BOARD_SEQ START WITH 1 INCREMENT BY 1<br>
//코드
@Entity
@SequenceGenerator(
        name = "BOARD_SEQ_GENERATOR",
        sequenceName = "BOARD_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 1)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
                    generator = "BOARD_SEQ_GENERATOR")
    private final Long id;    //아이디
</code></pre>
위와같이 DDL과 코드를 설정하면 BOARD_SEQ_GENERATOR 시퀀스를 사용해서 기본키를 설정한다.<br>
IDENTITY전략과 다른점은 <code>em.persist()</code>호출 시 시퀀스를 사용해서 식별자를 조회한 후 엔티티에 할당해서 Persistence Context에 저장하고 <code>flush()</code>호출 시 데이터베이스에 저장한다. 
<h5><code>@SequenceGenerator</code></h5>
<code>@SequenceGenerator</code>의 속성을 살펴보자
<ol>
    <li>name<blockquote>식별자 생성기 이름<br>
    기본값 : 필수</blockquote></li>
    <li>sequenceName<blockquote>데이터베이스에 등록되어 있는 시퀀스 이름<br>
    기본값 : hibernate_sequence</blockquote></li>
    <li>initialValue<blockquote>DDL 생성 시에만 사용되며, 시퀸스 DDL을 생성할 때 처음 시작하는 수를 지정한다.<br>
    기본값 : 1</blockquote></li>
    <li>allocationSize<blockquote>시퀀스 한 번 호출에 증가하는 수(성능최적화에 사용됨)<br>
    기본값 : 50</blockquote></li>
    <li>catalog schema<blockquote>데이터베이스 catalog, schema이름</blockquote></li>
</ol>
따라서 매핑되는 DDL은 다음과 같다.
<pre><code>create sequence [sequenceName]
start with [initialValue] increment by [allocationSize]</code></pre>
<blockquote>allocationSize는 시퀀스에 접근하는 횟수를 줄이기 위해 한번에 시퀀스를 증가시킨 후 메모리까지 식별자를 할당해서 데이터를 등록할 때 메모리에 있는 시퀸스값을 할당한다. 또 Size가 max일 경우 한번 더 allocationSize만큼 다시 시퀀스를 증가시킨 후 재할당한다.<br>
이 기능은 hibernate.id.new_generator_mappings 속성이 true여야 동작한다.</blockquote>
<h5>6-4. TABLE 전략</h5>
TABLE 전략은 키 생성 전용 테이블을 만들고 여기에 이름과 값으로 사용할 컬럼을 만들어서 시퀀스를 흉내내는 전략이다.<br>
테이블은 아래와 같이 생성하면 된다.
<pre><code>//DDL<br>CREATE TABLE MY_SEQUENCES{
    sequence_name varchar(255) not null,
    next_val bigint,
    primary key(  sequence_name )
}<br>
//code
@Entity
@TableGenerator(
    name = "BOARD_SEQ_GENERATOR",
    table = "MY_SEQUENCES",
    pkColumnValue = "BOARD_SEQ", allocationSize = 1)
public class Board{<br>
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
        genertor = "BOARD_SEQ_GENERATOR")
    private Long id;
    ...
}</code></pre>
<code>@TableGenerator</code>을 먼저 사용해서 테이블 키 생성기를 등록하고 BOARD_SEQ_GENERATOR라는 이름의 생성기로 MY_SEQUNCES라는 테이블을 매핑했다.
<br>
이제 <code>@TableGenerator</code>의 속성을 분석해보자.<br>
<ol>
    <li>name<blockquote>식별자 생성기 이름<br>
    기본값 : 필수</blockquote></li>
    <li>table<blockquote>키생성 테이블 명<br>
    기본값 : hibernate_sequences</blockquote></li>
    <li>pkColumnName<blockquote>시퀀스 컬럼명<br>
    기본값 : sequence_name</blockquote></li>
    <li>valueColumnValue<blockquote>시퀀스 값 컬럼명<br>
    기본값 : next_val</blockquote></li>
    <li>pkColumnValue<blockquote>키로 사용할 값 이름<br>
    기본값 : 엔티티 이름</blockquote></li>
    <li>initialValue<blockquote>초기 값. 마지막으로 생성된 값이 기준이다<br>
    기본값 : 0</blockquote></li>
    <li>allocationSize<blockquote>시퀀스 한 번 호출에 증가하는 수(성능최적화에 사용됨)br>
    기본값 : 50</blockquote></li>
    <li>catalog schema<blockquote>데이터베이스 catalog, schema이름</blockquote></li>
    <li>uniqueConstraints(DDL)<blockquote>유니크 제약 조건을 지정할 수 있다.</blockquote></li>
</ol>
<blockquote>TABLE전략은 값을 조회하면서 SELECT쿼리르 사용하고 값을 증가시키기 위해서 UPDATE를 사용하므로 최적화하려면 SEQUENCE전략과 똑같이 <code>@TableGenerator.allcationSize</code>를 사용하면 된다.</blockquote>
<h5>6-5. AUTO 전략</h5>
AUTO 전략은 데이터베이스의 방언에따라 IDENTITY, SEQUENCE, TABLE 전략 중 하나를 자동으로 선택한다.<br>
예를 들어 Oracle을 선택 시 SEQUENCE를, MySQL을 선택하면 IDENTITY를 사용한다.<br>
AUTO를 사용할때 SEQUENCE나 TABLE 전략이 섵개되면 시퀀스나 키 생성용 테이블을 미리 만들어 두어야 한다. 만약 스키마 자동 생성 기능ㅇ르 사용한다면 하이버네이트가 기본값으로 적절한 시퀀스나 키 생성용 테이블을 만들어준다.<br>
<h3>7. 필드와 컬럼 매핑 : 레퍼런스</h3>
JPA가 제공하는 필드와 컬럼 매핑용 어노테이션들을 레퍼런스형식으로 살펴보자<br>
<ol>
    <li>필드와 컬럼 매핑<ol>
        <li>@Column : 컬럼을 매핑한다.</li>
        <li>@Enumerated : 자바의 Enum 타입을 매핑한다.</li>
        <li>@Temporal : 날짜 타입을 매핑한다.</li>
        <li>@Lob : BLOB, CLOB 타입을 매핑한다.</li>
        <li>@Transient : 특정 필드를 데이터베이스에 매핑하지 않는다.</li>
    </ol></li>
    <li>기타
        <ol>
            <li>@Access : JPA가 엔티티에 접근하는 방식을 지정한다.</li>
        </ol>
    </li>
</ol>
위의 매핑 어노테이션들을 차례대로 알아보자.<br>
<h4>7-1. <code>@Column</code></h4>
<code>@Column</code>어노테이션은 테이블 컬럼을 매핑한다. 주로 name, nullable이 주로 사용되고 나머지는 잘 사용되지 않는다.<br>
<ol>
    <li>name : 필드와 매핑할 테이블의 컬럼 이름<blockquote>
    기본값 : 객체의 필드 이름</blockquote></li>
    <li>insertable : 엔티티 저장 시 필드도 같이 저장<blockquote>
    기본값 : true</blockquote></li>
    <li>updateable : 필드 수정 시 같이 수정<blockquote>
    기본값 : true</blockquote></li>
    <li>table : 하나의 엔티티를 두 개 이상의 테이블에 매핑할 때 사용한다. 지정한 필드를 다른테이블에 매핑할 수 있다.<blockquote>
    기본값 : 현재 클래스가 매핑된 테이블</blockquote></li>
    <li>nullable(DDL) : null 값의 허용 여부를 설정.<blockquote>
    기본값 : true</blockquote></li>
    <li>unique(DDL) : @Table의 uniqueConstraint와 같지만 한 컬럼에 간단히 유니크 제약조건을 걸 떄 사용</li>
    <li>columnDeinition(DDL) : 데이터베이스 컬럼 정보를 직접 줄 수 있다.<blockquote>
    기본값 : 필드의 자바 타입과 데이터베이스 방언 정보를 사용해서 적절한 컬럼타입을 생성</blockquote></li>
    <li>length(DL) : 문자 길이 제약조건, String 타입에만 사용한다.<blockquote>
    기본값 : 255</blockquote></li>
    <li>percision, scale(DDL) : BigDecimal 타입에서 사용한다(BigInteger도 사용 할 수 있다). precision은 소수점을 포함한 전체 자릿수, scale은 소수의 자릿수다. 참고로 double, float타입에는 적용되지 않는다. 아주 큰 숫자나 정밀한 소수를 다루어야 할 때만 사용한다.<blockquote>
    기본값 : precision=19, scale=2</blockquote></li>
</ol>
<h4>7-2. <code>@Enumerated</code></h4>
자바의 enum  타입을 매핑할때 사용한다.<br>
<pre><code>//Enum클래스
enum RoleType{
    ADMIN, USER
}<br>
//매핑 된 모델 필드
@Enumerated(EnumType.STRING)
private RoleType roleType;<br>
//사용하는 코드
member.setRoleType(RoleType.ADMIN); //DB에 문자 ADMIN으로 저장 됨
</code></pre>
위처럼 필드를 EnumType.STRING으로 저장하면 데이터베이스에 직접 문자로 들어가고,<br>
EnumType.ORDINAL로 저장 시 ADMIN은 0, USER는 1로 정의된 순서대로 젖아된다.
<h4>7-3. <code>@Temporal</code></h4>
날짜 타입을 매핑할때 사용한다. 속성을 먼저 알아보자
<ol>
    <li>value: <ol>
            <li>TemporalType.DATE : 날짜, 데이터베이스 date 타입과 매핑(예: 2013-10-11)</li>
            <li>TemporalType.TIME : 시간, 데이터베이스 time 타입과 매핑(예: 11:11:11)</li>
            <li>TemporalType.TIMESTAMP : 날짜와 시간, 데이터베이스 timestamp 타입과 매핑(예: 2013-10-11 11:11:11)</li>
        </ol><blockquote>
    기본값 : TemporalType은 필수로 지정해야 한다.</blockquote></li>
</ol>
<h4>7-4. <code>@Lob</code></h4>
<code>@Lob</code>에는 지정할 수 있는 매핑이 없다. 대신에 필드 타입이 문자면 CLOB으로 매핑하고 나머지는 BLOB으로 매핑한다.
<h4>7-5. <code>@Transient</code></h4>
이 필드는 매핑하지 않는다. 따라서 데이터베이스에 저장하지않고 조회도 안할때 객체에 임시로 값을 보관하고 싶을 때 사용한다.
<h4>7-6. <code>@Access</code></h4>
JPA가 엔티티에 접근하는 방식을 지정한다.
<ol>
    <li>필드 접근 : AccessType.FIELD로 지정한다. 필드에 직접 접근하고 접근권한이 private여도 접근이 가능하다.</li>
    <li>프로퍼티 접근 : AccessType.PROPERTY로 지정한다. 접근자(Getter)를 사용한다.</li>
</ol>
<pre><code>@Entity
@Access(AccessType.FIELD)
public class Member{<br>
    @Id
    private String id;<br>
    private String data1;
    private String data2;
    ...
}</code></pre>
필드에 이미 <code>@Id</code>가 선언되어 있으므로 <code>@Access(AccessType.FIELD)</code>로 설정한것과 같으므로 생략 가능하다.
<pre><code>@Entity
@Access(AccessType.PROPERTY)
public class Member{<br>
    private String id;<br>
    private String data1;
    private String data2;<br>
    @Id
    public String getId()   {
        return id;
    }<br>
    @Column
    public String getData1()    {
        return data1;
    }<br>
    public String getData2()    {
        return data2;
    }
}</code></pre>
<code>@Id</code>가 프로퍼티에 있으므로 <code>@Access(AccessType.PROPERTY)</code>로 설정한것과 같으므로 위와 같이 <code>@Access</code>를 생략 할 수 있다.
