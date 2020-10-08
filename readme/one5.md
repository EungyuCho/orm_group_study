<h1><b>연관관계 기초 ♻</b></h1>
<hr/><br/>
<a href="https://github.com/EungyuCho/orm_group_study/blob/master/README.md">목차로 돌아가기</a> 🏃 
<h3>5장에서는 JPA의 참조와 외래키를 어떻게 매핑하는지 알아보자.</h3>
엔티티는 대부분 다른 엔티티가 연관관계가 있다.<br>
객체는 참조(주소)를 사용해서 관계를 맺고 테이블을 외래 키를 사용해서 관계를 맺는다.<br>
서로 다른 두개를 매핑하는것이 이장의 목표이다.<br>
시작하기 전에 연관관계 매핑을 이해하기 위해 두가지 키워드를 정리해보자.
<ol>
    <li>방향(Direction): [단방향, 양방향]이 있다. 방향은 객체관계에만 존재하고 테이블 관계는 항상 양방향이다<blockquote>
    예를 들어 회원과 팀이 있을때 회원 -> 팀 또는 팀 -> 회원 둘 중 한쪽만 참조하는 것을 단반향 관계라 하고 회원 -> 팀, 팀 -> 회원 양쪽 모두 서로 참조하는 것을 양방향 관계라고 한다.</blockquote></li>
    <li>다중성(Multiplicity): [다대일(N:1), 일대다(1:N), 일대일(1:1), 다대다(N:M)] 다중성이 있다.<blockquote>
    예를 들어 회원과 팀 관계가 있을 때 여러 회원은 한 팀에 속하므로 회원과 팀은 다대일(N:1)관계이다. 반대로 한 팀에 여러회원이 소속될 수 있으므로 팀과 회원의 관계는 일대다(1:N) 관계이다.</blockquote></li>
    <li>연관관계의 주인(Owner) : 객체를 양방향 연관관계로 만들면 연관관계의 주인을 정해야한다.</li>
</ol>
<hr>
<h3>1. 단방향 연관관계</h3>
연관관계 중에선 다대일(N:1) 단반향 관계를 가장 먼저 이해해야 한다. 지금부터 회원과 팀의 관계를 통해 다대일 단반향 고나계를 알아보자.<br>
<br>
<ul>
    <li>회원과 팀이 있다.</li>
    <li>회원은 하나의 팀에만 소속될 수 있다.</li>
    <li>회원과 팀은 다대일 관계다.</li>
</ul>
<img src="img/N-1_mapping.PNG" width="800px" height="350px">
위 그림을 통해 단방향 연관관계를 알아보자.<br>
<h5>객체 연관관계</h5>
<ul>
    <li>회원과 객체는 Member.team 필드(멤버변수)로 팀 객체와 연관관계를 맺는다.</li>
    <li>회원 객체와 팀 객체는 단방향 관계이다. 회원은 Member.team 필드를 통해서 팀을 참조할 수 있지만 팀은 회원을 알 수 없다.</li>
</ul>
<h5>테이블 연관관계</h5>
<ul>
    <li>회원 테이블은 TEAM_ID 외래 키로 팀 테이블과 연관관계를 맺는다.</li>
    <li>회원 테이블과 팀 테이블은 양방향 관계이다. MEMBER테이블의 TEAM_ID 외래키로 MEMBER테이블과 TEAM테이블을 조인할 수 있다. 어느테이블을 기준으로 잡아도 JOIN이 가능하므로 양방향 관계이다.</li>
</ul>
위같이 객체는 참조로 연관관계를 맺고 테이블은 외래 키로 연관관계를 맺는다.<br>
이제 JPA를 사용해서 객체와 테이블의 연관관계를 매핑해 보자.
<pre><code>@Entity
public class Member    {<br>
    @Id
    @Column(name = "MEMBER_ID")
    private String id;<br>
    private String username;<br>
    //연관관계 매핑
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;<br>
    //연관관계 설정
    public void setTeam(Team team)  {
        this.team = team;
    }<br>
    //Getter, Setter ...
}<br>
public class Team  {
    private String id;
    private String name;<br>
    //Getter, Setter ...
}</code></pre>
위의 코드를 사용해서 회원1과 회원2를 팀1에 소속시켜보자.
<pre><code> //매핑할 회원 엔티티<br>public static void main(String[] args)  {<br>
    //생성자(id, 이름)
    Member member1 = new Member("member1", "회원1");
    Member member2 = new Member("member2", "회원2");    
    Team team1 = new Team("team1", "팀1");<br>
    member1.setTeam(team1);
    member2.setTeam(team1);<br>
    Team findTeam = member1.getTeam();
}<br>
    //매핑한 팀 엔티티
