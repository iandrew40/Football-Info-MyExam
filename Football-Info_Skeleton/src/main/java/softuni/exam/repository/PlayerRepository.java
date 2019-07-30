package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.domain.entities.Player;

import java.util.Collection;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Integer> {

    @Query(value = "SELECT p FROM Player p JOIN Team t ON p.team = t.id WHERE t.name = 'North Hub'")
    Collection<Player> findAllPlayersByTeam();

    @Query(value = "SELECT p FROM Player p WHERE p.salary > 100000 order by p.salary desc ")
    Collection<Player> findPlayers();

}
