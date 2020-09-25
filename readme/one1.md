<h1><b>JPA 소개 🦰</b></h1>
<hr/><br/>
<h2>1. JPA 소개</h2>
<br/>기존에 데이터 접근 계층을 개발 하는 과정은 SQL을 수정 해야 하고, JDBC API를 계속 바꿔줘야 하는 단점이 있었다.<br><br>
<h4>1-1. SQL을 직접 다룰 때 발생하는 문제점</h4><hr>
    <ol>
        <li>DAO 계층을 숨기더라도 직접 SQL을 열어서 확인을 해야했다(계층 분할의 문제)</li>
        <li>필드가 추가될 경우 연관되어 있던 쿼리들에 대해서 전부 필드를 추가 및 맵핑해줘야 했다.(SQL에 의존적인 개발)</li>
        <li>요구사항을 모델링한 객체를 엔티티라 하는데 SQL에 의존적이기 때문에 해당 객체를 신뢰할 수 없다(엔티티를 신뢰할 수 없음)</li>
    </ol><br/>
    이 문제를 해결하기위해 JPA는 어떤방식으로 문제 해결 JPA의 CRUD API를 살펴보자.<br>
    <br>
    <h3>1. 저장기능</h3>
    <code>
        jpa.persist(member);    //저장
    </code>
    <blockquote>
        persist() 메소드는 JPA가 객체와 매핑정보를 보고 INSERT SQL을 생성하여 데이터베이스에 저장한다.
    </blockquote>
    <h3>2. 조회 기능</h3>
    <code>
        String memeberId = "helloId";<br/>
        Member member  = jpa.find(Member.class, memberId);  //조회
    </code>
    <blockquote>
        find() 메소드는 JPA가 객체와 매핑정보를 보고 적절한 SELECT SQL을 생성해서 결과로 인자로 넣은 객체를 생성해서 반환한다.
    </blockquote>
    <h3>3. 수정 기능</h3>
    <code>
        Member member  = jpa.find(Member.class, memberId);<br/>
        member.setName("변경할이름"); //수정
    </code>
    <blockquote>
        수정은 별도의 메소드를 제공하지 않고 트랜잭션을 커밋할 때 객체를 조회해서 적절한 UPDATE SQL을 실행시킨다.
    </blockquote>
    <h3>4. 연관된 객체 조회</h3>
    <code>
        Member member  = jpa.find(Member.class, memberId);<br>
        Team team = member.getTeam();   //연관된 객체 조회
    </code>
    <blockquote>
        JPA는 연관된 객체를 사용하는 시점에 SELECT SQL을 실행해준다.
    </blockquote><hr>
<h4>1-2. 패러다임의 불일치</h4><hr>
    <h5>데이터를 저장하기 위해 데이터베이스를 사용하지만 데이터베이스는 추상화, 상속, 다형성 같은 개념이 없다.<br/>
    객체와 데이터베이스는 서로 목적이 다르므로 패러다임 불일치 문제가 있는데 이 문제점들을 알아보고 JPA를 통한 해결책을 알아보자<br>
    
    </h5>
<h4>1-3. JPA란 무엇일까?</h4><hr>
<h4>1-4. 정리</h4><hr>
<h4>1-5. ORM에 대한 궁금증과 오해</h4><hr>