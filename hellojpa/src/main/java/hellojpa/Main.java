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
            //member.setTeam(team); //단방향 연관관계 설정, 참조 저장
            //역방향 (주인이 아닌 방향)만 연관관계 설정
            team.getMembers().add(member);

            em.persist(member);

            // 새로운 팀B
            Team teamB = new Team();
            teamB.setName("TeamB");
            em.persist(teamB);
            // 회원1에 새로운 팀B 설정
            member.setTeam(teamB);

            //초기화
            em.flush();
            em.clear();

            //조회
            Member findMember = em.find(Member.class, member.getId());

            //참조를 사용해서 연관관계 조회
            Team findTeam = em.find(Team.class, team.getId());
            int memberSize = findTeam.getMembers().size(); //역방향 조회

            //검색
            /*String jpql = "select m  from Member m where m.name. like '%hello%' ";
            List<Member> result = em.createQuery(jpql, Member.class)
                    .getResultList();*/

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
