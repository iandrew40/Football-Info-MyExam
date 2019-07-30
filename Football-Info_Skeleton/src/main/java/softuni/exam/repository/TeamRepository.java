package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.domain.entities.Team;

import java.util.Collection;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

    Team findByName(String name);

    @Query(value = "SELECT t FROM Team t WHERE t.name = 'North Hub'")
    Collection<Team> findAllTeamsByTeamName();
}
