package hellojpa;

import hellojpa.entity.Member;
import hellojpa.entity.MemberType;
import hellojpa.entity.Team;

import javax.persistence.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            //팀 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            //회원 저장
            Member member = new Member();
            member.setName("member1");
            member.setTeam(team); //단방향 연관관계 설정, 참조 저장
            em.persist(member);

            //등록
            //Member member = new Member();
            //member.setId(1L);
            //member.setName("hello");
            //member.setMemberType(MemberType.USER);
            //member.setTeamId(member);
            em.persist(member);


            //검색
            /*String jpql = "select m  from Member m where m.name. like '%hello%' ";
            List<Member> result = em.createQuery(jpql, Member.class)
                    .getResultList();*/

            //조회
            Member findMember = em.find(Member.class, member.getId());
            Long TeamId = findMember.getId();

            //연관관계가 없음
            //Team findTeam = em.find(Team.class, team.getId());

            //참조를 사용해서 연관관계 조회
            Team findTeam = findMember.getTeam();

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