@Entity
public class Team  {<br>
    @Id
    @Column(name = "TEAM_ID")
    private String id;<br>
    private String name;<br>
    //Getter, Setter ...
}    
</code></pre>
위에서 회원 엔티티와 팀 엔티티를 매핑했다.<br>
객체 연관관계는 회원 객체의 Member.team 필드를 사용해서 연관관계를 매핑할 수 있었고, 테이블 연관관계는 회원 테이블의 MEMBER.TEAM_ID 외래 키 컬럼을 사용해서 연관관계를 설정 할 수 있었다.<br>
회원 엔티티를 보면 연관관계를 매핑하기위한 새로운 어노테이션들이 있는데 이 어노테이션들을 알아보자.
<ul>
    <li><code>@ManyToOne</code>: 이름 그대로 다대일(N:1) 관계를 나타내는  매핑 정보다. 연관관계를 매핑할때는 항상 이렇게 다중성을 나타내는 어노테이션을 사용해야 한다.</li>
    <li><code>@JoinColumn(name="TEAM_ID");</code> 조인 컬럼은 외래 키를 매핑할 때 사용한다. name 속성에는 매핑할 외래 키 이름을 설정한다. 위 코드에서는 회원과 팀 테이블의 TEAM_ID 외래키로 연관관계를 맺으므로 이 값을 설정해서 매핑했다. 생략 할 시 필드 명 + _ + 참조하는 테이블 의 기본키 컬럼 명이 되므로 생략하여도 TEAM_ID가 매핑된다.</li>
</ul>
<h5><code>@JoinColumn</code></h5>
<code>@ManyToOne</code> 어노테이션은 다대일 관계에서 사용한다. 속성들을 살펴보자.
<ol>
    <li>name<blockquote>매핑할 외래 키 이름<br>
    기본값 : 필드명 + _ + 참조하는 테이블의 기본 키 컬럼명</blockquote></li>
    <li>referencedColumnName<blockquote>외래 키가 참조하는 대상 테이블의 컬럼명<br>
    기본값 : 참조하는 테이블의 기본 키 컬럼명</blockquote></li>
    <li>foreignKey(DDL)<blockquote>외래 키 제약조건을 직접 지정할 수 있다. 이 속성은 테이블을 생성할 때에만 사용한다.</blockquote></li>
    <li>unique, nullable, insertable, updatable, columnDefinition, table<blockquote><code>@Column</code>의 속성과 같다.</blockquote></li>
</ol>
<h5><code>@ManyToOne</code></h5>
<code>@ManyToOne</code> 어노테이션은 다대일 관계에서 사용한다. 주요속성을 살펴보자.
<ol>
    <li>optional<blockquote>false로 설정하면 연관된 엔티티가 항상 있어야 한다.<br>
    기본값 : true</blockquote></li>
    <li>fetch<blockquote>글로벌 패치 전략을 설정한다. 자세한 내용은 8장에서 다룬다.<br>
    기본값 : 
    <ul>
        <li><code>@ManyToOne = FetchType.EAGER</code></li>
        <li><code>@OneToMany = FetchType.LAZY</code></li>        
    </ul></blockquote></li>
    <li>cascade<blockquote>영속성 전이 기능을 사용한다. 자세한 내용은 8장에서 설명한다.</blockquote></li>
    <li>targetEntity<blockquote>연관된 엔티티의 타입 정보를 설정한다. 이 기능은 거의 사용하지 않는다. 컬렉션을 사용해도 제네릭 타입으로 타입 정보를 알 수 있다.</blockquote></li>
</ol>